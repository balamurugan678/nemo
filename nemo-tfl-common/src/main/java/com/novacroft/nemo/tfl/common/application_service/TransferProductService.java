package com.novacroft.nemo.tfl.common.application_service;

import java.util.Map;

import javax.servlet.http.HttpSession;



public interface TransferProductService {
    
     Map<String,Object> transferProductFromSourceCardToTargetCard(HttpSession session,Long sourceCardId, Long targetCardId,Long stationId,Boolean isCardLostOrStolen);
}
