package com.novacroft.nemo.tfl.common.application_service.impl.incomplete_journey_history;

import static com.novacroft.nemo.test_support.LocationTestUtil.LOCATION_NAME_1;
import static com.novacroft.nemo.test_support.LocationTestUtil.getTestLocationDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.transfer.JourneyCompletedRefundItemDTO;

public class CompletedJourneyAuditReferencesServiceImplTest {
    private static final Integer ACTIVE_STATION_ID = 99;
    private static final Integer INACTIVE_STATION_ID = 100;
    
    private CompletedJourneyAuditReferencesServiceImpl service;
    private LocationDataService mockLocationDataService;
    
    @Before
    public void setUp() {
        service = new CompletedJourneyAuditReferencesServiceImpl();
        mockLocationDataService = mock(LocationDataService.class);
        service.locationDataService = mockLocationDataService;
        
        when(mockLocationDataService.getActiveLocationById(ACTIVE_STATION_ID))
                .thenReturn(getTestLocationDTO1());
        when(mockLocationDataService.getActiveLocationById(INACTIVE_STATION_ID))
                .thenReturn(null);
    }
    
    @Test
    public void testResovleReferences() {
        List<JourneyCompletedRefundItemDTO> refundItemDtoList = Arrays.asList(createTestRefundItemDTO());
        service.resovleReferences(refundItemDtoList);
        JourneyCompletedRefundItemDTO resolvedDTO = refundItemDtoList.get(0);
        assertEquals(LOCATION_NAME_1, resolvedDTO.getStartExitStationName());
        assertNull(resolvedDTO.getPickUpStationName());
        assertNull(resolvedDTO.getProvidedStationName());
    }
    
    private JourneyCompletedRefundItemDTO createTestRefundItemDTO() {
        JourneyCompletedRefundItemDTO testDTO = new JourneyCompletedRefundItemDTO();
        testDTO.setStartExitStation(ACTIVE_STATION_ID);
        testDTO.setPickUpStation(INACTIVE_STATION_ID);
        testDTO.setProvidedStation(null);
        return testDTO;
    }
    
}
