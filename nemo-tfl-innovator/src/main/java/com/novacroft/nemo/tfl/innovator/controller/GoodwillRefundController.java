package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.CartAttribute.LINE_NO;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.GOODWILL_REFUND_EXTRA_VALIDATION_MESSAGES;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.GOODWILL_REFUND_TYPES;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.REFUND_CART_TYPE;
import static com.novacroft.nemo.tfl.common.constant.PageCommand.CART;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_ADD_GOODWILL;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_DELETE_GOODWILL;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_GOODWILL_REFUND;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_STANDALONE_GOODWILL;
import static com.novacroft.nemo.tfl.common.constant.TicketType.GOODWILL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.tfl.common.application_service.GoodwillPaymentService;
import com.novacroft.nemo.tfl.common.application_service.GoodwillService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.GoodwillReasonType;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.controller.BaseController;
import com.novacroft.nemo.tfl.common.form_validator.GoodwillValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;

@Controller
@RequestMapping(value = Page.INV_GOODWILL_REFUND)
@SessionAttributes(CART)
@Deprecated
public class GoodwillRefundController extends BaseController {
    static final Logger logger = LoggerFactory.getLogger(GoodwillRefundController.class);

    @Autowired
    protected GoodwillValidator goodwillValidator;
    @Autowired
    protected GoodwillPaymentService goodwillPaymentService;
    @Autowired
    protected GoodwillService goodwillService;
    @Autowired
    protected CartService cartService;
    

    public void populateGoodwillRefundTypes(Model model, String cartType) {
        String goodwillReasonType = getGoodwillReasonTypeFromCartType(cartType);
        model.addAttribute(GOODWILL_REFUND_TYPES, goodwillService.getGoodwillRefundTypes(goodwillReasonType));
        model.addAttribute(GOODWILL_REFUND_EXTRA_VALIDATION_MESSAGES, goodwillService.getGoodwillRefundExtraValidationMessages(goodwillReasonType));
    }

    protected String getGoodwillReasonTypeFromCartType(String cartType) {
        return isStandaloneGoodwillRefundCartType(cartType) ? GoodwillReasonType.CONTACTLESS_PAYMENT_CARD.code() : GoodwillReasonType.OYSTER
                .code();
    }

    @RequestMapping(params = TARGET_ACTION_DELETE_GOODWILL, method = RequestMethod.POST)
    public ModelAndView deleteGoodwill(@ModelAttribute(CART) CartCmdImpl cmd,
                                       @RequestParam(value = LINE_NO) int lineNo,
                                       @RequestParam(value = REFUND_CART_TYPE) String refundCartType, Model model) {
        populateGoodwillRefundTypes(model, refundCartType);
        return deleteGoodwillRefund(cmd, lineNo, refundCartType);
    }

    @RequestMapping(params = TARGET_ACTION_ADD_GOODWILL, method = RequestMethod.POST)
    public ModelAndView addGoodwill(@ModelAttribute(PageCommand.CART) CartCmdImpl cartCmdImpl, BindingResult result, @RequestParam(value = REFUND_CART_TYPE) String refundCartType, Model model) {
        populateGoodwillRefundTypes(model, refundCartType);
        cartCmdImpl.setCartType(refundCartType);
        goodwillValidator.validate(cartCmdImpl, result);

        if (!result.hasErrors()) {
            cartCmdImpl.setTicketType(GOODWILL.code());

            CartItemCmdImpl cartItemCmd = new CartItemCmdImpl(cartCmdImpl.getCartItemCmd().getGoodwillPrice(),
                    cartCmdImpl.getCartItemCmd().getGoodwillPaymentId(),
                    GOODWILL.code(),
                    cartCmdImpl.getCardId(), cartCmdImpl.getCartItemCmd().getGoodwillOtherText());

            CartDTO cartDTO = addGoodwillItemToCart(refundCartType, cartCmdImpl, cartItemCmd);
            cartDTO = cartService.findById(cartCmdImpl.getCartDTO().getId());
            cartCmdImpl.setCartDTO(cartDTO);

            return getRefreshedCartAsModelAndView(cartCmdImpl.getCustomerId(), cartCmdImpl.getCardId(), refundCartType);
        } else {
            return getCartAsModelAndView(cartCmdImpl, refundCartType);
        }
    }
    
    protected CartDTO addGoodwillItemToCart(String refundCartType, CartCmdImpl cartCmdImpl, CartItemCmdImpl cartItemCmd) {
    	CartDTO cartDTO = getCartDTOBasedOnRefundCartType(refundCartType, cartCmdImpl);
        return cartService.addItem(cartDTO, cartItemCmd, GoodwillPaymentItemDTO.class);
    }
    
    protected CartDTO getCartDTOBasedOnRefundCartType(String refundCartType, CartCmdImpl cartCmdImpl) {
    	CartDTO cartDTO = null;
        if (isStandaloneGoodwillRefundCartType(refundCartType)) {
            return cartDTO = cartService.findById(cartCmdImpl.getCartDTO().getId());
        } else {
            return cartDTO = cartService.findNotInWorkFlowFlightCartByCustomerId(cartCmdImpl.getCartDTO().getCustomerId());
        }
    }

    protected ModelAndView deleteGoodwillRefund(CartCmdImpl cmd, int lineNo, String refundCartType) {
        cmd.setCartType(refundCartType);
        //cartService.deleteCartItemForExistingOysterCard(cmd, lineNo);
        return getRefreshedCartAsModelAndView(cmd.getCustomerId(), cmd.getCardId(), refundCartType);
    }

    protected ModelAndView getRefreshedCartAsModelAndView(Long customerId, Long cartId, String cartType) {
//        return isStandaloneGoodwillRefundCartType(cartType) ?
//                new ModelAndView(INV_STANDALONE_GOODWILL, CART,
//                    goodwillPaymentService.getCartCmdFromCustomerId(customerId)) :
//                new ModelAndView(INV_GOODWILL_REFUND, CART, refundService.getCartCmdFromCardId(cartId, cartType));
        return null;
    }

    protected boolean isStandaloneGoodwillRefundCartType(String cartType) {
        return cartType.equalsIgnoreCase(CartType.STANDALONE_GOODWILL_REFUND.code());
    }

    protected ModelAndView getCartAsModelAndView(CartCmdImpl cartCmdImpl, String cartType) {
        if (isStandaloneGoodwillRefundCartType(cartType)) {
            return new ModelAndView(INV_STANDALONE_GOODWILL, CART, cartCmdImpl);
        } else {
            return new ModelAndView(INV_GOODWILL_REFUND, CART, cartCmdImpl);
        }
    }
}
