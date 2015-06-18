package com.novacroft.nemo.tfl.common.transfer;

import java.util.List;

import com.novacroft.nemo.tfl.common.domain.MessageAttachment;

public class EmailMessageDTO extends MessageDTO {
    private static final long serialVersionUID = 636077078688346847L;

    private String to;
    private String cc;
    private String bcc;
    private String from;
    private String subject;
    private List<MessageAttachment> attachments;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

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

    public List<MessageAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<MessageAttachment> attachments) {
        this.attachments = attachments;
    }

}
