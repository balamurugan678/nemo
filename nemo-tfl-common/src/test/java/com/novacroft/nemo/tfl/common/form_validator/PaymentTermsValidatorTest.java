package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.common.command.OysterCardCmd;
import com.novacroft.nemo.tfl.common.command.PaymentTermsCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.form_validator.PaymentTermsValidator;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Payment terms and conditions unit tests
 */
public class PaymentTermsValidatorTest {
    private PaymentTermsValidator validator;
    private CartCmdImpl cmd;

    @Before
    public void setUp() {
        validator = new PaymentTermsValidator();
        cmd = new CartCmdImpl();
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(PaymentTermsCmd.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(OysterCardCmd.class));
    }

    @Test
    public void shouldValidate() {
        cmd.setPaymentTermsAccepted(true);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithPaymentTermsNotAccepted() {
        cmd.setPaymentTermsAccepted(false);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

}
