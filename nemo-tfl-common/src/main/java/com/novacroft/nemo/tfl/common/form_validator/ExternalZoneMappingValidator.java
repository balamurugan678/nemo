package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.ZONE_MAPPING_NOT_EXIST;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_START_ZONE;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.tfl.common.command.TravelCardCmd;

@Component("externalzoneMappingValidator")
public class ExternalZoneMappingValidator extends ZoneMappingValidator {

	@Override
	protected void validateNonOtherTravelCardsZoneMapping(TravelCardCmd travelCardCmd, Errors errors) {
    	if(getPrePaidTickectDTOById(travelCardCmd) == null) 
    	{
    		 errors.rejectValue(FIELD_START_ZONE, ZONE_MAPPING_NOT_EXIST.errorCode());
             getAlternativeZoneMapping(travelCardCmd);
    	}
        
    }

	@Override
    protected void validateOtherTravelCardsZoneMapping(TravelCardCmd travelCardCmd, Errors errors) {
    	this.validateNonOtherTravelCardsZoneMapping(travelCardCmd, errors);
    }
}
