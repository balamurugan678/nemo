package com.novacroft.nemo.tfl.common.form_validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.apache.commons.lang3.StringUtils;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.common.validator.PostcodeValidator;
import com.novacroft.nemo.tfl.common.command.impl.CustomerSearchCmdImpl;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.FIRSTNAME_CHARACTERS;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.LASTNAME_CHARACTERS;
import static com.novacroft.nemo.common.constant.CommonContentCode.INVALID_POSTCODE;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_FIRST_NAME;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_LAST_NAME;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_POSTCODE;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_EMAIL_ADDRESS;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CARD_NUMBER;

/**
 * Customer Search validation
 */
@Component("customerSearchValidator")
public class CustomerSearchValidator extends BaseValidator {

    private static final int FIRST_NAME_MAX_LENGTH = 2;
    private static final int LAST_NAME_MAX_LENGTH = 2;
    
    @Autowired
    protected PostcodeValidator postcodeValidator;

    @Override
    public boolean supports(Class<?> targetClass) {
        return CustomerSearchCmdImpl.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerSearchCmdImpl cmd = (CustomerSearchCmdImpl) target;

        validateFirstName(cmd.getFirstName(), errors);
        validateLastName(cmd.getLastName(), errors);
        validateEmail(cmd.getEmailAddress(), errors);
        validateCardNumber(cmd.getCardNumber(), errors);
        validatePostcode(cmd.getPostcode(), errors);
    }

    private void validateFirstName(String firstName, Errors errors) {
        if (!StringUtils.isEmpty(firstName)) {
            if (firstName.length() <= FIRST_NAME_MAX_LENGTH) {
                errors.rejectValue(FIELD_FIRST_NAME, FIRSTNAME_CHARACTERS.errorCode());
            }
            rejectIfNotAlphabetic(errors, FIELD_FIRST_NAME);
        }
    }

    private void validateLastName(String lastName, Errors errors) {
        if (!StringUtils.isEmpty(lastName)) {
            if (lastName.length() <= LAST_NAME_MAX_LENGTH) {
                errors.rejectValue(FIELD_LAST_NAME, LASTNAME_CHARACTERS.errorCode());
            }
            rejectIfNotAlphabetic(errors, FIELD_LAST_NAME);
        }
    }

    private void validateEmail(String email, Errors errors) {
        if (!StringUtils.isEmpty(email)) {
            rejectIfNotValidEmail(errors, FIELD_EMAIL_ADDRESS);
        }
    }

    private void validateCardNumber(String cardNumber, Errors errors) {
        if (!StringUtils.isEmpty(cardNumber)) {
            rejectIfNotNumeric(errors, FIELD_CARD_NUMBER);
        }
    }

    private void validatePostcode(String postcode, Errors errors) {
        if (!StringUtils.isEmpty(postcode) && !postcodeValidator.validate(postcode)) {
            errors.rejectValue(FIELD_POSTCODE, INVALID_POSTCODE.errorCode());
        }
    }
}
