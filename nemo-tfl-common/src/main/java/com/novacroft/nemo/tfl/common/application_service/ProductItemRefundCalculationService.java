package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;


/**
 * Specification for bus pass service
 */
public interface ProductItemRefundCalculationService {
    
    Refund calculateRefund(ProductItemDTO productItemDTO);
    
}
