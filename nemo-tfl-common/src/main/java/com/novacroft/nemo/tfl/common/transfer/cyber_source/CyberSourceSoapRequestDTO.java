package com.novacroft.nemo.tfl.common.transfer.cyber_source;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * DTO to encapsulate a CyberSource Secure Acceptance payment gateway SOAP request
 */
public class CyberSourceSoapRequestDTO implements CyberSourceRequestDTO {
    protected String billToBuildingNumber;
    protected String billToCity;
    protected String billToCountry;
    protected String billToCustomerId;
    protected String billToEmail;
    protected String billToFirstName;
    protected String billToIpAddress;
    protected String billToLastName;
    protected String billToPhoneNumber;
    protected String billToPostalCode;
    protected String billToStreet1;
    protected String billToStreet2;
    protected Boolean runAuthorizationService;
    protected Boolean runCaptureService;
    protected String orderNumber;
    protected String merchantId;
    protected String transactionUuid;
    protected String currency;
    protected Integer totalAmountInPence;
    protected String paymentCardToken;
    protected Long customerId;
    protected String merchantReferenceCode;

    public CyberSourceSoapRequestDTO() {
    }

    public CyberSourceSoapRequestDTO(String billToBuildingNumber, String billToCity, String billToCountry,
                                     String billToCustomerId, String billToEmail, String billToFirstName,
                                     String billToIpAddress, String billToLastName, String billToPhoneNumber,
                                     String billToPostalCode, String billToStreet1, String billToStreet2,
                                     Boolean runAuthorizationService, Boolean runCaptureService, String orderNumber,
                                     String merchantId, String transactionUuid, String currency, Integer totalAmountInPence,
                                     Long customerId) {
        this.billToBuildingNumber = billToBuildingNumber;
        this.billToCity = billToCity;
        this.billToCountry = billToCountry;
        this.billToCustomerId = billToCustomerId;
        this.billToEmail = billToEmail;
        this.billToFirstName = billToFirstName;
        this.billToIpAddress = billToIpAddress;
        this.billToLastName = billToLastName;
        this.billToPhoneNumber = billToPhoneNumber;
        this.billToPostalCode = billToPostalCode;
        this.billToStreet1 = billToStreet1;
        this.billToStreet2 = billToStreet2;
        this.runAuthorizationService = runAuthorizationService;
        this.runCaptureService = runCaptureService;
        this.orderNumber = orderNumber;
        this.merchantId = merchantId;
        this.transactionUuid = transactionUuid;
        this.currency = currency;
        this.totalAmountInPence = totalAmountInPence;
        this.customerId = customerId;
    }

    public CyberSourceSoapRequestDTO(Boolean runAuthorizationService, Boolean runCaptureService, String orderNumber,
                                     String merchantId, String transactionUuid, String currency, Integer totalAmountInPence,
                                     String paymentCardToken, String billToFirstName, String billToLastName,
                                     String billToIpAddress, String billToEmail, String billToCountry, String billToPostalCode,
                                     String billToCity, String billToStreet2, String billToStreet1, Long customerId) {
        this.runAuthorizationService = runAuthorizationService;
        this.runCaptureService = runCaptureService;
        this.orderNumber = orderNumber;
        this.merchantId = merchantId;
        this.transactionUuid = transactionUuid;
        this.currency = currency;
        this.totalAmountInPence = totalAmountInPence;
        this.paymentCardToken = paymentCardToken;
        this.billToFirstName = billToFirstName;
        this.billToLastName = billToLastName;
        this.billToIpAddress = billToIpAddress;
        this.billToEmail = billToEmail;
        this.billToCountry = billToCountry;
        this.billToPostalCode = billToPostalCode;
        this.billToCity = billToCity;
        this.billToStreet2 = billToStreet2;
        this.billToStreet1 = billToStreet1;
        this.customerId = customerId;
    }

    public CyberSourceSoapRequestDTO(String orderNumber, String transactionUuid, String currency, Integer totalAmountInPence,
                                     Long customerId) {
        this.orderNumber = orderNumber;
        this.transactionUuid = transactionUuid;
        this.currency = currency;
        this.totalAmountInPence = totalAmountInPence;
        this.customerId = customerId;
    }

    public CyberSourceSoapRequestDTO(String paymentCardToken, Long customerId, String merchantReferenceCode) {
        this.paymentCardToken = paymentCardToken;
        this.customerId = customerId;
        this.merchantReferenceCode = merchantReferenceCode;
    }

