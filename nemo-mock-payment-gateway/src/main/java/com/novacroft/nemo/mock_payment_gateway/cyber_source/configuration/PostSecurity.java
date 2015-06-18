package com.novacroft.nemo.mock_payment_gateway.cyber_source.configuration;

/**
 * CyberSource Secure Acceptance postSecurity configuration bean
 */
public class PostSecurity {
    protected String name;
    protected String key;

    public PostSecurity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
