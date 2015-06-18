package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.common.transfer.SelectListDTO;

/**
 * Get cards as a select list service specification
 */
public interface CardSelectListService {
    SelectListDTO getCardsSelectList(String username);
    SelectListDTO getCardsSelectListForCustomer(Long customerId);
    
}
