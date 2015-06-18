package com.novacroft.nemo.tfl.batch.application_service.impl.financial_services_centre;

import org.junit.Before;
import org.junit.Test;

import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.*;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19At0000;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

public class ChequeSettlementRecordServiceImplTest {
    private ChequeSettlementRecordServiceImpl service;

    @Before
    public void setUp() {
        this.service = mock(ChequeSettlementRecordServiceImpl.class, CALLS_REAL_METHODS);
    }

    @Test
    public void shouldGetChequeSerialNumber() {
        assertEquals(CHEQUE_SERIAL_NUMBER_AS_STRING, this.service.getChequeSerialNumber(TEST_CHEQUE_SETTLEMENT_IMPORT_RECORD));
    }

    @Test
    public void shouldGetChequeSerialNumberAsLong() {
        assertEquals(CHEQUE_SERIAL_NUMBER, this.service.getChequeSerialNumberAsLong(TEST_CHEQUE_SETTLEMENT_IMPORT_RECORD));
    }

    @Test
    public void shouldGetPaymentReferenceNumber() {
        assertEquals(REFERENCE_NUMBER_AS_STRING, this.service.getPaymentReferenceNumber(TEST_CHEQUE_SETTLEMENT_IMPORT_RECORD));
    }

    @Test
    public void shouldGetPaymentReferenceNumberAsLong() {
        assertEquals(REFERENCE_NUMBER, this.service.getPaymentReferenceNumberAsLong(TEST_CHEQUE_SETTLEMENT_IMPORT_RECORD));
    }

    @Test
    public void shouldGetCustomerName() {
        assertEquals(CUSTOMER_NAME, this.service.getCustomerName(TEST_CHEQUE_SETTLEMENT_IMPORT_RECORD));
    }

    @Test
    public void shouldGetClearedOn() {
        assertEquals(AUG_19_FSC_FORMAT_STRING, this.service.getClearedOn(TEST_CHEQUE_SETTLEMENT_IMPORT_RECORD));
    }

    @Test
    public void shouldGetClearedOnAsDate() {
        assertEquals(getAug19At0000(), this.service.getClearedOnAsDate(TEST_CHEQUE_SETTLEMENT_IMPORT_RECORD));
    }

    @Test
    public void shouldGetCurrency() {
        assertEquals(CURRENCY, this.service.getCurrency(TEST_CHEQUE_SETTLEMENT_IMPORT_RECORD));
    }

    @Test
    public void shouldGetAmount() {
        assertEquals(NET_AMOUNT_AS_STRING, this.service.getAmount(TEST_CHEQUE_SETTLEMENT_IMPORT_RECORD));
    }

    @Test
    public void shouldGetAmountAsFloat() {
        assertTrue(NET_AMOUNT * -1 == this.service.getAmountAsFloat(TEST_CHEQUE_SETTLEMENT_IMPORT_RECORD));
    }
}