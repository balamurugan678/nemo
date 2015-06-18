package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.test_support.ApprovalTestUtil.getWorkflowItem;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.getWorkflowItemBACS;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.getWorkflowItemCheque;
import static com.novacroft.nemo.test_support.BACSSettlementTestUtil.getTestBACSSettlementDTO2;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.getTestChequeSettlementDTO1;
import static com.novacroft.nemo.test_support.OrderTestUtil.getTestOrderDTO1;
import static com.novacroft.nemo.test_support.RefundCaseTestUtil.getRefundCaseAdHoc;
import static com.novacroft.nemo.test_support.RefundCaseTestUtil.getRefundCaseBACS;
import static com.novacroft.nemo.test_support.RefundCaseTestUtil.getRefundCaseCheque;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.command.impl.RefundCaseCmd;
import com.novacroft.nemo.tfl.common.data_service.BACSSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.ChequeSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;

public class RefundCaseConverterTest {
    private RefundCaseConverter converter;
    private RefundCaseConverter mockConverter;
    private WorkFlowService mockWorkflowService;
    private CardDataService mockCardDataService;
    private BACSSettlementDataService mockBACSSettlementDataService;
    private ChequeSettlementDataService mockChequeSettlementDataService;
    private List<BACSSettlementDTO> mockBACSList;

    @Before
    public void setUp() {
        converter = new RefundCaseConverter();
        mockConverter = mock(RefundCaseConverter.class);

        mockWorkflowService = mock(WorkFlowService.class);
        mockCardDataService = mock(CardDataService.class);
        mockBACSSettlementDataService = mock(BACSSettlementDataService.class);
        mockChequeSettlementDataService = mock(ChequeSettlementDataService.class);

        converter.bacsSettlementDataService = mockBACSSettlementDataService;
        converter.cardDataService = mockCardDataService;
        converter.chequeSettlementDataService = mockChequeSettlementDataService;
        converter.workflowService = mockWorkflowService;

        mockConverter.bacsSettlementDataService = mockBACSSettlementDataService;
        mockConverter.cardDataService = mockCardDataService;
        mockConverter.chequeSettlementDataService = mockChequeSettlementDataService;
        mockConverter.workflowService = mockWorkflowService;

        mockBACSList = new ArrayList<BACSSettlementDTO>();
        mockBACSList.add(getTestBACSSettlementDTO2());
    }

    @Test
    public void shouldConvertFromWorkflowItem() {
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        assertEquals(RefundCaseCmd.class, converter.convertFromWorkflowItem(getWorkflowItem()).getClass());
        verify(mockCardDataService).findById(anyLong());
    }

    @Test
    public void shouldConvertFromOrderBACS() {
        when(mockWorkflowService.getHistoricWorkflowItem(anyString())).thenReturn(getWorkflowItemBACS());
        when(mockConverter.convertFromWorkflowItem(any(WorkflowItemDTO.class))).thenReturn(getRefundCaseBACS());
        when(mockBACSSettlementDataService.findByOrderNumber(anyLong())).thenReturn(mockBACSList);
        doCallRealMethod().when(mockConverter).convertFromOrder(any(OrderDTO.class));
        assertEquals(RefundCaseCmd.class, mockConverter.convertFromOrder(getTestOrderDTO1()).getClass());
        verify(mockWorkflowService).getHistoricWorkflowItem(anyString());
        verify(mockConverter).convertFromWorkflowItem(any(WorkflowItemDTO.class));
        verify(mockBACSSettlementDataService).findByOrderNumber(anyLong());
    }

    @Test
    public void shouldConvertFromOrderCheque() {
        when(mockWorkflowService.getHistoricWorkflowItem(anyString())).thenReturn(getWorkflowItemCheque());
        when(mockConverter.convertFromWorkflowItem(any(WorkflowItemDTO.class))).thenReturn(getRefundCaseCheque());
        when(mockChequeSettlementDataService.findByOrderNumber(anyLong())).thenReturn(getTestChequeSettlementDTO1());
        doCallRealMethod().when(mockConverter).convertFromOrder(any(OrderDTO.class));
        assertEquals(RefundCaseCmd.class, mockConverter.convertFromOrder(getTestOrderDTO1()).getClass());
        verify(mockWorkflowService).getHistoricWorkflowItem(anyString());
        verify(mockConverter).convertFromWorkflowItem(any(WorkflowItemDTO.class));
        verify(mockChequeSettlementDataService).findByOrderNumber(anyLong());
    }

    @Test
    public void shouldConverterFromOrderOtherPaymentMethods() {
        when(mockWorkflowService.getHistoricWorkflowItem(anyString())).thenReturn(getWorkflowItem());
        when(mockConverter.convertFromWorkflowItem(any(WorkflowItemDTO.class))).thenReturn(getRefundCaseAdHoc());
        doCallRealMethod().when(mockConverter).convertFromOrder(any(OrderDTO.class));
        assertEquals(RefundCaseCmd.class, mockConverter.convertFromOrder(getTestOrderDTO1()).getClass());
        verify(mockWorkflowService).getHistoricWorkflowItem(anyString());
        verify(mockConverter).convertFromWorkflowItem(any(WorkflowItemDTO.class));
    }

}
