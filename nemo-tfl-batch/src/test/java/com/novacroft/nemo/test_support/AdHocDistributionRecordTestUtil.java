package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.batch.domain.cubic.AdHocDistributionRecord;
import com.novacroft.nemo.tfl.batch.util.cubic.CubicConvertUtil;

import java.util.Date;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CurrentActionRecordTestUtil.COMMA;
import static com.novacroft.nemo.tfl.batch.util.ConvertUtil.convertIntegerToString;

/**
 * Fixtures and utilities for Ad Hoc Distribution unit tests
 */
public final class AdHocDistributionRecordTestUtil {
    public static String PRESTIGE_ID_1 = OYSTER_NUMBER_1;
    public static Integer PICK_UP_LOCATION_1 = 1;
    public static Date PICK_UP_TIME_1 = DateTestUtil.getAug19();
    public static Integer REQUEST_SEQUENCE_NUMBER_1 = 2;
    public static Integer PRODUCT_CODE_1 = 3;
    public static Date PPT_START_DATE_1 = DateTestUtil.getAug20();
    public static Date PPT_EXPIRY_DATE_1 = DateTestUtil.getAug21();
    public static Integer PRE_PAY_VALUE_1 = 4;
    public static Integer CURRENCY_1 = 0;
    public static String STATUS_OF_ATTEMPTED_ACTION_1 = "X";
    public static Integer FAILURE_REASON_CODE_1 = 5;

    public static String[] getAdHocDistributionRawTestRecord1() {
        return new String[]{PRESTIGE_ID_1, convertIntegerToString(PICK_UP_LOCATION_1),
                CubicConvertUtil.convertDateAndTimeToString(PICK_UP_TIME_1), convertIntegerToString(REQUEST_SEQUENCE_NUMBER_1),
                convertIntegerToString(PRODUCT_CODE_1), CubicConvertUtil.convertDateToString(PPT_START_DATE_1),
                CubicConvertUtil.convertDateToString(PPT_EXPIRY_DATE_1), convertIntegerToString(PRE_PAY_VALUE_1),
                convertIntegerToString(CURRENCY_1), STATUS_OF_ATTEMPTED_ACTION_1,
                convertIntegerToString(FAILURE_REASON_CODE_1)};
    }

    public static String[] getAdHocDistributionRawTestRecord1WithNullPrestigeID() {
        return new String[]{null, convertIntegerToString(PICK_UP_LOCATION_1),
                CubicConvertUtil.convertDateAndTimeToString(PICK_UP_TIME_1), convertIntegerToString(REQUEST_SEQUENCE_NUMBER_1),
                convertIntegerToString(PRODUCT_CODE_1), CubicConvertUtil.convertDateToString(PPT_START_DATE_1),
                CubicConvertUtil.convertDateToString(PPT_EXPIRY_DATE_1), convertIntegerToString(PRE_PAY_VALUE_1),
                convertIntegerToString(CURRENCY_1), STATUS_OF_ATTEMPTED_ACTION_1,
                convertIntegerToString(FAILURE_REASON_CODE_1)};
    }

    public static AdHocDistributionRecord getAdHocDistributionTestRecord1() {
        return getAdHocDistributionTestRecord(PRESTIGE_ID_1, PICK_UP_LOCATION_1, PICK_UP_TIME_1, REQUEST_SEQUENCE_NUMBER_1,
                PRODUCT_CODE_1, PPT_START_DATE_1, PPT_EXPIRY_DATE_1, PRE_PAY_VALUE_1, CURRENCY_1, STATUS_OF_ATTEMPTED_ACTION_1,
                FAILURE_REASON_CODE_1);
    }

    public static String getAdHocDistributionCsvTestRecord1() {
        String[] record = getAdHocDistributionRawTestRecord1();
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

    public static AdHocDistributionRecord getAdHocDistributionTestRecord(String prestigeId, Integer pickUpLocation,
                                                                         Date pickUpTime, Integer requestSequenceNumber,
                                                                         Integer productCode, Date pptStartDate,
                                                                         Date pptExpiryDate, Integer prePayValue,
                                                                         Integer currency, String statusOfAttemptedAction,
                                                                         Integer failureReasonCode) {
        return new AdHocDistributionRecord(prestigeId, pickUpLocation, pickUpTime, requestSequenceNumber, productCode,
                pptStartDate, pptExpiryDate, prePayValue, currency, statusOfAttemptedAction, failureReasonCode);
    }

    private AdHocDistributionRecordTestUtil() {
    }
}
