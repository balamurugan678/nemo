package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.ApplicationEventTestUtil.ADDITIONAL_INFORMATION;
import static com.novacroft.nemo.test_support.AutoLoadConfigurationChangeTestUtil.TEST_AMOUNT_2;
import static com.novacroft.nemo.test_support.AutoLoadConfigurationChangeTestUtil.TEST_AUTO_LOAD_STATE_2;
import static com.novacroft.nemo.test_support.AutoLoadConfigurationChangeTestUtil.TEST_ERROR_DESCRIPTION_1;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.USERNAME_1;
import static com.novacroft.nemo.test_support.LocationTestUtil.LOCATION_ID_1;
import static com.novacroft.nemo.test_support.LocationTestUtil.LOCATION_NAME_1;
import static com.novacroft.nemo.test_support.LocationTestUtil.getTestLocationDTO1;
import static com.novacroft.nemo.test_support.OrderTestUtil.ORDER_ID;
import static com.novacroft.nemo.test_support.OrderTestUtil.getTestOrderDTO1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.REQUEST_SEQUENCE_NUMBER;
import static com.novacroft.nemo.test_support.SettlementTestUtil.getTestAutoLoadChangeSettlementDTO1;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;

import com.novacroft.nemo.common.application_service.impl.ApplicationEventServiceImpl;
import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.application_service.AutoLoadConfigurationChangePushToGateService;
import com.novacroft.nemo.tfl.common.data_service.AutoLoadChangeSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

/**
 * AutoTopUpConfigurationService unit tests
 */
public class AutoTopUpConfigurationServiceImplTest {
    private AutoTopUpConfigurationServiceImpl service;
    private AutoTopUpConfigurationServiceImpl mockService;
    private CardDataService mockCardDataService;
    private LocationDataService mockLocationDataService;    
    
    @Before
    public void setUp() {
        service = new AutoTopUpConfigurationServiceImpl();
        mockService = mock(AutoTopUpConfigurationServiceImpl.class);
        
        mockCardDataService = mock(CardDataService.class);
        mockLocationDataService = mock(LocationDataService.class);
        
        service.cardDataService = mockCardDataService;
        service.locationDataService = mockLocationDataService;

        mockService.cardDataService = mockCardDataService;
        mockService.locationDataService = mockLocationDataService;
    }
    
