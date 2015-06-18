package com.novacroft.nemo.mock_cubic.service.card;

import org.w3c.dom.Document;

import com.novacroft.nemo.mock_cubic.command.AddCardPrePayTicketCmd;

public interface AddPrePayTicketService {
    
    Document createPrePayTicketResponse(AddCardPrePayTicketCmd cmd);
    
   

}
