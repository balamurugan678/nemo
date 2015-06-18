package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.novacroft.nemo.common.command.impl.CommonLoginCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowEditCmd;

@RunWith(Parameterized.class)
public class WorkflowItemEditValidatorSupportsClassTest {
    private WorkflowItemEditValidator validator;
    private boolean expectedParameter;
    private Class<?> valueParameter;

    @Before
    public void setUp() throws Exception {
        validator = new WorkflowItemEditValidator();
    }
    
    public WorkflowItemEditValidatorSupportsClassTest(Class<?> givenValue, boolean expectedValue) {
        this.valueParameter = givenValue;
        this.expectedParameter = expectedValue;
    }

    @Parameters(name = "{index}: test({0}) expected={1}")
    public static Collection<?> testParameters() {
        return Arrays.asList(new Object[][] { { WorkflowEditCmd.class, true }, { CommonLoginCmd.class, false } });
    }

    @Test
    public void suuports() {
        assertEquals(this.expectedParameter, validator.supports(valueParameter));
    }


}
