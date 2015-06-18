package com.novacroft.nemo.tfl.common.domain;

import java.io.Serializable;
import java.util.Date;

import org.activiti.engine.history.HistoricTaskInstance;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.novacroft.nemo.tfl.common.constant.CaseNoteType;
import com.novacroft.nemo.tfl.common.constant.HashCodeSeed;

public class CaseHistoryNote implements Serializable {

    private static final long serialVersionUID = 8284277578715589813L;
    protected Date date;
    protected String message;
    protected String agent;
    protected String status;
    protected CaseNoteType type;

    public CaseHistoryNote() {
        date = new Date();
    }

    public CaseHistoryNote(String message, String agent, String status) {
        date = new Date();
        this.message = message;
        this.agent = agent;
        this.status = status;
        this.type = CaseNoteType.AGENT;
    }

    public CaseHistoryNote(Date date, String message, String agent, String status) {
        this.date = date;
        this.message = message;
        this.agent = agent;
        this.status = status;
        this.type = CaseNoteType.AGENT;
    }

    public CaseHistoryNote(HistoricTaskInstance task) {
        this.date = task.getStartTime();
        this.message = task.getName();
        this.agent = task.getAssignee();
        this.status = (String) task.getTaskLocalVariables().get("localStatus");
        this.type = CaseNoteType.HISTORIC;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CaseNoteType getType() {
        return type;
    }

    public void setType(CaseNoteType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        CaseHistoryNote that = (CaseHistoryNote) object;

        return new EqualsBuilder().append(date, that.date).append(message, that.message).append(agent, that.agent).append(status, that.status)
                        .append(type, that.type).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeSeed.CASE_HISTORY_NOTE.initialiser(), HashCodeSeed.CASE_HISTORY_NOTE.multiplier()).append(date)
                        .append(message).append(agent).append(status).append(type).toHashCode();
    }
}
