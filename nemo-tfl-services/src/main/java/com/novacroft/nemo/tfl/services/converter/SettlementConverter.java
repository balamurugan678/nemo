package com.novacroft.nemo.tfl.services.converter;

import java.util.List;

import com.novacroft.nemo.tfl.common.transfer.SettlementDTO;
import com.novacroft.nemo.tfl.services.transfer.Settlement;

public interface SettlementConverter {
    Settlement convertDTO(SettlementDTO settlementDTO);

    List<Settlement> convertDTOs(List<SettlementDTO> settlementDTOs);
}
