package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.test_support.ApprovalTestUtil.EXPECTED_RESULT;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.getJSONInput;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.tfl.common.application_service.impl.WorkFlowServiceImpl;
import com.novacroft.nemo.tfl.common.command.impl.ApprovalListCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.WorkflowDataService;
import com.novacroft.nemo.tfl.common.data_service.WorkflowSearchService;
import com.novacroft.nemo.tfl.common.data_service.impl.WorkflowSearchServiceImpl;
import com.novacroft.nemo.tfl.common.form_validator.ApprovalSearchValidator;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public class ApprovalListSearchControllerTest {

    private ApprovalListSearchController controller;
    private ApprovalListSearchController mockController;
    private TaskService mockTaskService;
    private WorkflowSearchServiceImpl mockWorkflowSearchService;
    private WorkFlowServiceImpl mockWorkflowService;
    private WorkflowDataService mockWorkflowDataService;
    private ApprovalSearchValidator mockValidator;
    private TaskQuery mockTaskQuery;
    private List<Task> mockTaskList;
    private ApprovalListCmdImpl approvalListCmd;
    private HttpServletRequest mockRequest;
    private BindingResult errors;
    private List<WorkflowItemDTO> results;

    @Before
    public void setUp() throws Exception {
        controller = new ApprovalListSearchController();
        mockController = mock(ApprovalListSearchController.class);
        mockTaskService = mock(TaskService.class);
        mockWorkflowSearchService = mock(WorkflowSearchServiceImpl.class);
        mockValidator = mock(ApprovalSearchValidator.class);
        mockTaskQuery = mock(TaskQuery.class);
        mockWorkflowService = mock(WorkFlowServiceImpl.class);
        mockWorkflowDataService = mock(WorkflowDataService.class);

        approvalListCmd = mock(ApprovalListCmdImpl.class);
        mockRequest = mock(HttpServletRequest.class);
        errors = new BeanPropertyBindingResult(approvalListCmd, "approvalListCmd");

        controller.approvalSearchValidator = mockValidator;

        mockController.approvalSearchValidator = mockValidator;
        mockController.workflowService = mockWorkflowService;

        doCallRealMethod().when(mockController).getResults(any(ApprovalListCmdImpl.class), any(BindingResult.class), any(HttpServletRequest.class));
        doCallRealMethod().when(mockController).checkSearch(any(ApprovalListCmdImpl.class), any(BindingResult.class), any(HttpServletRequest.class));
        doCallRealMethod().when(mockController).search(any(ApprovalListCmdImpl.class));
        doCallRealMethod().when(mockValidator).validate(anyObject(), any(Errors.class));
        doCallRealMethod().when(mockWorkflowService).findBySearchCriteria(any(ApprovalListCmdImpl.class));
        doCallRealMethod().when(mockWorkflowService).findAllByGroup(anyString());
        doCallRealMethod().when(mockWorkflowService).findAllByUser(anyString());
        doCallRealMethod().when(mockWorkflowService).setWorkflowSearchService(any(WorkflowSearchService.class));
        doCallRealMethod().when(mockWorkflowSearchService).findBySearchCriteria(any(ApprovalListCmdImpl.class));
        doCallRealMethod().when(mockWorkflowSearchService).findAllByGroup(anyString());
        doCallRealMethod().when(mockWorkflowSearchService).findAllByUser(anyString());
        doCallRealMethod().when(mockWorkflowSearchService).setTaskService(any(TaskService.class));
        doCallRealMethod().when(mockWorkflowSearchService).setWorkflowDataService(any(WorkflowDataService.class));

        mockWorkflowSearchService.setTaskService(mockTaskService);
        mockWorkflowSearchService.setWorkflowDataService(mockWorkflowDataService);
        mockWorkflowService.setWorkflowSearchService(mockWorkflowSearchService);

        when(approvalListCmd.getCaseNumber()).thenReturn("0");
        when(approvalListCmd.getAmount()).thenReturn("5");
        when(approvalListCmd.getFormLocation()).thenReturn("AgentList");

        when(mockTaskService.createTaskQuery()).thenReturn(mockTaskQuery);
        when(mockTaskQuery.taskId(anyString())).thenReturn(mockTaskQuery);
        when(mockTaskQuery.taskCandidateOrAssigned(anyString())).thenReturn(mockTaskQuery);
        when(mockTaskQuery.taskCandidateGroup(anyString())).thenReturn(mockTaskQuery);
        when(mockTaskQuery.processVariableValueEquals(anyString())).thenReturn(mockTaskQuery);
        when(mockTaskQuery.processVariableValueLike(anyString(), anyString())).thenReturn(mockTaskQuery);
        when(mockTaskQuery.list()).thenReturn(mockTaskList);

        results = new ArrayList<WorkflowItemDTO>();
        results.add(getJSONInput());

        when(mockWorkflowDataService.getWorkflowList(mockTaskList)).thenReturn(results);
    }

    @Test
    public void shouldGetResult() {
        String results2 = mockController.getResults(approvalListCmd, errors, mockRequest);

        assertEquals(EXPECTED_RESULT, results2.substring(0, 18));
        verify(mockTaskQuery).taskCandidateGroup("Agent");
    }

    @Test
    public void shouldGetUserSpecificResult() {
        doCallRealMethod().when(mockController).getAllByUser(anyString(), any(HttpServletRequest.class));
        String results2 = mockController.getAllByUser("Test", mockRequest);

        assertEquals(EXPECTED_RESULT, results2.substring(0, 18));
        verify(mockTaskQuery).taskCandidateOrAssigned("Test");
    }

    @Test
    public void shouldGetGroupSpecificResult() {
        doCallRealMethod().when(mockController)
                        .getAllByGroup(any(ApprovalListCmdImpl.class), any(BindingResult.class), any(HttpServletRequest.class));
        String results2 = mockController.getAllByGroup(approvalListCmd, errors, mockRequest);

        assertEquals(EXPECTED_RESULT, results2.substring(0, 18));
        verify(mockTaskQuery).taskCandidateGroup("Agent");
    }
}
