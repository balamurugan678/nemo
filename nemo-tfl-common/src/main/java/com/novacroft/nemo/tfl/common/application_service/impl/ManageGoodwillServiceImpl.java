package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.tfl.common.constant.PageAttribute.GOODWILL_REFUND_EXTRA_VALIDATION_MESSAGES;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.GOODWILL_REFUND_TYPES;
import static com.novacroft.nemo.tfl.common.constant.TicketType.GOODWILL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.GoodwillService;
import com.novacroft.nemo.tfl.common.application_service.ManageGoodwillService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.GoodwillReasonType;
import com.novacroft.nemo.tfl.common.form_validator.GoodwillValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;

@Service("manageGoodwillService")
public class ManageGoodwillServiceImpl implements ManageGoodwillService {
    static final Logger logger = LoggerFactory.getLogger(ManageGoodwillServiceImpl.class);
    
    @Autowired
    protected CartService cartService;
    @Autowired
    protected GoodwillValidator goodwillValidator;
    @Autowired
    protected GoodwillService goodwillService;

    @Override
    public CartCmdImpl addGoodwill(CartCmdImpl cartCmdImpl, BindingResult result, String refundCartType, Model model) {
    	populateGoodwillRefundTypes(model, refundCartType);
        cartCmdImpl.setCartType(refundCartType);
        goodwillValidator.validate(cartCmdImpl, result);

        if (!result.hasErrors()) {
            CartItemCmdImpl cartItemCmd = new CartItemCmdImpl(cartCmdImpl.getCartItemCmd().getGoodwillPrice(),
                    cartCmdImpl.getCartItemCmd().getGoodwillPaymentId(),
                    GOODWILL.code(),
                    cartCmdImpl.getCardId(), cartCmdImpl.getCartItemCmd().getGoodwillOtherText());
            
            CartDTO cartDTO = addGoodwillItemToCart(refundCartType, cartCmdImpl, cartItemCmd);
            
            cartDTO = cartService.findById(cartCmdImpl.getCartId());
            cartCmdImpl.setCartDTO(cartDTO);
            return cartCmdImpl;
        } else {
            return cartCmdImpl;
        }
    }
    
    protected CartDTO addGoodwillItemToCart(String refundCartType, CartCmdImpl cartCmdImpl, CartItemCmdImpl cartItemCmd) {
    	CartDTO cartDTO = cartService.findById(cartCmdImpl.getCartId());
        return cartService.addItem(cartDTO, cartItemCmd, GoodwillPaymentItemDTO.class);
    }
    
    public CartCmdImpl deleteGoodwill(CartCmdImpl cartCmdImpl, long cartItemId, String refundCartType, Model model) {
    	cartCmdImpl.setCartType(refundCartType);
        populateGoodwillRefundTypes(model, refundCartType);
        return deleteAnonymousGoodwillRefund(cartCmdImpl, cartItemId, refundCartType);
    }
    
    protected CartCmdImpl deleteAnonymousGoodwillRefund(CartCmdImpl cartCmdImpl, long cartItemId, String refundCartType) {
    	CartDTO cartDTO = cartService.deleteItem(cartService.findById(cartCmdImpl.getCartId()), cartItemId);
    	cartCmdImpl.setCartDTO(cartDTO);
    	return cartCmdImpl;
    }

    protected void populateGoodwillRefundTypes(Model model, String cartType) {
        String goodwillReasonType = getGoodwillReasonTypeFromCartType(cartType);
        model.addAttribute(GOODWILL_REFUND_TYPES, goodwillService.getGoodwillRefundTypes(goodwillReasonType));
        model.addAttribute(GOODWILL_REFUND_EXTRA_VALIDATION_MESSAGES, goodwillService.getGoodwillRefundExtraValidationMessages(goodwillReasonType));
    }

    protected String getGoodwillReasonTypeFromCartType(String cartType) {
        return isStandaloneGoodwillRefundCartType(cartType) ? GoodwillReasonType.CONTACTLESS_PAYMENT_CARD.code() : GoodwillReasonType.OYSTER
                .code();
    }
    
    protected boolean isStandaloneGoodwillRefundCartType(String cartType) {
        return cartType.equalsIgnoreCase(CartType.STANDALONE_GOODWILL_REFUND.code());
    }
}
