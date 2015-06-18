package com.novacroft.nemo.common.validator;

import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_FIRST_NAME;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_LAST_NAME;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_TITLE;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_MIDDLE_INITIAL;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.command.CustomerNameCmd;

/**
 * Customer personal details validation
 */
@Component("customerNameValidator")
public class CustomerNameValidator extends BaseValidator {

    @Override
    public boolean supports(Class<?> targetClass) {
        return CustomerNameCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, FIELD_TITLE);
        rejectIfMandatoryFieldEmpty(errors, FIELD_FIRST_NAME);
        if (!errors.hasFieldErrors(FIELD_FIRST_NAME)) {
            rejectIfNotAlphabeticWithHyphenWhiteSpaceBetweenWordsOnly(errors, FIELD_FIRST_NAME);
        }
        rejectIfMandatoryFieldEmpty(errors, FIELD_LAST_NAME);
        if (!errors.hasFieldErrors(FIELD_LAST_NAME)) {
            rejectIfNotAlphabeticWithHyphenWhiteSpaceBetweenWordsOnly(errors, FIELD_LAST_NAME);
        }
        if(StringUtils.isNotBlank((String) errors.getFieldValue(FIELD_MIDDLE_INITIAL))){
        	rejectIfNotAlphaNumeric(errors, FIELD_MIDDLE_INITIAL);
        }
    }
}
