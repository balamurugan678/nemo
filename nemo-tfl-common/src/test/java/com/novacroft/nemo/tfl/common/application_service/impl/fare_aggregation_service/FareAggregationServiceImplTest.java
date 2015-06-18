package com.novacroft.nemo.tfl.common.application_service.impl.fare_aggregation_service;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.IncompleteJourneyTestUtil.getTestIncompleteJourneyNotificationDTO;
import static com.novacroft.nemo.test_support.JourneyTestUtil.TOTAL_SPENT_100;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getTestJourneyDayDTOWithSpent100;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history.IncompleteJourneyService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryService;
import com.novacroft.nemo.tfl.common.constant.incomplete_journey.AutoFillSSRNotificationStatus;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.FareAggregationDataService;
import com.novacroft.nemo.tfl.common.data_service.NotificationsStatusDataService;
import com.novacroft.nemo.tfl.common.data_service.OysterJourneyHistoryDataService;
import com.novacroft.nemo.tfl.common.transfer.fare_aggregation_engine.RecalculatedOysterChargeResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.IncompleteJourneyNotificationDTO;
import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.RefundOrchestrationResultDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDayDTO;

public class FareAggregationServiceImplTest {
    private static final Integer TEST_INT_ZERO = 0;
    
    private FareAggregationServiceImpl service;
    private CardDataService mockCardDataService;
    private IncompleteJourneyService mockIncompleteJourneyService;
    private OysterJourneyHistoryDataService mockOysterDataService;
    private JourneyHistoryService mockJourneyHistoryService;
    private FareAggregationDataService mockFareAggregationDataService;
    private NotificationsStatusDataService mockNotificationDataService;
    
    private RecalculatedOysterChargeResponseDTO mockResponseDTO;
    
    @Before
    public void setUp() {
        service = new FareAggregationServiceImpl();
        mockCardDataService = mock(CardDataService.class);
        mockIncompleteJourneyService = mock(IncompleteJourneyService.class);
        mockOysterDataService = mock(OysterJourneyHistoryDataService.class);
        mockJourneyHistoryService = mock(JourneyHistoryService.class);
        mockFareAggregationDataService = mock(FareAggregationDataService.class);
        mockNotificationDataService = mock(NotificationsStatusDataService.class);
        mockResponseDTO = mock(RecalculatedOysterChargeResponseDTO.class);
        
        service.cardDataService = mockCardDataService;
        service.incompleteJourneyService = mockIncompleteJourneyService;
        service.oysterJourneyHistoryDataService = mockOysterDataService;
        service.journeyHistoryService = mockJourneyHistoryService;
        service.fareAggregationDataService = mockFareAggregationDataService;
        service.notificationStatusDataService = mockNotificationDataService;
        
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockIncompleteJourneyService.getIncompleteJourneyNotification(anyLong(), any(Date.class), anyInt()))
                .thenReturn(getTestIncompleteJourneyNotificationDTO());
        when(mockOysterDataService.insertSyntheticTap(any(IncompleteJourneyNotificationDTO.class), anyString(), anyInt(), anyString(), anyBoolean()))
                .thenReturn(null);
        when(mockJourneyHistoryService.getJourneysForDay(anyLong(), any(Date.class)))
                .thenReturn(getTestJourneyDayDTOWithSpent100());
        when(mockFareAggregationDataService.getRecalculatedOysterCharge(any(JourneyDayDTO.class), anyString()))
                .thenReturn(mockResponseDTO);
        when(mockNotificationDataService.notifyAutoFillOfJourneySSRStatus(any(IncompleteJourneyNotificationDTO.class), anyString(), any(AutoFillSSRNotificationStatus.class)))
                .thenReturn(null);
    }
    
    @Test
    public void orchestrateServicesForRefundSubmissionFail() {
        when(mockResponseDTO.getDayPricePence()).thenReturn(null);
        
        RefundOrchestrationResultDTO actualResult = service.orchestrateServicesForRefundSubmission(CARD_ID_1, null, null, null, null, false);
        
        assertEquals(AutoFillSSRNotificationStatus.SSR_COMMENCED_BUT_FAILED, 
                        actualResult.getAutoFillSelfServiceRefundNotificationStatus());
        assertEquals(Integer.valueOf(0), actualResult.getRefundAmount());
    }
    
    @Test
    public void orchestrateServicesForRefundSubmissionSuccess() {
        when(mockResponseDTO.getDayPricePence()).thenReturn(TEST_INT_ZERO);
        
        RefundOrchestrationResultDTO actualResult = service.orchestrateServicesForRefundSubmission(CARD_ID_1, null, null, null, null, false);
        
        assertEquals(AutoFillSSRNotificationStatus.AUTOFILL_NOTIFIED_COMMENCED, 
                        actualResult.getAutoFillSelfServiceRefundNotificationStatus());
        assertEquals(TOTAL_SPENT_100, actualResult.getRefundAmount());
    }
}
