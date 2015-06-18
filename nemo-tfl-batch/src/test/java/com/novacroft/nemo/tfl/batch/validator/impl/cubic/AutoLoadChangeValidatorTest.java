package com.novacroft.nemo.tfl.batch.validator.impl.cubic;

import com.novacroft.nemo.test_support.AutoLoadChangeRecordTestUtil;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * AutoLoadChangeValidator unit tests
 */
public class AutoLoadChangeValidatorTest {
    @Test
    public void shouldSupportClass() {
        AutoLoadChangeValidatorImpl validator = new AutoLoadChangeValidatorImpl();
        assertTrue(validator.supports(String[].class));
    }

    @Test
    public void shouldNotSupportClass() {
        AutoLoadChangeValidatorImpl validator = new AutoLoadChangeValidatorImpl();
        assertFalse(validator.supports(Long.class));
    }

    @Test
    public void shouldValidate() {
        AutoLoadChangeValidatorImpl validator = mock(AutoLoadChangeValidatorImpl.class, CALLS_REAL_METHODS);
        String[] testTarget = AutoLoadChangeRecordTestUtil.getAutoLoadChangeRawTestRecord1();
        Errors errors = new BeanPropertyBindingResult(testTarget, "testTarget");
        validator.validate(testTarget, errors);

        verify(validator).validatePrestigeId(any(Errors.class), anyString());
        verify(validator).validatePickUpLocation(any(Errors.class), anyString());
        verify(validator).validatePickUpTime(any(Errors.class), anyString());
        verify(validator).validateRequestSequenceNumber(any(Errors.class), anyString());
        verify(validator, times(2)).validateAutoLoadConfiguration(any(Errors.class), anyString());
        verify(validator).validateActionStatus(any(Errors.class), anyString());
        verify(validator).validateFailureReasonCode(any(Errors.class), anyString());
    }
}
