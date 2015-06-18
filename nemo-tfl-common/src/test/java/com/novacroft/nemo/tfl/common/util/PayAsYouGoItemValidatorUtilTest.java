package com.novacroft.nemo.tfl.common.util;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;

import static com.novacroft.nemo.test_support.CartCmdTestUtil.getTestCartCmd11;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.getTestCartCmdItemWithDefaultItems;
import static com.novacroft.nemo.test_support.CartItemTestUtil.PAY_AS_YOU_GO_TICKET_PRICE_DEFAULT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PayAsYouGoItemValidatorUtilTest {
    private static final Integer INTEGER_MINUS_ONE = -1;
    private static final String EXPECTED_PAYG_FIELD = "cartDTO.cartItems[0].price";
    
    private CartCmdImpl testPaygCartCmd;
    private CartCmdImpl testOtherCartCmd;
    
    @Before
    public void setUp() {
        testPaygCartCmd = getTestCartCmdItemWithDefaultItems();
        testOtherCartCmd = getTestCartCmd11();
    }
    
    @Test
    public void getPayAsYouGoItemPriceShouldReturnPrice() {
        assertEquals(PAY_AS_YOU_GO_TICKET_PRICE_DEFAULT, 
                        PayAsYouGoItemValidatorUtil.getPayAsYouGoItemPrice(testPaygCartCmd));
    }
    
    @Test
    public void getPayAsYouGoItemPriceShouldReturnMinusOne() {
        assertEquals(INTEGER_MINUS_ONE, 
                        PayAsYouGoItemValidatorUtil.getPayAsYouGoItemPrice(testOtherCartCmd));
    }
    
    @Test
    public void getPayAsYouGoItemFieldShouldReturnField() {
        assertEquals(EXPECTED_PAYG_FIELD, 
                        PayAsYouGoItemValidatorUtil.getPayAsYouGoItemField(testPaygCartCmd));
    }
    
    @Test
    public void getPayAsYouGoItemFieldShouldReturnEmpty() {
        assertEquals(StringUtil.EMPTY_STRING, 
                        PayAsYouGoItemValidatorUtil.getPayAsYouGoItemField(testOtherCartCmd));
    }
    
    @Test
    public void payAsYouGoItemFieldAvailableShouldReturnTrue() {
        assertTrue(PayAsYouGoItemValidatorUtil.payAsYouGoItemFieldAvailable(testPaygCartCmd));
    }
    
    @Test
    public void payAsYouGoItemFieldAvailableShouldReturnFalse() {
        assertFalse(PayAsYouGoItemValidatorUtil.payAsYouGoItemFieldAvailable(testOtherCartCmd));
    }
    
    @Test
    public void cartItemListNotEmptyShouldReturnTrue() {
        assertTrue(PayAsYouGoItemValidatorUtil.cartItemListNotEmpty(new CartCmdImpl()));
    }
    
    @Test
    public void cartItemListNotEmptyShouldReturnFalse() {
        CartCmdImpl cartCmd = new CartCmdImpl();
        cartCmd.setCartItemList(null);
        assertFalse(PayAsYouGoItemValidatorUtil.cartItemListNotEmpty(cartCmd));
    }
    
    @Test
    public void cartDTONotEmptyShouldReturnTrue() {
        CartCmdImpl cartCmd = new CartCmdImpl();
        cartCmd.setCartDTO(new CartDTO());
        assertTrue(PayAsYouGoItemValidatorUtil.cartDTONotEmpty(cartCmd));
    }
    
    @Test
    public void cartDTONotEmptyShouldReturnFalse() {
        CartCmdImpl cartCmd = new CartCmdImpl();
        cartCmd.setCartDTO(null);
        assertFalse(PayAsYouGoItemValidatorUtil.cartDTONotEmpty(cartCmd));
    }
}
