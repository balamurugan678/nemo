package com.novacroft.nemo.tfl.common.application_service.cubic_import;

public interface AdHocLoadErrorStatusService {
    Boolean hasAdHocLoadBeenRequested(Integer requestSequenceNumber, String cardNumber);

    void updateStatusToFailed(Integer requestSequenceNumber, String cardNumber);
}
