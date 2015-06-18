package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.common.utils.Converter;
import com.novacroft.nemo.tfl.common.data_access.ProductItemDAO;
import com.novacroft.nemo.tfl.common.domain.ProductItem;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

/**
 * Transform between product item entity and DTO.
 */
@Component(value = "productItemConverter")
public class ProductItemConverterImpl extends BaseDtoEntityConverterImpl<ProductItem, ProductItemDTO> {
    @Autowired
    protected ProductItemDAO productItemDAO;
    
    @Override
    protected ProductItemDTO getNewDto() {
        return new ProductItemDTO();
    }
    
    @Override
    public ProductItemDTO convertEntityToDto(ProductItem entity) {
        ProductItemDTO productItemDTO = (ProductItemDTO) Converter.convert(entity, getNewDto());
        if (entity.getRelatedItem() != null){
            productItemDTO.setRelatedItem( (ProductItemDTO) Converter.convert(entity.getRelatedItem(), getNewDto()));
        }
        
        return productItemDTO;
    }


    @Override
    public ProductItem convertDtoToEntity(ProductItemDTO dto, ProductItem entity) {
        ProductItem productItem;
        productItem = (ProductItem) Converter.convert(dto, entity);
        if(dto.getRelatedItem() !=null) {             
            ProductItem tradedTicket = null;
            if (dto.getRelatedItem().getId() != null) {
                tradedTicket = productItemDAO.findById(dto.getRelatedItem().getId());
            } else {
                tradedTicket = new ProductItem();
            }
            tradedTicket.setModifiedDateTime(entity.getModifiedDateTime());
            tradedTicket.setModifiedUserId(entity.getModifiedUserId());
            tradedTicket.setCreatedDateTime(entity.getCreatedDateTime());
            tradedTicket.setCreatedUserId(entity.getCreatedUserId());
            productItem.setRelatedItem((ProductItem) Converter.convert(dto.getRelatedItem(), tradedTicket));
        }
        return productItem;
    }
    
    
}
