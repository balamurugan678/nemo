package com.novacroft.nemo.tfl.services.converter.impl;

import com.novacroft.nemo.tfl.common.transfer.LocationDTO;
import com.novacroft.nemo.tfl.services.converter.StationConverter;
import com.novacroft.nemo.tfl.services.transfer.Station;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Convert LocationDTO objects to Station objects
 */
@Component("stationConverter")
public class StationConverterImpl implements StationConverter {
    @Override
    public Station convert(LocationDTO locationDTO) {
        return new Station(locationDTO.getId(), locationDTO.getName(), locationDTO.getStatus());
    }

    @Override
    public List<Station> convert(List<LocationDTO> locationDTOList) {
        List<Station> stationList = new ArrayList<Station>(locationDTOList.size());
        for (LocationDTO locationDTO : locationDTOList) {
            stationList.add(convert(locationDTO));
        }
        return stationList;
    }
}
