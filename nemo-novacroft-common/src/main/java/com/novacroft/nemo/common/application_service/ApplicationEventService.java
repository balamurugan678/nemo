package com.novacroft.nemo.common.application_service;

import java.util.List;

import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.common.transfer.ApplicationEventDTO;

public interface ApplicationEventService {
    List<ApplicationEventDTO> findApplicationEventsForCustomer(Long customerId);

    void create(Long customerId, EventName name, String additionalInformation);
    
}
