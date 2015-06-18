package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.application_service.ServiceCallLogService;
import com.novacroft.nemo.common.converter.XmlModelConverter;
import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.common.transfer.ServiceCallLogDTO;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.tfl.common.application_service.cubic.CubicCardService;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.converter.CardUpdatePrePayTicketChangeConverter;
import com.novacroft.nemo.tfl.common.converter.CardUpdatePrePayValueChangeConverter;
import com.novacroft.nemo.tfl.common.converter.impl.*;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayTicketRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayValueRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdateResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;
import com.novacroft.nemo.tfl.common.service_access.cubic.CubicServiceAccess;
import com.novacroft.nemo.tfl.common.transfer.CardUpdatePrePayTicketRequestDTO;
import com.novacroft.nemo.tfl.common.transfer.CardUpdatePrePayValueRequestDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.*;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CS_CARDNUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class CardUpdateRequestDataServiceImplTest {

    private CardUpdateRequestDataServiceImpl service;
    private CardUpdateRequestDataServiceImpl mockService;
    private CubicCardService cubicCardService;
    private CustomerDataService mockCustomerDataService;
    private ServiceCallLogService mockServiceCallLogService;
    private NemoUserContext mockNemoUserContext;
    private CubicServiceAccess mockCubicServiceAccess;

    private CardUpdatePrePayTicketChangeConverter cardUpdatePrePayTicketChangeConverter;
    private XmlModelConverter<CardUpdatePrePayTicketRequest> cardUpdatePrePayTicketRequestConverter;
    private XmlModelConverter<CardUpdateResponse> cardUpdateResponseConverter;
    private XmlModelConverter<RequestFailure> requestFailureConverter;
    private CardUpdatePrePayTicketRequestConverterImpl cardUpdatePrePayTicketRequestConverterImpl;
    private CardUpdatePrePayTicketChangeConverterImpl cardUpdatePrePayTicketChangeConverterImpl;

    private CardUpdatePrePayValueChangeConverter cardUpdatePrePayValueChangeConverter;
    private XmlModelConverter<CardUpdatePrePayValueRequest> cardUpdatePrePayValueRequestConverter;
    private CardUpdatePrePayValueRequestConverterImpl cardUpdatePrePayValueRequestConverterImpl;
    private CardUpdatePrePayValueChangeConverterImpl cardUpdatePrePayValueChangeConverterImpl;

    private CardUpdateResponseConverterImpl cardUpdateResponseConverterImpl;
    private RequestFailureConverterImpl requestFailureConverterImpl;

    @Before
    public void setUp() throws Exception {
        service = new CardUpdateRequestDataServiceImpl();
        mockService = mock(CardUpdateRequestDataServiceImpl.class);
        cubicCardService = mock(CubicCardService.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        mockServiceCallLogService = mock(ServiceCallLogService.class);
        mockNemoUserContext = mock(NemoUserContext.class);
        mockCubicServiceAccess = mock(CubicServiceAccess.class);
        cardUpdatePrePayTicketChangeConverter = mock(CardUpdatePrePayTicketChangeConverter.class);

        service.cardUpdateResponseConverter = cardUpdateResponseConverter;
        service.cardUpdatePrePayTicketRequestConverter = cardUpdatePrePayTicketRequestConverter;
        service.requestFailureConverter = requestFailureConverter;
        service.cubicCardService = cubicCardService;
        service.customerDataService = mockCustomerDataService;
        service.serviceCallLogService = mockServiceCallLogService;
        service.nemoUserContext = mockNemoUserContext;
        service.cubicServiceAccess = mockCubicServiceAccess;

        mockService.cubicCardService = cubicCardService;
        mockService.cubicServiceAccess = mockCubicServiceAccess;

        setUpResourceMapping();
    }

    protected void setUpResourceMapping() {

        Resource testMappingResource = new ClassPathResource("nemo-tfl-common-castor-mapping.xml");

        ApplicationContext mockApplicationContext = mock(ApplicationContext.class);
        when(mockApplicationContext.getResource(anyString())).thenReturn(testMappingResource);
        when(mockServiceCallLogService.initialiseCallLog(anyString(), anyString(), anyLong()))
                .thenReturn(new ServiceCallLogDTO());

        cardUpdatePrePayTicketRequestConverterImpl = new CardUpdatePrePayTicketRequestConverterImpl();
        setField(cardUpdatePrePayTicketRequestConverterImpl, "applicationContext", mockApplicationContext);
        cardUpdatePrePayTicketRequestConverterImpl.initialiseMapping();

        cardUpdatePrePayTicketChangeConverterImpl = new CardUpdatePrePayTicketChangeConverterImpl();

        cardUpdateResponseConverterImpl = new CardUpdateResponseConverterImpl();
        setField(cardUpdateResponseConverterImpl, "applicationContext", mockApplicationContext);
        cardUpdateResponseConverterImpl.initialiseMapping();

        requestFailureConverterImpl = new RequestFailureConverterImpl();
        setField(requestFailureConverterImpl, "applicationContext", mockApplicationContext);
        requestFailureConverterImpl.initialiseMapping();

        cardUpdatePrePayValueRequestConverterImpl = new CardUpdatePrePayValueRequestConverterImpl();
        setField(cardUpdatePrePayValueRequestConverterImpl, "applicationContext", mockApplicationContext);
        cardUpdatePrePayValueRequestConverterImpl.initialiseMapping();

        cardUpdatePrePayValueChangeConverterImpl = new CardUpdatePrePayValueChangeConverterImpl();

        mockService.cardUpdatePrePayTicketRequestConverter = cardUpdatePrePayTicketRequestConverterImpl;
        mockService.cardUpdatePrePayTicketChangeConverter = cardUpdatePrePayTicketChangeConverterImpl;
        mockService.cardUpdatePrePayValueRequestConverter = cardUpdatePrePayValueRequestConverterImpl;
        mockService.cardUpdatePrePayValueChangeConverter = cardUpdatePrePayValueChangeConverterImpl;
        mockService.cardUpdateResponseConverter = cardUpdateResponseConverterImpl;
        mockService.requestFailureConverter = requestFailureConverterImpl;
        mockService.customerDataService = mockCustomerDataService;
        mockService.serviceCallLogService = mockServiceCallLogService;
        mockService.nemoUserContext = mockNemoUserContext;
    }

    @Test
    public void addPrePayTicketReturnSuccessResponse() {
        when(mockService.isSuccessResponse(anyObject())).thenReturn(Boolean.TRUE);
        when(mockService.addPrePayTicket(any(CardUpdatePrePayTicketRequestDTO.class))).thenCallRealMethod();
        when(mockCubicServiceAccess.callCubic(anyString()))
                .thenReturn(new StringBuffer(getTestCardUpdatePrePayTicketResponse()));
        assertEquals(getCardUpdateResponseDTO(), mockService.addPrePayTicket(getCardUpdatePrePayTicketRequestDTO1()));
    }

    @Test
    public void addPrePayTicketReturnErrorResponse() {
        when(mockService.isSuccessResponse(anyObject())).thenReturn(Boolean.FALSE);
        when(mockService.addPrePayTicket(any(CardUpdatePrePayTicketRequestDTO.class))).thenCallRealMethod();
        when(mockCubicServiceAccess.callCubic(anyString())).thenReturn(new StringBuffer(getTestRequestFailureXml()));
        assertEquals(getFailedCardUpdateResponseDTO(), mockService.addPrePayTicket(getCardUpdatePrePayTicketRequestDTO1()));
    }

    @Test
    public void addPrePayValueReturnSuccessResponse() {
        when(mockService.isSuccessResponse(anyObject())).thenReturn(Boolean.TRUE);
        when(mockService.addPrePayValue(any(CardUpdatePrePayValueRequestDTO.class), anyString())).thenCallRealMethod();
        when(mockCubicServiceAccess.callCubic(anyString()))
                .thenReturn(new StringBuffer(getTestCardUpdatePrePayTicketResponse()));
        assertEquals(getCardUpdateResponseDTO(),
                mockService.addPrePayValue(getCardUpdatePrePayValueRequestDTO1(), CartType.FAILED_CARD_REFUND.code()));
    }

    @Test
    public void addPrePayValueReturnErrorResponse() {
        when(mockService.isSuccessResponse(anyObject())).thenReturn(Boolean.FALSE);
        when(mockService.addPrePayValue(any(CardUpdatePrePayValueRequestDTO.class), anyString())).thenCallRealMethod();
        when(mockCubicServiceAccess.callCubic(anyString())).thenReturn(new StringBuffer(getTestRequestFailureXml()));
        assertEquals(getFailedCardUpdateResponseDTO(),
                mockService.addPrePayValue(getCardUpdatePrePayValueRequestDTO1(), CartType.FAILED_CARD_REFUND.code()));
    }

    @Test
    public void addPrePayValueShouldNotCallGetCustomerIdForAnonymousGoodwillRefund() {
        when(mockService.isSuccessResponse(anyObject())).thenReturn(Boolean.TRUE);
        when(mockService.addPrePayValue(any(CardUpdatePrePayValueRequestDTO.class), anyString())).thenCallRealMethod();
        when(mockCubicServiceAccess.callCubic(anyString()))
                .thenReturn(new StringBuffer(getTestCardUpdatePrePayTicketResponse()));
        assertEquals(getCardUpdateResponseDTO(),
                mockService.addPrePayValue(getCardUpdatePrePayValueRequestDTO1(), CartType.ANONYMOUS_GOODWILL_REFUND.code()));
        verify(mockService, never()).getCustomerId(anyString());
    }

    @Test
    public void getCustomerIdReturnCustomer() {
        when(mockCustomerDataService.findByCardNumber(anyString())).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
        assertEquals(CUSTOMER_ID_1, service.getCustomerId(CS_CARDNUMBER_1));
    }

    @Test
    public void isSuccessResponseShouldReturnTrue() {
        assertTrue(service.isSuccessResponse(new CardUpdateResponse()));
    }

    @Test
    public void isSuccessResponseShouldReturnFalse() {
        assertFalse(service.isSuccessResponse(new String()));
    }

    @Test
    public void getCustomerIdShouldReturnNull() {
        when(mockCustomerDataService.findByCardNumber(anyString())).thenReturn(null);
        assertNull(service.getCustomerId(CS_CARDNUMBER_1));
    }
}
