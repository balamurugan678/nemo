package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.tfl.common.converter.impl.LocationConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.LocationDAO;
import com.novacroft.nemo.tfl.common.domain.Location;
import com.novacroft.nemo.tfl.common.transfer.LocationDTO;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.novacroft.nemo.test_support.LocationTestUtil.LOCATION_NAME_1;
import static com.novacroft.nemo.test_support.LocationTestUtil.getTestLocation1;
import static com.novacroft.nemo.test_support.LocationTestUtil.getTestLocationList1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

/**
 * Shipping method item data service unit tests.
 */
public class LocationDataServiceTest {

    static final Logger logger = LoggerFactory.getLogger(LocationDataServiceTest.class);

    private LocationDataServiceImpl dataService;
    private LocationDAO mockDao;

    @Before
    public void setUp() {
        dataService = new LocationDataServiceImpl();
        mockDao = mock(LocationDAO.class);

        dataService.setConverter(new LocationConverterImpl());
        dataService.setDao(mockDao);
    }

    @Test
    public void findAllActiveLocationsFindLocations() {
        when(mockDao.findByExample((Location) anyObject())).thenReturn(getTestLocationList1());

        List<LocationDTO> resultsDTO = dataService.findAllActiveLocations();

        verify(mockDao, atLeastOnce()).findByExample((Location) anyObject());
        assertEquals(LOCATION_NAME_1, resultsDTO.get(0).getName());
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(dataService.getNewEntity());
    }
    
    @Test
    public void findLatestCreatedDateTimeShouldReturnNull() {
        when(mockDao.findByQuery(anyString())).thenReturn(new ArrayList<Date>());
        assertNull(dataService.findLatestCreatedDateTime());
    }
    
    @Test
    public void findLatestCreatedDateTimeShouldReturnDate() {
        Date mockDate = mock(Date.class);
        when(mockDao.findByQuery(anyString())).thenReturn(Arrays.asList(mockDate));
        assertEquals(mockDate, dataService.findLatestCreatedDateTime());
    }
    
    @Test
    public void findLatestModifiedDateTimeShouldReturnNull() {
        when(mockDao.findByQuery(anyString())).thenReturn(new ArrayList<Date>());
        assertNull(dataService.findLatestModifiedDateTime());
    }
    
    @Test
    public void findLatestModifiedDateTimeShouldReturnDate() {
        Date mockDate = mock(Date.class);
        when(mockDao.findByQuery(anyString())).thenReturn(Arrays.asList(mockDate));
        assertEquals(mockDate, dataService.findLatestModifiedDateTime());
    }
    
    @Test
    public void getActiveLocationByIdShouldReturnDTO() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyVararg())).thenReturn(getTestLocation1());
        LocationDTO actualDTO = dataService.getActiveLocationById(0);
        assertEquals(LOCATION_NAME_1, actualDTO.getName());
    }
}
