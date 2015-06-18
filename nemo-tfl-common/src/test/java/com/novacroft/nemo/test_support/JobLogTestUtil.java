package com.novacroft.nemo.test_support;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.novacroft.nemo.tfl.common.command.impl.AgentLogSearchCmdImpl;
import com.novacroft.nemo.tfl.common.domain.JobLog;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;
import com.novacroft.nemo.tfl.common.transfer.JobLogSessionData;

/**
 * Fixtures and utilities for JobLog tests
 */
public final class JobLogTestUtil {
    public static final Long JOB_ID1 = FileTestUtil.TEST_JOB_ID_1;
    public static final String JOB_NAME_1 = "test-job-1";
    public static final String FILE_NAME_1 = FileTestUtil.TEST_FILE_NAME_1;
    public static final Date STARTED_AT_1 = DateTestUtil.getAug19();
    public static final Date ENDED_AT_1 = DateTestUtil.getAug21();
    public static final String STATUS_1 = "test-status-1";
    public static final String LOG_1 = "message-1";
    public static final Integer START_COUNT_1 = 1;
    public static final Integer END_COUNT_1 = 99;

    public static final String JOB_NAME_2 = "test-job-2";
    public static final String FILE_NAME_2 = FileTestUtil.TEST_FILE_NAME_2;
    public static final Date STARTED_AT_2 = DateTestUtil.getAug20();
    public static final Date ENDED_AT_2 = DateTestUtil.getAug21();
    public static final String STATUS_2 = "test-status-2";
    public static final String LOG_2 = "message-2";

    public static final Long JOB_ID3 = FileTestUtil.TEST_JOB_ID_3;
    public static final String JOB_NAME_3 = "ImportAutoLoadChangesCubicFileJob";
    public static final String FILE_NAME_3 = FileTestUtil.TEST_FILE_NAME_3;
    public static final Date STARTED_AT_3 = DateTestUtil.getJun10();
    public static final Date ENDED_AT_3 = DateTestUtil.getAug10();
    public static final String STATUS_3 = "Completed";
    public static final String LOG_3 = "Log Value for importAutoLoadChangesCubicFileJob";

    public static final Long JOB_ID4 = FileTestUtil.TEST_JOB_ID_4;
    public static final String JOB_NAME_4 = "ImportAdHocDistributionCubicFileJob";
    public static final String FILE_NAME_4 = FileTestUtil.TEST_FILE_NAME_4;
    public static final Date STARTED_AT_4 = DateTestUtil.getJun10();
    public static final Date ENDED_AT_4 = DateTestUtil.getAug10();
    public static final String STATUS_4 = "Completed";
    public static final String LOG_4 = "Log Value for ImportAdHocDistributionCubicFileJob";

    
    public static final Long JOB_ID5 = FileTestUtil.TEST_JOB_ID_5;
    public static final String JOB_NAME_5 = "ImportAutoLoadsPerformedCubicFileJob";
    public static final String FILE_NAME_5 = FileTestUtil.TEST_FILE_NAME_5;
    public static final Date STARTED_AT_5 = DateTestUtil.getJun10();
    public static final Date ENDED_AT_5 = DateTestUtil.getAug10();
    public static final String STATUS_5 = "Completed";
    public static final String LOG_5 = "Log Value for importAutoLoadsPerformedCubicFileJob";
    
    public static final String NULL_JOB_NAME="";
    public static final String TEST_JOB_NAME="agent_log_test_job_name";
    
    public static final String NO_RECORDFOUND_LENGTH_ERROR = "No record Found";
    public static final int NO_RECORDFOUND_LENGTH = 0;
    
    public static JobLogDTO getTestJobLogDTO1() {
        return getTestJobLogDTO(JOB_NAME_1, FILE_NAME_1, STARTED_AT_1, ENDED_AT_1, STATUS_1, LOG_1);
    }

    public static JobLogDTO getTestJobLogDTO2() {
        return getTestJobLogDTO(JOB_NAME_2, FILE_NAME_2, STARTED_AT_2, ENDED_AT_2, STATUS_2, LOG_2);
    }

    public static JobLogDTO getTestJobLogDTOForLogDetails() {
        return getTestJobLogDTO(JOB_NAME_3, FILE_NAME_3, STARTED_AT_3, ENDED_AT_3, STATUS_3, LOG_3);
    }
    
