package com.novacroft.nemo.tfl.common.data_service.cubic;

import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoRequestV2DTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

/**
 * Service to get card information. Using the cubic service. 
 */
public interface GetCardRequestDataService {

    CardInfoResponseV2DTO getCard(CardInfoRequestV2DTO request);
    
}
