package com.novacroft.nemo.tfl.batch.validator.impl.cubic;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.novacroft.nemo.tfl.batch.util.cubic.AutoLoadChangeRecordUtil.*;

/**
 * Validate a "raw" auto load change file record
 */
@Component("autoLoadChangeValidator")
public class AutoLoadChangeValidatorImpl extends BaseCubicFieldValidator implements Validator {
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
        validateAutoLoadConfiguration(errors, getPreviousAutoLoadConfiguration(record));
        validateAutoLoadConfiguration(errors, getNewAutoLoadConfiguration(record));
        validateActionStatus(errors, getStatusOfAttemptedAction(record));
        validateFailureReasonCode(errors, getFailureReasonCode(record));
    }
}
