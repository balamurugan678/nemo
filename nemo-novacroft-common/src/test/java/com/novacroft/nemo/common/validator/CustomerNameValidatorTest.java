package com.novacroft.nemo.common.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.command.AddressCmd;
import com.novacroft.nemo.common.command.CustomerNameCmd;
import com.novacroft.nemo.common.command.impl.CommonOrderCardCmd;

/**
 * CustomerNameValidator unit tests
 */
public class CustomerNameValidatorTest {
    private CustomerNameValidator validator;
    private CommonOrderCardCmd cmd;

    private static final String TITLE_1 = "Mr";
    private static final String FIRST_NAME_1 = "testfirstname";
    private static final String FIRST_NAME_2 = "test firstname";
    private static final String FIRST_NAME_3 = "test-firstname";
    private static final String LAST_NAME_1 = "testsurname";
    private static final String LAST_NAME_2 = "test surname";
    private static final String LAST_NAME_3 = "test-surname";

    @Before
    public void setUp() {
        validator = new CustomerNameValidator();
        cmd = new CommonOrderCardCmd();
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(CustomerNameCmd.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(AddressCmd.class));
    }

    @Test
    public void shouldValidate() {
        cmd.setTitle(TITLE_1);
        cmd.setFirstName(FIRST_NAME_1);
        cmd.setLastName(LAST_NAME_1);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithWhiteSpaceFirstName() {
        cmd.setTitle(TITLE_1);
        cmd.setFirstName(FIRST_NAME_2);
        cmd.setLastName(LAST_NAME_1);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithHyphenFirstName() {
        cmd.setTitle(TITLE_1);
        cmd.setFirstName(FIRST_NAME_3);
        cmd.setLastName(LAST_NAME_1);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithWhiteSpaceLastName() {
        cmd.setTitle(TITLE_1);
        cmd.setFirstName(FIRST_NAME_1);
        cmd.setLastName(LAST_NAME_2);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithHyphenLastName() {
        cmd.setTitle(TITLE_1);
        cmd.setFirstName(FIRST_NAME_1);
        cmd.setLastName(LAST_NAME_3);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithEmptyTitle() {
        cmd.setTitle(" ");
        cmd.setFirstName(FIRST_NAME_1);
        cmd.setLastName(LAST_NAME_1);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNullTitle() {
        cmd.setTitle(null);
        cmd.setFirstName(FIRST_NAME_1);
        cmd.setLastName(LAST_NAME_1);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithEmptyFirstName() {
        cmd.setTitle(TITLE_1);
        cmd.setFirstName(" ");
        cmd.setLastName(LAST_NAME_1);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNullFirstName() {
        cmd.setTitle(TITLE_1);
        cmd.setFirstName(null);
        cmd.setLastName(LAST_NAME_1);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateFirstNameWithSpecialCharacters() {
        cmd.setTitle(TITLE_1);
        cmd.setFirstName("testfirstname£$");
        cmd.setLastName(LAST_NAME_1);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldValidateFirstNameWithNumerics() {
        cmd.setTitle(TITLE_1);
        cmd.setFirstName("test45firstname");
        cmd.setLastName(LAST_NAME_1);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithEmptyLastName() {
        cmd.setTitle(TITLE_1);
        cmd.setFirstName(FIRST_NAME_1);
        cmd.setLastName(" ");

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNullLastName() {
        cmd.setTitle(TITLE_1);
        cmd.setFirstName(FIRST_NAME_1);
        cmd.setLastName(null);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateLastNameWithSpecialCharacters() {
        cmd.setTitle(TITLE_1);
        cmd.setFirstName(FIRST_NAME_1);
        cmd.setLastName("test£$surame");

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldValidateLastNameWithNumerics() {
        cmd.setTitle(TITLE_1);
        cmd.setFirstName(FIRST_NAME_1);
        cmd.setLastName("testsurame67");

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateInitialsWithSpecialCharacters() {
        cmd.setTitle(TITLE_1);
        cmd.setFirstName(FIRST_NAME_1);
        cmd.setLastName("test£$surame");
        cmd.setInitials("init£$");

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldValidateInitialsWithNumerics() {
        cmd.setTitle(TITLE_1);
        cmd.setFirstName(FIRST_NAME_1);
        cmd.setLastName("testsurame");
        cmd.setInitials("init12");

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

}
