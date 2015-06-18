package com.novacroft.nemo.common.command.impl;

/**
 * Login command (MVC "view") class common definition.
 */
public class CommonLoginCmd {
    protected String username;
    protected String password;

    public CommonLoginCmd() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
