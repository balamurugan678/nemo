package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.Date;
import java.util.List;
import static com.novacroft.nemo.common.utils.Converter.convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.tfl.common.application_service.JobLogService;
import com.novacroft.nemo.tfl.common.command.impl.AgentLogSearchCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.JobLogDataService;
import com.novacroft.nemo.tfl.common.domain.JobLog;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;

/**
 * Job Log application service specification
 */
@Service("jobLogService")
public class JobLogServiceImpl implements JobLogService {
    
	@Autowired
    protected JobLogDataService jobLogDataService;

    @Override
    public Boolean isFileAlreadyProcessed(String fileName) {
        return isJobForFile(this.jobLogDataService.findByFileName(fileName));
    }

    protected Boolean isJobForFile(List<JobLogDTO> jobs) {
        return (jobs != null && !jobs.isEmpty());
    }

	@Override
	public List<JobLog> findJobLogSearchDetailsBetweenExecutionDatesWithJobName(AgentLogSearchCmdImpl logSearchCriteria) {
		String jobName = logSearchCriteria.getJobName();
		String startedAt = logSearchCriteria.getStartDate();
		String endedAt = logSearchCriteria.getEndDate();
		return jobLogDataService.findJobLogSearchDetailsByExactExecutionDatesAndJobName(jobName, startedAt, endedAt) ;
	}

	public List<JobLog> findJobLogSearchDetailsBetweenExecutionDatesWithJobNameWithLimits(AgentLogSearchCmdImpl logSearchCriteria) {
		String jobName = logSearchCriteria.getJobName();
		Date startedAt = logSearchCriteria.getStartedAt();
		Date endedAt = logSearchCriteria.getEndedAt();
		Integer startCount = logSearchCriteria.getStartCount();
		Integer endCount = logSearchCriteria.getEndCount();
		return jobLogDataService.findJobLogSearchDetailsByExactExecutionDatesAndJobNameWithLimits(jobName, startedAt, endedAt, startCount, endCount);
	}

	@Override
	public void getAgentLogDetailsByJobId(AgentLogSearchCmdImpl cmd, Long jobId) {
		JobLogDTO jobLogDTO = jobLogDataService.getJobLogDetailsByJobId(jobId);
		convert(jobLogDTO, cmd);
	}
    
    
}
