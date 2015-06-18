package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.CartCmdTestUtil.getTestCartCmd4;
import static com.novacroft.nemo.test_support.CartItemTestUtil.OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestTravelCard1;
import static com.novacroft.nemo.test_support.CartTestUtil.getPayAsYouGoItemDTO;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.MAXIMUM_ALLOWED_TRAVEL_CARDS;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.data_service.CountryDataService;
import com.novacroft.nemo.common.transfer.CountryDTO;
import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.common.validator.PostcodeValidator;
import com.novacroft.nemo.test_support.AdministrationFeeTestUtil;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartCmdTestUtil;
import com.novacroft.nemo.test_support.CartItemTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.PayAsYouGoTestUtil;
import com.novacroft.nemo.tfl.common.command.BusPassCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

/**
 * Refund Cart Payment Validator unit tests
 */
public class RefundCartPaymentValidatorTest {
    private RefundCartPaymentValidator validator;
    private CartCmdImpl cmd;
    private SystemParameterService mockSystemParameterService;
    private PostcodeValidator postcodeValidator;

    private static final String AD_HOC_LOAD = "AdhocLoad";
    private static final String BACS = "BACS";
    private static final String CHEQUE = "Cheque";
    private static final Integer PREVIOUS_CREDIT = 12;
    private static final Integer TO_PAY_AMOUNT = 50;
    private CountryDTO ukCountryDTO = new CountryDTO();
    private CountryDataService mockCountryDataService;

    @Before
    public void setUp() {
        validator = new RefundCartPaymentValidator();
        cmd = new CartCmdImpl();
        mockSystemParameterService = mock(SystemParameterService.class);
        postcodeValidator = new PostcodeValidator();

        validator.systemParameterService = mockSystemParameterService;
        validator.postcodeValidator = postcodeValidator;
        ukCountryDTO.setCode("GB");
        mockCountryDataService = mock(CountryDataService.class);
        validator.countryDataService = mockCountryDataService;
        when(mockCountryDataService.findCountryByCode(BaseValidator.ISO_UK_CODE)).thenReturn(ukCountryDTO);
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
    public void shouldValidateForAdhochPaymentType() {
        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(200);
        cmd.setDateOfRefund(new Date());
        cmd.setCartDTO(CartTestUtil.getNewCartDTOWithCartRefundTotalForPayASyouGoItem());
        cmd.setCartItemList(CartItemTestUtil.getTestPayAsYouGoList2());
        cmd.setPayAsYouGoValue(PayAsYouGoTestUtil.TICKET_PAY_AS_YOU_GO_PRICE_1);
        cmd.setAdministrationFeeValue(AdministrationFeeTestUtil.ADMINISTRATION_FEE_PRICE_1);
        cmd.setPaymentType(AD_HOC_LOAD);
        cmd.setTargetCardNumber(CardTestUtil.OYSTER_NUMBER_1);
        cmd.setStationId(new Long(20));

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());

    }

