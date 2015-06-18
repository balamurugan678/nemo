package com.novacroft.nemo.tfl.batch.validator.impl.cubic;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.novacroft.nemo.tfl.batch.util.cubic.AdHocDistributionRecordUtil.*;

/**
 * Validate a "raw" ad-hoc distribution file record
 */
@Component("adHocDistributionValidator")
public class AdHocDistributionValidatorImpl extends BaseCubicFieldValidator implements Validator {
    @Override
    public boolean supports(Class<?> targetClass) {
        return String[].class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String[] record = (String[]) target;
        validatePrestigeId(errors, getPrestigeId(record));
        validatePickUpLocation(errors, getPickUpLocation(record));
        validatePickUpTime(errors, getPickUpTime(record));
        validateRequestSequenceNumber(errors, getRequestSequenceNumber(record));
        validateProductCode(errors, getProductCode(record));
        validatePptStartDate(errors, getPptStartDate(record));
        validatePptExpiryDate(errors, getPptExpiryDate(record));
        validatePrePayValue(errors, getPrePayValue(record));
        validateCurrency(errors, getCurrency(record));
        validateActionStatus(errors, getStatusOfAttemptedAction(record));
        validateFailureReasonCode(errors, getFailureReasonCode(record));
    }
}
