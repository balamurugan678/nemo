package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.CartCmdTestUtil.STATION_500;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.VALID_CARD_NUMBER_1;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.getTestAddress;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithCartRefundTotalForPayASyouGoItem;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.PAY_AS_YOU_GO_AD_HOC_REFUND_LIMIT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.validator.PostcodeValidator;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;

@RunWith(Parameterized.class)
public class RefundPaymentTypeValidatorTest {

    private RefundPaymentTypeValidator validator;

    private CartCmdImpl cmd;
    private SystemParameterService systemParameterService;
    private PostcodeValidator postcodeValidator;
    private Errors errors;

    private String paymentType;
    private AddressDTO payeeAddress;
    private boolean hasErrors;
    private String cardNumber;
    private Long stationId;
    private Integer payAsYouGoAdHocRefundLimitAmount;
    private boolean isValidPostcode;
    private CartDTO cartDto;
    private String sortCode;
    private String accountNumber;

    private static final String AD_HOC_LOAD = "AdhocLoad";
    private static final String BACS = "BACS";
    private static final String CHEQUE = "Cheque";
    private static final String DEFAULT_FIRST_NAME = "Jane";
    private static final String DEFAULT_LAST_NAME = "Smith";
    private static final String SORT_CODE = "12-12-12";
    private static final String ACCOUNT_NUMBER = "12345687";

    @Before
    public void setUp() throws Exception {
        validator = new RefundPaymentTypeValidator();
        cmd = new CartCmdImpl();
        systemParameterService = mock(SystemParameterService.class);
        postcodeValidator = mock(PostcodeValidator.class);

        errors = new BeanPropertyBindingResult(cmd, "cmd");

        validator.systemParameterService = systemParameterService;
        validator.postcodeValidator = postcodeValidator;
    }

    public RefundPaymentTypeValidatorTest(String testName, String paymentType, String cardNumber, Long stationId, AddressDTO payeeAddress,
                    Integer payAsYouGoAdHocRefundLimitAmount, boolean isValidPostcode, CartDTO cartDto, String accountNumber, String sortCode,
                    boolean hasErrors) {
        this.paymentType = paymentType;
        this.cardNumber = cardNumber;
        this.stationId = stationId;
        this.payeeAddress = payeeAddress;
        this.payAsYouGoAdHocRefundLimitAmount = payAsYouGoAdHocRefundLimitAmount;
        this.isValidPostcode = isValidPostcode;
        this.cartDto = cartDto;
        this.accountNumber = accountNumber;
        this.sortCode = sortCode;
        this.hasErrors = hasErrors;
    }

    @Parameters(name = "{index}: test({0}) expected={1}")
    public static Collection<?> testParameters() {
        return Arrays.asList(new Object[][] {
                        { "shouldValidateAdHocPayment", AD_HOC_LOAD, VALID_CARD_NUMBER_1, STATION_500, getTestAddress(), 40, true,
                                        getNewCartDTOWithCartRefundTotalForPayASyouGoItem(), null, null, false },
                        { "shouldValidateChequePayment", CHEQUE, VALID_CARD_NUMBER_1, STATION_500, getTestAddress(), 40, true,
                                        getNewCartDTOWithCartRefundTotalForPayASyouGoItem(), null, null, false },
                        { "shouldValidateBacsPayment", BACS, VALID_CARD_NUMBER_1, STATION_500, getTestAddress(), 40, true,
                                        getNewCartDTOWithCartRefundTotalForPayASyouGoItem(), ACCOUNT_NUMBER, SORT_CODE, false },
                        { "sholdValidateNonSpecifiedPayment", "Test", null, null, null, null, true, null, null, null, false },
                        { "shouldNOValidateIfPayAsYouGoBalanceIsGreaterThanLimitInSystemInAdHocLoad", AD_HOC_LOAD, VALID_CARD_NUMBER_1, STATION_500,
                                        getTestAddress(), 10, true, getNewCartDTOWithCartRefundTotalForPayASyouGoItem(), null, null, true },
                        { "shouldNOValidateIfInvalidPostcode", CHEQUE, VALID_CARD_NUMBER_1, STATION_500, getTestAddress(), 10, false,
                                        getNewCartDTOWithCartRefundTotalForPayASyouGoItem(), null, null, true }, });
    }

    @Test
    public void validate() {
        cmd.setPaymentType(this.paymentType);
        cmd.setCardNumber(this.cardNumber);
        cmd.setTargetCardNumber(this.cardNumber);
        cmd.setStationId(this.stationId);
        cmd.setPayeeAddress(this.payeeAddress);
        cmd.setCartDTO(this.cartDto);
        cmd.setFirstName(DEFAULT_FIRST_NAME);
        cmd.setLastName(DEFAULT_LAST_NAME);
        cmd.setPayeeAccountNumber(this.accountNumber);
        cmd.setPayeeSortCode(this.sortCode);

        when(systemParameterService.getIntegerParameterValue(PAY_AS_YOU_GO_AD_HOC_REFUND_LIMIT.code())).thenReturn(
                        this.payAsYouGoAdHocRefundLimitAmount);
        when(postcodeValidator.validate(anyString())).thenReturn(this.isValidPostcode);
        validator.validate(cmd, errors);
        assertEquals(this.hasErrors, errors.hasErrors());
    }

}
