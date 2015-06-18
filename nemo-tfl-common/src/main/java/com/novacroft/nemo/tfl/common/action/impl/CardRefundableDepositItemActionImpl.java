package com.novacroft.nemo.tfl.common.action.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.action.ItemDTOAction;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.CardRefundableDepositDataService;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.REFUNDABLE_DEPOSIT_AMOUNT;

@Component("cardRefundableDepositItemAction")
public class CardRefundableDepositItemActionImpl implements ItemDTOAction {

    @Autowired
    protected CardRefundableDepositDataService cardRefundableDepositDataService;

    @Override
    public ItemDTO createItemDTO(CartItemCmdImpl cartItemCmd) {
        Integer refundableDepositAmount = cardRefundableDepositDataService.findRefundableDepositAmount().getPrice();
        return new CardRefundableDepositItemDTO(cartItemCmd.getCardId(), refundableDepositAmount);

    }

    @Override
    public ItemDTO updateItemDTO(ItemDTO itemDTOToBeUpdated, ItemDTO newItemDTO) {
        return itemDTOToBeUpdated;
    }

    @Override
    public ItemDTO postProcessItemDTO(ItemDTO itemDTO,Boolean isRefundCalculationRequired) {
        itemDTO.setName(REFUNDABLE_DEPOSIT_AMOUNT);
        return itemDTO;
    }

    @Override
    public Boolean hasItemExpired(Date currentDate, ItemDTO itemDTO) {
        return Boolean.FALSE;
    }

    @Override
    public ItemDTO updateItemDTOForBackDatedAndDeceased(ItemDTO itemDTOToBeUpdated, ItemDTO newItemDTO) {
        return itemDTOToBeUpdated;
    }
}
