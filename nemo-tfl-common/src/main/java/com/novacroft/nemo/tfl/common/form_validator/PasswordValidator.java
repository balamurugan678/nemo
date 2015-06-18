package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.tfl.common.command.NewPasswordCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Password validator
 */
@Component("passwordValidator")
public class PasswordValidator implements Validator {
    @Autowired
    CurrentPasswordValidator currentPasswordValidator;
    @Autowired
    NewPasswordValidator newPasswordValidator;

    @Override
    public boolean supports(Class<?> targetClass) {
        return NewPasswordCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        this.currentPasswordValidator.validate(target, errors);
        this.newPasswordValidator.validate(target, errors);
    }
}
