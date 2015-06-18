package com.novacroft.nemo.tfl.common.action;

import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

import java.util.Date;
import java.util.List;

public interface ItemDTOActionDelegate {

    ItemDTO createItemDTO(CartItemCmdImpl cartItemCmd, Class<? extends ItemDTO> itemDTOSubclass);

    List<ItemDTO> postProcessItems(List<ItemDTO> items,boolean isRefundCalculationRequired);

    ItemDTO updateItemDTO(ItemDTO existingItemDTO, ItemDTO newItemDTO);

    Boolean hasItemExpired(Date currentDate, ItemDTO itemDTO);

    ItemDTOAction getItemDTOActionImpl(Class<? extends ItemDTO> itemDTOSubclass);
    
    ItemDTO updateItemDTOForBackDatedAndDeceased(ItemDTO existingItemDTO, ItemDTO newItemDTO);
}
