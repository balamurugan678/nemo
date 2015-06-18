package com.novacroft.nemo.common.domain;

import org.hibernate.envers.Audited;

import javax.mail.internet.MimeMessage;
import javax.persistence.MappedSuperclass;

/**
 * Common domain object to hold the Emails information
 * Automatically created.
 */

@Audited
@MappedSuperclass
public class CommonEmails extends AbstractBaseEntity {
    private static final long serialVersionUID = 6538976734048675792L;
    protected Long id;
    protected Long reference;
    protected String referenceType;
    protected Long emailId;
    protected MimeMessage message;
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

    public MimeMessage getMessage() {
        return message;
    }

    public void setMessage(MimeMessage message) {
        this.message = message;
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