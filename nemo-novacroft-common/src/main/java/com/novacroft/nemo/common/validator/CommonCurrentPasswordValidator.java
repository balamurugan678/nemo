package com.novacroft.nemo.common.validator;

import com.novacroft.nemo.common.command.CommonCurrentPasswordCmd;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_CURRENT_PASSWORD;

/**
 * Common newPassword validation
 */
@Component(value = "commonCurrentPasswordValidator")
public class CommonCurrentPasswordValidator extends BaseValidator {
    @Override
    public boolean supports(Class<?> targetClass) {
        return CommonCurrentPasswordCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, FIELD_CURRENT_PASSWORD);
    }
}
