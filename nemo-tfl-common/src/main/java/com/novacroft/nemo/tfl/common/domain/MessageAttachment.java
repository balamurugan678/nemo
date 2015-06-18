package com.novacroft.nemo.tfl.common.domain;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

@Entity
public class MessageAttachment extends AbstractBaseEntity {

    private static final long serialVersionUID = 2685455514498512230L;
    private Long messageId;
    private Blob content;

    @SequenceGenerator(name = "MESSAGEATTACHMENT_SEQ", sequenceName = "MESSAGEATTACHMENT_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGEATTACHMENT_SEQ")
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

    public Blob getContent() {
        return content;
    }

    public void setContent(Blob content) {
        this.content = content;
    }

}
