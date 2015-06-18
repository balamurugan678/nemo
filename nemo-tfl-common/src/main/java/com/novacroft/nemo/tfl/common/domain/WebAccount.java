package com.novacroft.nemo.tfl.common.domain;

import com.novacroft.nemo.common.domain.CommonWebAccount;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Transient;

/**
 * TfL implementation web account attributes.
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WebAccount extends CommonWebAccount {
    @Transient
    private static final long serialVersionUID = 1920836006687279053L;
    protected String photoCardNumber;
    protected String unformattedEmailAddress;
    protected int anonymised = 0;
    protected int readOnly = 0;
    protected int passwordChangeRequired = 0;
    protected int deactivated = 0;
    protected String deactivationReason;

    public WebAccount() {
        super();
    }

    public String getPhotoCardNumber() {
        return photoCardNumber;
    }

    public void setPhotoCardNumber(String photoCardNumber) {
        this.photoCardNumber = photoCardNumber;
    }

    public String getUnformattedEmailAddress() {
        return unformattedEmailAddress;
    }

    public void setUnformattedEmailAddress(String unformattedEmailAddress) {
        this.unformattedEmailAddress = unformattedEmailAddress;
    }

    public int getAnonymised() {
        return anonymised;
    }

    public void setAnonymised(int anonymised) {
        this.anonymised = anonymised;
    }

    public int getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(int readOnly) {
        this.readOnly = readOnly;
    }

    public int getPasswordChangeRequired() {
        return passwordChangeRequired;
    }

    public void setPasswordChangeRequired(int passwordChangeRequired) {
        this.passwordChangeRequired = passwordChangeRequired;
    }

    public int getDeactivated() {
        return deactivated;
    }

    public void setDeactivated(int deactivated) {
        this.deactivated = deactivated;
    }

    public String getDeactivationReason() {
        return deactivationReason;
    }

    public void setDeactivationReason(String deactivationReason) {
        this.deactivationReason = deactivationReason;
    }
}
