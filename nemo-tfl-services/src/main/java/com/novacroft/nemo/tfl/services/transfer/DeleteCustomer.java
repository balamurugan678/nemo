package com.novacroft.nemo.tfl.services.transfer;

import java.util.Date;

import com.novacroft.nemo.common.utils.DateUtil;

public class DeleteCustomer extends AbstractBase {

    private Long id;
    private String deletedDateTime;
    private String deletedReasonCode;
    private String deletedReferenceNumber;
    private String deletedNote;

    public DeleteCustomer() {
        
    }
    
    public DeleteCustomer(Long id, Date deletedDateTime, String deletedReasonCode, String deletedReferenceNumber, String deletedNote) {
        this.id = id;
        this.deletedDateTime = DateUtil.formatDate(deletedDateTime);
        this.deletedReasonCode = deletedReasonCode;
        this.deletedReferenceNumber = deletedReferenceNumber;
        this.deletedNote = deletedNote;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeletedDateTime() {
        return deletedDateTime;
    }

    public void setDeletedDateTime(String deletedDateTime) {
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

}
