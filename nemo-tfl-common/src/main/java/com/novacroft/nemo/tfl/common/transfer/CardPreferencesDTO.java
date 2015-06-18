package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.common.transfer.CommonCardPreferencesDTO;

/**
 * Customer preferences transfer class TfL definition
 */
public class CardPreferencesDTO extends CommonCardPreferencesDTO {
    protected Long stationId;
    protected String emailFrequency;
    protected String attachmentType;
    protected Boolean statementTermsAccepted;

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
