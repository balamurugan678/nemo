package com.novacroft.nemo.tfl.batch.util;

import com.novacroft.nemo.common.constant.CommonPrivateError;
import com.novacroft.nemo.common.exception.BatchJobException;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.novacroft.nemo.common.constant.DateConstant.ISO_8601_DATE_PATTERN;
import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static com.novacroft.nemo.tfl.batch.constant.NumberConstant.DIGIT_REG_EXP;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Conversion utilities for import file data.
 */
public final class ConvertUtil {

    public static Integer convertStringToInteger(String integerAsString) {

        if (isBlank(integerAsString)) {
            return null;
        }

        Integer i = null;

        if (!integerAsString.matches(DIGIT_REG_EXP)) {
            throw new BatchJobException(String.format(CommonPrivateError.INVALID_INTEGER.message(), integerAsString));
        }

        try {
            i = Integer.valueOf(integerAsString);
        } catch (NumberFormatException e) {
            throw new BatchJobException(String.format(CommonPrivateError.INVALID_INTEGER.message(), integerAsString), e);
        }

        return i;
    }

    public static String convertIntegerToString(Integer valueToConvert) {
        if (valueToConvert == null) {
            return EMPTY_STRING;
        }

        return String.valueOf(valueToConvert);
    }

    public static String convertDateToIsoFormatString(Date valueToConvert) {
        if (valueToConvert == null) {
            return EMPTY_STRING;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(ISO_8601_DATE_PATTERN);
        return sdf.format(valueToConvert);
    }

    public static Long convertStringToLong(String valueAsString) {
        try {
            return NumberFormat.getInstance().parse(valueAsString).longValue();
        } catch (ParseException e) {
            throw new BatchJobException(e.getMessage(), e);
        }
    }

    public static Float convertStringToFloat(String valueAsString) {
        try {
            return new DecimalFormat().parse(valueAsString).floatValue();
        } catch (ParseException e) {
            throw new BatchJobException(e.getMessage(), e);
        }
    }

    private ConvertUtil() {
    }
}
