package com.novacroft.nemo.mock_cubic.command;

public class AddCardPrePayTicketCmd {
    protected String prestigeId;
    protected Long requestSequenceNumber;
    protected Long pickupLocation;
    protected Long availableSlots;
    protected Boolean error;
    protected Integer ErrorCode;
    protected String ErrorDescription;
    
    public String getPrestigeId() {
        return prestigeId;
    }
    public void setPrestigeId(String prestigeId) {
        this.prestigeId = prestigeId;
    }
    public Long getRequestSequenceNumber() {
        return requestSequenceNumber;
    }
    public void setRequestSequenceNumber(Long requestSequenceNumber) {
        this.requestSequenceNumber = requestSequenceNumber;
    }
    public Long getPickupLocation() {
        return pickupLocation;
    }
    public void setPickupLocation(Long pickupLocation) {
        this.pickupLocation = pickupLocation;
    }
    public Long getAvailableSlots() {
        return availableSlots;
    }
    public void setAvailableSlots(Long availableSlots) {
        this.availableSlots = availableSlots;
    }
    public Boolean getError() {
        return error;
    }
    public void setError(Boolean error) {
        this.error = error;
    }
    public Integer getErrorCode() {
        return ErrorCode;
    }
    public void setErrorCode(Integer errorCode) {
        ErrorCode = errorCode;
    }
    public String getErrorDescription() {
        return ErrorDescription;
    }
    public void setErrorDescription(String errorDescription) {
        ErrorDescription = errorDescription;
    }
    
}
