package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;

import static com.novacroft.nemo.test_support.LocationTestUtil.getTestLocationDTO1;
import static com.novacroft.nemo.test_support.LocationTestUtil.getTestLocationDTO2;
import static com.novacroft.nemo.test_support.LocationTestUtil.LOCATION_NAME_1;
import static com.novacroft.nemo.test_support.LocationTestUtil.LOCATION_NAME_2;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LocationSelectListServiceImplTest {
    private static final String EXPECTED_SELECT_LIST_NAME = "Locations";
    
    private LocationSelectListServiceImpl service;
    private LocationDataService mockLocationDataService;
    
    @Before
    public void setUp() {
        service = new LocationSelectListServiceImpl();
        mockLocationDataService = mock(LocationDataService.class);
        service.locationDataService = mockLocationDataService;
        service.selectListOptionMeaningComparator = new SelectListOptionMeaningComparator();
    }
    
    @Test
    public void getLocationSelectListShouldBeOrdered() {
        when(mockLocationDataService.findAllActiveLocations())
            .thenReturn(Arrays.asList(getTestLocationDTO1(), getTestLocationDTO2()));
        
        SelectListDTO actualSelectListDTO = service.getLocationSelectList();
        List<SelectListOptionDTO> options = actualSelectListDTO.getOptions();
        assertEquals(EXPECTED_SELECT_LIST_NAME, actualSelectListDTO.getName());
        assertEquals(LOCATION_NAME_2, options.get(0).getMeaning());
        assertEquals(LOCATION_NAME_1, options.get(1).getMeaning());
    }
}
