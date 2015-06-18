package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.Location;
import com.novacroft.nemo.tfl.common.transfer.LocationDTO;

/**
 * Location (station/NLC) data service specification NLC = National Location Code
 */
public interface LocationDataService extends BaseDataService<Location, LocationDTO> {
    List<LocationDTO> findAllActiveLocations();
    LocationDTO getActiveLocationById(int stationId);
}
