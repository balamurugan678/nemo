package com.novacroft.nemo.tfl.common.data_service;

import java.util.Date;
import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.JobLog;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;

/**
 * Job Log data service specification
 */
public interface JobLogDataService extends BaseDataService<JobLog, JobLogDTO> {
    void create(JobLogDTO jobLogDTO);

    List<JobLogDTO> findByFileName(String fileName);

    List<JobLog> findJobLogSearchDetailsByExactExecutionDatesAndJobName(String jobName, String startDate, String endDate);

    List<JobLog> findJobLogSearchDetailsByExactExecutionDatesAndJobNameWithLimits(String jobName, Date startedAt, Date endedAt, Integer startCount, Integer endCount);

    JobLogDTO getJobLogDetailsByJobId(Long jobId);

}
