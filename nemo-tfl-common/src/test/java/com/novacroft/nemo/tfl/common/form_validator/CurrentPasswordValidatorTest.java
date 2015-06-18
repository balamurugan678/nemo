package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.command.CommonCurrentPasswordCmd;

public class CurrentPasswordValidatorTest {

    private CurrentPasswordValidator validator;
    private CurrentPasswordCmd currentPassword;
    private Errors errors;

    @Before
    public void setUp() throws Exception {
        validator = new CurrentPasswordValidator();
        currentPassword = new CurrentPasswordCmd();
        
        errors = new BeanPropertyBindingResult(currentPassword, "cmd");
    }
    
    @Test
    public void supportClass(){
        assertTrue(validator.supports(CommonCurrentPasswordCmd.class));
    }

    @Test
    public void doesNotSupportClass(){
        assertFalse(validator.supports(CurrentPasswordCmd.class));
    }
    
    @Test
    public void shouldNotvalidateIfCurrentPasswordIsEmpty() {
        currentPassword.setCurrentPassword("");
        validator.validate(currentPassword, errors);
        assertTrue(errors.hasErrors());

    }

    private class CurrentPasswordCmd {
        private String currentPassword;

        public CurrentPasswordCmd() {
        }

        @SuppressWarnings("unused")
        public String getCurrentPassword() {
            return currentPassword;
        }

        public void setCurrentPassword(String currentPassword) {
            this.currentPassword = currentPassword;
        }

    }
}
