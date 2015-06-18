package com.novacroft.nemo.tfl.online.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * controller for order Oyster card
 *
 * @deprecated will be obsoleted by SSO
 */
@Controller
@RequestMapping(value = PageUrl.ORDER_OYSTER_CARD)
@Deprecated
public class OrderOysterCardController extends OnlineBaseController {
    static final Logger logger = LoggerFactory.getLogger(OrderOysterCardController.class);

    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected CustomerService customerService;

    @ModelAttribute(PageCommand.CART)
    public CartCmdImpl getCartCmd() {
        return new CartCmdImpl();
    }

    @ModelAttribute
    public void populateBasketTicketTypesSelectList(Model model) {
        model.addAttribute(PageAttribute.BASKET_TICKET_TYPES,
                selectListService.getSelectList(PageSelectList.BASKET_TICKET_TYPES));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewBasket(HttpSession session, Model model) {
        CartDTO cartDTO = null;
        if (this.securityService.isLoggedIn()) {
            cartDTO = cartService.createCartFromCustomerId(getLoggedInUserCustomerId());
        } else {
            cartDTO = cartService.createCart();
        }
        CartSessionData cartSessionData = new CartSessionData(cartDTO.getId());
        cartSessionData.setAutoTopUpVisible(Boolean.TRUE);
        CartUtil.addCartSessionDataDTOToSession(session, cartSessionData);
        model.addAttribute(PageAttribute.CART_DTO);
        return new ModelAndView(PageView.BASKET);
    }

    @Override
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel() {
        return new ModelAndView(new RedirectView(PageUrl.OYSTER_HOME));
    }

}
