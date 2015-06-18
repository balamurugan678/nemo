package com.novacroft.nemo.tfl.common.comparator.journey_history;

import static com.novacroft.nemo.test_support.JourneyTestUtil.getTestJourneyDayDTO1;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getTestJourneyDayDTO2;
import static com.novacroft.nemo.tfl.common.constant.ComparatorConstant.SORT_AFTER;
import static com.novacroft.nemo.tfl.common.constant.ComparatorConstant.SORT_BEFORE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * JourneyDayComparator unit tests
 */
public class JourneyDayComparatorTest {

    @Test
    public void shouldSortBefore() {
        JourneyDayComparator comparator = new JourneyDayComparator();
        assertEquals(SORT_BEFORE, comparator.compare(getTestJourneyDayDTO2(), getTestJourneyDayDTO1()));
    }

    @Test
    public void shouldSortAfter() {
        JourneyDayComparator comparator = new JourneyDayComparator();
        assertEquals(SORT_AFTER, comparator.compare(getTestJourneyDayDTO1(), getTestJourneyDayDTO2()));
    }
}
