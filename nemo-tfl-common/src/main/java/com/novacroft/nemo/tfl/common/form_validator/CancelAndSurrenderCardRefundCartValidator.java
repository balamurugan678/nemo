package com.novacroft.nemo.tfl.common.form_validator;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

/**
 * Cancel and Surrender refund cart validation
 */
@Component("cancelAndSurrenderCardRefundCartValidator")
public class CancelAndSurrenderCardRefundCartValidator extends RefundCartValidator {

    @Override
    protected boolean isAnyOneOfSelectedProductsExpiredOnDateOfRefund(CartCmdImpl cartCmdImpl) {
        for (ItemDTO itemDTO : cartCmdImpl.getCartDTO().getCartItems()) {
            if (isItemDTOAProductItemDTO(itemDTO) && 
                            !isTicketUsed(itemDTO) && 
                            isItemDTOExpiredOnDateOfRefund(itemDTO)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    protected boolean isItemDTOAProductItemDTO(ItemDTO itemDTO) {
	return itemDTO instanceof ProductItemDTO;
    }

    protected boolean isTicketUsed(ItemDTO itemDTO) {
	ProductItemDTO productItemDTO = (ProductItemDTO) itemDTO; 
        return !productItemDTO.getTicketUnused();
    }
}
