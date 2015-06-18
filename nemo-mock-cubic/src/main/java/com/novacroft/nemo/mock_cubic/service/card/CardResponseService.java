package com.novacroft.nemo.mock_cubic.service.card;

import org.w3c.dom.Document;

import com.novacroft.nemo.mock_cubic.command.AddCardResponseCmd;
import com.novacroft.nemo.mock_cubic.constant.CardAction;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoRequestV2;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayTicketRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayValueRequest;

/**
 * Interface for CardResponse Service.
 */
public interface CardResponseService {
    void addGetCardResponse(String prestigeId, Document xmlDocument, CardAction cardAction);

    Document createGetCardResponseXML(final AddCardResponseCmd cmd);

    String getCardDetails(final String prestigeId, final String action);

    String getCardDetails(final Document document);

    String getCardDetails(final CardInfoRequestV2 cardInfoRequestV2);

    String getCardUpdatePrePayTicket(final CardUpdatePrePayTicketRequest request);

    String getCardUpdatePrePayValue(final CardUpdatePrePayValueRequest request);
}
