package com.novacroft.nemo.tfl.common.application_service.cubic;

import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardStatusDTO;

/**
 * Cubic card service specification
 */
public interface CubicCardService {

    String checkCardPrePayTicketStatusReturnMessage(String prestigeId, CardInfoResponseV2DTO cardInfoResponseV2DTO);

    String checkCardPrePayValueStatusReturnMessage(String prestigeId, CardInfoResponseV2DTO cardInfoResponseV2DTO);

    CardStatusDTO checkCardStatusReturnMessage(String prestigeId);
}

