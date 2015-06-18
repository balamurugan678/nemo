package com.novacroft.nemo.tfl.common.action;

import java.util.Date;

import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

/**
 * ItemDTO action specification.
 *
 * <p>An action is performed when an appropriate record is encountered.</p>
 */
public interface ItemDTOAction {

    ItemDTO createItemDTO(CartItemCmdImpl cartItemCmd);

    ItemDTO updateItemDTO(ItemDTO existingItemDTO, ItemDTO newItemDTO);

    ItemDTO postProcessItemDTO(ItemDTO itemDTO,Boolean isRefundCalculationRequired);

    Boolean hasItemExpired(Date currentDate, ItemDTO itemDTO);

    ItemDTO updateItemDTOForBackDatedAndDeceased(ItemDTO existingItemDTO, ItemDTO newItemDTO);
}
