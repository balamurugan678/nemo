package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.novacroft.nemo.tfl.common.command.BusPassCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;

@RunWith(Parameterized.class)
public class RefundPayAsYouGoItemValidatorSupportsClassTest {

    private RefundPayAsYouGoItemValidator validator;

    private boolean expectedParameter;
    private Class<?> valueParameter;

    @Before
    public void setUp() throws Exception {
        validator = new RefundPayAsYouGoItemValidator();
    }

    @Parameterized.Parameters(name = "{index}: test({0}) expected={1}")
    public static Collection<?> testParameters() {
        return Arrays.asList(new Object[][] {{ BusPassCmd.class, false }, { CartCmdImpl.class, true } });
    }

    public RefundPayAsYouGoItemValidatorSupportsClassTest(Class<?> valueParameter, boolean expectedParameter) {
        this.valueParameter = valueParameter;
        this.expectedParameter = expectedParameter;
    }

    @Test
    public void test() {
        assertEquals(this.expectedParameter, validator.supports(valueParameter));
    }

}
