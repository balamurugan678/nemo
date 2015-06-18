package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_EMAIL_ADDRESS;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.ALREADY_USED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.common.validator.CustomerNameValidator;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

@Component("commonCustomerRegistrationValidator")
public class CommonCustomerRegistrationValidator extends BaseValidator {

    @Autowired
    public CustomerService customerService;
    
    @Autowired
    protected CustomerNameValidator customerNameValidator;
    
    @Override
    public boolean supports(Class<?> targetClass) {
        return CustomerDTO.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerDTO customerDTO = (CustomerDTO) target;
        validateMandatoryField(errors, FIELD_EMAIL_ADDRESS);
        validateIfEmailAddressIsAlreadyUsed(errors, FIELD_EMAIL_ADDRESS, customerDTO.getEmailAddress());
        validateCustomerNames(errors, customerDTO);
    }

    public void validateMandatoryField(Errors errors, String field){
        rejectIfMandatoryFieldEmpty(errors, field);
    }
    
    protected void validateIfEmailAddressIsAlreadyUsed(Errors errors, String emailField, String emailAddress){
        if (!errors.hasFieldErrors(emailField)) {
            if (customerService.isEmailAddressAlreadyUsed(emailAddress)) {
                errors.rejectValue(emailField, ALREADY_USED.errorCode());
            }
        }
    }
    
    protected void validateCustomerNames(Errors errors, Object target){
        customerNameValidator.validate(target, errors);
    }
    
}
