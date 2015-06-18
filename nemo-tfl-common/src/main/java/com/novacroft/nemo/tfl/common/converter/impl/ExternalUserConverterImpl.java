package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.ExternalUser;
import com.novacroft.nemo.tfl.common.transfer.ExternalUserDTO;

/**
 * Transform between ExternalUser entity and DTO.
 */
@Component(value = "externalUserConverter")
public class ExternalUserConverterImpl extends BaseDtoEntityConverterImpl<ExternalUser, ExternalUserDTO> {
    @Override
    protected ExternalUserDTO getNewDto() {
        return new ExternalUserDTO();
    }
}
