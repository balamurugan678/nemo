package com.novacroft.nemo.tfl.batch.application_service.impl.financial_services_centre;

import com.novacroft.nemo.tfl.common.constant.financial_services_centre.BACSRejectCodeEnum;
import org.junit.Before;
import org.junit.Test;

import static com.novacroft.nemo.test_support.BACSSettlementTestUtil.BACS_REJECT_RECORD;
import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.*;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19At0000;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

public class BacsFailureRecordServiceImplTest {
    private BacsFailureRecordServiceImpl service;

    @Before
    public void setUp() {
        this.service = mock(BacsFailureRecordServiceImpl.class, CALLS_REAL_METHODS);
    }

    @Test
    public void shouldGetAmount() {
        assertEquals(NET_AMOUNT_AS_STRING, this.service.getAmount(BACS_REJECT_RECORD));
    }

    @Test
    public void shouldGetFinancialServicesReferenceNumber() {
        assertEquals(REFERENCE_NUMBER_AS_STRING, this.service.getFinancialServicesReferenceNumber(BACS_REJECT_RECORD));
    }

    @Test
    public void shouldGetPaymentFailureDateAsDate() {
        assertEquals(getAug19At0000(), this.service.getPaymentFailureDateAsDate(BACS_REJECT_RECORD));
    }

    @Test
    public void shouldGetAmountAsFloat() {
        assertEquals(NET_AMOUNT, this.service.getAmountAsFloat(BACS_REJECT_RECORD));
    }

    @Test
    public void shouldGetPaymentFailureDate() {
        assertEquals(AUG_19_FSC_FORMAT_STRING, this.service.getPaymentFailureDate(BACS_REJECT_RECORD));
    }

    @Test
    public void shouldGetFinancialServicesReferenceNumberAsLong() {
        assertEquals(REFERENCE_NUMBER, this.service.getFinancialServicesReferenceNumberAsLong(BACS_REJECT_RECORD));
    }

    @Test
    public void shouldGetBacsRejectCode() {
        assertEquals(BACSRejectCodeEnum.C.name(), this.service.getBacsRejectCode(BACS_REJECT_RECORD));
    }
}