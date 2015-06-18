package com.novacroft.nemo.tfl.common.transfer.cyber_source;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourcePostReplyField;

/**
 * DTO to encapsulate a CyberSource Secure Acceptance payment gateway HTTP POST reply
 */
public class CyberSourcePostReplyDTO extends BaseCyberSourceApiDTO implements CyberSourceReplyDTO {

    public CyberSourcePostReplyDTO() {
    }

    public CyberSourcePostReplyDTO(String authAmount, String authTime, String authTransRefNo, String decision,
                                   String invalidFields, String message, String reasonCode, String paymentToken,
                                   String reqReferenceNumber, String reqTransactionUuid, String signature,
                                   String signedDateTime, String signedFieldNames, String transactionId,
                                   String requiredFields) {
        this.set(CyberSourcePostReplyField.AUTH_AMOUNT, authAmount);
        this.set(CyberSourcePostReplyField.AUTH_TIME, authTime);
        this.set(CyberSourcePostReplyField.AUTH_TRANS_REF_NO, authTransRefNo);
        this.set(CyberSourcePostReplyField.DECISION, decision);
        this.set(CyberSourcePostReplyField.INVALID_FIELDS, invalidFields);
        this.set(CyberSourcePostReplyField.MESSAGE, message);
        this.set(CyberSourcePostReplyField.REASON_CODE, reasonCode);
        this.set(CyberSourcePostReplyField.PAYMENT_TOKEN, paymentToken);
        this.set(CyberSourcePostReplyField.REQ_REFERENCE_NUMBER, reqReferenceNumber);
        this.set(CyberSourcePostReplyField.REQ_TRANSACTION_UUID, reqTransactionUuid);
        this.set(CyberSourcePostReplyField.SIGNATURE, signature);
        this.set(CyberSourcePostReplyField.SIGNED_DATE_TIME, signedDateTime);
        this.set(CyberSourcePostReplyField.SIGNED_FIELD_NAMES, signedFieldNames);
        this.set(CyberSourcePostReplyField.TRANSACTION_ID, transactionId);
        this.set(CyberSourcePostReplyField.REQUIRED_FIELDS, requiredFields);
    }

    public CyberSourcePostReplyDTO(Map<String, String> arguments) {
        this.arguments = arguments;
    }

    @Override
    public String getDecision() {
        return this.get(CyberSourcePostReplyField.DECISION);
    }

    @Override
    public String getMessage() {
        return this.get(CyberSourcePostReplyField.MESSAGE);
    }

    @Override
    public String getReasonCode() {
        return this.get(CyberSourcePostReplyField.REASON_CODE);
    }

    @Override
    public String getTransactionId() {
        return this.get(CyberSourcePostReplyField.TRANSACTION_ID);
    }

    public String getAuthAmount() {
        return this.get(CyberSourcePostReplyField.AUTH_AMOUNT);
    }

    public String getAuthTime() {
        return this.get(CyberSourcePostReplyField.AUTH_TIME);
    }

    public String getAuthTransRefNo() {
        return this.get(CyberSourcePostReplyField.AUTH_TRANS_REF_NO);
    }

    public String getReqTransactionUuid() {
        return this.get(CyberSourcePostReplyField.REQ_TRANSACTION_UUID);
    }

    public String getReqReferenceNumber() {
        return this.get(CyberSourcePostReplyField.REQ_REFERENCE_NUMBER);
    }

    public String getSignature() {
        return this.get(CyberSourcePostReplyField.SIGNATURE);
    }

    public String getSignedDateTime() {
        return this.get(CyberSourcePostReplyField.SIGNED_DATE_TIME);
    }

    public String getSignedFieldNames() {
        return this.get(CyberSourcePostReplyField.SIGNED_FIELD_NAMES);
    }

    public String getRequestTransactionType() {
        return this.get(CyberSourcePostReplyField.REQ_TRANSACTION_TYPE);
    }

    public String getRequestCardNumber() {
        return this.get(CyberSourcePostReplyField.REQ_CARD_NUMBER);
    }

    public String getPaymentToken() {
        return this.get(CyberSourcePostReplyField.PAYMENT_TOKEN);
    }

    public String getRequestCardExpiryDate() {
        return this.get(CyberSourcePostReplyField.REQ_CARD_EXPIRY_DATE);
    }

    public String getRequestBillToForename() {
        return this.get(CyberSourcePostReplyField.REQ_BILL_TO_FORENAME);
    }

    public String getRequestBillToSurname() {
        return this.get(CyberSourcePostReplyField.REQ_BILL_TO_SURNAME);
    }

    public String getRequestBillToAddressLine1() {
        return this.get(CyberSourcePostReplyField.REQ_BILL_TO_ADDRESS_LINE1);
    }

    public String getRequestBillToAddressLine2() {
        return this.get(CyberSourcePostReplyField.REQ_BILL_TO_ADDRESS_LINE2);
    }

    public String getRequestBillToAddressPostCode() {
        return this.get(CyberSourcePostReplyField.REQ_BILL_TO_ADDRESS_POSTAL_CODE);
    }

    public String getRequestBillToAddressCountry() {
        return this.get(CyberSourcePostReplyField.REQ_BILL_TO_ADDRESS_COUNTRY);
    }

    public String getRequestBillToAddressCity() {
        return this.get(CyberSourcePostReplyField.REQ_BILL_TO_ADDRESS_CITY);
    }

    public void setReplyArgument(CyberSourcePostReplyField replyField, String argumentValue) {
        set(replyField, argumentValue);
    }

    @Override
    public String getTransactionAt() {
        return getAuthTime();
    }

    @Override
    public String getTransactionAmount() {
        return getAuthAmount();
    }

    @Override
    public String getTransactionReference() {
        return getAuthTransRefNo();
    }
    
    @Override
    public String getRequestId() {
        return getTransactionId();
    }

    private String get(CyberSourcePostReplyField replyField) {
        return get(replyField.code());
    }

    private void set(CyberSourcePostReplyField replyField, String argumentValue) {
        set(replyField.code(), argumentValue);
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this);
        for (CyberSourcePostReplyField cyberSourcePostReplyField : CyberSourcePostReplyField.values()) {
            toStringBuilder.append(cyberSourcePostReplyField.code(), this.get(cyberSourcePostReplyField.code()));
        }
        return toStringBuilder.toString();
    }

    
}