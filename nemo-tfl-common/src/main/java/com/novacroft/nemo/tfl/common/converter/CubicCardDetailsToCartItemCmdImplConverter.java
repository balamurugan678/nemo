package com.novacroft.nemo.tfl.common.converter;

import java.util.Set;

import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;


public interface CubicCardDetailsToCartItemCmdImplConverter {
    
    Set<CartItemCmdImpl> convertCubicCardDetailsToCartItemCmdImpls(String cardNumber, String refundCartType);

}
