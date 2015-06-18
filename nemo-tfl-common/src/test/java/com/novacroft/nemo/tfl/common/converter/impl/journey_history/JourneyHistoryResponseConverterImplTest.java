package com.novacroft.nemo.tfl.common.converter.impl.journey_history;

import com.novacroft.tfl.web_service.model.oyster_journey_history.ArrayOfJourney;
import com.novacroft.tfl.web_service.model.oyster_journey_history.GetHistoryResponse;
import com.novacroft.tfl.web_service.model.oyster_journey_history.Journey;
import org.junit.Test;

import static com.novacroft.nemo.test_support.JourneyHistoryResponseTestUtil.getTestGetHistoryResponse1;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getTestJourneyDTO1;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

/**
 * JourneyHistoryResponseConverter unit tests
 */
public class JourneyHistoryResponseConverterImplTest {
    @Test
    public void shouldConvertModelToDto() {
        JourneyHistoryJourneyConverterImpl journeyHistoryJourneyConverter = mock(JourneyHistoryJourneyConverterImpl.class);
        when(journeyHistoryJourneyConverter.convertModelToDto(any(Journey.class))).thenReturn(getTestJourneyDTO1());

        JourneyHistoryResponseConverterImpl converter = mock(JourneyHistoryResponseConverterImpl.class, CALLS_REAL_METHODS);
        setField(converter, "journeyHistoryJourneyConverter", journeyHistoryJourneyConverter);

        converter.convertModelToDto(getTestGetHistoryResponse1());

        verify(converter, atLeastOnce()).getResponse(any(GetHistoryResponse.class));
        verify(converter, atLeastOnce()).getJourneyList(any(ArrayOfJourney.class));
        verify(converter, atLeastOnce()).convertJourney(any(Journey.class));

        verify(journeyHistoryJourneyConverter, atLeastOnce()).convertModelToDto(any(Journey.class));
    }
}
