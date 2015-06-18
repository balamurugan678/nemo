package com.novacroft.nemo.tfl.common.converter.cubic;

import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoRequestV2;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoResponseV2;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoRequestV2DTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

/**
 * Get card info DTO / service model converter specification
 */

public interface GetCardConverter {
    
    CardInfoRequestV2 convertToModel(CardInfoRequestV2DTO cardInfoRequestV2DTO);

    CardInfoResponseV2DTO convertToDto(CardInfoResponseV2 cardInfoResponseV2);

    CardInfoResponseV2DTO convertToDto(RequestFailure requestFailure);

}
