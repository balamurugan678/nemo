package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.CartItemTestUtil.OTHER_TRAVEL_CARD_TYPE_1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getSevenDayTradedTicket;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestAnnualBusPass1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestTravelCard10;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestTravelCard9;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewAnnualBusPassProductItemDTO;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithGoodwillItem;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewProductItemDTO;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.tfl.common.application_service.AddUnlistedProductService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.Refund;
import com.novacroft.nemo.tfl.common.constant.TicketType;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;



public class AddUnlistedProductValidatorTest {
    
    protected  AddUnlistedProductValidator validator;
    private Errors errors;
    private CartCmdImpl cmd;
    
    private static final Integer MAXIMUM_ALLOWED_TRAVEL_CARDS = 3;
    private static final String ANNUAL_BUS_PASS = "Annual Bus Pass";
    
    @Before
    public void setUp() {
        validator = new AddUnlistedProductValidator();
        this.cmd = new CartCmdImpl();
        validator.systemParameterService = mock (SystemParameterService.class);
        validator.zoneMappingRefundValidator = mock(ZoneMappingRefundValidator.class);
        validator.travelCardRefundValidator = mock(TravelCardRefundValidator.class);
        validator.addUnlistedProductService = mock(AddUnlistedProductService.class);
        this.errors = new BeanPropertyBindingResult(cmd, "cmd");
    }
    
