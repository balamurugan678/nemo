package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.AdministrationFee;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeDTO;
import org.springframework.stereotype.Component;

/**
 * Transform between administration fee entity and DTO.
 */
@Component(value = "administrationFeeConverter")
public class AdministrationFeeConverterImpl extends BaseDtoEntityConverterImpl<AdministrationFee, AdministrationFeeDTO> {
    @Override
    protected AdministrationFeeDTO getNewDto() {
        return new AdministrationFeeDTO();
    }
}
