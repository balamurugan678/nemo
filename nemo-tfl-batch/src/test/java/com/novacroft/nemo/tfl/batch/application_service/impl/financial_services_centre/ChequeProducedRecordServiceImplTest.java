package com.novacroft.nemo.tfl.batch.application_service.impl.financial_services_centre;

import org.junit.Before;
import org.junit.Test;

import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.*;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19At0000;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

public class ChequeProducedRecordServiceImplTest {
    private ChequeProducedRecordServiceImpl service;

    @Before
    public void setUp() {
        this.service = mock(ChequeProducedRecordServiceImpl.class, CALLS_REAL_METHODS);
    }

    @Test
    public void shouldGetReferenceNumber() {
        assertEquals(REFERENCE_NUMBER_AS_STRING, this.service.getReferenceNumber(TEST_CHEQUE_PRODUCED_IMPORT_RECORD));
    }

    @Test
    public void shouldGetReferenceNumberAsLong() {
        assertEquals(REFERENCE_NUMBER, this.service.getReferenceNumberAsLong(TEST_CHEQUE_PRODUCED_IMPORT_RECORD));
    }

    @Test
    public void shouldGetAmount() {
        assertEquals(NET_AMOUNT_AS_STRING, this.service.getAmount(TEST_CHEQUE_PRODUCED_IMPORT_RECORD));
    }

    @Test
    public void shouldGetAmountAsFloat() {
        assertEquals(NET_AMOUNT, this.service.getAmountAsFloat(TEST_CHEQUE_PRODUCED_IMPORT_RECORD));
    }

    @Test
    public void shouldGetCustomerName() {
        assertEquals(CUSTOMER_NAME, this.service.getCustomerName(TEST_CHEQUE_PRODUCED_IMPORT_RECORD));
    }

    @Test
    public void shouldGetChequeSerialNumber() {
        assertEquals(CHEQUE_SERIAL_NUMBER_AS_STRING, this.service.getChequeSerialNumber(TEST_CHEQUE_PRODUCED_IMPORT_RECORD));
    }

    @Test
    public void shouldGetChequeSerialNumberAsLong() {
        assertEquals(CHEQUE_SERIAL_NUMBER, this.service.getChequeSerialNumberAsLong(TEST_CHEQUE_PRODUCED_IMPORT_RECORD));
    }

    @Test
    public void shouldGetPrintedOn() {
        assertEquals(AUG_19_FSC_FORMAT_STRING, this.service.getPrintedOn(TEST_CHEQUE_PRODUCED_IMPORT_RECORD));
    }

    @Test
    public void shouldGetPrintedOnAsDate() {
        assertEquals(getAug19At0000(), this.service.getPrintedOnAsDate(TEST_CHEQUE_PRODUCED_IMPORT_RECORD));
    }
}