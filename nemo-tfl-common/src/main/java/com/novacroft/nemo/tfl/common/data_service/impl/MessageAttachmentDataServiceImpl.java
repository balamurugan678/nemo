package com.novacroft.nemo.tfl.common.data_service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.MessageAttachmentConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.MessageAttachmentDAO;
import com.novacroft.nemo.tfl.common.data_service.MessageAttachmentDataService;
import com.novacroft.nemo.tfl.common.domain.MessageAttachment;
import com.novacroft.nemo.tfl.common.transfer.MessageAttachmentDTO;

@Service(value = "messageAttachmentDataService")
@Transactional(readOnly = true)
public class MessageAttachmentDataServiceImpl extends BaseDataServiceImpl<MessageAttachment, MessageAttachmentDTO> implements MessageAttachmentDataService {

    @Override
    public MessageAttachment getNewEntity() {
        return new MessageAttachment();
    }

    @Autowired
    public void setDao(MessageAttachmentDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(MessageAttachmentConverterImpl converter) {
        this.converter = converter;
    }

}
