package com.novacroft.nemo.tfl.batch.validator.impl;

import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.tfl.batch.constant.NumberConstant.DIGIT_REG_EXP;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Import data type base validation
 */
public abstract class BaseDataTypeValidator {
    public boolean isValidInteger(String value) {
        try {
            Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public boolean isValidDigits(String value) {
        return value.matches(DIGIT_REG_EXP);
    }

    public boolean isValidDate(String value, String pattern) {
        assert (isNotBlank(value));
        assert (isNotBlank(pattern));
        return parse(value, pattern) != null;
    }
}
