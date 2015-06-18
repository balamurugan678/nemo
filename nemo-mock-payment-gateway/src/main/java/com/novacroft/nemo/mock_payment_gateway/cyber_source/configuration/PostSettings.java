package com.novacroft.nemo.mock_payment_gateway.cyber_source.configuration;

/**
 * Bean to hold post service reply settings
 */
public class PostSettings {
    private static PostSettings instance = null;
    protected Boolean alive = Boolean.TRUE;

    protected PostSettings() {
    }

    public static PostSettings getInstance() {
        if (instance == null) {
            instance = new PostSettings();
        }
        return instance;
    }

    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }
}
