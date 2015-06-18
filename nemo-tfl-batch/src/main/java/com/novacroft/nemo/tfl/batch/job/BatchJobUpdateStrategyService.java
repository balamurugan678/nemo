package com.novacroft.nemo.tfl.batch.job;

import java.util.List;

import org.quartz.JobDataMap;

import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;

/**
 * Interface for batch update strategy. There can Hibernate or other JDBC implementations provided.
 * 
 * @author vijay.dabas
 * 
 * @param <E>
 */
public interface  BatchJobUpdateStrategyService<E extends BaseFileImportJob> {
	void setFileImportJob(E x);
	void processBatchUpdate(List<String[]> data, JobLogDTO log, JobDataMap jobDataMap);
}
