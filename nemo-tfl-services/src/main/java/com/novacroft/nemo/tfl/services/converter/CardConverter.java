package com.novacroft.nemo.tfl.services.converter;

import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.services.transfer.Card;

public interface CardConverter {

	Card convert(CardInfoResponseV2DTO cardInfoResponseV2DTO);
	Card convert(CardDTO cardDTO);
}
