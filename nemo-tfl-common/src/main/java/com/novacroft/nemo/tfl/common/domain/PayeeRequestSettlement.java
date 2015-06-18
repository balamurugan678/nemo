package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.novacroft.nemo.common.domain.Address;

@Entity
public abstract class PayeeRequestSettlement extends Settlement {
	
    private static final long serialVersionUID = 4663592355942917257L;
    protected String payeeName;
    protected Long fileExportLogId;
    protected Address address;
    protected Long paymentReference;
    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public Long getFileExportLogId() {
        return fileExportLogId;
    }

    public void setFileExportLogId(Long fileExportLogId) {
        this.fileExportLogId = fileExportLogId;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "addressid")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    public Long getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(Long paymentReference) {
        this.paymentReference = paymentReference;
    }

}
