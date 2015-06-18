package com.novacroft.nemo.common.data_service;

import com.novacroft.nemo.common.domain.Event;
import com.novacroft.nemo.common.transfer.EventDTO;

import java.util.List;

public interface EventDataService extends BaseDataService<Event, EventDTO> {

    EventDTO findByEventName(String name);

    List<EventDTO> findAllByDisplayOnline(Integer display);

}
