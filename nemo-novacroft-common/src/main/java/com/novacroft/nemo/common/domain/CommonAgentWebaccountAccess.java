package com.novacroft.nemo.common.domain;

import org.hibernate.envers.Audited;

import javax.persistence.MappedSuperclass;

/**
 * Common domain object to hold the Agentwebaccountaccess information Automatically created.
 */

@Audited
@MappedSuperclass
public class CommonAgentWebaccountAccess extends AbstractBaseEntity {
    private static final long serialVersionUID = 1L;

    protected String agentId;
    protected Long webaccountId;
    protected String token;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public Long getWebaccountId() {
        return webaccountId;
    }

    public void setWebaccountId(Long webaccountId) {
        this.webaccountId = webaccountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
