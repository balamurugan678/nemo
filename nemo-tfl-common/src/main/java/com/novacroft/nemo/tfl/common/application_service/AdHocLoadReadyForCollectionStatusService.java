package com.novacroft.nemo.tfl.common.application_service;


/**
 * Update the ad-hoc load request settlement status 
 */
public interface AdHocLoadReadyForCollectionStatusService {
    Boolean hasAdHocLoadBeenRequested(Integer requestSequenceNumber, String cardNumber);

    void updateAdHocLoadSettlementStatus(Integer requestSequenceNumber, String cardNumber);
}
