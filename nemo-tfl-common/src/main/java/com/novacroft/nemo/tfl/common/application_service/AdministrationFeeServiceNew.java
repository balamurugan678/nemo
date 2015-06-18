package com.novacroft.nemo.tfl.common.application_service;

import java.util.List;

import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;

/**
 * Specification for administration fee service
 */
@Deprecated
public interface AdministrationFeeServiceNew {
    
    void addItem(CartCmdImpl cartCmd, CartItemCmdImpl cartItemCmd);

    void clearAdministrationFeeCartItem(AdministrationFeeItemDTO administrationFeeItem);

    void populateAdministrationFeeCartItem(AdministrationFeeItemDTO administrationFeeItem, List<CartItemCmdImpl> cartItems);

    void persistAdministrationFeeItem(CartItemCmdImpl cartItem, Long cardId, Long cartId);

    CartCmdImpl addDefaultAdministrationFeeItemIfNotExists(final CartCmdImpl cartCmdImpl);

    void updateAdministrationFee(CartCmdImpl cartCmdImpl, CartItemCmdImpl cartItem);

    CartItemCmdImpl getAdministrationFeeFromCart(CartCmdImpl cart);

}
