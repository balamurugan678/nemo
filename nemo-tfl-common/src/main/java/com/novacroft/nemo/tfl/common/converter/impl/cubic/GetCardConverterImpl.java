package com.novacroft.nemo.tfl.common.converter.impl.cubic;

import static com.novacroft.nemo.common.utils.Converter.convert;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.constant.AutoLoadState;
import com.novacroft.nemo.tfl.common.converter.cubic.GetCardConverter;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoRequestV2;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoResponseV2;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoRequestV2DTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

/**
 * Get card info DTO / service model converter
 */
@Component(value = "getCardConverter")
public class GetCardConverterImpl implements GetCardConverter {

    @Override
    public CardInfoRequestV2 convertToModel(CardInfoRequestV2DTO cardInfoRequestV2DTO) {
        CardInfoRequestV2 cardInfoRequestV2 = new CardInfoRequestV2();
        convert(cardInfoRequestV2DTO, cardInfoRequestV2);
        return cardInfoRequestV2;
    }

    @Override
    public CardInfoResponseV2DTO convertToDto(CardInfoResponseV2 cardInfoResponseV2) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        convert(cardInfoResponseV2, cardInfoResponseV2DTO);
        if (cardInfoResponseV2.getAutoLoadState() == AutoLoadState.TOP_UP_AMOUNT_2.state()
                        || cardInfoResponseV2.getAutoLoadState() == AutoLoadState.TOP_UP_AMOUNT_3.state()) {
            cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        }
        return cardInfoResponseV2DTO;
    }

    @Override
    public CardInfoResponseV2DTO convertToDto(RequestFailure requestFailure) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        convert(requestFailure, cardInfoResponseV2DTO );
        return cardInfoResponseV2DTO;
    }


}
