package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.novacroft.nemo.tfl.common.command.NewPasswordCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;

@RunWith(Parameterized.class)
public class NewPasswordValidatorSupportsClassTest {
    
    private NewPasswordValidator validator;
    
    private Class<?> givenValue;
    private boolean expectedValue;

    @Before
    public void setUp() throws Exception {
        validator = new NewPasswordValidator();
    }
    
    @Parameters(name="{index}: test({0}) expected={1}")
    public static Collection<?> testParameters(){
        return Arrays.asList(new Object[][]{
                        {NewPasswordCmd.class, true},
                        {CartItemCmdImpl.class, false}
        });
    }
    
    public NewPasswordValidatorSupportsClassTest(Class<?> givenValue, boolean expectedValue) {
        this.givenValue = givenValue;
        this.expectedValue = expectedValue;
    }

    @Test
    public void supportsClass() {
        assertEquals(this.expectedValue, validator.supports(givenValue));
    }

}
