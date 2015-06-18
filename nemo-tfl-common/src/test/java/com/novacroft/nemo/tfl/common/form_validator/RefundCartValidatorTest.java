package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.CartItemTestUtil.getSevenDayTradedTicket;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestTravelCard10;
import static com.novacroft.nemo.test_support.CartTestUtil.FUTURE_DATE_OF_REFUND_INCREMENT;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithProductItem;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewProductItemDTOWithEndDateBeforeDateOfRefund;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.test_support.AdministrationFeeTestUtil;
import com.novacroft.nemo.test_support.CartCmdTestUtil;
import com.novacroft.nemo.test_support.CartItemTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.PayAsYouGoTestUtil;
import com.novacroft.nemo.tfl.common.command.BusPassCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;

/**
 * Refund Validator unit tests
 */
public class RefundCartValidatorTest {
    private RefundCartValidator validator;
    private CartCmdImpl cmd;
    private RefundPayAsYouGoItemValidator mockRefundPayAsYouGoItemValidator;
    private AdministrationFeeValidator mockAdministrationFeeValidator;
    private CartItemCmdImpl cartItemCmd;
    private static final String AD_HOC_LOAD = "AdHocLoad";
    private static final String ANNUAL_BUS_PASS = "Annual Bus Pass";

    @Before
    public void setUp() {
        validator = new RefundCartValidator();
        cmd = new CartCmdImpl();
        mockRefundPayAsYouGoItemValidator = mock(RefundPayAsYouGoItemValidator.class);
        mockAdministrationFeeValidator =  mock(AdministrationFeeValidator.class);
        validator.refundPayAsYouGoItemValidator = mockRefundPayAsYouGoItemValidator;
        validator.administrationFeeValidator = mockAdministrationFeeValidator;
        
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(CartCmdImpl.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(BusPassCmd.class));
    }

