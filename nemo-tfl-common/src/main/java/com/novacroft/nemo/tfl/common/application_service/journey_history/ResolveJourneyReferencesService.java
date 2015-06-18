package com.novacroft.nemo.tfl.common.application_service.journey_history;

import java.util.List;

import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;

/**
 * Look up references in the Journey History Tap data
 */
public interface ResolveJourneyReferencesService {
    void resolveReferences(List<JourneyDTO> journeys);
}
