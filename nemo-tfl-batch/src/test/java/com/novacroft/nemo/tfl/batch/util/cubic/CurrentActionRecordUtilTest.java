package com.novacroft.nemo.tfl.batch.util.cubic;

import com.novacroft.nemo.test_support.DateTestUtil;
import org.junit.Test;

import static com.novacroft.nemo.test_support.CurrentActionRecordTestUtil.*;
import static com.novacroft.nemo.tfl.batch.util.ConvertUtil.convertIntegerToString;
import static com.novacroft.nemo.tfl.batch.util.cubic.CubicConvertUtil.convertDateToString;
import static org.apache.commons.lang3.time.DateUtils.isSameDay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

/**
 * CurrentActionRecordUtil unit tests
 */
public class CurrentActionRecordUtilTest {

    @Test
    public void shouldGetPrestigeId() {
        assertEquals(PRESTIGE_ID_1, CurrentActionRecordUtil.getPrestigeId(getTestCurrentActionArray1()));
    }

    @Test
    public void shouldGetPrestigeIdWhenNull() {
        assertNull(CurrentActionRecordUtil.getPrestigeId(getTestCurrentActionArray1WithNullPrestigeID()));
    }

    @Test
    public void shouldGetRequestSequenceNumber() {
        assertEquals(convertIntegerToString(REQUEST_SEQUENCE_NUMBER_1),
                CurrentActionRecordUtil.getRequestSequenceNumber(getTestCurrentActionArray1()));
    }

    @Test
    public void shouldGetRequestSequenceNumberAsInteger() {
        assertEquals(REQUEST_SEQUENCE_NUMBER_1,
                CurrentActionRecordUtil.getRequestSequenceNumberAsInteger(getTestCurrentActionArray1()));
    }

    @Test
    public void shouldGetProductCode() {
        assertEquals(convertIntegerToString(PRODUCT_CODE_1),
                CurrentActionRecordUtil.getProductCode(getTestCurrentActionArray1()));
    }

    @Test
    public void shouldGetProductCodeAsInteger() {
        assertEquals(PRODUCT_CODE_1, CurrentActionRecordUtil.getProductCodeAsInteger(getTestCurrentActionArray1()));
    }

    @Test
    public void shouldGetFarePaid() {
        assertEquals(convertIntegerToString(FARE_PAID_1), CurrentActionRecordUtil.getFarePaid(getTestCurrentActionArray1()));
    }

    @Test
    public void shouldGetFarePaidAsInteger() {
        assertEquals(FARE_PAID_1, CurrentActionRecordUtil.getFarePaidAsInteger(getTestCurrentActionArray1()));
    }

    @Test
    public void shouldGetCurrency() {
        assertEquals(convertIntegerToString(CURRENCY_1), CurrentActionRecordUtil.getCurrency(getTestCurrentActionArray1()));
    }

    @Test
    public void shouldGetCurrencyAsInteger() {
        assertEquals(CURRENCY_1, CurrentActionRecordUtil.getCurrencyAsInteger(getTestCurrentActionArray1()));
    }

    @Test
    public void shouldGetPaymentMethodCode() {
        assertEquals(convertIntegerToString(PAYMENT_METHOD_CODE_1),
                CurrentActionRecordUtil.getPaymentMethodCode(getTestCurrentActionArray1()));
    }

    @Test
    public void shouldGetPaymentMethodCodeAsInteger() {
        assertEquals(PAYMENT_METHOD_CODE_1,
                CurrentActionRecordUtil.getPaymentMethodCodeAsInteger(getTestCurrentActionArray1()));
    }

    @Test
    public void shouldGetPrePayValue() {
        assertEquals(convertIntegerToString(PRE_PAY_VALUE_1),
                CurrentActionRecordUtil.getPrePayValue(getTestCurrentActionArray1()));
    }

    @Test
    public void shouldGetPrePayValueAsInteger() {
        assertEquals(PRE_PAY_VALUE_1, CurrentActionRecordUtil.getPrePayValueAsInteger(getTestCurrentActionArray1()));
    }

    @Test
    public void shouldGetPickUpLocation() {
        assertEquals(convertIntegerToString(PICK_UP_LOCATION_1),
                CurrentActionRecordUtil.getPickUpLocation(getTestCurrentActionArray1()));
    }

    @Test
    public void shouldGetPickUpLocationAsInteger() {
        assertEquals(PICK_UP_LOCATION_1, CurrentActionRecordUtil.getPickUpLocationAsInteger(getTestCurrentActionArray1()));
    }

    @Test
    public void shouldGetPptStartDate() {
        assertEquals(convertDateToString(PPT_START_DATE_1),
                CurrentActionRecordUtil.getPptStartDate(getTestCurrentActionArray1()));
    }

    @Test
    public void shouldGetPptStartDateAsDate() {
        assertTrue(isSameDay(DateTestUtil.getAug19(),
                CurrentActionRecordUtil.getPptStartDateAsDate(getTestCurrentActionArray1())));
    }

    @Test
    public void shouldGetPptExpiryDate() {
        assertEquals(convertDateToString(PPT_EXPIRY_DATE_1),
                CurrentActionRecordUtil.getPptExpiryDate(getTestCurrentActionArray1()));
    }

    @Test
    public void shouldGetPptExpiryDateAsDate() {
        assertTrue(isSameDay(DateTestUtil.getAug21(),
                CurrentActionRecordUtil.getPptExpiryDateAsDate(getTestCurrentActionArray1())));
    }

    @Test
    public void shouldGetAutoLoadState() {
        assertEquals(convertIntegerToString(AUTO_LOAD_STATE_1),
                CurrentActionRecordUtil.getAutoLoadState(getTestCurrentActionArray1()));
    }

    @Test
    public void shouldGetAutoLoadStateAsInteger() {
        assertEquals(AUTO_LOAD_STATE_1, CurrentActionRecordUtil.getAutoLoadStateAsInteger(getTestCurrentActionArray1()));
    }
}
