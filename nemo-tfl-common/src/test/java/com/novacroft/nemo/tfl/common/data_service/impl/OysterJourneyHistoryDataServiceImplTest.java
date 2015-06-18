package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug21;
import static com.novacroft.nemo.test_support.IncompleteJourneyTestUtil.getTestIncompleteJourneyNotificationDTO;
import static com.novacroft.nemo.test_support.JourneyTestUtil.PRODUCT_EXPIRY_DATE_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.converter.journey_history.InsertSyntheticTapResponseConverter;
import com.novacroft.nemo.tfl.common.converter.journey_history.JourneyHistoryResponseConverter;
import com.novacroft.nemo.tfl.common.service_access.OysterJourneyHistoryServiceAccess;
import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.IncompleteJourneyNotificationDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.InsertSyntheticTapResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyHistoryResponseDTO;
import com.novacroft.tfl.web_service.model.oyster_journey_history.GetHistory;
import com.novacroft.tfl.web_service.model.oyster_journey_history.GetHistoryResponse;
import com.novacroft.tfl.web_service.model.oyster_journey_history.InsertSyntheticOysterTap;
import com.novacroft.tfl.web_service.model.oyster_journey_history.InsertSyntheticOysterTapResponse;

public class OysterJourneyHistoryDataServiceImplTest {
    private static final Integer TEST_MISSING_STATION_ID = 2;
    private static final Integer EXPECTED_DAY_BACK = 3;
    private static final Integer EXPECTED_BOOLEAN_FALSE_AS_INT = 0;
    private static final String TEST_MISSING_REASON = "test-reason";
    
    private OysterJourneyHistoryDataServiceImpl service;
    private OysterJourneyHistoryServiceAccess mockOysterJourneyHistoryServiceAccess;
    private JourneyHistoryResponseConverter mockJourneyHistoryResponseConverter;
    private InsertSyntheticTapResponseConverter mockInsertSyntheticTapResponseConverter;
    
    private GetHistoryResponse mockGetHistoryResponse;
    private JourneyHistoryResponseDTO mockJourneyHistoryResponseDTO;
    
    @Before
    public void setUp() {
        service = new OysterJourneyHistoryDataServiceImpl();
        mockOysterJourneyHistoryServiceAccess = mock(OysterJourneyHistoryServiceAccess.class);
        mockJourneyHistoryResponseConverter = mock(JourneyHistoryResponseConverter.class);
        mockInsertSyntheticTapResponseConverter = mock(InsertSyntheticTapResponseConverter.class);
        
        service.oysterJourneyHistoryServiceAccess = mockOysterJourneyHistoryServiceAccess;
        service.journeyHistoryResponseConverter = mockJourneyHistoryResponseConverter;
        service.insertSyntheticTapResponseConverter = mockInsertSyntheticTapResponseConverter;
        
        mockJourneyHistoryResponseDTO = mock(JourneyHistoryResponseDTO.class);
        mockGetHistoryResponse = mock(GetHistoryResponse.class);
    }
    
    @Test
    public void shouldFindByCardNumberForDateRangeForOnline() {
        doNothing().when(mockOysterJourneyHistoryServiceAccess).setOnlineTimeout();
        when(mockOysterJourneyHistoryServiceAccess.getHistory(any(GetHistory.class)))
                .thenReturn(mockGetHistoryResponse);
        when(mockJourneyHistoryResponseConverter.convertModelToDto(any(GetHistoryResponse.class)))
                .thenReturn(mockJourneyHistoryResponseDTO);
        
        assertEquals(mockJourneyHistoryResponseDTO, 
                        service.findByCardNumberForDateRangeForOnline(OYSTER_NUMBER_1, getAug19(), getAug21()));
        verify(mockOysterJourneyHistoryServiceAccess).setOnlineTimeout();
        verify(mockOysterJourneyHistoryServiceAccess).getHistory(any(GetHistory.class));
        verify(mockJourneyHistoryResponseConverter).convertModelToDto(mockGetHistoryResponse);
    }
    
