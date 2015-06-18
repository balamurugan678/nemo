package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.AutoTopUpPerformedSettlement;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpPerformedSettlementDTO;

/**
 * Settlement entity/DTO translator
 */
@Component(value = "autoTopUpPerformedSettlementConverter")
public class AutoTopUpPerformedSettlementConverterImpl extends
                BaseDtoEntityConverterImpl<AutoTopUpPerformedSettlement, AutoTopUpPerformedSettlementDTO> {
    @Override
    protected AutoTopUpPerformedSettlementDTO getNewDto() {
        return new AutoTopUpPerformedSettlementDTO();
    }
}
