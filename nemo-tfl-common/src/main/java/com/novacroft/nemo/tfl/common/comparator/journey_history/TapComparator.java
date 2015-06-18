package com.novacroft.nemo.tfl.common.comparator.journey_history;

import static com.novacroft.nemo.common.utils.DateUtil.isAfter;
import static com.novacroft.nemo.common.utils.DateUtil.isBefore;
import static com.novacroft.nemo.tfl.common.constant.ComparatorConstant.SORT_AFTER;
import static com.novacroft.nemo.tfl.common.constant.ComparatorConstant.SORT_BEFORE;

import java.util.Comparator;

import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.TapDTO;

/**
 * Comparator for sorting taps - most recent tap first
 */
public class TapComparator implements Comparator<TapDTO> {
    @Override
    public int compare(TapDTO tap1, TapDTO tap2) {
        if (isAfter(tap1.getTransactionAt(), tap2.getTransactionAt())) {
            return SORT_AFTER;
        }
        if (isBefore(tap1.getTransactionAt(), tap2.getTransactionAt())) {
            return SORT_BEFORE;
        }
        return tap1.getSequenceNumber().compareTo(tap2.getSequenceNumber());
    }
}
