package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.Item;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpHistoryItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

/**
 * Item data service specification
 */
public interface ItemDataService extends BaseDataService<Item, ItemDTO> {
    List<ItemDTO> findAllByExample(Item example);
    List<ItemDTO> findByOrderId(Long orderId);
    ItemDTO setDateTime(ItemDTO itemDTO);    
    ItemDTO setUserId(ItemDTO itemDTO);
    List<AutoTopUpHistoryItemDTO> findAllAutoTopUpsForCard(Long cardId);
}
