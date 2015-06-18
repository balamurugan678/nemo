package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;

public class ExternalUserDTO extends AbstractBaseDTO {
    
    private static final long serialVersionUID = -3325472189243108479L;
    protected String username;
    protected String password;
    protected String status;
    protected String salt;
    protected String role;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
