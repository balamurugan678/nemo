package com.novacroft.nemo.tfl.batch.validator.impl.cubic;

import com.novacroft.nemo.common.exception.BatchJobException;
import com.novacroft.nemo.tfl.batch.validator.impl.BaseDataTypeValidator;

import static com.novacroft.nemo.tfl.batch.util.cubic.CubicConvertUtil.convertStringToDate;
import static com.novacroft.nemo.tfl.batch.util.cubic.CubicConvertUtil.convertStringToDateAndTime;

/**
 * CUBIC import data type base validation
 */
public abstract class BaseCubicDataTypeValidator extends BaseDataTypeValidator {
    protected boolean isValidDateAndTime(String value) {
        try {
            convertStringToDateAndTime(value);
        } catch (BatchJobException e) {
            return false;
        }
        return true;
    }

    protected boolean isValidDate(String value) {
        try {
            convertStringToDate(value);
        } catch (BatchJobException e) {
            return false;
        }
        return true;
    }

}
