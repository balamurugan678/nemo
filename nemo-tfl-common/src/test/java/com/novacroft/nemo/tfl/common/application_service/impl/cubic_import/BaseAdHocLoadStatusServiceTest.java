package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
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
import com.novacroft.nemo.tfl.common.application_service.OrderService;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.AdHocLoadSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;

public class BaseAdHocLoadStatusServiceTest {
    
    public static final Integer LOCATION_ID = 1000;
    private BaseAdHocLoadStatusService service;

    private AdHocLoadSettlementDataService mockAdHocLoadSettlementDataService;
    private ApplicationEventService mockApplicationEventService;
    private CustomerDataService mockCustomerDataService;

    private AdHocLoadSettlementDTO mockAdHocLoadSettlementDTO;
    private CustomerDTO mockCustomerDTO;
    private OrderDTO mockOrderDTO;
    private OrderService mockOrderService;
    private LocationDataService mockLocationDataService;

    @Before
    public void setUp() {
        this.service = mock(BaseAdHocLoadStatusService.class);

        this.mockAdHocLoadSettlementDataService = mock(AdHocLoadSettlementDataService.class);
        this.service.adHocLoadSettlementDataService = this.mockAdHocLoadSettlementDataService;

        this.mockApplicationEventService = mock(ApplicationEventService.class);
        this.service.applicationEventService = mockApplicationEventService;

        this.mockCustomerDataService = mock(CustomerDataService.class);
        this.service.customerDataService = mockCustomerDataService;

        this.mockAdHocLoadSettlementDTO = mock(AdHocLoadSettlementDTO.class);
        this.mockCustomerDTO = mock(CustomerDTO.class);
        this.mockOrderDTO = mock(OrderDTO.class);
        
        this.mockOrderService = mock(OrderService.class);
        this.service.orderService = this.mockOrderService;
        
        this.mockLocationDataService = mock(LocationDataService.class);
        this.service.locationDataService = mockLocationDataService;
    }

    @Test
    public void shouldCreateEvent() {
        doCallRealMethod().when(this.service).createEvent(anyString(), any(AdHocLoadSettlementDTO.class), any(EventName.class));
        when(this.service.buildAdditionalInformation(anyString(), any(AdHocLoadSettlementDTO.class), anyString())).thenReturn(EMPTY);
        doNothing().when(this.mockApplicationEventService).create(anyLong(), any(EventName.class), anyString());
        when(this.mockCustomerDataService.findByCardNumber(anyString())).thenReturn(this.mockCustomerDTO);
        when(this.mockCustomerDTO.getId()).thenReturn(CustomerTestUtil.CUSTOMER_ID_1);

        this.service.createEvent(OYSTER_NUMBER_1, this.mockAdHocLoadSettlementDTO, EventName.AD_HOC_LOAD_FAILED);

        verify(this.service).buildAdditionalInformation(anyString(), any(AdHocLoadSettlementDTO.class), anyString());
        verify(this.mockApplicationEventService).create(anyLong(), any(EventName.class), anyString());
        verify(this.mockCustomerDataService).findByCardNumber(anyString());
        verify(this.mockCustomerDTO).getId();
    }

    @Test
    public void shouldUpdateStatus() {
        doCallRealMethod().when(this.service).updateStatus(any(AdHocLoadSettlementDTO.class), any(SettlementStatus.class));
        doNothing().when(this.mockAdHocLoadSettlementDTO).setStatus(anyString());
        when(this.mockAdHocLoadSettlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class)))
                .thenReturn(this.mockAdHocLoadSettlementDTO);
        doNothing().when(service).updateOrderStatus(anyLong());

        this.service.updateStatus(this.mockAdHocLoadSettlementDTO, SettlementStatus.FAILED);

        verify(this.mockOrderService).updateOrderStatus(anyLong());
        verify(this.mockAdHocLoadSettlementDTO).setStatus(anyString());
        verify(this.mockAdHocLoadSettlementDataService).createOrUpdate(any(AdHocLoadSettlementDTO.class));
    }

    @Test
    public void shouldFindSettlement() {
        when(this.service.findSettlement(anyInt(), anyString())).thenCallRealMethod();
        when(this.mockAdHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(anyInt(), anyString()))
                .thenReturn(this.mockAdHocLoadSettlementDTO);
        this.service.findSettlement(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1);
        verify(this.mockAdHocLoadSettlementDataService).findByRequestSequenceNumberAndCardNumber(anyInt(), anyString());
    }

    @Test
    public void shouldUpdateOrderStatus() {
        doCallRealMethod().when(this.service).updateOrderStatus(anyLong());
        when(this.mockOrderService.updateOrderStatus(anyLong())).thenReturn(this.mockOrderDTO);
        this.service.updateOrderStatus(ORDER_ID);
        verify(this.mockOrderService).updateOrderStatus(anyLong());
    }

    @Test
    public void hasAdHocLoadBeenRequestedShouldReturnTrue() {
        when(this.service.hasAdHocLoadBeenRequested(anyInt(), anyString())).thenCallRealMethod();
        when(this.service.findSettlement(anyInt(), anyString())).thenReturn(this.mockAdHocLoadSettlementDTO);
        assertTrue(this.service.hasAdHocLoadBeenRequested(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1));
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
    public void hasAdHocLoadBeenRequestedShouldReturnFalse() {
        when(this.service.hasAdHocLoadBeenRequested(anyInt(), anyString())).thenCallRealMethod();
        when(this.service.findSettlement(anyInt(), anyString())).thenReturn(null);
        assertFalse(this.service.hasAdHocLoadBeenRequested(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1));
    }
}