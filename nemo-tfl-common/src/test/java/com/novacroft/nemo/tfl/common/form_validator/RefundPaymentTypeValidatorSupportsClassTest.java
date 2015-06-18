package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.novacroft.nemo.common.command.impl.CommonOrderCardCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;

@RunWith(Parameterized.class)
public class RefundPaymentTypeValidatorSupportsClassTest {

    private RefundPaymentTypeValidator validator;
    private boolean expectedValue;
    private Class<?> givenValue;

    @Before
    public void setUp() throws Exception {
        validator = new RefundPaymentTypeValidator();
    }
    
    public RefundPaymentTypeValidatorSupportsClassTest(Class<?> givenValue, boolean expectedValue){
        this.givenValue = givenValue;
        this.expectedValue = expectedValue;
    }
    
    @Parameters(name = "Supports: {index}")
    public static Collection<?> testParameters(){
        return Arrays.asList(new Object[][]{
                        {CartCmdImpl.class , true},
                        {CommonOrderCardCmd.class, false}
        });
    }
    
    @Test
    public void supportsClass() {
        assertEquals(this.expectedValue, validator.supports(this.givenValue));
    }

}
