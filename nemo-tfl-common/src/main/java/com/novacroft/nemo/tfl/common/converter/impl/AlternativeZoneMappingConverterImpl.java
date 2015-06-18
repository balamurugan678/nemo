package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.AlternativeZoneMapping;
import com.novacroft.nemo.tfl.common.transfer.AlternativeZoneMappingDTO;
import org.springframework.stereotype.Component;

/**
 * Transform between alternative zone mapping entity and DTO.
 */
@Component(value = "alternativeZoneMappingConverter")
public class AlternativeZoneMappingConverterImpl
        extends BaseDtoEntityConverterImpl<AlternativeZoneMapping, AlternativeZoneMappingDTO> {
    @Override
    protected AlternativeZoneMappingDTO getNewDto() {
        return new AlternativeZoneMappingDTO();
    }
}
