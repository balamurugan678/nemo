package com.novacroft.nemo.common.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Log/audit service calls
 */
@Entity
public class ServiceCallLog extends AbstractBaseEntity {
    protected String serviceName;
    protected String userId;
    protected Long customerId;
    protected Date requestedAt;
    protected Date respondedAt;
    protected String request;
    protected String response;

    @SequenceGenerator(name = "SERVICECALLLOG_SEQ", sequenceName = "SERVICECALLLOG_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SERVICECALLLOG_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Date getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Date requestedAt) {
        this.requestedAt = requestedAt;
    }

    public Date getRespondedAt() {
        return respondedAt;
    }

    public void setRespondedAt(Date respondedAt) {
        this.respondedAt = respondedAt;
    }

    @Type(type = "org.hibernate.type.MaterializedClobType")
    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    @Type(type = "org.hibernate.type.MaterializedClobType")
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
