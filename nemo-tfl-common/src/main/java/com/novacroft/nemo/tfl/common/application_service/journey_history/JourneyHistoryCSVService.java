package com.novacroft.nemo.tfl.common.application_service.journey_history;

import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDayDTO;

import java.util.List;

/**
 * Journey History CSV specification.
 */
public interface JourneyHistoryCSVService {
    byte[] createCSV(List<JourneyDayDTO> journeyDays);
}
