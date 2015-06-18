package com.novacroft.nemo.tfl.batch.action.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.financial_services_centre.ChequeProducedRecord;
import com.novacroft.nemo.tfl.common.application_service.financial_services_centre.ReconcileChequesProducedService;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.*;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19At0000;
import static org.mockito.Mockito.*;

public class ReconcileChequeProducedActionImplTest {
    private ReconcileChequeProducedActionImpl action;
    private ReconcileChequesProducedService mockReconcileChequesProducedService;
    private ChequeProducedRecord mockChequeProducedRecord;

    @Before
    public void setUp() {
        this.action = mock(ReconcileChequeProducedActionImpl.class);
        this.mockReconcileChequesProducedService = mock(ReconcileChequesProducedService.class);
        this.action.reconcileChequesProducedService = this.mockReconcileChequesProducedService;

        this.mockChequeProducedRecord = mock(ChequeProducedRecord.class);
    }

    @Test
    public void shouldHandle() {
        doCallRealMethod().when(this.action).handle(any(ImportRecord.class));
        doNothing().when(this.mockReconcileChequesProducedService)
                .reconcileChequeProduced(anyLong(), anyInt(), anyString(), anyLong(), any(Date.class));
        when(this.mockChequeProducedRecord.getReferenceNumber()).thenReturn(REFERENCE_NUMBER);
        when(this.mockChequeProducedRecord.getAmount()).thenReturn(NET_AMOUNT);
        when(this.mockChequeProducedRecord.getCustomerName()).thenReturn(CUSTOMER_NAME);
        when(this.mockChequeProducedRecord.getChequeSerialNumber()).thenReturn(CHEQUE_SERIAL_NUMBER);
        when(this.mockChequeProducedRecord.getPrintedOn()).thenReturn(getAug19At0000());

        this.action.handle(this.mockChequeProducedRecord);

        verify(this.mockReconcileChequesProducedService)
                .reconcileChequeProduced(anyLong(), anyInt(), anyString(), anyLong(), any(Date.class));
        verify(this.mockChequeProducedRecord).getReferenceNumber();
        verify(this.mockChequeProducedRecord).getAmount();
        verify(this.mockChequeProducedRecord).getCustomerName();
        verify(this.mockChequeProducedRecord).getChequeSerialNumber();
        verify(this.mockChequeProducedRecord).getPrintedOn();
    }
}