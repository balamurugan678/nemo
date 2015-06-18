package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.tfl.common.command.PayAsYouGoCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PaymentType;

public class PaymentMethodValidatorTest {
	
	private PaymentMethodValidator validator;
	private CartCmdImpl cmd;

	@Before
    public void setUp() {
        validator = new PaymentMethodValidator();
        cmd = new CartCmdImpl();
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(CartCmdImpl.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(PayAsYouGoCmd.class));
    }

    @Test
    public void shouldValidate() {
        cmd.setPaymentType(PaymentType.WEB_ACCOUNT_CREDIT.code());
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNullHotlistReasonId() {
    	cmd.setPaymentType(null);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

}
