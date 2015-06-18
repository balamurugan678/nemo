package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_EMAIL_ADDRESS;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.EMAIL_ADDRESS_NOT_FOUND;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.EmailAddressCmd;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

/**
 * Validates that an email address already exists
 */
@Component("existingEmailAddressValidator")
public class ExistingEmailAddressValidator extends BaseValidator {
    @Autowired
    CustomerDataService customerDataService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return EmailAddressCmd.class.equals(targetClass);

    }

    @Override
    public void validate(Object target, Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, FIELD_EMAIL_ADDRESS);

        EmailAddressCmd cmd = (EmailAddressCmd) target;
        if (isNotBlank(cmd.getEmailAddress())) {
            CustomerDTO customerDTO = this.customerDataService.findByUsernameOrEmail(cmd.getEmailAddress());
            if (customerDTO == null) {
                errors.rejectValue(FIELD_EMAIL_ADDRESS, EMAIL_ADDRESS_NOT_FOUND.errorCode(), new String[]{}, null);
            }
        }
    }
}
