package com.novacroft.nemo.tfl.common.converter;

import com.novacroft.nemo.tfl.common.domain.cubic.AutoLoadRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.AutoLoadResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeRequestDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeResponseDTO;

/**
 * Auto Load Change DTO / service model converter
 */
public interface AutoLoadChangeConverter {
    AutoLoadRequest convertToModel(AutoLoadChangeRequestDTO autoLoadChangeRequestDTO);

    AutoLoadChangeResponseDTO convertToDto(AutoLoadResponse autoLoadResponse);

    AutoLoadChangeResponseDTO convertToDto(RequestFailure requestFailure);
}