    @Test
    public void shouldValidate(){
        CartItemCmdImpl cmdItem =getTestTravelCard9();
        cmdItem.setTicketType(TicketType.TRAVEL_CARD.code());
        cmd.setCartItemCmd(cmdItem);
        when(validator.systemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        cmd.setCartDTO(getNewCartDTOWithGoodwillItem());
        
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldValidate_NotbusPassTicketType(){
        when(validator.systemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        CartItemCmdImpl cmdItem = getTestTravelCard9();
        cmdItem.setTicketType(TicketType.TRAVEL_CARD.code());
        cmdItem.setTravelCardType(Refund.GOODWILL);
        cmd.setCartItemCmd(cmdItem);
        cmd.setCartDTO(getNewCartDTOWithGoodwillItem());
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidatIfMaximumAllowedTravelCardsIsExeceeded(){
        when(validator.systemParameterService.getIntegerParameterValue(anyString())).thenReturn(0);
        CartItemCmdImpl cmdItem = getTestTravelCard9();
        cmdItem.setTicketType(TicketType.TRAVEL_CARD.code());
        cmd.setCartItemCmd(cmdItem);
        cmd.setCartDTO(getNewCartDTOWithGoodwillItem());
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateIfEndDateIsEmpty(){
        CartItemCmdImpl cmdItem =getTestTravelCard9();
        cmdItem.setTicketType(TicketType.TRAVEL_CARD.code());
        cmdItem.setTravelCardType(OTHER_TRAVEL_CARD_TYPE_1);
        cmdItem.setEndDate(null);
        cmd.setCartItemCmd(cmdItem);
        when(validator.systemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        cmd.setCartDTO(getNewCartDTOWithGoodwillItem());
        
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateIfPassengerTypeIsNull() {
        CartItemCmdImpl cmdItem = getTestTravelCard9();
        cmdItem.setTicketType(TicketType.TRAVEL_CARD.code());
        cmdItem.setTravelCardType(OTHER_TRAVEL_CARD_TYPE_1);
        cmdItem.setPassengerType(null);
        cmd.setCartItemCmd(cmdItem);
        when(validator.systemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        cmd.setCartDTO(getNewCartDTOWithGoodwillItem());

        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfDiscountTypeIsNull() {
        CartItemCmdImpl cmdItem = getTestTravelCard9();
        cmdItem.setTicketType(TicketType.TRAVEL_CARD.code());
        cmdItem.setTravelCardType(OTHER_TRAVEL_CARD_TYPE_1);
        cmdItem.setDiscountType(null);
        cmd.setCartItemCmd(cmdItem);
        when(validator.systemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        cmd.setCartDTO(getNewCartDTOWithGoodwillItem());

        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfTravelCardTypeIsNull(){
        CartItemCmdImpl cmdItem =getTestTravelCard9();
        cmdItem.setTicketType(TicketType.TRAVEL_CARD.code());
        cmdItem.setTravelCardType(null);
        cmdItem.setEndDate(null);
        cmd.setCartItemCmd(cmdItem);
        when(validator.systemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        cmd.setCartDTO(getNewCartDTOWithGoodwillItem());
        
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldValidateIfTicketTypeIsNull(){
        CartItemCmdImpl cmdItem =getTestTravelCard9();
        cmdItem.setTicketType(null);
        cmdItem.setTravelCardType(OTHER_TRAVEL_CARD_TYPE_1);
        cmd.setCartItemCmd(cmdItem);
        when(validator.systemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        cmd.setCartDTO(getNewCartDTOWithGoodwillItem());
        
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldValidateIfTicketTypeDoesNotEqualTravelCard(){
        CartItemCmdImpl cmdItem =getTestTravelCard9();
        cmdItem.setTicketType(TicketType.ADMINISTRATION_FEE.code());
        cmdItem.setTravelCardType(OTHER_TRAVEL_CARD_TYPE_1);
        cmd.setCartItemCmd(cmdItem);
        when(validator.systemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        cmd.setCartDTO(getNewCartDTOWithGoodwillItem());
        
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateIfBusPassOtTravelCardExceedsLimit(){
        CartItemCmdImpl cmdItem =getTestAnnualBusPass1();
        cmdItem.setTicketType(TicketType.BUS_PASS.code());
        cmdItem.setTravelCardType(null);
        cmdItem.setEndDate(null);
        cmd.setCartItemCmd(cmdItem);
        when(validator.systemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        CartDTO cartDTO = getNewCartDTOWithGoodwillItem();
        cartDTO.addCartItem(getNewAnnualBusPassProductItemDTO());
        cartDTO.addCartItem(getNewProductItemDTO());
        cartDTO.addCartItem(getNewAnnualBusPassProductItemDTO());
        cartDTO.addCartItem(getNewProductItemDTO());
        cartDTO.addCartItem(getNewAnnualBusPassProductItemDTO());
        cmd.setCartDTO(cartDTO);
        
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }
    

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(CartDTO.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(CartCmdImpl.class));
    }
    
    @Test
    public void shouldValidateWithPreviouslyExchangedFalse() {
        CartItemCmdImpl cartItemCmd = getTestTravelCard10();
        cartItemCmd.setPreviouslyExchanged(false);
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        cmd.setCartItemCmd(cartItemCmd);
        cmd.setCartDTO(getNewCartDTOWithGoodwillItem());
        when(validator.systemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        validator.validate(cmd, errors);
        
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldValidateWithPreviouslyExchangedTrue() {
        CartItemCmdImpl cartItemCmd = getTestTravelCard10();
        cartItemCmd.setPreviouslyExchanged(true);
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        cmd.setCartItemCmd(cartItemCmd);
        cmd.setCartDTO(getNewCartDTOWithGoodwillItem());
        when(validator.systemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        validator.validate(cmd, errors);
        
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidatePreviouslyExchangedTicketWithoutExchangedDate() {
        CartItemCmdImpl cartItemCmd = getTestTravelCard10();
        cartItemCmd.setPreviouslyExchanged(true);
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        tradedCartItemCmd.setExchangedDate(null);
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        cmd.setCartItemCmd(cartItemCmd);
        cmd.setCartDTO(getNewCartDTOWithGoodwillItem());
        when(validator.systemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        validator.validate(cmd, errors);
        
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidatePreviouslyExchangedTicketWithoutTravelcardType() {
        CartItemCmdImpl cartItemCmd = getTestTravelCard10();
        cartItemCmd.setPreviouslyExchanged(true);
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        tradedCartItemCmd.setTravelCardType(null);
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        cmd.setCartItemCmd(cartItemCmd);
        cmd.setCartDTO(getNewCartDTOWithGoodwillItem());
        when(validator.systemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        validator.validate(cmd, errors);
        
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidatePreviouslyExchangedTicketWithoutPassengerType() {
        CartItemCmdImpl cartItemCmd = getTestTravelCard10();
        cartItemCmd.setPreviouslyExchanged(true);
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        tradedCartItemCmd.setPassengerType(null);
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        cmd.setCartItemCmd(cartItemCmd);
        cmd.setCartDTO(getNewCartDTOWithGoodwillItem());
        when(validator.systemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        validator.validate(cmd, errors);
        
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidatePreviouslyExchangedTicketWithoutDiscountType() {
        CartItemCmdImpl cartItemCmd = getTestTravelCard10();
        cartItemCmd.setPreviouslyExchanged(true);
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        tradedCartItemCmd.setDiscountType(null);
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        cmd.setCartItemCmd(cartItemCmd);
        cmd.setCartDTO(getNewCartDTOWithGoodwillItem());
        when(validator.systemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        validator.validate(cmd, errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidatePreviouslyExchangedTicketWithoutStartDate() {
        CartItemCmdImpl cartItemCmd = getTestTravelCard10();
        cartItemCmd.setPreviouslyExchanged(true);
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        tradedCartItemCmd.setStartDate(null);
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        cmd.setCartItemCmd(cartItemCmd);
        cmd.setCartDTO(getNewCartDTOWithGoodwillItem());
        when(validator.systemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        validator.validate(cmd, errors);
        
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidatePreviouslyExchangedTicketWithoutStartZone() {
        CartItemCmdImpl cartItemCmd = getTestTravelCard10();
        cartItemCmd.setPreviouslyExchanged(true);
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        tradedCartItemCmd.setStartZone(null);
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        cmd.setCartItemCmd(cartItemCmd);
        cmd.setCartDTO(getNewCartDTOWithGoodwillItem());
        when(validator.systemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        validator.validate(cmd, errors);
        
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidatePreviouslyExchangedTicketWithoutEndZone() {
        CartItemCmdImpl cartItemCmd = getTestTravelCard10();
        cartItemCmd.setPreviouslyExchanged(true);
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        tradedCartItemCmd.setEndZone(null);
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        cmd.setCartItemCmd(cartItemCmd);
        cmd.setCartDTO(getNewCartDTOWithGoodwillItem());
        when(validator.systemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        validator.validate(cmd, errors);
        
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void ShouldValidateIgnoreMandatoryValidationForBusPassTravelCardTypeOfPreviouslyExchangedTicket(){
        CartItemCmdImpl cartItemCmd = getTestTravelCard10();
        cartItemCmd.setPreviouslyExchanged(true);
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        tradedCartItemCmd.setTravelCardType(ANNUAL_BUS_PASS);
        tradedCartItemCmd.setStartZone(null);
        tradedCartItemCmd.setEndZone(null);
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        cmd.setCartItemCmd(cartItemCmd);
        cmd.setCartDTO(getNewCartDTOWithGoodwillItem());
        when(validator.systemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        validator.validate(cmd, errors);
        
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateEndDateForOtherTravelCardTypePrevioslyExchangedTicket(){
        CartItemCmdImpl cartItemCmd = getTestTravelCard10();
        cartItemCmd.setPreviouslyExchanged(true);
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        tradedCartItemCmd.setTravelCardType(CartAttribute.OTHER_TRAVEL_CARD);
        tradedCartItemCmd.setEndDate(null);
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        cmd.setCartItemCmd(cartItemCmd);
        cmd.setCartDTO(getNewCartDTOWithGoodwillItem());
        when(validator.systemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        validator.validate(cmd, errors);
        
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void ShouldRejectIfPreviouslyExchangedTicketSameAsOriginalTicket(){
        CartItemCmdImpl cartItemCmd = getTestTravelCard10();
        cartItemCmd.setPreviouslyExchanged(true);
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        tradedCartItemCmd.setStartDate(cartItemCmd.getStartDate());
        tradedCartItemCmd.setEndDate(cartItemCmd.getEndDate());
        tradedCartItemCmd.setStartZone(cartItemCmd.getStartZone());
        tradedCartItemCmd.setEndZone(cartItemCmd.getEndZone());
        tradedCartItemCmd.setPrice(cartItemCmd.getPrice());
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        cmd.setCartItemCmd(cartItemCmd);
        cmd.setCartDTO(getNewCartDTOWithGoodwillItem());
        when(validator.systemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        validator.validate(cmd, errors);
        
        assertTrue(errors.hasErrors());
    }
    
}
