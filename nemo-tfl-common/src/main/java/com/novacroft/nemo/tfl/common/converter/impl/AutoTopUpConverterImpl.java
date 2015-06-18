package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.AutoTopUp;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpDTO;

/**
 * Transform between auto top-up entity and DTO.
 */
@Component(value = "autoTopUpConverter")
public class AutoTopUpConverterImpl extends BaseDtoEntityConverterImpl<AutoTopUp, AutoTopUpDTO> {
    
    @Override
    protected AutoTopUpDTO getNewDto() {
        return new AutoTopUpDTO();
    }
}
