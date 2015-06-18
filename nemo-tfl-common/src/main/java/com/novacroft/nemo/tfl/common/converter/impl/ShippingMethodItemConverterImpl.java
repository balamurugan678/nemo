package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.ShippingMethodItem;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodItemDTO;
import org.springframework.stereotype.Component;

/**
 * Transform between shipping method item entity and DTO.
 */
@Component(value = "shippingMethodItemConverter")
public class ShippingMethodItemConverterImpl extends BaseDtoEntityConverterImpl<ShippingMethodItem, ShippingMethodItemDTO> {
    @Override
    protected ShippingMethodItemDTO getNewDto() {
        return new ShippingMethodItemDTO();
    }
}
