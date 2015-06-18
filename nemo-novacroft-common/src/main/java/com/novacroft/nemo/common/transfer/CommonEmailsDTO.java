package com.novacroft.nemo.common.transfer;

/**
* Emails transfer common definition.
* Automatically created.
*/

public class CommonEmailsDTO extends AbstractBaseDTO {
    protected Long id;
    protected Long reference;
    protected String referenceType;
    protected Long emailId;
    protected String to;
    protected String cc;
    protected String bcc;
    protected String fromAddress;
    protected String subject;
    protected String body;
    protected Long attempts;
    protected String errorMessage;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getReference() {
        return reference;
    }
    public void setReference(Long reference) {
        this.reference = reference;
    }
    public String getReferenceType() {
        return referenceType;
    }
    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }
    public Long getEmailId() {
        return emailId;
    }
    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }
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
    public String getFromAddress() {
        return fromAddress;
    }
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public Long getAttempts() {
        return attempts;
    }
    public void setAttempts(Long attempts) {
        this.attempts = attempts;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
