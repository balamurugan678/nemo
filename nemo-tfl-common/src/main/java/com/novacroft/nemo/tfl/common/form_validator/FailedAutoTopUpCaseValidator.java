package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.INVALID_RESETTLEMENT_PERIOD_MORETHAN_LIMIT;
import static com.novacroft.nemo.tfl.common.constant.FailedAutoTopUpConstants.MAXIMUM_RESETTLEMENT_PERIOD;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_FAILED_AUTO_TOPUP_AMOUNT;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_RESETTLEMENT_PERIOD;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.impl.FailedAutoTopUpCaseCmdImpl;

/**
 * Ticket type validation
 */
@Component("failedAutoTopUpCaseValidator")
public class FailedAutoTopUpCaseValidator extends BaseValidator {

    @Override
    public boolean supports(Class<?> targetClass) {
        return FailedAutoTopUpCaseCmdImpl.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
    	FailedAutoTopUpCaseCmdImpl cmd = (FailedAutoTopUpCaseCmdImpl) target;
    	rejectIfMandatoryFieldEmpty(errors, FIELD_FAILED_AUTO_TOPUP_AMOUNT);
    	if(!errors.hasErrors()) {
    		rejectIfResettlementPeriodIsGreaterThanLimitInSystem(errors, FIELD_RESETTLEMENT_PERIOD, cmd.getResettlementPeriod());
    	}
    }

	protected void rejectIfResettlementPeriodIsGreaterThanLimitInSystem(Errors errors, String field, Integer resettlementPeriod) {
		if(resettlementPeriod > MAXIMUM_RESETTLEMENT_PERIOD){
			errors.rejectValue(field, INVALID_RESETTLEMENT_PERIOD_MORETHAN_LIMIT.errorCode());
		}
	}
}
