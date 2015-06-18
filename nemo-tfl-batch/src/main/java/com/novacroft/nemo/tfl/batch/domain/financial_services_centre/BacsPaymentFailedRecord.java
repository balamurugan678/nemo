package com.novacroft.nemo.tfl.batch.domain.financial_services_centre;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

import static com.novacroft.nemo.tfl.batch.util.cubic.CubicConvertUtil.convertDateToString;

public class BacsPaymentFailedRecord implements ImportRecord {

    private static final String FAILED_PAYMENT_DATE = "failedPaymentDate";

    private static final String FINANCIAL_SERVICES_REFERENCE = "financialServicesReference";

    private static final String AMOUNT = "amount";

    private static final String BACS_REJECT_CODE = "bacsRejectCode";

    protected String bacsRejectCode;
    protected Float amount;
    protected Long financialServicesReference;
    protected Date failedPaymentDate;

    public BacsPaymentFailedRecord() {

    }

    public BacsPaymentFailedRecord(Float amount, String bacsRejectCode, Long financialServicesReference,
                                   Date failedPaymentDate) {
        this.amount = amount;
        this.bacsRejectCode = bacsRejectCode;
        this.financialServicesReference = financialServicesReference;
        this.failedPaymentDate = failedPaymentDate;
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

    public Date getFailedPaymentDate() {
        return failedPaymentDate;
    }

    public String getBacsRejectCode() {
        return bacsRejectCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(BACS_REJECT_CODE, bacsRejectCode)
                .append(AMOUNT, amount).append(FINANCIAL_SERVICES_REFERENCE, financialServicesReference)
                .append(FAILED_PAYMENT_DATE, convertDateToString(failedPaymentDate)).toString();
    }

}
