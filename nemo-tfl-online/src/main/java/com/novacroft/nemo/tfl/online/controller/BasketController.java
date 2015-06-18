package com.novacroft.nemo.tfl.online.controller;

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
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.TicketType;

/**
 * controller for choose basket ticket types
 */
@Controller
@RequestMapping(value = PageUrl.BASKET)
public class BasketController extends OnlineBaseController {
    static final Logger logger = LoggerFactory.getLogger(BasketController.class);

    @Autowired
    protected SelectListService selectListService;

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
    public ModelAndView viewBasket(@ModelAttribute(PageCommand.CART) CartCmdImpl cmd) {
        return new ModelAndView(PageView.BASKET, PageCommand.CART, cmd);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CHOOSE_BASKET, method = RequestMethod.POST)
    public ModelAndView chooseBasket(@ModelAttribute(PageCommand.CART) CartCmdImpl cmd) {
        return new ModelAndView(new RedirectView(resolveRedirectUrlName(cmd.getTicketType())));
    }

    protected String resolveRedirectUrlName(String ticketTypeVal) {
        String redirectUrl = null;
        TicketType ticketType = TicketType.lookUpTicketType(ticketTypeVal);
        switch (ticketType) {
            case PAY_AS_YOU_GO:
                redirectUrl = PageUrl.PAY_AS_YOU_GO;
                break;
            case BUS_PASS:
                redirectUrl = PageUrl.BUS_PASS;
                break;
            case TRAVEL_CARD:
                redirectUrl = PageUrl.TRAVEL_CARD;
                break;
            default:
                break;
        }
        return redirectUrl;
    }

    @Override
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel() {
        return new ModelAndView(new RedirectView(PageUrl.OYSTER_HOME));
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_SHOPPING_BASKET, method = RequestMethod.POST)
    public ModelAndView shoppingBasket() {
        return new ModelAndView(new RedirectView(PageUrl.CART));
    }

}
