package com.novacroft.nemo.common.converter.impl;

import com.novacroft.nemo.common.domain.CallType;
import com.novacroft.nemo.common.transfer.CallTypeDTO;
import org.springframework.stereotype.Component;

/**
 * Call entity/DTO converter
 */
@Component(value = "callTypeConverter")
public class CallTypeConverterImpl extends BaseDtoEntityConverterImpl<CallType, CallTypeDTO> {

    @Override
    protected CallTypeDTO getNewDto() {
        return new CallTypeDTO();
    }

}
