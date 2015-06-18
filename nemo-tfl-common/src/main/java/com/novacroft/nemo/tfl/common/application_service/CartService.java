package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

/**
 * Cart service specification.
 */
public interface CartService {
	CartDTO createCart();
	
	CartDTO createCartFromCustomerId(Long customerId);
	
	CartDTO createCartFromCardId(Long cardId);
    
    CartDTO addItem(CartDTO cartDTO, CartItemCmdImpl cartItemCmd, Class<? extends ItemDTO> itemDTOSubclass);

    CartDTO addUpdateItem(CartDTO cartDTO, CartItemCmdImpl cartItemCmd, Class<? extends ItemDTO> itemDTOSubclass);

	CartDTO deleteItem(CartDTO cartDTO, Long cartItemId);
	
	CartDTO findNotInWorkFlowFlightCartByCustomerId(Long customerId);
	
	CartDTO findNotInWorkFlowFlightCartByCardId(Long cardId);
	
	CartDTO findById(Long id);
	
    void deleteCart(Long cartId);

    void deleteCartForCardId(Long cardId);
	
	void deleteCartForCustomerId(Long customerId);
	
	void updateRefundCalculationBasis(Long cartId, Long itemId, String refundCalculationBasis);
	
	CartDTO updatePrice(CartDTO cartDTO, Long itemId, Integer updatedPrice);
		
    CartDTO emptyCart(CartDTO cartDTO);

    CartDTO updateCart(CartDTO cartDTO);
    
    CartDTO removeExpiredCartItems(CartDTO cartDTO);

    CartDTO addUpdateItems(Long cartId, CartItemCmdImpl cartItemCmd, Class<? extends ItemDTO> itemDTOSubclass);
    
    ItemDTO getMatchedProductItemDTOFromCartDTO(CartDTO cartDTO, Class<? extends ItemDTO> itemDTOSubclass, long cartItemId) ;
    
	CustomerDTO findCustomerForCart(Long cartId);
	
	CartDTO findByApprovalId(Long approvalId);
	  
	CartDTO updateCartWithoutRefundCalculationInPostProcess(CartDTO cartDTO);
	
	CartDTO postProcessAndSortCartDTOAndRecalculateRefund(CartDTO cartDTO);
	
	CartDTO postProcessAndSortCartDTOWithoutRefundRecalculation(CartDTO cartDTO);
}
