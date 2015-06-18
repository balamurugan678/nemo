package com.novacroft.nemo.common.application_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.application_service.ApplicationEventService;
import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.common.data_service.ApplicationEventDataService;
import com.novacroft.nemo.common.data_service.EventDataService;
import com.novacroft.nemo.common.transfer.ApplicationEventDTO;
import com.novacroft.nemo.common.transfer.EventDTO;

@Service("applicationEventService")
public class ApplicationEventServiceImpl implements ApplicationEventService {

    @Autowired
    ApplicationEventDataService applicationEventDataService;

    @Autowired
    EventDataService eventDataService;

    @Override
    public List<ApplicationEventDTO> findApplicationEventsForCustomer(Long customerId) {
        List<ApplicationEventDTO> applicationEvents =
                applicationEventDataService.findAllCustomerEventsByCustomerId(customerId);
        for (ApplicationEventDTO applicationEvent : applicationEvents) {
            EventDTO event = eventDataService.findById(applicationEvent.getEventId());
            applicationEvent.setEventName(event.getName());
            applicationEvent.setEventDescription(event.getDescription());
        }
        return applicationEvents;
    }

    public void setApplicationEventDataService(ApplicationEventDataService applicationEventDataService) {
        this.applicationEventDataService = applicationEventDataService;
    }

    public void setEventDataService(EventDataService eventDataService) {
        this.eventDataService = eventDataService;
    }

    @Override
    @Transactional
    public void create(Long customerId, EventName name, String additionalInformation) {
        EventDTO event = this.eventDataService.findByEventName(name.code());
        ApplicationEventDTO applicationEvent =
                new ApplicationEventDTO(event.getId(), customerId, additionalInformation);
        this.applicationEventDataService.createOrUpdate(applicationEvent);
    }    
}