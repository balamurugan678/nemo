package com.novacroft.nemo.tfl.innovator.controller;

import com.google.gson.Gson;
import com.novacroft.nemo.common.application_service.UserService;
import com.novacroft.nemo.common.domain.cubic.HolderDetails;
import com.novacroft.nemo.tfl.common.application_service.AddUnattachedCardService;
import com.novacroft.nemo.tfl.common.application_service.PersonalDetailsService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.AddUnattachedCardCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.constant.*;
import com.novacroft.nemo.tfl.common.controller.BaseController;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.form_validator.OysterCardValidator;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;
import com.novacroft.nemo.tfl.common.util.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.novacroft.nemo.tfl.common.constant.PageParameter.*;

@Controller
@RequestMapping(value = Page.ADD_UNATTACHED_CARD)
@SessionAttributes("cardCmd")
public class AddUnattachedCardController extends BaseController {
    static final Logger logger = LoggerFactory.getLogger(AddUnattachedCardController.class);
    private String warningMessage = "The customer details stored on the Oyster card being attached do not match those in the online account / master customer record. If you submit this change, the details from the master customer record/ online account will be written to the Oyster card Are you sure you wish to continue?";
    
    @Autowired
    protected GetCardService getCardService;

    @Autowired
    protected CardDataService cardOysterOnlineService;

    @Autowired
    protected PersonalDetailsService personalDetailsService;
    
    @Autowired
    protected CustomerDataService customerDataService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected OysterCardValidator cardValidator;
    
    @Autowired
    protected AddUnattachedCardService addUnattachedCardService;
    
    @Autowired
    protected MessageSource messageSource;

    AddUnattachedCardController() {

    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView view(@RequestParam(value = CUSTOMER_ID, required = false) String customerId,
            @ModelAttribute(PageCommand.ADD_UNATTACHED_CARD) AddUnattachedCardCmdImpl cardCmd, HttpSession session) {

        if (null != customerId) {
            cardCmd.setCustomerId(customerId);
        }
        return createNewModelAndView(cardCmd);
    }

  
    @RequestMapping(value = "/valid", method = RequestMethod.POST)
    @ResponseBody
    public String checkSearch(AddUnattachedCardCmdImpl cmd, BindingResult result) {
        
        Map<String, String> messages = new HashMap<String, String>();
        cardValidator.validate(cmd, result);
        List<ObjectError> allErrors = result.getAllErrors();
        
        for (ObjectError error : allErrors) {
            String e = messageSource.getMessage(error, null);
            messages.put(error.getCode(), e);
        }
        return new Gson().toJson(messages);

    }


    @RequestMapping(value = "/cardSearch", method = RequestMethod.POST)
    @ResponseBody
    public String searchCard(AddUnattachedCardCmdImpl cmd) {

        Gson gson = new Gson();
        String cardNumber = cmd.getCardNumber();

        HolderDetails holderdetails = new HolderDetails();
        CardInfoResponseV2DTO cardInfoResponseV2 = getCardService.getCard(cardNumber);

        Boolean isHotlisted = Boolean.FALSE;
        if (cardInfoResponseV2 != null) {
            holderdetails = cardInfoResponseV2.getHolderDetails();
            isHotlisted = (cardInfoResponseV2.getHotListReasons() != null && cardInfoResponseV2.getHotListReasons().getHotListReasonCodes() != null
                            && cardInfoResponseV2.getHotListReasons().getHotListReasonCodes().size() > 0 ? Boolean.TRUE : Boolean.FALSE);
        }

        PersonalDetailsCmdImpl personalDetailsByCustomerId = addUnattachedCardService.retrieveOysterDetailsByCustomerID(Long.valueOf(cmd.getCustomerId()));
        JsonResponse  result = addUnattachedCardService.compareCubicDataToOyster(holderdetails, isHotlisted, personalDetailsByCustomerId);

        return gson.toJson(result);

    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_ATTACH_CARD, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView attachCard(AddUnattachedCardCmdImpl cmd, BindingResult result, HttpServletRequest request, HttpSession session) {
    	CustomerDTO customer = customerDataService.findById(Long.valueOf(cmd.getCustomerId()));
        if (null == customer) {
        	customer = addUnattachedCardService.createNewCustomer(cmd, result);
    	}
        if (result.hasErrors()) {
            cmd.setErrors(result);
            return createNewModelAndView(cmd);
        }

        addUnattachedCardService.saveOysterCardRecord(cmd, customer);

        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
	    if (null != cartSessionData && Page.TRANSFER_PRODUCT.equalsIgnoreCase(cartSessionData.getPageName())) {
	    	CartCmdImpl cartCmd = new CartCmdImpl();
	    	cartCmd.setTargetCardNumber(cmd.getCardNumber());
	    	String sourceCardNumber = cartSessionData.getSourceCardNumber();
	    	cartCmd.setSourceCardNumber(sourceCardNumber);
	        ModelAndView redirectView = new ModelAndView(new RedirectView(PageUrl.TRANSFER_PRODUCT), PageCommand.CART, cartCmd);
	        cartSessionData.setTargetCardNumber(cmd.getCardNumber());
	        redirectView.addObject(ID, customer.getId());
	        redirectView.addObject(CARD_ID, cardOysterOnlineService.findByCardNumber(sourceCardNumber).getId());
	        return redirectView;
	    } else {
	        ModelAndView redirectView = new ModelAndView(new RedirectView(PageUrl.INV_CUSTOMER));
	        redirectView.addObject(ID, cmd.getCustomerId());
	        return redirectView;
        }
    }

    private ModelAndView createNewModelAndView(AddUnattachedCardCmdImpl cardCmd) {
        ModelAndView modelAndView = new ModelAndView(PageView.ADD_UNATTACHED_CARD);
        modelAndView.addObject(PageCommand.ADD_UNATTACHED_CARD, cardCmd);
        modelAndView.addObject("MessageText", warningMessage);

        return modelAndView;
    }
}
