package com.novacroft.nemo.tfl.common.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("Email")
public class EmailMessage extends Message {
    private static final long serialVersionUID = -2759262351381315393L;

    private String to;
    private String cc;
    private String bcc;
    private String from;
    private String subject;
    private List<MessageAttachment> attachments;

    @Column(name = "MESSAGETO")
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Column(name = "MESSAGECC")
    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    @Column(name = "MESSAGEBCC")
    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    @Column(name = "MESSAGEFROM")
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @OneToMany(cascade = CascadeType.ALL, targetEntity = MessageAttachment.class, orphanRemoval = true)
    @JoinColumn(name = "messageid", nullable = true)
    public List<MessageAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<MessageAttachment> attachments) {
        this.attachments = attachments;
    }
}
