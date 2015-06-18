package com.novacroft.nemo.tfl.batch.util.cubic;

import com.novacroft.nemo.common.constant.CommonPrivateError;
import com.novacroft.nemo.common.exception.BatchJobException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static com.novacroft.nemo.tfl.batch.constant.cubic.CubicDateConstant.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Conversion utilites for CUBIC records
 */
public final class CubicConvertUtil {

    public static Date convertStringToDate(String dateAsString) {

        if (isBlank(dateAsString)) {
            return null;
        }

        Date date = null;

        if (!dateAsString.matches(DATE_REG_EXP)) {
            throw new BatchJobException(String.format(CommonPrivateError.INVALID_DATE.message(), dateAsString));
        }

        try {
            date = new SimpleDateFormat(DATE_FORMAT).parse(dateAsString);
        } catch (ParseException e) {
            throw new BatchJobException(String.format(CommonPrivateError.INVALID_DATE.message(), dateAsString), e);
        }
        return date;
    }

    public static Date convertStringToDateAndTime(String dateAndTimeAsString) {

        if (isBlank(dateAndTimeAsString)) {
            return null;
        }

        Date dateAndTime = null;

        if (!dateAndTimeAsString.matches(DATE_AND_TIME_REG_EXP)) {
            throw new BatchJobException(String.format(CommonPrivateError.INVALID_DATE_AND_TIME.message(), dateAndTimeAsString));
        }

        try {
            dateAndTime = new SimpleDateFormat(DATE_AND_TIME_FORMAT).parse(dateAndTimeAsString);
        } catch (ParseException e) {
            throw new BatchJobException(String.format(CommonPrivateError.INVALID_DATE_AND_TIME.message(), dateAndTimeAsString));
        }
        return dateAndTime;
    }

    public static String convertDateToString(Date valueToConvert) {
        if (valueToConvert == null) {
            return EMPTY_STRING;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(valueToConvert);
    }

    public static String convertDateAndTimeToString(Date valueToConvert) {
        if (valueToConvert == null) {
            return EMPTY_STRING;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_AND_TIME_FORMAT);
        return sdf.format(valueToConvert);
    }

    private CubicConvertUtil() {
    }

}
