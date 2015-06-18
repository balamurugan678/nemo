package com.novacroft.nemo.tfl.batch.validator.impl;

import com.novacroft.nemo.common.constant.DateConstant;
import com.novacroft.nemo.test_support.ChequeSettlementTestUtil;
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
 * DataTypeBaseValidator <code>isValidDate</code> unit tests
 */
@RunWith(Parameterized.class)
public class BaseDataTypeValidatorDateTest {
    private BaseDataTypeValidator validator;

    private String valueTestParameter;
    private String patternTestParameter;
    private boolean isValidTestParameter;

    public BaseDataTypeValidatorDateTest(String valueParameter, String patternParameter, boolean isValidParameter) {
        this.valueTestParameter = valueParameter;
        this.patternTestParameter = patternParameter;
        this.isValidTestParameter = isValidParameter;
    }

    @Parameterized.Parameters
    public static Collection validateTestParameters() {
        return Arrays.asList(new Object[][]{
                {ChequeSettlementTestUtil.AUG_19_FSC_FORMAT_STRING, DateConstant.SHORT_DATE_PATTERN, true},
                {"99/08/2013", DateConstant.SHORT_DATE_PATTERN, false},
                {"19-AUG-2013", DateConstant.SHORT_DATE_PATTERN, false}});
    }

    @Before
    public void setUp() {
        this.validator = mock(BaseDataTypeValidator.class, CALLS_REAL_METHODS);
    }

    @Test
    public void isValidIntegerTest() {
        assertEquals(this.isValidTestParameter, this.validator.isValidDate(this.valueTestParameter, this.patternTestParameter));
    }
}
