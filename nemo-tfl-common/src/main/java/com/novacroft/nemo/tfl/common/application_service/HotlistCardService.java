package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.common.transfer.SelectListDTO;

public interface HotlistCardService {

    void toggleCardHotlisted(String cardNumber, Integer hotlistReasonId);
    
    SelectListDTO getHotlistReasonSelectList();

}
