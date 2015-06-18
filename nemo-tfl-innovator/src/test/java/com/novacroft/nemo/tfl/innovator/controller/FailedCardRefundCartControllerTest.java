package com.novacroft.nemo.tfl.innovator.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.constant.CartType;

public class FailedCardRefundCartControllerTest {
    
    private FailedCardRefundCartController controller;
    
    @Before
    public void setUp() throws Exception {
        controller = new FailedCardRefundCartController();
    }

    @Test
    public void getRefundTypeShouldReturnFailedCardRefundView() {
        assertEquals("FailedCardRefundView", controller.getRefundType());
    }
    
    @Test
    public void getCartTypeCodeShouldReturnFailedType() {
        assertEquals(CartType.FAILED_CARD_REFUND.code(), controller.getCartTypeCode());
    }
}
