package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.common.utils.Converter;
import com.novacroft.nemo.tfl.common.domain.GoodwillPaymentItem;
import com.novacroft.nemo.tfl.common.domain.GoodwillReason;
import com.novacroft.nemo.tfl.common.domain.Item;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillReasonDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

@Component(value = "goodwillPaymentItemConverter")
public class GoodwillPaymentItemConverterImpl extends BaseDtoEntityConverterImpl<GoodwillPaymentItem, GoodwillPaymentItemDTO> {
    @Autowired
    GoodwillReasonConverterImpl goodwillReasonConverter;
    
    @Override
    protected GoodwillPaymentItemDTO getNewDto() {
        return new GoodwillPaymentItemDTO();
    }
    
    public ItemDTO convertEntityToDto(Item entity) {
        GoodwillPaymentItem goodwillPaymentItem = (GoodwillPaymentItem) entity;
        GoodwillReasonDTO goodwillReasonDTO = convertGoodwillReasonToGoodwillReasonDTO(goodwillPaymentItem.getGoodwillReason());

        GoodwillPaymentItemDTO goodwillPaymentItemDTO = new GoodwillPaymentItemDTO(); 
        goodwillPaymentItemDTO = (GoodwillPaymentItemDTO) Converter.convert(goodwillPaymentItem, goodwillPaymentItemDTO);
        goodwillPaymentItemDTO.setGoodwillReasonDTO(goodwillReasonDTO);

        return goodwillPaymentItemDTO;
    }
    
    public Item convertDtoToEntity(ItemDTO dto, Item entity) {
        GoodwillPaymentItemDTO goodwillPaymentItemDTO = (GoodwillPaymentItemDTO) dto;
        GoodwillReason goodwillReason = convertGoodwillReasonDTOToGoodwillReason(goodwillPaymentItemDTO.getGoodwillReasonDTO());

        GoodwillPaymentItem goodwillPaymentItem = (GoodwillPaymentItem) Converter.convert(goodwillPaymentItemDTO, entity);
        goodwillPaymentItem.setGoodwillReason(goodwillReason);

        return goodwillPaymentItem; 
    }
    
    protected GoodwillReasonDTO convertGoodwillReasonToGoodwillReasonDTO(GoodwillReason goodwillReason) {
        return goodwillReasonConverter.convertEntityToDto(goodwillReason);
    }
    
    protected GoodwillReason convertGoodwillReasonDTOToGoodwillReason(GoodwillReasonDTO goodwillReasonDTO) {
        GoodwillReason goodwillReason = new GoodwillReason();
        return goodwillReasonConverter.convertDtoToEntity(goodwillReasonDTO, goodwillReason);
    }
}
