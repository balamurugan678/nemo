package com.novacroft.nemo.tfl.common.action.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.action.ItemDTOAction;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.converter.impl.GoodwillPaymentItemDTOConverterImpl;
import com.novacroft.nemo.tfl.common.data_service.GoodwillReasonDataService;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillReasonDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

/**
 * Action to handle the goodwill item specific actions
 */
@Component("goodwillPaymentItemAction")
public class GoodwillPaymentItemActionImpl implements ItemDTOAction {

    protected static final String GOODWILL_REASON_OTHER = "OTHER";
    protected static final String HYPHEN = " - ";

    @Autowired
    protected GoodwillReasonDataService goodwillReasonDataService;

    @Autowired
    protected GoodwillPaymentItemDTOConverterImpl goodwillPaymentItemDTOConverter;

    @Override
    public ItemDTO createItemDTO(CartItemCmdImpl cartItemCmd) {
        GoodwillPaymentItemDTO goodwillPaymentItemDTO = new GoodwillPaymentItemDTO();

        goodwillPaymentItemDTO = goodwillPaymentItemDTOConverter.convertCmdToDto(cartItemCmd, goodwillPaymentItemDTO);

        GoodwillReasonDTO goodwillReasonDTO = goodwillReasonDataService.findByReasonId(cartItemCmd.getGoodwillPaymentId());
        goodwillPaymentItemDTO.setGoodwillReasonDTO(goodwillReasonDTO);
        goodwillPaymentItemDTO = setGoodwillPaymentItemDTOOtherText(goodwillPaymentItemDTO, goodwillReasonDTO);

        return goodwillPaymentItemDTO;
    }

    @Override
    public ItemDTO updateItemDTO(ItemDTO itemDTOToBeUpdated, ItemDTO newItemDTO) {
        return itemDTOToBeUpdated;
    }

    @Override
    public ItemDTO postProcessItemDTO(ItemDTO itemDTO, Boolean isRefundCalculationRequired) {
        if (itemDTO instanceof GoodwillPaymentItemDTO) {
            GoodwillPaymentItemDTO goodwillPaymentItemDTO = (GoodwillPaymentItemDTO) itemDTO;
            itemDTO.setName(goodwillPaymentItemDTO.getGoodwillReasonDTO().getDescription());
        }
        return itemDTO;
    }

    protected GoodwillPaymentItemDTO setGoodwillPaymentItemDTOOtherText(GoodwillPaymentItemDTO goodwillPaymentItemDTO,
                    GoodwillReasonDTO goodwillReasonDTO) {
        if (goodwillReasonDTO.getDescription().equalsIgnoreCase(GOODWILL_REASON_OTHER)) {
            goodwillPaymentItemDTO.setGoodwillOtherText(goodwillPaymentItemDTO.getGoodwillOtherText());
        } else {
            goodwillPaymentItemDTO.setGoodwillOtherText(StringUtil.EMPTY_STRING);
        }
        return goodwillPaymentItemDTO;
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
