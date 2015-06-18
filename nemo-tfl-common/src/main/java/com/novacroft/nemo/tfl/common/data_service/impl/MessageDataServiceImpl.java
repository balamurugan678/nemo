package com.novacroft.nemo.tfl.common.data_service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.MessageConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.MessageDAO;
import com.novacroft.nemo.tfl.common.data_service.MessageDataService;
import com.novacroft.nemo.tfl.common.domain.Message;
import com.novacroft.nemo.tfl.common.transfer.MessageDTO;

@Service(value = "messageDataService")
@Transactional(readOnly = true)
public class MessageDataServiceImpl extends BaseDataServiceImpl<Message, MessageDTO> implements MessageDataService {

    @Override
    public Message getNewEntity() {
        return new Message();
    }

    @Autowired
    public void setDao(MessageDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(MessageConverterImpl converter) {
        this.converter = converter;
    }

}
