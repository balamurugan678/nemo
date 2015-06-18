package com.novacroft.nemo.tfl.batch.validator.impl.cubic;

import com.novacroft.nemo.test_support.CurrentActionRecordTestUtil;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * CurrentActionValidator unit tests
 */
public class CurrentActionValidatorTest {
    @Test
    public void shouldSupportClass() {
        CurrentActionValidatorImpl validator = new CurrentActionValidatorImpl();
        assertTrue(validator.supports(String[].class));
    }

    @Test
    public void shouldNotSupportClass() {
        CurrentActionValidatorImpl validator = new CurrentActionValidatorImpl();
        assertFalse(validator.supports(Long.class));
    }

    @Test
    public void shouldValidate() {
        CurrentActionValidatorImpl validator = mock(CurrentActionValidatorImpl.class, CALLS_REAL_METHODS);
        String[] testTarget = CurrentActionRecordTestUtil.getTestCurrentActionArray1();
        Errors errors = new BeanPropertyBindingResult(testTarget, "testTarget");
        validator.validate(testTarget, errors);
        verify(validator).validatePrestigeId(any(Errors.class), anyString());
        verify(validator).validateRequestSequenceNumber(any(Errors.class), anyString());
        verify(validator).validateProductCode(any(Errors.class), anyString());
        verify(validator).validateFarePaid(any(Errors.class), anyString());
        verify(validator).validateCurrency(any(Errors.class), anyString());
        verify(validator).validatePaymentMethodCode(any(Errors.class), anyString());
        verify(validator).validatePrePayValue(any(Errors.class), anyString());
        verify(validator).validatePickUpLocation(any(Errors.class), anyString());
        verify(validator).validatePptStartDate(any(Errors.class), anyString());
        verify(validator).validatePptExpiryDate(any(Errors.class), anyString());
        verify(validator).validateAutoLoadState(any(Errors.class), anyString());
        verify(validator).validateAutoLoadState(any(Errors.class), anyString());
    }
}
