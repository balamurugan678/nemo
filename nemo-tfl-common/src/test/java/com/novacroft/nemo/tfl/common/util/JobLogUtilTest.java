package com.novacroft.nemo.tfl.common.util;

import com.novacroft.nemo.tfl.common.constant.JobStatus;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;
import com.novacroft.nemo.tfl.common.transfer.JobLogSessionData;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;

import static com.novacroft.nemo.test_support.JobLogTestUtil.*;
import static com.novacroft.nemo.tfl.common.constant.AgentLogSearchConstant.SESSION_ATTRIBUTE_JOBLOG_DATA;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * JobLogUtil unit tests
 */
public class JobLogUtilTest {

    @Test
    public void shouldCreateLogWithFile() {
        JobLogDTO result = JobLogUtil.createLog(JOB_NAME_1, FILE_NAME_1);
        assertEquals(JOB_NAME_1, result.getJobName());
        assertEquals(FILE_NAME_1, result.getFileName());
    }

    @Test
    public void shouldCreateLog() {
        JobLogDTO result = JobLogUtil.createLog(JOB_NAME_1);
        assertEquals(JOB_NAME_1, result.getJobName());
    }

    @Test
    public void shouldLogMessage() {
        JobLogDTO testLog = new JobLogDTO();
        JobLogUtil.logMessage(testLog, LOG_1);
        assertThat(testLog.getLog(), endsWith(LOG_1));
    }

    @Test
    public void shouldLogMessageAppend() {
        JobLogDTO testLog = new JobLogDTO();
        testLog.setLog(LOG_1);
        JobLogUtil.logMessage(testLog, LOG_2);
        assertThat(testLog.getLog(), endsWith(LOG_2));
    }

    @Test
    public void shouldLogInComplete() {
        JobLogDTO testLog = new JobLogDTO();
        JobLogUtil.logInComplete(testLog);
        assertThat(testLog.getStatus(), is(JobStatus.IN_COMPLETE.code()));
    }

    @Test
    public void shouldLogEnd() {
        JobLogDTO testLog = new JobLogDTO();
        assertThat(testLog.getEndedAt(), nullValue());
        JobLogUtil.logEnd(testLog);
        assertThat(testLog.getEndedAt(), notNullValue());
    }

    @Test
    public void shouldLogComplete() {
        JobLogDTO testLog = new JobLogDTO();
        JobLogUtil.logComplete(testLog);
        assertThat(testLog.getStatus(), is(JobStatus.COMPLETE.code()));
    }

    @Test
    public void testAddJobLogSearchDataInSession() {

        MockHttpSession mockHttpSession = new MockHttpSession();
        JobLogSessionData jobLogSessionDataExpected = new JobLogSessionData();
        jobLogSessionDataExpected.setId(100L);
        JobLogUtil.addJobLogSearchDataInSession(mockHttpSession, jobLogSessionDataExpected);
        JobLogSessionData jobLogSessionDataActual =
                (JobLogSessionData) mockHttpSession.getAttribute(SESSION_ATTRIBUTE_JOBLOG_DATA);
        assertEquals(jobLogSessionDataExpected.getId(), jobLogSessionDataActual.getId());
    }

    @Test
    public void testRemoveJobLogSearchDataFromSession() {

        MockHttpSession mockHttpSession = new MockHttpSession();
        JobLogSessionData jobLogSessionData = new JobLogSessionData();
        jobLogSessionData.setId(101L);
        mockHttpSession.setAttribute(SESSION_ATTRIBUTE_JOBLOG_DATA, jobLogSessionData);
        JobLogUtil.removeJobLogSearchDataFromSession(mockHttpSession);
        assertNull(mockHttpSession.getAttribute(SESSION_ATTRIBUTE_JOBLOG_DATA));
    }

    @Test
    public void testGetJobLogSearchDataFromSession() {

        MockHttpSession mockHttpSession = new MockHttpSession();
        JobLogSessionData jobLogSessionDataExpected = new JobLogSessionData();
        jobLogSessionDataExpected.setId(102L);
        mockHttpSession.setAttribute(SESSION_ATTRIBUTE_JOBLOG_DATA, jobLogSessionDataExpected);
        JobLogSessionData jobLogSessionDataActual = JobLogUtil.getJobLogSearchDataFromSession(mockHttpSession);
        assertEquals(jobLogSessionDataExpected, jobLogSessionDataActual);
    }

    @Test
    public void shouldFormatLogAsHtml() {
        final String testLog = "ABC %s %s XYZ";
        assertEquals(String.format(testLog, JobLogUtil.NEW_LINE_MARK_UP, JobLogUtil.ERROR_TEXT_WITH_EMPHASIS_MARK_UP),
                JobLogUtil.formatLogAsHtml(String.format(testLog, JobLogUtil.NEW_LINE, JobLogUtil.ERROR_TEXT)));
    }
}
