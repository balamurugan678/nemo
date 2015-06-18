package com.novacroft.nemo.tfl.batch.domain.financial_services_centre;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

import static com.novacroft.nemo.tfl.batch.util.cubic.CubicConvertUtil.convertDateToString;

/**
 * Domain class for Financial Services Centre (FSC) bacs payment settled item
 */
public class BacsPaymentSettledRecord implements ImportRecord, Serializable {

    private static final String PAYMENT_DATE = "paymentDate";

    private static final String FINANCIAL_SERVICES_REFERENCE = "financialServicesReference";

    private static final String AMOUNT = "amount";

    private static final String CUSTOMER_NAME = "customerName";

    private static final String PAYMENT_REFERENCE_NUMBER = "paymentReferenceNumber";

    private static final long serialVersionUID = -4892602983057069152L;

    protected Long paymentReferenceNumber;
    protected String customerName;
    protected Float amount;
    protected Long financialServicesReference;
    protected Date paymentDate;

    public BacsPaymentSettledRecord() {
    }

    public BacsPaymentSettledRecord(Long paymentReferenceNumber, Float amount, String customerName,
                                    Long financialServicesReference, Date paymentDate) {
        this.paymentReferenceNumber = paymentReferenceNumber;
        this.amount = amount;
        this.customerName = customerName;
        this.financialServicesReference = financialServicesReference;
        this.paymentDate = paymentDate;
    }

    public Long getPaymentReferenceNumber() {
        return paymentReferenceNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Long getFinancialServicesReference() {
        return financialServicesReference;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(PAYMENT_REFERENCE_NUMBER, paymentReferenceNumber)
                .append(CUSTOMER_NAME, customerName).append(AMOUNT, amount)
                .append(FINANCIAL_SERVICES_REFERENCE, financialServicesReference)
                .append(PAYMENT_DATE, convertDateToString(paymentDate)).toString();
    }

}
