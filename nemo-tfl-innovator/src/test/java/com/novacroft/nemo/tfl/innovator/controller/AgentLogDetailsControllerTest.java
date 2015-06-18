package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.PageCommand.AGENTLOG_SEARCH;
import static com.novacroft.nemo.tfl.common.constant.PageUrl.INV_AGENTLOG_SEARCH;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_AGENTLOG_DETAILS;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.tfl.common.application_service.JobLogService;
import com.novacroft.nemo.tfl.common.command.impl.AgentLogSearchCmdImpl;
import com.novacroft.nemo.tfl.common.controller.BaseTestController;

public class AgentLogDetailsControllerTest extends BaseTestController {
    private AgentLogDetailsController controller;

    private JobLogService mockJobLogService;

    private AgentLogSearchCmdImpl mockAgentLogSearchCmd;
    private HttpServletRequest mockRequest;
    private SelectListDTO mockSelectListDTO;
    private HttpSession mockSession;
    private SelectListService mockSelectListService;
    private BindingResult mockBindingResult;


    @Before
    public void setUp() {
        this.controller = mock(AgentLogDetailsController.class, CALLS_REAL_METHODS);
        this.mockJobLogService = mock(JobLogService.class);
        this.controller.jobLogService = this.mockJobLogService;
        this.mockAgentLogSearchCmd = mock(AgentLogSearchCmdImpl.class);
        this.mockRequest = mock(HttpServletRequest.class);
        this.mockSelectListDTO = mock(SelectListDTO.class);
        this.mockSession = mock(HttpSession.class);
        this.mockSelectListService = mock(SelectListService.class);
        this.mockBindingResult = mock(BindingResult.class);
        this.controller.selectListService = mockSelectListService;
        
    }

    @Test
    public void shouldLoadJobLogDetailsForNonNullJobIdAndNoException() {
        when(this.mockAgentLogSearchCmd.getId()).thenReturn(1L);
        doNothing().when(this.mockJobLogService).getAgentLogDetailsByJobId(any(AgentLogSearchCmdImpl.class), anyLong());
        ModelAndView result = this.controller.loadJobLogDetails(this.mockAgentLogSearchCmd, this.mockRequest);
        assertViewName(result, INV_AGENTLOG_DETAILS);
        assertModelAttributeAvailable(result, AGENTLOG_SEARCH);
        verify(this.mockJobLogService).getAgentLogDetailsByJobId(any(AgentLogSearchCmdImpl.class), anyLong());
    }

    @Test
    public void shouldLoadJobLogDetailsForNonNullJobIdAndException() {
        when(this.mockAgentLogSearchCmd.getId()).thenReturn(1L);
        doThrow(new NullPointerException()).when(this.mockJobLogService).getAgentLogDetailsByJobId(any(AgentLogSearchCmdImpl.class), anyLong());
        ModelAndView result = this.controller.loadJobLogDetails(this.mockAgentLogSearchCmd, this.mockRequest);
        assertViewName(result, INV_AGENTLOG_DETAILS);
        assertModelAttributeAvailable(result, AGENTLOG_SEARCH);
        assertModelAttributeAvailable(result, "error");
        verify(this.mockJobLogService).getAgentLogDetailsByJobId(any(AgentLogSearchCmdImpl.class), anyLong());
    }

    @Test
    public void shouldLoadJobLogDetailsForNullJobId() {
        when(this.mockAgentLogSearchCmd.getId()).thenReturn(null);
        doNothing().when(this.mockJobLogService).getAgentLogDetailsByJobId(any(AgentLogSearchCmdImpl.class), anyLong());
        ModelAndView result = this.controller.loadJobLogDetails(this.mockAgentLogSearchCmd, this.mockRequest);
        assertViewName(result, INV_AGENTLOG_DETAILS);
        assertModelAttributeAvailable(result, AGENTLOG_SEARCH);
        verify(this.mockJobLogService, never()).getAgentLogDetailsByJobId(any(AgentLogSearchCmdImpl.class), anyLong());
    }

    @Test
    public void shouldLoadJobLogSeachDetailsBasedOnSessionStoredSearchCriteriaByInvokingCancelFunction() {
    	when(mockSelectListService.getSelectList(anyString())).thenReturn(mockSelectListDTO);
        ModelAndView result = this.controller.cancel(mockSession, mockAgentLogSearchCmd, mockBindingResult);
        assertEquals(INV_AGENTLOG_SEARCH, redirectViewCheck(result));
        assertModelAttributeAvailable(result, AGENTLOG_SEARCH);
    }
}