package com.novacroft.nemo.tfl.batch.validator.impl.cubic;

import com.novacroft.nemo.tfl.batch.constant.cubic.CubicActionStatus;
import com.novacroft.nemo.tfl.common.constant.AutoLoadState;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

/**
 * FieldBaseValidator unit tests
 */
public class BaseCubicFieldValidatorTest {
    @Test
    public void isValidPrestigeIdShouldReturnFalseWithBlankValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertFalse(validator.isValidPrestigeId(""));
    }

    @Test
    public void isValidPrestigeIdShouldReturnFalseWithNonIntegerValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertFalse(validator.isValidPrestigeId("X"));
    }

    @Test
    public void isValidPrestigeIdShouldReturnTrueWithIntegerValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidPrestigeId("99"));
    }

    @Test
    public void isValidRequestSequenceNumberShouldReturnFalseWithBlankValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertFalse(validator.isValidRequestSequenceNumber(""));
    }

    @Test
    public void isValidRequestSequenceNumberShouldReturnFalseWithNonIntegerValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertFalse(validator.isValidRequestSequenceNumber("X"));
    }

    @Test
    public void isValidRequestSequenceNumberShouldReturnTrueWithIntegerValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidRequestSequenceNumber("99"));
    }

    @Test
    public void isValidProductCodeShouldReturnTrueWithBlankValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidProductCode(""));
    }

    @Test
    public void isValidProductCodeShouldReturnFalseWithNonIntegerValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertFalse(validator.isValidProductCode("X"));
    }

    @Test
    public void isValidProductCodeShouldReturnTrueWithIntegerValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidProductCode("99"));
    }

    @Test
    public void isValidFarePaidShouldReturnTrueWithBlankValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidFarePaid(""));
    }

    @Test
    public void isValidFarePaidShouldReturnFalseWithNonIntegerValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertFalse(validator.isValidFarePaid("X"));
    }

    @Test
    public void isValidFarePaidShouldReturnTrueWithIntegerValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidFarePaid("99"));
    }

    @Test
    public void isValidCurrencyShouldReturnTrueWithBlankValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidCurrency(""));
    }

    @Test
    public void isValidCurrencyShouldReturnFalseWithNonIntegerValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertFalse(validator.isValidCurrency("X"));
    }

    @Test
    public void isValidCurrencyShouldReturnTrueWithIntegerValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidCurrency("99"));
    }

    @Test
    public void isValidPaymentMethodCodeShouldReturnTrueWithBlankValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidPaymentMethodCode(""));
    }

    @Test
    public void isValidPaymentMethodCodeShouldReturnFalseWithNonIntegerValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertFalse(validator.isValidPaymentMethodCode("X"));
    }

    @Test
    public void isValidPaymentMethodCodeShouldReturnTrueWithIntegerValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidPaymentMethodCode("99"));
    }

    @Test
    public void isValidPrePayValueShouldReturnTrueWithBlankValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidPrePayValue(""));
    }

    @Test
    public void isValidPrePayValueShouldReturnFalseWithNonIntegerValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertFalse(validator.isValidPrePayValue("X"));
    }

    @Test
    public void isValidPrePayValueShouldReturnTrueWithIntegerValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidPrePayValue("99"));
    }

    @Test
    public void isValidPickUpLocationShouldReturnTrueWithBlankValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidPickUpLocation(""));
    }

    @Test
    public void isValidPickUpLocationShouldReturnFalseWithNonIntegerValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertFalse(validator.isValidPickUpLocation("X"));
    }

    @Test
    public void isValidPickUpLocationShouldReturnTrueWithIntegerValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidPickUpLocation("99"));
    }

    @Test
    public void isValidAutoLoadStateShouldReturnTrueWithBlankValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidAutoLoadState(""));
    }

    @Test
    public void isValidAutoLoadStateShouldReturnFalseWithNonIntegerValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertFalse(validator.isValidAutoLoadState("X"));
    }

    @Test
    public void isValidAutoLoadStateShouldReturnTrueWithIntegerValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidAutoLoadState("99"));
    }

    @Test
    public void isValidPptStartDateShouldReturnTrueWithBlankValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidPptStartDate(""));
    }

    @Test
    public void isValidPptStartDateShouldReturnFalseWithNonDateValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertFalse(validator.isValidPptStartDate("X"));
    }

    @Test
    public void isValidPptStartDateShouldReturnTrueWithDateValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidPptStartDate("20-AUG-2013"));
    }

    @Test
    public void isValidPptExpiryDateShouldReturnTrueWithBlankValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidPptExpiryDate(""));
    }

    @Test
    public void isValidPptExpiryDateShouldReturnFalseWithNonDateValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertFalse(validator.isValidPptExpiryDate("X"));
    }

    @Test
    public void isValidPptExpiryDateShouldReturnTrueWithDateValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidPptExpiryDate("20-AUG-2013"));
    }

    @Test
    public void isValidPickUpTimeShouldReturnTrueWithBlankValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidPickUpTime(""));
    }

    @Test
    public void isValidPickUpTimeShouldReturnTrueWithDateAndTimeValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidPickUpTime("20-AUG-2013 12:30:30"));
    }

    @Test
    public void isValidPickUpTimeShouldReturnFalseWithNonDateAndTimeValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertFalse(validator.isValidPickUpTime("X"));
    }

    @Test
    public void isValidActionStatusShouldReturnTrueWithBlankValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidActionStatus(""));
    }

    @Test
    public void isValidActionStatusShouldReturnTrueWithValidStatus() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        for (CubicActionStatus status : CubicActionStatus.values()) {
            assertTrue(validator.isValidActionStatus(status.name()));
        }
    }

    @Test
    public void isValidActionStatusShouldReturnFalseWithInValidStatus() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertFalse(validator.isValidActionStatus("X"));
    }

    @Test
    public void isValidAutoLoadConfigurationShouldReturnTrueWithBlankValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidAutoLoadConfiguration(""));
    }

    @Test
    public void isValidAutoLoadConfigurationShouldReturnTrueWithValidValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        for (String state : AutoLoadState.getStates()) {
            assertTrue(validator.isValidAutoLoadConfiguration(state));
        }
    }

    @Test
    public void isValidAutoLoadConfigurationShouldReturnFalseWithInValidValue() {
        String[] invalidValues = {"x", "0", "5"};
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        for (int i = 0; i < invalidValues.length; i++) {
            assertFalse(validator.isValidAutoLoadConfiguration(invalidValues[i]));
        }
    }

    @Test
    public void isValidFailureReasonCodeShouldReturnTrueWithBlankValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidFailureReasonCode(""));
    }

    @Test
    public void isValidFailureReasonCodeShouldReturnFalseWithNonIntegerValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertFalse(validator.isValidFailureReasonCode("X"));
    }

    @Test
    public void isValidFailureReasonCodeShouldReturnTrueWithIntegerValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidFailureReasonCode("99"));
    }

    @Test
    public void isValidBusRouteIdShouldReturnTrue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidBusRouteId("ABC"));
    }

    @Test
    public void isValidTopUpAmountShouldReturnFalseWithBlankValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertFalse(validator.isValidTopUpAmount(""));
    }

    @Test
    public void isValidTopUpAmountShouldReturnFalseWithNonIntegerValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertFalse(validator.isValidTopUpAmount("X"));
    }

    @Test
    public void isValidTopUpAmountShouldReturnTrueWithIntegerValue() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        assertTrue(validator.isValidTopUpAmount("99"));
    }

    @Test
    public void validatePrestigeIdShouldValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validatePrestigeId(errors, "99");
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validatePrestigeIdShouldNotValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validatePrestigeId(errors, "X");
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validateRequestSequenceNumberShouldValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validateRequestSequenceNumber(errors, "99");
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validateRequestSequenceNumberShouldNotValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validateRequestSequenceNumber(errors, "X");
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validateProductCodeShouldValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validateProductCode(errors, "99");
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validateProductCodeShouldNotValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validateProductCode(errors, "X");
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validateFarePaidShouldValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validateFarePaid(errors, "99");
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validateFarePaidShouldNotValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validateFarePaid(errors, "X");
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validateCurrencyShouldValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validateCurrency(errors, "99");
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validateCurrencyShouldNotValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validateCurrency(errors, "X");
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validatePaymentMethodCodeShouldValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validatePaymentMethodCode(errors, "99");
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validatePaymentMethodCodeShouldNotValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validatePaymentMethodCode(errors, "X");
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validatePrePayValueShouldValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validatePrePayValue(errors, "99");
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validatePrePayValueShouldNotValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validatePrePayValue(errors, "X");
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validatePickUpLocationShouldValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validatePickUpLocation(errors, "99");
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validatePickUpLocationShouldNotValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validatePickUpLocation(errors, "X");
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validateAutoLoadStateShouldValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validateAutoLoadState(errors, "99");
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validateAutoLoadStateShouldNotValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validateAutoLoadState(errors, "X");
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validatePptStartDateShouldValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validatePptStartDate(errors, "20-AUG-2013");
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validatePptStartDateShouldNotValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validatePptStartDate(errors, "X");
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validatePptExpiryDateShouldValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validatePptExpiryDate(errors, "20-AUG-2013");
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validatePptExpiryDateShouldNotValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validatePptExpiryDate(errors, "X");
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validateAutoLoadConfigurationShouldValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validateAutoLoadConfiguration(errors, AutoLoadState.getStates().get(0));
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validateAutoLoadConfigurationShouldNotValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validateAutoLoadConfiguration(errors, "X");
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validatePickUpTimeShouldValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validatePickUpTime(errors, "20-AUG-2013 12:30:30");
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validatePickUpTimeShouldNotValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validatePickUpTime(errors, "X");
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validateActionStatusShouldValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validateActionStatus(errors, CubicActionStatus.FAILED.name());
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validateActionStatusShouldNotValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validateActionStatus(errors, "X");
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validateFailureReasonCodeShouldValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validateFailureReasonCode(errors, "0");
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validateFailureReasonCodeShouldNotValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validateFailureReasonCode(errors, "X");
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validateBusRouteIdShouldValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validateBusRouteId(errors, "0");
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validateTopUpAmountShouldValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validateTopUpAmount(errors, "0");
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validateTopUpAmountShouldNotValidate() {
        BaseCubicFieldValidator validator = mock(BaseCubicFieldValidator.class, CALLS_REAL_METHODS);
        Errors errors = new BeanPropertyBindingResult(new Object(), "test");
        validator.validateTopUpAmount(errors, "X");
        assertTrue(errors.hasErrors());
    }
}