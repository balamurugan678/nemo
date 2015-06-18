package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.SELECTED_STATION_DIFFERENT_FROM_PENDINGITEM_STATION;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_STATION_ID;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.SelectStationCmd;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;

/**
 * Select station validator
 */
@Component(value = "selectStationValidator")
public class SelectStationValidator extends BaseValidator {
    @Override
    public boolean supports(Class<?> targetClass) {
        return SelectStationCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        rejectIfMandatorySelectFieldEmpty(errors, FIELD_STATION_ID);
    }
    
    public void rejectIfPickUpStationIsDifferentFromPendingItemLocation( Object target, Errors errors) {
    	CartDTO cartDTO = (CartDTO) target;
    	if(cartDTO.isPpvPickupLocationAddFlag()) {
    		errors.rejectValue(FIELD_STATION_ID, 
    							SELECTED_STATION_DIFFERENT_FROM_PENDINGITEM_STATION.errorCode(),
    							new String[]{cartDTO.getPpvPickupLocationName()},
    							null);
    	}
    }
}
