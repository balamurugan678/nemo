package com.novacroft.nemo.tfl.common.application_service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.novacroft.nemo.tfl.common.application_service.impl.CompletedJourneyProcessingResult;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CompleteJourneyCommandImpl;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.RefundOrderItemDTO;

/**
 * New Refund service specification.
 */
public interface RefundService {
	CartCmdImpl createCartCmdImplWithCartDTO();
	
	CartCmdImpl createCartCmdImplWithCartDTOFromCustomerId(Long customerId, String cartType);
	
	CartDTO updateCartDTOWithCardDetailsInCubic(CartDTO cartDTO, String cardNumber, String refundCartType);
	
	void deleteCartForCustomerId(Long customerId);
	
	CartDTO addTravelCardToCart(CartDTO cartDTO, CartCmdImpl cmd, String refundType);
	
	CartDTO updateProductItemDTO(Long cartId, Long itemId, String refundCalculationBasis);
	
	CartDTO updatePayAsYouGoItemDTO(CartDTO cartDTO, Integer updatedPrice);
	
	CartDTO updateAdministrationFeeItemDTO(CartDTO cartDTO, Integer updatedPrice);
	
	CartDTO getUpdatedCart(Long cartId);
	
	CartDTO getCartDTOForRefund(String cardNumber,String cartType, Boolean isGetDetailsFromCubic);

	void updatePayAsYouGoValueInCartCmdImpl(CartCmdImpl cmd, CartDTO cartDTO);

	void updateAdministrationFeeValueInCartCmdImpl(CartCmdImpl cmd, CartDTO cartDTO);

	CartDTO getCartDTOUsingCartSessionDataDTOInSession(HttpSession session);

	void storeCartIdInCartSessionDataDTOInSession(HttpSession session,
			Long cartId);

	void updateDateOfRefundInCartCmdImpl(CartCmdImpl cmd, CartDTO cartDTO);

	CartDTO updateDateOfRefund(CartDTO cartDTO, Date updatedDateOfRefund);
	
	CartDTO updateDateOfRefundAndRefundCalculationBasis(CartDTO cartDTO, Date updatedDateOfRefund, String refundCalculationBasis);
	
    List<RefundOrderItemDTO> findAllRefundsForCustomer(Long customerId);

    RefundOrderItemDTO findRefundDetailsForIdAndCustomer(Long refundId, Long customerId);

    CompletedJourneyProcessingResult processRefundsForCompletedJourney(CompleteJourneyCommandImpl command);
    
    void updatePaymentDetailsInCartCmdImpl(CartCmdImpl cmd, CartDTO cartDTO);
    
    void updateCustomerNameAndAddress(String cardNumber, CartCmdImpl cartCmdImpl, CartDTO cartDTO);
    
    Integer getTotalTicketPrice(List<ItemDTO> cartItemsDto);
    
    CartDTO attachPreviouslyExchangedTicketToTheExistingProductItem(CartItemCmdImpl cartItemCmd, CartDTO cartDTO,Long cartItemId);

    CartDTO updateCartDTOWithDepositItem(CartDTO cartDTO, String cardNumber);
    
    

}
