package com.novacroft.nemo.mock_payment_gateway.cyber_source.configuration;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * CyberSource Secure Acceptance profile configuration bean
 */
public class PostProfile {
    protected String name;
    protected String id;
    protected String companyName;
    protected Boolean active = Boolean.TRUE;
    protected String customResponseUrl;

    public PostProfile() {
    }

    public PostProfile(String name, String id, String companyName, Boolean active, String customResponseUrl) {
        this.name = name;
        this.id = id;
        this.companyName = companyName;
        this.active = active;
        this.customResponseUrl = customResponseUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCustomResponseUrl() {
        return customResponseUrl;
    }

    public void setCustomResponseUrl(String customResponseUrl) {
        this.customResponseUrl = customResponseUrl;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        PostProfile that = (PostProfile) object;

        return new EqualsBuilder().append(name, that.name).append(id, that.id).append(companyName, that.companyName)
                .append(active, that.active).append(customResponseUrl, that.customResponseUrl).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(35, 37).append(name).append(id).append(companyName).append(active).append(customResponseUrl)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("id", id).append("companyName", companyName)
                .append("active", active).append("customResponseUrl", customResponseUrl).toString();
    }
}
