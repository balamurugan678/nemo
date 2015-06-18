package com.novacroft.nemo.tfl.common.application_service;

import java.util.List;

import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

public interface AddUnlistedProductService {

    void setTicketType(CartItemCmdImpl cmd);

    void setTravelcardTypeByFormTravelCardType(CartItemCmdImpl cmd);

    String removeTravelCardSuffixFromTravelCardType(String travelCardType);

    boolean isCartItemDuplicate(List<ItemDTO> cartItemList, CartItemCmdImpl cartItemCmdImpl);

}
