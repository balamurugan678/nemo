package com.novacroft.nemo.tfl.common.application_service.impl.journey_history;

import static com.novacroft.nemo.common.utils.DateUtil.isBefore;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug22;
import static com.novacroft.nemo.test_support.JourneyHistoryResponseTestUtil.getJourneyHistoryResponseDTO1;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getTestJourneyDayDtoList2;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getTestJourneyHistoryDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.journey_history.CollateJourneysService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryCSVService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryPDFService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.ResolveJourneyReferencesService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.ResolveTapReferencesService;
import com.novacroft.nemo.tfl.common.command.impl.CompleteJourneyCommandImpl;
import com.novacroft.nemo.tfl.common.constant.PseudoTransactionType;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.OysterJourneyHistoryDataService;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDayDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDisplayDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyHistoryDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.TapDTO;
import com.novacroft.nemo.tfl.common.util.JourneyStatementDateUtil;

public class JourneyHistoryServiceImplTest {
    
    private static final int NINE = 9;
    private static final int TEN = 10;

    @Test
    public void shouldSortJourneysWithMostRecentFirst() {
        JourneyHistoryServiceImpl service = new JourneyHistoryServiceImpl();

        List<JourneyDTO> journeys = getJourneyHistoryResponseDTO1().getJourneys();
        service.sortJourneysWithMostRecentFirst(journeys);

        Date previousTrafficOn = null;

        for (JourneyDTO journeyDTO : journeys) {
            if (previousTrafficOn != null) {
                assertTrue(!isBefore(previousTrafficOn, journeyDTO.getTrafficOn()));
            }
            previousTrafficOn = journeyDTO.getTrafficOn();
        }
    }

    @Test
    public void shouldSortJourneyDaysWithMostRecentFirst() {
        JourneyHistoryServiceImpl service = mock(JourneyHistoryServiceImpl.class);
        doCallRealMethod().when(service).sortJourneyDaysWithMostRecentFirst(anyList());
        doNothing().when(service).sortJourneysWithMostRecentFirst(anyList());

        List<JourneyDayDTO> journeyDays = getTestJourneyDayDtoList2();
        service.sortJourneyDaysWithMostRecentFirst(journeyDays);

        Date previousEffectiveTrafficOn = null;

        for (JourneyDayDTO journeyDayDTO : journeyDays) {
            if (previousEffectiveTrafficOn != null) {
                assertTrue(!isBefore(previousEffectiveTrafficOn, journeyDayDTO.getEffectiveTrafficOn()));
            }
            previousEffectiveTrafficOn = journeyDayDTO.getEffectiveTrafficOn();
        }
    }

    @Test
    public void shouldGetJourneyHistoryAsCsv() {
        JourneyHistoryCSVService mockJourneyHistoryCSVService = mock(JourneyHistoryCSVService.class);
        when(mockJourneyHistoryCSVService.createCSV(anyList())).thenReturn(new byte[]{});

        JourneyHistoryServiceImpl service = new JourneyHistoryServiceImpl();
        service.journeyHistoryCSVService = mockJourneyHistoryCSVService;

        service.getJourneyHistoryAsCsv(getTestJourneyHistoryDTO1());

        verify(mockJourneyHistoryCSVService).createCSV(anyList());
    }

    @Test
    public void shouldGetJourneyHistoryAsPdf() {
        JourneyHistoryPDFService mockJourneyHistoryPDFService = mock(JourneyHistoryPDFService.class);
        when(mockJourneyHistoryPDFService.createPDF(any(JourneyHistoryDTO.class))).thenReturn(new byte[]{});

        JourneyHistoryServiceImpl service = new JourneyHistoryServiceImpl();
        service.journeyHistoryPDFService = mockJourneyHistoryPDFService;

        service.getJourneyHistoryAsPdf(getTestJourneyHistoryDTO1());

        verify(mockJourneyHistoryPDFService).createPDF(any(JourneyHistoryDTO.class));
    }

    @Test
    public void shouldGetJourneyHistory() {
        CardDataService mockCardDataService = mock(CardDataService.class);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());

        OysterJourneyHistoryDataService mockOysterJourneyHistoryDataService = mock(OysterJourneyHistoryDataService.class);
        when(mockOysterJourneyHistoryDataService
                .findByCardNumberForDateRangeForOnline(anyString(), any(Date.class), any(Date.class)))
                .thenReturn(getJourneyHistoryResponseDTO1());

        ResolveJourneyReferencesService mockResolveJourneyReferencesService = mock(ResolveJourneyReferencesService.class);
        doNothing().when(mockResolveJourneyReferencesService).resolveReferences(anyList());

        CollateJourneysService mockCollateJourneysService = mock(CollateJourneysService.class);
        when(mockCollateJourneysService.collateByDay(anyList())).thenReturn(Collections.EMPTY_LIST);

