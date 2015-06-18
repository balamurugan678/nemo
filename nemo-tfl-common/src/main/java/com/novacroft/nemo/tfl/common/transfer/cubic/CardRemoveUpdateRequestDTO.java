package com.novacroft.nemo.tfl.common.transfer.cubic;

public class CardRemoveUpdateRequestDTO {
    protected String prestigeId;
    protected String action;
    protected Long originalRequestSequenceNumber;
    protected String userId;
    protected String password;

    public CardRemoveUpdateRequestDTO() {

    }

    public CardRemoveUpdateRequestDTO(String prestigeId, Long originalRequestSequenceNumber) {
        this.prestigeId = prestigeId;
        this.action = "REMOVE";
        this.originalRequestSequenceNumber = originalRequestSequenceNumber;
    }

    public CardRemoveUpdateRequestDTO(String prestigeId, Long originalRequestSequenceNumber, String userId, String password) {
        this.prestigeId = prestigeId;
        this.action = "REMOVE";
        this.originalRequestSequenceNumber = originalRequestSequenceNumber;
        this.userId = userId;
        this.password = password;
    }

    public CardRemoveUpdateRequestDTO(String prestigeId, String action, Long originalRequestSequenceNumber, String userId, String password) {
        this.prestigeId = prestigeId;
        this.action = action;
        this.originalRequestSequenceNumber = originalRequestSequenceNumber;
        this.userId = userId;
        this.password = password;
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
