package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.ZoneIdDescription;
import com.novacroft.nemo.tfl.common.transfer.ZoneIdDescriptionDTO;
import org.springframework.stereotype.Component;

/**
 * Transform between zone id description entity and DTO.
 */
@Component(value = "zoneIdDescriptionConverter")
public class ZoneIdDescriptionConverterImpl extends BaseDtoEntityConverterImpl<ZoneIdDescription, ZoneIdDescriptionDTO> {
    @Override
    protected ZoneIdDescriptionDTO getNewDto() {
        return new ZoneIdDescriptionDTO();
    }
}
