package com.novacroft.nemo.tfl.common.transfer.cyber_source;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.novacroft.nemo.common.utils.StringUtil;

/**
 * DTO to encapsulate a CyberSource Secure Acceptance payment gateway HTTP POST reply
 */
public class CyberSourceSoapReplyDTO implements CyberSourceReplyDTO {
    protected String authorizedAmount;
    protected String authorizationCode;
    protected String authorizedAt;
    protected String authorizationPaymentNetworkTransactionId;
    protected String authorizationReasonCode;
    protected String captureAmount;
    protected String captureReasonCode;
    protected String decision;
    protected String invalidFields;
    protected String merchantReferenceCode;
    protected String missingFields;
    protected String reasonCode;
    protected String requestId;

    public CyberSourceSoapReplyDTO() {
    }

    public CyberSourceSoapReplyDTO(String authorizedAmount, String authorizationCode, String authorizedAt,
                                   String authorizationPaymentNetworkTransactionId, String authorizationReasonCode,
                                   String captureAmount, String captureReasonCode, String decision, String invalidFields,
                                   String merchantReferenceCode, String missingFields, String reasonCode, String requestId) {
        this.authorizedAmount = authorizedAmount;
        this.authorizationCode = authorizationCode;
        this.authorizedAt = authorizedAt;
        this.authorizationPaymentNetworkTransactionId = authorizationPaymentNetworkTransactionId;
        this.authorizationReasonCode = authorizationReasonCode;
        this.captureAmount = captureAmount;
        this.captureReasonCode = captureReasonCode;
        this.decision = decision;
        this.invalidFields = invalidFields;
        this.merchantReferenceCode = merchantReferenceCode;
        this.missingFields = missingFields;
        this.reasonCode = reasonCode;
        this.requestId = requestId;
    }

    public CyberSourceSoapReplyDTO(String decision, String invalidFields, String merchantReferenceCode, String missingFields,
                                   String reasonCode, String requestId) {
        this.requestId = requestId;
        this.reasonCode = reasonCode;
        this.missingFields = missingFields;
        this.merchantReferenceCode = merchantReferenceCode;
        this.invalidFields = invalidFields;
        this.decision = decision;
    }

    public String getAuthorizedAmount() {
        return authorizedAmount;
    }

    public void setAuthorizedAmount(String authorizedAmount) {
        this.authorizedAmount = authorizedAmount;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getAuthorizedAt() {
        return authorizedAt;
    }

    public void setAuthorizedAt(String authorizedAt) {
        this.authorizedAt = authorizedAt;
    }

    public String getAuthorizationPaymentNetworkTransactionId() {
        return authorizationPaymentNetworkTransactionId;
    }

    public void setAuthorizationPaymentNetworkTransactionId(String authorizationPaymentNetworkTransactionId) {
        this.authorizationPaymentNetworkTransactionId = authorizationPaymentNetworkTransactionId;
    }

    public String getAuthorizationReasonCode() {
        return authorizationReasonCode;
    }

    public void setAuthorizationReasonCode(String authorizationReasonCode) {
        this.authorizationReasonCode = authorizationReasonCode;
    }

    public String getCaptureAmount() {
        return captureAmount;
    }

    public void setCaptureAmount(String captureAmount) {
        this.captureAmount = captureAmount;
    }

    public String getCaptureReasonCode() {
        return captureReasonCode;
    }

    public void setCaptureReasonCode(String captureReasonCode) {
        this.captureReasonCode = captureReasonCode;
    }

    @Override
    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getInvalidFields() {
        return invalidFields;
    }

    public void setInvalidFields(String invalidFields) {
        this.invalidFields = invalidFields;
    }

    public String getMerchantReferenceCode() {
        return merchantReferenceCode;
    }

    public void setMerchantReferenceCode(String merchantReferenceCode) {
        this.merchantReferenceCode = merchantReferenceCode;
    }

    public String getMissingFields() {
        return missingFields;
    }

    public void setMissingFields(String missingFields) {
        this.missingFields = missingFields;
    }

    @Override
    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }
    
    @Override
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String getMessage() {
        return StringUtil.EMPTY_STRING;
    }

    @Override
    public String getTransactionId() {
        return this.authorizationPaymentNetworkTransactionId;
    }

    @Override
    public String getTransactionAt() {
        return this.authorizedAt;
    }

    @Override
    public String getTransactionAmount() {
        return this.authorizedAmount;
    }

    @Override
    public String getTransactionReference() {
        return null;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("authorizedAmount", authorizedAmount)
                .append("authorizationCode", authorizationCode).append("authorizedAt", authorizedAt)
                .append("authorizationPaymentNetworkTransactionId", authorizationPaymentNetworkTransactionId)
                .append("authorizationReasonCode", authorizationReasonCode).append("captureAmount", captureAmount)
                .append("captureReasonCode", captureReasonCode).append("decision", decision)
                .append("invalidFields", invalidFields).append("merchantReferenceCode", merchantReferenceCode)
                .append("missingFields", missingFields).append("reasonCode", reasonCode).append("requestId", requestId)
                .toString();
    }
}
