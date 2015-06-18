package com.novacroft.nemo.tfl.common.command.impl;

import java.util.Date;

import com.novacroft.nemo.common.command.impl.CommonCardPreferencesCmd;
import com.novacroft.nemo.common.domain.BaseEntity;
import com.novacroft.nemo.tfl.common.command.EmailPreferencesCmd;
import com.novacroft.nemo.tfl.common.command.SelectCardCmd;
import com.novacroft.nemo.tfl.common.command.SelectStationCmd;

/**
 * Card Preferences command (MVC "view") class TfL definition.
 */
public class CardPreferencesCmdImpl extends CommonCardPreferencesCmd
        implements SelectCardCmd, EmailPreferencesCmd, SelectStationCmd,BaseEntity {
    protected Long cardId;
    protected Long stationId;
    protected String emailFrequency;
    protected String attachmentType;
    protected Boolean statementTermsAccepted = Boolean.FALSE;
    protected String emailAddress;
    protected Long autoTopUpStationId;
    protected Boolean autoTopUpEnabled;
    protected String cardNumber;
    
    
    @Override
    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    @Override
    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    @Override
    public String getEmailFrequency() {
        return emailFrequency;
    }

    public void setEmailFrequency(String emailFrequency) {
        this.emailFrequency = emailFrequency;
    }

    @Override
    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    @Override
    public Boolean getStatementTermsAccepted() {
        return statementTermsAccepted;
    }

    public void setStatementTermsAccepted(Boolean statementTermsAccepted) {
        this.statementTermsAccepted = statementTermsAccepted;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    
    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void setId(Long id) {
    }

    @Override
    public String getCreatedUserId() {
        return null;
    }

    @Override
    public void setCreatedUserId(String createdUserId) {
     }

    @Override
    public Date getCreatedDateTime() {
        return null;
    }

    @Override
    public void setCreatedDateTime(Date createdDateTime) {
    }

    @Override
    public String getModifiedUserId() {
        return null;
    }

    @Override
    public void setModifiedUserId(String modifiedUserId) {
     }

    @Override
    public Date getModifiedDateTime() {
       return null;
    }

    @Override
    public void setModifiedDateTime(Date modifiedDateTime) {
    }

    public Long getAutoTopUpStationId() {
        return autoTopUpStationId;
    }

    public void setAutoTopUpStationId(Long autoTopUpStationId) {
        this.autoTopUpStationId = autoTopUpStationId;
    }

    public Boolean getAutoTopUpEnabled() {
        return autoTopUpEnabled;
    }

    public void setAutoTopUpEnabled(Boolean autoTopUpEnabled) {
        this.autoTopUpEnabled = autoTopUpEnabled;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    
    
}
