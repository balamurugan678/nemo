package com.novacroft.nemo.tfl.common.application_service.impl.financial_services_centre;

import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.data_service.ChequeSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.*;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19At0000;
import static org.mockito.Mockito.*;

public class ReconcileClearedChequeServiceImplTest {
    private ReconcileClearedChequeServiceImpl service;
    private ChequeSettlementDataService mockChequeSettlementDataService;

    private ChequeSettlementDTO mockChequeSettlementDTO;

    @Before
    public void setUp() {
        this.service = mock(ReconcileClearedChequeServiceImpl.class);
        this.mockChequeSettlementDataService = mock(ChequeSettlementDataService.class);
        this.service.chequeSettlementDataService = this.mockChequeSettlementDataService;

        this.mockChequeSettlementDTO = mock(ChequeSettlementDTO.class);
    }

    @Test
    public void shouldReconcileClearedCheque() {
        doCallRealMethod().when(this.service)
                .reconcileClearedCheque(anyLong(), anyLong(), anyString(), any(Date.class), anyInt());
        when(this.mockChequeSettlementDataService.findByChequeSerialNumber(anyLong())).thenReturn(this.mockChequeSettlementDTO);
        doNothing().when(this.service).validateIssuedStatus(any(ChequeSettlementDTO.class));
        doNothing().when(this.service).validatePayeeAndAmount(any(ChequeSettlementDTO.class), anyString(), anyInt(), any(PrivateError.class));
        doNothing().when(this.service)
                .updateSettlementWithClearanceDetails(any(ChequeSettlementDTO.class), anyLong(), any(Date.class));
        this.service.reconcileClearedCheque(CHEQUE_SERIAL_NUMBER, REFERENCE_NUMBER, CUSTOMER_NAME, getAug19At0000(),
                NET_AMOUNT_AS_PENCE);
        verify(this.mockChequeSettlementDataService).findByChequeSerialNumber(anyLong());
        verify(this.service).validateIssuedStatus(any(ChequeSettlementDTO.class));
        verify(this.service).validatePayeeAndAmount(any(ChequeSettlementDTO.class), anyString(), anyInt(), any(PrivateError.class));
        verify(this.service).updateSettlementWithClearanceDetails(any(ChequeSettlementDTO.class), anyLong(), any(Date.class));
    }

    @Test
    public void shouldUpdateSettlementWithClearanceDetails() {
        doCallRealMethod().when(this.service)
                .updateSettlementWithClearanceDetails(any(ChequeSettlementDTO.class), anyLong(), any(Date.class));
        doNothing().when(this.mockChequeSettlementDTO).setPaymentReference(anyLong());
        doNothing().when(this.mockChequeSettlementDTO).setClearedOn(any(Date.class));
        doNothing().when(this.mockChequeSettlementDTO).setStatus(anyString());
        when(this.mockChequeSettlementDataService.createOrUpdate(any(ChequeSettlementDTO.class)))
                .thenReturn(this.mockChequeSettlementDTO);
        this.service.updateSettlementWithClearanceDetails(this.mockChequeSettlementDTO, REFERENCE_NUMBER, getAug19At0000());
        verify(this.mockChequeSettlementDTO).setPaymentReference(anyLong());
        verify(this.mockChequeSettlementDTO).setClearedOn(any(Date.class));
        verify(this.mockChequeSettlementDTO).setStatus(anyString());
        verify(this.mockChequeSettlementDataService).createOrUpdate(any(ChequeSettlementDTO.class));
    }
}