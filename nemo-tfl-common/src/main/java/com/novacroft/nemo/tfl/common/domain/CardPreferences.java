package com.novacroft.nemo.tfl.common.domain;

import com.novacroft.nemo.common.domain.CommonCardPreferences;

import javax.persistence.*;

/**
 * Card preferences domain definition
 */
// @Audited
@Entity
public class CardPreferences extends CommonCardPreferences {
    private static final long serialVersionUID = 7749721026984176704L;

    protected Long stationId;
    protected String emailFrequency;
    protected String attachmentType;
    protected Boolean statementTermsAccepted = Boolean.FALSE;

    @SequenceGenerator(name = "CARDPREFERENCES_SEQ", sequenceName = "CARDPREFERENCES_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARDPREFERENCES_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getEmailFrequency() {
        return emailFrequency;
    }

    public void setEmailFrequency(String emailFrequency) {
        this.emailFrequency = emailFrequency;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    public Boolean getStatementTermsAccepted() {
        return statementTermsAccepted;
    }

    public void setStatementTermsAccepted(Boolean statementTermsAccepted) {
        this.statementTermsAccepted = statementTermsAccepted;
    }
}
