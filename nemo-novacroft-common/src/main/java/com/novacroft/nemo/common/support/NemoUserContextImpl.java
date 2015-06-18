package com.novacroft.nemo.common.support;

import org.springframework.stereotype.Component;

import java.io.Serializable;

import static com.novacroft.nemo.common.utils.StringUtil.isNotBlank;

/**
 * Nemo application user context
 */
@Component("nemoUserContext")
public class NemoUserContextImpl implements NemoUserContext, Serializable {
    public static final String DEFAULT_USER_NAME = "unknown";
    private static final long serialVersionUID = 1L;
    protected String userName = DEFAULT_USER_NAME;

    @Override
    public String getUserName() {
        return isNotBlank(userName) ? userName : DEFAULT_USER_NAME;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
