package com.novacroft.nemo.tfl.batch.validator.impl.cubic;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.novacroft.nemo.tfl.batch.util.cubic.CurrentActionRecordUtil.*;

/**
 * Validate a "raw" current action list file record
 */
@Component("currentActionValidator")
public class CurrentActionValidatorImpl extends BaseCubicFieldValidator implements Validator {
    @Override
    public boolean supports(Class<?> targetClass) {
        return String[].class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String[] record = (String[]) target;
        validatePrestigeId(errors, getPrestigeId(record));
        validateRequestSequenceNumber(errors, getRequestSequenceNumber(record));
        validateProductCode(errors, getProductCode(record));
        validateFarePaid(errors, getFarePaid(record));
        validateCurrency(errors, getCurrency(record));
        validatePaymentMethodCode(errors, getPaymentMethodCode(record));
        validatePrePayValue(errors, getPrePayValue(record));
        validatePickUpLocation(errors, getPickUpLocation(record));
        validatePptStartDate(errors, getPptStartDate(record));
        validatePptExpiryDate(errors, getPptExpiryDate(record));
        validateAutoLoadState(errors, getAutoLoadState(record));
    }
}
