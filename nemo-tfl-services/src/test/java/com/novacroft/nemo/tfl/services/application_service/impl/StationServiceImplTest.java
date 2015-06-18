package com.novacroft.nemo.tfl.services.application_service.impl;

import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO18;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO4;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO6;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOWithPpvAndEmptyPptList;
import static com.novacroft.nemo.test_support.CardTestUtil.CARD_ID;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_4;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.LocationTestUtil.getTestLocationDTO1;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;

import com.novacroft.nemo.common.domain.cubic.PendingItems;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.tfl.common.application_service.impl.cubic.GetCardServiceImpl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.data_service.impl.LocationDataServiceImpl;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.services.converter.StationConverter;
import com.novacroft.nemo.tfl.services.converter.impl.StationConverterImpl;
import com.novacroft.nemo.tfl.services.test_support.TestSupportUtilities;
import com.novacroft.nemo.tfl.services.transfer.Station;

/**
 * StationService unit tests
 */
public class StationServiceImplTest {
    private StationServiceImpl service;
    private LocationDataService mockLocationDataService;
    private StationConverter mockStationConverter;
    private CardDataService mockCardDataService;
    private GetCardServiceImpl mockGetCardServiceImpl;
    private CustomerDataService mockCustomerDataService;

    @Before
    public void setUp() {
        service = mock(StationServiceImpl.class);
        this.mockLocationDataService = mock(LocationDataServiceImpl.class);
        this.mockStationConverter = mock(StationConverterImpl.class);
        this.mockCardDataService = mock(CardDataService.class);
        this.mockGetCardServiceImpl = mock(GetCardServiceImpl.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        service.locationDataService = mockLocationDataService;
        service.stationConverter = mockStationConverter;
        service.cardDataService = mockCardDataService;
        service.getCardService = mockGetCardServiceImpl;
        service.customerDataService = mockCustomerDataService;

        when(service.findStationForOutstandingOrder(anyLong(), anyLong())).thenCallRealMethod();
        when(service.isCardDTOHasPendingItems(anyString())).thenCallRealMethod();
        when(service.isCardHasAnyPendingItems(any(CardInfoResponseV2DTO.class))).thenCallRealMethod();
        when(service.isCardHasPptItems(any(PendingItems.class))).thenCallRealMethod();
        when(service.isCardHasPpvItems(any(PendingItems.class))).thenCallRealMethod();
        when(service.findStationId(any(List.class))).thenCallRealMethod();

    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldGetStations() {
        doCallRealMethod().when(service).getActiveStations();
        when(this.mockLocationDataService.findAllActiveLocations()).thenReturn(TestSupportUtilities.getTestLocationDTOList());
        when(this.mockStationConverter.convert(anyList())).thenReturn(Collections.EMPTY_LIST);

        this.service.getActiveStations();

        verify(this.mockLocationDataService).findAllActiveLocations();
        verify(this.mockStationConverter).convert(anyList());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldGetAllStations() {
        doCallRealMethod().when(service).getStations();
        when(this.mockLocationDataService.findAll()).thenReturn(TestSupportUtilities.getTestLocationDTOList());
        when(this.mockStationConverter.convert(anyList())).thenReturn(Collections.EMPTY_LIST);

        this.service.getStations();

        verify(this.mockLocationDataService).findAll();
        verify(this.mockStationConverter).convert(anyList());
    }

    @Test
    public void shouldFindStationForOutstandingOrderHasPptItems() {
        when(service.findStationForOutstandingOrder(anyLong(), anyLong())).thenCallRealMethod();
        when(mockCustomerDataService.getInternalIdFromExternalId(anyLong())).thenReturn(CustomerTestUtil.EXTERNAL_CUSTOMER_ID);
        CardDTO mockCardDTO = mock(CardDTO.class);
        when(mockCardDataService.findByCustomerIdAndExternalId(anyLong(), anyLong())).thenReturn(getTestCardDTO1());
        when(mockCardDTO.getCardNumber()).thenReturn(OYSTER_NUMBER_4);
        when(mockGetCardServiceImpl.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO6());
        when(mockLocationDataService.getActiveLocationById(anyInt())).thenReturn(getTestLocationDTO1());

        Station station = service.findStationForOutstandingOrder(CUSTOMER_ID_1, CARD_ID);

        assertNotNull(station);
        verify(mockLocationDataService).getActiveLocationById(anyInt());
    }

    @Test
    public void shouldFindStationForOutstandingOrderHasPpvItems() {
        when(service.findStationForOutstandingOrder(anyLong(), anyLong())).thenCallRealMethod();
        when(mockCustomerDataService.getInternalIdFromExternalId(anyLong())).thenReturn(CustomerTestUtil.EXTERNAL_CUSTOMER_ID);
        CardDTO mockCardDTO = mock(CardDTO.class);
        when(mockCardDataService.findByCustomerIdAndExternalId(anyLong(), anyLong())).thenReturn(getTestCardDTO1());
        when(mockCardDTO.getCardNumber()).thenReturn(OYSTER_NUMBER_4);
        when(mockGetCardServiceImpl.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTOWithPpvAndEmptyPptList());
        when(mockLocationDataService.getActiveLocationById(anyInt())).thenReturn(getTestLocationDTO1());

        Station station = service.findStationForOutstandingOrder(CUSTOMER_ID_1, CARD_ID);

        assertNotNull(station);
        verify(mockLocationDataService).getActiveLocationById(anyInt());
    }

    @Test
    public void shouldNotFindStationForOutstandingOrderWithNoPendingItems() {
        when(service.findStationForOutstandingOrder(anyLong(), anyLong())).thenCallRealMethod();
        CardDTO mockCardDTO = mock(CardDTO.class);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockCardDTO.getCardNumber()).thenReturn(OYSTER_NUMBER_4);
        when(mockGetCardServiceImpl.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO4());

        Station station = service.findStationForOutstandingOrder(CUSTOMER_ID_1, CARD_ID);

        assertNotNull(station);
        verify(mockLocationDataService, never()).getActiveLocationById(anyInt());
    }

    @Test
    public void shouldNotFindStationForOutstandingOrderIfCustomerNotFound() {
        when(service.findStationForOutstandingOrder(anyLong(), anyLong())).thenCallRealMethod();
        when(mockCustomerDataService.getInternalIdFromExternalId(anyLong())).thenReturn(null);
        when(service.getContent(anyString())).thenReturn("");

        Station station = service.findStationForOutstandingOrder(CUSTOMER_ID_1, CARD_ID);

        assertNotNull(station);
        assertNotNull(station.getErrors());
        verify(mockLocationDataService, never()).getActiveLocationById(anyInt());
    }

    @Test
    public void shouldNotFindStationForOutstandingOrderIfCardNotFound() {
        when(service.findStationForOutstandingOrder(anyLong(), anyLong())).thenCallRealMethod();
        when(mockCustomerDataService.getInternalIdFromExternalId(anyLong())).thenReturn(CustomerTestUtil.EXTERNAL_CUSTOMER_ID);
        when(mockCardDataService.findByCustomerIdAndExternalId(anyLong(), anyLong())).thenReturn(null);
        when(service.getContent(anyString())).thenReturn("");

        Station station = service.findStationForOutstandingOrder(CUSTOMER_ID_1, CARD_ID);

        assertNotNull(station);
        assertNotNull(station.getErrors());
    }

    @Test
    public void shouldNotFindStationForOutstandingOrderIfAnExceptionIsThrown() {
        when(service.findStationForOutstandingOrder(anyLong(), anyLong())).thenCallRealMethod();
        when(mockCustomerDataService.getInternalIdFromExternalId(anyLong())).thenReturn(CustomerTestUtil.EXTERNAL_CUSTOMER_ID);
        BDDMockito.willThrow(Exception.class).given(mockCardDataService).findByCustomerIdAndExternalId(anyLong(), anyLong());

        Station station = service.findStationForOutstandingOrder(CUSTOMER_ID_1, CARD_ID);

        assertNotNull(station);
        assertNotNull(station.getErrors());
    }

    @Test
    public void shouldReturnEmptyObjectIfNoOutstandingOrders() {
        CardDTO mockCardDTO = mock(CardDTO.class);
        when(mockCardDataService.findByCustomerIdAndExternalId(anyLong(), anyLong())).thenReturn(getTestCardDTO1());
        when(mockCardDTO.getCardNumber()).thenReturn(OYSTER_NUMBER_4);
        when(mockGetCardServiceImpl.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO18());

        when(service.findStationForOutstandingOrder(anyLong(), anyLong())).thenCallRealMethod();
        when(service.isCardDTOHasPendingItems(anyString())).thenReturn(false);

        Station station = service.findStationForOutstandingOrder(CUSTOMER_ID_1, CARD_ID);
        assertNotNull(station);
        assertNull(station.getErrors());
    }
    
}
