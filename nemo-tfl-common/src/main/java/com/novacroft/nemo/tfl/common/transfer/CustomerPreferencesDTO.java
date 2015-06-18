package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.common.transfer.CommonCustomerPreferencesDTO;

/**
 * Customer preferences transfer class TfL definition
 */
public class CustomerPreferencesDTO extends CommonCustomerPreferencesDTO {
    protected Boolean canTflContact;
    protected Boolean canThirdPartyContact;
    protected Long stationId ;
    protected String emailFrequency;
    protected String attachmentType;
    protected Boolean statementTermsAccepted;

    public Boolean getCanTflContact() {
        return canTflContact;
    }

    public void setCanTflContact(Boolean canTflContact) {
        this.canTflContact = canTflContact;
    }

    public Boolean getCanThirdPartyContact() {
        return canThirdPartyContact;
    }

    public void setCanThirdPartyContact(Boolean canThirdPartyContact) {
        this.canThirdPartyContact = canThirdPartyContact;
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
