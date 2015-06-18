package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_EMAIL_ADDRESS;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.ALREADY_USED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.AddressValidator;
import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.common.validator.ContactDetailsValidator;
import com.novacroft.nemo.common.validator.CustomerNameValidator;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

/**
 * Common Customer validator details validation
 */
@Component("personalDetailsValidator")
public class PersonalDetailsValidator extends BaseValidator {

    @Autowired
    protected AddressValidator addressValidator;
    @Autowired
    protected CustomerNameValidator customerNameValidator;
    @Autowired
    protected ContactDetailsValidator contactDetailsValidator;
    @Autowired
    protected CustomerDataService customerDataService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return PersonalDetailsCmdImpl.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        this.customerNameValidator.validate(target, errors);
        this.addressValidator.validate(target, errors);
        this.contactDetailsValidator.validate(target, errors);
        PersonalDetailsCmdImpl cmd = (PersonalDetailsCmdImpl) target;
        CustomerDTO customerDTO = cmd.getCustomerId() != null ? customerDataService.findById(cmd.getCustomerId()) : null;
        if (null == customerDTO || (customerDTO != null && !cmd.getEmailAddress().equals(customerDTO.getEmailAddress()))) {
            validateIfEmailAddressIsAlreadyUsed(errors, cmd.getEmailAddress());
        }
    }

    protected void validateIfEmailAddressIsAlreadyUsed(Errors errors, String emailAddress) {
        if (!errors.hasFieldErrors(FIELD_EMAIL_ADDRESS) && customerDataService.findByUsernameOrEmail(emailAddress) != null) {
            errors.rejectValue(FIELD_EMAIL_ADDRESS, ALREADY_USED.errorCode());
        }
    }
}
