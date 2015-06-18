package com.novacroft.nemo.tfl.common.application_service.impl.financial_services_centre;

import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.data_service.ChequeSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.*;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19At0000;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ReconcileOutdatedChequeServiceImplTest {
    private ReconcileOutdatedChequeServiceImpl service;
    private ChequeSettlementDataService mockChequeSettlementDataService;

    private ChequeSettlementDTO mockChequeSettlementDTO;

    @Before
    public void setUp() {
        this.service = mock(ReconcileOutdatedChequeServiceImpl.class);
        this.mockChequeSettlementDataService = mock(ChequeSettlementDataService.class);
        this.service.chequeSettlementDataService = this.mockChequeSettlementDataService;

        this.mockChequeSettlementDTO = mock(ChequeSettlementDTO.class);
    }

    @Test
    public void shouldReconcileOutdatedCheque() {
        doCallRealMethod().when(this.service)
                .reconcileOutdatedCheque(anyLong(), anyInt(), anyString(), anyLong(), any(Date.class));
        when(this.mockChequeSettlementDataService.findByChequeSerialNumber(anyLong())).thenReturn(this.mockChequeSettlementDTO);
        doNothing().when(this.service).validateIssuedStatus(any(ChequeSettlementDTO.class));
        doNothing().when(this.service).validatePayeeAndAmount(any(ChequeSettlementDTO.class), anyString(), anyInt(), any(PrivateError.class));
        doNothing().when(this.service).updateSettlementWithOutdatedDetails(any(ChequeSettlementDTO.class), any(Date.class));

        this.service.reconcileOutdatedCheque(REFERENCE_NUMBER, NET_AMOUNT_AS_PENCE, CUSTOMER_NAME, CHEQUE_SERIAL_NUMBER,
                getAug19At0000());

        verify(this.mockChequeSettlementDataService).findByChequeSerialNumber(anyLong());
        verify(this.service).validateIssuedStatus(any(ChequeSettlementDTO.class));
        verify(this.service).validatePayeeAndAmount(any(ChequeSettlementDTO.class), anyString(), anyInt(),any(PrivateError.class));
        verify(this.service).updateSettlementWithOutdatedDetails(any(ChequeSettlementDTO.class), any(Date.class));
    }

    @Test
    public void shouldUpdateSettlementWithOutdatedDetails() {
        doCallRealMethod().when(this.service)
                .updateSettlementWithOutdatedDetails(any(ChequeSettlementDTO.class), any(Date.class));
        doNothing().when(this.mockChequeSettlementDTO).setOutdatedOn(any(Date.class));
        doNothing().when(this.mockChequeSettlementDTO).setStatus(anyString());
        when(this.mockChequeSettlementDataService.createOrUpdate(any(ChequeSettlementDTO.class)))
                .thenReturn(this.mockChequeSettlementDTO);

        this.service.updateSettlementWithOutdatedDetails(this.mockChequeSettlementDTO, getAug19At0000());

        verify(this.mockChequeSettlementDTO).setOutdatedOn(any(Date.class));
        verify(this.mockChequeSettlementDTO).setStatus(anyString());
        verify(this.mockChequeSettlementDataService).createOrUpdate(any(ChequeSettlementDTO.class));
    }
}