package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.LocationTestUtil.LOCATION_NAME_1;
import static com.novacroft.nemo.test_support.LocationTestUtil.getTestLocationDTO1;
import static com.novacroft.nemo.test_support.OrderTestUtil.ORDER_ID;
import static com.novacroft.nemo.test_support.SettlementTestUtil.REQUEST_SEQUENCE_NUMBER;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.ApplicationEventService;
import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.tfl.common.data_service.AdHocLoadSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;

public class BaseAdHocLoadNotificationServiceTest {
    
    public static final String STATION_NAME = "Stanmore";
    public static final Integer LOCATION_ID = 1000;

    private BaseAdHocLoadNotificationServiceImpl service;

    private AdHocLoadSettlementDataService mockAdHocLoadSettlementDataService;
    private ApplicationEventService mockApplicationEventService;
    private CustomerDataService mockCustomerDataService;

    private AdHocLoadSettlementDTO mockAdHocLoadSettlementDTO;
    private CustomerDTO mockCustomerDTO;
    private OrderDTO mockOrderDTO;
    private LocationDataService mockLocationDataService;
    private OrderDataService mockOrderDataService;
   

    @Before
    public void setUp() {
        this.service = mock(BaseAdHocLoadNotificationServiceImpl.class);

        this.mockAdHocLoadSettlementDataService = mock(AdHocLoadSettlementDataService.class);
        this.service.adHocLoadSettlementDataService = this.mockAdHocLoadSettlementDataService;

        this.mockApplicationEventService = mock(ApplicationEventService.class);
        this.service.applicationEventService = mockApplicationEventService;
        
        this.mockCustomerDataService = mock(CustomerDataService.class);
        this.service.customerDataService = mockCustomerDataService;

        this.mockOrderDataService = mock(OrderDataService.class);
        this.service.orderDataService = mockOrderDataService;


        this.mockLocationDataService = mock(LocationDataService.class);
        this.service.locationDataService = mockLocationDataService;

        this.mockAdHocLoadSettlementDTO = mock(AdHocLoadSettlementDTO.class);
        this.mockCustomerDTO = mock(CustomerDTO.class);
        this.mockOrderDTO = mock(OrderDTO.class);
        
    }

    @Test
    public void shouldCreateEvent() {
        doCallRealMethod().when(this.service).createEvent(any(AdHocLoadSettlementDTO.class), anyString(), anyString(), any(OrderDTO.class));
        when(this.service.buildAdditionalInformation(any(AdHocLoadSettlementDTO.class), anyString(), anyString())).thenReturn(EMPTY);
        doNothing().when(this.mockApplicationEventService).create(anyLong(), any(EventName.class), anyString());
        when(this.mockCustomerDTO.getId()).thenReturn(CustomerTestUtil.CUSTOMER_ID_1);

        this.service.createEvent(this.mockAdHocLoadSettlementDTO, OYSTER_NUMBER_1, STATION_NAME, this.mockOrderDTO);

        verify(this.service).buildAdditionalInformation(any(AdHocLoadSettlementDTO.class), anyString(), anyString());
        verify(this.mockApplicationEventService).create(anyLong(), any(EventName.class), anyString());
    }


    @Test
    public void shouldGetSettlement() {
        when(this.service.getSettlement(anyInt(), anyString())).thenCallRealMethod();
        when(this.mockAdHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(anyInt(), anyString()))
                .thenReturn(this.mockAdHocLoadSettlementDTO);
        this.service.getSettlement(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1);
        verify(this.mockAdHocLoadSettlementDataService).findByRequestSequenceNumberAndCardNumber(anyInt(), anyString());
    }

    @Test
    public void shouldGetOrder() {
        doCallRealMethod().when(this.service).getOrder(anyLong());
        when(this.mockOrderDataService.findById(anyLong())).thenReturn(this.mockOrderDTO);
        this.service.getOrder(ORDER_ID);
        verify(this.mockOrderDataService).findById(anyLong());
    }
    
    @Test
    public void shouldLookupLocationName() {
        doCallRealMethod().when(this.service).lookupLocationName(anyInt());
        when(this.mockLocationDataService.findById(anyLong())).thenReturn(getTestLocationDTO1());
        String locationName = this.service.lookupLocationName(LOCATION_ID);
        verify(this.mockLocationDataService).findById(anyLong());
        assertEquals(LOCATION_NAME_1, locationName);
    }
    
    @Test
    public void shouldGetCustomer() {
        doCallRealMethod().when(this.service).getCustomer(anyLong());
        when(this.mockCustomerDataService.findById(anyLong())).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
        CustomerDTO customer = this.service.getCustomer(CUSTOMER_ID_1);
    }

    @Test
    public void hasAdHocLoadBeenRequestedShouldReturnTrue() {
        when(this.service.hasAdHocLoadBeenRequested(anyInt(), anyString())).thenCallRealMethod();
        when(this.mockAdHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(anyInt(), anyString()))
        .thenReturn(this.mockAdHocLoadSettlementDTO);
        assertTrue(this.service.hasAdHocLoadBeenRequested(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1));
    }

    @Test
    public void hasAdHocLoadBeenRequestedShouldReturnFalse() {
        when(this.service.hasAdHocLoadBeenRequested(anyInt(), anyString())).thenCallRealMethod();
        when(this.mockAdHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(anyInt(), anyString()))
        .thenReturn(null);
        assertFalse(this.service.hasAdHocLoadBeenRequested(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1));
    }
    
    @Test
    public void isNotificationEmailRequiredShouldReturnTrue() {
        when(this.service.isNotificationEmailRequired(anyInt(), anyString())).thenCallRealMethod();
        when(this.service.getSettlement(anyInt(), anyString())).thenReturn(this.mockAdHocLoadSettlementDTO);
        when(this.mockAdHocLoadSettlementDTO.getPickUpNationalLocationCode()).thenReturn(LOCATION_ID);
        when(this.mockAdHocLoadSettlementDTO.getOrderId()).thenReturn(ORDER_ID);
        when(this.service.getOrder(anyLong())).thenReturn(this.mockOrderDTO);
        when(this.mockOrderDTO.getCustomerId()).thenReturn(CUSTOMER_ID_1);
        when(this.service.getCustomer(anyLong())).thenReturn(this.mockCustomerDTO);
        when(this.mockCustomerDTO.getExternalUserId()).thenReturn(null);
        assertTrue(this.service.isNotificationEmailRequired(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1));
    }

    @Test
    public void isNotificationEmailRequiredShouldReturnFalse() {
        when(this.service.isNotificationEmailRequired(anyInt(), anyString())).thenCallRealMethod();
        when(this.service.getSettlement(anyInt(), anyString())).thenReturn(this.mockAdHocLoadSettlementDTO);
        when(this.mockAdHocLoadSettlementDTO.getPickUpNationalLocationCode()).thenReturn(LOCATION_ID);
        when(this.mockAdHocLoadSettlementDTO.getOrderId()).thenReturn(ORDER_ID);
        when(this.service.getOrder(anyLong())).thenReturn(this.mockOrderDTO);
        when(this.mockOrderDTO.getCustomerId()).thenReturn(CUSTOMER_ID_1);
        when(this.service.getCustomer(anyLong())).thenReturn(this.mockCustomerDTO);
        when(this.mockCustomerDTO.getExternalUserId()).thenReturn(CUSTOMER_ID_1);
        assertFalse(this.service.isNotificationEmailRequired(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1));
    }
}