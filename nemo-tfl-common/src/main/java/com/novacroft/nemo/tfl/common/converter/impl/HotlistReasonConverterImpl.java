package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.HotlistReason;
import com.novacroft.nemo.tfl.common.transfer.HotlistReasonDTO;

/**
 * Transform between hotlistReason entity and DTO.
 */
@Component(value = "hotlistReasonConverter")
public class HotlistReasonConverterImpl extends BaseDtoEntityConverterImpl<HotlistReason, HotlistReasonDTO> {
    @Override
    protected HotlistReasonDTO getNewDto() {
        return new HotlistReasonDTO();
    }
}
