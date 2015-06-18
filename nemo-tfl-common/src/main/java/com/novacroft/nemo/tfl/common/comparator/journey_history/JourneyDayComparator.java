package com.novacroft.nemo.tfl.common.comparator.journey_history;

import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDayDTO;

import java.util.Comparator;

/**
 * Comparator for sorting journey days - most recent day first
 */
public class JourneyDayComparator implements Comparator<JourneyDayDTO> {
    @Override
    public int compare(JourneyDayDTO journeyDay1, JourneyDayDTO journeyDay2) {
        return journeyDay2.getEffectiveTrafficOn().compareTo(journeyDay1.getEffectiveTrafficOn());
    }
}
