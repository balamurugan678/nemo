package com.novacroft.nemo.tfl.common.action.impl;

import static com.novacroft.nemo.common.utils.DateUtil.addDaysToDate;
import static com.novacroft.nemo.common.utils.DateUtil.getMidnightDay;
import static com.novacroft.nemo.common.utils.DateUtil.isAfter;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.REMINDER_DATE_NONE;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.USER_PRODUCT_START_AFTER_DAYS;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.action.ItemDTOAction;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.PayAsYouGoDataService;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;

/**
 * Action to handle the pay as you go item specific actions
 */
@Component("payAsYouGoItemAction")
public class PayAsYouGoItemActionImpl implements ItemDTOAction {

    @Autowired
    protected PayAsYouGoDataService payAsYouGoDataService;
    @Autowired
    protected SystemParameterService systemParameterService;

    @Override
    public ItemDTO createItemDTO(CartItemCmdImpl cartItemCmd) {
        Integer creditBalance = getPayAsYouGoApplicableFromCmd(cartItemCmd);
        PayAsYouGoDTO payAsYouGo = payAsYouGoDataService.findByTicketPrice(creditBalance);
        if (null != payAsYouGo) {
            PayAsYouGoItemDTO itemDTO = new PayAsYouGoItemDTO(cartItemCmd.getCardId(), creditBalance, payAsYouGo.getId(),
                    !StringUtil.isBlank(cartItemCmd.getStartDate()) ? DateUtil.parse(cartItemCmd.getStartDate()) : null,
                    !StringUtil.isBlank(cartItemCmd.getEndDate()) ? DateUtil.parse(cartItemCmd.getEndDate()) : null,
                    !StringUtil.isBlank(cartItemCmd.getEmailReminder()) ? cartItemCmd.getEmailReminder() : REMINDER_DATE_NONE,
                    cartItemCmd.getAutoTopUpAmt());
            itemDTO.setName(payAsYouGo.getPayAsYouGoName());
            return itemDTO;
        }
        return null;
    }

    @Override
    public ItemDTO updateItemDTO(ItemDTO itemDTOToBeUpdated, ItemDTO newItemDTO) {
        PayAsYouGoItemDTO target = (PayAsYouGoItemDTO) itemDTOToBeUpdated;
        PayAsYouGoItemDTO source = (PayAsYouGoItemDTO) newItemDTO;
        target.setStartDate(source.getStartDate());
        target.setEndDate(source.getEndDate());
        target.setPayAsYouGoId(source.getPayAsYouGoId());
        target.setReminderDate(source.getReminderDate());
        target.setPrice(source.getPrice());
        target.setAutoTopUpAmount(source.getAutoTopUpAmount());
        return target;
    }

    @Override
    public ItemDTO postProcessItemDTO(ItemDTO itemDTO,Boolean isRefundCalculationRequired) {
        PayAsYouGoDTO payAsYouGo = payAsYouGoDataService.findById(((PayAsYouGoItemDTO) itemDTO).getPayAsYouGoId());
        if (null != payAsYouGo) {
            itemDTO.setName(payAsYouGo.getPayAsYouGoName());
        }
        return itemDTO;
    }

    @Override
    public Boolean hasItemExpired(Date currentDate, ItemDTO itemDTO) {
        PayAsYouGoItemDTO payAsYouGoItem = (PayAsYouGoItemDTO) itemDTO;
        Date userCartStartDate = getMidnightDay(
                addDaysToDate(currentDate, systemParameterService.getIntegerParameterValue(USER_PRODUCT_START_AFTER_DAYS)));
        if (payAsYouGoItem.getStartDate() != null && isAfter(userCartStartDate, payAsYouGoItem.getStartDate())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;

    }

    protected Integer getPayAsYouGoApplicableFromCmd(CartItemCmdImpl cartItemCmd) {
        Integer creditBalance = cartItemCmd.getCreditBalance();
        if (null == creditBalance) {
            creditBalance = cartItemCmd.getAutoTopUpCreditBalance();
        }
        return creditBalance;
    }

    @Override
    public ItemDTO updateItemDTOForBackDatedAndDeceased(ItemDTO itemDTOToBeUpdated, ItemDTO newItemDTO) {
        return itemDTOToBeUpdated;
    }
}
