package com.novacroft.nemo.tfl.batch.util.cubic;

import static com.novacroft.nemo.tfl.batch.util.ConvertUtil.convertStringToInteger;
import static com.novacroft.nemo.tfl.batch.util.cubic.CubicConvertUtil.convertStringToDateAndTime;

import java.util.Date;

import com.novacroft.nemo.common.utils.OysterCardNumberUtil;

/**
 * Utilities for working with "raw" auto load change records
 */
public final class AutoLoadChangeRecordUtil {
    public static final int PRESTIGE_ID_INDEX = 0;
    public static final int PICK_UP_LOCATION_INDEX = 1;
    public static final int PICK_UP_TIME_INDEX = 2;
    public static final int REQUEST_SEQUENCE_NUMBER_INDEX = 3;
    public static final int PREVIOUS_AUTO_LOAD_CONFIGURATION = 4;
    public static final int NEW_AUTO_LOAD_CONFIGURATION = 5;
    public static final int STATUS_OF_ATTEMPTED_ACTION_INDEX = 6;
    public static final int FAILURE_REASON_CODE_INDEX = 7;

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

    public static String getPickUpLocation(String[] record) {
        return record[PICK_UP_LOCATION_INDEX];
    }

    public static Integer getPickUpLocationAsInteger(String[] record) {
        return convertStringToInteger(record[PICK_UP_LOCATION_INDEX]);
    }

    public static String getPickUpTime(String[] record) {
        return record[PICK_UP_TIME_INDEX];
    }

    public static Date getPickUpTimeAsDate(String[] record) {
        return convertStringToDateAndTime(record[PICK_UP_TIME_INDEX]);
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

    public static String getPreviousAutoLoadConfiguration(String[] record) {
        return record[PREVIOUS_AUTO_LOAD_CONFIGURATION];
    }

    public static Integer getPreviousAutoLoadConfigurationAsInteger(String[] record) {
        return convertStringToInteger(record[PREVIOUS_AUTO_LOAD_CONFIGURATION]);
    }

    public static String getNewAutoLoadConfiguration(String[] record) {
        return record[NEW_AUTO_LOAD_CONFIGURATION];
    }

    public static Integer getNewAutoLoadConfigurationAsInteger(String[] record) {
        return convertStringToInteger(record[NEW_AUTO_LOAD_CONFIGURATION]);
    }

    private AutoLoadChangeRecordUtil() {
    }
}
