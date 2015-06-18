package com.novacroft.nemo.tfl.common.application_service.impl.journey_history;

import static com.novacroft.nemo.test_support.DateTestUtil.getAug19;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug20;
import static com.novacroft.nemo.test_support.JourneyTestUtil.ADDED_S_V_BALANCE_1;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getTestJourneyDTO1;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getTestJourneyListDTO2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDayDTO;

/**
 * CollateJourneysService unit tests
 */
public class CollateJourneysServiceImplTest {

    @Test
    public void shouldInitialiseDay() {
        CollateJourneysServiceImpl service = new CollateJourneysServiceImpl();
        List<JourneyDayDTO> journeyDays = new ArrayList<JourneyDayDTO>();
        JourneyDayDTO result = service.initialiseDay(journeyDays, getAug19());
        assertEquals(getAug19(), result.getEffectiveTrafficOn());
        assertTrue(journeyDays.contains(result));
    }

    @Test
    public void shouldGetDayNotWithExistingDay() {
        CollateJourneysServiceImpl service = new CollateJourneysServiceImpl();
        List<JourneyDayDTO> journeyDays = new ArrayList<JourneyDayDTO>();
        JourneyDayDTO result = service.getDay(journeyDays, getAug19());
        assertEquals(getAug19(), result.getEffectiveTrafficOn());
    }

    @Test
    public void shouldGetDayWithExistingDay() {
        CollateJourneysServiceImpl service = new CollateJourneysServiceImpl();
        List<JourneyDayDTO> journeyDays = new ArrayList<JourneyDayDTO>();
        journeyDays.add(new JourneyDayDTO(getAug19(), new ArrayList<JourneyDTO>(), 0, 0));
        journeyDays.add(new JourneyDayDTO(getAug20(), new ArrayList<JourneyDTO>(), 0, 0));
        JourneyDayDTO result = service.getDay(journeyDays, getAug19());
        assertEquals(getAug19(), result.getEffectiveTrafficOn());
    }

    @Test
    public void shouldAddJourneyToDay() {
        JourneyDayDTO journeyDay = new JourneyDayDTO(getAug19(), new ArrayList<JourneyDTO>(), 0, 0);

        List<JourneyDayDTO> journeyDays = new ArrayList<JourneyDayDTO>();
        journeyDays.add(journeyDay);

        JourneyDTO journey = getTestJourneyDTO1();
        journey.getJourneyDisplay().setEffectiveTrafficOn(getAug19());

        CollateJourneysServiceImpl service = mock(CollateJourneysServiceImpl.class);
        doCallRealMethod().when(service).addJourneyToDay(anyList(), any(JourneyDTO.class));
        when(service.getDay(anyList(), eq(getAug19()))).thenReturn(journeyDay);

        service.addJourneyToDay(journeyDays, journey);

        assertEquals(ADDED_S_V_BALANCE_1, journeyDays.get(0).getDailyBalance());
    }

    @Test
    public void shouldCollateByDay() {
        CollateJourneysServiceImpl service = mock(CollateJourneysServiceImpl.class);
        when(service.collateByDay(anyList())).thenCallRealMethod();
        doNothing().when(service).addJourneyToDay(anyList(), any(JourneyDTO.class));

        service.collateByDay(getTestJourneyListDTO2());

        verify(service, times(getTestJourneyListDTO2().size())).addJourneyToDay(anyList(), any(JourneyDTO.class));
    }

    @Test
    public void testJourneyDayDTOExplanatoryFlagsFalse() {
        CollateJourneysServiceImpl service = new CollateJourneysServiceImpl();
        List<JourneyDayDTO> journeyDays = service.collateByDay(getTestJourneyListDTO2());
        JourneyDayDTO day = journeyDays.get(0);
        assertFalse(day.getContainsExplanatoryWarningFlag());
        assertFalse(day.getContainsExplanatoryCappingFlag());
        assertFalse(day.getContainsExplanatoryAutoCompleteFlag());
        assertFalse(day.getContainsExplanatoryManuallyCorrectedFlag());
        assertFalse(day.getMultipleExplanatory());

    }

    @Test
    public void testJourneyDayDTOWarningExplanatoryFlag() {
        CollateJourneysServiceImpl service = new CollateJourneysServiceImpl();
        List<JourneyDTO> journeys = getTestJourneyListDTO2();
        journeys.get(0).getJourneyDisplay().setWarning(Boolean.TRUE);
        List<JourneyDayDTO> journeyDays = service.collateByDay(journeys);
        JourneyDayDTO day = journeyDays.get(0);
        assertTrue(day.getContainsExplanatoryWarningFlag());
        assertFalse(day.getContainsExplanatoryCappingFlag());
        assertFalse(day.getContainsExplanatoryAutoCompleteFlag());
        assertFalse(day.getContainsExplanatoryManuallyCorrectedFlag());
        assertFalse(day.getMultipleExplanatory());

    }

    @Test
    public void testJourneyDayDTOWarningCappingExplanatoryFlags() {
        CollateJourneysServiceImpl service = new CollateJourneysServiceImpl();
        List<JourneyDTO> journeys = getTestJourneyListDTO2();
        journeys.get(0).getJourneyDisplay().setWarning(Boolean.TRUE);
        journeys.get(0).setDailyCappingFlag(Boolean.TRUE);
        List<JourneyDayDTO> journeyDays = service.collateByDay(journeys);
        JourneyDayDTO day = journeyDays.get(0);
        assertTrue(day.getContainsExplanatoryWarningFlag());
        assertTrue(day.getContainsExplanatoryCappingFlag());
        assertFalse(day.getContainsExplanatoryAutoCompleteFlag());
        assertFalse(day.getContainsExplanatoryManuallyCorrectedFlag());
        assertTrue(day.getMultipleExplanatory());

    }

}

