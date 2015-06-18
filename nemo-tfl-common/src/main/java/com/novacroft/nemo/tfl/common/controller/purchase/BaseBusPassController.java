package com.novacroft.nemo.tfl.common.controller.purchase;

import static com.novacroft.nemo.tfl.common.constant.TicketType.BUS_PASS;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.tfl.common.application_service.BusPassService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.BusPassValidator;
import com.novacroft.nemo.tfl.common.form_validator.CartValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * controller for bus pass shopping basket
 */
@Controller
@RequestMapping(value = Page.BUS_PASS)
public class BaseBusPassController extends BasePurchaseController {
    static final Logger logger = LoggerFactory.getLogger(BaseBusPassController.class);

    @Autowired
    protected BusPassValidator busPassValidator;
    @Autowired
    protected BusPassService busPassService;
    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected CartValidator cartValidator;
    @Autowired
    protected CartAdministrationService cartAdminService;

    @ModelAttribute
    public void populateStartDatesSelectList(Model model) {
        model.addAttribute(PageAttribute.START_DATES, cartAdminService.getProductStartDateList());
    }

    @ModelAttribute
    public void populateEmailRemindersSelectList(Model model) {
        model.addAttribute(PageAttribute.BASKET_EMAIL_REMINDERS,
                selectListService.getSelectList(PageSelectList.BASKET_EMAIL_REMINDERS));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewBusPass() {
        return new ModelAndView(PageView.BUS_PASS, PageCommand.CART_ITEM, new CartItemCmdImpl());
    }

    

    @Override
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel() {
        return new ModelAndView(new RedirectView(PageUrl.BASKET));
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_SHOPPING_BASKET, method = RequestMethod.POST)
    public ModelAndView shoppingBasket() {
        return new ModelAndView(new RedirectView(PageUrl.CART));
    }

    
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_ADD_TO_CART, method = RequestMethod.POST)
    public ModelAndView addBusPassToCartModelAndView(HttpSession session, @ModelAttribute(PageCommand.CART_ITEM) CartItemCmdImpl cartItemCmd,
                                                   BindingResult result) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);         
        CartDTO cartDTO = loadCartDTO(cartSessionData.getCartId());
        cartItemCmd.setCartId(cartDTO.getId());
        cartItemCmd.setTicketType(BUS_PASS.code());
        cartItemCmd.setTravelCardType(Durations.ANNUAL.getDurationType());

        busPassValidator.validate(cartItemCmd, result);
        cartValidator.validate(cartItemCmd, result);
        
        if (!result.hasErrors()) {
            cartDTO = busPassService.addCartItemForNewCard(cartDTO, cartItemCmd);
            return new ModelAndView(new RedirectView(PageUrl.CART));
        }else{
            return new ModelAndView(PageView.BUS_PASS);
        }
    }
    

    
}
