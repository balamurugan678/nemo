package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.ApprovalTestUtil.REFUND_IDENTIFIER;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.REQUEST_SEQUENCE_NUMBER;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.command.impl.RefundSearchCmd;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.domain.Order;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public class RefundSearchDataServiceTest {
    private RefundSearchDataServiceImpl service;
    private RefundSearchDataServiceImpl mockService;
    private WorkFlowService mockWorkflowService;
    private CustomerDataService mockCustomerDataService;
    private OrderDataService mockOrderService;
    private List<OrderDTO> mockOrderList;
    private OrderDTO mockOrder;
    private List<WorkflowItemDTO> mockWorkflowItemList;
    private WorkflowItemDTO mockWorkflowItem;
    private Set<OrderDTO> potentialOrderSet;
    private RefundSearchCmd search;
    private Order exampleOrder;
    private List<CustomerDTO> mockCustomerList;
    private CustomerDTO mockCustomer;

    @Before
    public void setUp() {
        service = new RefundSearchDataServiceImpl();
        mockService = mock(RefundSearchDataServiceImpl.class);
        mockWorkflowService = mock(WorkFlowService.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        mockOrderService = mock(OrderDataService.class);

        service.orderDataService = mockOrderService;
        service.workflowService = mockWorkflowService;
        service.customerDataService = mockCustomerDataService;
        mockService.orderDataService = mockOrderService;
        mockService.workflowService = mockWorkflowService;
        mockService.customerDataService = mockCustomerDataService;

        mockWorkflowItem = mock(WorkflowItemDTO.class);
        mockOrder = mock(OrderDTO.class);
        mockWorkflowItemList = new ArrayList<WorkflowItemDTO>();
        mockWorkflowItemList.add(mockWorkflowItem);
        when(mockWorkflowItem.getCaseNumber()).thenReturn(REFUND_IDENTIFIER);
        mockOrderList = new ArrayList<OrderDTO>();
        mockOrderList.add(mockOrder);
        when(mockOrderService.findByExample(exampleOrder)).thenReturn(mockOrderList);

        potentialOrderSet = new HashSet<OrderDTO>();
        potentialOrderSet.add(mockOrder);
        search = new RefundSearchCmd(REFUND_IDENTIFIER, "Smith", "John", REFUND_IDENTIFIER, "Smith", "John", OYSTER_NUMBER_1,
                        REQUEST_SEQUENCE_NUMBER.toString(), REQUEST_SEQUENCE_NUMBER.toString());
        exampleOrder = new Order();
        mockCustomerList = new ArrayList<CustomerDTO>();
        mockCustomer = mock(CustomerDTO.class);
        mockCustomerList.add(mockCustomer);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldCallProcessMethodsWhenFindBySearchCriteria() {
        doCallRealMethod().when(mockService).findBySearchCriteria(any(RefundSearchCmd.class));
        mockService.findBySearchCriteria(search);
        verify(mockService).processAgentName(any(RefundSearchCmd.class), any(Set.class));
        verify(mockService).processBacsNumber(anyString(), any(Set.class));
        verify(mockService).processCardNumber(any(RefundSearchCmd.class), any(Set.class));
        verify(mockService).processCaseNumber(anyString(), any(Set.class));
        verify(mockService).processChequeNumber(anyString(), any(Set.class));
        verify(mockService).processCustomerName(any(RefundSearchCmd.class), any(Order.class), any(Set.class));
        verify(mockService).findOrderListByCriteria(any(Set.class), any(Order.class));
    }

    @Test
    public void shouldProcessAgentName() {
        when(mockWorkflowService.findHistoricRefundsByAgent(anyString(), anyString())).thenReturn(mockWorkflowItemList);
        service.processAgentName(search, potentialOrderSet);
        verify(mockWorkflowService).findHistoricRefundsByAgent(anyString(), anyString());
        verify(mockOrderService).findByExample(any(Order.class));
    }

    @Test
    public void shouldProcessBacsNumber() {
        when(mockOrderService.findRefundByBACSReferenceNumber(anyLong())).thenReturn(mockOrderList);
        service.processBacsNumber(REQUEST_SEQUENCE_NUMBER.toString(), potentialOrderSet);
        verify(mockOrderService).findRefundByBACSReferenceNumber(anyLong());
    }

    @Test
    public void shouldProcessCardNumber() {
        when(mockWorkflowService.findHistoricRefundsByCardNumber(anyString(), anyString())).thenReturn(mockWorkflowItemList);
        service.processCardNumber(search, potentialOrderSet);
        verify(mockWorkflowService).findHistoricRefundsByCardNumber(anyString(), anyString());
        verify(mockOrderService).findByExample(any(Order.class));
    }

    @Test
    public void shouldProcessCaseNumber() {
        Set<OrderDTO> orderSet = service.processCaseNumber(REFUND_IDENTIFIER, potentialOrderSet);
        assertEquals(Long.valueOf(REFUND_IDENTIFIER), orderSet.iterator().next().getApprovalId());
    }

    @Test
    public void shouldProcessChequeNumber() {
        when(mockOrderService.findRefundByChequeSerialNumber(anyLong())).thenReturn(mockOrderList);
        service.processChequeNumber(REQUEST_SEQUENCE_NUMBER.toString(), potentialOrderSet);
        verify(mockOrderService).findRefundByChequeSerialNumber(anyLong());
    }

    @Test
    public void shouldProcessCustomerNameWithFirstNameAndLastName() {
        when(mockCustomerDataService.findByFirstNameAndLastName(anyString(), anyString(), anyBoolean())).thenReturn(mockCustomerList);
        service.processCustomerName(search, exampleOrder, potentialOrderSet);
        verify(mockCustomerDataService).findByFirstNameAndLastName(anyString(), anyString(), anyBoolean());
        verify(mockOrderService).findRefundsForCustomer(anyLong());
    }

    @Test
    public void shouldProcessCustomerNameWithFirstName() {
        search.setCustomerLastName(StringUtils.EMPTY);
        when(mockCustomerDataService.findByFirstName(anyString(), anyBoolean())).thenReturn(mockCustomerList);
        service.processCustomerName(search, exampleOrder, potentialOrderSet);
        verify(mockCustomerDataService).findByFirstName(anyString(), anyBoolean());
        verify(mockOrderService).findRefundsForCustomer(anyLong());
    }

    @Test
    public void shouldProcessCustomerNameWithLastName() {
        search.setCustomerFirstName(StringUtils.EMPTY);
        when(mockCustomerDataService.findByLastName(anyString(), anyBoolean())).thenReturn(mockCustomerList);
        service.processCustomerName(search, exampleOrder, potentialOrderSet);
        verify(mockCustomerDataService).findByLastName(anyString(), anyBoolean());
        verify(mockOrderService).findRefundsForCustomer(anyLong());
    }

    @Test
    public void shouldFindOrderListByCriteria() {
        service.findOrderListByCriteria(potentialOrderSet, exampleOrder);
        verify(mockOrderService).findByExample(any(Order.class));
    }
}
