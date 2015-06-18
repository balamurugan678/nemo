package com.novacroft.nemo.tfl.common.action.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.action.ItemDTOAction;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.AutoTopUpOrderConstant;
import com.novacroft.nemo.tfl.common.data_service.FailedAutoTopUpCaseDataService;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpCaseItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

@Component("failedAutoTopUpResettlementAction")
public class FailedAutoTopUpResettlementActionImpl implements ItemDTOAction {
	
    @Autowired
    protected FailedAutoTopUpCaseDataService failedAutoTopUpCaseDataService;
	
    @Override
    public ItemDTO createItemDTO(CartItemCmdImpl cartItemCmd) {
        Double amount = (failedAutoTopUpCaseDataService.findByFATUCaseId(cartItemCmd.getFailedAutoTopUpCaseId())).getFailedAutoTopUpAmount();
        return new FailedAutoTopUpCaseItemDTO(cartItemCmd.getCardId(), amount);
    }

    @Override
    public ItemDTO updateItemDTO(ItemDTO itemDTOToBeUpdated, ItemDTO newItemDTO) {
        return itemDTOToBeUpdated;
    }

    @Override
    public ItemDTO postProcessItemDTO(ItemDTO itemDTO, Boolean isRefundCalculationRequired) {
        itemDTO.setName(AutoTopUpOrderConstant.RESETTLEMENT);
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
