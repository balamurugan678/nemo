package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.CartItemTestUtil.getSevenDayTradedTicket;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestTravelCard10;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
public class TradedTicketValidatorTest {

    protected  TradedTicketValidator validator;
    private Errors errors;
    private CartCmdImpl cmd;
    private CartItemCmdImpl cartItemCmd;
    
    private static final String ANNUAL_BUS_PASS = "Annual Bus Pass";
    
    @Before
    public void setUp() {
        validator = new TradedTicketValidator();
        this.cmd = new CartCmdImpl();
        this.errors = new BeanPropertyBindingResult(cmd, "cmd");
        cartItemCmd = getTestTravelCard10();
        List<CartItemCmdImpl> cartItemList = new ArrayList<CartItemCmdImpl>();
        cartItemCmd.setPreviouslyExchanged(true);
        cartItemList.add(cartItemCmd);
        cmd.setCartItemList(cartItemList);
    }
    @Test
    public void shouldValidateWithPreviouslyExchangedFalse() {
        
        cartItemCmd.setPreviouslyExchanged(false);
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        validator.validate(cmd, errors);
        
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldValidatePreviouslyExchanged() {
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        validator.validate(cmd, errors);
        
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidatePreviouslyExchangedTicketWithoutExchangedDate() {
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        tradedCartItemCmd.setExchangedDate(null);
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        validator.validate(cmd, errors);
        
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidatePreviouslyExchangedTicketWithoutTravelcardType() {
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        tradedCartItemCmd.setTravelCardType(null);
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        validator.validate(cmd, errors);
        
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidatePreviouslyExchangedTicketWithoutDiscountType() {
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        tradedCartItemCmd.setDiscountType(null);
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        validator.validate(cmd, errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidatePreviouslyExchangedTicketWithoutPassengerType() {
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        tradedCartItemCmd.setPassengerType(null);
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        validator.validate(cmd, errors);
        
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidatePreviouslyExchangedTicketWithoutStartDate() {
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        tradedCartItemCmd.setStartDate(null);
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        validator.validate(cmd, errors);
        
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidatePreviouslyExchangedTicketWithoutStartZone() {
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        tradedCartItemCmd.setStartZone(null);
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        validator.validate(cmd, errors);
        
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidatePreviouslyExchangedTicketWithoutEndZone() {
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        tradedCartItemCmd.setEndZone(null);
        validator.validate(cmd, errors);
        
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldValidateIgnoreMandatoryValidationForBusPassTravelCardTypeOfPreviouslyExchangedTicket(){
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        tradedCartItemCmd.setTravelCardType(ANNUAL_BUS_PASS);
        tradedCartItemCmd.setStartZone(null);
        tradedCartItemCmd.setEndZone(null);
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        validator.validate(cmd, errors);
        
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateEndDateForOtherTravelCardTypePrevioslyExchangedTicket(){
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        tradedCartItemCmd.setTravelCardType(CartAttribute.OTHER_TRAVEL_CARD);
        tradedCartItemCmd.setEndDate(null);
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        validator.validate(cmd, errors);
        
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldRejectIfPreviouslyExchangedTicketSameAsOriginalTicket(){
        CartItemCmdImpl cartItemCmd = getTestTravelCard10();
        cartItemCmd.setPreviouslyExchanged(true);
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        tradedCartItemCmd.setStartDate(cartItemCmd.getStartDate());
        tradedCartItemCmd.setEndDate(cartItemCmd.getEndDate());
        tradedCartItemCmd.setStartZone(cartItemCmd.getStartZone());
        tradedCartItemCmd.setEndZone(cartItemCmd.getEndZone());
        tradedCartItemCmd.setPrice(cartItemCmd.getPrice());
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        List<CartItemCmdImpl> cartItems = new ArrayList<CartItemCmdImpl>();
        cartItems.add(cartItemCmd);
        cmd.setCartItemList(cartItems);
        validator.validate(cmd, errors);
        
        assertTrue(errors.hasErrors());
    }
}
