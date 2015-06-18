package com.novacroft.nemo.tfl.common.application_service.impl.financial_services_centre;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.ChequeSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.*;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19At0000;
import static com.novacroft.nemo.test_support.OrderTestUtil.ORDER_NUMBER;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ReconcileChequesProducedServiceImplTest {
    private ReconcileChequesProducedServiceImpl service;
    private ChequeSettlementDataService mockChequeSettlementDataService;

    private ChequeSettlementDTO mockChequeSettlementDTO;
    private List<ChequeSettlementDTO> mockChequeSettlementDTOList;

    @Before
    public void setUp() {
        this.service = mock(ReconcileChequesProducedServiceImpl.class);
        this.mockChequeSettlementDataService = mock(ChequeSettlementDataService.class);
        this.service.chequeSettlementDataService = this.mockChequeSettlementDataService;

        this.mockChequeSettlementDTO = mock(ChequeSettlementDTO.class);
        this.mockChequeSettlementDTOList = new ArrayList<ChequeSettlementDTO>();
        this.mockChequeSettlementDTOList.add(this.mockChequeSettlementDTO);
    }

    @Test
    public void isStatusRequestedShouldReturnTrue() {
        when(this.service.isStatusRequested(anyString())).thenCallRealMethod();
        assertTrue(this.service.isStatusRequested(SettlementStatus.REQUESTED.code()));
    }

    @Test
    public void isStatusRequestedShouldReturnFalse() {
        when(this.service.isStatusRequested(anyString())).thenCallRealMethod();
        assertFalse(this.service.isStatusRequested(EMPTY_STRING));
    }

    @Test
    public void doAmountAndPayeeMatchShouldReturnTrue() {
        when(this.service.doAmountAndPayeeMatch(anyInt(), anyString(), anyInt(), anyString())).thenCallRealMethod();
        assertTrue(this.service.doAmountAndPayeeMatch(NET_AMOUNT_AS_PENCE, CUSTOMER_NAME, NET_AMOUNT_AS_PENCE, CUSTOMER_NAME));
    }

    @Test
    public void doAmountAndPayeeMatchShouldReturnFalseWithAmountMismatch() {
        when(this.service.doAmountAndPayeeMatch(anyInt(), anyString(), anyInt(), anyString())).thenCallRealMethod();
        assertFalse(this.service.doAmountAndPayeeMatch(NET_AMOUNT_AS_PENCE, CUSTOMER_NAME, 0, CUSTOMER_NAME));
    }

    @Test
    public void doAmountAndPayeeMatchShouldReturnFalseWithNameMismatch() {
        when(this.service.doAmountAndPayeeMatch(anyInt(), anyString(), anyInt(), anyString())).thenCallRealMethod();
        assertFalse(this.service.doAmountAndPayeeMatch(NET_AMOUNT_AS_PENCE, CUSTOMER_NAME, NET_AMOUNT_AS_PENCE, "!$%^&*"));
    }

    @Test
    public void doAmountAndPayeeNotMatchShouldReturnTrue() {
        when(this.service.doAmountAndPayeeNotMatch(anyInt(), anyString(), anyInt(), anyString())).thenCallRealMethod();
        when(this.service.doAmountAndPayeeMatch(anyInt(), anyString(), anyInt(), anyString())).thenReturn(false);
        assertTrue(
                this.service.doAmountAndPayeeNotMatch(NET_AMOUNT_AS_PENCE, CUSTOMER_NAME, NET_AMOUNT_AS_PENCE, CUSTOMER_NAME));
    }

    @Test
    public void doAmountAndPayeeNotMatchShouldReturnFalse() {
        when(this.service.doAmountAndPayeeNotMatch(anyInt(), anyString(), anyInt(), anyString())).thenCallRealMethod();
        when(this.service.doAmountAndPayeeMatch(anyInt(), anyString(), anyInt(), anyString())).thenReturn(true);
        assertFalse(
                this.service.doAmountAndPayeeNotMatch(NET_AMOUNT_AS_PENCE, CUSTOMER_NAME, NET_AMOUNT_AS_PENCE, CUSTOMER_NAME));
    }

    @Test
    public void shouldUpdateChequeSettlementWithChequeDetails() {
        doCallRealMethod().when(this.service)
                .updateChequeSettlementWithChequeDetails(any(ChequeSettlementDTO.class), anyLong(), any(Date.class));
        doNothing().when(this.mockChequeSettlementDTO).setChequeSerialNumber(anyLong());
        doNothing().when(this.mockChequeSettlementDTO).setPrintedOn(any(Date.class));
        when(this.mockChequeSettlementDataService.createOrUpdate(any(ChequeSettlementDTO.class))).thenReturn(null);
        this.service
                .updateChequeSettlementWithChequeDetails(this.mockChequeSettlementDTO, CHEQUE_SERIAL_NUMBER, getAug19At0000());
        verify(this.mockChequeSettlementDTO).setChequeSerialNumber(anyLong());
        verify(this.mockChequeSettlementDTO).setPrintedOn(any(Date.class));
        verify(this.mockChequeSettlementDataService).createOrUpdate(any(ChequeSettlementDTO.class));
    }

    @Test
    public void shouldValidateChequeDetails() {
        doCallRealMethod().when(this.service).validateChequeDetails(any(ChequeSettlementDTO.class), anyInt(), anyString());
        when(this.service.doAmountAndPayeeNotMatch(anyInt(), anyString(), anyInt(), anyString())).thenReturn(false);
        this.service.validateChequeDetails(this.mockChequeSettlementDTO, NET_AMOUNT_AS_PENCE, CUSTOMER_NAME);
    }

    @Test(expected = ApplicationServiceException.class)
    public void validateChequeDetailsShouldError() {
        doCallRealMethod().when(this.service).validateChequeDetails(any(ChequeSettlementDTO.class), anyInt(), anyString());
        when(this.service.doAmountAndPayeeNotMatch(anyInt(), anyString(), anyInt(), anyString())).thenReturn(true);
        this.service.validateChequeDetails(this.mockChequeSettlementDTO, NET_AMOUNT_AS_PENCE, CUSTOMER_NAME);
    }

    @Test
    public void shouldValidateOnlyOneChequeSettlementWithRequestedStatus() {
        doCallRealMethod().when(this.service).validateOnlyOneChequeSettlementWithRequestedStatus(any(ChequeSettlementDTO.class));
        when(this.service.isStatusRequested(anyString())).thenReturn(true);
        this.service.validateOnlyOneChequeSettlementWithRequestedStatus(this.mockChequeSettlementDTO);
        verify(this.service).isStatusRequested(anyString());
    }

    @Test(expected = ApplicationServiceException.class)
    public void validateOnlyOneChequeSettlementWithRequestedStatusShouldError() {
        doCallRealMethod().when(this.service).validateOnlyOneChequeSettlementWithRequestedStatus(any(ChequeSettlementDTO.class));
        when(this.service.isStatusRequested(anyString())).thenReturn(false);
        this.service.validateOnlyOneChequeSettlementWithRequestedStatus(this.mockChequeSettlementDTO);
    }

    @Test
    public void shouldReconcileChequeProduced() {
        doCallRealMethod().when(this.service)
                .reconcileChequeProduced(anyLong(), anyInt(), anyString(), anyLong(), any(Date.class));
        when(this.mockChequeSettlementDataService.findBySettlementNumber((anyLong()))).thenReturn(mockChequeSettlementDTO);
        doNothing().when(this.service).validateOnlyOneChequeSettlementWithRequestedStatus(any(ChequeSettlementDTO.class));
        doNothing().when(this.service).validateChequeDetails(any(ChequeSettlementDTO.class), anyInt(), anyString());
        doNothing().when(this.service)
                .updateChequeSettlementWithChequeDetails(any(ChequeSettlementDTO.class), anyLong(), any(Date.class));

        this.service.reconcileChequeProduced(ORDER_NUMBER, NET_AMOUNT_AS_PENCE, CUSTOMER_NAME, CHEQUE_SERIAL_NUMBER,
                getAug19At0000());
        verify(this.service).validateOnlyOneChequeSettlementWithRequestedStatus(any(ChequeSettlementDTO.class));
        verify(this.service).validateChequeDetails(any(ChequeSettlementDTO.class), anyInt(), anyString());
        verify(this.service)
                .updateChequeSettlementWithChequeDetails(any(ChequeSettlementDTO.class), anyLong(), any(Date.class));
    }
}