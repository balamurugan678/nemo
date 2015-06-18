package com.novacroft.nemo.test_support;

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.tfl.common.domain.Location;
import com.novacroft.nemo.tfl.common.transfer.LocationDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Location (NLC = National Location Code) test fixtures and utilities
 */
public final class LocationTestUtil {
    public static final Long LOCATION_ID_1 = 99L;
    public static final String LOCATION_NAME_1 = "Mornington Crescent";
    public static final String LOCATION_STATUS_1 = "Active";
    public static final String LOCATION_DESCRIPTION_1 = "Testing";
    
    public static final Long LOCATION_ID_2 = 100L;
    public static final String LOCATION_NAME_2 = "Kingscross";
    public static final String LOCATION_STATUS_2 = "Active";

    public static List<Location> getTestLocationList1() {
        List<Location> locations = new ArrayList<Location>();
        locations.add(getTestLocation1());
        return locations;
    }

    public static List<LocationDTO> getTestLocationDTOList1() {
        List<LocationDTO> locationDTOs = new ArrayList<LocationDTO>();
        locationDTOs.add(getTestLocationDTO1());
        return locationDTOs;
    }

    public static Location getTestLocation1() {
        return getTestLocation(LOCATION_ID_1, LOCATION_NAME_1, LOCATION_STATUS_1);
    }
    
    public static Location getTestLocation(Long locationId, String name, String status) {
        Location location = new Location();
        location.setId(locationId);
        location.setName(name);
        location.setStatus(status);
        return location;
    }
    
    public static LocationDTO getTestLocationDTO1() {
        return getTestLocationDTO(LOCATION_ID_1, LOCATION_NAME_1, LOCATION_STATUS_1);
    }
    
    public static LocationDTO getTestLocationDTO2() {
        return getTestLocationDTO(LOCATION_ID_2, LOCATION_NAME_2, LOCATION_STATUS_2);
    }

    public static LocationDTO getTestLocationDTO(Long locationId, String name, String status) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(locationId);
        locationDTO.setName(name);
        locationDTO.setStatus(status);
        return locationDTO;
    }
    
    public static SelectListDTO getLocationSelectList() {
        SelectListDTO selectListDTO = new SelectListDTO();
        selectListDTO.setName(LOCATION_NAME_1);
        selectListDTO.setDescription(LOCATION_DESCRIPTION_1);
        return selectListDTO;
    }

    private LocationTestUtil() {
    }
}
