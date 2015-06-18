package com.novacroft.nemo.tfl.batch.validator.impl.financial_services_centre;

import com.novacroft.nemo.common.utils.StringUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class BaseFinancialServicesCentreFieldValidatorTest {
    private static final String TEST_VALUE = "1";
    private BaseFinancialServicesCentreFieldValidator validator;
    private Errors mockErrors;

    @Before
    public void setUp() {
        this.validator = mock(BaseFinancialServicesCentreFieldValidator.class);

        this.mockErrors = mock(Errors.class);
        doNothing().when(this.mockErrors).reject(anyString(), any(Object[].class), anyString());
    }

    @Test
    public void isValidPrintedOnShouldReturnFalseWithBlankValue() {
        doIsValidPrintedOnTest(StringUtil.EMPTY_STRING, false, true);
    }

    @Test
    public void isValidPrintedOnShouldReturnFalseWithInvalidValue() {
        doIsValidPrintedOnTest(TEST_VALUE, false, false);
    }

    @Test
    public void isValidPrintedOnShouldReturnTrueWithValidValue() {
        doIsValidPrintedOnTest(TEST_VALUE, true, true);
    }

    @Test
    public void isValidChequeSerialNumberShouldReturnFalseWithBlankValue() {
        doIsValidChequeSerialNumberTest(StringUtil.EMPTY_STRING, false, true);
    }

    @Test
    public void isValidChequeSerialNumberShouldReturnFalseWithInvalidValue() {
        doIsValidChequeSerialNumberTest(TEST_VALUE, false, false);
    }

    @Test
    public void isValidChequeSerialNumberShouldReturnTrueWithValidValue() {
        doIsValidChequeSerialNumberTest(TEST_VALUE, true, true);
    }

    @Test
    public void isValidCustomerNameShouldReturnFalseWithBlankValue() {
        doIsValidCustomerNameTest(StringUtil.EMPTY_STRING, false);
    }

    @Test
    public void isValidCustomerNameShouldReturnTrueWithValidValue() {
        doIsValidCustomerNameTest(TEST_VALUE, true);
    }

    @Test
    public void isValidAmountShouldReturnFalseWithBlankValue() {
        doIsValidAmountTest(StringUtil.EMPTY_STRING, false, true);
    }

    @Test
    public void isValidAmountShouldReturnFalseWithInvalidValue() {
        doIsValidAmountTest(TEST_VALUE, false, false);
    }

    @Test
    public void isValidAmountShouldReturnTrueWithValidValue() {
        doIsValidAmountTest(TEST_VALUE, true, true);
    }

    @Test
    public void isValidReferenceNumberShouldReturnFalseWithBlankValue() {
        doIsValidReferenceNumberTest(StringUtil.EMPTY_STRING, false, true);
    }

    @Test
    public void isValidReferenceNumberShouldReturnFalseWithInvalidValue() {
        doIsValidReferenceNumberTest(TEST_VALUE, false, false);
    }

    @Test
    public void isValidReferenceNumberShouldReturnTrueWithValidValue() {
        doIsValidReferenceNumberTest(TEST_VALUE, true, true);
    }

    @Test
    public void validatePrintedOnShouldNotRejectValidValue() {
        doValidatePrintedOnTest(TEST_VALUE, true, 0);
    }

    @Test
    public void validatePrintedOnShouldRejectInvalidValue() {
        doValidatePrintedOnTest(TEST_VALUE, false, 1);
    }

    @Test
    public void validateChequeSerialNumberShouldNotRejectValidValue() {
        doValidateChequeSerialNumberTest(TEST_VALUE, true, 0);
    }

    @Test
    public void validateChequeSerialNumberShouldRejectInvalidValue() {
        doValidateChequeSerialNumberTest(TEST_VALUE, false, 1);
    }

    @Test
    public void validateCustomerNameShouldNotRejectValidValue() {
        doValidateCustomerNameTest(TEST_VALUE, true, 0);
    }

    @Test
    public void validateCustomerNameShouldRejectInvalidValue() {
        doValidateCustomerNameTest(TEST_VALUE, false, 1);
    }

    @Test
    public void validateAmountShouldNotRejectValidValue() {
        doValidateAmountTest(TEST_VALUE, true, 0);
    }

    @Test
    public void validateAmountShouldRejectInvalidValue() {
        doValidateAmountTest(TEST_VALUE, false, 1);
    }

    @Test
    public void validateReferenceNumberShouldNotRejectValidValue() {
        doValidateReferenceNumberTest(TEST_VALUE, true, 0);
    }

    @Test
    public void validateReferenceNumberShouldRejectInvalidValue() {
        doValidateReferenceNumberTest(TEST_VALUE, false, 1);
    }

    @Test
    public void isValidClearedOnShouldReturnFalseWithBlankValue() {
        doIsValidClearedOnTest(StringUtil.EMPTY_STRING, false, true);
    }

    @Test
    public void isValidClearedOnShouldReturnFalseWithInvalidValue() {
        doIsValidClearedOnTest(TEST_VALUE, false, false);
    }

    @Test
    public void validateClearedOnShouldNotRejectValidValue() {
        doValidateClearedOnTest(TEST_VALUE, true, 0);
    }

    @Test
    public void validateClearedOnShouldRejectInvalidValue() {
        doValidateClearedOnTest(TEST_VALUE, false, 1);
    }

    @Test
    public void isValidCurrencyShouldReturnFalseWithBlankValue() {
        doIsValidCurrencyTest(StringUtil.EMPTY_STRING, false);
    }

    @Test
    public void isValidCurrencyShouldReturnTrueWithValidValue() {
        doIsValidCurrencyTest(TEST_VALUE, true);
    }

    @Test
    public void validateCurrencyShouldNotRejectValidValue() {
        doValidateCurrencyTest(TEST_VALUE, true, 0);
    }

    @Test
    public void validateCurrencyShouldRejectInvalidValue() {
        doValidateCurrencyTest(TEST_VALUE, false, 1);
    }

    @Test
    public void isValidOutdatedOnShouldReturnFalseWithBlankValue() {
        doIsValidOutdatedOnTest(StringUtil.EMPTY_STRING, false, true);
    }

    @Test
    public void isValidOutdatedOnShouldReturnFalseWithInvalidValue() {
        doIsValidOutdatedOnTest(TEST_VALUE, false, false);
    }

    @Test
    public void isValidOutdatedOnShouldReturnTrueWithValidValue() {
        doIsValidOutdatedOnTest(TEST_VALUE, true, true);
    }

    @Test
    public void validateOutdatedOnShouldNotRejectValidValue() {
        doValidateOutdatedOnTest(TEST_VALUE, true, 0);
    }

    @Test
    public void validateOutdatedOnShouldRejectInvalidValue() {
        doValidateOutdatedOnTest(TEST_VALUE, false, 1);
    }

    private void doIsValidPrintedOnTest(String value, boolean expectedResult, boolean isValidDateReturnValue) {
        when(this.validator.isValidPrintedOn(anyString())).thenCallRealMethod();
        when(this.validator.isValidDate(anyString(), anyString())).thenReturn(isValidDateReturnValue);
        assertEquals(expectedResult, this.validator.isValidPrintedOn(value));

    }

    private void doIsValidChequeSerialNumberTest(String value, boolean expectedResult, boolean isValidDigitsReturnValue) {
        when(this.validator.isValidChequeSerialNumber(anyString())).thenCallRealMethod();
        when(this.validator.isValidDigits(anyString())).thenReturn(isValidDigitsReturnValue);
        assertEquals(expectedResult, this.validator.isValidChequeSerialNumber(value));
    }

    private void doIsValidCustomerNameTest(String value, boolean expectedResult) {
        when(this.validator.isValidCustomerName(anyString())).thenCallRealMethod();
        assertEquals(expectedResult, this.validator.isValidCustomerName(value));
    }

    private void doIsValidAmountTest(String value, boolean expectedResult, boolean isValidMonetaryValueReturnValue) {
        when(this.validator.isValidAmount(anyString())).thenCallRealMethod();
        when(this.validator.isValidMonetaryValue(anyString())).thenReturn(isValidMonetaryValueReturnValue);
        assertEquals(expectedResult, this.validator.isValidAmount(value));
    }

    private void doIsValidReferenceNumberTest(String value, boolean expectedResult, boolean isValidDigitsReturnValue) {
        when(this.validator.isValidReferenceNumber(anyString())).thenCallRealMethod();
        when(this.validator.isValidDigits(anyString())).thenReturn(isValidDigitsReturnValue);
        assertEquals(expectedResult, this.validator.isValidReferenceNumber(value));
    }

    private void doValidatePrintedOnTest(String testValue, boolean isValidPrintedOnReturnValue, int errorsRejectTimes) {
        doCallRealMethod().when(this.validator).validatePrintedOn(any(Errors.class), anyString());
        when(this.validator.isValidPrintedOn(anyString())).thenReturn(isValidPrintedOnReturnValue);
        this.validator.validatePrintedOn(this.mockErrors, testValue);
        verify(this.validator).isValidPrintedOn(anyString());
        verify(this.mockErrors, times(errorsRejectTimes)).reject(anyString(), any(Object[].class), anyString());
    }

    private void doValidateChequeSerialNumberTest(String testValue, boolean isValidChequeSerialNumberReturnValue,
                                                  int errorsRejectTimes) {
        doCallRealMethod().when(this.validator).validateChequeSerialNumber(any(Errors.class), anyString());
        when(this.validator.isValidChequeSerialNumber(anyString())).thenReturn(isValidChequeSerialNumberReturnValue);
        this.validator.validateChequeSerialNumber(this.mockErrors, testValue);
        verify(this.validator).isValidChequeSerialNumber(anyString());
        verify(this.mockErrors, times(errorsRejectTimes)).reject(anyString(), any(Object[].class), anyString());
    }

    private void doValidateCustomerNameTest(String testValue, boolean isValidCustomerNameReturnValue, int errorsRejectTimes) {
        doCallRealMethod().when(this.validator).validateCustomerName(any(Errors.class), anyString());
        when(this.validator.isValidCustomerName(anyString())).thenReturn(isValidCustomerNameReturnValue);
        this.validator.validateCustomerName(this.mockErrors, testValue);
        verify(this.validator).isValidCustomerName(anyString());
        verify(this.mockErrors, times(errorsRejectTimes)).reject(anyString(), any(Object[].class), anyString());
    }

    private void doValidateAmountTest(String testValue, boolean isValidAmountReturnValue, int errorsRejectTimes) {
        doCallRealMethod().when(this.validator).validateAmount(any(Errors.class), anyString());
        when(this.validator.isValidAmount(anyString())).thenReturn(isValidAmountReturnValue);
        this.validator.validateAmount(this.mockErrors, testValue);
        verify(this.validator).isValidAmount(anyString());
        verify(this.mockErrors, times(errorsRejectTimes)).reject(anyString(), any(Object[].class), anyString());
    }

    private void doValidateReferenceNumberTest(String testValue, boolean isValidReferenceNumberReturnValue,
                                               int errorsRejectTimes) {
        doCallRealMethod().when(this.validator).validateReferenceNumber(any(Errors.class), anyString());
        when(this.validator.isValidReferenceNumber(anyString())).thenReturn(isValidReferenceNumberReturnValue);
        this.validator.validateReferenceNumber(this.mockErrors, testValue);
        verify(this.validator).isValidReferenceNumber(anyString());
        verify(this.mockErrors, times(errorsRejectTimes)).reject(anyString(), any(Object[].class), anyString());
    }

    private void doIsValidClearedOnTest(String value, boolean expectedResult, boolean isValidDateReturnValue) {
        when(this.validator.isValidClearedOn(anyString())).thenCallRealMethod();
        when(this.validator.isValidDate(anyString(), anyString())).thenReturn(isValidDateReturnValue);
        assertEquals(expectedResult, this.validator.isValidClearedOn(value));
    }

    private void doValidateClearedOnTest(String testValue, boolean isValidClearedOnReturnValue, int errorsRejectTimes) {
        doCallRealMethod().when(this.validator).validateClearedOn(any(Errors.class), anyString());
        when(this.validator.isValidClearedOn(anyString())).thenReturn(isValidClearedOnReturnValue);
        this.validator.validateClearedOn(this.mockErrors, testValue);
        verify(this.validator).isValidClearedOn(anyString());
        verify(this.mockErrors, times(errorsRejectTimes)).reject(anyString(), any(Object[].class), anyString());
    }

    private void doIsValidCurrencyTest(String value, boolean expectedResult) {
        when(this.validator.isValidCurrency(anyString())).thenCallRealMethod();
        assertEquals(expectedResult, this.validator.isValidCurrency(value));
    }

    private void doValidateCurrencyTest(String testValue, boolean isValidCurrencyReturnValue, int errorsRejectTimes) {
        doCallRealMethod().when(this.validator).validateCurrency(any(Errors.class), anyString());
        when(this.validator.isValidCurrency(anyString())).thenReturn(isValidCurrencyReturnValue);
        this.validator.validateCurrency(this.mockErrors, testValue);
        verify(this.validator).isValidCurrency(anyString());
        verify(this.mockErrors, times(errorsRejectTimes)).reject(anyString(), any(Object[].class), anyString());
    }

    private void doIsValidOutdatedOnTest(String value, boolean expectedResult, boolean isValidDateReturnValue) {
        when(this.validator.isValidOutdatedOn(anyString())).thenCallRealMethod();
        when(this.validator.isValidDate(anyString(), anyString())).thenReturn(isValidDateReturnValue);
        assertEquals(expectedResult, this.validator.isValidOutdatedOn(value));
    }

    private void doValidateOutdatedOnTest(String testValue, boolean isValidOutdatedOnReturnValue, int errorsRejectTimes) {
        doCallRealMethod().when(this.validator).validateOutdatedOn(any(Errors.class), anyString());
        when(this.validator.isValidOutdatedOn(anyString())).thenReturn(isValidOutdatedOnReturnValue);
        this.validator.validateOutdatedOn(this.mockErrors, testValue);
        verify(this.validator).isValidOutdatedOn(anyString());
        verify(this.mockErrors, times(errorsRejectTimes)).reject(anyString(), any(Object[].class), anyString());
    }
}