package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;

public interface CartAdministrationService {

    CartDTO applyRefundableDeposit(CartDTO cartDTO, CartItemCmdImpl cartItemCmd);

    CartDTO applyShippingCost(CartDTO cartDTO, CartItemCmdImpl cartItemCmd);

    CartDTO removeRefundableDepositAndShippingCost(CartDTO cartDTO);

    SelectListDTO getProductStartDateList();
    
    SelectListDTO getUserProductStartDateList();
    
    Long addApprovalId(CartDTO cartDTO);

	void addOrRemoveAdministrationFeeToCart(CartDTO cartDTO, Long cardId, String cartType);
}
