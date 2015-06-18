package com.novacroft.nemo.tfl.common.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import com.novacroft.nemo.common.domain.CommonCustomer;

/**
 * TfL customer implementation
 *  TODO Fix the stale cache issue.
 *  When any data is updated through online application which is already cached in the innovator application, 
 *  the changes don't reflect in the innovator application and vice versa. 
 *  
 *  Commented the @Cache annotation so that the application will fetch the data
 *  from database instead of cache to fix issue 1087194 temporarily.
 */

@Audited
@Entity
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer extends CommonCustomer {

    @Transient
    private static final long serialVersionUID = 6200449962689402878L;
    protected int deceased = 0;
    protected int anonymised = 0;
    protected int readOnly = 0;
    protected String status;
    protected String password;
    protected String salt;
	protected String photoCardNumber;
    protected String unformattedEmailAddress;
    protected int passwordChangeRequired;
    protected int deactivated;
    protected String deactivationReason;
    protected Long externalUserId;
    protected Date deletedDateTime;
    protected String deletedReasonCode;
    protected String deletedReferenceNumber;
    protected String deletedNote;
    protected Long tflMasterId;
    
    
    public Customer() {
        super();
    }

    public Customer(Long id, String createdUserId, Date createdDateTime, String modifiedUserId, Date modifiedDateTime,
    				String status, String title, String firstName, String initials, String lastName,
    				int deceased,int anonymised, int readOnly, Long addressId, String userName, String emailAddress, String salt, 
    				String password,String photoCardNumber, String unformattedEmailAddress, int passwordChangeRequired,
                    int deactivated, String deactivationReason, Long externalUserId) {
        this.id = id;
        this.createdUserId = createdUserId;
        this.createdDateTime = createdDateTime;
        this.modifiedUserId = modifiedUserId;
        this.modifiedDateTime = modifiedDateTime;
        this.status=status;
        this.title = title;
        this.firstName = firstName;
        this.initials = initials;
        this.lastName = lastName;
        this.addressId = addressId;
        this.deceased = deceased;
        this.anonymised = anonymised;
        this.readOnly = readOnly;
        this.username = userName;
        this.emailAddress = emailAddress;
        this.salt = salt;
        this.password = password;
        this.photoCardNumber=photoCardNumber;
        this.unformattedEmailAddress=unformattedEmailAddress;
        this.passwordChangeRequired=passwordChangeRequired;
        this.deactivated=deactivated;
        this.deactivationReason=deactivationReason;
        this.externalUserId = externalUserId;
    }

    @SequenceGenerator(name = "CUSTOMER_SEQ", sequenceName = "CUSTOMER_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMER_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
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

    public int getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(int readonly) {
        this.readOnly = readonly;
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