    public static JobLog getTestJobLog3() {
        return getTestJobLog(JOB_ID3, JOB_NAME_3, FILE_NAME_3, STARTED_AT_3, ENDED_AT_3, STATUS_3, LOG_3);
    }
    public static JobLog getTestJobLog4() {
        return getTestJobLog(JOB_ID4, JOB_NAME_4, FILE_NAME_4, STARTED_AT_4, ENDED_AT_4, STATUS_4, LOG_4);
    }
    public static JobLog getTestJobLog5() {
        return getTestJobLog(JOB_ID5, JOB_NAME_5, FILE_NAME_5, STARTED_AT_5, ENDED_AT_5, STATUS_5, LOG_5);
    }
    
    public static JobLogSessionData getTestJobLogSessionDataWithSearchCriteriaTrue() {
        return getTestJobLogSessionData(JOB_ID1, JOB_NAME_1, FILE_NAME_1, STARTED_AT_1, ENDED_AT_1, Boolean.TRUE);
    }

    public static List<JobLogDTO> getTestJobLogDTOList1() {
        List<JobLogDTO> jobs = new ArrayList<JobLogDTO>();
        jobs.add(getTestJobLogDTO1());
        return jobs;
    }

    public static List<JobLogDTO> getTestJobLogDTOList2() {
        List<JobLogDTO> jobs = getTestJobLogDTOList1();
        jobs.add(getTestJobLogDTO2());
        return jobs;
    }

    public static JobLogDTO getTestJobLogDTO(String jobName, String fileName, Date startedAt, Date endedAt, String status, String log) {
        return new JobLogDTO(jobName, fileName, startedAt, endedAt, status, log);
    }

    public static JobLog getTestJobLog(Long id, String jobName, String fileName, Date startedAt, Date endedAt, String status,  String log) {
    	JobLog jobLog = new JobLog();
    	jobLog.setId(id);
    	jobLog.setJobName(jobName);
    	jobLog.setFileName(fileName);
    	jobLog.setStartedAt(startedAt);
    	jobLog.setEndedAt(endedAt);
    	jobLog.setStatus(status);
    	jobLog.setLog(log);
    	return jobLog;
    }
    
    public static JobLogSessionData getTestJobLogSessionData(Long id, String jobName, String fileName, Date startedAt, Date endedAt,
                    Boolean searchCriteriaNonEmptyFlag) {
        JobLogSessionData jobLogSessionData = new JobLogSessionData();
        jobLogSessionData.setId(id);
        jobLogSessionData.setJobName(jobName);
        jobLogSessionData.setFileName(fileName);
        jobLogSessionData.setStartedAt(startedAt);
        jobLogSessionData.setEndedAt(endedAt);
        jobLogSessionData.setSearchCriteriaNonEmptyFlag(searchCriteriaNonEmptyFlag);
        return jobLogSessionData;
    }
    
    public static String getSearchResultForNoRecordFoundSearchCriteriaInWords() {
    	return NO_RECORDFOUND_LENGTH_ERROR;
    }
    
    public static int getSearchResultForNoRecordFoundSearchCriteriaInCount() {
    	return NO_RECORDFOUND_LENGTH;
    }
    
    public static List<JobLog> getTestJobLogList3() {
        List<JobLog> jobs = new ArrayList<JobLog>();
        jobs.add(getTestJobLog3());
        return jobs;
    }

    public static List<JobLog> getTestJobLogList4() {
        List<JobLog> jobs = new ArrayList<JobLog>();
        jobs.add(getTestJobLog4());
        return jobs;
    }
    
    public static List<JobLog> getTestJobLogList5() {
        List<JobLog> jobs = new ArrayList<JobLog>();
        jobs.add(getTestJobLog5());
        return jobs;
    }

    public static AgentLogSearchCmdImpl getAgentLogSearchCmdImpl() {
        AgentLogSearchCmdImpl cmd = new AgentLogSearchCmdImpl();
        cmd.setStartedAt(JobLogTestUtil.STARTED_AT_1);
        cmd.setEndedAt(JobLogTestUtil.ENDED_AT_1);
        cmd.setJobName(JobLogTestUtil.JOB_NAME_1);
        return cmd;
    }

    private JobLogTestUtil() {
    }
}
