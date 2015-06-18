package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.common.command.impl.CommonLoginCmd;
import com.novacroft.nemo.tfl.common.command.EmailPreferencesCmd;
import com.novacroft.nemo.tfl.common.command.impl.CardPreferencesCmdImpl;
import com.novacroft.nemo.tfl.common.form_validator.EmailPreferencesValidator;

import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for EmailPreferencesValidator
 */
public class EmailPreferencesValidatorTest {
    @Test
    public void shouldSupportClass() {
        EmailPreferencesValidator validator = new EmailPreferencesValidator();
        assertTrue(validator.supports(EmailPreferencesCmd.class));
    }

    @Test
    public void shouldNotSupportClass() {
        EmailPreferencesValidator validator = new EmailPreferencesValidator();
        assertFalse(validator.supports(CommonLoginCmd.class));
    }

    @Test
    public void shouldNotValidatePreferencesWithoutAttachment() {
        CardPreferencesCmdImpl cmd = new CardPreferencesCmdImpl();
        cmd.setEmailFrequency("X");
        cmd.setAttachmentType("");
        cmd.setStatementTermsAccepted(Boolean.TRUE);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        EmailPreferencesValidator validator = new EmailPreferencesValidator();
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidatePreferencesWithTermsNotAccepted() {
        CardPreferencesCmdImpl cmd = new CardPreferencesCmdImpl();
        cmd.setEmailFrequency("X");
        cmd.setAttachmentType("X");
        cmd.setStatementTermsAccepted(Boolean.FALSE);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        EmailPreferencesValidator validator = new EmailPreferencesValidator();
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldReturnTrueWithNullValue() {
        Boolean testValue = null;
        EmailPreferencesValidator validator = new EmailPreferencesValidator();
        assertTrue(validator.isNotTrue(testValue));
    }

    @Test
    public void shouldReturnTrueWithFalseValue() {
        Boolean testValue = Boolean.FALSE;
        EmailPreferencesValidator validator = new EmailPreferencesValidator();
        assertTrue(validator.isNotTrue(testValue));
    }

    @Test
    public void shouldReturnFalseWithTrueValue() {
        Boolean testValue = Boolean.TRUE;
        EmailPreferencesValidator validator = new EmailPreferencesValidator();
        assertFalse(validator.isNotTrue(testValue));
    }
}
