package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.common.utils.Converter;
import com.novacroft.nemo.tfl.common.data_access.AutoTopUpItemDAO;
import com.novacroft.nemo.tfl.common.domain.AutoTopUpConfigurationItem;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;

/**
 * Transform between auto top-up item entity and DTO.
 */
@Component(value = "autoTopUpItemConverter")
public class AutoTopUpConfigurationItemConverterImpl extends BaseDtoEntityConverterImpl<AutoTopUpConfigurationItem, AutoTopUpConfigurationItemDTO> {
    @Autowired
    protected AutoTopUpItemDAO autoTopUpItemDAO;
    @Override
    protected AutoTopUpConfigurationItemDTO getNewDto() {
        return new AutoTopUpConfigurationItemDTO();
    }
    
    @Override
    public AutoTopUpConfigurationItemDTO convertEntityToDto(AutoTopUpConfigurationItem entity) {
        AutoTopUpConfigurationItemDTO autoTopUpItemDTO = (AutoTopUpConfigurationItemDTO) Converter.convert(entity, getNewDto());
        if (entity.getRelatedItem() != null){
            autoTopUpItemDTO.setRelatedItem( (AutoTopUpConfigurationItemDTO) Converter.convert(entity.getRelatedItem(), getNewDto()));
        }
        
        return autoTopUpItemDTO;
    }

    @Override
    public AutoTopUpConfigurationItem convertDtoToEntity(AutoTopUpConfigurationItemDTO dto, AutoTopUpConfigurationItem entity) {
        AutoTopUpConfigurationItem autoTopUpItem;
        autoTopUpItem = (AutoTopUpConfigurationItem) Converter.convert(dto, entity);
        if(dto.getRelatedItem() !=null) {             
            AutoTopUpConfigurationItem relatedItem = null;
            if (dto.getRelatedItem().getId() != null) {
                relatedItem = autoTopUpItemDAO.findById(dto.getRelatedItem().getId());
            } else {
                relatedItem = new AutoTopUpConfigurationItem();
            }
            relatedItem.setModifiedDateTime(entity.getModifiedDateTime());
            relatedItem.setModifiedUserId(entity.getModifiedUserId());
            relatedItem.setCreatedDateTime(entity.getCreatedDateTime());
            relatedItem.setCreatedUserId(entity.getCreatedUserId());
            autoTopUpItem.setRelatedItem((AutoTopUpConfigurationItem) Converter.convert(dto.getRelatedItem(), relatedItem));
        }
        return autoTopUpItem;
    }

}
