package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.tfl.common.command.CancelAndSurrenderCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;

public class CancelAndSurrenderAjaxValidatorTest {
    
    private CancelAndSurrenderAjaxValidator validator;
    private CartCmdImpl cmd;
    private Errors errors;

    @Before
    public void setUp() throws Exception {
        validator = new CancelAndSurrenderAjaxValidator();
        cmd = new CartCmdImpl();
        errors = new BeanPropertyBindingResult(cmd, "cmd");
    }

    @Test
    public void suuportsClass() {
        assertTrue(validator.supports(CancelAndSurrenderCmd.class));
    }

    @Test
    public void doesNotSupportClass(){
        assertFalse(validator.supports(Errors.class));
    }
}
