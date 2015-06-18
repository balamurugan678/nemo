package com.novacroft.nemo.tfl.services.application_service;

import java.util.List;

import com.novacroft.nemo.tfl.services.transfer.Item;
import com.novacroft.nemo.tfl.services.transfer.ListContainer;
import com.novacroft.nemo.tfl.services.transfer.PayAsYouGo;
import com.novacroft.nemo.tfl.services.transfer.PrePaidTicket;

public interface ProductService {
    
    Item getTravelCard(PrePaidTicket prePaidTicket);
    Item getPayAsYouGo(PayAsYouGo payAsYouGo);
    List<ListContainer> getReferenceData();
    Item getBusPass(PrePaidTicket prePaidTicket);
}
