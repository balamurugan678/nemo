package com.novacroft.nemo.tfl.batch.validator.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.constant.NumberConstant;
import com.novacroft.nemo.tfl.batch.validator.impl.BaseDataTypeValidator;

/**
 * Validate data types used in Financial Service Centre (FSC) import files
 */
public abstract class BaseFinancialServicesCentreDataTypeValidator extends BaseDataTypeValidator {

    protected boolean isValidMonetaryValue(String value) {
        return value.matches(NumberConstant.MONETARY_VALUE_REG_EXP);
    }
}
