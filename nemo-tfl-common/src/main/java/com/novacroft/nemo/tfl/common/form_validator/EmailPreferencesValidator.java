package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.EmailPreferencesCmd;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.STATEMENT_TERMS_NOT_ACCEPTED;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_ATTACHMENT_TYPE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_STATEMENT_TERMS_ACCEPTED;

/**
 * Email preferences validator
 */
@Component("emailPreferencesValidator")
public class EmailPreferencesValidator extends BaseValidator {
    @Override
    public boolean supports(Class<?> targetClass) {
        return EmailPreferencesCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmailPreferencesCmd cmd = (EmailPreferencesCmd) target;

        rejectIfMandatoryFieldEmpty(errors, FIELD_ATTACHMENT_TYPE);

        if (isNotTrue(cmd.getStatementTermsAccepted())) {
            errors.rejectValue(FIELD_STATEMENT_TERMS_ACCEPTED, STATEMENT_TERMS_NOT_ACCEPTED.errorCode(), new String[]{}, null);
        }
    }

    protected boolean isNotTrue(Boolean value) {
        return (value == null || !value);
    }
}
