package com.novacroft.nemo.tfl.services.converter;

import com.novacroft.nemo.tfl.common.transfer.LocationDTO;
import com.novacroft.nemo.tfl.services.transfer.Station;

import java.util.List;

/**
 * Convert LocationDTO objects to Station objects
 */
public interface StationConverter {
    Station convert(LocationDTO locationDTO);

    List<Station> convert(List<LocationDTO> locationDTOList);
}
