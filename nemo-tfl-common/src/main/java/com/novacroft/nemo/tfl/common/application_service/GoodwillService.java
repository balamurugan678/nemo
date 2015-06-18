package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.common.transfer.SelectListDTO;

public interface GoodwillService {

    SelectListDTO getGoodwillRefundTypes(String goodwillReasonType);

    String getGoodwillRefundExtraValidationMessages(String goodwillReasonType);
    
    SelectListDTO getAnonymousGoodwillRefundTypes(String goodwillReasonType);

}
