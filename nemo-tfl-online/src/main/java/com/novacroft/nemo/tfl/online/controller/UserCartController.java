package com.novacroft.nemo.tfl.online.controller;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.*;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

import static com.novacroft.nemo.tfl.common.constant.CartAttribute.LINE_NO;
import static com.novacroft.nemo.tfl.common.util.CartUtil.getCartSessionDataDTOFromSession;

/**
 * controller for user basket
 */
@Controller
@RequestMapping(value = PageUrl.USER_CART)
public class UserCartController extends OnlineBaseController {
    static final Logger logger = LoggerFactory.getLogger(UserCartController.class);

    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected CardService cardService;
    @Autowired
    protected CartAdministrationService cartAdminService;

    @ModelAttribute
    public void populateBasketTicketTypesSelectList(Model model) {
        model.addAttribute(PageAttribute.CART_SHIPPING_METHODS,
                selectListService.getSelectList(PageSelectList.CART_SHIPPING_METHODS));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewUserCart(HttpSession session, Model model, RedirectAttributes redirectAttributes) {

        CartSessionData cartSessionData = getCartSessionDataDTOFromSession(session);
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        model.addAttribute(PageAttribute.CART_DTO, cartDTO);

        cartSessionData.setAutoTopUpVisible(cardService.getAutoTopUpVisibleOptionForCard(cartDTO.getCardId()));

        CartCmdImpl cmd = new CartCmdImpl();
        cmd.setCardId(cartDTO.getCardId());
        cmd.setCardNumber(cardService.getCardDTOById(cartDTO.getCardId()).getCardNumber());
        cmd.setAutoTopUpVisible(cartSessionData.getAutoTopUpVisible());
        clearFlashStatusMessage(redirectAttributes);
        return new ModelAndView(PageView.USER_CART, PageCommand.CART, cmd);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_EMPTY_BASKET, method = RequestMethod.POST)
    public ModelAndView emptyUserCart(HttpSession session) {
        CartSessionData cartSessionData = getCartSessionDataDTOFromSession(session);
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        ModelAndView redirectView = new ModelAndView(new RedirectView(PageUrl.TOP_UP_TICKET));
        cartService.emptyCart(cartDTO);
        return redirectView;
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_ADD_MORE_ITEMS, method = RequestMethod.POST)
    public ModelAndView addMoreItems(HttpSession session) {
        return new ModelAndView(new RedirectView(PageUrl.TOP_UP_TICKET));
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CONTINUE, method = RequestMethod.POST)
    public ModelAndView continueUserCart() {
        return new ModelAndView(new RedirectView(PageUrl.COLLECT_PURCHASE));
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_DELETE, method = RequestMethod.POST)
    public ModelAndView deleteShoppingItem(HttpSession session, Model model, @RequestParam(value = LINE_NO) int itemId) {
        CartSessionData cartSessionData = getCartSessionDataDTOFromSession(session);
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        cartDTO = cartService.deleteItem(cartDTO, Long.valueOf(itemId));
        cartDTO = cartAdminService.removeRefundableDepositAndShippingCost(cartDTO);
        model.addAttribute(PageAttribute.CART_DTO, cartDTO);
        return new ModelAndView(new RedirectView(PageUrl.USER_CART));
    }

}
