package com.novacroft.nemo.tfl.common.transfer.cyber_source;

/**
 * CyberSource payment gateway reply specification
 */
public interface CyberSourceReplyDTO {
    String getDecision();

    String getMessage();

    String getReasonCode();

    String getTransactionId();

    String getTransactionAt();

    String getTransactionAmount();

    String getTransactionReference();
    
    String getRequestId();
}
