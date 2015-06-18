package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.common.transfer.SelectListDTO;

/**
 * Get payment card select list service
 */
public interface PaymentCardSelectListService {
    SelectListDTO getPaymentCardSelectList(Long customerId);

    SelectListDTO getPaymentCardSelectListWithAllOptions(Long customerId);

    SelectListDTO getPaymentCardSelectListWithOnlySaveOption(Long customerId);
    
    SelectListDTO getPaymentCardSelectListForAdHocLoad(Long customerId);
    
    SelectListDTO getPaymentCardSelectListForAdHocLoadWithAllOptions(Long customerId);
}
