package com.novacroft.nemo.tfl.batch.domain.cubic;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.common.constant.HashCodeSeed;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

import static com.novacroft.nemo.tfl.batch.util.cubic.CubicConvertUtil.convertDateToString;

/**
 * Represents a record from the CUBIC Ad-Hoc Distributions batch file
 */
public class AdHocDistributionRecord implements ImportRecord, Serializable {
    private String prestigeId;
    private Integer pickUpLocation;
    private Date pickUpTime;
    private Integer requestSequenceNumber;
    private Integer productCode;
    private Date pptStartDate;
    private Date pptExpiryDate;
    private Integer prePayValue;
    private Integer currency = 0;
    private String statusOfAttemptedAction;
    private Integer failureReasonCode;

    public AdHocDistributionRecord() {
    }

    public AdHocDistributionRecord(String prestigeId, Integer pickUpLocation, Date pickUpTime, Integer requestSequenceNumber,
                                   Integer productCode, Date pptStartDate, Date pptExpiryDate, Integer prePayValue,
                                   Integer currency, String statusOfAttemptedAction, Integer failureReasonCode) {
        this.prestigeId = prestigeId;
        this.pickUpLocation = pickUpLocation;
        this.pickUpTime = pickUpTime;
        this.requestSequenceNumber = requestSequenceNumber;
        this.productCode = productCode;
        this.pptStartDate = pptStartDate;
        this.pptExpiryDate = pptExpiryDate;
        this.prePayValue = prePayValue;
        this.currency = currency;
        this.statusOfAttemptedAction = statusOfAttemptedAction;
        this.failureReasonCode = failureReasonCode;
    }

    public String getPrestigeId() {
        return prestigeId;
    }

    public void setPrestigeId(String prestigeId) {
        this.prestigeId = prestigeId;
    }

    public Integer getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(Integer pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public Date getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(Date pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public Integer getRequestSequenceNumber() {
        return requestSequenceNumber;
    }

    public void setRequestSequenceNumber(Integer requestSequenceNumber) {
        this.requestSequenceNumber = requestSequenceNumber;
    }

    public Integer getProductCode() {
        return productCode;
    }

    public void setProductCode(Integer productCode) {
        this.productCode = productCode;
    }

    public Date getPptStartDate() {
        return pptStartDate;
    }

    public void setPptStartDate(Date pptStartDate) {
        this.pptStartDate = pptStartDate;
    }

    public Date getPptExpiryDate() {
        return pptExpiryDate;
    }

    public void setPptExpiryDate(Date pptExpiryDate) {
        this.pptExpiryDate = pptExpiryDate;
    }

    public Integer getPrePayValue() {
        return prePayValue;
    }

    public void setPrePayValue(Integer prePayValue) {
        this.prePayValue = prePayValue;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public String getStatusOfAttemptedAction() {
        return statusOfAttemptedAction;
    }

    public void setStatusOfAttemptedAction(String statusOfAttemptedAction) {
        this.statusOfAttemptedAction = statusOfAttemptedAction;
    }

    public Integer getFailureReasonCode() {
        return failureReasonCode;
    }

    public void setFailureReasonCode(Integer failureReasonCode) {
        this.failureReasonCode = failureReasonCode;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        AdHocDistributionRecord that = (AdHocDistributionRecord) object;

        // Start and Expiry dates are converted to a string because the time components of the date are not relevant
        return new EqualsBuilder().append(prestigeId, that.prestigeId).append(pickUpLocation, that.pickUpLocation)
                .append(pickUpTime, that.pickUpTime).append(requestSequenceNumber, that.requestSequenceNumber)
                .append(productCode, that.productCode)
                .append(convertDateToString(pptStartDate), convertDateToString(that.pptStartDate))
                .append(convertDateToString(pptExpiryDate), convertDateToString(that.pptExpiryDate))
                .append(prePayValue, that.prePayValue).append(currency, that.currency)
                .append(statusOfAttemptedAction, that.statusOfAttemptedAction).append(failureReasonCode, that.failureReasonCode)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeSeed.AD_HOC_DISTRIBUTION_RECORD.initialiser(),
                HashCodeSeed.AD_HOC_DISTRIBUTION_RECORD.multiplier()).append(prestigeId).append(pickUpLocation)
                .append(pickUpTime).append(requestSequenceNumber).append(productCode).append(convertDateToString(pptStartDate))
                .append(convertDateToString(pptExpiryDate)).append(prePayValue).append(currency).append(statusOfAttemptedAction)
                .append(failureReasonCode).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("prestigeId", prestigeId).append("pickUpLocation", pickUpLocation)
                .append("pickUpTime", pickUpTime).append("requestSequenceNumber", requestSequenceNumber)
                .append("productCode", productCode).append("pptStartDate", convertDateToString(pptStartDate))
                .append("pptExpiryDate", convertDateToString(pptExpiryDate)).append("prePayValue", prePayValue)
                .append("currency", currency).append("statusOfAttemptedAction", statusOfAttemptedAction)
                .append("failureReasonCode", failureReasonCode).toString();
    }
}
