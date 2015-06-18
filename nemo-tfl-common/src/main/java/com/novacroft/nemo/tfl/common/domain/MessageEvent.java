package com.novacroft.nemo.tfl.common.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

@Entity
public class MessageEvent extends AbstractBaseEntity {

    private static final long serialVersionUID = 2685455514498512230L;
    private Long messageId;
    private String response;
    private Date eventDate;

    @SequenceGenerator(name = "MESSAGEEVENT_SEQ", sequenceName = "MESSAGEEVENT_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGEEVENT_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
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
