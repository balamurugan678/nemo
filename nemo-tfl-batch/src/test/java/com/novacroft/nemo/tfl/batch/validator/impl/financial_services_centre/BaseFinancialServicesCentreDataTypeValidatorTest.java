package com.novacroft.nemo.tfl.batch.validator.impl.financial_services_centre;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

@RunWith(Parameterized.class)
public class BaseFinancialServicesCentreDataTypeValidatorTest {
    private BaseFinancialServicesCentreDataTypeValidator validator;

    private String valueParameter;
    private boolean isValidParameter;

    public BaseFinancialServicesCentreDataTypeValidatorTest(String valueParameter, boolean isValidParameter) {
        this.valueParameter = valueParameter;
        this.isValidParameter = isValidParameter;
    }

    @Parameterized.Parameters
    public static Collection validateTestParameters() {
        return Arrays.asList(new Object[][]{{"9876.78", true}, {"12.34", true}, {"1.2", true}, {"1.", true}, {".1", true},
                {"-12.34", true}, {"12345.67", false}, {"1234.567", false}, {"1234", false}, {"ABC.D", false}});

    }

    @Before
    public void setUp() {
        this.validator = mock(BaseFinancialServicesCentreDataTypeValidator.class, CALLS_REAL_METHODS);
    }

    @Test
    public void isValidAmountShouldReturnTrueForGoodAmount() {
        assertEquals(this.isValidParameter, this.validator.isValidMonetaryValue(this.valueParameter));
    }
}