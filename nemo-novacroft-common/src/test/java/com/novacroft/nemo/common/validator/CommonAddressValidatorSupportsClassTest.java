package com.novacroft.nemo.common.validator;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.novacroft.cyber_source.web_service.model.transaction.Address;
import com.novacroft.nemo.common.transfer.AddressDTO;

@RunWith(Parameterized.class)
public class CommonAddressValidatorSupportsClassTest {
    
    private CommonAddressValidator validator;
    
    private Class<?> givenValue;
    private boolean expectedValue;

    @Before
    public void setUp() throws Exception {
        validator = new CommonAddressValidator();
    }
    
    public CommonAddressValidatorSupportsClassTest(Class<?> givenValue, boolean expectedValue) {
        this.givenValue = givenValue;
        this.expectedValue = expectedValue;
    }
    
    @Parameters(name = "{index}: test({0}) expected={1}")
    public static Collection<?> testParameters(){
        return Arrays.asList(new Object[][]{
                        {AddressDTO.class, true},
                        {Address.class, false}
        });
    }

    @Test
    public void supportsClass() {
        assertEquals(expectedValue, validator.supports(givenValue));
    }

}
