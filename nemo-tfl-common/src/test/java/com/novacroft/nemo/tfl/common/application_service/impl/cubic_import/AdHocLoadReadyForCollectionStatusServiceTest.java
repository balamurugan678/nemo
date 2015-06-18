package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.REQUEST_SEQUENCE_NUMBER;
import static com.novacroft.nemo.test_support.SettlementTestUtil.getTestAdHocLoadSettlementDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.novacroft.nemo.common.application_service.ApplicationEventService;
import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.tfl.common.application_service.OrderService;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.AdHocLoadSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;

public class AdHocLoadReadyForCollectionStatusServiceTest {

    private AdHocLoadReadyForCollectionStatusServiceImpl service;
    private AdHocLoadSettlementDataService mockAdHocLoadSettlementDataService;
    private ApplicationEventService mockApplicationEventService;
    private OrderService mockOrderService;
    private ApplicationContext mockApplicationContext;


    @Before
    public void setUp() {
        this.service = mock(AdHocLoadReadyForCollectionStatusServiceImpl.class);

        this.mockAdHocLoadSettlementDataService = mock(AdHocLoadSettlementDataService.class);
        this.mockApplicationEventService = mock(ApplicationEventService.class);
        this.mockOrderService = mock(OrderService.class);
        
        this.service.adHocLoadSettlementDataService = this.mockAdHocLoadSettlementDataService;
        this.service.applicationEventService = this.mockApplicationEventService;
        this.service.orderService = this.mockOrderService;
        this.mockApplicationContext = mock(ApplicationContext.class);
        setField(this.service, "applicationContext", this.mockApplicationContext);
    }

    @Test
    public void shouldBuildAdditionalInformation() {
        when(this.service.buildAdditionalInformation(anyString(), any(AdHocLoadSettlementDTO.class), anyString())).thenCallRealMethod();
        when(this.service.getContent(anyString())).thenReturn("Item ready for pick up: ");
        final String expectedResult = "Item ready for pick up: amount [20.00]; pick up at []; Oyster card number [100000000001]; expires on [21/08/2013]";
        String result = service.buildAdditionalInformation(OYSTER_NUMBER_1, getTestAdHocLoadSettlementDTO1(), "");
        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldUpdateAdHocLoadSettlementStatus() {
        doCallRealMethod().when(this.service).updateAdHocLoadSettlementStatus(anyInt(), anyString());
        when(mockAdHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(anyInt(), anyString())).thenReturn(getTestAdHocLoadSettlementDTO1());
        doNothing().when(service).updateStatus(any(AdHocLoadSettlementDTO.class), any(SettlementStatus.class));
        doNothing().when(service).updateOrderStatus(anyLong());
        doNothing().when(service).createEvent(anyString(), any(AdHocLoadSettlementDTO.class), any(EventName.class));
        service.updateAdHocLoadSettlementStatus(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1);
        verify(mockAdHocLoadSettlementDataService, atLeastOnce()).findByRequestSequenceNumberAndCardNumber(anyInt(), anyString());
        verify(service, atLeastOnce()).updateStatus(any(AdHocLoadSettlementDTO.class), any(SettlementStatus.class));
        verify(mockOrderService, atLeastOnce()).updateOrderStatus(anyLong());
        verify(service, atLeastOnce()).createEvent(anyString(), any(AdHocLoadSettlementDTO.class), any(EventName.class));
    }

    @Test
    public void instantiationTest(){
        assertNotNull(service = new AdHocLoadReadyForCollectionStatusServiceImpl());
    }
}
