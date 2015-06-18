package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;


public interface ConvertCubicCardDetailsToCartDTOService {
    
    CartDTO populateCartItemsToCartDTOFromCubic(CartDTO cartDTO,Long cardId);
    CardInfoResponseV2DTO getCardDetailsFromCubic(Long cardId);
}
