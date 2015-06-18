package com.novacroft.nemo.tfl.common.converter.journey_history;

import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.TapDTO;
import com.novacroft.tfl.web_service.model.oyster_journey_history.Tap;

/**
 * Journey history tap transfer class / web service model converter.
 */
public interface JourneyHistoryTapConverter {
    TapDTO convertModelToDto(Tap tap);
}
