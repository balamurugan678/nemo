package com.novacroft.nemo.tfl.common.action.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.action.ItemDTOAction;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.data_service.AutoTopUpDataService;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

@Component("autoTopUpConfigurationItemAction")
public class AutoTopUpConfigurationItemActionImpl implements ItemDTOAction {

    static final Logger logger = LoggerFactory.getLogger(AutoTopUpConfigurationItemActionImpl.class);

    @Autowired
    protected AutoTopUpDataService autoTopUpDataService;
    @Autowired
    protected ApplicationContext applicationContext;

    private static final String CONTENT_CODE_AUTO_TOPUP = "autoTopup.text";

    @Override
    public ItemDTO createItemDTO(CartItemCmdImpl cartItemCmd) {
        AutoTopUpDTO autoTopUp = autoTopUpDataService.findByAutoTopUpAmount(cartItemCmd.getAutoTopUpAmt());
        if (null == autoTopUp) {
            String errorMessage = String.format(PrivateError.FOUND_NO_RECORDS.message(), cartItemCmd.getCardId(),
                    cartItemCmd.getAutoTopUpAmt());
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        } else {
            return getAutoTopUpItemDTO(autoTopUp, cartItemCmd);
        }
    }
    
    protected AutoTopUpConfigurationItemDTO getAutoTopUpItemDTO(AutoTopUpDTO autoTopUp, CartItemCmdImpl cartItemCmd) {
        if (StringUtil.isBlank(cartItemCmd.getAutoTopUpActivity())) {
            return new AutoTopUpConfigurationItemDTO(cartItemCmd.getCardId(), 0, autoTopUp.getId(), cartItemCmd.getAutoTopUpAmt());
        } else {
            return new AutoTopUpConfigurationItemDTO(cartItemCmd.getCardId(), 0, autoTopUp.getId(), cartItemCmd.getAutoTopUpAmt(), cartItemCmd.getAutoTopUpActivity());
        }
    }

    @Override
    public ItemDTO updateItemDTO(ItemDTO itemDTOToBeUpdated, ItemDTO newItemDTO) {
        return itemDTOToBeUpdated;
    }

    @Override
    public ItemDTO postProcessItemDTO(ItemDTO itemDTO,Boolean isRefundCalculationRequired) {
        itemDTO.setName(this.applicationContext.getMessage(CONTENT_CODE_AUTO_TOPUP, null, null));
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
