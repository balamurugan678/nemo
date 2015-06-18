package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.test_support.PaymentCardTestUtil;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.ManageCardCmd;

public class ChoosePaymentCardValidatorTest {
    private ChoosePaymentCardValidator validator;
    private ManageCardCmd manageCardCmd;
	
    @Before
    public void setUp() {
        validator = new ChoosePaymentCardValidator();
        manageCardCmd = new ManageCardCmd();

    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(ManageCardCmd.class));
        assertTrue(validator.supports(manageCardCmd.getClass()));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(CartCmdImpl.class));
    }

    @Test
    public void shouldValidateWithPaymentCardId() {
    	manageCardCmd.setPaymentCardID(PaymentCardTestUtil.TEST_PAYMENT_CARD_ID_1);
        Errors errors = new BeanPropertyBindingResult(manageCardCmd, "paymentCardID");
        validator.validate(manageCardCmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithoutPaymentCardId() {
        Errors errors = new BeanPropertyBindingResult(manageCardCmd, "cmd");
        validator.validate(manageCardCmd, errors);
        assertTrue(errors.hasErrors());
    }
}