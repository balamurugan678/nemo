package com.novacroft.nemo.tfl.common.domain.cubic;

public class CardRemoveUpdateResponse {
    protected String prestigeId;
    protected Integer requestSequenceNumber;
    protected Integer removedRequestSequenceNumber;

    public CardRemoveUpdateResponse() {
        
    }
    
    public CardRemoveUpdateResponse(String prestigeId, Integer requestSequenceNumber, Integer removedRequestSequenceNumber) {
        this.prestigeId = prestigeId;
        this.requestSequenceNumber = requestSequenceNumber;
        this.removedRequestSequenceNumber = removedRequestSequenceNumber;
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

    public Integer getRemovedRequestSequenceNumber() {
        return removedRequestSequenceNumber;
    }

    public void setRemovedRequestSequenceNumber(Integer removedRequestSequenceNumber) {
        this.removedRequestSequenceNumber = removedRequestSequenceNumber;
    }
}
