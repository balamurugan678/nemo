package com.novacroft.nemo.tfl.common.domain.cubic;

/**
 * Model for originator info of auto load request
 */
public class OriginatorInfo {
    protected String userId;
    protected String password;

    public OriginatorInfo() {
    }

    public OriginatorInfo(String userId, String password) {
        super();
        this.userId = userId;
        this.password = password;
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
