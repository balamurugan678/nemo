package com.novacroft.nemo.mock_cubic.application_service;

import com.novacroft.nemo.mock_cubic.command.AddCardResponseCmd;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoRequestV2;

public interface OysterCardDetailsService {

    void createOrUpdateOysterCard(AddCardResponseCmd cmd);

    void createOrUpdateCard(AddCardResponseCmd cmd);

    void createOrUpdateCardPptPending(AddCardResponseCmd cmd);

    void createOrUpdateCardPpvPending(AddCardResponseCmd cmd);

    void createOrUpdateCardPrepayTicket(AddCardResponseCmd cmd);

    void createOrUpdateCardPrepayValue(AddCardResponseCmd cmd);
    
    String getCardDetails(final CardInfoRequestV2 cardInfoRequestV2);
    
    void freePrePayTicketSlots(AddCardResponseCmd cmd);
}
