package com.novacroft.nemo.tfl.batch.action.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.financial_services_centre.ChequeSettledRecord;
import com.novacroft.nemo.tfl.common.application_service.financial_services_centre.ReconcileClearedChequeService;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.*;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19At0000;
import static org.mockito.Mockito.*;

public class ReconcileChequeClearanceActionImplTest {
    private ReconcileChequeClearanceActionImpl action;
    private ReconcileClearedChequeService mockReconcileClearedChequeService;
    private ChequeSettledRecord mockChequeSettledRecord;

    @Before
    public void setUp() {
        this.action = mock(ReconcileChequeClearanceActionImpl.class);
        this.mockReconcileClearedChequeService = mock(ReconcileClearedChequeService.class);
        this.action.reconcileClearedChequeService = this.mockReconcileClearedChequeService;

        this.mockChequeSettledRecord = mock(ChequeSettledRecord.class);
    }

    @Test
    public void shouldHandle() {
        doCallRealMethod().when(this.action).handle(any(ImportRecord.class));

        doNothing().when(this.mockReconcileClearedChequeService)
                .reconcileClearedCheque(anyLong(), anyLong(), anyString(), any(Date.class), anyInt());

        when(this.mockChequeSettledRecord.getChequeSerialNumber()).thenReturn(CHEQUE_SERIAL_NUMBER);
        when(this.mockChequeSettledRecord.getPaymentReferenceNumber()).thenReturn(REFERENCE_NUMBER);
        when(this.mockChequeSettledRecord.getCustomerName()).thenReturn(CUSTOMER_NAME);
        when(this.mockChequeSettledRecord.getClearedOn()).thenReturn(getAug19At0000());
        when(this.mockChequeSettledRecord.getAmount()).thenReturn(NET_AMOUNT);

        this.action.handle(this.mockChequeSettledRecord);

        verify(this.mockReconcileClearedChequeService)
                .reconcileClearedCheque(anyLong(), anyLong(), anyString(), any(Date.class), anyInt());

        verify(this.mockChequeSettledRecord).getChequeSerialNumber();
        verify(this.mockChequeSettledRecord).getPaymentReferenceNumber();
        verify(this.mockChequeSettledRecord).getCustomerName();
        verify(this.mockChequeSettledRecord).getClearedOn();
        verify(this.mockChequeSettledRecord).getAmount();
    }
}