    @Test
    public void shouldFindByCardNumberForDateRangeForBatch() {
        doNothing().when(mockOysterJourneyHistoryServiceAccess).setBatchTimeout();
        when(mockOysterJourneyHistoryServiceAccess.getHistory(any(GetHistory.class)))
                .thenReturn(mockGetHistoryResponse);
        when(mockJourneyHistoryResponseConverter.convertModelToDto(any(GetHistoryResponse.class)))
                .thenReturn(mockJourneyHistoryResponseDTO);
        
        assertEquals(mockJourneyHistoryResponseDTO, 
                        service.findByCardNumberForDateRangeForBatch(OYSTER_NUMBER_1, getAug19(), getAug21()));
        verify(mockOysterJourneyHistoryServiceAccess).setBatchTimeout();
        verify(mockOysterJourneyHistoryServiceAccess).getHistory(any(GetHistory.class));
        verify(mockJourneyHistoryResponseConverter).convertModelToDto(mockGetHistoryResponse);
    }
    
    @Test
    public void shouldInsertSyntheticTap() {
        OysterJourneyHistoryDataServiceImpl spyService = spy(service);
        
        InsertSyntheticOysterTap mockReturnInsertSyntheticOysterTap = mock(InsertSyntheticOysterTap.class);
        doReturn(mockReturnInsertSyntheticOysterTap)
                .when(spyService)
                .createSyntheticTapRequest(any(IncompleteJourneyNotificationDTO.class), anyString(), anyInt(), anyString(), anyBoolean());
        
        doNothing().when(mockOysterJourneyHistoryServiceAccess).setOnlineTimeout();
        
        InsertSyntheticOysterTapResponse mockResponse = mock(InsertSyntheticOysterTapResponse.class);
        when(mockOysterJourneyHistoryServiceAccess.insertSyntheticTap(any(InsertSyntheticOysterTap.class)))
                .thenReturn(mockResponse);
        
        InsertSyntheticTapResponseDTO mockDTO = mock(InsertSyntheticTapResponseDTO.class);
        when(mockInsertSyntheticTapResponseConverter.convertModelToDto(any(InsertSyntheticOysterTapResponse.class)))
                .thenReturn(mockDTO);
        
        assertEquals(mockDTO, 
                        spyService.insertSyntheticTap(null, OYSTER_NUMBER_1, TEST_MISSING_STATION_ID, TEST_MISSING_REASON, false));
        verify(mockOysterJourneyHistoryServiceAccess).setOnlineTimeout();
        verify(mockOysterJourneyHistoryServiceAccess).insertSyntheticTap(mockReturnInsertSyntheticOysterTap);
        verify(mockInsertSyntheticTapResponseConverter).convertModelToDto(mockResponse);
    }
    
    @Test
    public void shouldCreateRequestWithCorrectData() {
        GetHistory actualResult = service.createRequest(OYSTER_NUMBER_1, getAug19(), getAug21(), Boolean.TRUE);
        assertEquals(Long.valueOf(0), actualResult.getPrestigeId());
        assertEquals(PRODUCT_EXPIRY_DATE_1, actualResult.getStartDay());
        assertEquals(EXPECTED_DAY_BACK, actualResult.getDaysBack());
        assertEquals(EXPECTED_BOOLEAN_FALSE_AS_INT, actualResult.getLive());
        assertEquals(Boolean.FALSE, actualResult.isTrainingpurpose());
        assertEquals(Boolean.TRUE, actualResult.isIsHighPriority());
    }
    
    @Test
    public void shouldCreateSyntheticTapRequest() {
        InsertSyntheticOysterTap actualResult = 
                        service.createSyntheticTapRequest(getTestIncompleteJourneyNotificationDTO(), 
                                        OYSTER_NUMBER_1, TEST_MISSING_STATION_ID, TEST_MISSING_REASON, Boolean.TRUE);
        assertNotNull(actualResult);
    }
}
