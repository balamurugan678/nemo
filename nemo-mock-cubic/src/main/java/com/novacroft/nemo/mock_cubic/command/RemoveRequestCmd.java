package com.novacroft.nemo.mock_cubic.command;

public class RemoveRequestCmd {
    protected Long requestSequenceNumber;
    protected String prestigeId;
    protected String action;
    protected Long originalRequestSequenceNumber;
    protected Long removedRequestSequenceNumber;
    protected String userId;
    protected String password;

    public Long getRequestSequenceNumber() {
        return requestSequenceNumber;
    }

    public void setRequestSequenceNumber(Long requestSequenceNumber) {
        this.requestSequenceNumber = requestSequenceNumber;
    }

    public String getPrestigeId() {
        return prestigeId;
    }

    public void setPrestigeId(String prestigeId) {
        this.prestigeId = prestigeId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getOriginalRequestSequenceNumber() {
        return originalRequestSequenceNumber;
    }

    public void setOriginalRequestSequenceNumber(Long originalRequestSequenceNumber) {
        this.originalRequestSequenceNumber = originalRequestSequenceNumber;
    }
    
    public Long getRemovedRequestSequenceNumber() {
        return removedRequestSequenceNumber;
    }

    public void setRemovedRequestSequenceNumber(Long removedRequestSequenceNumber) {
        this.removedRequestSequenceNumber = removedRequestSequenceNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
