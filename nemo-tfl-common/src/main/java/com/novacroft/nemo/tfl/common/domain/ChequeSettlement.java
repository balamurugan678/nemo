package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * Settlement using cheque.  Likely to be for refunds.
 */
@Entity
@DiscriminatorValue("Cheque")
public class ChequeSettlement extends PayeeRequestSettlement {
    protected Long chequeSerialNumber;
    protected Date printedOn;
    protected Date clearedOn;
    protected Date outdatedOn;

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
