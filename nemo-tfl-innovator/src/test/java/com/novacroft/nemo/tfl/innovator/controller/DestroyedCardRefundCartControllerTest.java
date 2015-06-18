package com.novacroft.nemo.tfl.innovator.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.constant.CartType;

public class DestroyedCardRefundCartControllerTest {
    
    private DestroyedCardRefundCartController controller;
    
    @Before
    public void setUp() throws Exception {
        controller = new DestroyedCardRefundCartController();
    }

    @Test
    public void getRefundTypeShouldReturnDestroyedCardRefundView() {
        assertEquals("DestroyedCardRefundView", controller.getRefundType());
    }
    
    @Test
    public void getCartTypeCodeShouldReturnDestroyedType() {
        assertEquals(CartType.DESTROYED_CARD_REFUND.code(), controller.getCartTypeCode());
    }
}
