package com.novacroft.nemo.common.converter.impl;

import com.novacroft.nemo.common.domain.ApplicationEvent;
import com.novacroft.nemo.common.transfer.ApplicationEventDTO;
import org.springframework.stereotype.Component;

/**
 * Event entity/DTO converter
 */
@Component(value = "applicationEventConverter")
public class ApplicationEventConverterImpl extends BaseDtoEntityConverterImpl<ApplicationEvent, ApplicationEventDTO> {

    @Override
    protected ApplicationEventDTO getNewDto() {
        return new ApplicationEventDTO();
    }

}
