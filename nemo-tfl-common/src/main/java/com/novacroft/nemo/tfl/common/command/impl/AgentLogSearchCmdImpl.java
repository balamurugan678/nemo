package com.novacroft.nemo.tfl.common.command.impl;

import static com.novacroft.nemo.tfl.common.constant.AgentLogSearchConstant.DEFAULT_END_COUNT;
import static com.novacroft.nemo.tfl.common.constant.AgentLogSearchConstant.DEFAULT_RESULT_LENGTH;
import static com.novacroft.nemo.tfl.common.constant.AgentLogSearchConstant.DEFAULT_START_COUNT;

import java.util.Date;

/**
 * Command class for Innovator Agent Log search
 */
public class AgentLogSearchCmdImpl {

    protected Long id;
    protected String jobName;
    protected String fileName;
    protected Date startedAt;
    protected Date endedAt;
    protected String status;
    protected String log;
    protected Integer resultLength = DEFAULT_RESULT_LENGTH;
    protected Integer startCount = DEFAULT_START_COUNT;
    protected Integer endCount = DEFAULT_END_COUNT;
    protected String startDate;
    protected String endDate;
    protected boolean searchCriteriaNonEmptyFlag = false;
    
    public AgentLogSearchCmdImpl () {

    }

    public AgentLogSearchCmdImpl (Long id, String jobName, String fileName,  Date startedAt ,  Date endedAt, String status, String log, Integer resultLength, Integer startCount,
            Integer endCount) {
        this.id = id;
        this.jobName = jobName;
        this.fileName = fileName;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.status = status;
        this.log = log;
        this.resultLength = resultLength;
        this.startCount = startCount;
        this.endCount = endCount;
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
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
    
    public Integer getResultLength() {
        return resultLength;
    }

    public void setResultLength(Integer resultLength) {
        this.resultLength = resultLength;
    }

    public Integer getStartCount() {
        return startCount;
    }

    public void setStartCount(Integer startCount) {
        this.startCount = startCount;
    }

    public Integer getEndCount() {
        return endCount;
    }

    public void setEndCount(Integer endCount) {
        this.endCount = endCount;
    }

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public boolean isSearchCriteriaNonEmptyFlag() {
		return searchCriteriaNonEmptyFlag;
	}
	public void setSearchCriteriaNonEmptyFlag(boolean searchCriteriaNonEmptyFlag) {
		this.searchCriteriaNonEmptyFlag = searchCriteriaNonEmptyFlag;
	}
    
}
