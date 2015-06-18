package com.novacroft.nemo.tfl.batch.domain.cubic;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a record from the CUBIC Auto Load Changes batch file
 */
public class AutoLoadChangeRecord implements ImportRecord, Serializable {
    private String prestigeId;
    private Integer pickUpLocation;
    private Date pickUpTime;
    private Integer requestSequenceNumber;
    private Integer previousAutoLoadConfiguration;
    private Integer newAutoLoadConfiguration;
    private String statusOfAttemptedAction;
    private Integer failureReasonCode;

    public AutoLoadChangeRecord() {
    }

    public AutoLoadChangeRecord(String prestigeId, Integer pickUpLocation, Date pickUpTime, Integer requestSequenceNumber,
                                Integer previousAutoLoadConfiguration, Integer newAutoLoadConfiguration,
                                String statusOfAttemptedAction, Integer failureReasonCode) {
        this.prestigeId = prestigeId;
        this.pickUpLocation = pickUpLocation;
        this.pickUpTime = pickUpTime;
        this.requestSequenceNumber = requestSequenceNumber;
        this.previousAutoLoadConfiguration = previousAutoLoadConfiguration;
        this.newAutoLoadConfiguration = newAutoLoadConfiguration;
        this.statusOfAttemptedAction = statusOfAttemptedAction;
        this.failureReasonCode = failureReasonCode;
    }

    public String getPrestigeId() {
        return prestigeId;
    }

    public void setPrestigeId(String prestigeId) {
        this.prestigeId = prestigeId;
    }

    public Integer getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(Integer pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public Date getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(Date pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public Integer getRequestSequenceNumber() {
        return requestSequenceNumber;
    }

    public void setRequestSequenceNumber(Integer requestSequenceNumber) {
        this.requestSequenceNumber = requestSequenceNumber;
    }

    public Integer getPreviousAutoLoadConfiguration() {
        return previousAutoLoadConfiguration;
    }

    public void setPreviousAutoLoadConfiguration(Integer previousAutoLoadConfiguration) {
        this.previousAutoLoadConfiguration = previousAutoLoadConfiguration;
    }

    public Integer getNewAutoLoadConfiguration() {
        return newAutoLoadConfiguration;
    }

    public void setNewAutoLoadConfiguration(Integer newAutoLoadConfiguration) {
        this.newAutoLoadConfiguration = newAutoLoadConfiguration;
    }

    public String getStatusOfAttemptedAction() {
        return statusOfAttemptedAction;
    }

    public void setStatusOfAttemptedAction(String statusOfAttemptedAction) {
        this.statusOfAttemptedAction = statusOfAttemptedAction;
    }

    public Integer getFailureReasonCode() {
        return failureReasonCode;
    }

    public void setFailureReasonCode(Integer failureReasonCode) {
        this.failureReasonCode = failureReasonCode;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        AutoLoadChangeRecord that = (AutoLoadChangeRecord) object;

        return new EqualsBuilder().append(prestigeId, that.prestigeId).append(pickUpLocation, that.pickUpLocation)
                .append(pickUpTime, that.pickUpTime).append(requestSequenceNumber, that.requestSequenceNumber)
                .append(previousAutoLoadConfiguration, that.previousAutoLoadConfiguration)
                .append(newAutoLoadConfiguration, that.newAutoLoadConfiguration)
                .append(statusOfAttemptedAction, that.statusOfAttemptedAction).append(failureReasonCode, that.failureReasonCode)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(55, 77).append(prestigeId).append(pickUpLocation).append(pickUpTime)
                .append(requestSequenceNumber).append(previousAutoLoadConfiguration).append(newAutoLoadConfiguration)
                .append(statusOfAttemptedAction).append(failureReasonCode).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("prestigeId", prestigeId).append("pickUpLocation", pickUpLocation)
                .append("pickUpTime", pickUpTime).append("requestSequenceNumber", requestSequenceNumber)
                .append("previousAutoLoadConfiguration", previousAutoLoadConfiguration)
                .append("newAutoLoadConfiguration", newAutoLoadConfiguration)
                .append("statusOfAttemptedAction", statusOfAttemptedAction).append("failureReasonCode", failureReasonCode)
                .toString();
    }
}
