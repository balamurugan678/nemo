package com.novacroft.nemo.tfl.common.comparator.journey_history;

import static com.novacroft.nemo.common.utils.DateUtil.isAfter;
import static com.novacroft.nemo.common.utils.DateUtil.isBefore;
import static com.novacroft.nemo.tfl.common.constant.ComparatorConstant.SORT_AFTER;
import static com.novacroft.nemo.tfl.common.constant.ComparatorConstant.SORT_BEFORE;

import java.util.Comparator;

import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;

/**
 * Comparator for sorting journeys - most recent journey last
 * 
 * Returns a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second
 */
public class JourneyComparator implements Comparator<JourneyDTO> {
    @Override
    public int compare(JourneyDTO journey1, JourneyDTO journey2) {
        if (isAfter(journey1.getTrafficOn(), journey2.getTrafficOn())) {
            return SORT_AFTER;
        }
        if (isBefore(journey1.getTrafficOn(), journey2.getTrafficOn())) {
            return SORT_BEFORE;
        }
        if (journey1.getTransactionAt() != null && journey2.getTransactionAt() != null &&
                isAfter(journey1.getTransactionAt(), journey2.getTransactionAt())) {
            return SORT_AFTER;
        }
        if (journey1.getTransactionAt() != null && journey2.getTransactionAt() != null &&
                isBefore(journey1.getTransactionAt(), journey2.getTransactionAt())) {
            return SORT_BEFORE;
        }
        return journey1.getJourneyId().compareTo(journey2.getJourneyId());
    }
}
