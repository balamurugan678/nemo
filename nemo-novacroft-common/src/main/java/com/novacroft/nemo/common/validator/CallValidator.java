package com.novacroft.nemo.common.validator;

import com.novacroft.nemo.common.command.impl.CallCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.*;

/**
 * Customer call validation
 */
@Component("callValidator")
public class CallValidator extends BaseValidator {
    @Autowired
    PostcodeValidator postcodeValidator;

    @Override
    public boolean supports(Class<?> targetClass) {
        return CallCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, FIELD_CALL_TYPE_ID);
        rejectIfMandatoryFieldEmpty(errors, FIELD_DESCRIPTION);
        rejectIfMandatoryFieldEmpty(errors, FIELD_RESOLUTION);
    }

    // Added for junit tests
    public void setPostcodeValidator(PostcodeValidator postcodeValidator) {
        this.postcodeValidator = postcodeValidator;
    }

}
