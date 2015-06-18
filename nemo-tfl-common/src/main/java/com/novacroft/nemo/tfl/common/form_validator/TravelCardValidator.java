package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_END_DATE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_START_DATE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_TRAVEL_CARD_TYPE;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.tfl.common.command.TravelCardCmd;

/**
 * Travel Card validation
 */
@Component("travelCardValidator")
public class TravelCardValidator extends CommonTravelCardValidator {
    
    protected static final String[] MANDATORY_FIELDS = { FIELD_TRAVEL_CARD_TYPE, FIELD_START_DATE};

    @Override
    public boolean supports(Class<?> targetClass) {
        return TravelCardCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TravelCardCmd cmd = (TravelCardCmd) target;
        validateMandatoryFields(errors, MANDATORY_FIELDS);
        validateIfNotShortDate(errors, FIELD_START_DATE);
        validateOddPeriodTravelCard(errors, cmd);
    }

    protected void validateOddPeriodTravelCard(Errors errors, TravelCardCmd cmd) {
        if (!errors.hasFieldErrors(FIELD_TRAVEL_CARD_TYPE) && cmd.getTravelCardType().equalsIgnoreCase(Durations.OTHER.getDurationType())) {
            validateEndDate(errors);
            validateInvalidDate(errors, FIELD_END_DATE, cmd.getEndDate());
            if (!errors.hasFieldErrors(FIELD_START_DATE) && !errors.hasFieldErrors(FIELD_END_DATE)) {
                validateStartDateAfterEndDate(errors, cmd.getStartDate(), cmd.getEndDate());
                validateOddPeriodTravelCardDurations(errors, cmd.getStartDate(), cmd.getEndDate());
            }
        }
    }

}
