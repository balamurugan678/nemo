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
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;

@RunWith(Parameterized.class)
public class TravelCardRefundValidatorTestSupportClass {

    private TravelCardRefundValidator travelCardRefundValidator;

    private boolean expectedParameter;
    private Class<?> valueParameter;

    public TravelCardRefundValidatorTestSupportClass(Class<?> valueParameter, boolean expectedParameter) {
        this.valueParameter = valueParameter;
        this.expectedParameter = expectedParameter;
    }

    @Parameterized.Parameters(name = "{index}: test({0}) expected={1}")
    public static Collection<?> testParameters() {
        return Arrays.asList(new Object[][] { { CartCmdImpl.class, false }, { BusPassCmd.class, false }, { CartItemCmdImpl.class, true } });
    }

    @Before
    public void setUp() throws Exception {
        travelCardRefundValidator = new TravelCardRefundValidator();
    }

    @Test
    public void supportsClass() {
        assertEquals(this.expectedParameter, travelCardRefundValidator.supports(valueParameter));
    }

}
