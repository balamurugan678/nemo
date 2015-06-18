package com.novacroft.nemo.common.domain;

/**
 * Common web account attributes that will be inherited by all implementations.
 */
public class CommonWebAccount extends AbstractBaseEntity {
    private static final long serialVersionUID = 1L;
    protected String username;
    protected String password;
    protected String salt;
    protected String emailAddress;
    protected Long customerId;

    public CommonWebAccount() {
        setCreated();
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
