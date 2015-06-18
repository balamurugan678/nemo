package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.AutoLoadChangeSettlement;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeSettlementDTO;
import org.springframework.stereotype.Component;

/**
 * Settlement entity/DTO translator
 */
@Component(value = "autoLoadChangeSettlementConverter")
public class AutoLoadChangeSettlementConverterImpl
        extends BaseDtoEntityConverterImpl<AutoLoadChangeSettlement, AutoLoadChangeSettlementDTO> {
    @Override
    protected AutoLoadChangeSettlementDTO getNewDto() {
        return new AutoLoadChangeSettlementDTO();
    }
}
