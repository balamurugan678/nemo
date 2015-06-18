package com.novacroft.nemo.mock_cubic.domain;

/**
 * Represents a record from the CUBIC Ad-Hoc Distribution File batch file
 */
public class AutoLoadChange {
    private String prestigeId;
    private String pickUpLocation;
    private String pickUpTime;
    private String requestSequenceNumber;
    private String previousAutoLoadConfiguration;
    private String newAutoLoadConfiguration;
    private String statusOfAttemptedAction;
    private String failureReasonCode;

    public AutoLoadChange() {
    }

    public AutoLoadChange(String prestigeId, String pickUpLocation, String pickUpTime, String requestSequenceNumber,
                          String previousAutoLoadConfiguration, String newAutoLoadConfiguration, String statusOfAttemptedAction,
                          String failureReasonCode) {
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

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getRequestSequenceNumber() {
        return requestSequenceNumber;
    }

    public void setRequestSequenceNumber(String requestSequenceNumber) {
        this.requestSequenceNumber = requestSequenceNumber;
    }

    public String getPreviousAutoLoadConfiguration() {
        return previousAutoLoadConfiguration;
    }

    public void setPreviousAutoLoadConfiguration(String previousAutoLoadConfiguration) {
        this.previousAutoLoadConfiguration = previousAutoLoadConfiguration;
    }

    public String getNewAutoLoadConfiguration() {
        return newAutoLoadConfiguration;
    }

    public void setNewAutoLoadConfiguration(String newAutoLoadConfiguration) {
        this.newAutoLoadConfiguration = newAutoLoadConfiguration;
    }

    public String getStatusOfAttemptedAction() {
        return statusOfAttemptedAction;
    }

    public void setStatusOfAttemptedAction(String statusOfAttemptedAction) {
        this.statusOfAttemptedAction = statusOfAttemptedAction;
    }

    public String getFailureReasonCode() {
        return failureReasonCode;
    }

    public void setFailureReasonCode(String failureReasonCode) {
        this.failureReasonCode = failureReasonCode;
    }
}
