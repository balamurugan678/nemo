package com.novacroft.nemo.tfl.common.converter.journey_history;

import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyHistoryResponseDTO;
import com.novacroft.tfl.web_service.model.oyster_journey_history.GetHistoryResponse;

/**
 * Journey history response transfer class / web service model converter.
 */
public interface JourneyHistoryResponseConverter {
    JourneyHistoryResponseDTO convertModelToDto(GetHistoryResponse response);
}
