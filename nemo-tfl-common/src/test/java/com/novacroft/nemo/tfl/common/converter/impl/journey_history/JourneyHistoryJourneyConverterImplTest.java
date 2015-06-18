package com.novacroft.nemo.tfl.common.converter.impl.journey_history;

import static com.novacroft.nemo.test_support.JourneyTestUtil.EXPECTED_JOURNEY_DESCRIPTION_UNFINISHED;
import static com.novacroft.nemo.test_support.JourneyTestUtil.EXPECTED_JOURNEY_DESCRIPTION_UNSTARTED;
import static com.novacroft.nemo.test_support.JourneyTestUtil.JOURNEY_DESCRIPTION_1;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getTestJourney1;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getTestJourneyDTO1;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getTestJourneyDTO3;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getTestJourneyDTO4;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;

/**
 * JourneyHistoryJourneyConverter unit tests
 */
public class JourneyHistoryJourneyConverterImplTest {
    
    private JourneyHistoryJourneyConverterImpl journeyConverter;
    
    @Before
    public void setUp(){
        journeyConverter = new JourneyHistoryJourneyConverterImpl();
    }
    
    @Test
    public void shouldConvertModelToDto() {
        JourneyHistoryJourneyConverterImpl converter = new JourneyHistoryJourneyConverterImpl();
        converter.journeyHistoryTapConverter = new JourneyHistoryTapConverterImpl();
        JourneyDTO expectedResult = getTestJourneyDTO1();
        JourneyDTO result = converter.convertModelToDto(getTestJourney1());
        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldConvertDtoToRecord() {
        JourneyHistoryJourneyConverterImpl converter = new JourneyHistoryJourneyConverterImpl();
        String[] expectedResult =
                new String[]{"20/08/2013", "17:23", "18:04", "Mornington Crescent to Piccadilly Circus", "12.34", "", "0.28",
                        ""};
        String[] result = converter.convertDtoToRecord(getTestJourneyDTO1());
        assertArrayEquals(expectedResult, result);
    }
    
    @Test
    public void getJourneyDescriptionForUnStartedJourneyTest(){
        assertEquals(EXPECTED_JOURNEY_DESCRIPTION_UNSTARTED, journeyConverter.getJourneyDescription(getTestJourneyDTO3()));
    }
    
    @Test
    public void getJourneyDescriptionForUnFinishedJourneyTest(){
        assertEquals(EXPECTED_JOURNEY_DESCRIPTION_UNFINISHED, journeyConverter.getJourneyDescription(getTestJourneyDTO4()));
    }
    
    @Test
    public void getJourneyDescriptionShouldNotUpdateJourneyTest(){
        assertEquals(JOURNEY_DESCRIPTION_1, journeyConverter.getJourneyDescription(getTestJourneyDTO1()));
    }
}
