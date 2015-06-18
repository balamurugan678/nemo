package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.test_support.AutoTopUpTestUtil;
import com.novacroft.nemo.tfl.common.command.impl.ManageCardCmd;

public class AutoTopUpValidatorTest {
    
    private AutoTopUpValidator validator;
    private ManageCardCmd cmd;
    private Errors errors;

    @Before
    public void setUp() throws Exception {
        validator = new AutoTopUpValidator();
        cmd = new ManageCardCmd();
        errors = new BeanPropertyBindingResult(cmd, "cmd");
    }

    @Test
    public void supportsClass() {
       assertTrue(validator.supports(ManageCardCmd.class));
    }
    
    @Test
    public void doesNotSupportClass() {
       assertFalse(validator.supports(Errors.class));
    }
    
    @Test
    public void shouldValidate(){
        cmd.setAutoTopUpState(AutoTopUpTestUtil.AUTO_TOP_UP_AMOUNT_1);
        cmd.setPaymentCardID(AutoTopUpTestUtil.CARD_ID_1);
        validator.validate(cmd, errors);;
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateIfAutoTopUpStateIsGreatThanZeroAndPaymentCardIdIsNull(){
        cmd.setAutoTopUpState(AutoTopUpTestUtil.AUTO_TOP_UP_AMOUNT_1);
        cmd.setPaymentCardID(null);
        validator.validate(cmd, errors);;
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldValidateIfAutoTopUpStateWithoutPendingAmountForExistingOysterCard(){
        cmd.setAutoTopUpStateExistingPendingAmount(AutoTopUpTestUtil.AUTO_TOP_UP_AMOUNT_1);
        validator.validateAutoTopUpStateExistingPendingAmountForExistingOysterCard(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateAutoTopUpStateAmountIsValidAmount(){
    	cmd.setAutoTopUpState(0);
        cmd.setAutoTopUpStateExistingPendingAmount(AutoTopUpTestUtil.AUTO_TOP_UP_AMOUNT_1);
        validator.validateAutoTopUpStateAmountIsValidAmount(cmd, errors);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldValidateIfAutoTopUpStateWithPendingAmountForExistingOysterCard() {
        cmd.setAutoTopUpStateExistingPendingAmount(AutoTopUpTestUtil.AUTO_TOP_UP_AMOUNT_1);
        cmd.setAutoTopUpState(AutoTopUpTestUtil.AUTO_TOP_UP_AMOUNT_1);
        validator.validateAutoTopUpStateExistingPendingAmountForExistingOysterCard(cmd, errors);
        assertTrue(errors.hasErrors());
    }
}
