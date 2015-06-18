package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.MessageAttachment;
import com.novacroft.nemo.tfl.common.transfer.MessageAttachmentDTO;

@Component("messageAttachmentConverter")
public class MessageAttachmentConverterImpl extends BaseDtoEntityConverterImpl<MessageAttachment, MessageAttachmentDTO> {

    @Override
    protected MessageAttachmentDTO getNewDto() {
        return new MessageAttachmentDTO();
    }

}
