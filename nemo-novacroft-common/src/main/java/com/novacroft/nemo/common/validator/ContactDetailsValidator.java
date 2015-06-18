package com.novacroft.nemo.common.validator;

import com.novacroft.nemo.common.command.ContactDetailsCmd;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.*;
import static com.novacroft.nemo.common.utils.StringUtil.isNotEmpty;

/**
 * Customer contact details validation
 */
@Component("contactDetailsValidator")
public class ContactDetailsValidator extends BaseValidator {

    @Override
    public boolean supports(Class<?> targetClass) {
        return ContactDetailsCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ContactDetailsCmd cmd = (ContactDetailsCmd) target;
        rejectIfMandatoryFieldEmpty(errors, FIELD_HOME_PHONE);
        if (!errors.hasFieldErrors(FIELD_HOME_PHONE)) {
            rejectIfNotValidPhone(errors, FIELD_HOME_PHONE);
        }
        if (isNotEmpty(cmd.getMobilePhone())) {
            rejectIfNotValidPhone(errors, FIELD_MOBILE_PHONE);
        }
        rejectIfMandatoryFieldEmpty(errors, FIELD_EMAIL_ADDRESS);
        if (!errors.hasFieldErrors(FIELD_EMAIL_ADDRESS)) {
            rejectIfNotValidEmail(errors, FIELD_EMAIL_ADDRESS);
        }
    }
}
