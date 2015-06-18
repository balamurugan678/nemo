package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.IncompleteJourneyTestUtil.getTestIncompleteJourneyNotificationDTO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.constant.incomplete_journey.AutoFillSSRNotificationStatus;
import com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification.IncompleteJourneyNotificationResponseConverter;
import com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification.IncompleteJourneyNotificationResponseDTO;
import com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification.NotifyAutoFillOfSSRStatusResponseConverter;
import com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification.NotifyAutoFillOfSSRStatusResponseDTO;
import com.novacroft.nemo.tfl.common.service_access.NotificationStatusServiceAccess;
import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.IncompleteJourneyNotificationDTO;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.GetIncompleteJourneyNotifications;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.GetIncompleteJourneyNotificationsResponse;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.NotifyAutoFillOfSSRStatus;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.NotifyAutoFillOfSSRStatusResponse;

public class NotificationsStatusDataServiceImplTest {
    private static final int TEST_DAYS = 3;
    
    private NotificationsStatusDataServiceImpl service;
    private NotificationStatusServiceAccess mockNotificationStatusServiceAccess;
    private IncompleteJourneyNotificationResponseConverter mockNotificationResponseConverter;
    private NotifyAutoFillOfSSRStatusResponseConverter mockNotifyAutoFillOfSSRStatusResponseConverter;
    
    @Before
    public void setUp() {
        service = new NotificationsStatusDataServiceImpl();
        
        mockNotificationStatusServiceAccess = mock(NotificationStatusServiceAccess.class);
        mockNotificationResponseConverter = mock(IncompleteJourneyNotificationResponseConverter.class);
        mockNotifyAutoFillOfSSRStatusResponseConverter = mock(NotifyAutoFillOfSSRStatusResponseConverter.class);
        
        service.notificationStatusServiceAccess = mockNotificationStatusServiceAccess;
        service.notificationResponseConverter = mockNotificationResponseConverter;
        service.notifyAutoFillOfSSRStatusResponseConverter = mockNotifyAutoFillOfSSRStatusResponseConverter;
    }
    
    @Test
    public void shouldFindByCardNumberForDaysRangeOnline() {
        GetIncompleteJourneyNotificationsResponse mockResponse = mock(GetIncompleteJourneyNotificationsResponse.class);
        when(mockNotificationStatusServiceAccess.getIncompleteJourneyNotifications(any(GetIncompleteJourneyNotifications.class)))
                .thenReturn(mockResponse);
        
        IncompleteJourneyNotificationResponseDTO mockDTO = mock(IncompleteJourneyNotificationResponseDTO.class);
        when(mockNotificationResponseConverter.convertModelToDto(any(GetIncompleteJourneyNotificationsResponse.class)))
                .thenReturn(mockDTO);
        
        assertEquals(mockDTO, service.findByCardNumberForDaysRangeOnline(OYSTER_NUMBER_1, TEST_DAYS));
        verify(mockNotificationStatusServiceAccess).getIncompleteJourneyNotifications(any(GetIncompleteJourneyNotifications.class));
        verify(mockNotificationResponseConverter).convertModelToDto(mockResponse);
    }
    
    @Test
    public void shouldNotifyAutoFillOfJourneySSRStatus() {
        NotificationsStatusDataServiceImpl spyService = spy(service);
        
        NotifyAutoFillOfSSRStatus mockStatus = mock(NotifyAutoFillOfSSRStatus.class);
        doReturn(mockStatus).when(spyService)
                .createNotifyAutoFillOfSSRStatus(any(IncompleteJourneyNotificationDTO.class), anyString(), any(AutoFillSSRNotificationStatus.class));
        
        NotifyAutoFillOfSSRStatusResponse mockResponse = mock(NotifyAutoFillOfSSRStatusResponse.class);
        when(mockNotificationStatusServiceAccess.notifyAutoFillOfSSRStatus(any(NotifyAutoFillOfSSRStatus.class)))
                .thenReturn(mockResponse);
        
        NotifyAutoFillOfSSRStatusResponseDTO mockDTO = mock(NotifyAutoFillOfSSRStatusResponseDTO.class);
        when(mockNotifyAutoFillOfSSRStatusResponseConverter.convertModelToDto(any(NotifyAutoFillOfSSRStatusResponse.class)))
                .thenReturn(mockDTO);
        
        assertEquals(mockDTO, spyService.notifyAutoFillOfJourneySSRStatus(null, OYSTER_NUMBER_1, null));
        verify(mockNotificationStatusServiceAccess).notifyAutoFillOfSSRStatus(mockStatus);
        verify(mockNotifyAutoFillOfSSRStatusResponseConverter).convertModelToDto(mockResponse);
    }
    
    @Test
    public void shouldCreateNotifyAutoFillOfSSRStatus() {
        assertNotNull(service.createNotifyAutoFillOfSSRStatus(getTestIncompleteJourneyNotificationDTO(), 
                        OYSTER_NUMBER_1, AutoFillSSRNotificationStatus.AUTOFILL_NOTIFIED_OF_COMPLETION));
    }
}
