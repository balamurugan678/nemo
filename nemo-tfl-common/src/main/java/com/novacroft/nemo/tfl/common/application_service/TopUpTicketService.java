package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

public interface TopUpTicketService {
	boolean isOysterCardIncludesPendingOrExistingTravelCards(Long cardId);
	CartCmdImpl updateCartItemCmdWithProductsFromCubic(CartCmdImpl cartCmdImpl);
	void removeExpiredPrePaidTicketsInACard(CardDTO card, CardInfoResponseV2DTO cardInfo);
	CartDTO updateCartDTOWithProductsFromCubic(CartDTO cartDTO);
	CartDTO updateCartDTOWithProductsFromCubic(CartDTO cartDTO,CardInfoResponseV2DTO cardInfoResponseV2DTO);
}
