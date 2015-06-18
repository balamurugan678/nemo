package com.novacroft.nemo.tfl.innovator.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.test_support.JobLogTestUtil;
import com.novacroft.nemo.tfl.common.application_service.JobLogService;
import com.novacroft.nemo.tfl.common.command.impl.AgentLogSearchCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageView;

public class AgentLogSearchControllerTest {
    private AgentLogSearchController controller;

    private JobLogService mockJobLogService;
    private SelectListService mockSelectListService;
    private Model mockModel;
    private SelectListDTO mockSelectListDTO;
    private AgentLogSearchCmdImpl mockCmd;
    private HttpSession mockSession;

    @Before
    public void setUp() {
        this.controller = mock(AgentLogSearchController.class);
        this.mockJobLogService = mock(JobLogService.class);
        this.controller.jobLogService = this.mockJobLogService;
        this.mockSelectListService = mock(SelectListService.class);
        this.controller.selectListService = this.mockSelectListService;
        this.mockModel = mock(Model.class);
        this.mockSelectListDTO = mock(SelectListDTO.class);
        this.mockCmd = mock(AgentLogSearchCmdImpl.class);
        this.mockSession = mock(HttpSession.class);
    }

    @Test
    public void shouldPopulateJobNameList() {
        doCallRealMethod().when(this.controller).populateJobNameList(any(Model.class));
        when(this.mockSelectListService.getSelectList(anyString())).thenReturn(this.mockSelectListDTO);
        when(this.mockModel.addAttribute(anyString(), anyObject())).thenReturn(null);
        this.controller.populateJobNameList(this.mockModel);
        verify(this.mockSelectListService).getSelectList(anyString());
        verify(this.mockModel).addAttribute(anyString(), anyObject());
    }

    @Test
    public void shouldView() {
        doCallRealMethod().when(controller).view(mockCmd, mockSession);
        ModelAndView result = controller.view(this.mockCmd, mockSession);
        ModelAndViewAssert.assertViewName(result, PageView.INV_AGENTLOG_SEARCH);
        ModelAndViewAssert.assertModelAttributeAvailable(result, PageCommand.AGENTLOG_SEARCH);
    }

    @Test
    public void shouldViewRedirect() {
        doCallRealMethod().when(controller).view((AgentLogSearchCmdImpl) anyObject(), (HttpSession) anyObject());
        when(mockSession.getAttribute(anyString())).thenReturn(JobLogTestUtil.getTestJobLogSessionDataWithSearchCriteriaTrue());

        AgentLogSearchCmdImpl cmd = new AgentLogSearchCmdImpl();
        ModelAndView result = controller.view(cmd, mockSession);
        ModelAndViewAssert.assertViewName(result, PageView.INV_AGENTLOG_SEARCH);
        ModelAndViewAssert.assertModelAttributeAvailable(result, PageCommand.AGENTLOG_SEARCH);
    }

    @Test
    public void getJobLogResultsByJobNameAndExecutionDates() {
        doCallRealMethod().when(controller)
                        .getJobLogResultsByJobNameAndExecutionDates((HttpSession) anyObject(), (AgentLogSearchCmdImpl) anyObject());
        when(mockJobLogService.findJobLogSearchDetailsBetweenExecutionDatesWithJobName((AgentLogSearchCmdImpl) anyObject())).thenReturn(
                        JobLogTestUtil.getTestJobLogList3());

        AgentLogSearchCmdImpl cmd = JobLogTestUtil.getAgentLogSearchCmdImpl();
        String result = controller.getJobLogResultsByJobNameAndExecutionDates(mockSession, cmd);
        assertNotNull(result);
    }

}