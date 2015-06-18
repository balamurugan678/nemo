package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_MISSING_STATION_ID;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PICK_UP_STATION;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_REASON_FOR_MISSISNG;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.impl.CompleteJourneyCommandImpl;

@Component(value="CompleteJourneyValidator")
public class CompleteJourneyValidator extends BaseValidator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return CompleteJourneyCommandImpl.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CompleteJourneyCommandImpl command = (CompleteJourneyCommandImpl)target;
		rejectIfMandatorySelectFieldEmpty(errors, FIELD_MISSING_STATION_ID);
		rejectIfMandatorySelectFieldEmpty(errors, FIELD_REASON_FOR_MISSISNG);
        if (preferredStationNotAvailable(command)) {
        	rejectIfMandatorySelectFieldEmpty(errors, FIELD_PICK_UP_STATION);
        }
	}

	protected boolean preferredStationNotAvailable(CompleteJourneyCommandImpl command){
		return command.getPreferredStation() == null;
	}
}
