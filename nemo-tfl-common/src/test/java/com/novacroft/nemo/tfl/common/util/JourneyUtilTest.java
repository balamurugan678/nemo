package com.novacroft.nemo.tfl.common.util;

import com.novacroft.nemo.tfl.common.constant.PseudoTransactionType;
import org.junit.Test;

import static com.novacroft.nemo.test_support.DateTestUtil.getAug22At1723;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug22At1804;
import static org.junit.Assert.*;

/**
 * JourneyUtil unit tests
 */
public class JourneyUtilTest {
    protected static final String START_LOCATION_NAME = "Mornington Crescent";
    protected static final String END_LOCATION_NAME = "Piccadilly Circus";
    protected static final String SEPARATOR = "to";
    protected static final String ROUTE_ID = "42";
    protected static final String BUS_TRIP_PREFIX = "Bus route";
    protected static final String TOP_UP_PREFIX = "Topped up";
    protected static final String TOP_UP_SEPARATOR = "at";
    protected static final String TIME_SEPARATOR = "-";

    @Test
    public void shouldCreateJourneyDescriptionForBusTrip() {
        assertEquals("Bus route 42", JourneyUtil.createJourneyDescription("", "", SEPARATOR, ROUTE_ID, BUS_TRIP_PREFIX));
    }

    @Test
    public void shouldCreateJourneyDescriptionForTubeTrip() {
        assertEquals("Mornington Crescent to Piccadilly Circus",
                JourneyUtil.createJourneyDescription(START_LOCATION_NAME, END_LOCATION_NAME, SEPARATOR, "", BUS_TRIP_PREFIX));
    }

    @Test
    public void shouldCreateJourneyDescriptionForTubeTripWithNoExit() {
        assertEquals("Mornington Crescent",
                JourneyUtil.createJourneyDescription(START_LOCATION_NAME, "", SEPARATOR, "", BUS_TRIP_PREFIX));
    }

    @Test
    public void shouldCreateJourneyDescriptionForTopUp() {
        assertEquals("Topped up at Mornington Crescent",
                JourneyUtil.createJourneyDescription(START_LOCATION_NAME, TOP_UP_SEPARATOR, TOP_UP_PREFIX));
    }

    @Test
    public void shouldCreateJourneyDescriptionForTopUpWithNoLocation() {
        assertEquals("Topped up", JourneyUtil.createJourneyDescription("", TOP_UP_SEPARATOR, TOP_UP_PREFIX));
    }

    @Test
    public void shouldCreateTimeDescription() {
        assertEquals("17:23 - 18:04", JourneyUtil.createTimeDescription(getAug22At1723(), getAug22At1804(), TIME_SEPARATOR));
    }

    @Test
    public void shouldCreateTimeDescriptionWithNoExit() {
        assertEquals("17:23", JourneyUtil.createTimeDescription(getAug22At1723(), null, TIME_SEPARATOR));
    }

    @Test
    public void shouldCreateDatedFileName() {
        assertTrue(JourneyUtil.createDatedFileName("01234", "pdf")
                .matches("01234-[0-9]{4}_[0-9]{2}_[0-9]{2}-[0-9]{2}_[0-9]{2}_[0-9]{2}.pdf"));
    }

    @Test
    public void isAutoTopUpJourneyShouldReturnTrue() {
        assertTrue(JourneyUtil.isAutoTopUpJourney(PseudoTransactionType.AUTO_TOP_UP.pseudoTransactionTypeId()));
    }

    @Test
    public void isAutoTopUpJourneyShouldReturnFalse() {
        assertFalse(JourneyUtil.isAutoTopUpJourney(PseudoTransactionType.COMPLETE_JOURNEY.pseudoTransactionTypeId()));
    }

    @Test
    public void isWarningJourneyShouldReturnTrue() {
        assertTrue(JourneyUtil.isWarningJourney(PseudoTransactionType.UNFINISHED_JOURNEY.pseudoTransactionTypeId()));
    }

    @Test
    public void isWarningJourneyShouldReturnFalse() {
        assertFalse(JourneyUtil.isWarningJourney(PseudoTransactionType.COMPLETE_JOURNEY.pseudoTransactionTypeId()));
    }

    @Test
    public void isDailyCappedReturnTrue() {
        assertTrue(JourneyUtil.isDailyCapped(89));
    }

    @Test
    public void isDailyCappedReturnFalse() {
        assertFalse(JourneyUtil.isDailyCapped(0));
    }
}
