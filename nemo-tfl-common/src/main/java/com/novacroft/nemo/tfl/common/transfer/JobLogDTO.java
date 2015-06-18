package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;
import com.novacroft.nemo.tfl.common.constant.JobStatus;

import java.util.Date;

/**
 * Job Log transfer class
 */
public class JobLogDTO extends AbstractBaseDTO {
    protected String jobName;
    protected String fileName;
    protected Date startedAt;
    protected Date endedAt;
    protected String status;
    protected String log;

    public JobLogDTO() {
    }

    public JobLogDTO(String jobName) {
        this.jobName = jobName;
        this.startedAt = new Date();
        this.status = JobStatus.COMPLETE.code();
        this.log = "";
    }

    public JobLogDTO(String jobName, String fileName) {
        this.jobName = jobName;
        this.fileName = fileName;
        this.startedAt = new Date();
        this.status = JobStatus.COMPLETE.code();
        this.log = "";
    }

    public JobLogDTO(String jobName, String fileName, Date startedAt, Date endedAt, String status, String log) {
        this.jobName = jobName;
        this.fileName = fileName;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.status = status;
        this.log = log;
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
}
