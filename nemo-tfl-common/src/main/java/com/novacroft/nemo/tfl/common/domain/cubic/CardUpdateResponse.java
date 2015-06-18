package com.novacroft.nemo.tfl.common.domain.cubic;

public class CardUpdateResponse {
    protected String prestigeId;
    protected Integer requestSequenceNumber;
    protected Long pickupLocation;
    protected Integer availableSlots;
    
    public CardUpdateResponse(){
        
    }
    
    public CardUpdateResponse(String prestigeId, Integer requestSequenceNumber, Long pickupLocation, Integer availableSlots) {
        super();
        this.prestigeId = prestigeId;
        this.requestSequenceNumber = requestSequenceNumber;
        this.pickupLocation = pickupLocation;
        this.availableSlots = availableSlots;
    }
    public CardUpdateResponse(String prestigeId) {
        super();
        this.prestigeId = prestigeId;
    }

    public CardUpdateResponse(String prestigeId, Integer requestSequenceNumber) {
        super();
        this.prestigeId = prestigeId;
        this.requestSequenceNumber = requestSequenceNumber;
    }

    public String getPrestigeId() {
        return prestigeId;
    }
    public void setPrestigeId(String prestigeId) {
        this.prestigeId = prestigeId;
    }
    public Integer getRequestSequenceNumber() {
        return requestSequenceNumber;
    }
    public void setRequestSequenceNumber(Integer requestSequenceNumber) {
        this.requestSequenceNumber = requestSequenceNumber;
    }
    public Long getPickupLocation() {
        return pickupLocation;
    }
    public void setPickupLocation(Long pickupLocation) {
        this.pickupLocation = pickupLocation;
    }
    public Integer getAvailableSlots() {
        return availableSlots;
    }
    public void setAvailableSlots(Integer availableSlots) {
        this.availableSlots = availableSlots;
    }
    
    
}
