package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.ApprovalTestUtil.FIRSTNAME;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.LASTNAME;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.REFUND_IDENTIFIER;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.getSearch;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.test_support.ApprovalTestUtil;
import com.novacroft.nemo.tfl.common.command.impl.ApprovalListCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.RefundSearchCmd;
import com.novacroft.nemo.tfl.common.constant.AgentGroup;
import com.novacroft.nemo.tfl.common.data_service.WorkflowDataService;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public class WorkflowSearchServiceTest {
    private WorkflowSearchServiceImpl service;
    private WorkflowSearchServiceImpl mockService;
    private TaskService mockTaskService;
    private TaskQuery mockTaskQuery;
    private List<Task> mockTaskList;
    private Task mockTask;
    private List<WorkflowItemDTO> mockWorkflowItemList;
    private WorkflowDataService mockWorkflowDataService;
    private HistoryService mockHistoryService;
    private HistoricProcessInstanceQuery mockHistoricProcessQuery;
    private List<HistoricProcessInstance> mockHistoricProcessList;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        service = new WorkflowSearchServiceImpl();
        mockService = mock(WorkflowSearchServiceImpl.class);
        mockTaskService = mock(TaskService.class);
        mockTaskQuery = mock(TaskQuery.class);
        mockTask = mock(Task.class);
        mockTaskList = mock(List.class);
        mockWorkflowItemList = new ArrayList<WorkflowItemDTO>();
        mockWorkflowDataService = mock(WorkflowDataService.class);
        mockHistoryService = mock(HistoryService.class);
        mockHistoricProcessQuery = mock(HistoricProcessInstanceQuery.class);
        mockHistoricProcessList = mock(List.class);

        service.taskService = mockTaskService;
        service.workflowDataService = mockWorkflowDataService;
        service.taskHistoryService = mockHistoryService;

        mockService.taskService = mockTaskService;
        mockService.workflowDataService = mockWorkflowDataService;
        mockService.taskHistoryService = mockHistoryService;

        when(mockTaskService.createTaskQuery()).thenReturn(mockTaskQuery);
        when(mockTaskQuery.processVariableValueEquals(anyString(), anyObject())).thenReturn(mockTaskQuery);
        when(mockTaskQuery.processVariableValueLike(anyString(), anyString())).thenReturn(mockTaskQuery);
        when(mockTaskQuery.taskCandidateGroup(anyString())).thenReturn(mockTaskQuery);
        when(mockTaskQuery.taskCandidateOrAssigned(anyString())).thenReturn(mockTaskQuery);
        when(mockTaskQuery.singleResult()).thenReturn(mockTask);
        when(mockTaskQuery.list()).thenReturn(mockTaskList);
        when(mockWorkflowDataService.getWorkflowList(mockTaskList)).thenReturn(mockWorkflowItemList);
        when(mockHistoryService.createHistoricProcessInstanceQuery()).thenReturn(mockHistoricProcessQuery);
        when(mockHistoricProcessQuery.includeProcessVariables()).thenReturn(mockHistoricProcessQuery);
        when(mockHistoricProcessQuery.list()).thenReturn(mockHistoricProcessList);
    }

    @Test
    public void shouldFindByCriteraExact() {
        ApprovalListCmdImpl cmd = getSearch();
        cmd.setExact("Yes");
        service.findBySearchCriteria(cmd);
        verify(mockTaskService).createTaskQuery();
        verify(mockTaskQuery, atLeastOnce()).processVariableValueEquals(anyString(), anyString());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldFindByCriteriaRefundSearch() {
        RefundSearchCmd cmd = new RefundSearchCmd(REFUND_IDENTIFIER, LASTNAME, FIRSTNAME, REFUND_IDENTIFIER, LASTNAME, FIRSTNAME, OYSTER_NUMBER_1,
                        REFUND_IDENTIFIER, REFUND_IDENTIFIER);
        service.findBySearchCriteria(cmd);
        verify(mockTaskService).createTaskQuery();
        verify(mockTaskQuery, atLeastOnce()).processVariableValueLike(anyString(), anyString());
        verify(mockWorkflowDataService).getWorkflowList(any(List.class));
    }

    @Test
    public void shouldFindByCriteraNotExact() {
        ApprovalListCmdImpl cmd = getSearch();
        service.findBySearchCriteria(cmd);
        verify(mockTaskService).createTaskQuery();
        verify(mockTaskQuery, atLeastOnce()).processVariableValueLike(anyString(), anyString());
    }

    @Test
    public void shouldFindByGroupUsingAgentGroup() {
        AgentGroup agentGroup = AgentGroup.AGENT;
        List<WorkflowItemDTO> result = service.findAllByGroup(agentGroup);
        verify(mockTaskQuery).taskCandidateGroup(anyString());
        assertEquals(ArrayList.class, result.getClass());
    }

    @Test
    public void shouldFindByGroupUsingString() {
        String group = "Agent";
        List<WorkflowItemDTO> result = service.findAllByGroup(group);
        verify(mockTaskQuery).taskCandidateGroup(anyString());
        assertEquals(ArrayList.class, result.getClass());
    }

    @Test
    public void shouldFindByUser() {
        String user = "Agent1";
        List<WorkflowItemDTO> result = service.findAllByUser(user);
        verify(mockTaskQuery).taskCandidateOrAssigned(anyString());
        assertEquals(ArrayList.class, result.getClass());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldFindHistoricRefundByAgent() {
        service.findHistoricRefundsByAgent(FIRSTNAME, LASTNAME);
        verify(mockHistoryService).createHistoricProcessInstanceQuery();
        verify(mockWorkflowDataService).getHistoricWorkflowList(any(List.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldFindHistoricRefundByCardNumber() {
        service.findHistoricRefundsByCardNumber(OYSTER_NUMBER_1, null);
        verify(mockHistoryService).createHistoricProcessInstanceQuery();
        verify(mockWorkflowDataService).getHistoricWorkflowList(any(List.class));
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void shouldFindPaymentTypeWithSpaces() {
    	ApprovalListCmdImpl search = new ApprovalListCmdImpl();
    	search.setPaymentMethod(ApprovalTestUtil.PAYMENT_METHOD);
    	service.findBySearchCriteria(search);
    	verify(mockTaskService).createTaskQuery();
        verify(mockTaskQuery, atLeastOnce()).processVariableValueLike(anyString(), anyString());
        verify(mockWorkflowDataService).getWorkflowList(any(List.class));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void shouldFindHistoricRefundByAgentWithFirstNameTest() {
        service.findHistoricRefundsByAgent(FIRSTNAME, null);
        verify(mockHistoryService).createHistoricProcessInstanceQuery();
        verify(mockWorkflowDataService).getHistoricWorkflowList(any(List.class));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void shouldFindHistoricRefundByAgentWithLastNameTest() {
        service.findHistoricRefundsByAgent(null, LASTNAME);
        verify(mockHistoryService).createHistoricProcessInstanceQuery();
        verify(mockWorkflowDataService).getHistoricWorkflowList(any(List.class));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void shouldFindHistoricRefundByAgentWithNoSearchCriteriaTest() {
        service.findHistoricRefundsByAgent(null, null);
        verify(mockHistoryService).createHistoricProcessInstanceQuery();
        verify(mockWorkflowDataService).getHistoricWorkflowList(any(List.class));
    }
    
    @Test
    public void processNameVariablesWithNoSearchCriteriaTest() {
        service.processNameVariables(null, mockTaskQuery, null, null);
        verify(mockTaskQuery, never()).processVariableValueLike(anyString(), anyString());
    }
    
    @Test
    public void processNameVariablesTest() {
        service.processNameVariables(null, mockTaskQuery, FIRSTNAME, LASTNAME);
        verify(mockTaskQuery).processVariableValueLike(anyString(), anyString());
    }
    
    @Test
    public void processNameVariablesWithFirstNameTest() {
        service.processNameVariables(null, mockTaskQuery, FIRSTNAME, null);
        verify(mockTaskQuery).processVariableValueLike(anyString(), anyString());
    }
    
    @Test
    public void processNameVariablesWithLastNameTest() {
        service.processNameVariables(null, mockTaskQuery, null, LASTNAME);
        verify(mockTaskQuery).processVariableValueLike(anyString(), anyString());
    }
}
