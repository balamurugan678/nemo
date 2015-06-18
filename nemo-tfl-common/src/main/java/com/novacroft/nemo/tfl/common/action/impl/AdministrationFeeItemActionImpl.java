package com.novacroft.nemo.tfl.common.action.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.action.ItemDTOAction;
import com.novacroft.nemo.tfl.common.application_service.AdministrationFeeService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.AdministrationFeeDataService;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeDTO;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

/**
 * Action to handle the administration fee item specific actions
 */
@Component("administrationFeeItemAction")
public class AdministrationFeeItemActionImpl implements ItemDTOAction {
    @Autowired
    protected AdministrationFeeService administrationFeeService;
    
    @Autowired
    protected AdministrationFeeDataService administrationFeeDataService;
    
    @Override
    public ItemDTO createItemDTO(CartItemCmdImpl cartItemCmd) {
	return administrationFeeService.getNewAdministrationFeeItemDTO(cartItemCmd);
    }

    @Override
    public ItemDTO updateItemDTO(ItemDTO itemDTOToBeUpdated, ItemDTO newItemDTO) {
        return itemDTOToBeUpdated;
    }

    @Override
    public ItemDTO postProcessItemDTO(ItemDTO itemDTO,Boolean isRefundCalculationRequired) {
        AdministrationFeeDTO administrationFeeDTO = administrationFeeDataService.findById(((AdministrationFeeItemDTO) itemDTO).getAdministrationFeeId());
        if (null != administrationFeeDTO) {
            itemDTO.setName(administrationFeeDTO.getDescription());
        }
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
