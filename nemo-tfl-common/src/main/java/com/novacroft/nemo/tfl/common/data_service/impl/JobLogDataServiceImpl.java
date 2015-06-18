package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.converter.impl.JobLogConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.JobLogDAO;
import com.novacroft.nemo.tfl.common.data_service.JobLogDataService;
import com.novacroft.nemo.tfl.common.domain.JobLog;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;

/**
 * Job Log data service implementation
 */
@Service(value = "jobLogDataService")
@Transactional(readOnly = true)
public class JobLogDataServiceImpl extends BaseDataServiceImpl<JobLog, JobLogDTO> implements JobLogDataService {
    static final Logger logger = LoggerFactory.getLogger(JobLogDataServiceImpl.class);

    public JobLogDataServiceImpl() {
        super();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void create(JobLogDTO jobLogDTO) {
        createOrUpdate(jobLogDTO);
    }

    @Override
    public List<JobLogDTO> findByFileName(String fileName) {
        JobLog exampleJobLog = new JobLog();
        exampleJobLog.setFileName(fileName);
        return this.convert(this.dao.findByExample(exampleJobLog));
    }

    @Autowired
    public void setDao(JobLogDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(JobLogConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public JobLog getNewEntity() {
        return new JobLog();
    }

	@Override
    @SuppressWarnings("unchecked")
	public List<JobLog> findJobLogSearchDetailsByExactExecutionDatesAndJobName(String jobName, String startDate, String endDate) {
		List<JobLog> results = null;
		StringBuffer hsql = new StringBuffer("select J from JobLog J where ");
		if(StringUtil.isNotBlank(jobName)) {
			hsql.append(" J.jobName = ? and J.startedAt >= to_date(?, 'DD-MM-YYYY') and J.endedAt <= to_date(?,'DD-MM-YYYY')+1");
	        results = dao.findByQuery(hsql.toString(), jobName, startDate, endDate);
		}
		else {
			hsql.append("J.startedAt >= to_date(?,'DD-MM-YYYY') and J.endedAt <= to_date(?,'DD-MM-YYYY')+1");
	        results = dao.findByQuery(hsql.toString(), startDate, endDate);
		}
        return results;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<JobLog> findJobLogSearchDetailsByExactExecutionDatesAndJobNameWithLimits(String jobName, Date startedAt, Date endedAt, Integer startCount, Integer endCount) {
		
		List<JobLog> results = null;
		StringBuffer hsql = new StringBuffer("select J from JobLog J where ");
		if(StringUtil.isNotBlank(jobName)) {
			hsql.append(" J.jobName = ? and J.startedAt >= ? and J.endedAt <= ?");
	        results = dao.findByQueryWithLimit(hsql.toString(), startCount, endCount, jobName, startedAt, endedAt);
		}
		else {
			hsql.append("J.startedAt >= ? and J.endedAt <= ?");
	        results = dao.findByQueryWithLimit(hsql.toString(), startCount, endCount, startedAt, endedAt);
		}
        return results;
	}
	
	@Override
	public JobLogDTO getJobLogDetailsByJobId(Long jobId) {
		 final String hsql = "select J from JobLog J WHERE J.id = ?";
		 JobLog joblog = this.dao.findByQueryUniqueResult(hsql, jobId);
		 return (joblog != null) ? this.converter.convertEntityToDto(joblog) : null;
	}

}
