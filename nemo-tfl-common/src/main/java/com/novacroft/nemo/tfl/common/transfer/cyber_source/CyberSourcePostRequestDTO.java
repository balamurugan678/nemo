package com.novacroft.nemo.tfl.common.transfer.cyber_source;

import com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourcePostRequestField;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * DTO to encapsulate a CyberSource Secure Acceptance payment gateway HTTP POST request
 */
public class CyberSourcePostRequestDTO extends BaseCyberSourceApiDTO implements CyberSourceRequestDTO {
    public CyberSourcePostRequestDTO() {
    }

    public CyberSourcePostRequestDTO(String accessKey, String amount, String currency, String locale, String profileId,
                                     String referenceNumber, String transactionType, String transactionUuid,
                                     String deviceFingerPrint, String paymentMethod, String consumerId, String clientIpAddress,
                                     String cookiesEnabledOnClient, String cancelReplyUrl, String receiptReplyUrl) {
        set(CyberSourcePostRequestField.ACCESS_KEY, accessKey);
        set(CyberSourcePostRequestField.AMOUNT, amount);
        set(CyberSourcePostRequestField.CURRENCY, currency);
        set(CyberSourcePostRequestField.LOCALE, locale);
        set(CyberSourcePostRequestField.PROFILE_ID, profileId);
        set(CyberSourcePostRequestField.REFERENCE_NUMBER, referenceNumber);
        set(CyberSourcePostRequestField.TRANSACTION_TYPE, transactionType);
        set(CyberSourcePostRequestField.TRANSACTION_UUID, transactionUuid);
        set(CyberSourcePostRequestField.DEVICE_FINGERPRINT_ID, deviceFingerPrint);
        set(CyberSourcePostRequestField.PAYMENT_METHOD, paymentMethod);
        set(CyberSourcePostRequestField.CONSUMER_ID, consumerId);
        set(CyberSourcePostRequestField.CUSTOMER_IP_ADDRESS, clientIpAddress);
        set(CyberSourcePostRequestField.CUSTOMER_COOKIES_ACCEPTED, cookiesEnabledOnClient);
        set(CyberSourcePostRequestField.OVERRIDE_CUSTOM_CANCEL_PAGE, cancelReplyUrl);
        set(CyberSourcePostRequestField.OVERRIDE_CUSTOM_RECEIPT_PAGE, receiptReplyUrl);
    }

    public CyberSourcePostRequestDTO(String accessKey, String amount, String currency, String locale, String profileId,
                                     String referenceNumber, String signature, String signedDateTime, String signedFieldNames,
                                     String transactionType, String transactionUuid, String unsignedFieldNames) {
        set(CyberSourcePostRequestField.ACCESS_KEY, accessKey);
        set(CyberSourcePostRequestField.AMOUNT, amount);
        set(CyberSourcePostRequestField.CURRENCY, currency);
        set(CyberSourcePostRequestField.LOCALE, locale);
        set(CyberSourcePostRequestField.PROFILE_ID, profileId);
        set(CyberSourcePostRequestField.REFERENCE_NUMBER, referenceNumber);
        set(CyberSourcePostRequestField.SIGNATURE, signature);
        set(CyberSourcePostRequestField.SIGNED_DATE_TIME, signedDateTime);
        set(CyberSourcePostRequestField.SIGNED_FIELD_NAMES, signedFieldNames);
        set(CyberSourcePostRequestField.TRANSACTION_TYPE, transactionType);
        set(CyberSourcePostRequestField.TRANSACTION_UUID, transactionUuid);
        set(CyberSourcePostRequestField.UNSIGNED_FIELD_NAMES, unsignedFieldNames);
    }

    public String getAccessKey() {
        return get(CyberSourcePostRequestField.ACCESS_KEY);
    }

    public void setAccessKey(String accessKey) {
        set(CyberSourcePostRequestField.ACCESS_KEY, accessKey);
    }

    public String getAmount() {
        return get(CyberSourcePostRequestField.AMOUNT);
    }

    public void setAmount(String amount) {
        set(CyberSourcePostRequestField.AMOUNT, amount);
    }

    public String getCurrency() {
        return get(CyberSourcePostRequestField.CURRENCY);
    }

    public void setCurrency(String currency) {
        set(CyberSourcePostRequestField.CURRENCY, currency);
    }

    public String getLocale() {
        return get(CyberSourcePostRequestField.LOCALE);
    }

    public void setLocale(String locale) {
        set(CyberSourcePostRequestField.LOCALE, locale);
    }

    public String getProfileId() {
        return get(CyberSourcePostRequestField.PROFILE_ID);
    }

    public void setProfileId(String profileId) {
        set(CyberSourcePostRequestField.PROFILE_ID, profileId);
    }

    public String getReferenceNumber() {
        return get(CyberSourcePostRequestField.REFERENCE_NUMBER);
    }

    public void setReferenceNumber(String referenceNumber) {
        set(CyberSourcePostRequestField.REFERENCE_NUMBER, referenceNumber);
    }

    public String getSignature() {
        return get(CyberSourcePostRequestField.SIGNATURE);
    }

    public void setSignature(String signature) {
        set(CyberSourcePostRequestField.SIGNATURE, signature);
    }

