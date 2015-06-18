package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.FailedAutoTopUpNotificationMessage;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpNotificationMessageDTO;

@Component("failedAutoTopUpNotificationMessageConverter")
public class FailedAutoTopUpNotificationMessageConverterImpl extends
                BaseDtoEntityConverterImpl<FailedAutoTopUpNotificationMessage, FailedAutoTopUpNotificationMessageDTO> {

    @Override
    protected FailedAutoTopUpNotificationMessageDTO getNewDto() {
        return new FailedAutoTopUpNotificationMessageDTO();
    }

}
