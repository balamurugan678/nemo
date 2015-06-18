package com.novacroft.nemo.tfl.batch.validator.impl.product_fare_loader;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.novacroft.nemo.tfl.batch.application_service.product_fare_loader.PrePaidTicketRecordService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.data_service.PrePaidTicketDataService;
import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;

@Component("prePaidTicketValidator")
public class PrePaidTicketValidator implements Validator {


    public static final String PARSABLE_INTEGER_ONLY = "^[-]?\\d+$";

	@Autowired
	protected PrePaidTicketRecordService prePaidTicketRecordService;
	
	@Autowired
	protected PrePaidTicketDataService paidTicketDataService;

	@Override
    public boolean supports(Class<?> targetClass) {
        return String[].class.equals(targetClass);
    }

	@Override
	public void validate(Object target, Errors errors) {
        String[] record = (String[]) target;
        validateAddhocCode(errors, prePaidTicketRecordService.getAdhocCode(record));
        validateStartZone(errors, record);
        validateEndZone(errors, record);
        validateFromDuration(errors, record);
        validateToDuration(errors, record);
        validateEffectiveDate(errors, record);
        validatePassengerType(errors, record);
        if (!errors.hasErrors()) {
            validateForDuplicateForZoneDurationPassengerTypeAndDiscountType(errors, record);
        }
        validatePriceIsValid(errors, record);
	     
	}

	protected void validateEffectiveDate(Errors errors, String[] record) {
		if(prePaidTicketRecordService.getPrePaidTicketEffectiveDate(record) == null) {
			errors.reject(ContentCode.PRE_PAID_TICKET_EFFECTIVE_DATE_INVALID.errorCode(), new String[]{ prePaidTicketRecordService.getAdhocCode(record)}, null);
		}
		
	}


	protected void validatePassengerType(Errors errors, String[] record) {
		if(StringUtils.isBlank(prePaidTicketRecordService.getPassengerTypeCode(record))){
	    	   errors.reject(ContentCode.PRE_PAID_TICKET_INVALID_PASSENGER_TYPE.errorCode(),
	                   null, null);
		}
	}

	protected void validateToDuration(Errors errors, String[] record) {
		try {
				if(StringUtils.isBlank(prePaidTicketRecordService.getPrePaidTicketToDurationCode(record))){
						  errors.reject(ContentCode.PRE_PAID_TICKET_INVALID_TO_DURATION.errorCode(),
				                   null, null);
				}
		}catch(Exception exc){
			errors.reject(ContentCode.PRE_PAID_TICKET_INVALID_TO_DURATION.errorCode(),
	                   null, null);
		}
		
	}

	protected void validateFromDuration(Errors errors, String[] record) {
		try {
				if(StringUtils.isBlank(prePaidTicketRecordService.getPrePaidTicketFromDurationCode(record))){
					  errors.reject(ContentCode.PRE_PAID_TICKET_INVALID_FROM__DURATION.errorCode(),
			                   null, null);
				}
		}catch(Exception exc){
			errors.reject(ContentCode.PRE_PAID_TICKET_INVALID_FROM__DURATION.errorCode(),
                   null, null);
		}
		
	}

	protected void validateEndZone(Errors errors, String[] record) {
		try {
				if(StringUtils.isBlank(prePaidTicketRecordService.getPrePaidTicketEndZone(record))){
					  errors.reject(ContentCode.PRE_PAID_TICKET_INVALID_END_ZONE.errorCode(),
			                   null, null);
				}
		}catch(Exception exc){
			errors.reject(ContentCode.PRE_PAID_TICKET_INVALID_END_ZONE.errorCode(),
	               null, null);
		}
		
	}

	protected void validateStartZone(Errors errors, String[] record) {
		try {
				if(StringUtils.isBlank(prePaidTicketRecordService.getPrePaidTicketStartZone(record))){
					  errors.reject(ContentCode.PRE_PAID_TICKET_INVALID_START_ZONE.errorCode(),
			                   null, null);
				}
		}catch(Exception exc){
			errors.reject(ContentCode.PRE_PAID_TICKET_INVALID_START_ZONE.errorCode(),null, null);
		}
	}

	protected void validateAddhocCode(Errors errors, String adhocCode) {
        if (!isParsableInteger(adhocCode)) {
    	   errors.reject(ContentCode.PRE_PAID_TCIKET_ADHOC_CODE_EMPTY.errorCode(),
                   null, null);
       }
	}

    protected boolean isParsableInteger(String adhocCode) {
        try {
            Integer.parseInt(adhocCode);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


	protected void validateForDuplicateForZoneDurationPassengerTypeAndDiscountType(Errors errors, String[] record) {
		final List<PrePaidTicketDTO> prePaidTicketDTOList =  paidTicketDataService.findAllByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(prePaidTicketRecordService.getPrePaidTicketFromDurationCode(record),
				 prePaidTicketRecordService.getPrePaidTicketToDurationCode(record),  Integer.parseInt(prePaidTicketRecordService.getPrePaidTicketStartZone(record)),  Integer.parseInt(prePaidTicketRecordService.getPrePaidTicketEndZone(record)), 
				   prePaidTicketRecordService.getPassengerTypeCode(record), prePaidTicketRecordService.getPrePaidTicketDiscountCode(record), prePaidTicketRecordService.getPrePaidTicketType(record));

		for(PrePaidTicketDTO prePaidTicketDTO : prePaidTicketDTOList) {
			if(hasOverlap(prePaidTicketDTO, record)){
				errors.reject(ContentCode.DUPLICATE_ADHOC_CODE_FILE_FIELD.errorCode(),
	                    new String[]{ prePaidTicketRecordService.getAdhocCode(record), prePaidTicketDTO.getAdHocPrePaidTicketCode()}, null);
			}
		
		}
		 
	}
	
	protected boolean hasOverlap(PrePaidTicketDTO prePaidTicketDTO, String[] record) {
		return !prePaidTicketDTO.getAdHocPrePaidTicketCode().equals(prePaidTicketRecordService.getAdhocCode(record));
		
	}

	protected void validatePriceIsValid(Errors errors, String[] record) {
		try {
			prePaidTicketRecordService.getPrePaidTicketPrice(record);
		} catch (Exception exc){
			 errors.reject(ContentCode.PRE_PAID_TICKET_PRICE_INVALID.errorCode(), null, null);
		}
	}
	
}
