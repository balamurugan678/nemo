package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.ShippingMethod;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodDTO;
import org.springframework.stereotype.Component;

/**
 * Transform between shipping method entity and DTO.
 */
@Component(value = "shippingMethodConverter")
public class ShippingMethodConverterImpl extends BaseDtoEntityConverterImpl<ShippingMethod, ShippingMethodDTO> {
    @Override
    protected ShippingMethodDTO getNewDto() {
        return new ShippingMethodDTO();
    }
}
