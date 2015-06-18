package com.novacroft.nemo.tfl.batch.application_service.impl.financial_services_centre;

import org.junit.Before;
import org.junit.Test;

import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.*;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19At0000;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

public class OutdatedChequeRecordServiceImplTest {
    private OutdatedChequeRecordServiceImpl service;

    @Before
    public void setUp() {
        this.service = mock(OutdatedChequeRecordServiceImpl.class, CALLS_REAL_METHODS);
    }

    @Test
    public void shouldGetReferenceNumber() {
        assertEquals(REFERENCE_NUMBER_AS_STRING, this.service.getReferenceNumber(TEST_OUTDATED_CHEQUE_IMPORT_RECORD));
    }

    @Test
    public void shouldGetReferenceNumberAsLong() {
        assertEquals(REFERENCE_NUMBER, this.service.getReferenceNumberAsLong(TEST_OUTDATED_CHEQUE_IMPORT_RECORD));
    }

    @Test
    public void shouldGetAmount() {
        assertEquals(NET_AMOUNT_AS_STRING, this.service.getAmount(TEST_OUTDATED_CHEQUE_IMPORT_RECORD));
    }

    @Test
    public void shouldGetAmountAsFloat() {
        assertEquals(NET_AMOUNT, this.service.getAmountAsFloat(TEST_OUTDATED_CHEQUE_IMPORT_RECORD));
    }

    @Test
    public void shouldGetCustomerName() {
        assertEquals(CUSTOMER_NAME, this.service.getCustomerName(TEST_OUTDATED_CHEQUE_IMPORT_RECORD));
    }

    @Test
    public void shouldGetChequeSerialNumber() {
        assertEquals(CHEQUE_SERIAL_NUMBER_AS_STRING, this.service.getChequeSerialNumber(TEST_OUTDATED_CHEQUE_IMPORT_RECORD));
    }

    @Test
    public void shouldGetChequeSerialNumberAsLong() {
        assertEquals(CHEQUE_SERIAL_NUMBER, this.service.getChequeSerialNumberAsLong(TEST_OUTDATED_CHEQUE_IMPORT_RECORD));
    }

    @Test
    public void shouldGetPrintedOn() {
        assertEquals(AUG_19_FSC_FORMAT_STRING, this.service.getOutdatedOn(TEST_OUTDATED_CHEQUE_IMPORT_RECORD));
    }

    @Test
    public void shouldGetPrintedOnAsDate() {
        assertEquals(getAug19At0000(), this.service.getOutdatedOnAsDate(TEST_OUTDATED_CHEQUE_IMPORT_RECORD));
    }
}