package com.novacroft.nemo.tfl.common.application_service;

import java.util.List;

import com.novacroft.nemo.tfl.common.command.impl.AgentLogSearchCmdImpl;
import com.novacroft.nemo.tfl.common.domain.JobLog;

/**
 * Job Log application service specification
 */
public interface JobLogService {
    Boolean isFileAlreadyProcessed(String fileName);

    List<JobLog> findJobLogSearchDetailsBetweenExecutionDatesWithJobName(AgentLogSearchCmdImpl logSearchCriteria);

    List<JobLog> findJobLogSearchDetailsBetweenExecutionDatesWithJobNameWithLimits(AgentLogSearchCmdImpl logSearchCriteria);
    
    void getAgentLogDetailsByJobId(AgentLogSearchCmdImpl cmd, Long jobId);

}
