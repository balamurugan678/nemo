package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.SELECT_CARD_FIELD_EMPTY;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CARD_ID;
import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.SelectCardCmd;

/**
 * Select card validator
 */
@Component("selectCardValidator")
public class SelectCardValidator extends BaseValidator {
    @Override
    public boolean supports(Class<?> targetClass) {
        return SelectCardCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        rejectIfEmptyOrWhitespace(errors, FIELD_CARD_ID, SELECT_CARD_FIELD_EMPTY.errorCode());
    }
}
