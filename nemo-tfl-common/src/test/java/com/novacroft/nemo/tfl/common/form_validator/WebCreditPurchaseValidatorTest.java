package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.command.impl.CommonLoginCmd;
import com.novacroft.nemo.tfl.common.command.WebCreditPurchaseCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;

/**
 * WebCreditPurchaseValidator unit tests
 */
public class WebCreditPurchaseValidatorTest {
    private WebCreditPurchaseValidator validator;
    private WebCreditPurchaseCmd mockWebCreditPurchaseCmd;
    private Errors mockErrors;

    private static final Integer WEB_CREDIT_AMOUNT = 123;
    private static final Integer WEB_CREDIT_APPLY_AMOUNT = 152;
    private static final Integer ORDER_AMOUNT = 128;

    @Before
    public void setUp() {
        this.validator = mock(WebCreditPurchaseValidator.class);
        this.mockWebCreditPurchaseCmd = mock(WebCreditPurchaseCmd.class);
        this.mockErrors = mock(Errors.class);
    }

    @Test
    public void shouldSupportClass() {
        when(this.validator.supports(any(Class.class))).thenCallRealMethod();
        assertTrue(this.validator.supports(WebCreditPurchaseCmd.class));
    }

    @Test
    public void shouldNotSupportClass() {
        when(this.validator.supports(any(Class.class))).thenCallRealMethod();
        assertFalse(this.validator.supports(CommonLoginCmd.class));
    }

    @Test
    public void shouldValidateWebCreditApplyAmountIsNotMoreThanAvailableAmount() {
        doCallRealMethod().when(this.validator).validateWebCreditApplyAmountIsNotMoreThanAvailableAmount(any(WebCreditPurchaseCmd.class),
                        any(Errors.class));
        CartCmdImpl webCreditPurchaseCmd = new CartCmdImpl();
        webCreditPurchaseCmd.setWebCreditAvailableAmount(WEB_CREDIT_AMOUNT);
        webCreditPurchaseCmd.setTotalAmt(ORDER_AMOUNT);
        webCreditPurchaseCmd.setWebCreditApplyAmount(WEB_CREDIT_APPLY_AMOUNT);
        Errors errors = new BeanPropertyBindingResult(webCreditPurchaseCmd, "cmd");
        this.validator.validateWebCreditApplyAmountIsNotMoreThanAvailableAmount(webCreditPurchaseCmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldValidateWebCreditApplyAmountIsNotLessThanZero() {
        doCallRealMethod().when(this.validator).validateWebCreditApplyAmountIsNotLessThanZero(any(WebCreditPurchaseCmd.class), any(Errors.class));
        doNothing().when(this.validator).rejectIfPenceAmountBelowMinimumValue(any(Errors.class), anyString(), anyInt(), anyInt());
        this.validator.validateWebCreditApplyAmountIsNotLessThanZero(this.mockWebCreditPurchaseCmd, this.mockErrors);
        verify(this.validator).rejectIfPenceAmountBelowMinimumValue(any(Errors.class), anyString(), anyInt(), anyInt());
    }

    @Test
    public void shouldValidateWebCreditApplyAmountIsNotNull() {
        doCallRealMethod().when(this.validator).validateWebCreditApplyAmountIsNotNull(any(Errors.class));
        doNothing().when(this.validator).rejectIfMandatoryFieldEmpty(any(Errors.class), anyString());
        this.validator.validateWebCreditApplyAmountIsNotNull(this.mockErrors);
        verify(this.validator).rejectIfMandatoryFieldEmpty(any(Errors.class), anyString());
    }

    @Test
    public void shouldValidateWebCreditApplyAmountGreaterThanZero() {
        doCallRealMethod().when(validator).validateWebCreditApplyAmount(any(WebCreditPurchaseCmd.class), any(Errors.class));
        when(mockWebCreditPurchaseCmd.getWebCreditAvailableAmount()).thenReturn(WEB_CREDIT_AMOUNT);
        when(mockWebCreditPurchaseCmd.getWebCreditApplyAmount()).thenReturn(WEB_CREDIT_APPLY_AMOUNT);
        validator.validateWebCreditApplyAmount(mockWebCreditPurchaseCmd, mockErrors);
        verify(validator).validateWebCreditApplyAmountIsNotMoreThanAvailableAmount(any(WebCreditPurchaseCmd.class), any(Errors.class));
    }

    @Test
    public void shouldValidateWebCreditApplyAmountWhenAvailableAmountIsZero() {
        doCallRealMethod().when(validator).validateWebCreditApplyAmount(any(WebCreditPurchaseCmd.class), any(Errors.class));
        doCallRealMethod().when(validator).validateWCApplyAmountWhenWCAvailableAmountIsZero(any(WebCreditPurchaseCmd.class), any(Errors.class));
        when(mockWebCreditPurchaseCmd.getWebCreditAvailableAmount()).thenReturn(0);
        when(mockWebCreditPurchaseCmd.getWebCreditApplyAmount()).thenReturn(WEB_CREDIT_APPLY_AMOUNT);
        validator.validateWebCreditApplyAmount(mockWebCreditPurchaseCmd, mockErrors);
        verify(validator).validateWCApplyAmountWhenWCAvailableAmountIsZero(any(WebCreditPurchaseCmd.class), any(Errors.class));
    }

    @Test
    public void shouldValidate() {
        doCallRealMethod().when(this.validator).validate(any(Object.class), any(Errors.class));
        doNothing().when(this.validator).validateWebCreditApplyAmountIsNotNull(any(Errors.class));
        doNothing().when(this.validator).validateWebCreditApplyAmountIsNotLessThanZero(any(WebCreditPurchaseCmd.class), any(Errors.class));
        doNothing().when(this.validator).validateWebCreditApplyAmountIsNotMoreThanAvailableAmount(any(WebCreditPurchaseCmd.class), any(Errors.class));
        this.validator.validate(mockWebCreditPurchaseCmd, mockErrors);
        verify(this.validator).validateWebCreditApplyAmountIsNotNull(any(Errors.class));
        verify(this.validator).validateWebCreditApplyAmountIsNotLessThanZero(any(WebCreditPurchaseCmd.class), any(Errors.class));
        verify(this.validator).validateWebCreditApplyAmount(any(WebCreditPurchaseCmd.class), any(Errors.class));
    }

    @Test
    public void instantiationTest() {
        assertNotNull(validator = new WebCreditPurchaseValidator());
    }
}
