package com.novacroft.nemo.tfl.batch.validator.impl.cubic;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.novacroft.nemo.tfl.batch.util.cubic.AutoLoadPerformedRecordUtil.*;

/**
 * Validate a "raw" auto load performed file record
 */
@Component("autoLoadPerformedValidator")
public class AutoLoadPerformedValidatorImpl extends BaseCubicFieldValidator implements Validator {
    @Override
    public boolean supports(Class<?> targetClass) {
        return String[].class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String[] record = (String[]) target;
        validatePrestigeId(errors, getPrestigeId(record));
        validatePickUpLocation(errors, getPickUpLocation(record));
        validateBusRouteId(errors, getBusRouteId(record));
        validatePickUpTime(errors, getPickUpTime(record));
        validateAutoLoadConfiguration(errors, getAutoLoadConfiguration(record));
        validateTopUpAmount(errors, getTopUpAmountAdded(record));
        validateCurrency(errors, getCurrency(record));
    }

    @Override
    protected boolean isValidPickUpLocation(String value) {
        return isUnknown(value) || super.isValidPickUpLocation(value);
    }

    @Override
    protected boolean isValidBusRouteId(String value) {
        return isUnknown(value) || super.isValidBusRouteId(value);
    }

    @Override
    protected boolean isValidAutoLoadConfiguration(String value) {
        return isUnknown(value) || super.isValidAutoLoadConfiguration(value);
    }

    @Override
    protected boolean isValidCurrency(String value) {
        return isUnknown(value) || super.isValidCurrency(value);
    }
}