    @Test
    public void shouldBuildAdditionalInformation() {
        String expectedResult =
                "Auto Load Change Request; amount [2000]; pick up location [Mornington Crescent]; request sequence number " +
                        "[9876]; error [AUTO LOAD CHANGE CONFIG TEST ERROR]; Oyster card number [100000000001]";
        String result =
                service.buildAdditionalInformation(OYSTER_NUMBER_1, TEST_AMOUNT_2, LOCATION_NAME_1, REQUEST_SEQUENCE_NUMBER,
                        TEST_ERROR_DESCRIPTION_1);
        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldBuildAdditionalInformationWithoutError() {
        String expectedResult =
                "Auto Load Change Request; amount [2000]; pick up location [Mornington Crescent]; request sequence number " +
                        "[9876]; Oyster card number [100000000001]";
        String result =
                service.buildAdditionalInformation(OYSTER_NUMBER_1, TEST_AMOUNT_2, LOCATION_NAME_1, REQUEST_SEQUENCE_NUMBER);
        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldBuildAdditionalInformationWithoutRequestSequenceNumber() {
        String expectedResult =
                "Auto Load Change Request; amount [2000]; pick up location [Mornington Crescent]; error [AUTO LOAD CHANGE " +
                        "CONFIG TEST ERROR]; Oyster card number [100000000001]";
        String result =
                service.buildAdditionalInformation(OYSTER_NUMBER_1, TEST_AMOUNT_2, LOCATION_NAME_1, TEST_ERROR_DESCRIPTION_1);
        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldCreateEvent() {
        OrderDataService mockOrderDataService = mock(OrderDataService.class);
        when(mockOrderDataService.findById(anyLong())).thenReturn(getTestOrderDTO1());
        
        ApplicationEventServiceImpl mockApplicationEventService = mock(ApplicationEventServiceImpl.class);
        doNothing().when(mockApplicationEventService).create(anyLong(), any(EventName.class), anyString());

        service.orderDataService = mockOrderDataService;
        service.applicationEventService = mockApplicationEventService;

        service.createEvent(ORDER_ID, ADDITIONAL_INFORMATION);

        verify(mockOrderDataService).findById(anyLong());
        verify(mockApplicationEventService).create(anyLong(), any(EventName.class), anyString());
    }

    @Test
    public void shouldCreateSettlement() {
        AutoLoadChangeSettlementDataService mockAutoLoadChangeSettlementDataService =
                mock(AutoLoadChangeSettlementDataService.class);
        when(mockAutoLoadChangeSettlementDataService.createOrUpdate(any(AutoLoadChangeSettlementDTO.class)))
                .thenReturn(getTestAutoLoadChangeSettlementDTO1());

        service.autoLoadChangeSettlementDataService = mockAutoLoadChangeSettlementDataService;

        service.createSettlement(CARD_ID_1, ORDER_ID, TEST_AMOUNT_2, REQUEST_SEQUENCE_NUMBER, LOCATION_ID_1);

        verify(mockAutoLoadChangeSettlementDataService).createOrUpdate(any(AutoLoadChangeSettlementDTO.class));
    }

    @Test
    public void shouldPushToGateWithSuccess() {
        AutoLoadConfigurationChangePushToGateService mockAutoLoadConfigurationChangePushToGateService =
                mock(AutoLoadConfigurationChangePushToGateService.class);
        when(mockAutoLoadConfigurationChangePushToGateService
                .requestAutoLoadConfigurationChange(anyString(), anyInt(), anyLong())).thenReturn(REQUEST_SEQUENCE_NUMBER);

        service.autoLoadConfigurationChangePushToGateService = mockAutoLoadConfigurationChangePushToGateService;

        Integer result = service.pushToGate(getTestCardDTO1(), TEST_AUTO_LOAD_STATE_2, LOCATION_ID_1, ORDER_ID, TEST_AMOUNT_2,
                LOCATION_NAME_1);

        assertEquals(REQUEST_SEQUENCE_NUMBER, result);
        verify(mockAutoLoadConfigurationChangePushToGateService)
                .requestAutoLoadConfigurationChange(anyString(), anyInt(), anyLong());
    }

    @Test(expected = ApplicationServiceException.class)
    public void shouldPushToGateWithFail() {
        AutoLoadConfigurationChangePushToGateService mockAutoLoadConfigurationChangePushToGateService =
                mock(AutoLoadConfigurationChangePushToGateService.class);
        when(mockAutoLoadConfigurationChangePushToGateService
                .requestAutoLoadConfigurationChange(anyString(), anyInt(), anyLong()))
                .thenThrow(new ApplicationServiceException(TEST_ERROR_DESCRIPTION_1));

        AutoTopUpConfigurationServiceImpl service = mock(AutoTopUpConfigurationServiceImpl.class);
        when(service.pushToGate(any(CardDTO.class), anyInt(), anyLong(), anyLong(), anyInt(), anyString()))
                .thenCallRealMethod();
        doNothing().when(service).createEvent(anyLong(), anyString());
        service.autoLoadConfigurationChangePushToGateService = mockAutoLoadConfigurationChangePushToGateService;

        Integer result = service.pushToGate(getTestCardDTO1(), TEST_AUTO_LOAD_STATE_2, LOCATION_ID_1, ORDER_ID, TEST_AMOUNT_2,
                LOCATION_NAME_1);

        assertEquals(REQUEST_SEQUENCE_NUMBER, result);
        verify(mockAutoLoadConfigurationChangePushToGateService)
                .requestAutoLoadConfigurationChange(anyString(), anyInt(), anyLong());
        verify(service).createEvent(anyLong(), anyString());
    }

    @Test
    public void shouldChangeConfiguration() {
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockLocationDataService.findById(anyLong())).thenReturn(getTestLocationDTO1());

        doCallRealMethod().when(mockService).changeConfiguration(anyLong(), anyLong(), anyInt(), anyLong());
        when(mockService.pushToGate(any(CardDTO.class), anyInt(), anyLong(), anyLong(), anyInt(), anyString()))
                .thenReturn(REQUEST_SEQUENCE_NUMBER);
        doNothing().when(mockService).createSettlement(anyLong(), anyLong(), anyInt(), anyInt(), anyLong());
        doNothing().when(mockService).createEvent(anyLong(), anyString());

        mockService.changeConfiguration(anyLong(), anyLong(), anyInt(), anyLong());
        verify(mockCardDataService).findById(anyLong());
        verify(mockLocationDataService).findById(anyLong());
        verify(mockService).pushToGate(any(CardDTO.class), anyInt(), anyLong(), anyLong(), anyInt(), anyString());
        verify(mockService).createSettlement(anyLong(), anyLong(), anyInt(), anyInt(), anyLong());
        verify(mockService).createEvent(anyLong(), anyString());
    }

    @Test
    public void shouldNotChangeConfigurationIfPushToGateFails() {
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockLocationDataService.findById(anyLong())).thenReturn(getTestLocationDTO1());
        
        doCallRealMethod().when(mockService).changeConfiguration(anyLong(), anyLong(), anyInt(), anyLong());

        BDDMockito.willThrow(new ApplicationServiceException()).given(mockService).pushToGate(any(CardDTO.class), anyInt(), anyLong(), anyLong(), anyInt(), anyString());
        
        mockService.changeConfiguration(anyLong(), anyLong(), anyInt(), anyLong());
        verify(mockCardDataService, atLeastOnce()).findById(anyLong());
        verify(mockService, never()).createSettlement(anyLong(), anyLong(), anyInt(), anyInt(), anyLong());
        verify(mockService, never()).createEvent(anyLong(), anyString());
    }

}
