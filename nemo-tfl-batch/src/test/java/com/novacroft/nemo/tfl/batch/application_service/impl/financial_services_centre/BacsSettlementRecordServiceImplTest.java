package com.novacroft.nemo.tfl.batch.application_service.impl.financial_services_centre;

import org.junit.Before;
import org.junit.Test;

import static com.novacroft.nemo.test_support.BACSSettlementTestUtil.BACS_REQUESTS_HANDLED_RECORD;
import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.*;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19At0000;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

public class BacsSettlementRecordServiceImplTest {
    private BacsSettlementRecordServiceImpl service;

    @Before
    public void setUp() {
        this.service = mock(BacsSettlementRecordServiceImpl.class, CALLS_REAL_METHODS);
    }

    @Test
    public void shouldGetPaymentReferenceNumber() {
        assertEquals(REFERENCE_NUMBER_AS_STRING, this.service.getPaymentReferenceNumber(BACS_REQUESTS_HANDLED_RECORD));
    }

    @Test
    public void shouldGetAmount() {
        assertEquals(NET_AMOUNT_AS_STRING, this.service.getAmount(BACS_REQUESTS_HANDLED_RECORD));
    }

    @Test
    public void shouldGetCustomerName() {
        assertEquals(CUSTOMER_NAME, this.service.getCustomerName(BACS_REQUESTS_HANDLED_RECORD));
    }

    @Test
    public void shouldGetFinancialServicesReferenceNumber() {
        assertEquals(REFERENCE_NUMBER_AS_STRING,
                this.service.getFinancialServicesReferenceNumber(BACS_REQUESTS_HANDLED_RECORD));
    }

    @Test
    public void shouldGetPaymentDate() {
        assertEquals(AUG_19_FSC_FORMAT_STRING, this.service.getPaymentDate(BACS_REQUESTS_HANDLED_RECORD));
    }

    @Test
    public void shouldGetPaymentReferenceNumberAsLong() {
        assertEquals(REFERENCE_NUMBER, this.service.getPaymentReferenceNumberAsLong(BACS_REQUESTS_HANDLED_RECORD));
    }

    @Test
    public void shouldGetPaymentDateAsDate() {
        assertEquals(getAug19At0000(), this.service.getPaymentDateAsDate(BACS_REQUESTS_HANDLED_RECORD));
    }

    @Test
    public void shouldGetAmountAsFloat() {
        assertEquals(NET_AMOUNT, this.service.getAmountAsFloat(BACS_REQUESTS_HANDLED_RECORD));
    }

    @Test
    public void shouldGetFinancialServicesReferenceNumberAsLong() {
        assertEquals(REFERENCE_NUMBER, this.service.getFinancialServicesReferenceNumberAsLong(BACS_REQUESTS_HANDLED_RECORD));
    }
}