package com.novacroft.nemo.common.transfer;

/**
 * ApplicationEvent transfer common definition
 */

public class CommonApplicationEventDTO extends AbstractBaseDTO {

    protected Long eventId;
    protected Long customerId;
    protected Long webaccountId;
    protected String additionalInformation;
    private String eventDescription;
    private String eventName;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getWebaccountId() {
        return webaccountId;
    }

    public void setWebaccountId(Long webaccountId) {
        this.webaccountId = webaccountId;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
