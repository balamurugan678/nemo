package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.novacroft.nemo.tfl.common.command.TravelCardCmd;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

@RunWith(Parameterized.class)
public class CommonCustomerRegistrationValidatorSupportsClassTest {
    
    private CommonCustomerRegistrationValidator validator;
    
    private boolean expectedParameter;
    private Class<?> givenParameter;
    
    public CommonCustomerRegistrationValidatorSupportsClassTest(Class<?> givenParameter, boolean expectedParameter) {
        this.givenParameter = givenParameter;
        this.expectedParameter = expectedParameter;
    }
    
    @Parameterized.Parameters(name = "{index}: test({0}) expected={1}")
    public static Collection<?> testParameters() {
        return Arrays.asList(new Object[][] { { CustomerDTO.class, true }, { TravelCardCmd.class, false } });
    }


    @Before
    public void setUp() throws Exception {
        validator = new CommonCustomerRegistrationValidator();
    }

    @Test
    public void test() {
        assertEquals(this.expectedParameter, validator.supports(givenParameter));
    }

}
