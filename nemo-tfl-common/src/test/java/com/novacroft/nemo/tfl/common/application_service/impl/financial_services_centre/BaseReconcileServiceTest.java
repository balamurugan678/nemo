package com.novacroft.nemo.tfl.common.application_service.impl.financial_services_centre;

import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.CUSTOMER_NAME;
import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.NET_AMOUNT_AS_PENCE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.PayeeSettlementDTO;

public class BaseReconcileServiceTest {
    private BaseReconciliationService service;

    private PayeeSettlementDTO mockPayeeSettlementDTO;

    @Before
    public void setUp() {
        this.service = mock(BaseReconciliationService.class);
        this.mockPayeeSettlementDTO = mock(PayeeSettlementDTO.class);
    }

    @Test
    public void doAmountAndPayeeNotMatchShouldReturnTrue() {
        when(this.service.doAmountAndPayeeNotMatch(any(ChequeSettlementDTO.class), anyString(), anyInt())).thenCallRealMethod();
        when(this.service.doAmountAndPayeeMatch(any(ChequeSettlementDTO.class), anyString(), anyInt())).thenReturn(false);
        assertTrue(this.service.doAmountAndPayeeNotMatch(this.mockPayeeSettlementDTO, CUSTOMER_NAME, NET_AMOUNT_AS_PENCE));
    }

    @Test
    public void doAmountAndPayeeNotMatchShouldReturnFalse() {
        when(this.service.doAmountAndPayeeNotMatch(any(ChequeSettlementDTO.class), anyString(), anyInt())).thenCallRealMethod();
        when(this.service.doAmountAndPayeeMatch(any(ChequeSettlementDTO.class), anyString(), anyInt())).thenReturn(true);
        assertFalse(this.service.doAmountAndPayeeNotMatch(this.mockPayeeSettlementDTO, CUSTOMER_NAME, NET_AMOUNT_AS_PENCE));
    }

    @Test
    public void doAmountAndPayeeMatchShouldReturnTrue() {
        when(this.service.doAmountAndPayeeMatch(any(ChequeSettlementDTO.class), anyString(), anyInt())).thenCallRealMethod();
        when(this.mockPayeeSettlementDTO.getPayeeName()).thenReturn(CUSTOMER_NAME);
        when(this.mockPayeeSettlementDTO.getAmount()).thenReturn(NET_AMOUNT_AS_PENCE * -1);
        assertTrue(this.service.doAmountAndPayeeMatch(this.mockPayeeSettlementDTO, CUSTOMER_NAME, NET_AMOUNT_AS_PENCE));
    }

    @Test
    public void doAmountAndPayeeMatchShouldReturnFalseWithNameMismatch() {
        when(this.service.doAmountAndPayeeMatch(any(ChequeSettlementDTO.class), anyString(), anyInt())).thenCallRealMethod();
        when(this.mockPayeeSettlementDTO.getPayeeName()).thenReturn(EMPTY_STRING);
        when(this.mockPayeeSettlementDTO.getAmount()).thenReturn(NET_AMOUNT_AS_PENCE * -1);
        assertFalse(this.service.doAmountAndPayeeMatch(this.mockPayeeSettlementDTO, CUSTOMER_NAME, NET_AMOUNT_AS_PENCE));
    }

    @Test
    public void doAmountAndPayeeMatchShouldReturnFalseWithAmountMismatch() {
        when(this.service.doAmountAndPayeeMatch(any(ChequeSettlementDTO.class), anyString(), anyInt())).thenCallRealMethod();
        when(this.mockPayeeSettlementDTO.getPayeeName()).thenReturn(CUSTOMER_NAME);
        when(this.mockPayeeSettlementDTO.getAmount()).thenReturn(0);
        assertFalse(this.service.doAmountAndPayeeMatch(this.mockPayeeSettlementDTO, CUSTOMER_NAME, NET_AMOUNT_AS_PENCE));
    }
   
    @Test
    public void shouldValidatePayeeAndAmount() {
        doCallRealMethod().when(this.service).validatePayeeAndAmount(any(ChequeSettlementDTO.class), anyString(), anyInt(), any(PrivateError.class));
        when(this.service.doAmountAndPayeeNotMatch(any(ChequeSettlementDTO.class), anyString(), anyInt())).thenReturn(false);
        this.service.validatePayeeAndAmount(this.mockPayeeSettlementDTO, CUSTOMER_NAME, NET_AMOUNT_AS_PENCE, PrivateError.CARD_UPDATE_REQUEST_FAILED);
        verify(this.service).doAmountAndPayeeNotMatch(any(ChequeSettlementDTO.class), anyString(), anyInt());
    }

    @Test(expected = ApplicationServiceException.class)
    public void validatePayeeAndAmountShouldFail() {
        doCallRealMethod().when(this.service).validatePayeeAndAmount(any(ChequeSettlementDTO.class), anyString(), anyInt(), any(PrivateError.class));
        when(this.service.doAmountAndPayeeNotMatch(any(ChequeSettlementDTO.class), anyString(), anyInt())).thenReturn(true);
        this.service.validatePayeeAndAmount(this.mockPayeeSettlementDTO, CUSTOMER_NAME, NET_AMOUNT_AS_PENCE, PrivateError.CARD_UPDATE_REQUEST_FAILED);
        verify(this.service).doAmountAndPayeeNotMatch(any(ChequeSettlementDTO.class), anyString(), anyInt());
    }
}
