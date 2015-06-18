package com.novacroft.nemo.mock_single_sign_on.command;

import java.util.List;

public class LogoutCmd {
    private String username;
    private List<String> apps;
    private String returnURL;
    private String sessionId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getApps() {
        return apps;
    }

    public void setApps(List<String> apps) {
        this.apps = apps;
    }

    public String getReturnURL() {
        return returnURL;
    }

    public void setReturnURL(String returnUrl) {
        this.returnURL = returnUrl;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
