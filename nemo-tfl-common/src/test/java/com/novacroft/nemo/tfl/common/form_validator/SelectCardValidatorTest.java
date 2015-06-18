package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.common.command.impl.CommonLoginCmd;
import com.novacroft.nemo.tfl.common.command.SelectCardCmd;
import com.novacroft.nemo.tfl.common.command.impl.CardPreferencesCmdImpl;
import com.novacroft.nemo.tfl.common.form_validator.SelectCardValidator;

import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for SelectCardValidator
 */
public class SelectCardValidatorTest {
    @Test
    public void shouldSupportClass() {
        SelectCardValidator validator = new SelectCardValidator();
        assertTrue(validator.supports(SelectCardCmd.class));
    }

    @Test
    public void shouldNotSupportClass() {
        SelectCardValidator validator = new SelectCardValidator();
        assertFalse(validator.supports(CommonLoginCmd.class));
    }

    @Test
    public void shouldValidate() {
        CardPreferencesCmdImpl cmd = new CardPreferencesCmdImpl();
        cmd.setCardId(1L);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        SelectCardValidator validator = new SelectCardValidator();
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidate() {
        CardPreferencesCmdImpl cmd = new CardPreferencesCmdImpl();
        cmd.setCardId(null);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        SelectCardValidator validator = new SelectCardValidator();
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }
}
