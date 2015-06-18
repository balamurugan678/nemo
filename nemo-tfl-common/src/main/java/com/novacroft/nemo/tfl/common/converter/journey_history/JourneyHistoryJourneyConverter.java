package com.novacroft.nemo.tfl.common.converter.journey_history;

import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;
import com.novacroft.tfl.web_service.model.oyster_journey_history.Journey;

/**
 * Journey history journey transfer class / web service model converter.
 */
public interface JourneyHistoryJourneyConverter {
    JourneyDTO convertModelToDto(Journey journey);

    String[] convertDtoToRecord(JourneyDTO journeyDTO);
}
