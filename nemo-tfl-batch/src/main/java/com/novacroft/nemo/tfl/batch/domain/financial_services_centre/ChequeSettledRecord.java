package com.novacroft.nemo.tfl.batch.domain.financial_services_centre;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

import static com.novacroft.nemo.tfl.batch.util.cubic.CubicConvertUtil.convertDateToString;

/**
 * Domain class for Financial Services Centre (FSC) cheque settled file
 */
public class ChequeSettledRecord implements ImportRecord, Serializable {
    protected Long chequeSerialNumber;
    protected Long paymentReferenceNumber;
    protected String customerName;
    protected Date clearedOn;
    protected String currency;
    protected Float amount;

    public ChequeSettledRecord() {
    }

    public ChequeSettledRecord(Long chequeSerialNumber, Long paymentReferenceNumber, String customerName, Date clearedOn,
                               String currency, Float amount) {
        this.chequeSerialNumber = chequeSerialNumber;
        this.paymentReferenceNumber = paymentReferenceNumber;
        this.customerName = customerName;
        this.clearedOn = clearedOn;
        this.currency = currency;
        this.amount = amount;
    }

    public Long getChequeSerialNumber() {
        return chequeSerialNumber;
    }

    public void setChequeSerialNumber(Long chequeSerialNumber) {
        this.chequeSerialNumber = chequeSerialNumber;
    }

    public Long getPaymentReferenceNumber() {
        return paymentReferenceNumber;
    }

    public void setPaymentReferenceNumber(Long paymentReferenceNumber) {
        this.paymentReferenceNumber = paymentReferenceNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getClearedOn() {
        return clearedOn;
    }

    public void setClearedOn(Date clearedOn) {
        this.clearedOn = clearedOn;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("chequeSerialNumber", chequeSerialNumber)
                .append("paymentReferenceNumber", paymentReferenceNumber).append("customerName", customerName)
                .append("clearedOn", convertDateToString(clearedOn)).append("currency", currency).append("amount", amount)
                .toString();
    }
}
