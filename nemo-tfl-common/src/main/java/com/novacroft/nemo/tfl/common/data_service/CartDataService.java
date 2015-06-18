package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.Cart;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;

/**
 * Cart data service specification
 */
public interface CartDataService extends BaseDataService<Cart, CartDTO> {
	CartDTO findByWebAccountId(Long webAccountId);
	CartDTO findByCardId(Long cardId);
	CartDTO findByCustomerId(Long customerId);
    List<Long> findCartListByCustomerId(Long customerId);
    CartDTO addApprovalId(CartDTO cartDTO);
    CartDTO findByApprovalId(Long approvalId);
    CartDTO findNotInWorkFlowFlightCartByCustomerId(Long customerId);
    CartDTO findNotInWorkFlowFlightCartByCardId(Long cardId);
}
