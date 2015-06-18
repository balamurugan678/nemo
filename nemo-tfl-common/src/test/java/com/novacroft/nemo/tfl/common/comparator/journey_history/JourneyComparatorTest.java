package com.novacroft.nemo.tfl.common.comparator.journey_history;

import static com.novacroft.nemo.test_support.DateTestUtil.getAug19;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug20;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug22;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug22At1723;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug22At1804;
import static com.novacroft.nemo.tfl.common.constant.ComparatorConstant.SORT_AFTER;
import static com.novacroft.nemo.tfl.common.constant.ComparatorConstant.SORT_BEFORE;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;

/**
 * JourneyComparator unit tests
 */
public class JourneyComparatorTest {

    @Test
    public void shouldSortBeforeByDate() {
        JourneyComparator comparator = new JourneyComparator();
        assertEquals(SORT_BEFORE, comparator.compare(getJourneyOnAug19WithIdOf1(), getJourneyOnAug20WithIdOf2()));

    }

    @Test
    public void shouldSortAfterByDate() {
        JourneyComparator comparator = new JourneyComparator();
        assertEquals(SORT_AFTER, comparator.compare(getJourneyOnAug20WithIdOf2(), getJourneyOnAug19WithIdOf1()));

    }

    @Test
    public void shouldSortBeforeByTime() {
        JourneyComparator comparator = new JourneyComparator();
        assertEquals(SORT_BEFORE, comparator.compare(getJourneyOnAug22At1723WithIdOf3(), getJourneyOnAug22At1804WithIdOf4()));

    }

    @Test
    public void shouldSortAfterByTime() {
        JourneyComparator comparator = new JourneyComparator();
        assertEquals(SORT_AFTER, comparator.compare(getJourneyOnAug22At1804WithIdOf4(), getJourneyOnAug22At1723WithIdOf3()));

    }

    @Test
    public void shouldSortBeforeById() {
        JourneyComparator comparator = new JourneyComparator();
        assertEquals(SORT_BEFORE, comparator.compare(getJourneyOnAug22At1804WithIdOf4(), getJourneyOnAug22At1804WithIdOf5()));

    }

    @Test
    public void shouldSortAfterById() {
        JourneyComparator comparator = new JourneyComparator();
        assertEquals(SORT_AFTER, comparator.compare(getJourneyOnAug22At1804WithIdOf5(), getJourneyOnAug22At1804WithIdOf4()));

    }

    protected JourneyDTO getJourneyOnAug19WithIdOf1() {
        return getTestJourney(1, getAug19());
    }

    protected JourneyDTO getJourneyOnAug20WithIdOf2() {
        return getTestJourney(2, getAug20());
    }

    protected JourneyDTO getJourneyOnAug22At1723WithIdOf3() {
        return getTestJourney(3, getAug22(), getAug22At1723());
    }

    protected JourneyDTO getJourneyOnAug22At1804WithIdOf4() {
        return getTestJourney(4, getAug22(), getAug22At1804());
    }

    protected JourneyDTO getJourneyOnAug22At1804WithIdOf5() {
        return getTestJourney(5, getAug22(), getAug22At1804());
    }

    protected JourneyDTO getTestJourney(Integer journeyId, Date trafficOn) {
        JourneyDTO journeyDTO = new JourneyDTO();
        journeyDTO.setJourneyId(journeyId);
        journeyDTO.setTrafficOn(trafficOn);
        return journeyDTO;
    }

    protected JourneyDTO getTestJourney(Integer journeyId, Date trafficOn, Date transactionAt) {
        JourneyDTO journeyDTO = getTestJourney(journeyId, trafficOn);
        journeyDTO.setTransactionAt(transactionAt);
        return journeyDTO;
    }
}
