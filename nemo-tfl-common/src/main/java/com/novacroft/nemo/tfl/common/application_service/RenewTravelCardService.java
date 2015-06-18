package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;

public interface RenewTravelCardService {

    CartCmdImpl getCartItemsFromCubic(CartDTO cartDTO, Long cardId);

    SelectListDTO getRenewProductStartDateList(String startDateStr);

    void renewProducts(CartCmdImpl cmd, CartDTO cartDTO);

    boolean isOverlappingWithCubicProducts(CartItemCmdImpl cartItem, Long cardId);

}
