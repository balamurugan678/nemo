package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.test_support.ApprovalTestUtil.getJSONInput;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.test_support.ApprovalTestUtil;
import com.novacroft.nemo.tfl.common.application_service.impl.WorkFlowServiceImpl;
import com.novacroft.nemo.tfl.common.command.impl.ApprovalListCmdImpl;
import com.novacroft.nemo.tfl.common.constant.AgentGroup;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.WorkflowDataService;
import com.novacroft.nemo.tfl.common.data_service.WorkflowSearchService;
import com.novacroft.nemo.tfl.common.data_service.impl.WorkflowSearchServiceImpl;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public class ApprovalListControllerTest {

    private ApprovalListController controller;
    private TaskService mockTaskService;
    private WorkFlowServiceImpl mockWorkflowService;
    private WorkflowSearchServiceImpl mockWorkflowSearchService;
    private WorkflowDataService mockWorkflowDataService;
    private List<Task> mockTaskList;
    private TaskQuery mockTaskQuery;
    private BindingAwareModelMap model;
    private SelectListService mockSelectListService;
    private List<WorkflowItemDTO> results;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {

        controller = new ApprovalListController();
        mockTaskService = mock(TaskService.class);
        mockWorkflowService = mock(WorkFlowServiceImpl.class);
        mockWorkflowSearchService = mock(WorkflowSearchServiceImpl.class);
        mockWorkflowDataService = mock(WorkflowDataService.class);
        mockTaskQuery = mock(TaskQuery.class);
        mockTaskList = mock(List.class);
        model = new BindingAwareModelMap();
        mockSelectListService = mock(SelectListService.class);

        controller.workflowService = mockWorkflowService;
        controller.selectListService = mockSelectListService;

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

        when(mockTaskService.createTaskQuery()).thenReturn(mockTaskQuery);
        when(mockTaskQuery.taskId(anyString())).thenReturn(mockTaskQuery);
        when(mockTaskQuery.taskCandidateOrAssigned(anyString())).thenReturn(mockTaskQuery);
        when(mockTaskQuery.taskCandidateGroup(anyString())).thenReturn(mockTaskQuery);
        when(mockTaskQuery.list()).thenReturn(mockTaskList);

        results = new ArrayList<WorkflowItemDTO>();
        results.add(getJSONInput());

        when(mockWorkflowDataService.getWorkflowList(any(List.class))).thenReturn(results);
    }

    @Test
    public void shouldReturnAgentListView() {
        ModelAndView agentApproval = controller.showAgentApprovalList(null);
        assertViewName(agentApproval, PageView.AGENTLIST);

    }

    @Test
    public void shouldReturnAgentUserListView() {
        ModelAndView agentApproval = controller.showAgentApprovalList("Agent");
        assertViewName(agentApproval, PageView.AGENTLIST);
    }

    @Test
    public void shouldReturnFirstStageApprovalListView() {
        ModelAndView agentApproval = controller.showFirstStageApprovalList(null);
        assertViewName(agentApproval, PageView.APPROVALLIST);
        Map<String, Object> model = agentApproval.getModel();
        assertNotNull(model.get(PageCommand.APPROVALLIST));
    }

    @Test
    public void shouldReturnFirstStageApprovalUserListView() {
        ModelAndView agentApproval = controller.showFirstStageApprovalList("FirstStageApprover");
        assertViewName(agentApproval, PageView.APPROVALLIST);
        Map<String, Object> model = agentApproval.getModel();
        assertNotNull(model.get(PageCommand.APPROVALLIST));
    }

    @Test
    public void shouldReturnSecondStageApprovalListView() {
        ModelAndView agentApproval = controller.showSecondStageApprovalList(null);
        assertViewName(agentApproval, PageView.APPROVALLISTSTAGE2);
        Map<String, Object> model = agentApproval.getModel();
        assertNotNull(model.get(PageCommand.APPROVALLIST));
    }

    @Test
    public void shouldReturnSecondStageApprovalUserListView() {
        ModelAndView agentApproval = controller.showSecondStageApprovalList("SecondStageApprover");
        assertViewName(agentApproval, PageView.APPROVALLISTSTAGE2);
        Map<String, Object> model = agentApproval.getModel();
        assertNotNull(model.get(PageCommand.APPROVALLIST));
    }

    @Test
    public void shouldReturnSupervisorListView() {
        ModelAndView agentApproval = controller.showSupervisorList(null);
        assertViewName(agentApproval, PageView.SUPERVISORLIST);
    }

    @Test
    public void shouldReturnSupervisorUserListView() {
        ModelAndView agentApproval = controller.showSupervisorList("Supervisor");
        assertViewName(agentApproval, PageView.SUPERVISORLIST);
    }

    @Test
    public void shouldReturnExceptionListView() {
        ModelAndView agentApproval = controller.showExceptionList(null);
        assertViewName(agentApproval, PageView.EXCEPTIONLIST);
        Map<String, Object> model = agentApproval.getModel();
        assertNotNull(model.get(PageCommand.APPROVALLIST));
    }

    @Test
    public void shouldReturnExceptionUserListView() {
        ModelAndView agentApproval = controller.showExceptionList("Exceptions");
        assertViewName(agentApproval, PageView.EXCEPTIONLIST);
        Map<String, Object> model = agentApproval.getModel();
        assertNotNull(model.get(PageCommand.APPROVALLIST));
    }

    @Test
    public void shouldCallServiceToClaim() {
        when(mockWorkflowService.claim(anyString(), anyString())).thenReturn(AgentGroup.FIRST_STAGE_APPROVER);
        controller.claim(ApprovalTestUtil.getSearch());
        verify(mockWorkflowService).claim(anyString(), anyString());
    }
    
    @Test
    public void shouldPopulateWorkflowStatusList() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        controller.populateWorkflowStatus(model);
        assertNotNull(model.get(PageAttribute.WORK_FLOW_STATUS_LIST));
    }
    
    @Test
    public void shouldPopulatePaymentTypes() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        controller.populatePaymentTypes(model);
        assertNotNull(model.get(PageAttribute.PAYMENT_TYPE));
    }
    
    @Test
    public void shouldPopulateRefundApprovalReasons() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        controller.populateRefundApprovalReasons(model);
        assertNotNull(model.get(PageAttribute.REFUND_REASONS));
    }
}
