package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.common.validator.CommonNewPasswordValidator;
import com.novacroft.nemo.tfl.common.command.NewPasswordCmd;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_NEW_PASSWORD;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.INVALID_PASSWORD;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * New password validator
 */
@Component(value = "newPasswordValidator")
public class NewPasswordValidator extends CommonNewPasswordValidator implements Validator {
    protected static final Integer MIN_PASSWORD_LENGTH = 6;
    protected static final String UPPER_CASE_LETTER_PATTERN = ".*[A-Z].*";
    protected static final String LOWER_CASE_LETTER_PATTERN = ".*[a-z].*";
    protected static final String DIGIT_PATTERN = ".*[0-9].*";
    protected static final String ALLOWED_CHARS_PATTERN = "[a-zA-Z0-9]*";

    @Override
    public boolean supports(Class<?> targetClass) {
        return NewPasswordCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        super.validate(target, errors);

        NewPasswordCmd cmd = (NewPasswordCmd) target;

        if (isBlank(cmd.getNewPassword())) {
            return;
        }

        if (isTooShort(cmd.getNewPassword()) || doesNotContainAnUpperCaseLetter(cmd.getNewPassword())
                        || doesNotContainALowerCaseLetter(cmd.getNewPassword()) || doesNotContainADigit(cmd.getNewPassword())
                        || doesContainSpecialCharacters(cmd.getNewPassword())) {
            errors.rejectValue(FIELD_NEW_PASSWORD, INVALID_PASSWORD.errorCode(), new String[] {}, null);
        }
    }

    protected boolean isTooShort(String password) {
        return isNotBlank(password) && password.length() < MIN_PASSWORD_LENGTH;
    }

    protected boolean doesNotContainAnUpperCaseLetter(String password) {
        return isNotBlank(password) && !password.matches(UPPER_CASE_LETTER_PATTERN);
    }

    protected boolean doesNotContainALowerCaseLetter(String password) {
        return isNotBlank(password) && !password.matches(LOWER_CASE_LETTER_PATTERN);
    }

    protected boolean doesNotContainADigit(String password) {
        return isNotBlank(password) && !password.matches(DIGIT_PATTERN);
    }

    protected boolean doesContainSpecialCharacters(String password) {
        return isNotBlank(password) && !password.matches(ALLOWED_CHARS_PATTERN);
    }
}
