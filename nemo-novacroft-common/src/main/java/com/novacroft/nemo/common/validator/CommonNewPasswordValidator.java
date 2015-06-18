package com.novacroft.nemo.common.validator;

import com.novacroft.nemo.common.command.CommonNewPasswordCmd;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.common.constant.CommonContentCode.CONFIRMATION_MISMATCH;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_NEW_PASSWORD;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_NEW_PASSWORD_CONFIRMATION;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Common new password validation
 */
@Component(value = "commonNewPasswordValidator")
public class CommonNewPasswordValidator extends BaseValidator {
    @Override
    public boolean supports(Class<?> targetClass) {
        return CommonNewPasswordCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, FIELD_NEW_PASSWORD);
        rejectIfMandatoryFieldEmpty(errors, FIELD_NEW_PASSWORD_CONFIRMATION);

        CommonNewPasswordCmd cmd = (CommonNewPasswordCmd) target;
        if (isBlank(cmd.getNewPassword()) || isBlank(cmd.getNewPasswordConfirmation())) {
            return;
        }

        if (!cmd.getNewPassword().equals(cmd.getNewPasswordConfirmation())) {
            errors.rejectValue(FIELD_NEW_PASSWORD_CONFIRMATION, CONFIRMATION_MISMATCH.errorCode(),
                    new Object[]{new DefaultMessageSourceResolvable("newPassword.label"),
                            new DefaultMessageSourceResolvable("newPasswordConfirmation.label")}, null);
        }
    }
}
