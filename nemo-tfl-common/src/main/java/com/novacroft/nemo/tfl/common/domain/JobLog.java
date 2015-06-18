package com.novacroft.nemo.tfl.common.domain;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

import javax.persistence.*;

import java.util.Date;

/**
 * Job log domain definition
 */
@Audited
@Table(name = "JOBLOG")
@Entity
public class JobLog extends AbstractBaseEntity {
    private static final long serialVersionUID = -1L;

    protected String jobName;
    protected String fileName;
    protected Date startedAt;
    protected Date endedAt;
    protected String status;
    protected String log;

    @SequenceGenerator(name = "JOBLOG_SEQ", sequenceName = "JOBLOG_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JOBLOG_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
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

    @Type(type = "org.hibernate.type.MaterializedClobType")
    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
