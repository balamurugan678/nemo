package com.novacroft.nemo.common.converter.impl;

import com.novacroft.nemo.common.domain.SystemParameter;
import com.novacroft.nemo.common.transfer.SystemParameterDTO;
import org.springframework.stereotype.Component;

/**
 * System Parameter entity/DTO converter
 */
@Component(value = "systemParameterConverter")
public class SystemParameterConverterImpl extends BaseDtoEntityConverterImpl<SystemParameter, SystemParameterDTO> {
    @Override
    protected SystemParameterDTO getNewDto() {
        return new SystemParameterDTO();
    }
}
