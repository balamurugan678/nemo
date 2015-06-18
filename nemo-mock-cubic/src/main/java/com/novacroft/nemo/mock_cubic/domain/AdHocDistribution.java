package com.novacroft.nemo.mock_cubic.domain;

/**
 * Represents a record from the CUBIC Ad-Hoc Distribution File batch file
 */
public class AdHocDistribution {
    private String prestigeId;
    private String pickUpLocation;
    private String pickUpTime;
    private String requestSequenceNumber;
    private String productCode;
    private String pptStartDate;
    private String pptExpiryDate;
    private String prePayValue;
    private String currency = "0";
    private String statusOfAttemptedAction;
    private String failureReasonCode;

    public AdHocDistribution() {
    }

    public AdHocDistribution(String prestigeId, String pickUpLocation, String pickUpTime, String requestSequenceNumber,
                             String productCode, String pptStartDate, String pptExpiryDate, String prePayValue, String currency,
                             String statusOfAttemptedAction, String failureReasonCode) {
        this.prestigeId = prestigeId;
        this.pickUpLocation = pickUpLocation;
        this.pickUpTime = pickUpTime;
        this.requestSequenceNumber = requestSequenceNumber;
        this.productCode = productCode;
        this.pptStartDate = pptStartDate;
        this.pptExpiryDate = pptExpiryDate;
        this.prePayValue = prePayValue;
        this.currency = currency;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getPptStartDate() {
        return pptStartDate;
    }

    public void setPptStartDate(String pptStartDate) {
        this.pptStartDate = pptStartDate;
    }

    public String getPptExpiryDate() {
        return pptExpiryDate;
    }

    public void setPptExpiryDate(String pptExpiryDate) {
        this.pptExpiryDate = pptExpiryDate;
    }

    public String getPrePayValue() {
        return prePayValue;
    }

    public void setPrePayValue(String prePayValue) {
        this.prePayValue = prePayValue;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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
