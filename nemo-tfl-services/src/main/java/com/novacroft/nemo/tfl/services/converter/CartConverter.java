package com.novacroft.nemo.tfl.services.converter;

import java.util.List;

import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.services.transfer.Cart;

public interface CartConverter {
    
    Cart convert(CartDTO cartDTO);

    List<Cart> convert(List<CartDTO> cartDTOList);
}
