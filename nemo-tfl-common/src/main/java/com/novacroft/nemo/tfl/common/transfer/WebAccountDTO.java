package com.novacroft.nemo.tfl.common.transfer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novacroft.nemo.common.transfer.CommonWebAccountDTO;

/**
 * TfL Web account data transfer class
 */
public class WebAccountDTO extends CommonWebAccountDTO {
    protected static final Logger logger = LoggerFactory.getLogger(WebAccountDTO.class);
    protected String photoCardNumber;
    protected String unformattedEmailAddress;
    protected int anonymised;
    protected int readOnly;
    protected int passwordChangeRequired;
    protected int deactivated;
    protected String deactivationReason;
    
	public WebAccountDTO() {
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
