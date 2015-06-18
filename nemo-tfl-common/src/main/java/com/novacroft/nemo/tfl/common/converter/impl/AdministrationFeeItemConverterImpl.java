package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.AdministrationFeeItem;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import org.springframework.stereotype.Component;

/**
 * Transform between administration fee item entity and DTO.
 */
@Component(value = "administrationFeeItemConverter")
public class AdministrationFeeItemConverterImpl extends BaseDtoEntityConverterImpl<AdministrationFeeItem, AdministrationFeeItemDTO> {
    @Override
    protected AdministrationFeeItemDTO getNewDto() {
        return new AdministrationFeeItemDTO();
    }
}
