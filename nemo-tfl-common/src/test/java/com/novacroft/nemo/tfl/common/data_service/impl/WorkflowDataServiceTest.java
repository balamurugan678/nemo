package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.AGENT;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.CUSTOMER_ADDRESS;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.CUSTOMER_ALTERNATIVE_ADDRESS;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.PAYMENT_METHOD;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.REFUND_IDENTIFIER;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.WORKFLOW_ITEM;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.data_service.ContentDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.ContentDTO;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.test_support.ApprovalTestUtil;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

public class WorkflowDataServiceTest {

    private WorkflowDataServiceImpl service;
    private TaskService mockTaskService;
    private HistoryService mockHistoryService;
    private HistoricProcessInstanceQuery mockHistoricProcessQuery;
    private List<HistoricProcessInstance> mockHistoricProcessList;
    private HistoricProcessInstance mockHistoricProcessInstance;
    private TaskQuery mockTaskQuery;
    private List<Task> mockTaskList;
    private Iterator<Task> mockTaskIterator;
    private Task mockTask;
    private Map<String, Object> mockVariableList;
    private Set<Entry<String, Object>> mockEntrySet;
    private Iterator<Entry<String, Object>> mockEntryIterator;
    private Entry<String, Object> mockEntry;
    private WorkflowItemDTO mockWorkflowItemDTO;
    private RuntimeService mockRuntimeService;
    private RefundDetailDTO mockRefundDetails;
    private AddressDTO mockAddress;
    private ContentDataService mockContentDataService;
    private ContentDTO mockContentDTO;
    private ProcessInstanceQuery mockProcessInstanceQuery;
    private ProcessInstance mockProcessInstance;
    private List<ProcessInstance> mockProcessInstanceList;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        service = new WorkflowDataServiceImpl();
        mockTaskService = mock(TaskService.class);
        mockHistoryService = mock(HistoryService.class);
        mockHistoricProcessQuery = mock(HistoricProcessInstanceQuery.class);
        mockHistoricProcessList = mock(List.class);
        mockHistoricProcessInstance = mock(HistoricProcessInstance.class);
        mockTaskQuery = mock(TaskQuery.class);
        mockTask = mock(Task.class);
        mockTaskList = mock(List.class);
        mockTaskIterator = mock(Iterator.class);
        mockVariableList = mock(Map.class);
        mockEntrySet = mock(Set.class);
        mockEntryIterator = mock(Iterator.class);
        mockEntry = mock(Entry.class);
        mockWorkflowItemDTO = mock(WorkflowItemDTO.class);
        mockRuntimeService = mock(RuntimeService.class);
        mockAddress = mock(AddressDTO.class);
        mockRefundDetails = mock(RefundDetailDTO.class);
        mockContentDataService = mock(ContentDataService.class);
        mockContentDTO = mock(ContentDTO.class);
        mockProcessInstanceQuery = mock(ProcessInstanceQuery.class);
        mockProcessInstance = mock(ProcessInstance.class);

        service.taskService = mockTaskService;
        service.historyService = mockHistoryService;
        service.runtimeService = mockRuntimeService;
        service.contentDataService = mockContentDataService;
        
        when(mockContentDataService.findByLocaleAndCode(anyString(), anyString())).thenReturn(mockContentDTO);
        when(mockContentDTO.getContent()).thenReturn(ApprovalTestUtil.PAYMENT_METHOD);

        when(mockTaskService.createTaskQuery()).thenReturn(mockTaskQuery);
        when(mockRuntimeService.getVariables(anyString())).thenReturn(mockVariableList);
        when(mockHistoricProcessInstance.getProcessVariables()).thenReturn(mockVariableList);

