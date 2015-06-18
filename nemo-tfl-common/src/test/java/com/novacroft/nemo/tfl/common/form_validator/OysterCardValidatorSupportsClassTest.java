package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.novacroft.nemo.common.command.OysterCardCmd;
import com.novacroft.nemo.common.command.impl.CommonOrderCardCmd;

@RunWith(Parameterized.class)
public class OysterCardValidatorSupportsClassTest {
    
    private OysterCardValidator validator;
    
    private boolean expectedValue;
    private Class<?> givenValue;

    @Before
    public void setUp() throws Exception {
        validator = new OysterCardValidator();
    }
    
    @Parameters(name = "Supports : {index}")
    public static Collection<?> testParameters(){
        return Arrays.asList(new Object[][]{
                        {OysterCardCmd.class, true},
                        {CommonOrderCardCmd.class, false}                    
        });
    }
    
    public OysterCardValidatorSupportsClassTest(Class<?> givenValue, boolean expectedValue) {
        this.givenValue = givenValue;
        this.expectedValue = expectedValue;
    }

    @Test
    public void suuportsClass() {
        assertEquals(this.expectedValue, validator.supports(this.givenValue));
    }

}
