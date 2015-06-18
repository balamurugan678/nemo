package com.novacroft.nemo.tfl.services.controller;

import com.novacroft.nemo.tfl.services.application_service.StationService;
import com.novacroft.nemo.tfl.services.application_service.impl.StationServiceImpl;
import com.novacroft.nemo.tfl.services.transfer.Station;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * StationController unit tests
 */
public class StationControllerTest {
    
    private StationController controller;
    private StationService mockStationService;
    
    @Before
    public void setUp() {
    	controller = new StationController();
        this.mockStationService = mock(StationServiceImpl.class);
        controller.stationService = mockStationService;
    }

    @Test
    public void shouldGetStations() {
    	
        when(this.mockStationService.getStations()).thenReturn(Collections.<Station> emptyList());
        List<Station> stationList = this.controller.getStations();
        assertNotNull(stationList);
        verify(this.mockStationService).getActiveStations();
    }
    
    @Test
    public void shouldGetAllStations() {
        when(this.mockStationService.getStations()).thenReturn(Collections.<Station> emptyList());
        List<Station> stationList = this.controller.getAllStations();
        assertNotNull(stationList);
        verify(this.mockStationService).getStations();
    }
}
