package com.novacroft.nemo.common.transfer;

import java.util.Date;

/**
 * Service call log data transfer class.
 */
public class ServiceCallLogDTO extends AbstractBaseDTO {
    protected String serviceName;
    protected String userId;
    protected Long customerId;
    protected Date requestedAt;
    protected Date respondedAt;
    protected String request;
    protected String response;

    public ServiceCallLogDTO() {
    }

    public ServiceCallLogDTO(String serviceName, String userId, Long customerId, Date requestedAt) {
        this.serviceName = serviceName;
        this.userId = userId;
        this.customerId = customerId;
        this.requestedAt = requestedAt;
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

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
