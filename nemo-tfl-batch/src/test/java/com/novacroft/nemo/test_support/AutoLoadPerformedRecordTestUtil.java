package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.batch.domain.cubic.AutoLoadPerformedRecord;

import java.util.Date;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_2;
import static com.novacroft.nemo.test_support.CurrentActionRecordTestUtil.COMMA;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug20;
import static com.novacroft.nemo.tfl.batch.util.ConvertUtil.convertIntegerToString;
import static com.novacroft.nemo.tfl.batch.util.cubic.CubicConvertUtil.convertDateAndTimeToString;

/**
 * Auto Load Performed unit tests fixtures and utilities
 */
public final class AutoLoadPerformedRecordTestUtil {
    public static String PRESTIGE_ID_1 = OYSTER_NUMBER_1;
    public static Integer PICK_UP_LOCATION_1 = 1;
    public static String BUS_ROUTE_ID_1 = "ABC";
    public static Date PICK_UP_TIME_1 = getAug19();
    public static Integer AUTO_LOAD_CONFIGURATION_1 = 2;
    public static Integer TOP_UP_AMOUNT_ADDED_1 = 345;
    public static Integer CURRENCY_1 = 0;

    public static String PRESTIGE_ID_2 = OYSTER_NUMBER_2;
    public static String PICK_UP_LOCATION_2 = "UNKNOWN";
    public static String BUS_ROUTE_ID_2 = "UNKNOWN";
    public static Date PICK_UP_TIME_2 = getAug20();
    public static String AUTO_LOAD_CONFIGURATION_2 = "UNKNOWN";
    public static Integer TOP_UP_AMOUNT_ADDED_2 = 567;
    public static String CURRENCY_2 = "UNKNOWN";

    public static String[] getAutoLoadPerformedRawTestRecord1() {
        return new String[]{PRESTIGE_ID_1, convertIntegerToString(PICK_UP_LOCATION_1), BUS_ROUTE_ID_1,
                convertDateAndTimeToString(PICK_UP_TIME_1), convertIntegerToString(AUTO_LOAD_CONFIGURATION_1),
                convertIntegerToString(TOP_UP_AMOUNT_ADDED_1), convertIntegerToString(CURRENCY_1)};
    }

    public static String[] getAutoLoadPerformedRawTestRecord1WithNullPrestigeID() {
        return new String[]{null, convertIntegerToString(PICK_UP_LOCATION_1), BUS_ROUTE_ID_1,
                convertDateAndTimeToString(PICK_UP_TIME_1), convertIntegerToString(AUTO_LOAD_CONFIGURATION_1),
                convertIntegerToString(TOP_UP_AMOUNT_ADDED_1), convertIntegerToString(CURRENCY_1)};
    }

    public static String[] getAutoLoadPerformedRawTestRecord2() {
        return new String[]{PRESTIGE_ID_2, PICK_UP_LOCATION_2, BUS_ROUTE_ID_2, convertDateAndTimeToString(PICK_UP_TIME_2),
                AUTO_LOAD_CONFIGURATION_2, convertIntegerToString(TOP_UP_AMOUNT_ADDED_2), CURRENCY_2};
    }

    public static AutoLoadPerformedRecord getAutoLoadPerformedTestRecord1() {
        return getAutoLoadPerformedTestRecord(PRESTIGE_ID_1, PICK_UP_LOCATION_1, BUS_ROUTE_ID_1, PICK_UP_TIME_1,
                AUTO_LOAD_CONFIGURATION_1, TOP_UP_AMOUNT_ADDED_1, CURRENCY_1);
    }

    public static String getAutoLoadPerformedCsvTestRecord1() {
        String[] record = getAutoLoadPerformedRawTestRecord1();
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

    public static AutoLoadPerformedRecord getAutoLoadPerformedTestRecord(String prestigeId, Integer pickUpLocation,
                                                                         String busRouteId, Date pickUpTime,
                                                                         Integer autoLoadConfiguration,
                                                                         Integer topUpAmountAdded, Integer currency) {
        return new AutoLoadPerformedRecord(prestigeId, pickUpLocation, busRouteId, pickUpTime, autoLoadConfiguration,
                topUpAmountAdded, currency);
    }

    private AutoLoadPerformedRecordTestUtil() {
    }
}
