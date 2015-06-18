package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

@Entity
@Table(name = "FATU_NOTIFICATION_MESSAGE")
public class FailedAutoTopUpNotificationMessage extends AbstractBaseEntity {

    private static final long serialVersionUID = 2685455514498512230L;
    private String code;
    private String content;

    @SequenceGenerator(name = "FATU_NOTIFICATION_MESSAGE_SEQ", sequenceName = "FATU_NOTIFICATION_MESSAGE_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FATU_NOTIFICATION_MESSAGE_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