    @Test
    public void shouldValidateForChequePaymentType() {
        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(200);

        cmd = CartCmdTestUtil.getTestCartCmdItemWithDefaultItemsAndPayeeDetails();
        cmd.setPaymentType(CHEQUE);
        cmd.setTargetCardNumber(CardTestUtil.OYSTER_NUMBER_1);
        cmd.setStationId(new Long(20));

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateForBACSPaymentType() {
        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(200);

        cmd = CartCmdTestUtil.getTestCartCmdItemWithDefaultItemsAndPayeeDetails();
        cmd.setPaymentType(BACS);
        cmd.setTargetCardNumber(CardTestUtil.OYSTER_NUMBER_1);
        cmd.setStationId(new Long(20));

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateDifferentInstanceOfItemDTO() {
        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(50);
        cmd.setDateOfRefund(new Date());
        cmd.setCartDTO(CartTestUtil.getNewCartDTOWithDateOfRefund());
        cmd.setCartItemList(CartItemTestUtil.getTestPayAsYouGoList2());
        cmd.setPaymentType(AD_HOC_LOAD);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        verify(mockSystemParameterService, atLeastOnce()).getIntegerParameterValue(anyString());
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfMandatoryFieldsForChequePaymentAreEmpty() {
        cmd.setDateOfRefund(new Date());
        cmd.setCartDTO(CartTestUtil.getNewCartDTOWithDateOfRefund());
        cmd.setPaymentType(CHEQUE);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfMandatoryFieldsForBacsPaymentAreEmpty() {
        cmd.setPaymentType(BACS);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfMandatoryFieldsForBacsPaymentWhenPayeeAccountDetailsAreEmpty() {
        cmd = CartCmdTestUtil.getTestCartCmdItemWithDefaultItemsAndPayeeDetailsNoAccountDetails();
        cmd.setPaymentType(BACS);
        cmd.setTargetCardNumber(CardTestUtil.OYSTER_NUMBER_1);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfMandatoryFieldsForBacsPaymentWhenPayeeAccountDetailsAreInvalid() {
        cmd = CartCmdTestUtil.getTestCartCmdItemWithDefaultItemsAndPayeeDetailsInvalidAccountDetails();
        cmd.setPaymentType(BACS);
        cmd.setTargetCardNumber(CardTestUtil.OYSTER_NUMBER_1);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfMandatoryFieldsForBacsPaymentWhenPayeeAccountDetailsHasInvalidPostcode() {
        cmd = CartCmdTestUtil.getTestCartCmdItemWithDefaultItemsAndPayeeDetailsInvalidAccountDetails2();
        cmd.setPaymentType(BACS);
        cmd.setTargetCardNumber(CardTestUtil.OYSTER_NUMBER_1);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfTotalAmountAndPreviousCreditIsGreaterThanLimitInAdHocLoadNullPreviosCredit() {
        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(50);
        cmd.setCartDTO(CartTestUtil.getNewCartDTOWithCartRefundTotal());
        cmd.setCartItemList(CartItemTestUtil.getTestPayAsYouGoList2());
        cmd.setPaymentType(AD_HOC_LOAD);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockSystemParameterService, atLeastOnce()).getIntegerParameterValue(anyString());
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfPayAsYouGoBalanceIsGreaterThanLimitInSystemInAdHocLoad() {
        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(10);
        cmd.setDateOfRefund(new Date());
        cmd.setCartDTO(CartTestUtil.getNewCartDTOWithDateOfRefund());
        cmd.setCartItemList(CartItemTestUtil.getTestPayAsYouGoList2());
        cmd.setPaymentType(AD_HOC_LOAD);
        cmd.getCartDTO().setCartItems(Arrays.asList(new ItemDTO[] { getPayAsYouGoItemDTO() }));
        cmd.setToPayAmount(TO_PAY_AMOUNT);
        
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        verify(mockSystemParameterService, atLeastOnce()).getIntegerParameterValue(anyString());
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfTotalAmountAndPreviousCreditIsGreaterThanLimitInAdHocLoad() {
        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(10);
        cmd.setDateOfRefund(new Date());
        cmd.setCartDTO(CartTestUtil.getNewCartDTOWithDateOfRefund());
        cmd.setCartItemList(CartItemTestUtil.getTestPayAsYouGoList2());
        cmd.setPreviousCreditAmountOnCard(PREVIOUS_CREDIT);
        cmd.setPaymentType(AD_HOC_LOAD);
        cmd.getCartDTO().setCartItems(Arrays.asList(new ItemDTO[] { getPayAsYouGoItemDTO() }));
        cmd.setTargetCardNumber(CardTestUtil.OYSTER_NUMBER_1);
        cmd.setToPayAmount(TO_PAY_AMOUNT);
        
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        verify(mockSystemParameterService, atLeastOnce()).getIntegerParameterValue(anyString());
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfPaymentTypeFieldEmpty() {
        cmd.setDateOfRefund(new Date());
        cmd.setCartDTO(CartTestUtil.getNewCartDTOWithDateOfRefund());
        cmd.setPaymentType("");

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfBackDatedOtherReasonEmpty() {
        cmd = getTestCartCmd4();
        CartItemCmdImpl cartItem = getTestTravelCard1();
        cartItem.setTravelCardType(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
        cartItem.setRate("123");
        cartItem.setBackdated(true);
        cartItem.setBackdatedRefundReasonId(2L);
        cartItem.setDateOfCanceAndSurrender(new Date());

        cmd.setCartItemCmd(cartItem);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(3);
        validator.validate(cmd, errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldValidateIfBackDatedOtherReasonIsPresent() {
        cmd = getTestCartCmd4();
        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(200);

        cmd = CartCmdTestUtil.getTestCartCmdItemWithDefaultItemsAndPayeeDetails();
        cmd.setPaymentType(CHEQUE);
        cmd.setTargetCardNumber(CardTestUtil.OYSTER_NUMBER_1);
        cmd.setStationId(new Long(20));
        CartItemCmdImpl cartItem = getTestTravelCard1();
        cartItem.setTravelCardType(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
        cartItem.setRate("123");
        cartItem.setBackdated(true);
        cartItem.setBackdatedRefundReasonId(2L);
        cartItem.setDateOfCanceAndSurrender(new Date());
        cartItem.setBackdatedOtherReason("Broken Ticket");

        cmd.setCartItemCmd(cartItem);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        assertFalse(errors.hasErrors());
    }
}
