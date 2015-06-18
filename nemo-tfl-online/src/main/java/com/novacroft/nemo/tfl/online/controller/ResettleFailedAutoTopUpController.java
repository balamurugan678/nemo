package com.novacroft.nemo.tfl.online.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.novacroft.nemo.common.utils.CurrencyUtil;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.command.impl.CardCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.AutoTopUpOrderConstant;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameter;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.FailedAutoTopUpCaseDataService;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpResettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Controller for adding an existing card to an account
 */
@Controller
@RequestMapping(PageUrl.RESETTLE_FAILED_AUTO_TOP_UP)
public class ResettleFailedAutoTopUpController extends OnlineBaseController {
    protected static final Logger logger = LoggerFactory.getLogger(AddExistingCardToAccountController.class);

    @Autowired
    protected FailedAutoTopUpCaseDataService failedAutoTopUpCaseDataService;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showPage() {
    	String username = getLoggedInUsername();
    	CustomerDTO customer = customerDataService.findByUsernameOrEmail(username);
    	int amount = failedAutoTopUpCaseDataService.findPendingAmountByCustomerId(customer.getId());
        ModelAndView modelAndView = new ModelAndView(PageView.RESETTLE_FAILED_AUTO_TOP_UP);
        modelAndView.addObject(PageParameter.AMOUNT, amount);
        return modelAndView;
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_RESETTLE, method = RequestMethod.POST)
    public ModelAndView redirectToCheckout(HttpSession session, @ModelAttribute(PageCommand.CARD) CardCmdImpl cmd, BindingResult result,
                                    final RedirectAttributes redirectAttributes) {
    	CartDTO cartDTO = cartService.createCartFromCardId(5001L);
        cartDTO = cartService.removeExpiredCartItems(cartDTO);
        CartSessionData cartSessionData = new CartSessionData(cartDTO.getId());
        CartUtil.addCartSessionDataDTOToSession(session, cartSessionData);
        ItemDTO item = null;
        List<ItemDTO> list = cartDTO.getCartItems();
        for (int i=0; i<list.size(); i++) {
        	if (AutoTopUpOrderConstant.RESETTLEMENT.equals(list.get(i).getName())) {
        		item = list.get(i);
        	}
        }
        if (null == item) {
        	item = new FailedAutoTopUpResettlementDTO();
        	String username = getLoggedInUsername();
        	CustomerDTO customer = customerDataService.findByUsernameOrEmail(username);
        	int amount = CurrencyUtil.convertPoundsToPence(new Float(failedAutoTopUpCaseDataService.findPendingAmountByCustomerId(customer.getId())));
        	item.setPrice(amount);
            cartDTO.addCartItem(item);
            CartItemCmdImpl cartItemCmdImpl = new CartItemCmdImpl();
            cartItemCmdImpl.setPrice(item.getPrice());
            cartItemCmdImpl.setFailedAutoTopUpCaseId(cartDTO.getId());
            cartService.addUpdateItem(cartDTO, cartItemCmdImpl, FailedAutoTopUpResettlementDTO.class);
        }
    	return new ModelAndView(getRedirectViewWithoutExposedAttributes(PageUrl.CHECKOUT));
    }
}