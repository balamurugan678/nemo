package com.novacroft.nemo.common.converter.impl;

import com.novacroft.nemo.common.domain.ServiceCallLog;
import com.novacroft.nemo.common.transfer.ServiceCallLogDTO;
import org.springframework.stereotype.Component;

/**
 * Service call log entity/DTO converter.
 */
@Component(value = "serviceCallLogConverter")
public class ServiceCallLogConverterImpl extends BaseDtoEntityConverterImpl<ServiceCallLog, ServiceCallLogDTO> {
    @Override
    protected ServiceCallLogDTO getNewDto() {
        return new ServiceCallLogDTO();
    }
}
