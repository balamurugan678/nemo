package com.novacroft.nemo.tfl.services.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.constant.ItemType;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoDTO;
import com.novacroft.nemo.tfl.services.converter.PayAsYouGoConverter;
import com.novacroft.nemo.tfl.services.transfer.Item;

@Component("payAsYouGoWSConverter")
public class PayAsYouGoConverterImpl implements PayAsYouGoConverter {
    
    @Override
    public Item convertToItem(PayAsYouGoDTO payAsYouGoDTO) {
        Item result = new Item();
        result.setPrice(payAsYouGoDTO.getTicketPrice());
        result.setName(payAsYouGoDTO.getPayAsYouGoName());
        result.setProductType(ItemType.PAY_AS_YOU_GO.code());
        return result;
    }

}