        JourneyHistoryServiceImpl service = mock(JourneyHistoryServiceImpl.class);
        when(service.getJourneyHistory(anyLong(), any(Date.class), any(Date.class))).thenCallRealMethod();
        doNothing().when(service).sortJourneyDaysWithMostRecentFirst(anyList());

        service.cardDataService = mockCardDataService;
        service.oysterJourneyHistoryDataService = mockOysterJourneyHistoryDataService;
        service.resolveJourneyReferencesService = mockResolveJourneyReferencesService;
        service.collateJourneysService = mockCollateJourneysService;

        service.getJourneyHistory(CARD_ID_1, getAug19(), getAug22());

        verify(mockCardDataService).findById(anyLong());
        verify(mockOysterJourneyHistoryDataService)
                .findByCardNumberForDateRangeForOnline(anyString(), any(Date.class), any(Date.class));
        verify(mockResolveJourneyReferencesService).resolveReferences(anyList());
        verify(mockCollateJourneysService).collateByDay(anyList());
        verify(service).sortJourneyDaysWithMostRecentFirst(anyList());
    }
 
    @Test
    public void shouldGetJourneyHistoryItem() {
        CardDataService mockCardDataService = mock(CardDataService.class);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());

        OysterJourneyHistoryDataService mockOysterJourneyHistoryDataService = mock(OysterJourneyHistoryDataService.class);
        when(mockOysterJourneyHistoryDataService
                .findByCardNumberForDateRangeForOnline(anyString(), any(Date.class), any(Date.class)))
                .thenReturn(getJourneyHistoryResponseDTO1());

        ResolveJourneyReferencesService mockResolveJourneyReferencesService = mock(ResolveJourneyReferencesService.class);
        doNothing().when(mockResolveJourneyReferencesService).resolveReferences(anyList());

        ResolveTapReferencesService mockTapReferencesService = mock(ResolveTapReferencesService.class);
        doNothing().when(mockTapReferencesService).resolveReferences(anyList());

        JourneyHistoryServiceImpl service = mock(JourneyHistoryServiceImpl.class);
        when(service.getJourneyHistoryItem(anyLong(), any(Date.class), any(Integer.class))).thenCallRealMethod();
        when(service.findJourneyById(any(Integer.class), anyList())).thenCallRealMethod();

        service.cardDataService = mockCardDataService;
        service.oysterJourneyHistoryDataService = mockOysterJourneyHistoryDataService;
        service.resolveJourneyReferencesService = mockResolveJourneyReferencesService;
        service.resolveTapReferencesService = mockTapReferencesService;

        JourneyDTO journey = service.getJourneyHistoryItem(CARD_ID_1, getAug19(), Integer.valueOf("623000"));

        verify(mockCardDataService).findById(anyLong());
        verify(mockOysterJourneyHistoryDataService)
                .findByCardNumberForDateRangeForOnline(anyString(), any(Date.class), any(Date.class));
        verify(mockResolveJourneyReferencesService).resolveReferences(anyList());
        assertEquals(journey.getPrestigeId().longValue(), 108055752);
        assertEquals(journey.getJourneyId().intValue(), 623000);
    }

    @Test
    public void testGetWeekRangeSelectListDTO() {
        JourneyHistoryService service = new JourneyHistoryServiceImpl();
        Date yesterday = DateUtil.addDaysToDate(new Date(), -1);
        Date startDate = JourneyStatementDateUtil.getStartOfWeek(yesterday);
        Date endDate = null;

        ApplicationContext applicationContext = mock(ApplicationContext.class);
        ReflectionTestUtils.setField(service, "applicationContext", applicationContext);
        when(applicationContext.getMessage(any(String.class), any(Object[].class), any(Locale.class)))
                .thenReturn("Mocked Content");

        SelectListDTO selectList = service.getWeekRangeSelectListDTO();

        if (JourneyStatementDateUtil.isMonday(new Date())) {
            assertEquals(NINE, selectList.getOptions().size());
        } else {
            assertEquals(TEN, selectList.getOptions().size());
        }
        //1st option, this week
        assertSelectListOptionDTO(selectList.getOptions().get(0), 0, "0", "Mocked Content");

        //2nd option, previous week
        startDate = DateUtil.addDaysToDate(startDate, -7);
        endDate = DateUtil.addDaysToDate(startDate, 6);
        String expectedMeaning = DateUtil.formatDate(startDate, JourneyHistoryServiceImpl.WEEK_RANGE_DATE_DISPLAY_FORMAT) +
                JourneyHistoryServiceImpl.WEEK_RANGE_DATE_DISPLAY_FORMAT_SEPARATOR +
                DateUtil.formatDate(endDate, JourneyHistoryServiceImpl.WEEK_RANGE_DATE_DISPLAY_FORMAT);
        assertSelectListOptionDTO(selectList.getOptions().get(1), 1, "1", expectedMeaning);

        //3rd option, two weeks ago
        startDate = DateUtil.addDaysToDate(startDate, -14);
        endDate = DateUtil.addDaysToDate(startDate, 6);
        expectedMeaning = DateUtil.formatDate(startDate, JourneyHistoryServiceImpl.WEEK_RANGE_DATE_DISPLAY_FORMAT) +
                JourneyHistoryServiceImpl.WEEK_RANGE_DATE_DISPLAY_FORMAT_SEPARATOR +
                DateUtil.formatDate(endDate, JourneyHistoryServiceImpl.WEEK_RANGE_DATE_DISPLAY_FORMAT);
        assertSelectListOptionDTO(selectList.getOptions().get(3), 3, "3", expectedMeaning);

        //8th option, custom period
        int last = selectList.getOptions().size();
        assertSelectListOptionDTO(selectList.getOptions().get(last-1), 10, "10", "Mocked Content");

    }

    @Test
    public void resolveLinkedStationAndTransactionReturnsLastTapDataForMissingTransactionLocation() {
        JourneyDTO journeyDTO = new JourneyDTO();
        journeyDTO.setPseudoTransactionTypeId(PseudoTransactionType.UNFINISHED_JOURNEY.pseudoTransactionTypeId());

        CompleteJourneyCommandImpl completeJourneyCommandImpl = new CompleteJourneyCommandImpl();
        completeJourneyCommandImpl.setJourney(journeyDTO);

        JourneyDisplayDTO journeyDisplayDTO = new JourneyDisplayDTO();
        journeyDisplayDTO.setExitLocationName("Test");
        journeyDTO.setJourneyDisplay(journeyDisplayDTO);

        TapDTO firstTapDto = new TapDTO();
        Date firstTapsetLinkedTransactionTime = new Date();
        firstTapDto.setNationalLocationCode(1);
        firstTapDto.setTransactionAt(firstTapsetLinkedTransactionTime);

        TapDTO secondTapDto = new TapDTO();
        Date secondTapsetLinkedTransactionTime = new Date();
        secondTapDto.setNationalLocationCode(2);
        secondTapDto.setTransactionAt(secondTapsetLinkedTransactionTime);

        journeyDTO.setTaps(new ArrayList<TapDTO>());
        journeyDTO.getTaps().add(firstTapDto);
        journeyDTO.getTaps().add(secondTapDto);

        JourneyHistoryServiceImpl service = new JourneyHistoryServiceImpl();

        service.resolveLinkedStationAndTransaction(completeJourneyCommandImpl);

        assertTrue(secondTapDto.getNationalLocationCode().equals(completeJourneyCommandImpl.getLinkedStationKey()));
        assertTrue(secondTapDto.getTransactionAt().equals(completeJourneyCommandImpl.getLinkedTransactionTime()));

    }

    @Ignore
    public void resolveLinkedStationAndTransactionReturnsFirstTapDataForMissingExit() {
        JourneyDTO journeyDTO = new JourneyDTO();
        journeyDTO.setPseudoTransactionTypeId(PseudoTransactionType.UNFINISHED_JOURNEY.pseudoTransactionTypeId());

        CompleteJourneyCommandImpl completeJourneyCommandImpl = new CompleteJourneyCommandImpl();
        completeJourneyCommandImpl.setJourney(journeyDTO);

        JourneyDisplayDTO journeyDisplayDTO = new JourneyDisplayDTO();
        journeyDisplayDTO.setTransactionLocationName("Test");
        journeyDTO.setJourneyDisplay(journeyDisplayDTO);

        TapDTO firstTapDto = new TapDTO();
        Date firstTapsetLinkedTransactionTime = new Date();
        firstTapDto.setNationalLocationCode(1);
        firstTapDto.setTransactionAt(firstTapsetLinkedTransactionTime);

        TapDTO secondTapDto = new TapDTO();
        Date secondTapsetLinkedTransactionTime = new Date();
        secondTapDto.setNationalLocationCode(2);
        secondTapDto.setTransactionAt(secondTapsetLinkedTransactionTime);

        journeyDTO.setTaps(new ArrayList<TapDTO>());
        journeyDTO.getTaps().add(firstTapDto);
        journeyDTO.getTaps().add(secondTapDto);

        JourneyHistoryServiceImpl service = new JourneyHistoryServiceImpl();

        service.resolveLinkedStationAndTransaction(completeJourneyCommandImpl);

        assertTrue(firstTapDto.getNationalLocationCode().equals(completeJourneyCommandImpl.getLinkedStationKey()));
        assertTrue(firstTapDto.getTransactionAt().equals(completeJourneyCommandImpl.getLinkedTransactionTime()));

    }

    private void assertSelectListOptionDTO(SelectListOptionDTO option, int index, String value, String meaning) {
        assertTrue(index == option.getDisplayOrder());
        assertEquals(value, option.getValue());
        assertEquals(meaning, option.getMeaning());
    }

}
