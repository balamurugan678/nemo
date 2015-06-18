package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.PayAsYouGo;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoDTO;
import org.springframework.stereotype.Component;

/**
 * Transform between pay as you go entity and DTO.
 */
@Component(value = "payAsYouGoConverter")
public class PayAsYouGoConverterImpl extends BaseDtoEntityConverterImpl<PayAsYouGo, PayAsYouGoDTO> {
    @Override
    protected PayAsYouGoDTO getNewDto() {
        return new PayAsYouGoDTO();
    }
}
