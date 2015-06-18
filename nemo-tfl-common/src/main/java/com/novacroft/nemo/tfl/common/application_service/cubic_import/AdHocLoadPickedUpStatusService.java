package com.novacroft.nemo.tfl.common.application_service.cubic_import;

public interface AdHocLoadPickedUpStatusService {
    Boolean hasAdHocLoadBeenRequested(Integer requestSequenceNumber, String cardNumber);

    void updateStatusToPickedUp(Integer requestSequenceNumber, String cardNumber);
}
