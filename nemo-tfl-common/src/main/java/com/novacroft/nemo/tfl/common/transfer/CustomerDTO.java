package com.novacroft.nemo.tfl.common.transfer;

import java.util.Date;

import com.novacroft.nemo.common.transfer.CommonCustomerDTO;

/**
 * TfL customer implementation
 */

public class CustomerDTO extends CommonCustomerDTO {
    private static final long serialVersionUID = -2412193547422713532L;
    
    protected int deceased;
    protected int anonymised;
    protected int readonly;
    protected String photoCardNumber;
    protected String unformattedEmailAddress;
    protected int readOnly;
    protected int passwordChangeRequired;
    protected int deactivated;
    protected String deactivationReason;
    protected Long externalUserId;
    protected Date deletedDateTime;
    protected String deletedReasonCode;
    protected String deletedReferenceNumber;
    protected String deletedNote;
    protected Long tflMasterId;
    
    public CustomerDTO(){
        super();
    }
    
    public CustomerDTO(String title, String firstName, String initials, String lastName) {
        super();
        this.title = title;
        this.firstName = firstName;
        this.initials = initials;
        this.lastName = lastName;
    }

    public int getDeceased() {
        return deceased;
    }

    public void setDeceased(int deceased) {
        this.deceased = deceased;
    }

    public int getAnonymised() {
        return anonymised;
    }

    public void setAnonymised(int anonymised) {
        this.anonymised = anonymised;
    }

    public int getReadonly() {
        return readonly;
    }

    public void setReadonly(int readonly) {
        this.readonly = readonly;
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

    public Long getExternalUserId() {
        return externalUserId;
    }

    public void setExternalUserId(Long externalUserId) {
        this.externalUserId = externalUserId;
    }

    public Date getDeletedDateTime() {
        return deletedDateTime;
    }

    public void setDeletedDateTime(Date deletedDateTime) {
        this.deletedDateTime = deletedDateTime;
    }

    public String getDeletedReasonCode() {
        return deletedReasonCode;
    }

    public void setDeletedReasonCode(String deletedReasonCode) {
        this.deletedReasonCode = deletedReasonCode;
    }

    public String getDeletedReferenceNumber() {
        return deletedReferenceNumber;
    }

    public void setDeletedReferenceNumber(String deletedReferenceNumber) {
        this.deletedReferenceNumber = deletedReferenceNumber;
    }

    public String getDeletedNote() {
        return deletedNote;
    }

    public void setDeletedNote(String deletedNote) {
        this.deletedNote = deletedNote;
    }

    public Long getTflMasterId() {
        return tflMasterId;
    }

    public void setTflMasterId(Long tflMasterId) {
        this.tflMasterId = tflMasterId;
    }
    
}
