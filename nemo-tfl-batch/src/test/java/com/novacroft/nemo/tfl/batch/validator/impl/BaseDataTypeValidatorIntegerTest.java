package com.novacroft.nemo.tfl.batch.validator.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

/**
 * DataTypeBaseValidator <code>isValidInteger</code> unit tests
 */
@RunWith(Parameterized.class)
public class BaseDataTypeValidatorIntegerTest {
    private BaseDataTypeValidator validator;

    private String valueTestParameter;
    private boolean isValidTestParameter;

    public BaseDataTypeValidatorIntegerTest(String valueParameter, boolean isValidParameter) {
        this.valueTestParameter = valueParameter;
        this.isValidTestParameter = isValidParameter;
    }

    @Parameterized.Parameters
    public static Collection validateTestParameters() {
        return Arrays.asList(new Object[][]{{"128", true}, {"256.64", false}, {"ABC", false}});

    }

    @Before
    public void setUp() {
        this.validator = mock(BaseDataTypeValidator.class, CALLS_REAL_METHODS);
    }

    @Test
    public void isValidIntegerTest() {
        assertEquals(this.isValidTestParameter, this.validator.isValidInteger(this.valueTestParameter));
    }
}
