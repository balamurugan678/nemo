package com.novacroft.nemo.tfl.common.transfer.financial_services_centre;

import java.util.Date;

import com.novacroft.nemo.common.transfer.AddressDTO;

/**
 * Settlement using cheque.  Likely to be for refunds.
 */
public class ChequeSettlementDTO extends PayeeSettlementDTO {
    
    private static final long serialVersionUID = -3726532343828564897L;
    protected Long chequeSerialNumber;
    protected Date printedOn;
    protected Date clearedOn;
    protected Date outdatedOn;
    
    public ChequeSettlementDTO() {
    }
    
    public ChequeSettlementDTO(AddressDTO addressDTO, Integer amount, Date settlementDate, Long orderId, String payeeName, String status) {
        this.addressDTO = addressDTO;
        this.amount = amount;
        this.settlementDate = settlementDate;
        this.orderId = orderId;
        this.payeeName = payeeName;
        this.status = status;
    }

    public Long getChequeSerialNumber() {
        return chequeSerialNumber;
    }

    public void setChequeSerialNumber(Long chequeSerialNumber) {
        this.chequeSerialNumber = chequeSerialNumber;
    }

    public Date getPrintedOn() {
        return printedOn;
    }

    public void setPrintedOn(Date printedOn) {
        this.printedOn = printedOn;
    }

    public Long getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(Long paymentReference) {
        this.paymentReference = paymentReference;
    }

    public Date getClearedOn() {
        return clearedOn;
    }

    public void setClearedOn(Date clearedOn) {
        this.clearedOn = clearedOn;
    }

    public Date getOutdatedOn() {
        return outdatedOn;
    }

    public void setOutdatedOn(Date outdatedOn) {
        this.outdatedOn = outdatedOn;
    }
}
