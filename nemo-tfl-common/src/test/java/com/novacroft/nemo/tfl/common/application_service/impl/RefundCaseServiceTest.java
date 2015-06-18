package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.ApprovalTestUtil.REFUND_IDENTIFIER;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.converter.impl.RefundCaseConverter;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public class RefundCaseServiceTest {
    private RefundCaseServiceImpl service;
    private WorkFlowService mockWorkflowService;
    private RefundCaseConverter mockRefundCaseConverter;
    private OrderDataService mockOrderService;
    private WorkflowItemDTO mockWorkflowItem;
    private List<OrderDTO> mockOrderList;
    private OrderDTO mockOrder;
    
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        service = new RefundCaseServiceImpl();
        mockWorkflowService = mock(WorkFlowService.class);
        mockRefundCaseConverter = mock(RefundCaseConverter.class);
        mockOrderService = mock(OrderDataService.class);

        service.workflowService = mockWorkflowService;
        service.refundCaseConverter = mockRefundCaseConverter;
        service.orderDataService = mockOrderService;

        mockWorkflowItem = mock(WorkflowItemDTO.class);
        mockOrderList = mock(List.class);
        mockOrder = mock(OrderDTO.class);

        when(mockWorkflowService.getWorkflowItem(anyString())).thenReturn(mockWorkflowItem);
    }
    
    @Test
    public void shouldGetRefundCase() {
        when(mockWorkflowItem.getCaseNumber()).thenReturn(REFUND_IDENTIFIER);
        service.getRefundCase(REFUND_IDENTIFIER);
        verify(mockRefundCaseConverter).convertFromWorkflowItem(mockWorkflowItem);
    }
    
    @Test
    public void shouldGetRefundCaseFromOrder() {
        when(mockWorkflowItem.getCaseNumber()).thenReturn(null);
        when(mockOrderService.getOrdersByCaseNumber(anyString())).thenReturn(mockOrderList);
        when(mockOrderList.get(anyInt())).thenReturn(mockOrder);
        service.getRefundCase(REFUND_IDENTIFIER);
        verify(mockRefundCaseConverter).convertFromOrder(mockOrder);
    }
}
