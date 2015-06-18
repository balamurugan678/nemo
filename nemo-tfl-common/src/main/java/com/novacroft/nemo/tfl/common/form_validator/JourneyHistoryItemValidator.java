package com.novacroft.nemo.tfl.common.form_validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.JourneyHistoryItemCmd;
import com.novacroft.nemo.tfl.common.constant.PageCommandAttribute;

@Component("journeyHistoryItemValidator")
public class JourneyHistoryItemValidator extends BaseValidator {

    @Override
    public boolean supports(Class<?> targetClass) {
        return JourneyHistoryItemCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        rejectIfMandatoryFieldEmpty(errors, PageCommandAttribute.FIELD_CARD_ID);
        rejectIfMandatoryFieldEmpty(errors, PageCommandAttribute.FIELD_JOURNEY_DATE);
        rejectIfMandatoryFieldEmpty(errors, PageCommandAttribute.FIELD_JOURNEY_ID);
        if (!errors.hasFieldErrors(PageCommandAttribute.FIELD_JOURNEY_DATE)) {
            rejectIfNotShortDate(errors, PageCommandAttribute.FIELD_JOURNEY_DATE);
        }
    }

}
