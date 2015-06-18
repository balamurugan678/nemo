package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.CURRENCY;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.EXPIRY_DATE;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.PICKUP_LOCATION;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.PRESTIGE_ID;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.PRE_PAY_VALUE;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.PRODUCT_CODE;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.PRODUCT_PRICE;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.REQUEST_SEQUENCE_NUMBER;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.START_DATE;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getCardUpdateResponseDTO;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getFailedCardUpdateResponseDTO;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.novacroft.nemo.common.application_service.ApplicationEventService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.test_support.HotlistReasonTestUtil;
import com.novacroft.nemo.test_support.LocationTestUtil;
import com.novacroft.nemo.tfl.common.application_service.AutoLoadConfigurationChangePushToGateService;
import com.novacroft.nemo.tfl.common.constant.AutoLoadState;
import com.novacroft.nemo.tfl.common.constant.HotlistReasonTypes;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CardUpdateRequestDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.HotlistReasonDataService;
import com.novacroft.nemo.tfl.common.transfer.CardUpdatePrePayTicketRequestDTO;
import com.novacroft.nemo.tfl.common.transfer.CardUpdatePrePayValueRequestDTO;

public class CardUpdateServiceTest {
    private static final Integer EXPECTED_SEQUENCE_NUMBER_ZERO = 0;
    private static final String TEST_SYSTEM_PARAMETER_VALUE = "A";
    private static final String TEST_CONTENT = "Test";
    
    private CardUpdateServiceImpl service; 
    private CardUpdateRequestDataService mockCardUpdateRequestDataService;
    private SystemParameterService mockSystemParameterService;
    private AutoLoadConfigurationChangePushToGateService mockAutoLoadConfigurationChangePushToGateService;
    private CardDataService mockCardDataService;
    private CardUpdateServiceImpl mockCardUpdateService;
    private ApplicationEventService mockApplicationEventService;
    private CustomerDataService mockCustomerDataService;
    private HotlistReasonDataService mockHotlistReasonDataService;
    
