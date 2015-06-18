package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.tfl.common.transfer.CardUpdatePrePayTicketRequestDTO;
import com.novacroft.nemo.tfl.common.transfer.CardUpdatePrePayValueRequestDTO;
import com.novacroft.nemo.tfl.common.transfer.CardUpdateResponseDTO;

/**
 * Service to add Pre Pay Ticket or Pre Pay Value to a card. Using the cubic service. 
 */
public interface CardUpdateRequestDataService {

    CardUpdateResponseDTO addPrePayTicket(CardUpdatePrePayTicketRequestDTO request);
    
    CardUpdateResponseDTO addPrePayValue(CardUpdatePrePayValueRequestDTO request, String cartType);
    
}
