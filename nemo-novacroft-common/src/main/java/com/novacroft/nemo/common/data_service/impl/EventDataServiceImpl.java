package com.novacroft.nemo.common.data_service.impl;

import com.novacroft.nemo.common.converter.impl.EventConverterImpl;
import com.novacroft.nemo.common.data_access.EventDAO;
import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.data_service.EventDataService;
import com.novacroft.nemo.common.domain.Event;
import com.novacroft.nemo.common.transfer.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "eventDataService")
@Transactional(readOnly = true)
public class EventDataServiceImpl extends BaseDataServiceImpl<Event, EventDTO> implements EventDataService {

    @Autowired
    public void setDao(EventDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(EventConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public Event getNewEntity() {
        return new Event();
    }

    @Override
    public EventDTO findByEventName(String name) {
        final String hsql = "from Event e where e.name = ?";
        Event event = dao.findByQueryUniqueResult(hsql, name);
        List<EventDTO> events = convert(event);
        if (events.size() == 0) {
            return null;
        }
        return events.get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<EventDTO> findAllByDisplayOnline(Integer display) {
        final String hsql = "from Event e where e.displayonline = ?";
        List<Event> events = dao.findByQuery(hsql, display);
        return convert(events);
    }

}