    public String getBillToBuildingNumber() {
        return billToBuildingNumber;
    }

    public void setBillToBuildingNumber(String billToBuildingNumber) {
        this.billToBuildingNumber = billToBuildingNumber;
    }

    public String getBillToCity() {
        return billToCity;
    }

    public void setBillToCity(String billToCity) {
        this.billToCity = billToCity;
    }

    public String getBillToCountry() {
        return billToCountry;
    }

    public void setBillToCountry(String billToCountry) {
        this.billToCountry = billToCountry;
    }

    public String getBillToCustomerId() {
        return billToCustomerId;
    }

    public void setBillToCustomerId(String billToCustomerId) {
        this.billToCustomerId = billToCustomerId;
    }

    public String getBillToEmail() {
        return billToEmail;
    }

    public void setBillToEmail(String billToEmail) {
        this.billToEmail = billToEmail;
    }

    public String getBillToFirstName() {
        return billToFirstName;
    }

    public void setBillToFirstName(String billToFirstName) {
        this.billToFirstName = billToFirstName;
    }

    public String getBillToIpAddress() {
        return billToIpAddress;
    }

    public void setBillToIpAddress(String billToIpAddress) {
        this.billToIpAddress = billToIpAddress;
    }

    public String getBillToLastName() {
        return billToLastName;
    }

    public void setBillToLastName(String billToLastName) {
        this.billToLastName = billToLastName;
    }

    public String getBillToPhoneNumber() {
        return billToPhoneNumber;
    }

    public void setBillToPhoneNumber(String billToPhoneNumber) {
        this.billToPhoneNumber = billToPhoneNumber;
    }

    public String getBillToPostalCode() {
        return billToPostalCode;
    }

    public void setBillToPostalCode(String billToPostalCode) {
        this.billToPostalCode = billToPostalCode;
    }

    public String getBillToStreet1() {
        return billToStreet1;
    }

    public void setBillToStreet1(String billToStreet1) {
        this.billToStreet1 = billToStreet1;
    }

    public String getBillToStreet2() {
        return billToStreet2;
    }

    public void setBillToStreet2(String billToStreet2) {
        this.billToStreet2 = billToStreet2;
    }

    public Boolean getRunAuthorizationService() {
        return runAuthorizationService;
    }

    public void setRunAuthorizationService(Boolean runAuthorizationService) {
        this.runAuthorizationService = runAuthorizationService;
    }

    public Boolean getRunCaptureService() {
        return runCaptureService;
    }

    public void setRunCaptureService(Boolean runCaptureService) {
        this.runCaptureService = runCaptureService;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getTransactionUuid() {
        return transactionUuid;
    }

    public void setTransactionUuid(String transactionUuid) {
        this.transactionUuid = transactionUuid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getTotalAmountInPence() {
        return totalAmountInPence;
    }

    public void setTotalAmountInPence(Integer totalAmountInPence) {
        this.totalAmountInPence = totalAmountInPence;
    }

    public String getPaymentCardToken() {
        return paymentCardToken;
    }

    public void setPaymentCardToken(String paymentCardToken) {
        this.paymentCardToken = paymentCardToken;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getMerchantReferenceCode() {
        return merchantReferenceCode;
    }

    public void setMerchantReferenceCode(String merchantReferenceCode) {
        this.merchantReferenceCode = merchantReferenceCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("billToBuildingNumber", billToBuildingNumber).append("billToCity", billToCity)
                .append("billToCountry", billToCountry).append("billToCustomerId", billToCustomerId)
                .append("billToEmail", billToEmail).append("billToFirstName", billToFirstName)
                .append("billToIpAddress", billToIpAddress).append("billToLastName", billToLastName)
                .append("billToPhoneNumber", billToPhoneNumber).append("billToPostalCode", billToPostalCode)
                .append("billToStreet1", billToStreet1).append("billToStreet2", billToStreet2)
                .append("runAuthorizationService", runAuthorizationService).append("runCaptureService", runCaptureService)
                .append("orderNumber", orderNumber).append("merchantId", merchantId).append("transactionUuid", transactionUuid)
                .append("currency", currency).append("totalAmountInPence", totalAmountInPence)
                .append("paymentCardToken", paymentCardToken).append("customerId", customerId)
                .append("merchantReferenceCode", merchantReferenceCode).toString();
    }
}
