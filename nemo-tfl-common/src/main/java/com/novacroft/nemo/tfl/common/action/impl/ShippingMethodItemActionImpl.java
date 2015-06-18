package com.novacroft.nemo.tfl.common.action.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.action.ItemDTOAction;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.data_service.ShippingMethodDataService;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodDTO;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodItemDTO;

@Component("shippingMethodItemAction")
public class ShippingMethodItemActionImpl implements ItemDTOAction {

    static final Logger logger = LoggerFactory.getLogger(ShippingMethodItemActionImpl.class);

    @Autowired
    protected ShippingMethodDataService shippingMethodDataService;

    @Override
    public ItemDTO createItemDTO(CartItemCmdImpl cmd) {
        ShippingMethodDTO shippingMethod = shippingMethodDataService.findByShippingMethodName(cmd.getShippingMethodType(), false);
        if (null == shippingMethod) {
            String errorMessage = String.format(PrivateError.FOUND_NO_RECORDS.message(), cmd.getCardId(), cmd.getShippingMethodType());
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        return new ShippingMethodItemDTO(cmd.getCardId(), shippingMethod.getPrice(), shippingMethod.getId());
    }

    @Override
    public ItemDTO updateItemDTO(ItemDTO itemDTOToBeUpdated, ItemDTO newItemDTO) {
        ShippingMethodItemDTO target = (ShippingMethodItemDTO) itemDTOToBeUpdated;
        ShippingMethodItemDTO source = (ShippingMethodItemDTO) newItemDTO;
        target.setPrice(source.getPrice());
        target.setName(source.getName());
        target.setShippingMethodId(source.getShippingMethodId());
        return itemDTOToBeUpdated;
    }

    @Override
    public ItemDTO postProcessItemDTO(ItemDTO itemDTO,Boolean isRefundCalculationRequired) {
        ShippingMethodDTO shippingMethod = shippingMethodDataService.findById(((ShippingMethodItemDTO) itemDTO).getShippingMethodId());
        if (null != shippingMethod) {
            itemDTO.setName(shippingMethod.getName());
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
