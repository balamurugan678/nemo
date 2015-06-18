package com.novacroft.nemo.tfl.common.application_service.cubic_import;

public interface AdHocLoadPickUpWindowExpiredStatusService {
    Boolean hasAdHocLoadBeenRequested(Integer requestSequenceNumber, String cardNumber);

    void updateStatusToPickUpExpired(Integer requestSequenceNumber, String cardNumber);
}