        when(mockHistoryService.createHistoricProcessInstanceQuery()).thenReturn(mockHistoricProcessQuery);
        when(mockHistoricProcessQuery.variableValueEquals(anyString(), anyString())).thenReturn(mockHistoricProcessQuery);
        when(mockHistoricProcessQuery.includeProcessVariables()).thenReturn(mockHistoricProcessQuery);
        when(mockHistoricProcessQuery.orderByProcessInstanceEndTime()).thenReturn(mockHistoricProcessQuery);
        when(mockHistoricProcessQuery.desc()).thenReturn(mockHistoricProcessQuery);
        when(mockHistoricProcessQuery.list()).thenReturn(mockHistoricProcessList);
        when(mockHistoricProcessList.get(anyInt())).thenReturn(mockHistoricProcessInstance);
        when(mockTaskQuery.processVariableValueEquals(anyString(), anyObject())).thenReturn(mockTaskQuery);
        when(mockTaskQuery.singleResult()).thenReturn(mockTask);
        when(mockTaskQuery.list()).thenReturn(mockTaskList);
        when(mockTaskList.iterator()).thenReturn(mockTaskIterator);
        when(mockTaskIterator.next()).thenReturn(mockTask);
        when(mockVariableList.entrySet()).thenReturn(mockEntrySet);
        when(mockVariableList.get(WORKFLOW_ITEM)).thenReturn(mockWorkflowItemDTO);
        when(mockVariableList.get(PAYMENT_METHOD)).thenReturn(PaymentType.WEB_ACCOUNT_CREDIT.code());
        when(mockVariableList.get(CUSTOMER_ADDRESS)).thenReturn(mockAddress);
        when(mockVariableList.get(CUSTOMER_ALTERNATIVE_ADDRESS)).thenReturn(mockAddress);
        when(mockEntrySet.iterator()).thenReturn(mockEntryIterator);
        when(mockEntryIterator.next()).thenReturn(mockEntry);
        when(mockWorkflowItemDTO.getTaskId()).thenReturn("0");
        when(mockWorkflowItemDTO.getExecutionId()).thenReturn("0");
        when(mockWorkflowItemDTO.getRefundDetails()).thenReturn(mockRefundDetails);

        when(mockRuntimeService.createProcessInstanceQuery()).thenReturn(mockProcessInstanceQuery);
        when(mockProcessInstanceQuery.variableValueEquals(anyString(), anyString())).thenReturn(mockProcessInstanceQuery);
    }

    @Test
    public void shouldGetWorkflowItem() {
        WorkflowItemDTO result = service.getWorkflowItem("0");
        verify(mockRuntimeService).getVariables(anyString());
        assertEquals(mockWorkflowItemDTO, result);
    }

    @Test
    public void shouldGetHistoricWorkflowItem() {
        WorkflowItemDTO result = service.getHistoricWorkflowItem("0");
        assertEquals(mockWorkflowItemDTO, result);
    }

    @Test
    public void shouldGetWorkflowList() {
        List<Task> tasks = new ArrayList<Task>();
        tasks.add(mockTask);
        List<WorkflowItemDTO> result = service.getWorkflowList(tasks);
        verify(mockRuntimeService).getVariables(anyString());
        assertEquals(ArrayList.class, result.getClass());
        assertEquals(mockWorkflowItemDTO, result.get(0));
    }

    @Test
    public void shouldGetHistoricWorkflowList() {
        List<HistoricProcessInstance> processList = new ArrayList<HistoricProcessInstance>();
        processList.add(mockHistoricProcessInstance);
        List<WorkflowItemDTO> result = service.getHistoricWorkflowList(processList);
        verify(mockHistoricProcessInstance, atLeastOnce()).getProcessVariables();
        assertEquals(ArrayList.class, result.getClass());
        assertEquals(mockWorkflowItemDTO, result.get(0));
    }
    
    @Test
    public void shouldGetPaymentMethodContet() {
    	assertEquals(ApprovalTestUtil.PAYMENT_METHOD ,service.getPaymentMethod(PaymentType.WEB_ACCOUNT_CREDIT.code()));
    	verify(mockContentDataService).findByLocaleAndCode(anyString(), anyString());
    }

    @Test
    public void shouldUpdateAgent() {
        UserEntity agent = new UserEntity();
        agent.setFirstName(AGENT);
        agent.setLastName(AGENT);
        service.updateAgentAndAddClaimTime(REFUND_IDENTIFIER, agent, new DateTime());
        verify(mockRuntimeService, atLeastOnce()).setVariable(anyString(), anyString(), anyObject());
    }

    @Test
    public void shouldGetAgentName() {
        UserEntity agent = new UserEntity();
        agent.setFirstName(AGENT);
        agent.setLastName(AGENT);
        assertEquals(AGENT + StringUtil.SPACE + AGENT, service.getAgentName(agent));
    }

    @Test
    public void shoulReturnTrueIfApprovalIdBeingUsed() {
        mockProcessInstanceList = new ArrayList<ProcessInstance>();
        mockProcessInstanceList.add(mockProcessInstance);
        when(mockProcessInstanceQuery.list()).thenReturn(mockProcessInstanceList);
        assertTrue(service.isApprovalIdBeingUsed(ApprovalTestUtil.APPROVAL_ID));
    }

    @Test
    public void shoulReturnFalseIfApprovalIdIsNotBeingUsed() {
        mockProcessInstanceList = new ArrayList<ProcessInstance>();
        when(mockProcessInstanceQuery.list()).thenReturn(mockProcessInstanceList);
        assertFalse(service.isApprovalIdBeingUsed(ApprovalTestUtil.APPROVAL_ID));
    }

}
