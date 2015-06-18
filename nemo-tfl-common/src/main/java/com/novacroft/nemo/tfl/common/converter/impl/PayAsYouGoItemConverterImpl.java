package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.common.utils.Converter;
import com.novacroft.nemo.tfl.common.data_access.PayAsYouGoItemDAO;
import com.novacroft.nemo.tfl.common.domain.PayAsYouGoItem;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;

/**
 * Transform between pay as you go item entity and DTO.
 */
@Component(value = "payAsYouGoItemConverter")
public class PayAsYouGoItemConverterImpl extends BaseDtoEntityConverterImpl<PayAsYouGoItem, PayAsYouGoItemDTO> {
    
    @Autowired
    protected PayAsYouGoItemDAO payAsYouGoItemDAO;
    
    @Override
    protected PayAsYouGoItemDTO getNewDto() {
        return new PayAsYouGoItemDTO();
    }
    
    @Override
    public PayAsYouGoItemDTO convertEntityToDto(PayAsYouGoItem entity) {
        PayAsYouGoItemDTO payAsYouGoItemDTO = (PayAsYouGoItemDTO) Converter.convert(entity, getNewDto());
        if (entity.getRelatedItem() != null){
            payAsYouGoItemDTO.setRelatedItem( (PayAsYouGoItemDTO) Converter.convert(entity.getRelatedItem(), getNewDto()));
        }
        
        return payAsYouGoItemDTO;
    }

    @Override
    public PayAsYouGoItem convertDtoToEntity(PayAsYouGoItemDTO dto, PayAsYouGoItem entity) {
        PayAsYouGoItem payAsYouGoItem;
        payAsYouGoItem = (PayAsYouGoItem) Converter.convert(dto, entity);
        if(dto.getRelatedItem() !=null) {             
            PayAsYouGoItem relatedItem = null;
            if (dto.getRelatedItem().getId() != null) {
                relatedItem = payAsYouGoItemDAO.findById(dto.getRelatedItem().getId());
            } else {
                relatedItem = new PayAsYouGoItem();
            }
            relatedItem.setModifiedDateTime(entity.getModifiedDateTime());
            relatedItem.setModifiedUserId(entity.getModifiedUserId());
            relatedItem.setCreatedDateTime(entity.getCreatedDateTime());
            relatedItem.setCreatedUserId(entity.getCreatedUserId());
            payAsYouGoItem.setRelatedItem((PayAsYouGoItem) Converter.convert(dto.getRelatedItem(), relatedItem));
        }
        return payAsYouGoItem;
    }
}
