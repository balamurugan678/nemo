package com.novacroft.nemo.common.validator;

import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.test_support.DateTestUtil.INVALID_DATE_1;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

/**
 * BaseValidator unit tests
 */
public class BaseValidatorTest {

    private static final String TEST_FIELD_NAME = "test-field";
    private static final Integer TEST_PENCE_ZERO_AMOUNT = 0;
    private static final Integer TEST_PENCE_LOW_AMOUNT = 10;
    private static final Integer TEST_PENCE_HIGH_AMOUNT = 20;
    private static final Date TEST_VALID_DATE = parse(formatDate(new Date()));;
    private static final String TEST_VALID_DATE_STRING = "14/04/2014";
    private static final String TEST_STRING_WITH_NUMERIC = "2500";
    private static final String TEST_STRING_WITH_DECIMALS = "250.50";
    private static final String TEST_STRING_WITH_SYMBOLS = "!!!";
    private static final String TEST_STRING_WITH_WHITE_SPACE_ALPHABETIC = "Test string";
    private static final String TEST_STRING_WITH_USER_NAME = "Teststring@mail.com";
    private static final String TEST_STRING_WITH_PHONE_NUMBER = "01212132343";
    private static final String TEST_STRING_WITH_ALPHABETIC = "Teststring";
    private static final String TEST_STRING_WITH_WHITE_SPACE_ALPHANUMERIC = "Test string1";
    private static final String TEST_STRING_WITH_CURRENCY_AND_WHITE_SPACE = "£1,00 test";
    private static final String TEST_STRING_WITH_HYPHEN_AND_WHITE_SPACE = "space hypen-test";
    private Errors mockErrors;
    private BaseValidator validator;

    @Before
    public void setUp() {
        this.mockErrors = mock(Errors.class);
        this.validator = mock(BaseValidator.class);
    }

    @Test
    public void hasNoFieldError() {
        when(this.mockErrors.hasFieldErrors(anyString())).thenReturn(true);
        doCallRealMethod().when(this.validator).hasNoFieldError(any(Errors.class), anyString());
        assertFalse(this.validator.hasNoFieldError(mockErrors, TEST_FIELD_NAME));
        verify(this.mockErrors, times(1)).hasFieldErrors(anyString());
    }

    @Test
    public void hasNoFieldErrorFalse() {
        when(this.mockErrors.hasFieldErrors(anyString())).thenReturn(false);
        doCallRealMethod().when(this.validator).hasNoFieldError(any(Errors.class), anyString());
        assertTrue(this.validator.hasNoFieldError(mockErrors, TEST_FIELD_NAME));
        verify(this.mockErrors, times(1)).hasFieldErrors(anyString());
    }

