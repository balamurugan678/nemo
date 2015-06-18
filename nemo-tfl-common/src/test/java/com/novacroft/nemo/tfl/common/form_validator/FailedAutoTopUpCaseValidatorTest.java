package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.FailedAutoTopUpCaseTestUtil.NULL_FAILED_AUTO_TOPUP_AMOUNT;
import static com.novacroft.nemo.test_support.FailedAutoTopUpCaseTestUtil.FAILED_AUTO_TOPUP_AMOUNT;
import static com.novacroft.nemo.test_support.FailedAutoTopUpCaseTestUtil.RESETTLEMENT_PERIOD_GREATER_THAN_LIMIT;
import static com.novacroft.nemo.test_support.FailedAutoTopUpCaseTestUtil.RESETTLEMENT_PERIOD_LESS_THAN_LIMIT;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_RESETTLEMENT_PERIOD;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.FailedAutoTopUpCaseCmdImpl;

/**
 * TicketTypeValidator unit tests
 */
public class FailedAutoTopUpCaseValidatorTest {
    private FailedAutoTopUpCaseValidator validator;
    private FailedAutoTopUpCaseCmdImpl cmd;

    @Before
    public void setUp() {
        validator = new FailedAutoTopUpCaseValidator();
        cmd = new FailedAutoTopUpCaseCmdImpl();
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(FailedAutoTopUpCaseCmdImpl.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(CartItemCmdImpl.class));
    }

    @Test
    public void shouldValidate() {
        cmd.setFailedAutoTopUpAmount(FAILED_AUTO_TOPUP_AMOUNT);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNullFailedAutoTopUpAmount() {
        cmd.setFailedAutoTopUpAmount(NULL_FAILED_AUTO_TOPUP_AMOUNT);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotThrowErrorWhenResettlementPeriodIsLessThanLimit() {
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.rejectIfResettlementPeriodIsGreaterThanLimitInSystem(errors, FIELD_RESETTLEMENT_PERIOD, RESETTLEMENT_PERIOD_LESS_THAN_LIMIT);
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldThrowErrorWhenResettlementPeriodIsGreaterThanLimit() {
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.rejectIfResettlementPeriodIsGreaterThanLimitInSystem(errors, FIELD_RESETTLEMENT_PERIOD, RESETTLEMENT_PERIOD_GREATER_THAN_LIMIT);
        assertTrue(errors.hasErrors());
    }

}
