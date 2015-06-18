package com.novacroft.nemo.tfl.batch.validator.impl.cubic;

import com.novacroft.nemo.test_support.AdHocDistributionRecordTestUtil;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * AdHocDistributionValidator unit tests
 */
public class AdHocDistributionValidatorTest {
    @Test
    public void shouldSupportClass() {
        AdHocDistributionValidatorImpl validator = new AdHocDistributionValidatorImpl();
        assertTrue(validator.supports(String[].class));
    }

    @Test
    public void shouldNotSupportClass() {
        AdHocDistributionValidatorImpl validator = new AdHocDistributionValidatorImpl();
        assertFalse(validator.supports(Long.class));
    }

    @Test
    public void shouldValidate() {
        AdHocDistributionValidatorImpl validator = mock(AdHocDistributionValidatorImpl.class, CALLS_REAL_METHODS);
        String[] testTarget = AdHocDistributionRecordTestUtil.getAdHocDistributionRawTestRecord1();
        Errors errors = new BeanPropertyBindingResult(testTarget, "testTarget");
        validator.validate(testTarget, errors);

        verify(validator).validatePrestigeId(any(Errors.class), anyString());
        verify(validator).validatePickUpLocation(any(Errors.class), anyString());
        verify(validator).validatePickUpTime(any(Errors.class), anyString());
        verify(validator).validateRequestSequenceNumber(any(Errors.class), anyString());
        verify(validator).validateProductCode(any(Errors.class), anyString());
        verify(validator).validatePptStartDate(any(Errors.class), anyString());
        verify(validator).validatePptExpiryDate(any(Errors.class), anyString());
        verify(validator).validatePrePayValue(any(Errors.class), anyString());
        verify(validator).validateCurrency(any(Errors.class), anyString());
        verify(validator).validateActionStatus(any(Errors.class), anyString());
        verify(validator).validateFailureReasonCode(any(Errors.class), anyString());
    }
}
