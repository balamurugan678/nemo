package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestGoodwillPayment1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.GOODWILL_AMOUNT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;

public class GoodwillPaymentItemDTOConverterImplTest {
    private GoodwillPaymentItemDTOConverterImpl converter;
    
    @Before
    public void setUp() {
        converter = new GoodwillPaymentItemDTOConverterImpl();
    }
    
    @Test
    public void shouldConvertCmdToDto() {
        GoodwillPaymentItemDTO actualResult = 
                        converter.convertCmdToDto(getTestGoodwillPayment1(), new GoodwillPaymentItemDTO());
        assertNotNull(actualResult);
        assertEquals(Integer.valueOf(GOODWILL_AMOUNT), actualResult.getPrice());
    }
}
