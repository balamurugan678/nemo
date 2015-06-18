package com.novacroft.nemo.tfl.common.converter;

import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;

public interface GoodwillPaymentItemDTOConverter {

    GoodwillPaymentItemDTO convertCmdToDto(CartItemCmdImpl cmd, GoodwillPaymentItemDTO goodwillPaymentItemDTO);
    
}
