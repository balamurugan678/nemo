package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.UserCredentialsCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

/**
 * OysterCard validation
 */
@Component("userCredentialsValidator")
public class UserCredentialsValidator extends BaseValidator {
    @Autowired
    protected NewPasswordValidator newPasswordValidator;

    @Override
    public boolean supports(Class<?> targetClass) {
        return UserCredentialsCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCredentialsCmd cmd = (UserCredentialsCmd) target;

        rejectIfEmptyOrWhitespace(errors, "username", "mandatoryFieldEmpty.error",
                new Object[]{new DefaultMessageSourceResolvable("username.label")});

        if (!errors.hasFieldErrors("username")) {
            rejectIfNotValidUsername(errors, "username");
        }

        this.newPasswordValidator.validate(cmd, errors);
    }
}
