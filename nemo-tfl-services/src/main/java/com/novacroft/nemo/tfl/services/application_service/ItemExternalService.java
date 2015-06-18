package com.novacroft.nemo.tfl.services.application_service;

import org.springframework.validation.Errors;

import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.services.transfer.Cart;
import com.novacroft.nemo.tfl.services.transfer.Item;

public interface ItemExternalService {

    Errors addTravelCardItemToCart(CartDTO cartDTO, Item item, Cart cart, Integer itemsToAddCount);

    Errors addPayAsYouGoItemToCart(CartDTO cartDTO, Item item, Integer itemsToAddCount);

    Errors addBusPassItemToCart(CartDTO cartDTO, Item item, Cart cart, Integer itemsToAddCount);

}
