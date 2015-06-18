package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.batch.domain.cubic.CurrentActionRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_2;
import static com.novacroft.nemo.tfl.batch.util.ConvertUtil.convertIntegerToString;
import static com.novacroft.nemo.tfl.batch.util.cubic.CubicConvertUtil.convertDateToString;

/**
 * Test fixtures and utilities for CurrentActionRecord unit tests
 */
public final class CurrentActionRecordTestUtil {
    public static final String COMMA = ",";

    public static final String PRESTIGE_ID_1 = OYSTER_NUMBER_1;
    public static final Integer REQUEST_SEQUENCE_NUMBER_1 = 1;
    public static final Integer PRODUCT_CODE_1 = 2;
    public static final Integer FARE_PAID_1 = 3;
    public static final Integer CURRENCY_1 = 0;
    public static final Integer PAYMENT_METHOD_CODE_1 = 4;
    public static final Integer PRE_PAY_VALUE_1 = 5;
    public static final Integer PICK_UP_LOCATION_1 = 6;
    public static final Date PPT_START_DATE_1 = DateTestUtil.getAug19();
    public static final Date PPT_EXPIRY_DATE_1 = DateTestUtil.getAug21();
    public static final Integer AUTO_LOAD_STATE_1 = 7;

    public static final String PRESTIGE_ID_2 = OYSTER_NUMBER_2;
    public static final Integer REQUEST_SEQUENCE_NUMBER_2 = 8;
    public static final Integer PRODUCT_CODE_2 = 9;
    public static final Integer FARE_PAID_2 = 10;
    public static final Integer CURRENCY_2 = 0;
    public static final Integer PAYMENT_METHOD_CODE_2 = 11;
    public static final Integer PRE_PAY_VALUE_2 = 12;
    public static final Integer PICK_UP_LOCATION_2 = 13;
    public static final Date PPT_START_DATE_2 = DateTestUtil.getAug20();
    public static final Date PPT_EXPIRY_DATE_2 = DateTestUtil.getAug20();
    public static final Integer AUTO_LOAD_STATE_2 = 14;

    public static CurrentActionRecord getTestCurrentActionRecord1() {
        return getTestCurrentActionRecord(PRESTIGE_ID_1, REQUEST_SEQUENCE_NUMBER_1, PRODUCT_CODE_1, FARE_PAID_1, CURRENCY_1,
                PAYMENT_METHOD_CODE_1, PRE_PAY_VALUE_1, PICK_UP_LOCATION_1, PPT_START_DATE_1, PPT_EXPIRY_DATE_1,
                AUTO_LOAD_STATE_1);
    }

    public static String[] getTestCurrentActionArray1() {
        return new String[]{PRESTIGE_ID_1, convertIntegerToString(REQUEST_SEQUENCE_NUMBER_1),
                convertIntegerToString(PRODUCT_CODE_1), convertIntegerToString(FARE_PAID_1), convertIntegerToString(CURRENCY_1),
                convertIntegerToString(PAYMENT_METHOD_CODE_1), convertIntegerToString(PRE_PAY_VALUE_1),
                convertIntegerToString(PICK_UP_LOCATION_1), convertDateToString(PPT_START_DATE_1),
                convertDateToString(PPT_EXPIRY_DATE_1), convertIntegerToString(AUTO_LOAD_STATE_1)};
    }

    public static String[] getTestCurrentActionArray1WithNullPrestigeID() {
        return new String[]{null, convertIntegerToString(REQUEST_SEQUENCE_NUMBER_1),
                convertIntegerToString(PRODUCT_CODE_1), convertIntegerToString(FARE_PAID_1), convertIntegerToString(CURRENCY_1),
                convertIntegerToString(PAYMENT_METHOD_CODE_1), convertIntegerToString(PRE_PAY_VALUE_1),
                convertIntegerToString(PICK_UP_LOCATION_1), convertDateToString(PPT_START_DATE_1),
                convertDateToString(PPT_EXPIRY_DATE_1), convertIntegerToString(AUTO_LOAD_STATE_1)};
    }

    public static String[] getTestCurrentActionArray2() {
        return new String[]{PRESTIGE_ID_2, convertIntegerToString(REQUEST_SEQUENCE_NUMBER_2),
                convertIntegerToString(PRODUCT_CODE_2), convertIntegerToString(FARE_PAID_2), convertIntegerToString(CURRENCY_2),
                convertIntegerToString(PAYMENT_METHOD_CODE_2), convertIntegerToString(PRE_PAY_VALUE_2),
                convertIntegerToString(PICK_UP_LOCATION_2), convertDateToString(PPT_START_DATE_2),
                convertDateToString(PPT_EXPIRY_DATE_2), convertIntegerToString(AUTO_LOAD_STATE_2)};
    }

    public static List<String[]> getTestCurrentActionArrayList1() {
        List<String[]> list = new ArrayList<String[]>();
        list.add(getTestCurrentActionArray1());
        return list;
    }

    public static List<String[]> getTestCurrentActionArrayList2() {
        List<String[]> list = getTestCurrentActionArrayList1();
        list.add(getTestCurrentActionArray2());
        return list;
    }

    public static String getTestCurrentActionCsv1() {
        String[] record = getTestCurrentActionArray1();
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

    public static CurrentActionRecord getTestCurrentActionRecord(String prestigeId, Integer requestSequenceNumber,
                                                                 Integer productCode, Integer farePaid, Integer currency,
                                                                 Integer paymentMethodCode, Integer prePayValue,
                                                                 Integer pickUpLocation, Date pptStartDate, Date pptExpiryDate,
                                                                 Integer autoLoadState) {
        return new CurrentActionRecord(prestigeId, requestSequenceNumber, productCode, farePaid, currency, paymentMethodCode,
                prePayValue, pickUpLocation, pptStartDate, pptExpiryDate, autoLoadState);
    }

    private CurrentActionRecordTestUtil() {
    }
}
