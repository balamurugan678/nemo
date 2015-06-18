package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.batch.domain.cubic.AutoLoadChangeRecord;

import java.util.Date;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CurrentActionRecordTestUtil.COMMA;
import static com.novacroft.nemo.tfl.batch.util.ConvertUtil.convertIntegerToString;
import static com.novacroft.nemo.tfl.batch.util.cubic.CubicConvertUtil.convertDateAndTimeToString;

/**
 * Auto Load Change unit tests fixtures and utilities
 */
public final class AutoLoadChangeRecordTestUtil {
    public static String PRESTIGE_ID_1 = OYSTER_NUMBER_1;
    public static Integer PICK_UP_LOCATION_1 = 1;
    public static Date PICK_UP_TIME_1 = DateTestUtil.getAug19();
    public static Integer REQUEST_SEQUENCE_NUMBER_1 = 2;
    public static String STATUS_OF_ATTEMPTED_ACTION_1 = "X";
    public static Integer FAILURE_REASON_CODE_1 = 5;
    public static Integer PREVIOUS_AUTO_LOAD_CONFIGURATION_1 = 1;
    public static Integer NEW_AUTO_LOAD_CONFIGURATION_1 = 2;

    public static String[] getAutoLoadChangeRawTestRecord1() {
        return new String[]{PRESTIGE_ID_1, convertIntegerToString(PICK_UP_LOCATION_1),
                convertDateAndTimeToString(PICK_UP_TIME_1), convertIntegerToString(REQUEST_SEQUENCE_NUMBER_1),
                convertIntegerToString(PREVIOUS_AUTO_LOAD_CONFIGURATION_1),
                convertIntegerToString(NEW_AUTO_LOAD_CONFIGURATION_1), STATUS_OF_ATTEMPTED_ACTION_1,
                convertIntegerToString(FAILURE_REASON_CODE_1)};
    }

    public static String[] getAutoLoadChangeRawTestRecord1WithNullPrestigeID() {
        return new String[]{null, convertIntegerToString(PICK_UP_LOCATION_1),
                convertDateAndTimeToString(PICK_UP_TIME_1), convertIntegerToString(REQUEST_SEQUENCE_NUMBER_1),
                convertIntegerToString(PREVIOUS_AUTO_LOAD_CONFIGURATION_1),
                convertIntegerToString(NEW_AUTO_LOAD_CONFIGURATION_1), STATUS_OF_ATTEMPTED_ACTION_1,
                convertIntegerToString(FAILURE_REASON_CODE_1)};
    }

    public static AutoLoadChangeRecord getAutoLoadChangeTestRecord1() {
        return getAutoLoadChangeTestRecord(PRESTIGE_ID_1, PICK_UP_LOCATION_1, PICK_UP_TIME_1, REQUEST_SEQUENCE_NUMBER_1,
                PREVIOUS_AUTO_LOAD_CONFIGURATION_1, NEW_AUTO_LOAD_CONFIGURATION_1, STATUS_OF_ATTEMPTED_ACTION_1,
                FAILURE_REASON_CODE_1);
    }

    public static String getAutoLoadChangeCsvTestRecord1() {
        String[] record = getAutoLoadChangeRawTestRecord1();
        StringBuilder stringBuilder = new StringBuilder();
        boolean notFirst = false;
        for (int i = 0; i < record.length; i++) {
            if (notFirst) {
                stringBuilder.append(COMMA);
            } else {
                notFirst = true;
            }
            stringBuilder.append(record[i]);
        }
        return stringBuilder.toString().trim();
    }

    public static AutoLoadChangeRecord getAutoLoadChangeTestRecord(String prestigeId, Integer pickUpLocation, Date pickUpTime,
                                                                   Integer requestSequenceNumber,
                                                                   Integer previousAutoLoadConfiguration,
                                                                   Integer newAutoLoadConfiguration,
                                                                   String statusOfAttemptedAction, Integer failureReasonCode) {
        return new AutoLoadChangeRecord(prestigeId, pickUpLocation, pickUpTime, requestSequenceNumber,
                previousAutoLoadConfiguration, newAutoLoadConfiguration, statusOfAttemptedAction, failureReasonCode);
    }

    private AutoLoadChangeRecordTestUtil() {
    }
}
