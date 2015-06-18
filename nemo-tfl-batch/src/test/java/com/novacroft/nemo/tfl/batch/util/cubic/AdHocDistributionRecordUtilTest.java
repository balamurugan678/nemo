package com.novacroft.nemo.tfl.batch.util.cubic;

import org.junit.Test;

import static com.novacroft.nemo.test_support.AdHocDistributionRecordTestUtil.*;
import static com.novacroft.nemo.tfl.batch.util.ConvertUtil.convertIntegerToString;
import static com.novacroft.nemo.tfl.batch.util.cubic.AdHocDistributionRecordUtil.*;
import static org.apache.commons.lang3.time.DateUtils.isSameDay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

/**
 * AdHocDistributionRecordUtil unit tests
 */
public class AdHocDistributionRecordUtilTest {
    @Test
    public void shouldGetPrestigeId() {
        assertEquals(PRESTIGE_ID_1, getPrestigeId(getAdHocDistributionRawTestRecord1()));
    }

    @Test
    public void shouldGetPrestigeIdWhenNull() {
        assertNull(getPrestigeId(getAdHocDistributionRawTestRecord1WithNullPrestigeID()));
    }

    @Test
    public void shouldGetRequestSequenceNumber() {
        assertEquals(convertIntegerToString(REQUEST_SEQUENCE_NUMBER_1),
                getRequestSequenceNumber(getAdHocDistributionRawTestRecord1()));
    }

    @Test
    public void shouldGetRequestSequenceNumberAsInteger() {
        assertEquals(REQUEST_SEQUENCE_NUMBER_1, getRequestSequenceNumberAsInteger(getAdHocDistributionRawTestRecord1()));
    }

    @Test
    public void shouldGetProductCode() {
        assertEquals(convertIntegerToString(PRODUCT_CODE_1), getProductCode(getAdHocDistributionRawTestRecord1()));
    }

    @Test
    public void shouldGetProductCodeAsInteger() {
        assertEquals(PRODUCT_CODE_1, getProductCodeAsInteger(getAdHocDistributionRawTestRecord1()));
    }

    @Test
    public void shouldGetCurrency() {
        assertEquals(convertIntegerToString(CURRENCY_1), getCurrency(getAdHocDistributionRawTestRecord1()));
    }

    @Test
    public void shouldGetCurrencyAsInteger() {
        assertEquals(CURRENCY_1, getCurrencyAsInteger(getAdHocDistributionRawTestRecord1()));
    }

    @Test
    public void shouldGetPrePayValue() {
        assertEquals(convertIntegerToString(PRE_PAY_VALUE_1), getPrePayValue(getAdHocDistributionRawTestRecord1()));
    }

    @Test
    public void shouldGetPrePayValueAsInteger() {
        assertEquals(PRE_PAY_VALUE_1, getPrePayValueAsInteger(getAdHocDistributionRawTestRecord1()));
    }

    @Test
    public void shouldGetPickUpLocation() {
        assertEquals(convertIntegerToString(PICK_UP_LOCATION_1), getPickUpLocation(getAdHocDistributionRawTestRecord1()));
    }

    @Test
    public void shouldGetPickUpLocationAsInteger() {
        assertEquals(PICK_UP_LOCATION_1, getPickUpLocationAsInteger(getAdHocDistributionRawTestRecord1()));
    }

    @Test
    public void shouldGetPptStartDate() {
        assertEquals(CubicConvertUtil.convertDateToString(PPT_START_DATE_1),
                getPptStartDate(getAdHocDistributionRawTestRecord1()));
    }

    @Test
    public void shouldGetPptStartDateAsDate() {
        assertTrue(isSameDay(PPT_START_DATE_1, getPptStartDateAsDate(getAdHocDistributionRawTestRecord1())));
    }

    @Test
    public void shouldGetPptExpiryDate() {
        assertEquals(CubicConvertUtil.convertDateToString(PPT_EXPIRY_DATE_1),
                getPptExpiryDate(getAdHocDistributionRawTestRecord1()));
    }

    @Test
    public void shouldGetPptExpiryDateAsDate() {
        assertTrue(isSameDay(PPT_EXPIRY_DATE_1, getPptExpiryDateAsDate(getAdHocDistributionRawTestRecord1())));
    }

    @Test
    public void shouldGetPickUpTime() {
        assertEquals(CubicConvertUtil.convertDateAndTimeToString(PICK_UP_TIME_1),
                getPickUpTime(getAdHocDistributionRawTestRecord1()));
    }

    @Test
    public void shouldGetPickUpTimeAsDate() {
        assertEquals(PICK_UP_TIME_1, getPickUpTimeAsDate(getAdHocDistributionRawTestRecord1()));
    }

    @Test
    public void shouldGetStatusOfAttemptedAction() {
        assertEquals(STATUS_OF_ATTEMPTED_ACTION_1,
                AdHocDistributionRecordUtil.getStatusOfAttemptedAction(getAdHocDistributionRawTestRecord1()));
    }

    @Test
    public void shouldGetFailureReasonCode() {
        assertEquals(convertIntegerToString(FAILURE_REASON_CODE_1),
                AdHocDistributionRecordUtil.getFailureReasonCode(getAdHocDistributionRawTestRecord1()));
    }

    @Test
    public void shouldGetFailureReasonCodeAsInteger() {
        assertEquals(FAILURE_REASON_CODE_1,
                AdHocDistributionRecordUtil.getFailureReasonCodeAsInteger(getAdHocDistributionRawTestRecord1()));
    }
}
