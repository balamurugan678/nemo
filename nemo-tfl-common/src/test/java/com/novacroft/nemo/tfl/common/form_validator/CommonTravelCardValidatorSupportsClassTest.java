package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.novacroft.nemo.tfl.common.command.TravelCardCmd;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

@RunWith(Parameterized.class)
public class CommonTravelCardValidatorSupportsClassTest {

    private CommonTravelCardValidator validator;

    private boolean expectedParameter;
    private Class<?> valueParameter;

    public CommonTravelCardValidatorSupportsClassTest(Class<?> valueParameter, boolean expectedParameter) {
        this.valueParameter = valueParameter;
        this.expectedParameter = expectedParameter;
    }

    @Parameterized.Parameters(name = "{index}: test({0}) expected={1}")
    public static Collection<?> testParameters() {
        return Arrays.asList(new Object[][] { { ProductItemDTO.class, true }, { TravelCardCmd.class, false } });
    }

    @Before
    public void setUp() throws Exception {
        validator = new CommonTravelCardValidator();
    }

    @Test
    public void supportsClass() {
        assertEquals(this.expectedParameter, validator.supports(valueParameter));
    }

}
