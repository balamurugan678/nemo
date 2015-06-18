package com.novacroft.nemo.tfl.batch.domain.cubic;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

import static com.novacroft.nemo.tfl.batch.util.cubic.CubicConvertUtil.convertDateToString;

/**
 * Represents a record from the CUBIC Current Action List File batch file
 */
public class CurrentActionRecord implements ImportRecord, Serializable {
    private String prestigeId;
    private Integer requestSequenceNumber;
    private Integer productCode;
    private Integer farePaid;
    private Integer currency;
    private Integer paymentMethodCode;
    private Integer prePayValue;
    private Integer pickUpLocation;
    private Date pptStartDate;
    private Date pptExpiryDate;
    private Integer autoLoadState;

    public CurrentActionRecord() {
    }

    public CurrentActionRecord(String prestigeId, Integer requestSequenceNumber, Integer productCode, Integer farePaid,
                               Integer currency, Integer paymentMethodCode, Integer prePayValue, Integer pickUpLocation,
                               Date pptStartDate, Date pptExpiryDate, Integer autoLoadState) {
        this.prestigeId = prestigeId;
        this.requestSequenceNumber = requestSequenceNumber;
        this.productCode = productCode;
        this.farePaid = farePaid;
        this.currency = currency;
        this.paymentMethodCode = paymentMethodCode;
        this.prePayValue = prePayValue;
        this.pickUpLocation = pickUpLocation;
        this.pptStartDate = pptStartDate;
        this.pptExpiryDate = pptExpiryDate;
        this.autoLoadState = autoLoadState;
    }

    public String getPrestigeId() {
        return prestigeId;
    }

    public void setPrestigeId(String prestigeId) {
        this.prestigeId = prestigeId;
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

    public Integer getFarePaid() {
        return farePaid;
    }

    public void setFarePaid(Integer farePaid) {
        this.farePaid = farePaid;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public Integer getPaymentMethodCode() {
        return paymentMethodCode;
    }

    public void setPaymentMethodCode(Integer paymentMethodCode) {
        this.paymentMethodCode = paymentMethodCode;
    }

    public Integer getPrePayValue() {
        return prePayValue;
    }

    public void setPrePayValue(Integer prePayValue) {
        this.prePayValue = prePayValue;
    }

    public Integer getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(Integer pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
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

    public Integer getAutoLoadState() {
        return autoLoadState;
    }

    public void setAutoLoadState(Integer autoLoadState) {
        this.autoLoadState = autoLoadState;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        CurrentActionRecord that = (CurrentActionRecord) object;

        // Start and Expiry dates are converted to a string because the time components of the date are not relevant
        return new EqualsBuilder().append(autoLoadState, that.autoLoadState).append(currency, that.currency)
                .append(farePaid, that.farePaid).append(paymentMethodCode, that.paymentMethodCode)
                .append(pickUpLocation, that.pickUpLocation)
                .append(convertDateToString(pptExpiryDate), convertDateToString(that.pptExpiryDate))
                .append(convertDateToString(pptStartDate), convertDateToString(that.pptStartDate))
                .append(prePayValue, that.prePayValue).append(prestigeId, that.prestigeId).append(productCode, that.productCode)
                .append(requestSequenceNumber, that.requestSequenceNumber).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(31, 33).append(prestigeId).append(requestSequenceNumber).
                append(productCode).append(farePaid).append(currency).append(paymentMethodCode).
                append(prePayValue).append(pickUpLocation).append(convertDateToString(pptStartDate))
                .append(convertDateToString(pptExpiryDate)).append(autoLoadState).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("prestigeId", prestigeId).append("requestSequenceNumber", requestSequenceNumber)
                .append("productCode", productCode).append("farePaid", farePaid).append("currency", currency)
                .append("paymentMethodCode", paymentMethodCode).append("prePayValue", prePayValue)
                .append("pickUpLocation", pickUpLocation).append("pptStartDate", convertDateToString(pptStartDate))
                .append("pptExpiryDate", convertDateToString(pptExpiryDate)).append("autoLoadState", autoLoadState).toString();
    }
}
