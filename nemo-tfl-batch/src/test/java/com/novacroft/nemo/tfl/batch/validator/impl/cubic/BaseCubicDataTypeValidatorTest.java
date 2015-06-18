package com.novacroft.nemo.tfl.batch.validator.impl.cubic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

public class BaseCubicDataTypeValidatorTest {
    private BaseCubicDataTypeValidator validator;

    @Before
    public void setUp() {
        this.validator = mock(BaseCubicDataTypeValidator.class, CALLS_REAL_METHODS);
    }

    @Test
    public void isValidDateAndTimeShouldReturnTrueForDateAndTime() {
        assertTrue(this.validator.isValidDateAndTime("23-APR-1976 15:15:15"));
    }

    @Test
    public void isValidDateAndTimeShouldReturnFalseForInteger() {
        assertFalse(this.validator.isValidDateAndTime("12345678"));
    }

    @Test
    public void isValidDateShouldReturnTrueForDate() {
        assertTrue(this.validator.isValidDate("23-APR-1976"));
    }

    @Test
    public void isValidDateShouldReturnFalseForInteger() {
        assertFalse(this.validator.isValidDate("12345678"));
    }
}