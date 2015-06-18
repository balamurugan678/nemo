package com.novacroft.nemo.tfl.common.transfer.oyster_journey_history;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Transfer class for journey history response
 */
public class JourneyHistoryResponseDTO {
    protected List<JourneyDTO> journeys = new ArrayList<JourneyDTO>();

    public List<JourneyDTO> getJourneys() {
        return journeys;
    }

    public void setJourneys(List<JourneyDTO> journeys) {
        this.journeys = journeys;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("journeys", journeys).toString();
    }
}
