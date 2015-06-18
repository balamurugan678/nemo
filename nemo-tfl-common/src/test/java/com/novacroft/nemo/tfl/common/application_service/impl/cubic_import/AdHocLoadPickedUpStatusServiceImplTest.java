package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.LocationTestUtil.LOCATION_ID_1;
import static com.novacroft.nemo.test_support.LocationTestUtil.LOCATION_NAME_1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.REQUEST_SEQUENCE_NUMBER;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.LocationDTO;

public class AdHocLoadPickedUpStatusServiceImplTest {
    private AdHocLoadPickedUpStatusServiceImpl service;
    private LocationDataService mockLocationDataService;
    private AdHocLoadSettlementDTO mockAdHocLoadSettlementDTO;
    private LocationDTO mockLocationDTO;
    private ApplicationContext mockApplicationContext;

    @Before
    public void setUp() {
        this.service = mock(AdHocLoadPickedUpStatusServiceImpl.class);
        this.mockLocationDataService = mock(LocationDataService.class);
        this.service.locationDataService = mockLocationDataService;
        this.mockAdHocLoadSettlementDTO = mock(AdHocLoadSettlementDTO.class);
        this.mockLocationDTO = mock(LocationDTO.class);
        this.mockApplicationContext = mock(ApplicationContext.class);
        setField(this.service, "applicationContext", this.mockApplicationContext);
    }

    @Test
    public void shouldUpdateStatusToPickUpExpired() {
        doCallRealMethod().when(this.service).updateStatusToPickedUp(anyInt(), anyString());
        when(this.service.findSettlement(anyInt(), anyString())).thenReturn(this.mockAdHocLoadSettlementDTO);
        doNothing().when(this.service).createEvent(anyString(), any(AdHocLoadSettlementDTO.class), any(EventName.class));
        doNothing().when(this.service).updateStatus(any(AdHocLoadSettlementDTO.class), any(SettlementStatus.class));

        this.service.updateStatusToPickedUp(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1);

        verify(this.service).findSettlement(anyInt(), anyString());
        verify(this.service).createEvent(anyString(), any(AdHocLoadSettlementDTO.class), any(EventName.class));
        verify(this.service).updateStatus(any(AdHocLoadSettlementDTO.class), any(SettlementStatus.class));
    }

    @Test
    public void shouldBuildAdditionalInformation() {
        when(this.service.buildAdditionalInformation(anyString(), any(AdHocLoadSettlementDTO.class), anyString())).thenCallRealMethod();
        when(this.service.getLocationName(anyInt())).thenReturn(LOCATION_NAME_1);
        when(this.mockAdHocLoadSettlementDTO.getPickUpNationalLocationCode()).thenReturn(LOCATION_ID_1.intValue());
        when(this.service.getContent(anyString())).thenReturn("Item picked up : ");
        assertEquals("Item picked up : Oyster card number [100000000001]; picked up at [Mornington Crescent]",
                this.service.buildAdditionalInformation(OYSTER_NUMBER_1, this.mockAdHocLoadSettlementDTO, ""));
        verify(this.mockAdHocLoadSettlementDTO).getPickUpNationalLocationCode();
    }

    @Test
    public void shouldGetLocationName() {
        when(this.service.getLocationName(anyInt())).thenCallRealMethod();
        when(this.mockLocationDataService.findById(anyLong())).thenReturn(this.mockLocationDTO);
        when(this.mockLocationDTO.getName()).thenReturn(LOCATION_NAME_1);

        this.service.getLocationName(LOCATION_ID_1.intValue());

        verify(this.mockLocationDataService).findById(anyLong());
        verify(this.mockLocationDTO).getName();
    }

    @Test
    public void getLocationNameShouldReturnEmpty() {
        when(this.service.getLocationName(anyInt())).thenCallRealMethod();
        when(this.mockLocationDataService.findById(anyLong())).thenReturn(this.mockLocationDTO);
        when(this.mockLocationDTO.getName()).thenReturn(LOCATION_NAME_1);

        this.service.getLocationName(null);

        verify(this.mockLocationDataService, never()).findById(anyLong());
        verify(this.mockLocationDTO, never()).getName();
    }
}