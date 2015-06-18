package com.novacroft.nemo.tfl.common.transfer;

import java.util.Date;

/**
 * Data Transfer Object to hold attributes of the Job Log stored in the HTTP session.
 * 
 */
public class JobLogSessionData {
    protected Long id;
    protected String jobName;
    protected String fileName;
    protected Date startedAt;
    protected Date endedAt;
    protected boolean searchCriteriaNonEmptyFlag = false;

    public JobLogSessionData(Long id, String jobName, String fileName, Date startedAt, Date endedAt) {
		this.id = id;
		this.jobName = jobName;
		this.fileName = fileName;
		this.startedAt = startedAt;
		this.endedAt = endedAt;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}
	public Date getEndedAt() {
		return endedAt;
	}
	public void setEndedAt(Date endedAt) {
		this.endedAt = endedAt;
	}
	public boolean isSearchCriteriaNonEmptyFlag() {
		return searchCriteriaNonEmptyFlag;
	}
	public void setSearchCriteriaNonEmptyFlag(boolean searchCriteriaNonEmptyFlag) {
		this.searchCriteriaNonEmptyFlag = searchCriteriaNonEmptyFlag;
	}
	public JobLogSessionData() {
	}
}
