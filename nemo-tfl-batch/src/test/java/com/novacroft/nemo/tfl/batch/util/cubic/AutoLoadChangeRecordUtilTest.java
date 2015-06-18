package com.novacroft.nemo.tfl.batch.util.cubic;

import org.junit.Test;

import static com.novacroft.nemo.test_support.AutoLoadChangeRecordTestUtil.*;
import static com.novacroft.nemo.tfl.batch.util.ConvertUtil.convertIntegerToString;
import static com.novacroft.nemo.tfl.batch.util.cubic.AutoLoadChangeRecordUtil.*;
import static com.novacroft.nemo.tfl.batch.util.cubic.CubicConvertUtil.convertDateAndTimeToString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * AutoLoadChangeRecordUtil unit tests
 */
public class AutoLoadChangeRecordUtilTest {
    @Test
    public void shouldGetPrestigeId() {
        assertEquals(PRESTIGE_ID_1, getPrestigeId(getAutoLoadChangeRawTestRecord1()));
    }

    @Test
    public void shouldGetPrestigeIdWhenNull() {
        assertNull(getPrestigeId(getAutoLoadChangeRawTestRecord1WithNullPrestigeID()));
    }

    @Test
    public void shouldGetRequestSequenceNumber() {
        assertEquals(convertIntegerToString(REQUEST_SEQUENCE_NUMBER_1),
                getRequestSequenceNumber(getAutoLoadChangeRawTestRecord1()));
    }

    @Test
    public void shouldGetRequestSequenceNumberAsInteger() {
        assertEquals(REQUEST_SEQUENCE_NUMBER_1, getRequestSequenceNumberAsInteger(getAutoLoadChangeRawTestRecord1()));
    }

    @Test
    public void shouldGetPreviousAutoLoadConfiguration() {
        assertEquals(convertIntegerToString(PREVIOUS_AUTO_LOAD_CONFIGURATION_1),
                getPreviousAutoLoadConfiguration(getAutoLoadChangeRawTestRecord1()));
    }

    @Test
    public void shouldGetPreviousAutoLoadConfigurationAsInteger() {
        assertEquals(PREVIOUS_AUTO_LOAD_CONFIGURATION_1,
                getPreviousAutoLoadConfigurationAsInteger(getAutoLoadChangeRawTestRecord1()));
    }

    @Test
    public void shouldGetNewAutoLoadConfiguration() {
        assertEquals(convertIntegerToString(NEW_AUTO_LOAD_CONFIGURATION_1),
                getNewAutoLoadConfiguration(getAutoLoadChangeRawTestRecord1()));
    }

    @Test
    public void shouldGetNewAutoLoadConfigurationAsInteger() {
        assertEquals(NEW_AUTO_LOAD_CONFIGURATION_1, getNewAutoLoadConfigurationAsInteger(getAutoLoadChangeRawTestRecord1()));
    }

    @Test
    public void shouldGetPickUpLocation() {
        assertEquals(convertIntegerToString(PICK_UP_LOCATION_1), getPickUpLocation(getAutoLoadChangeRawTestRecord1()));
    }

    @Test
    public void shouldGetPickUpLocationAsInteger() {
        assertEquals(PICK_UP_LOCATION_1, getPickUpLocationAsInteger(getAutoLoadChangeRawTestRecord1()));
    }

    @Test
    public void shouldGetPickUpTime() {
        assertEquals(convertDateAndTimeToString(PICK_UP_TIME_1), getPickUpTime(getAutoLoadChangeRawTestRecord1()));
    }

    @Test
    public void shouldGetPickUpTimeAsDate() {
        assertEquals(PICK_UP_TIME_1, getPickUpTimeAsDate(getAutoLoadChangeRawTestRecord1()));
    }

    @Test
    public void shouldGetStatusOfAttemptedAction() {
        assertEquals(STATUS_OF_ATTEMPTED_ACTION_1,
                AutoLoadChangeRecordUtil.getStatusOfAttemptedAction(getAutoLoadChangeRawTestRecord1()));
    }

    @Test
    public void shouldGetFailureReasonCode() {
        assertEquals(convertIntegerToString(FAILURE_REASON_CODE_1),
                AutoLoadChangeRecordUtil.getFailureReasonCode(getAutoLoadChangeRawTestRecord1()));
    }

    @Test
    public void shouldGetFailureReasonCodeAsInteger() {
        assertEquals(FAILURE_REASON_CODE_1,
                AutoLoadChangeRecordUtil.getFailureReasonCodeAsInteger(getAutoLoadChangeRawTestRecord1()));
    }
}
