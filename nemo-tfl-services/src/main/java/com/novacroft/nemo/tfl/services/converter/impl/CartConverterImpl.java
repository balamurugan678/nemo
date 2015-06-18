package com.novacroft.nemo.tfl.services.converter.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.utils.Converter;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.services.converter.CartConverter;
import com.novacroft.nemo.tfl.services.converter.ItemConverter;
import com.novacroft.nemo.tfl.services.transfer.Cart;

@Service("cartDTOConverter")
public class CartConverterImpl implements CartConverter {

    @Autowired
    protected ItemConverter itemConverter;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected CardDataService cardDataService;
    
    @Override
    public Cart convert(CartDTO cartDTO) {
        Cart cart = new Cart();
        Converter.convert(cartDTO, cart);
        cart.setCartItems(itemConverter.convert(cartDTO.getCartItems()));
        cart.setFormattedDateOfRefund(DateUtil.formatDate(cartDTO.getDateOfRefund()));
        cart.setId(cartDTO.getExternalId());
        Long customerId = cartDTO.getCustomerId();
        if(customerId != null){
            cart.setCustomerId(customerDataService.findByCustomerId(customerId).getExternalId());
        }
        Long cardId = cartDTO.getCardId();
        if(cardId != null){
            cart.setCardId(cardDataService.findById(cardId).getExternalId());
        }
        return cart;
    }

    @Override
    public List<Cart> convert(List<CartDTO> cartDTOList) {
        List<Cart> cartList = new ArrayList<>(cartDTOList.size());
        for (CartDTO cartDTO : cartDTOList) {
            cartList.add(convert(cartDTO));
        }
        return cartList;
    }

}
