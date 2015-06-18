package com.novacroft.nemo.tfl.common.transfer.cubic;

public class CardInfoRequestV2DTO {
    protected String prestigeId;
    protected String userId;
    protected String password;
    
    public CardInfoRequestV2DTO() {
        super();
    }
    
    public CardInfoRequestV2DTO(String prestigeId, String userId, String password) {
        super();
        this.prestigeId = prestigeId;
        this.userId = userId;
        this.password = password;
    }

    public String getPrestigeId() {
        return prestigeId;
    }
    public void setPrestigeId(String prestigeId) {
        this.prestigeId = prestigeId;
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
