package com.novacroft.nemo.tfl.common.controller.purchase;

import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.WebCreditService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.*;
import com.novacroft.nemo.tfl.common.controller.BaseController;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.util.CartUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

public abstract class BasePurchaseController extends BaseController {
    public static final String COMPLETE = "Complete";

    @Autowired
    protected WebCreditService webCreditService;
    @Autowired 
    protected CustomerService customerService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected CartService cartService;
    
   
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_INV_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancelReturnToCart(HttpSession session, Model model, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        model.addAttribute(PageAttribute.CART_DTO, cartDTO);
        addCartDTOToModel(model, cmd);
        model.addAttribute(PageParameter.CUSTOMER_ID, cartDTO.getCustomerId());
        return new ModelAndView(new RedirectView(PageUrl.CART));
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_SHOPPING_BASKET, method = RequestMethod.POST)
    public ModelAndView shoppingBasket() {
        return new ModelAndView(new RedirectView(PageUrl.CART));
    }

    protected CartDTO loadCartDTO(Long cartId) {
        return cartService.findById(cartId);
    }

    protected void addCartDTOToModel(Model model, CartCmdImpl cmd) {
        model.addAttribute(PageCommand.CART, cmd);
    }
    
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel() {
        return new ModelAndView(new RedirectView(PageUrl.DASHBOARD));
    }
    
    protected Long getLoggedInUserCustomerId() {
        return this.securityService.getLoggedInCustomer().getId();
    }
}
