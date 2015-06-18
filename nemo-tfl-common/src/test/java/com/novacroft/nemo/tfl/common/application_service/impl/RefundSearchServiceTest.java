package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.ApprovalTestUtil.AGENT;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.PAYMENT_METHOD;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.REFUND_IDENTIFIER;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.STATUS;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.DateTestUtil.REFUND_DATE_1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.REQUEST_SEQUENCE_NUMBER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.test_support.ApprovalTestUtil;
import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.command.impl.RefundSearchCmd;
import com.novacroft.nemo.tfl.common.converter.impl.RefundSearchConverter;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.RefundSearchDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.RefundSearchResultDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public class RefundSearchServiceTest {
    private RefundSearchServiceImpl service;
    private RefundSearchServiceImpl mockService;
    private RefundSearchDataService mockRefundSearchDataService;
    private WorkFlowService mockWorkflowService;
    private RefundSearchConverter mockConverter;
    private OrderDataService mockOrderService;
    private List<OrderDTO> mockOrderList;
    private OrderDTO mockOrder;
    private List<WorkflowItemDTO> mockWorkflowItemList;
    private WorkflowItemDTO mockWorkflowItem;
    private RefundSearchResultDTO mockRefundSearchResult;
    private Set<OrderDTO> potentialOrderSet;
    private RefundSearchCmd search;
    private List<CustomerDTO> mockCustomerList;
    private CustomerDTO mockCustomer;

    @Before
    public void setUp() {
        service = new RefundSearchServiceImpl();
        mockService = mock(RefundSearchServiceImpl.class);
        mockRefundSearchDataService = mock(RefundSearchDataService.class);
        mockWorkflowService = mock(WorkFlowService.class);
        mockConverter = mock(RefundSearchConverter.class);
        mockOrderService = mock(OrderDataService.class);

        service.refundSearchConverter = mockConverter;
        service.orderDataService = mockOrderService;
        service.workflowService = mockWorkflowService;
        service.refundSearchDataService = mockRefundSearchDataService;
        mockService.refundSearchConverter = mockConverter;
        mockService.orderDataService = mockOrderService;
        mockService.workflowService = mockWorkflowService;
        mockService.refundSearchDataService = mockRefundSearchDataService;

        mockWorkflowItem = mock(WorkflowItemDTO.class);
        mockOrder = mock(OrderDTO.class);
        when(mockOrder.getApprovalId()).thenReturn(ApprovalTestUtil.APPROVAL_ID);
        mockWorkflowItemList = new ArrayList<WorkflowItemDTO>();
        mockWorkflowItemList.add(mockWorkflowItem);
        when(mockWorkflowItem.getCaseNumber()).thenReturn(REFUND_IDENTIFIER);
        mockOrderList = new ArrayList<OrderDTO>();
        mockOrderList.add(mockOrder);
        mockRefundSearchResult = new RefundSearchResultDTO(REFUND_IDENTIFIER, REFUND_DATE_1, AGENT, AGENT, OYSTER_NUMBER_1, PAYMENT_METHOD, STATUS);
        when(mockConverter.convertFromOrder(any(OrderDTO.class))).thenReturn(mockRefundSearchResult);
        when(mockConverter.convertFromWorkflowItem(any(WorkflowItemDTO.class))).thenReturn(mockRefundSearchResult);

        potentialOrderSet = new HashSet<OrderDTO>();
        potentialOrderSet.add(mockOrder);
        search = new RefundSearchCmd(REFUND_IDENTIFIER, "Smith", "John", REFUND_IDENTIFIER, "Smith", "John", OYSTER_NUMBER_1,
                        REQUEST_SEQUENCE_NUMBER.toString(), REQUEST_SEQUENCE_NUMBER.toString());
        mockCustomerList = new ArrayList<CustomerDTO>();
        mockCustomer = mock(CustomerDTO.class);
        mockCustomerList.add(mockCustomer);
    }

    @Test
    public void shouldGetAllRefunds() {
        when(mockWorkflowService.findAll()).thenReturn(mockWorkflowItemList);
        when(mockOrderService.findAllRefunds()).thenReturn(mockOrderList);
        List<RefundSearchResultDTO> resultList = service.getAllRefunds();
        assertEquals(RefundSearchResultDTO.class, resultList.get(0).getClass());
        verify(mockConverter, atLeastOnce()).convertFromOrder(any(OrderDTO.class));
        verify(mockConverter, atLeastOnce()).convertFromWorkflowItem(any(WorkflowItemDTO.class));
    }
    
    @Test
    public void shouldCallFindBySearchCriteria() {
        when(mockWorkflowService.findBySearchCriteria(any(RefundSearchCmd.class))).thenReturn(mockWorkflowItemList);
        when(mockRefundSearchDataService.findBySearchCriteria(any(RefundSearchCmd.class))).thenReturn(potentialOrderSet);
        service.findBySearchCriteria(search);
        verify(mockWorkflowService).findBySearchCriteria(any(RefundSearchCmd.class));
        verify(mockRefundSearchDataService).findBySearchCriteria(any(RefundSearchCmd.class));
    }

    @Test
    public void shouldNotNotAddCaseTwice() {
        List<RefundSearchResultDTO> resultList = new ArrayList<RefundSearchResultDTO>();
        assertFalse(service.alreadyInResultList(resultList, OrderTestUtil.getTestOrderDTO1()));
        mockRefundSearchResult.setCaseNumber(OrderTestUtil.APPROVAL_ID.toString());
        resultList.add(mockRefundSearchResult);
        assertTrue(service.alreadyInResultList(resultList, OrderTestUtil.getTestOrderDTO1()));
    }
}
