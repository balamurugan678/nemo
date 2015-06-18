package com.novacroft.nemo.tfl.common.transfer;

import java.util.Date;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;

public class MessageEventDTO extends AbstractBaseDTO {
    private static final long serialVersionUID = 6124520572501290428L;

    private Long messageId;
    private String response;
    private Date eventDate;

    public MessageEventDTO(Long messageId, String response, Date eventDate) {
        super();
        this.messageId = messageId;
        this.response = response;
        this.eventDate = eventDate;
    }

    public MessageEventDTO() {
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

}
