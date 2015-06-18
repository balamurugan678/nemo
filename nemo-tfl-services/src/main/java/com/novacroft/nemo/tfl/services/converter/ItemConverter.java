package com.novacroft.nemo.tfl.services.converter;

import java.util.Date;
import java.util.List;

import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.services.transfer.Item;

/**
 * Convert nemo-tfl-common ItemDTO objects to nemo-tfl-services Item objects
 */
public interface ItemConverter {    
    Item convert(ItemDTO itemDTO);
    List<Item> convert(List<ItemDTO> itemDTOList);
    List<Item> convert(List<ItemDTO> itemDTOList, Date orderDate);
}
