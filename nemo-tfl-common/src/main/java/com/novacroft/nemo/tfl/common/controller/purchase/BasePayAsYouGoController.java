package com.novacroft.nemo.tfl.common.controller.purchase;

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

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.PayAsYouGoService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.TicketType;
import com.novacroft.nemo.tfl.common.form_validator.PayAsYouGoValidator;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * controller for pay as you go shopping basket
 */
@Controller
@RequestMapping(value = Page.PAY_AS_YOU_GO)
public class BasePayAsYouGoController extends BasePurchaseController {
    static final Logger logger = LoggerFactory.getLogger(BasePayAsYouGoController.class);

    @Autowired
    protected PayAsYouGoValidator payAsYouGoValidator;
    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected PayAsYouGoService payAsYouGoService;
    @Autowired
    protected CartAdministrationService cartAdminService;

    @ModelAttribute(PageCommand.CART)
    public CartCmdImpl getCartCmd() {
        return new CartCmdImpl();
    }

    @ModelAttribute
    public void populatePayAsYouGoCreditBalancesSelectList(Model model) {
        model.addAttribute(PageAttribute.PAY_AS_YOU_GO_CREDIT_BALANCES,
                selectListService.getSelectList(PageSelectList.PAY_AS_YOU_GO_CREDIT_BALANCES));
    }

    @ModelAttribute
    public void populatePayAsYouGoAutoTopUpAmtsSelectList(Model model) {
        model.addAttribute(PageAttribute.PAY_AS_YOU_GO_AUTO_TOPUP_AMOUNTS,
                selectListService.getSelectList(PageSelectList.PAY_AS_YOU_GO_AUTO_TOPUP_AMOUNTS));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewPayAsYouGo() {
        return new ModelAndView(PageView.PAY_AS_YOU_GO, PageCommand.CART_ITEM, new CartItemCmdImpl());
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_ADD_TO_CART, method = RequestMethod.POST)
    public ModelAndView addPayAsYouGoToCart(HttpSession session,
                                            @ModelAttribute(PageCommand.CART_ITEM) CartItemCmdImpl cartItemCmd,
                                            BindingResult result) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        CartDTO cartDTO = loadCartDTO(cartSessionData.getCartId());
        getTicketType(cartDTO, cartSessionData);
        ModelAndView modelAndView = new ModelAndView(PageView.PAY_AS_YOU_GO);
        payAsYouGoValidator.validate(cartItemCmd, result);
        if (result.hasErrors()) {
            return modelAndView;
        }

        payAsYouGoService.addCartItemForNewCard(cartDTO, cartItemCmd);
        return shoppingBasket();

    }

    protected void getTicketType(CartDTO cartDTO, CartSessionData cartSessionData) {
        boolean payAsYouItem = false;
        boolean autoTopUpItem = false;
        for (ItemDTO itemDTOObj : cartDTO.getCartItems()) {
            if (itemDTOObj.getClass().equals(PayAsYouGoItemDTO.class)) {
                payAsYouItem = true;
            }
            if (itemDTOObj.getClass().equals(AutoTopUpConfigurationItemDTO.class)) {
                autoTopUpItem = true;
            }
        }
        if (payAsYouItem && autoTopUpItem) {
            cartSessionData.setTicketType(TicketType.PAY_AS_YOU_GO_AUTO_TOP_UP.code());
        }
    }

}