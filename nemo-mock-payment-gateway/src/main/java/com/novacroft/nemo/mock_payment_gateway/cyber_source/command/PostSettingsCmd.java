package com.novacroft.nemo.mock_payment_gateway.cyber_source.command;

/**
 * Command class for post service reply settings
 */
public class PostSettingsCmd {
    protected Boolean alive = Boolean.TRUE;

    public PostSettingsCmd() {
    }

    public PostSettingsCmd(Boolean alive) {
        this.alive = alive;
    }

    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }
}
