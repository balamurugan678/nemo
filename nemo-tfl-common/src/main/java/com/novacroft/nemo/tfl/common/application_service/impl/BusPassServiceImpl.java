package com.novacroft.nemo.tfl.common.application_service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.tfl.common.application_service.BusPassService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

/**
 * Bus pass service implementation
 */
@Service("busPassService")
public class BusPassServiceImpl implements BusPassService {
    static final Logger logger = LoggerFactory.getLogger(BusPassServiceImpl.class);
    
    @Autowired
    protected CartService cartService;
    @Autowired
    protected CartAdministrationService cartAdminService;

    @Override
    @Transactional
    public CartDTO addCartItemForExistingCard(CartDTO cartDTO, CartItemCmdImpl cartItemCmd){
        assert (cartDTO != null);
        assert (cartItemCmd != null);
        cartItemCmd.setCardId(cartDTO.getCardId());
        cartDTO = cartService.addItem(cartDTO, cartItemCmd, ProductItemDTO.class);
        return cartDTO;
    }
    
    @Override
    @Transactional
    public CartDTO addCartItemForNewCard(CartDTO cartDTO, CartItemCmdImpl cartItemCmd){
        assert (cartDTO != null);
        assert (cartItemCmd != null);
        cartItemCmd.setCardId(cartDTO.getCardId());
        cartDTO = cartService.addItem(cartDTO, cartItemCmd, ProductItemDTO.class);
        cartDTO = cartAdminService.applyRefundableDeposit(cartDTO, cartItemCmd);
        cartDTO = cartAdminService.applyShippingCost(cartDTO, cartItemCmd);
        return cartDTO;
    }

    
}
