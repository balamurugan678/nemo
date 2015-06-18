package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.PageCommand.CART;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_FAILED_CARD_REFUND;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.Page;

@Controller
@RequestMapping(value = Page.INV_FAILED_CARD_REFUND)
@SessionAttributes(CART)
public class FailedCardRefundCartController extends DestroyedOrFailedCardRefundController {

	@Override
	protected String getRefundType() {
		return INV_FAILED_CARD_REFUND;
	}
    
    @Override
    protected String getCartTypeCode() {
    	return CartType.FAILED_CARD_REFUND.code();
    }
}
