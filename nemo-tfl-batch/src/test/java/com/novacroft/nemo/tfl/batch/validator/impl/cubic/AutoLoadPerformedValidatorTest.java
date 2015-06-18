package com.novacroft.nemo.tfl.batch.validator.impl.cubic;

import com.novacroft.nemo.test_support.AutoLoadPerformedRecordTestUtil;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.tfl.batch.util.cubic.AutoLoadPerformedRecordUtil.UNKNOWN;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * AutoLoadPerformedValidator unit tests
 */
public class AutoLoadPerformedValidatorTest {
    @Test
    public void shouldSupportClass() {
        AutoLoadPerformedValidatorImpl validator = new AutoLoadPerformedValidatorImpl();
        assertTrue(validator.supports(String[].class));
    }

    @Test
    public void shouldNotSupportClass() {
        AutoLoadPerformedValidatorImpl validator = new AutoLoadPerformedValidatorImpl();
        assertFalse(validator.supports(Long.class));
    }

    @Test
    public void shouldValidate() {
        AutoLoadPerformedValidatorImpl validator = mock(AutoLoadPerformedValidatorImpl.class, CALLS_REAL_METHODS);
        String[] testTarget = AutoLoadPerformedRecordTestUtil.getAutoLoadPerformedRawTestRecord1();
        Errors errors = new BeanPropertyBindingResult(testTarget, "testTarget");
        validator.validate(testTarget, errors);

        verify(validator).validatePrestigeId(any(Errors.class), anyString());
        verify(validator).validatePickUpLocation(any(Errors.class), anyString());
        verify(validator).validateBusRouteId(any(Errors.class), anyString());
        verify(validator).validatePickUpTime(any(Errors.class), anyString());
        verify(validator).validateAutoLoadConfiguration(any(Errors.class), anyString());
        verify(validator).validateTopUpAmount(any(Errors.class), anyString());
        verify(validator).validateCurrency(any(Errors.class), anyString());
    }

    @Test
    public void isValidPickUpLocationShouldReturnTrueWithUnknown() {
        AutoLoadPerformedValidatorImpl validator = new AutoLoadPerformedValidatorImpl();
        assertTrue(validator.isValidPickUpLocation(UNKNOWN));
    }

    @Test
    public void isValidBusRouteIdShouldReturnTrueWithUnknown() {
        AutoLoadPerformedValidatorImpl validator = new AutoLoadPerformedValidatorImpl();
        assertTrue(validator.isValidBusRouteId(UNKNOWN));
    }

    @Test
    public void isValidAutoLoadConfigurationShouldReturnTrueWithUnknown() {
        AutoLoadPerformedValidatorImpl validator = new AutoLoadPerformedValidatorImpl();
        assertTrue(validator.isValidAutoLoadConfiguration(UNKNOWN));
    }

    @Test
    public void isValidCurrencyShouldReturnTrueWithUnknown() {
        AutoLoadPerformedValidatorImpl validator = new AutoLoadPerformedValidatorImpl();
        assertTrue(validator.isValidCurrency(UNKNOWN));
    }
}
