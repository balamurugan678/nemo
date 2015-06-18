package com.novacroft.nemo.tfl.common.transfer;

import java.net.URI;
import java.util.Date;

/**
 * DTO for passing around email arguments
 */
public class EmailArgumentsDTO {
    protected String toAddress;
    protected String salutation;
    protected URI resetPasswordUri;
    protected URI baseUri;
    protected String pickUpLocationName;
    protected Integer refundAmountInPence;
    protected Date refundedJourneyOn;
    protected String refundReason;
    protected String refundReference;
    protected String cardNumber;
    protected Date pickUpExpiresOn;
    private Date pickedUpOn;
    protected byte[] journeyHistoryCsv;
    protected byte[] journeyHistoryPdf;
    protected Date rangeFrom;
    protected Date rangeTo;
    protected Long customerId;
    protected Long referenceNumber;
    public EmailArgumentsDTO() {
    }

    public EmailArgumentsDTO(String toAddress, String salutation, URI resetPasswordUri, URI baseUri) {
        this.toAddress = toAddress;
        this.salutation = salutation;
        this.resetPasswordUri = resetPasswordUri;
        this.baseUri = baseUri;
    }

    public EmailArgumentsDTO(String toAddress, String salutation, String pickUpLocationName, Integer refundAmountInPence,
                             Date refundedJourneyOn, String refundReason, String refundReference, String cardNumber,
                             Date pickUpExpiresOn, URI baseUri) {
        this.toAddress = toAddress;
        this.salutation = salutation;
        this.pickUpLocationName = pickUpLocationName;
        this.refundAmountInPence = refundAmountInPence;
        this.refundedJourneyOn = refundedJourneyOn;
        this.refundReason = refundReason;
        this.refundReference = refundReference;
        this.cardNumber = cardNumber;
        this.pickUpExpiresOn = pickUpExpiresOn;
        this.baseUri = baseUri;
    }

    public EmailArgumentsDTO(String toAddress, String salutation, URI baseUri, String cardNumber, Date rangeFrom, Date rangeTo,
                             byte[] journeyHistoryCsv, byte[] journeyHistoryPdf, Long customerId) {
        this.toAddress = toAddress;
        this.salutation = salutation;
        this.baseUri = baseUri;
        this.cardNumber = cardNumber;
        this.journeyHistoryCsv = (journeyHistoryCsv != null) ? journeyHistoryCsv.clone() : null;
        this.journeyHistoryPdf = (journeyHistoryPdf != null) ? journeyHistoryPdf.clone() : null;
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
        this.customerId = customerId;
    }

    public Long getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(Long referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getPickUpLocationName() {
        return pickUpLocationName;
    }

    public void setPickUpLocationName(String pickUpLocationName) {
        this.pickUpLocationName = pickUpLocationName;
    }

    public Integer getRefundAmountInPence() {
        return refundAmountInPence;
    }

    public void setRefundAmountInPence(Integer refundAmountInPence) {
        this.refundAmountInPence = refundAmountInPence;
    }

    public Date getRefundedJourneyOn() {
        return refundedJourneyOn;
    }

    public void setRefundedJourneyOn(Date refundedJourneyOn) {
        this.refundedJourneyOn = refundedJourneyOn;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getRefundReference() {
        return refundReference;
    }

    public void setRefundReference(String refundReference) {
        this.refundReference = refundReference;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getPickUpExpiresOn() {
        return pickUpExpiresOn;
    }

    public void setPickUpExpiresOn(Date pickUpExpiresOn) {
        this.pickUpExpiresOn = pickUpExpiresOn;
    }

    public URI getResetPasswordUri() {
        return resetPasswordUri;
    }

    public void setResetPasswordUri(URI resetPasswordUri) {
        this.resetPasswordUri = resetPasswordUri;
    }

    public URI getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(URI baseUri) {
        this.baseUri = baseUri;
    }

    public byte[] getJourneyHistoryCsv() {
        return journeyHistoryCsv;
    }

    public void setJourneyHistoryCsv(byte[] journeyHistoryCsv) {
        this.journeyHistoryCsv = (journeyHistoryCsv != null) ? journeyHistoryCsv.clone() : null;
    }

    public byte[] getJourneyHistoryPdf() {
        return journeyHistoryPdf;
    }

    public void setJourneyHistoryPdf(byte[] journeyHistoryPdf) {
        this.journeyHistoryPdf = (journeyHistoryPdf != null) ? journeyHistoryPdf.clone() : null;
    }

    public Date getRangeFrom() {
        return rangeFrom;
    }

    public void setRangeFrom(Date rangeFrom) {
        this.rangeFrom = rangeFrom;
    }

    public Date getRangeTo() {
        return rangeTo;
    }

    public void setRangeTo(Date rangeTo) {
        this.rangeTo = rangeTo;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the pickedUpOn
     */
    public Date getPickedUpOn() {
        return pickedUpOn;
    }

    /**
     * @param pickedUpOn the pickedUpOn to set
     */
    public void setPickedUpOn(Date pickedUpOn) {
        this.pickedUpOn = pickedUpOn;
    }
}
