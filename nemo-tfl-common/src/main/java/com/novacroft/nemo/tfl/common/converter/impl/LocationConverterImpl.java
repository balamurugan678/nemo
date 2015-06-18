package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.Location;
import com.novacroft.nemo.tfl.common.transfer.LocationDTO;
import org.springframework.stereotype.Component;

/**
 * Entity - Transfer Converter for Location
 */
@Component(value = "locationConverter")
public class LocationConverterImpl extends BaseDtoEntityConverterImpl<Location, LocationDTO> {
    @Override
    protected LocationDTO getNewDto() {
        return new LocationDTO();
    }
}
