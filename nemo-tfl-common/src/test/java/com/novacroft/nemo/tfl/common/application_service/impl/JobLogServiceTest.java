package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.DateTestUtil.AUG_01;
import static com.novacroft.nemo.test_support.DateTestUtil.AUG_05;
import static com.novacroft.nemo.test_support.JobLogTestUtil.ENDED_AT_1;
import static com.novacroft.nemo.test_support.JobLogTestUtil.END_COUNT_1;
import static com.novacroft.nemo.test_support.JobLogTestUtil.FILE_NAME_1;
import static com.novacroft.nemo.test_support.JobLogTestUtil.JOB_ID3;
import static com.novacroft.nemo.test_support.JobLogTestUtil.JOB_NAME_5;
import static com.novacroft.nemo.test_support.JobLogTestUtil.STARTED_AT_1;
import static com.novacroft.nemo.test_support.JobLogTestUtil.START_COUNT_1;
import static com.novacroft.nemo.test_support.JobLogTestUtil.getTestJobLogDTO1;
import static com.novacroft.nemo.test_support.JobLogTestUtil.getTestJobLogDTOList1;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.command.impl.AgentLogSearchCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.JobLogDataService;

/**
 * JobLogService unit tests
 */
@SuppressWarnings("unchecked")
public class JobLogServiceTest {
    private JobLogServiceImpl service;
    private JobLogDataService mockJobLogDataService;
    private AgentLogSearchCmdImpl mockAgentLogSearchCmdImpl;
    
    @Before
    public void setUp() {
        service = new JobLogServiceImpl();
        mockJobLogDataService = mock(JobLogDataService.class);
        mockAgentLogSearchCmdImpl = mock(AgentLogSearchCmdImpl.class);
        service.jobLogDataService = mockJobLogDataService;
    }
    
    @Test
    public void isJobForFileShouldReturnTrue() {
        assertTrue(service.isJobForFile(getTestJobLogDTOList1()));
    }

    @Test
    public void isJobForFileShouldReturnFalseWithNullList() {
        assertFalse(service.isJobForFile(null));
    }

    @Test
    public void isJobForFileShouldReturnFalseWithEmptyList() {
        assertFalse(service.isJobForFile(Collections.EMPTY_LIST));
    }

    @Test
    public void isFileAlreadyProcessedShouldReturnTrue() {
        when(mockJobLogDataService.findByFileName(anyString())).thenReturn(getTestJobLogDTOList1());
        assertTrue(service.isFileAlreadyProcessed(FILE_NAME_1));
    }

    @Test
    public void isFileAlreadyProcessedShouldReturnFalse() {
        when(mockJobLogDataService.findByFileName(anyString())).thenReturn(Collections.EMPTY_LIST);
        assertFalse(service.isFileAlreadyProcessed(FILE_NAME_1));
    }
    
    @Test
    public void testFindJobLogSearchDetailsBetweenExecutionDatesWithJobName() {
        when(mockAgentLogSearchCmdImpl.getJobName()).thenReturn(JOB_NAME_5);
        when(mockAgentLogSearchCmdImpl.getStartDate()).thenReturn(AUG_01);
        when(mockAgentLogSearchCmdImpl.getEndDate()).thenReturn(AUG_05);
        when(mockJobLogDataService.findJobLogSearchDetailsByExactExecutionDatesAndJobName(
                        anyString(), anyString(), anyString())
            ).thenReturn(null);
        service.findJobLogSearchDetailsBetweenExecutionDatesWithJobName(mockAgentLogSearchCmdImpl);
        verify(mockJobLogDataService).findJobLogSearchDetailsByExactExecutionDatesAndJobName(
                        JOB_NAME_5, AUG_01, AUG_05);
    }
    
    @Test
    public void testFindJobLogSearchDetailsBetweenExecutionDatesWithJobNameWithLimits() {
        when(mockAgentLogSearchCmdImpl.getJobName()).thenReturn(JOB_NAME_5);
        when(mockAgentLogSearchCmdImpl.getStartedAt()).thenReturn(STARTED_AT_1);
        when(mockAgentLogSearchCmdImpl.getEndedAt()).thenReturn(ENDED_AT_1);
        when(mockAgentLogSearchCmdImpl.getStartCount()).thenReturn(START_COUNT_1);
        when(mockAgentLogSearchCmdImpl.getEndCount()).thenReturn(END_COUNT_1);
        when(mockJobLogDataService.findJobLogSearchDetailsByExactExecutionDatesAndJobNameWithLimits(
                        anyString(), any(Date.class), any(Date.class), anyInt(), anyInt())
            ).thenReturn(null);
        service.findJobLogSearchDetailsBetweenExecutionDatesWithJobNameWithLimits(mockAgentLogSearchCmdImpl);
        verify(mockJobLogDataService).findJobLogSearchDetailsByExactExecutionDatesAndJobNameWithLimits(
                        JOB_NAME_5, STARTED_AT_1, ENDED_AT_1, START_COUNT_1, END_COUNT_1);
    }

    @Test
    public void testGetAgentLogDetailsByJobId() {
        when(mockJobLogDataService.getJobLogDetailsByJobId(anyLong())).thenReturn(getTestJobLogDTO1());
        service.getAgentLogDetailsByJobId(mockAgentLogSearchCmdImpl, JOB_ID3);
        verify(mockJobLogDataService).getJobLogDetailsByJobId(JOB_ID3);
    }
}
