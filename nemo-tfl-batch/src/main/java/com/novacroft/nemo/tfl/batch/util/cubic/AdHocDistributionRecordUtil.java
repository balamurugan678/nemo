package com.novacroft.nemo.tfl.batch.util.cubic;

import static com.novacroft.nemo.tfl.batch.util.ConvertUtil.convertStringToInteger;

import java.util.Date;

import com.novacroft.nemo.common.utils.OysterCardNumberUtil;

/**
 * Utilities for working with "raw" ad-hoc distribution records
 */

public final class AdHocDistributionRecordUtil {
    public static final int PRESTIGE_ID_INDEX = 0;
    public static final int PICK_UP_LOCATION_INDEX = 1;
    public static final int PICK_UP_TIME_INDEX = 2;
    public static final int REQUEST_SEQUENCE_NUMBER_INDEX = 3;
    public static final int PRODUCT_CODE_INDEX = 4;
    public static final int PPT_START_DATE_INDEX = 5;
    public static final int PPT_EXPIRY_DATE_INDEX = 6;
    public static final int PRE_PAY_VALUE_INDEX = 7;
    public static final int CURRENCY_INDEX = 8;
    public static final int STATUS_OF_ATTEMPTED_ACTION_INDEX = 9;
    public static final int FAILURE_REASON_CODE_INDEX = 10;

    public static String getPrestigeId(String[] record) {
        String prestigeId = record[PRESTIGE_ID_INDEX];
        return null != prestigeId ? OysterCardNumberUtil.getFullCardNumber(prestigeId) : prestigeId;
    }

    public static String getRequestSequenceNumber(String[] record) {
        return record[REQUEST_SEQUENCE_NUMBER_INDEX];
    }

    public static Integer getRequestSequenceNumberAsInteger(String[] record) {
        return convertStringToInteger(record[REQUEST_SEQUENCE_NUMBER_INDEX]);
    }

    public static String getProductCode(String[] record) {
        return record[PRODUCT_CODE_INDEX];
    }

    public static Integer getProductCodeAsInteger(String[] record) {
        return convertStringToInteger(record[PRODUCT_CODE_INDEX]);
    }

    public static String getCurrency(String[] record) {
        return record[CURRENCY_INDEX];
    }

    public static Integer getCurrencyAsInteger(String[] record) {
        return convertStringToInteger(record[CURRENCY_INDEX]);
    }

    public static String getPrePayValue(String[] record) {
        return record[PRE_PAY_VALUE_INDEX];
    }

    public static Integer getPrePayValueAsInteger(String[] record) {
        return convertStringToInteger(record[PRE_PAY_VALUE_INDEX]);
    }

    public static String getPickUpLocation(String[] record) {
        return record[PICK_UP_LOCATION_INDEX];
    }

    public static Integer getPickUpLocationAsInteger(String[] record) {
        return convertStringToInteger(record[PICK_UP_LOCATION_INDEX]);
    }

    public static String getPptStartDate(String[] record) {
        return record[PPT_START_DATE_INDEX];
    }

    public static Date getPptStartDateAsDate(String[] record) {
        return CubicConvertUtil.convertStringToDate(record[PPT_START_DATE_INDEX]);
    }

    public static String getPptExpiryDate(String[] record) {
        return record[PPT_EXPIRY_DATE_INDEX];
    }

    public static Date getPptExpiryDateAsDate(String[] record) {
        return CubicConvertUtil.convertStringToDate(record[PPT_EXPIRY_DATE_INDEX]);
    }

    public static String getPickUpTime(String[] record) {
        return record[PICK_UP_TIME_INDEX];
    }

    public static Date getPickUpTimeAsDate(String[] record) {
        return CubicConvertUtil.convertStringToDateAndTime(record[PICK_UP_TIME_INDEX]);
    }

    public static String getStatusOfAttemptedAction(String[] record) {
        return record[STATUS_OF_ATTEMPTED_ACTION_INDEX];
    }

    public static String getFailureReasonCode(String[] record) {
        return record[FAILURE_REASON_CODE_INDEX];
    }

    public static Integer getFailureReasonCodeAsInteger(String[] record) {
        return convertStringToInteger(record[FAILURE_REASON_CODE_INDEX]);
    }

    private AdHocDistributionRecordUtil() {
    }
}
