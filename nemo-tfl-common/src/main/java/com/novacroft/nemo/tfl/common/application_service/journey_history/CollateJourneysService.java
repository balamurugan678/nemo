package com.novacroft.nemo.tfl.common.application_service.journey_history;

import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDayDTO;

import java.util.List;

/**
 * Collate journeys
 */
public interface CollateJourneysService {
    List<JourneyDayDTO> collateByDay(List<JourneyDTO> journeys);
}
