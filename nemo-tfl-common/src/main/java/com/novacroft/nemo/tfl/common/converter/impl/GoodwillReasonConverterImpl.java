package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.GoodwillReason;
import com.novacroft.nemo.tfl.common.transfer.GoodwillReasonDTO;
import org.springframework.stereotype.Component;

/**
 * Transform between pay as you go entity and DTO.
 */
@Component(value = "goodwillReasonConverter")
public class GoodwillReasonConverterImpl extends BaseDtoEntityConverterImpl<GoodwillReason, GoodwillReasonDTO> {
    @Override
    protected GoodwillReasonDTO getNewDto() {
        return new GoodwillReasonDTO();
    }
}
