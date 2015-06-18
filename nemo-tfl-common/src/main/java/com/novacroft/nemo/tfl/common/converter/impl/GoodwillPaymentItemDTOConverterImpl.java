package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.utils.Converter;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.converter.GoodwillPaymentItemDTOConverter;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;

@Component(value = "goodwillPaymentItemDTOConverter")
public class GoodwillPaymentItemDTOConverterImpl implements GoodwillPaymentItemDTOConverter {

    @Override
    public GoodwillPaymentItemDTO convertCmdToDto(CartItemCmdImpl cmd, GoodwillPaymentItemDTO goodwillPaymentItemDTO) {
        return (GoodwillPaymentItemDTO) Converter.convert(cmd, goodwillPaymentItemDTO);
    }
}
