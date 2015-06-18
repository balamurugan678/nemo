package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.common.utils.Converter;
import com.novacroft.nemo.tfl.common.domain.AdHocLoadSettlement;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;

/**
 * Settlement entity/DTO translator
 */
@Component(value = "adHocLoadSettlementConverter")
public class AdHocLoadSettlementConverterImpl extends BaseDtoEntityConverterImpl<AdHocLoadSettlement, AdHocLoadSettlementDTO> {
    
    @Autowired
    protected ItemConverterImpl itemConverterImpl;
    @Override
    protected AdHocLoadSettlementDTO getNewDto() {
        return new AdHocLoadSettlementDTO();
    }
    
    public AdHocLoadSettlementDTO convertEntityToDto (AdHocLoadSettlement entity) {
        AdHocLoadSettlementDTO adHocLoadSettlementDTO =  (AdHocLoadSettlementDTO) Converter.convert(entity, getNewDto());
        if (entity.getItem() != null) {
            adHocLoadSettlementDTO.setItem(itemConverterImpl.convertEntityToDto(entity.getItem()));
        }
        return adHocLoadSettlementDTO;
    }
    
    public AdHocLoadSettlement convertDtoToEntity(AdHocLoadSettlementDTO dto, AdHocLoadSettlement entity){
        if(dto.getItem() != null){
            entity.setItem(itemConverterImpl.convertDtoToEntity(dto.getItem(), entity.getItem()));
        }
        return (AdHocLoadSettlement) Converter.convert(dto, entity);
    }
}
