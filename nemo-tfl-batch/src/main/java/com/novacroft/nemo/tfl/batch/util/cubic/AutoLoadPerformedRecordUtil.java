package com.novacroft.nemo.tfl.batch.util.cubic;

import static com.novacroft.nemo.tfl.batch.util.ConvertUtil.convertStringToInteger;
import static com.novacroft.nemo.tfl.batch.util.cubic.CubicConvertUtil.convertStringToDateAndTime;

import java.util.Date;

import com.novacroft.nemo.common.utils.OysterCardNumberUtil;

/**
 * Utilities for working with "raw" auto load performed records
 */
public final class AutoLoadPerformedRecordUtil {
    public static final int PRESTIGE_ID_INDEX = 0;
    public static final int PICK_UP_LOCATION_INDEX = 1;
    public static final int BUS_ROUTE_ID_INDEX = 2;
    public static final int PICK_UP_TIME_INDEX = 3;
    public static final int AUTO_LOAD_CONFIGURATION_INDEX = 4;
    public static final int TOP_UP_AMOUNT_ADDED_INDEX = 5;
    public static final int CURRENCY_INDEX = 6;
    public static final String UNKNOWN = "Unknown";

    public static String getPrestigeId(String[] record) {
        String prestigeId = record[PRESTIGE_ID_INDEX];
        return null != prestigeId ? OysterCardNumberUtil.getFullCardNumber(prestigeId) : prestigeId;
    }

    public static String getPickUpLocation(String[] record) {
        return record[PICK_UP_LOCATION_INDEX];
    }

    public static String getPickUpLocationIgnoringUnknown(String[] record) {
        return isUnknown(getPickUpLocation(record)) ? null : getPickUpLocation(record);
    }

    public static Integer getPickUpLocationAsInteger(String[] record) {
        return convertStringToInteger(record[PICK_UP_LOCATION_INDEX]);
    }

    public static Integer getPickUpLocationAsIntegerIgnoringUnknown(String[] record) {
        return isUnknown(getPickUpLocation(record)) ? null : getPickUpLocationAsInteger(record);
    }

    public static String getBusRouteId(String[] record) {
        return record[BUS_ROUTE_ID_INDEX];
    }

    public static String getBusRouteIdIgnoringUnknown(String[] record) {
        return isUnknown(getBusRouteId(record)) ? null : getBusRouteId(record);
    }

    public static String getPickUpTime(String[] record) {
        return record[PICK_UP_TIME_INDEX];
    }

    public static Date getPickUpTimeAsDate(String[] record) {
        return convertStringToDateAndTime(record[PICK_UP_TIME_INDEX]);
    }

    public static String getAutoLoadConfiguration(String[] record) {
        return record[AUTO_LOAD_CONFIGURATION_INDEX];
    }

    public static String getAutoLoadConfigurationIgnoringUnknown(String[] record) {
        return isUnknown(getAutoLoadConfiguration(record)) ? null : getAutoLoadConfiguration(record);
    }

    public static Integer getAutoLoadConfigurationAsInteger(String[] record) {
        return convertStringToInteger(record[AUTO_LOAD_CONFIGURATION_INDEX]);
    }

    public static Integer getAutoLoadConfigurationAsIntegerIgnoringUnknown(String[] record) {
        return isUnknown(getAutoLoadConfiguration(record)) ? null : getAutoLoadConfigurationAsInteger(record);
    }

    public static String getTopUpAmountAdded(String[] record) {
        return record[TOP_UP_AMOUNT_ADDED_INDEX];
    }

    public static Integer getTopUpAmountAddedAsInteger(String[] record) {
        return convertStringToInteger(record[TOP_UP_AMOUNT_ADDED_INDEX]);
    }

    public static String getCurrency(String[] record) {
        return record[CURRENCY_INDEX];
    }

    public static String getCurrencyIgnoringUnknown(String[] record) {
        return isUnknown(getCurrency(record)) ? null : getCurrency(record);
    }

    public static Integer getCurrencyAsInteger(String[] record) {
        return convertStringToInteger(record[CURRENCY_INDEX]);
    }

    public static Integer getCurrencyAsIntegerIgnoringUnknown(String[] record) {
        return isUnknown(getCurrency(record)) ? null : getCurrencyAsInteger(record);
    }

    public static Boolean isUnknown(String value) {
        return UNKNOWN.equalsIgnoreCase(value);
    }

    private AutoLoadPerformedRecordUtil() {
    }
}