    @Test
    public void rejectIfMandatoryFieldEmpty() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfMandatoryFieldEmpty(any(Errors.class), anyString());
        this.validator.rejectIfMandatoryFieldEmpty(mockErrors, TEST_FIELD_NAME);
        verify(this.validator, times(1)).rejectIfMandatoryFieldEmpty(any(Errors.class), anyString());
    }

    @Test
    public void rejectIfMandatoryFieldEmptyUsingAn() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfMandatoryFieldEmptyUsingAn(any(Errors.class), anyString());
        this.validator.rejectIfMandatoryFieldEmptyUsingAn(mockErrors, TEST_FIELD_NAME);
        verify(this.validator, times(1)).rejectIfMandatoryFieldEmptyUsingAn(any(Errors.class), anyString());
    }

    @Test
    public void rejectIfMandatorySelectFieldEmpty() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfMandatorySelectFieldEmpty(any(Errors.class), anyString());
        this.validator.rejectIfMandatorySelectFieldEmpty(mockErrors, TEST_FIELD_NAME);
        verify(this.validator, times(1)).rejectIfMandatorySelectFieldEmpty(any(Errors.class), anyString());
    }

    @Test
    public void rejectIfPenceAmountBelowMinimumValueShouldValidate() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString(), any(Object[].class), anyString());
        doCallRealMethod().when(this.validator).rejectIfPenceAmountBelowMinimumValue(any(Errors.class), anyString(), anyInt(), anyInt());
        this.validator.rejectIfPenceAmountBelowMinimumValue(mockErrors, TEST_FIELD_NAME, TEST_PENCE_HIGH_AMOUNT, TEST_PENCE_LOW_AMOUNT);
        verify(this.mockErrors, never()).rejectValue(anyString(), anyString(), any(Object[].class), anyString());
    }

    @Test
    public void rejectIfPenceAmountBelowMinimumValueShouldReject() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString(), any(Object[].class), anyString());
        doCallRealMethod().when(this.validator).rejectIfPenceAmountBelowMinimumValue(any(Errors.class), anyString(), anyInt(), anyInt());
        this.validator.rejectIfPenceAmountBelowMinimumValue(mockErrors, TEST_FIELD_NAME, TEST_PENCE_LOW_AMOUNT, TEST_PENCE_HIGH_AMOUNT);
        verify(this.mockErrors, times(1)).rejectValue(anyString(), anyString(), any(Object[].class), anyString());
    }

    @Test
    public void rejectIfPenceAmountAboveMaximumValueShouldValidate() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString(), any(Object[].class), anyString());
        doCallRealMethod().when(this.validator).rejectIfPenceAmountAboveMaximumValue(any(Errors.class), anyString(), anyInt(), anyInt());
        this.validator.rejectIfPenceAmountAboveMaximumValue(mockErrors, TEST_FIELD_NAME, TEST_PENCE_LOW_AMOUNT, TEST_PENCE_HIGH_AMOUNT);
        verify(this.mockErrors, never()).rejectValue(anyString(), anyString(), any(Object[].class), anyString());
    }

    @Test
    public void rejectIfPenceAmountAboveMaximumValueShouldReject() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString(), any(Object[].class), anyString());
        doCallRealMethod().when(this.validator).rejectIfPenceAmountAboveMaximumValue(any(Errors.class), anyString(), anyInt(), anyInt());
        this.validator.rejectIfPenceAmountAboveMaximumValue(mockErrors, TEST_FIELD_NAME, TEST_PENCE_HIGH_AMOUNT, TEST_PENCE_LOW_AMOUNT);
        verify(this.mockErrors, times(1)).rejectValue(anyString(), anyString(), any(Object[].class), anyString());
    }

    @Test
    public void rejectIfPenceAmountIsZero() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString(), any(Object[].class), anyString());
        doCallRealMethod().when(this.validator).rejectIfPenceAmountIsZero(any(Errors.class), anyString(), anyInt(), anyInt());
        this.validator.rejectIfPenceAmountIsZero(mockErrors, TEST_FIELD_NAME, TEST_PENCE_LOW_AMOUNT, TEST_PENCE_ZERO_AMOUNT);
        verify(this.mockErrors, times(1)).rejectValue(anyString(), anyString(), any(Object[].class), anyString());
    }

    @Test
    public void acceptIfPenceAmountIsNonZero() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString(), any(Object[].class), anyString());
        doCallRealMethod().when(this.validator).rejectIfPenceAmountIsZero(any(Errors.class), anyString(), anyInt(), anyInt());
        this.validator.rejectIfPenceAmountIsZero(mockErrors, TEST_FIELD_NAME, TEST_PENCE_LOW_AMOUNT, TEST_PENCE_HIGH_AMOUNT);
        verify(this.mockErrors, never()).rejectValue(anyString(), anyString());
    }

    @Test
    public void acceptIfBothAreZero() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString(), any(Object[].class), anyString());
        doCallRealMethod().when(this.validator).rejectIfPenceAmountIsZero(any(Errors.class), anyString(), anyInt(), anyInt());
        this.validator.rejectIfPenceAmountIsZero(mockErrors, TEST_FIELD_NAME, TEST_PENCE_ZERO_AMOUNT, TEST_PENCE_ZERO_AMOUNT);
        verify(this.mockErrors, never()).rejectValue(anyString(), anyString());
    }

    @Test
    public void patternMatchFalseIfBothNull() {
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        assertFalse(this.validator.isPatternMatched(null, null));
    }

    @Test
    public void patternMatchFalseIfPatternNull() {
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        assertFalse(this.validator.isPatternMatched(null, TEST_STRING_WITH_NUMERIC));
    }

    @Test
    public void patternMatchFalseIfValueNull() {
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        assertFalse(this.validator.isPatternMatched(TEST_STRING_WITH_NUMERIC, null));
    }

    @Test
    public void rejectIfNotShortDate() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotShortDate(any(Errors.class), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_NUMERIC);
        this.validator.rejectIfNotShortDate(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, times(1)).rejectValue(anyString(), anyString());
    }

    @Test
    public void acceptIfShortDate() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotShortDate(any(Errors.class), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_VALID_DATE_STRING);
        this.validator.rejectIfNotShortDate(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, never()).rejectValue(anyString(), anyString());
    }

    @Test
    public void acceptIfShortDateWithDate() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotShortDate(any(Errors.class), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_VALID_DATE);
        this.validator.rejectIfNotShortDate(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, never()).rejectValue(anyString(), anyString());
    }

    @Test
    public void rejectIfInvalidDate() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfInvalidDate(any(Errors.class), anyString(), anyString());
        this.validator.rejectIfInvalidDate(mockErrors, TEST_FIELD_NAME, INVALID_DATE_1);
        verify(this.mockErrors, times(1)).rejectValue(anyString(), anyString());
    }

    @Test
    public void doNotRejectIfValidDate() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfInvalidDate(any(Errors.class), anyString(), anyString());
        this.validator.rejectIfInvalidDate(mockErrors, TEST_FIELD_NAME, TEST_VALID_DATE_STRING);
        verify(this.mockErrors, never()).rejectValue(anyString(), anyString());
    }

    @Test
    public void rejectIfNotValidEmail() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_SYMBOLS);
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotValidEmail(any(Errors.class), anyString());
        this.validator.rejectIfNotValidEmail(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, times(1)).rejectValue(anyString(), anyString());
    }

    @Test
    public void acceptIfValidEmail() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_USER_NAME);
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotValidEmail(any(Errors.class), anyString());
        this.validator.rejectIfNotValidEmail(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, never()).rejectValue(anyString(), anyString());
    }

    @Test
    public void rejectIfNotValidPhone() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_SYMBOLS);
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotValidPhone(any(Errors.class), anyString());
        this.validator.rejectIfNotValidPhone(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, times(1)).rejectValue(anyString(), anyString());
    }

    @Test
    public void acceptIfValidPhone() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_PHONE_NUMBER);
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotValidUsername(any(Errors.class), anyString());
        this.validator.rejectIfNotValidUsername(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, never()).rejectValue(anyString(), anyString());
    }

    @Test
    public void rejectIfNotValidUsername() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_SYMBOLS);
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotValidUsername(any(Errors.class), anyString());
        this.validator.rejectIfNotValidUsername(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, times(1)).rejectValue(anyString(), anyString());
    }

    @Test
    public void acceptIfValidUsername() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_USER_NAME);
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotValidUsername(any(Errors.class), anyString());
        this.validator.rejectIfNotValidUsername(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, never()).rejectValue(anyString(), anyString());
    }

    @Test
    public void rejectIfNotDecimal() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_ALPHABETIC);
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotDecimal(any(Errors.class), anyString());
        this.validator.rejectIfNotDecimal(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, times(1)).rejectValue(anyString(), anyString());
    }

    @Test
    public void acceptIfDecimal() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_DECIMALS);
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotDecimal(any(Errors.class), anyString());
        this.validator.rejectIfNotDecimal(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, never()).rejectValue(anyString(), anyString());
    }

    @Test
    public void rejectIfNotNumeric() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_ALPHABETIC);
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotNumeric(any(Errors.class), anyString());
        this.validator.rejectIfNotNumeric(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, times(1)).rejectValue(anyString(), anyString());
    }

    @Test
    public void acceptIfNumeric() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_NUMERIC);
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotNumeric(any(Errors.class), anyString());
        this.validator.rejectIfNotNumeric(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, never()).rejectValue(anyString(), anyString());
    }

    @Test
    public void rejectIfNotAlphabetic() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_SYMBOLS);
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotAlphabetic(any(Errors.class), anyString());
        this.validator.rejectIfNotAlphabetic(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, times(1)).rejectValue(anyString(), anyString());
    }

    @Test
    public void acceptIfAlphabetic() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_ALPHABETIC);
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotAlphabetic(any(Errors.class), anyString());
        this.validator.rejectIfNotAlphabetic(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, never()).rejectValue(anyString(), anyString());
    }

    @Test
    public void rejectIfNotAlphabeticOrWhiteSpace() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_SYMBOLS);
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotAlphabeticOrWhiteSpace(any(Errors.class), anyString());
        this.validator.rejectIfNotAlphabeticOrWhiteSpace(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, times(1)).rejectValue(anyString(), anyString());
    }

    @Test
    public void acceptIfAlphabeticOrWhiteSpace() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_WHITE_SPACE_ALPHABETIC);
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotAlphabeticOrWhiteSpace(any(Errors.class), anyString());
        this.validator.rejectIfNotAlphabeticOrWhiteSpace(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, never()).rejectValue(anyString(), anyString());
    }

    @Test
    public void rejectIfNotAlphabeticWithWhiteSpaceBetweenWords() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_SYMBOLS);
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotAlphabeticWithWhiteSpaceBetweenWordsOnly(any(Errors.class), anyString());
        this.validator.rejectIfNotAlphabeticWithWhiteSpaceBetweenWordsOnly(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, times(1)).rejectValue(anyString(), anyString());
    }

    @Test
    public void acceptIfAlphabeticWithWhiteSpaceBetweenWords() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_WHITE_SPACE_ALPHABETIC);
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotAlphabeticWithWhiteSpaceBetweenWordsOnly(any(Errors.class), anyString());
        this.validator.rejectIfNotAlphabeticWithWhiteSpaceBetweenWordsOnly(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, never()).rejectValue(anyString(), anyString());
    }

    @Test
    public void rejectIfNotAlphaNumericWithWhiteSpaceBetweenWords() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_SYMBOLS);
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotAlphaNumericWithWhiteSpaceBetweenWordsOnly(any(Errors.class), anyString());
        this.validator.rejectIfNotAlphaNumericWithWhiteSpaceBetweenWordsOnly(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, times(1)).rejectValue(anyString(), anyString());
    }

    @Test
    public void acceptIfAlphaNumericWithWhiteSpaceBetweenWords() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_WHITE_SPACE_ALPHANUMERIC);
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotAlphaNumericWithWhiteSpaceBetweenWordsOnly(any(Errors.class), anyString());
        this.validator.rejectIfNotAlphaNumericWithWhiteSpaceBetweenWordsOnly(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, never()).rejectValue(anyString(), anyString());
    }

    @Test
    public void rejectIfNotAlphaNumericWithCurrencyWhiteSpaceBetweenWords() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_SYMBOLS);
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotAlphaNumericWithCurrencyWhiteSpaceBetweenWordsOnly(any(Errors.class), anyString());
        this.validator.rejectIfNotAlphaNumericWithCurrencyWhiteSpaceBetweenWordsOnly(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, times(1)).rejectValue(anyString(), anyString());
    }

    @Test
    public void acceptIfAlphaNumericWithCurrencyWhiteSpaceBetweenWords() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_CURRENCY_AND_WHITE_SPACE);
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotAlphaNumericWithCurrencyWhiteSpaceBetweenWordsOnly(any(Errors.class), anyString());
        this.validator.rejectIfNotAlphabeticWithWhiteSpaceBetweenWordsOnly(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, never()).rejectValue(anyString(), anyString());
    }

    @Test
    public void rejectIfNotAlphabeticWithHyphenWhiteSpaceBetweenWordsOnly() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_SYMBOLS);
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotAlphabeticWithHyphenWhiteSpaceBetweenWordsOnly(any(Errors.class), anyString());
        this.validator.rejectIfNotAlphabeticWithHyphenWhiteSpaceBetweenWordsOnly(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, times(1)).rejectValue(anyString(), anyString());
    }

    @Test
    public void acceptIfAlphabeticWithHyphenWhiteSpaceBetweenWords() {
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString());
        when(this.mockErrors.getFieldValue(anyString())).thenReturn(TEST_STRING_WITH_HYPHEN_AND_WHITE_SPACE);
        doCallRealMethod().when(this.validator).isPatternMatched(anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
        doCallRealMethod().when(this.validator).rejectIfNotAlphabeticWithHyphenWhiteSpaceBetweenWordsOnly(any(Errors.class), anyString());
        this.validator.rejectIfNotAlphabeticWithHyphenWhiteSpaceBetweenWordsOnly(mockErrors, TEST_FIELD_NAME);
        verify(this.mockErrors, never()).rejectValue(anyString(), anyString());
    }

}
