package com.novacroft.nemo.mock_cubic.domain;

/**
 * Represents a record from the CUBIC Current Action List File batch file.
 */
public class CurrentAction {
    private String prestigeId;
    private String requestSequenceNumber;
    private String productCode;
    private String farePaid;
    private String currency = "0";
    private String paymentMethodCode;
    private String prePayValue;
    private String pickUpLocation;
    private String pptStartDate;
    private String pptExpiryDate;
    private String autoLoadState;

    public CurrentAction() {
    }

    public CurrentAction(final String prestigeId, final String requestSequenceNumber, final String productCode, final String farePaid, final String currency,
                         final String paymentMethodCode, final String prePayValue, final String pickUpLocation, final String pptStartDate,
                         final String pptExpiryDate, final String autoLoadState) {
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

    public final String getPrestigeId() {
        return prestigeId;
    }

    public final void setPrestigeId(final String prestigeId) {
        this.prestigeId = prestigeId;
    }

    public final String getRequestSequenceNumber() {
        return requestSequenceNumber;
    }

    public final void setRequestSequenceNumber(final String requestSequenceNumber) {
        this.requestSequenceNumber = requestSequenceNumber;
    }

    public final String getProductCode() {
        return productCode;
    }

    public final void setProductCode(final String productCode) {
        this.productCode = productCode;
    }

    public final String getFarePaid() {
        return farePaid;
    }

    public final void setFarePaid(final String farePaid) {
        this.farePaid = farePaid;
    }

    public final String getCurrency() {
        return currency;
    }

    public final void setCurrency(final String currency) {
        this.currency = currency;
    }

    public final String getPaymentMethodCode() {
        return paymentMethodCode;
    }

    public final void setPaymentMethodCode(final String paymentMethodCode) {
        this.paymentMethodCode = paymentMethodCode;
    }

    public final String getPrePayValue() {
        return prePayValue;
    }

    public final void setPrePayValue(final String prePayValue) {
        this.prePayValue = prePayValue;
    }

    public final String getPickUpLocation() {
        return pickUpLocation;
    }

    public final void setPickUpLocation(final String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public final String getPptStartDate() {
        return pptStartDate;
    }

    public final void setPptStartDate(final String pptStartDate) {
        this.pptStartDate = pptStartDate;
    }

    public final String getPptExpiryDate() {
        return pptExpiryDate;
    }

    public final void setPptExpiryDate(final String pptExpiryDate) {
        this.pptExpiryDate = pptExpiryDate;
    }

    public final String getAutoLoadState() {
        return autoLoadState;
    }

    public final void setAutoLoadState(final String autoLoadState) {
        this.autoLoadState = autoLoadState;
    }
}
