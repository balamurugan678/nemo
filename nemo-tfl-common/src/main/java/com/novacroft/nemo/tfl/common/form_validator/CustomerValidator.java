package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.UNABLE_TO_CREATE_A_CUSTOMER_ERROR;

@Component("customerValidator")
public class CustomerValidator extends BaseValidator {

    @Override
    public boolean supports(Class<?> targetClass) {
        return CustomerDTO.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
    	CustomerDTO customerDTO = (CustomerDTO) target;
        if (customerDTO.getId() == null) {
            errors.reject(UNABLE_TO_CREATE_A_CUSTOMER_ERROR.errorCode());
        }
    }
}
