package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.tfl.common.command.TicketTypeCmd;
import com.novacroft.nemo.tfl.common.command.TravelCardCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.TicketType;

/**
 * TicketTypeValidator unit tests
 */
public class TicketTypeValidatorTest {
    private TicketTypeValidator validator;
    private CartItemCmdImpl cmd;

    private static final String TICKET_TYPE = "test_ticket_type_1";

    @Before
    public void setUp() {
        validator = new TicketTypeValidator();
        cmd = new CartItemCmdImpl();
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(TicketTypeCmd.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(TravelCardCmd.class));
    }

    @Test
    public void shouldValidate() {
        cmd.setTicketType(TICKET_TYPE);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithEmptyTicketType() {
        cmd.setTicketType(" ");
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNullTicketType() {
        cmd.setTicketType(null);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldValidateIfOysterCardWithFailedAutoTopUp() {
        cmd.setOysterCardWithFailedAutoTopUpCaseCheck(true);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldRejectIfOysterCardWithFailedAutoTopUp() {
        cmd.setOysterCardWithFailedAutoTopUpCaseCheck(true);
        cmd.setTicketType(TicketType.PAY_AS_YOU_GO_AUTO_TOP_UP.code());
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.rejectIfOysterCardWithFailedAutoTopUp(cmd, errors);
        assertTrue(errors.hasErrors());
    }

}
