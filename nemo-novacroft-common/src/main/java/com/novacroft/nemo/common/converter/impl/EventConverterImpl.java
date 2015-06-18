package com.novacroft.nemo.common.converter.impl;

import com.novacroft.nemo.common.domain.Event;
import com.novacroft.nemo.common.transfer.EventDTO;
import org.springframework.stereotype.Component;

/**
 * Event entity/DTO converter
 */
@Component(value = "eventConverter")
public class EventConverterImpl extends BaseDtoEntityConverterImpl<Event, EventDTO> {

    @Override
    protected EventDTO getNewDto() {
        return new EventDTO();
    }

}
