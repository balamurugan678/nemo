package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.Message;
import com.novacroft.nemo.tfl.common.transfer.MessageDTO;

@Component("messageConverterConverter")
public class MessageConverterImpl extends BaseDtoEntityConverterImpl<Message, MessageDTO> {

    @Override
    protected MessageDTO getNewDto() {
        return new MessageDTO();
    }

}
