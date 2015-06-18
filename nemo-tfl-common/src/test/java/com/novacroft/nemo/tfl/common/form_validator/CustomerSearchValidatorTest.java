package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_FIRST_NAME;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_LAST_NAME;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_POSTCODE;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_EMAIL_ADDRESS;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CARD_NUMBER;
import static com.novacroft.nemo.test_support.CustomerTestUtil.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.command.impl.CommonLoginCmd;
import com.novacroft.nemo.common.validator.PostcodeValidator;
import com.novacroft.nemo.tfl.common.command.impl.CustomerSearchCmdImpl;
import com.novacroft.nemo.tfl.common.form_validator.CustomerSearchValidator;

/**
 * CustomerSearchValidator unit tests
 */
public class CustomerSearchValidatorTest {
    private CustomerSearchValidator validator;
    private CustomerSearchCmdImpl cmd;
    private PostcodeValidator mockPostcodeValidator;

    @Before
    public void setUp() {
        validator = new CustomerSearchValidator();
        cmd = new CustomerSearchCmdImpl();
        mockPostcodeValidator = mock(PostcodeValidator.class);

        validator.postcodeValidator = mockPostcodeValidator;
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(CustomerSearchCmdImpl.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(this.validator.supports(CommonLoginCmd.class));
    }

    @Test
    public void shouldValidate() {
        cmd = getSearch1();

        when(mockPostcodeValidator.validate(anyString())).thenReturn(true);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfCardNumberHasInvalidCharacters() {
        cmd = getInvalidSearchIncludingNameCharacters();

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        assertTrue(errors.hasFieldErrors(FIELD_CARD_NUMBER));
    }

    @Test
    public void shouldNotValidateIfFirstNameIsLessThanCharacterLimit() {
        cmd = getInvalidSearchIncludingNameLength();

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        assertTrue(errors.hasFieldErrors(FIELD_FIRST_NAME));
    }

    @Test
    public void shouldNotValidateIfLastNameIsLessThanCharacterLimit() {
        cmd = getInvalidSearchIncludingNameLength();

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        assertTrue(errors.hasFieldErrors(FIELD_LAST_NAME));
    }

    @Test
    public void shouldNotValidateIfFirstNameHasInvalidCharacters() {
        cmd = getInvalidSearchIncludingNameCharacters();

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        assertTrue(errors.hasFieldErrors(FIELD_FIRST_NAME));
    }

    @Test
    public void shouldNotValidateIfLastNameHasInvalidCharacters() {
        cmd = getInvalidSearchIncludingNameCharacters();

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        assertTrue(errors.hasFieldErrors(FIELD_LAST_NAME));
    }

    @Test
    public void shouldNotValidateIfEmailHasInvalidCharacters() {
        cmd = getInvalidSearchIncludingNameCharacters();

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        assertTrue(errors.hasFieldErrors(FIELD_EMAIL_ADDRESS));
    }

    @Test
    public void shouldNotValidateIfPostcodeHasInvalidCharacters() {
        cmd = getInvalidSearchIncludingNameCharacters();

        when(mockPostcodeValidator.validate(anyString())).thenReturn(false);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        assertTrue(errors.hasFieldErrors(FIELD_POSTCODE));
    }
}
