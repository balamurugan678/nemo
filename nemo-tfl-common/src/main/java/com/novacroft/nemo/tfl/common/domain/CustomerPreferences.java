package com.novacroft.nemo.tfl.common.domain;

import com.novacroft.nemo.common.domain.CommonCustomerPreferences;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * TfL Customer Preferences implementation
 */
@Audited
@Entity
public class CustomerPreferences extends CommonCustomerPreferences {
    @Transient
    private static final long serialVersionUID = 4403596627518106812L;
    protected Boolean canTflContact = Boolean.FALSE;
    protected Boolean canThirdPartyContact = Boolean.FALSE;
    protected Long stationId;
    protected String emailFrequency;
    protected String attachmentType;
    protected Boolean statementTermsAccepted = Boolean.FALSE;

    @SequenceGenerator(name = "CUSTOMERPREFERENCES_SEQ", sequenceName = "CUSTOMERPREFERENCES_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMERPREFERENCES_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    @Column(name = "dpacantflcontact")
    public Boolean getCanTflContact() {
        return canTflContact;
    }

    public void setCanTflContact(Boolean canTflContact) {
        this.canTflContact = canTflContact;
    }

    @Column(name = "dpacanthirdpartycontact")
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
