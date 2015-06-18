package com.novacroft.nemo.common.validator;

import com.novacroft.nemo.common.command.AddressCmd;
import com.novacroft.nemo.common.command.ContactDetailsCmd;
import com.novacroft.nemo.common.command.impl.CommonOrderCardCmd;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.test_support.CommonContactTestUtil.VALUE_1;
import static com.novacroft.nemo.test_support.CommonContactTestUtil.VALUE_2;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * ContactDetailsValidator unit tests
 */
public class ContactDetailsValidatorTest {
    private ContactDetailsValidator validator;
    private CommonOrderCardCmd cmd;

    private static final String EMAIL_ADDRESS = "test-1@novacroft.com";
    private static final String INVALID_EMAIL_ADDRESS = "test-1@novacroft";
    private static final String INVALID_PHONE_NUMBER = "9246435454";

    @Before
    public void setUp() {
        validator = new ContactDetailsValidator();
        cmd = new CommonOrderCardCmd();
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(ContactDetailsCmd.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(AddressCmd.class));
    }

    @Test
    public void shouldValidate() {
        cmd.setHomePhone(VALUE_1);
        cmd.setMobilePhone(VALUE_2);
        cmd.setEmailAddress(EMAIL_ADDRESS);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateWithEmptyMobilePhone() {
        cmd.setHomePhone(VALUE_1);
        cmd.setMobilePhone("");
        cmd.setEmailAddress(EMAIL_ADDRESS);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateWithNullMobilePhone() {
        cmd.setHomePhone(VALUE_1);
        cmd.setMobilePhone(null);
        cmd.setEmailAddress(EMAIL_ADDRESS);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithEmptyHomePhone() {
        cmd.setHomePhone(" ");
        cmd.setMobilePhone(VALUE_2);
        cmd.setEmailAddress(EMAIL_ADDRESS);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNullHomePhone() {
        cmd.setHomePhone(null);
        cmd.setMobilePhone(VALUE_2);
        cmd.setEmailAddress(EMAIL_ADDRESS);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithInvalidHomePhone() {
        cmd.setHomePhone(INVALID_PHONE_NUMBER);
        cmd.setMobilePhone(VALUE_2);
        cmd.setEmailAddress(EMAIL_ADDRESS);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithInvalidMobilePhone() {
        cmd.setHomePhone(VALUE_1);
        cmd.setMobilePhone(INVALID_PHONE_NUMBER);
        cmd.setEmailAddress(EMAIL_ADDRESS);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithEmptyEmailAddress() {
        cmd.setHomePhone(VALUE_1);
        cmd.setMobilePhone(VALUE_2);
        cmd.setEmailAddress(" ");

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNullEmailAddress() {
        cmd.setHomePhone(VALUE_1);
        cmd.setMobilePhone(VALUE_2);
        cmd.setEmailAddress(null);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithInvalidEmailAddress() {
        cmd.setHomePhone(VALUE_1);
        cmd.setMobilePhone(VALUE_2);
        cmd.setEmailAddress(INVALID_EMAIL_ADDRESS);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

}
