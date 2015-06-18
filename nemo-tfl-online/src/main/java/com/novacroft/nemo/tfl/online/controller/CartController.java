package com.novacroft.nemo.tfl.online.controller;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.PayAsYouGoService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.*;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodItemDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;
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

/**
 * controller for shopping basket
 */
@Controller
@RequestMapping(value = PageUrl.CART)
public class CartController extends OnlineBaseController {
    static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    protected CartService cartService;
    @Autowired
    protected CartAdministrationService cartAdminService;
    @Autowired
    protected CustomerService customerService;
    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected PayAsYouGoService payAsYouGoService;

    @ModelAttribute(PageCommand.CART)
    public CartCmdImpl getCartCmd() {
        return new CartCmdImpl();
    }

    @ModelAttribute
    public void populateBasketTicketTypesSelectList(Model model) {
        model.addAttribute(PageAttribute.CART_SHIPPING_METHODS,
                selectListService.getSelectList(PageSelectList.CART_SHIPPING_METHODS));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewCart(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd,
                                 RedirectAttributes redirectAttributes, Model model) {
        clearFlashStatusMessage(redirectAttributes);
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        if (null != cartSessionData) {
            CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
            model.addAttribute(PageAttribute.CART_DTO, cartDTO);
            ShippingMethodItemDTO shippingItem = cartDTO.getShippingMethodItem();
            if (null != shippingItem) {
                cmd.setShippingType(shippingItem.getName());
            }
			model.addAttribute(PageAttribute.IS_CART_CONTAINS_BUS_PASS_AND_TRAVEL_CARD,
                            CartUtil.isCartContainsBusPassAndTravelCard(cartDTO.getCartItems()));
        }
        return new ModelAndView(PageView.CART, PageCommand.CART, cmd);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_EMPTY_BASKET, method = RequestMethod.POST)
    public ModelAndView emptyCart(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        if (null != cartDTO) {
            cartDTO = cartService.emptyCart(cartDTO);
            cartSessionData.setCartTotal(0);
            cartSessionData.setToPayAmount(0);
            cartSessionData.setOrderId(null);
            cartSessionData.setPaymentCardSettlementId(null);
            cartSessionData.setWebCreditApplyAmount(Integer.valueOf(0));
            cartSessionData.setWebCreditAvailableAmount(Integer.valueOf(0));
        }
        return new ModelAndView(new RedirectView(PageUrl.BASKET));
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_ADD_MORE_ITEMS, method = RequestMethod.POST)
    public ModelAndView addMoreItems() {
        return new ModelAndView(new RedirectView(PageUrl.BASKET));
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CONTINUE, method = RequestMethod.POST)
    public ModelAndView continueCart(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        cartSessionData.setPageName(Page.CART);
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        applyShippingCostToCart(cartDTO, cmd.getShippingType());
        return this.securityService.isLoggedIn() ? new ModelAndView(getRedirectViewWithoutExposedAttributes(PageUrl.SECURITY_QUESTION)) :
                new ModelAndView(getRedirectViewWithoutExposedAttributes(PageUrl.ACCOUNT_DETAILS));
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_UPDATE_TOTAL, method = RequestMethod.POST)
    public ModelAndView updateTotalCost(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, Model model) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        cartDTO = applyShippingCostToCart(cartDTO, cmd.getShippingType());
        model.addAttribute(PageAttribute.CART_DTO, cartDTO);
        return new ModelAndView(PageView.CART);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_DELETE, method = RequestMethod.POST)
    public ModelAndView deleteShoppingItem(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd,
                                           @RequestParam(value = LINE_NO) int itemId, Model model) {
        ModelAndView modelAndView = new ModelAndView(PageView.CART);
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        cartService.deleteItem(cartDTO, Long.valueOf(itemId));
        cartDTO = cartAdminService.removeRefundableDepositAndShippingCost(cartDTO);
        cartDTO = payAsYouGoService.removeNonApplicableAutoTopUpCartItem(cartDTO);
        model.addAttribute(PageAttribute.CART_DTO, cartDTO);
        ShippingMethodItemDTO shippingItem = cartDTO.getShippingMethodItem();
        if (null != shippingItem) {
            cmd.setShippingType(shippingItem.getName());
            modelAndView.addObject(PageCommand.CART, cmd);
        }
        return modelAndView;
    }

    protected CartDTO applyShippingCostToCart(CartDTO cartDTO, String shippingType) {
        if (!StringUtil.isBlank(shippingType)) {
            CartItemCmdImpl cartItemCmd = new CartItemCmdImpl();
            cartItemCmd.setShippingMethodType(shippingType);
            cartDTO = cartAdminService.applyShippingCost(cartDTO, cartItemCmd);
        }
        return cartDTO;
    }
}
