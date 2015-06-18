package com.novacroft.nemo.tfl.batch.util.cubic;

import org.junit.Test;

import static com.novacroft.nemo.test_support.AutoLoadPerformedRecordTestUtil.*;
import static com.novacroft.nemo.tfl.batch.util.ConvertUtil.convertIntegerToString;
import static com.novacroft.nemo.tfl.batch.util.cubic.AutoLoadPerformedRecordUtil.*;
import static com.novacroft.nemo.tfl.batch.util.cubic.CubicConvertUtil.convertDateAndTimeToString;
import static org.junit.Assert.*;

/**
 * AutoLoadPerformedRecordUtil unit tests
 */
public class AutoLoadPerformedRecordUtilTest {
    @Test
    public void shouldGetPrestigeId() {
        assertEquals(PRESTIGE_ID_1, getPrestigeId(getAutoLoadPerformedRawTestRecord1()));
    }

    @Test
    public void shouldGetPrestigeIdWhenNull() {
        assertNull(getPrestigeId(getAutoLoadPerformedRawTestRecord1WithNullPrestigeID()));
    }

    @Test
    public void shouldGetPickUpLocation() {
        assertEquals(convertIntegerToString(PICK_UP_LOCATION_1), getPickUpLocation(getAutoLoadPerformedRawTestRecord1()));
    }

    @Test
    public void shouldGetPickUpLocationAsInteger() {
        assertEquals(PICK_UP_LOCATION_1, getPickUpLocationAsInteger(getAutoLoadPerformedRawTestRecord1()));
    }

    @Test
    public void shouldGetBusRouteId() {
        assertEquals(BUS_ROUTE_ID_1, getBusRouteId(getAutoLoadPerformedRawTestRecord1()));
    }

    @Test
    public void shouldGetPickUpTime() {
        assertEquals(convertDateAndTimeToString(PICK_UP_TIME_1), getPickUpTime(getAutoLoadPerformedRawTestRecord1()));
    }

    @Test
    public void shouldGetPickUpTimeAsDate() {
        assertEquals(PICK_UP_TIME_1, getPickUpTimeAsDate(getAutoLoadPerformedRawTestRecord1()));
    }

    @Test
    public void shouldGetAutoLoadConfiguration() {
        assertEquals(convertIntegerToString(AUTO_LOAD_CONFIGURATION_1),
                getAutoLoadConfiguration(getAutoLoadPerformedRawTestRecord1()));
    }

    @Test
    public void shouldGetAutoLoadConfigurationAsInteger() {
        assertEquals(AUTO_LOAD_CONFIGURATION_1, getAutoLoadConfigurationAsInteger(getAutoLoadPerformedRawTestRecord1()));
    }

    @Test
    public void shouldGetTopUpAmountAdded() {
        assertEquals(convertIntegerToString(TOP_UP_AMOUNT_ADDED_1), getTopUpAmountAdded(getAutoLoadPerformedRawTestRecord1()));
    }

    @Test
    public void shouldGetTopUpAmountAddedAsInteger() {
        assertEquals(TOP_UP_AMOUNT_ADDED_1, getTopUpAmountAddedAsInteger(getAutoLoadPerformedRawTestRecord1()));
    }

    @Test
    public void shouldGetCurrency() {
        assertEquals(convertIntegerToString(CURRENCY_1), getCurrency(getAutoLoadPerformedRawTestRecord1()));
    }

    @Test
    public void shouldGetCurrencyAsInteger() {
        assertEquals(CURRENCY_1, getCurrencyAsInteger(getAutoLoadPerformedRawTestRecord1()));
    }

    @Test
    public void isUnknownShouldReturnTrue() {
        assertTrue(isUnknown("unknown"));
    }

    @Test
    public void isUnknownShouldReturnFalse() {
        assertFalse(isUnknown("N236"));
    }

    @Test
    public void getPickUpLocationIgnoringUnknownShouldReturnNull() {
        assertNull(getPickUpLocationIgnoringUnknown(getAutoLoadPerformedRawTestRecord2()));
    }

    @Test
    public void getPickUpLocationIgnoringUnknownShouldReturnValue() {
        assertEquals(convertIntegerToString(PICK_UP_LOCATION_1),
                getPickUpLocationIgnoringUnknown(getAutoLoadPerformedRawTestRecord1()));
    }

    @Test
    public void getPickUpLocationAsIntegerIgnoringUnknownShouldReturnNull() {
        assertNull(getPickUpLocationAsIntegerIgnoringUnknown(getAutoLoadPerformedRawTestRecord2()));
    }

    @Test
    public void getPickUpLocationAsIntegerIgnoringUnknownShouldReturnValue() {
        assertEquals(PICK_UP_LOCATION_1, getPickUpLocationAsIntegerIgnoringUnknown(getAutoLoadPerformedRawTestRecord1()));
    }

    @Test
    public void getBusRouteIdIgnoringUnknownShouldReturnNull() {
        assertNull(getBusRouteIdIgnoringUnknown(getAutoLoadPerformedRawTestRecord2()));
    }

    @Test
    public void getBusRouteIdIgnoringUnknownShouldReturnValue() {
        assertEquals(BUS_ROUTE_ID_1, getBusRouteIdIgnoringUnknown(getAutoLoadPerformedRawTestRecord1()));
    }

    @Test
    public void getAutoLoadConfigurationIgnoringUnknownShouldReturnNull() {
        assertNull(getAutoLoadConfigurationIgnoringUnknown(getAutoLoadPerformedRawTestRecord2()));
    }

    @Test
    public void getAutoLoadConfigurationIgnoringUnknownShouldReturnValue() {
        assertEquals(convertIntegerToString(AUTO_LOAD_CONFIGURATION_1),
                getAutoLoadConfigurationIgnoringUnknown(getAutoLoadPerformedRawTestRecord1()));
    }

    @Test
    public void getAutoLoadConfigurationAsIntegerIgnoringUnknownShouldReturnNull() {
        assertNull(getAutoLoadConfigurationAsIntegerIgnoringUnknown(getAutoLoadPerformedRawTestRecord2()));
    }

    @Test
    public void getAutoLoadConfigurationAsIntegerIgnoringUnknownShouldReturnValue() {
        assertEquals(AUTO_LOAD_CONFIGURATION_1,
                getAutoLoadConfigurationAsIntegerIgnoringUnknown(getAutoLoadPerformedRawTestRecord1()));
    }

    @Test
    public void getCurrencyIgnoringUnknownShouldReturnNull() {
        assertNull(getCurrencyIgnoringUnknown(getAutoLoadPerformedRawTestRecord2()));
    }

    @Test
    public void getCurrencyIgnoringUnknownShouldReturnValue() {
        assertEquals(convertIntegerToString(CURRENCY_1), getCurrencyIgnoringUnknown(getAutoLoadPerformedRawTestRecord1()));
    }

    @Test
    public void getCurrencyAsIntegerIgnoringUnknownShouldReturnNull() {
        assertNull(getCurrencyAsIntegerIgnoringUnknown(getAutoLoadPerformedRawTestRecord2()));
    }

    @Test
    public void getCurrencyAsIntegerIgnoringUnknownShouldReturnValue() {
        assertEquals(CURRENCY_1, getCurrencyAsIntegerIgnoringUnknown(getAutoLoadPerformedRawTestRecord1()));
    }
}
