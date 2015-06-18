package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.common.constant.CommonContentCode.NOT_EQUAL;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_EMAIL_ADDRESS;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.ALREADY_USED;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CONFIRM_EMAIL_ADDRESS;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_USERNAME;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.AddressValidator;
import com.novacroft.nemo.common.validator.ContactDetailsValidator;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;

/**
 * Customer registration validator
 */
@Component("customerRegistrationValidator")
public class CustomerRegistrationValidator extends CommonCustomerRegistrationValidator {
    @Autowired
    protected AddressValidator addressValidator;
    @Autowired
    protected ContactDetailsValidator contactDetailsValidator;
    @Autowired
    protected UserCredentialsValidator userCredentialsValidator;
    @Autowired
    protected PasswordValidator passwordValidator;

    @Override
    public boolean supports(Class<?> targetClass) {
        return CartCmdImpl.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        CartCmdImpl cmd = (CartCmdImpl) target;
        validateCustomerNames(errors, cmd);
        addressValidator.validate(cmd, errors);
        contactDetailsValidator.validate(cmd, errors);
        userCredentialsValidator.validate(cmd, errors);
        rejectIfMandatoryFieldEmpty(errors, FIELD_CONFIRM_EMAIL_ADDRESS);

        if (!errors.hasFieldErrors(FIELD_EMAIL_ADDRESS) && !errors.hasFieldErrors(FIELD_CONFIRM_EMAIL_ADDRESS)) {
            if (!cmd.getEmailAddress().equals(cmd.getConfirmEmailAddress())) {
                errors.rejectValue(FIELD_CONFIRM_EMAIL_ADDRESS, NOT_EQUAL.errorCode());
            }
        }

        if (!errors.hasFieldErrors(FIELD_USERNAME)) {
            if (customerService.isUsernameAlreadyUsed(cmd.getUsername())) {
                errors.rejectValue(FIELD_USERNAME, ALREADY_USED.errorCode());
            }
        }
        validateIfEmailAddressIsAlreadyUsed(errors, FIELD_EMAIL_ADDRESS, cmd.getEmailAddress());
    }

}
