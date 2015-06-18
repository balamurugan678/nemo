package com.novacroft.nemo.mock_cubic.application_service;

import com.novacroft.nemo.mock_cubic.command.AddRequestCmd;
import com.novacroft.nemo.mock_cubic.command.RemoveRequestCmd;
import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayTicketRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayValueRequest;

public interface UpdateCardService {

    String update(AddRequestCmd cmd);

    String remove(RemoveRequestCmd cmd);

    String update(CardUpdatePrePayValueRequest requestModel);

    String update(CardUpdatePrePayTicketRequest requestModel);

    String remove(CardRemoveUpdateRequest requestModel);

    String updatePending(AddRequestCmd cmd);
    
    String populatePrePayTicketFromPendingTicket(String cardNumber);
    
    String populatePrePayTicketFromPendingTicket2(String cardNumber);
    
}