    public String getSignedDateTime() {
        return get(CyberSourcePostRequestField.SIGNED_DATE_TIME);
    }

    public void setSignedDateTime(String signedDateTime) {
        set(CyberSourcePostRequestField.SIGNED_DATE_TIME, signedDateTime);
    }

    public String getSignedFieldNames() {
        return get(CyberSourcePostRequestField.SIGNED_FIELD_NAMES);
    }

    public void setSignedFieldNames(String signedFieldNames) {
        set(CyberSourcePostRequestField.SIGNED_FIELD_NAMES, signedFieldNames);
    }

    public String getTransactionType() {
        return get(CyberSourcePostRequestField.TRANSACTION_TYPE);
    }

    public void setTransactionType(String transactionType) {
        set(CyberSourcePostRequestField.TRANSACTION_TYPE, transactionType);
    }

    public String getTransactionUuid() {
        return get(CyberSourcePostRequestField.TRANSACTION_UUID);
    }

    public void setTransactionUuid(String transactionUuid) {
        set(CyberSourcePostRequestField.TRANSACTION_UUID, transactionUuid);
    }

    public String getUnsignedFieldNames() {
        return get(CyberSourcePostRequestField.UNSIGNED_FIELD_NAMES);
    }

    public void setUnsignedFieldNames(String unsignedFieldNames) {
        set(CyberSourcePostRequestField.UNSIGNED_FIELD_NAMES, unsignedFieldNames);
    }

    public String getClientIpAddress() {
        return get(CyberSourcePostRequestField.CUSTOMER_IP_ADDRESS);
    }

    public void setClientIpAddress(String clientIpAddress) {
        set(CyberSourcePostRequestField.CUSTOMER_IP_ADDRESS, clientIpAddress);
    }

    public String getCookiesEnabledOnClient() {
        return get(CyberSourcePostRequestField.CUSTOMER_COOKIES_ACCEPTED);
    }

    public void setCookiesEnabledOnClient(String cookiesEnabledOnClient) {
        set(CyberSourcePostRequestField.CUSTOMER_COOKIES_ACCEPTED, cookiesEnabledOnClient);
    }

    public String getConsumerId() {
        return get(CyberSourcePostRequestField.CONSUMER_ID);
    }

    public void setConsumerId(String consumerId) {
        set(CyberSourcePostRequestField.CONSUMER_ID, consumerId);
    }

    public String getDateOfBirth() {
        return get(CyberSourcePostRequestField.DATE_OF_BIRTH);
    }

    public void setDateOfBirth(String dateOfBirth) {
        set(CyberSourcePostRequestField.DATE_OF_BIRTH, dateOfBirth);
    }

    public String getDeviceFingerPrint() {
        return get(CyberSourcePostRequestField.DEVICE_FINGERPRINT_ID);
    }

    public void setDeviceFingerPrint(String deviceFingerPrint) {
        set(CyberSourcePostRequestField.DEVICE_FINGERPRINT_ID, deviceFingerPrint);
    }

    public String getPaymentToken() {
        return get(CyberSourcePostRequestField.PAYMENT_TOKEN);
    }

    public void setPaymentToken(String paymentToken) {
        set(CyberSourcePostRequestField.PAYMENT_TOKEN, paymentToken);
    }

    public String getPaymentTokenTitle() {
        return get(CyberSourcePostRequestField.PAYMENT_TOKEN_TITLE);
    }

    public void setPaymentTokenTitle(String paymentTokenTitle) {
        set(CyberSourcePostRequestField.PAYMENT_TOKEN_TITLE, paymentTokenTitle);
    }

    public String getPaymentMethod() {
        return get(CyberSourcePostRequestField.PAYMENT_METHOD);
    }

    public void setPaymentMethod(String paymentMethod) {
        set(CyberSourcePostRequestField.PAYMENT_METHOD, paymentMethod);
    }

    public String getOverrideCustomCancelPage() {
        return get(CyberSourcePostRequestField.OVERRIDE_CUSTOM_CANCEL_PAGE);
    }

    public void setOverrideCustomCancelPage(String cancelPage) {
        set(CyberSourcePostRequestField.OVERRIDE_CUSTOM_CANCEL_PAGE, cancelPage);
    }

    public String getOverrideCustomReceiptPage() {
        return get(CyberSourcePostRequestField.OVERRIDE_CUSTOM_RECEIPT_PAGE);
    }

    public void setOverrideCustomReceiptPage(String cancelPage) {
        set(CyberSourcePostRequestField.OVERRIDE_CUSTOM_RECEIPT_PAGE, cancelPage);
    }

    private String get(CyberSourcePostRequestField RequestField) {
        return get(RequestField.code());
    }

    private void set(CyberSourcePostRequestField RequestField, String argumentValue) {
        set(RequestField.code(), argumentValue);
    }

    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this);
        for (CyberSourcePostRequestField cyberSourcePostRequestField : CyberSourcePostRequestField.values()) {
            toStringBuilder.append(cyberSourcePostRequestField.code(), this.get(cyberSourcePostRequestField.code()));
        }
        return toStringBuilder.toString();
    }
}