    @Test
    public void shouldValidate() {
        CartItemCmdImpl element = new CartItemCmdImpl();
        CartItemCmdImpl element2 = new CartItemCmdImpl();
        element.setPrice(0);
        element2.setPrice(0);
        cmd.setDateOfRefund(new Date());
        cmd.setCartDTO(CartTestUtil.getNewCartDTOWithDateOfRefund());
        cmd.setPayAsYouGoValue(PayAsYouGoTestUtil.TICKET_PAY_AS_YOU_GO_PRICE_1);
        cmd.setAdministrationFeeValue(AdministrationFeeTestUtil.ADMINISTRATION_FEE_PRICE_1);
        cmd.setPaymentType(AD_HOC_LOAD);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());

    }

    @Test
    public void shouldNotValidateIfPayAsYouGoCreditFieldEmpty() {
        cmd.setDateOfRefund(new Date());
        cmd.setCartItemList(CartItemTestUtil.getTestPayAsYouGoEmptyList());

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfDateOfRefundIsInFuture() {
        cmd.setDateOfRefund(DateUtil.addDaysToDate(new Date(), FUTURE_DATE_OF_REFUND_INCREMENT));
        cmd.setCartItemList(CartItemTestUtil.getTestPayAsYouGoList1());

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfAnyOneOfSelectedProductsExpiredOnDateOfRefund() {
        cmd.setDateOfRefund(new Date());
        cmd.setCartItemList(CartItemTestUtil.getTestTravelCardList1());

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidatefPayAsYouGoCreditFieldEmpty() {
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithCartRefundTotalForPayASyouGoItem();
        cartDTO.setDateOfRefund(new Date());
        cartDTO.getCartItems().get(0).setPrice(null);
        cmd.setCartDTO(cartDTO);
        cmd.setDateOfRefund(new Date());
        cmd.setPayAsYouGoValue(PayAsYouGoTestUtil.TICKET_PAY_AS_YOU_GO_PRICE_1);
        cmd.setAdministrationFeeValue(AdministrationFeeTestUtil.ADMINISTRATION_FEE_PRICE_1);
        cmd.setPaymentType(AD_HOC_LOAD);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfPayAsYouGoItemfieldIsNotAvailable() {
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithPayAsYouGoItem();
        cmd.setCartDTO(cartDTO);
        cmd.setDateOfRefund(new Date());
        cmd.setPayAsYouGoValue(PayAsYouGoTestUtil.TICKET_PAY_AS_YOU_GO_PRICE_1);
        cmd.setAdministrationFeeValue(AdministrationFeeTestUtil.ADMINISTRATION_FEE_PRICE_1);
        cmd.setPaymentType(AD_HOC_LOAD);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfCartDateOfRefundIsInFuture() {
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithDateOfRefund();
        cartDTO.setDateOfRefund(DateUtil.addDaysToDate(new Date(), FUTURE_DATE_OF_REFUND_INCREMENT));
        cmd.setCartDTO(cartDTO);
        cmd.setDateOfRefund(DateUtil.addDaysToDate(new Date(), FUTURE_DATE_OF_REFUND_INCREMENT));
        cmd.setPayAsYouGoValue(PayAsYouGoTestUtil.TICKET_PAY_AS_YOU_GO_PRICE_1);
        cmd.setAdministrationFeeValue(AdministrationFeeTestUtil.ADMINISTRATION_FEE_PRICE_1);
        cmd.setPaymentType(AD_HOC_LOAD);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfAnyOneOfSelectedProductsInCartAreExpiredOnDateOfRefund() {
        CartDTO cartDTO = getNewCartDTOWithProductItem();
        cartDTO.getCartItems().clear();
        cartDTO.addCartItem(getNewProductItemDTOWithEndDateBeforeDateOfRefund());
        cartDTO.setDateOfRefund(DateUtil.addDaysToDate(new Date(), FUTURE_DATE_OF_REFUND_INCREMENT));
        cmd.setCartDTO(cartDTO);
        cmd.setDateOfRefund(DateUtil.addDaysToDate(new Date(), FUTURE_DATE_OF_REFUND_INCREMENT));
        cmd.setPayAsYouGoValue(PayAsYouGoTestUtil.TICKET_PAY_AS_YOU_GO_PRICE_1);
        cmd.setAdministrationFeeValue(AdministrationFeeTestUtil.ADMINISTRATION_FEE_PRICE_1);
        cmd.setPaymentType(AD_HOC_LOAD);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateForNegativeValueOfAdministrationFeeValue() {
        CartDTO cartDTO = getNewCartDTOWithProductItem();
        cartDTO.getCartItems().clear();
        cartDTO.addCartItem(getNewProductItemDTOWithEndDateBeforeDateOfRefund());
        cartDTO.setDateOfRefund(DateUtil.addDaysToDate(new Date(), FUTURE_DATE_OF_REFUND_INCREMENT));
        cmd.setCartDTO(cartDTO);
        cmd.setDateOfRefund(DateUtil.addDaysToDate(new Date(), FUTURE_DATE_OF_REFUND_INCREMENT));
        cmd.setPayAsYouGoValue(PayAsYouGoTestUtil.TICKET_PAY_AS_YOU_GO_PRICE_1);
        cmd.setAdministrationFeeValue(AdministrationFeeTestUtil.NEGATIVE_ADMINISTRATION_FEE);
        cmd.setPaymentType(AD_HOC_LOAD);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateForNegativeValueOfPayAsYouGoValue() {
        CartDTO cartDTO = getNewCartDTOWithProductItem();
        cartDTO.getCartItems().clear();
        cartDTO.addCartItem(getNewProductItemDTOWithEndDateBeforeDateOfRefund());
        cartDTO.setDateOfRefund(DateUtil.addDaysToDate(new Date(), FUTURE_DATE_OF_REFUND_INCREMENT));
        cmd.setCartDTO(cartDTO);
        cmd.setDateOfRefund(DateUtil.addDaysToDate(new Date(), FUTURE_DATE_OF_REFUND_INCREMENT));
        cmd.setPayAsYouGoValue(PayAsYouGoTestUtil.NEGATIVE_PAY_AS_YOU_GO_PRICE);
        cmd.setAdministrationFeeValue(AdministrationFeeTestUtil.NEGATIVE_ADMINISTRATION_FEE);
        cmd.setPaymentType(AD_HOC_LOAD);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }
    
    
    
    @Test
    public void shouldValidateWithPreviouslyExchangedFalse() {
        cartItemCmd = getTestTravelCard10();
        cartItemCmd.setPreviouslyExchanged(false);
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        cmd.setCartItemCmd(cartItemCmd);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validatePreviouslyExchangedTicketFields(errors,cmd);
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldValidateWithPreviouslyExchangedTrue() {
        CartItemCmdImpl cartItemCmd = getTestTravelCard10();
        cartItemCmd.setPreviouslyExchanged(true);
        CartItemCmdImpl tradedCartItemCmd = getSevenDayTradedTicket();
        cartItemCmd.setTradedTicket(tradedCartItemCmd);
        cmd.setCartItemCmd(cartItemCmd);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validatePreviouslyExchangedTicketFields(errors,cmd);
        
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
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validatePreviouslyExchangedTicketFields(errors,cmd);
        
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
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validatePreviouslyExchangedTicketFields(errors,cmd);
        
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
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validatePreviouslyExchangedTicketFields(errors, cmd);

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
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validatePreviouslyExchangedTicketFields(errors,cmd);
        
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
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validatePreviouslyExchangedTicketFields(errors,cmd);
        
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
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validatePreviouslyExchangedTicketFields(errors,cmd);
        
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
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validatePreviouslyExchangedTicketFields(errors,cmd);
        
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
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validatePreviouslyExchangedTicketFields(errors,cmd);
        
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
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validatePreviouslyExchangedTicketFields(errors,cmd);
        
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
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validatePreviouslyExchangedTicketFields(errors,cmd);
        
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void ShouldRejectIfBackDatedReasonIsEmptyForEnabledBackDatedFlag(){
        CartItemCmdImpl cartItemCmd = getTestTravelCard10();
        cartItemCmd.setBackdated(true);
        cmd.setCartItemCmd(cartItemCmd);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.rejectIfBackDatedReasonFieldEmpty(errors,cmd);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldRejectIfDateOfLastUsageFieldIsEmptyTest(){
        CartItemCmdImpl cartItemCmd = getTestTravelCard10();
        cartItemCmd.setDeceasedCustomer(true);
        cartItemCmd.setDateOfLastUsage(null);
        cmd.setCartItemCmd(cartItemCmd);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.rejectIfDateOfLastUsageFieldEmpty(errors, cmd);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotRejectIfDateOfLastUsageFieldIsNotEmptyTest(){
        CartItemCmdImpl cartItemCmd = getTestTravelCard10();
        cartItemCmd.setDeceasedCustomer(false);
        cartItemCmd.setDateOfLastUsage(new Date());
        cmd.setCartItemCmd(cartItemCmd);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.rejectIfDateOfLastUsageFieldEmpty(errors, cmd);
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldRejectIfCardSurrenderDateFieldIsEmptyTest(){
        CartItemCmdImpl cartItemCmd = getTestTravelCard10();
        cartItemCmd.setBackdated(true);
        cartItemCmd.setDateOfCanceAndSurrender(null);
        cmd.setCartItemCmd(cartItemCmd);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.rejectIfCardSurrenderDateFieldEmpty(errors, cmd);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotRejectIfCardSurrenderDateFieldIsNotEmptyTest(){
        CartItemCmdImpl cartItemCmd = getTestTravelCard10();
        cartItemCmd.setBackdated(false);
        cartItemCmd.setDateOfCanceAndSurrender(new Date());
        cmd.setCartItemCmd(cartItemCmd);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.rejectIfCardSurrenderDateFieldEmpty(errors, cmd);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void isAnyOneOfSelectedProductsExpiredOnDateOfRefundShouldReturnFalseIfNoProductItemsInList() {
        assertFalse(validator.isAnyOneOfSelectedProductsExpiredOnDateOfRefund(CartCmdTestUtil.getTestCartCmdItemWithDefaultItems()));
    }
}