    @Before
    public void setup(){
        mockCardUpdateRequestDataService = mock(CardUpdateRequestDataService.class);
        mockSystemParameterService = mock(SystemParameterService.class);
        mockAutoLoadConfigurationChangePushToGateService = mock(AutoLoadConfigurationChangePushToGateService.class);
        mockCardDataService = mock(CardDataService.class);
        mockCardUpdateService = mock(CardUpdateServiceImpl.class);
        mockApplicationEventService = mock(ApplicationEventService.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        mockHotlistReasonDataService = mock(HotlistReasonDataService.class);
        
        when(mockCardUpdateRequestDataService.addPrePayTicket(any(CardUpdatePrePayTicketRequestDTO.class))).thenReturn(getCardUpdateResponseDTO());
        when(mockCardUpdateRequestDataService.addPrePayValue(any(CardUpdatePrePayValueRequestDTO.class), anyString())).thenReturn(getCardUpdateResponseDTO());
        when(mockSystemParameterService.getParameterPurpose(anyString())).thenReturn(TEST_SYSTEM_PARAMETER_VALUE);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        
        when(mockCardUpdateService.getContent(anyString())).thenReturn(TEST_CONTENT);
        when(mockCardUpdateService.requestCardAutoLoadChange(anyLong(), anyLong(), anyInt())).thenCallRealMethod();
        
        when(mockCustomerDataService.findById(anyLong())).thenReturn(getTestCustomerDTO1());
        when(mockCustomerDataService.findByCardNumber(anyString())).thenReturn(getTestCustomerDTO1());
        
        doCallRealMethod().when(mockCardUpdateService).createApplicationEvent(anyLong(), anyLong(), anyInt(), anyLong());
        
        doNothing().when(mockApplicationEventService).create(anyLong(), any(EventName.class), anyString());

        service = new CardUpdateServiceImpl();
        service.cardUpdateRequestDataService = mockCardUpdateRequestDataService;
        service.systemParameterService = mockSystemParameterService;
        service.autoLoadConfigurationChangePushToGateService = mockAutoLoadConfigurationChangePushToGateService;
        service.cardDataService = mockCardDataService;
        service.applicationEventService = mockApplicationEventService;
        service.customerDataService = mockCustomerDataService;
        service.hotlistReasonDataService = mockHotlistReasonDataService;
        
        mockCardUpdateService.cardDataService = mockCardDataService;
        mockCardUpdateService.cardUpdateRequestDataService = mockCardUpdateRequestDataService;
        mockCardUpdateService.systemParameterService = mockSystemParameterService;
        mockCardUpdateService.autoLoadConfigurationChangePushToGateService = mockAutoLoadConfigurationChangePushToGateService;
        mockCardUpdateService.applicationEventService = mockApplicationEventService;
        mockCardUpdateService.customerDataService = mockCustomerDataService;
    }
    
    @Test
    public void shouldSendCardUpdatePrePayTicketRequest(){
        Integer result = service.requestCardUpdatePrePayTicket(PRESTIGE_ID, PRODUCT_CODE, START_DATE, EXPIRY_DATE, PRODUCT_PRICE, PICKUP_LOCATION);
        assertEquals(REQUEST_SEQUENCE_NUMBER, result);
    }
    
    @Test(expected = ApplicationServiceException.class)
    public void shouldSendReturnErrorCardUpdatePrePayTicketRequest(){
        when(mockCardUpdateRequestDataService.addPrePayTicket(any(CardUpdatePrePayTicketRequestDTO.class))).thenReturn(getFailedCardUpdateResponseDTO());
        service.requestCardUpdatePrePayTicket(PRESTIGE_ID, PRODUCT_CODE, START_DATE, EXPIRY_DATE, PRODUCT_PRICE, PICKUP_LOCATION);
    }
    
    @Test
    public void shouldSendCardUpdatePrePayValueRequest(){
        Integer result = service.requestCardUpdatePrePayValue(PRESTIGE_ID, PICKUP_LOCATION, PRE_PAY_VALUE, CURRENCY, null);
        assertEquals(REQUEST_SEQUENCE_NUMBER, result);
    }
    
    @Test(expected = ApplicationServiceException.class)
    public void shouldSendReturnErrorCardUpdatePrePayValueRequest(){
        when(mockCardUpdateRequestDataService.addPrePayValue(any(CardUpdatePrePayValueRequestDTO.class), anyString())).thenReturn(
                        getFailedCardUpdateResponseDTO());
        Integer result = service.requestCardUpdatePrePayValue(PRESTIGE_ID, PICKUP_LOCATION, PRE_PAY_VALUE, CURRENCY, null);
        assertEquals(EXPECTED_SEQUENCE_NUMBER_ZERO, result);
    }
    
    @Test
    public void shouldRequestCardAutoLoadChange() {
        when(mockCardUpdateService.requestCardAutoLoadChange(
                        CARD_ID_1, LocationTestUtil.LOCATION_ID_1, AutoLoadState.TOP_UP_AMOUNT_2.state(), CUSTOMER_ID_1)
            ).thenCallRealMethod();
        mockCardUpdateService.requestCardAutoLoadChange(CARD_ID_1, LocationTestUtil.LOCATION_ID_1, AutoLoadState.TOP_UP_AMOUNT_2.state(), CUSTOMER_ID_1);
    	verify(mockAutoLoadConfigurationChangePushToGateService)
    	    .requestAutoLoadConfigurationChange(anyString(), anyInt(), anyLong());
    }
    
    @Test
    public void shouldRequestCardAutoLoadChangeAndCreateEvent() {
        Integer result = mockCardUpdateService.requestCardAutoLoadChange(CARD_ID_1, LocationTestUtil.LOCATION_ID_1, AutoLoadState.TOP_UP_AMOUNT_2.state(), CUSTOMER_ID_1);
        assertEquals(EXPECTED_SEQUENCE_NUMBER_ZERO, result);
    }
    
    @Test
    public void shouldCreateApplicationEvent(){
        doNothing().when(this.mockApplicationEventService).create(anyLong(), any(EventName.class), anyString());
        mockCardUpdateService.createApplicationEvent(CARD_ID_1, LocationTestUtil.LOCATION_ID_1, AutoLoadState.TOP_UP_AMOUNT_2.state(), CUSTOMER_ID_1);
        verify(this.mockApplicationEventService).create(anyLong(), any(EventName.class), anyString());
    }
    
    @Test
    public void shouldCreateLostEventForHotlistedCard() {
        ArgumentCaptor<Long> customerIdCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<EventName> eventNameCaptor = ArgumentCaptor.forClass(EventName.class);
        ArgumentCaptor<String> additionalInformationCaptor = ArgumentCaptor.forClass(String.class);

        when(mockHotlistReasonDataService.findByDescription(anyString())).thenReturn(HotlistReasonTestUtil.getHotlistReasonDTO1());
        service.createLostOrStolenEventForHotlistedCard(OYSTER_NUMBER_1, HotlistReasonTypes.LOST_CARD.getCode());
        verify(mockApplicationEventService, times(2)).create(customerIdCaptor.capture(), eventNameCaptor.capture(),
                        additionalInformationCaptor.capture());

        assertEquals(CUSTOMER_ID_1, customerIdCaptor.getAllValues().get(0));
        assertEquals(EventName.OYSTER_CARD_LOST, eventNameCaptor.getAllValues().get(0));
        assertEquals(service.getAdditionalInformation(OYSTER_NUMBER_1, HotlistReasonTypes.LOST_CARD.getCode()), additionalInformationCaptor
                        .getAllValues().get(0));

        assertEquals(CUSTOMER_ID_1, customerIdCaptor.getAllValues().get(1));
        assertEquals(EventName.OYSTER_CARD_HOTLISTED, eventNameCaptor.getAllValues().get(1));
        assertEquals(service.getAdditionalInformation(OYSTER_NUMBER_1, null), additionalInformationCaptor.getAllValues().get(1));
    }
    
    @Test
    public void shouldCreateStolenEventForHotlistedCard() {
        ArgumentCaptor<Long> customerIdCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<EventName> eventNameCaptor = ArgumentCaptor.forClass(EventName.class);
        ArgumentCaptor<String> additionalInformationCaptor = ArgumentCaptor.forClass(String.class);
        
        when(mockHotlistReasonDataService.findByDescription(anyString())).thenReturn(HotlistReasonTestUtil.getHotlistReasonDTO3());
        service.createLostOrStolenEventForHotlistedCard(OYSTER_NUMBER_1, HotlistReasonTypes.STOLEN_CARD.getCode());
        verify(mockApplicationEventService, times(2))
            .create(customerIdCaptor.capture(), eventNameCaptor.capture(), additionalInformationCaptor.capture());
        
        assertEquals(CUSTOMER_ID_1, customerIdCaptor.getAllValues().get(0));
        assertEquals(EventName.OYSTER_CARD_STOLEN, eventNameCaptor.getAllValues().get(0));
        assertEquals(service.getAdditionalInformation(OYSTER_NUMBER_1, HotlistReasonTypes.STOLEN_CARD.getCode()), 
                        additionalInformationCaptor.getAllValues().get(0));
        
        assertEquals(CUSTOMER_ID_1, customerIdCaptor.getAllValues().get(1));
        assertEquals(EventName.OYSTER_CARD_HOTLISTED, eventNameCaptor.getAllValues().get(1));
        assertEquals(service.getAdditionalInformation(OYSTER_NUMBER_1, null), 
                        additionalInformationCaptor.getAllValues().get(1));
    }
    
    @Test
    public void shouldRequestCardAutoLoadChangeWithoutCustomerIdProvided() {
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockCustomerDataService.findById(any(Long.class))).thenReturn(getTestCustomerDTO1());
        mockCardUpdateService.requestCardAutoLoadChange(CARD_ID_1, PICKUP_LOCATION, AutoLoadState.TOP_UP_AMOUNT_2.state());
        verify(mockCardDataService, atLeastOnce()).findById(anyLong());
        verify(mockCustomerDataService).findById(any(Long.class));
        verify(mockCardUpdateService).requestCardAutoLoadChange(anyLong(), anyLong(), anyInt(), anyLong());
    }

    @Test
    public void getEventNameShouldReturnTransferedIfInputMatchesTransferredId() {
        when(mockHotlistReasonDataService.findByDescription(anyString())).thenReturn(HotlistReasonTestUtil.getHotlistReasonDTO4());
        assertEquals(EventName.OYSTER_CARD_TRANSFERRED, service.getEventName(new Integer(HotlistReasonTestUtil.HOTLIST_REASON_ID_3.intValue())));
    }
}
