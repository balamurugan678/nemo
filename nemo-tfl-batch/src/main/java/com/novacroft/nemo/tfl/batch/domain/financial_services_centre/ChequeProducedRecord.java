package com.novacroft.nemo.tfl.batch.domain.financial_services_centre;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.ImportRecordWithReferenceNumber;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

import static com.novacroft.nemo.tfl.batch.util.cubic.CubicConvertUtil.convertDateToString;

/**
 * Domain class for Financial Services Centre (FSC) cheque produced file
 */
public class ChequeProducedRecord implements ImportRecord, ImportRecordWithReferenceNumber, Serializable {
    protected Long referenceNumber;
    protected Float amount;
    protected String customerName;
    protected Long chequeSerialNumber;
    protected Date printedOn;

    public ChequeProducedRecord() {
    }

    public ChequeProducedRecord(Long referenceNumber, Float amount, String customerName, Long chequeSerialNumber,
                                Date printedOn) {
        this.referenceNumber = referenceNumber;
        this.amount = amount;
        this.customerName = customerName;
        this.chequeSerialNumber = chequeSerialNumber;
        this.printedOn = printedOn;
    }

    public Long getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(Long referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("referenceNumber", referenceNumber)
                .append("amount", amount)
                .append("customerName", customerName).append("chequeSerialNumber", chequeSerialNumber)
                .append("printedOn", convertDateToString(printedOn)).toString();
    }
}
