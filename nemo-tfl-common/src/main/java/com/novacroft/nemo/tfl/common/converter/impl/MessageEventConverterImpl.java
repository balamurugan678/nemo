package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.MessageEvent;
import com.novacroft.nemo.tfl.common.transfer.MessageEventDTO;

@Component("messageEventConverterConverter")
public class MessageEventConverterImpl extends BaseDtoEntityConverterImpl<MessageEvent, MessageEventDTO> {

    @Override
    protected MessageEventDTO getNewDto() {
        return new MessageEventDTO();
    }

}
