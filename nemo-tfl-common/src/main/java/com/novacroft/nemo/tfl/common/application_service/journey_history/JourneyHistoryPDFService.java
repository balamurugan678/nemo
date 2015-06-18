package com.novacroft.nemo.tfl.common.application_service.journey_history;

import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyHistoryDTO;

/*
 * Journey History PDF Specifications
 */
public interface JourneyHistoryPDFService {
    byte[] createPDF(JourneyHistoryDTO journeyHistory);
}
