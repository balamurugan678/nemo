package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.transfer.WebAccountDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.UNABLE_TO_CREATE_AN_ACCOUNT_ERROR;

@Component("webAccountValidator")
public class WebAccountValidator extends BaseValidator {
	//TODO We should be able to delete this class

    @Override
    public boolean supports(Class<?> targetClass) {
        return WebAccountDTO.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WebAccountDTO webAccountDTO = (WebAccountDTO) target;
        if (webAccountDTO.getId() == null) {
            errors.reject(UNABLE_TO_CREATE_AN_ACCOUNT_ERROR.errorCode());
        }
    }
}
