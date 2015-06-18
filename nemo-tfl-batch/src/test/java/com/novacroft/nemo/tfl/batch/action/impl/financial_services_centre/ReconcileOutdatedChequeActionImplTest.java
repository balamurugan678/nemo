package com.novacroft.nemo.tfl.batch.action.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.financial_services_centre.OutdatedChequeRecord;
import com.novacroft.nemo.tfl.common.application_service.financial_services_centre.ReconcileOutdatedChequeService;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.*;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19At0000;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ReconcileOutdatedChequeActionImplTest {
    private ReconcileOutdatedChequeActionImpl action;
    private ReconcileOutdatedChequeService mockReconcileOutdatedChequeService;
    private OutdatedChequeRecord mockOutdatedChequeRecord;

    @Before
    public void setUp() {
        this.action = mock(ReconcileOutdatedChequeActionImpl.class);
        this.mockReconcileOutdatedChequeService = mock(ReconcileOutdatedChequeService.class);
        this.action.reconcileOutdatedChequeService = this.mockReconcileOutdatedChequeService;

        this.mockOutdatedChequeRecord = mock(OutdatedChequeRecord.class);
    }

    @Test
    public void shouldHandle() {
        doCallRealMethod().when(this.action).handle(any(ImportRecord.class));
        doNothing().when(this.mockReconcileOutdatedChequeService)
                .reconcileOutdatedCheque(anyLong(), anyInt(), anyString(), anyLong(), any(Date.class));
        when(this.mockOutdatedChequeRecord.getReferenceNumber()).thenReturn(REFERENCE_NUMBER);
        when(this.mockOutdatedChequeRecord.getAmount()).thenReturn(NET_AMOUNT);
        when(this.mockOutdatedChequeRecord.getCustomerName()).thenReturn(CUSTOMER_NAME);
        when(this.mockOutdatedChequeRecord.getChequeSerialNumber()).thenReturn(CHEQUE_SERIAL_NUMBER);
        when(this.mockOutdatedChequeRecord.getOutdatedOn()).thenReturn(getAug19At0000());

        this.action.handle(this.mockOutdatedChequeRecord);

        verify(this.mockReconcileOutdatedChequeService)
                .reconcileOutdatedCheque(anyLong(), anyInt(), anyString(), anyLong(), any(Date.class));
        verify(this.mockOutdatedChequeRecord).getReferenceNumber();
        verify(this.mockOutdatedChequeRecord).getAmount();
        verify(this.mockOutdatedChequeRecord).getCustomerName();
        verify(this.mockOutdatedChequeRecord).getChequeSerialNumber();
        verify(this.mockOutdatedChequeRecord).getOutdatedOn();
    }
}