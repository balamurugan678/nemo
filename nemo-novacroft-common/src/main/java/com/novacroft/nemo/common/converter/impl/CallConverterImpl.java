package com.novacroft.nemo.common.converter.impl;

import com.novacroft.nemo.common.domain.Call;
import com.novacroft.nemo.common.transfer.CallDTO;
import org.springframework.stereotype.Component;

/**
 * Call entity/DTO converter
 */
@Component(value = "callConverter")
public class CallConverterImpl extends BaseDtoEntityConverterImpl<Call, CallDTO> {

    @Override
    protected CallDTO getNewDto() {
        return new CallDTO();
    }

}
