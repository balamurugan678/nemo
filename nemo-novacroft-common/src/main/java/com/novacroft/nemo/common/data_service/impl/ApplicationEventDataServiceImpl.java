package com.novacroft.nemo.common.data_service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.converter.impl.ApplicationEventConverterImpl;
import com.novacroft.nemo.common.data_access.ApplicationEventDAO;
import com.novacroft.nemo.common.data_service.ApplicationEventDataService;
import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.domain.ApplicationEvent;
import com.novacroft.nemo.common.transfer.ApplicationEventDTO;

@Service(value = "applicationEventDataService")
@Transactional(readOnly = true)
public class ApplicationEventDataServiceImpl extends BaseDataServiceImpl<ApplicationEvent, ApplicationEventDTO>
        implements ApplicationEventDataService {

    @Autowired
    public void setDao(ApplicationEventDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(ApplicationEventConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public ApplicationEvent getNewEntity() {
        return new ApplicationEvent();
    }

    @Override
    public List<ApplicationEventDTO> findByCustomerId(Long customerId) {
        ApplicationEvent example = new ApplicationEvent();
        example.setCustomerId(customerId);
        List<ApplicationEvent> events = dao.findByExample(example);
        return convert(events);
    }

    @Override
    public List<ApplicationEventDTO> findByEventId(Long eventId) {
        ApplicationEvent example = new ApplicationEvent();
        example.setEventId(eventId);
        List<ApplicationEvent> events = dao.findByExample(example);
        return convert(events);
    }

    /**
     * Return all events that have happened for a Customer using their customerid
     */
    @Override
    public List<ApplicationEventDTO> findAllCustomerEventsByCustomerId(Long customerId) {
        List<ApplicationEventDTO> allEvents = new ArrayList<ApplicationEventDTO>();
        if (customerId != null) {
            List<ApplicationEventDTO> customerEvents = findByCustomerId(customerId);
            allEvents.addAll(customerEvents);
        }
        return allEvents;
    }

}
