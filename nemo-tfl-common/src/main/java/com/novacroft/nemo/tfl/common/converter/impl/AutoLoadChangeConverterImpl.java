package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.tfl.common.converter.AutoLoadChangeConverter;
import com.novacroft.nemo.tfl.common.domain.cubic.AutoLoadRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.AutoLoadResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeRequestDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeResponseDTO;
import org.springframework.stereotype.Component;

import static com.novacroft.nemo.common.utils.Converter.convert;

/**
 * Auto Load Change DTO / service model converter
 */
@Component(value = "autoLoadChangeConverter")
public class AutoLoadChangeConverterImpl implements AutoLoadChangeConverter {
    @Override
    public AutoLoadRequest convertToModel(AutoLoadChangeRequestDTO autoLoadChangeRequestDTO) {
        AutoLoadRequest autoLoadRequest = new AutoLoadRequest();
        convert(autoLoadChangeRequestDTO, autoLoadRequest);
        autoLoadRequest.setPickupLocation(autoLoadChangeRequestDTO.getPickUpLocation().intValue());
        return autoLoadRequest;
    }

    @Override
    public AutoLoadChangeResponseDTO convertToDto(AutoLoadResponse autoLoadResponse) {
        AutoLoadChangeResponseDTO autoLoadChangeResponseDTO = new AutoLoadChangeResponseDTO();
        convert(autoLoadResponse, autoLoadChangeResponseDTO);
        autoLoadChangeResponseDTO.setPickUpLocation((long) autoLoadResponse.getPickupLocation());
        return autoLoadChangeResponseDTO;
    }

    @Override
    public AutoLoadChangeResponseDTO convertToDto(RequestFailure requestFailure) {
        AutoLoadChangeResponseDTO autoLoadChangeResponseDTO = new AutoLoadChangeResponseDTO();
        convert(requestFailure, autoLoadChangeResponseDTO);
        return autoLoadChangeResponseDTO;
    }
}
