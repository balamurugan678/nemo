package com.novacroft.nemo.tfl.common.data_service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.MessageEventConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.MessageEventDAO;
import com.novacroft.nemo.tfl.common.data_service.MessageEventDataService;
import com.novacroft.nemo.tfl.common.domain.MessageEvent;
import com.novacroft.nemo.tfl.common.transfer.MessageEventDTO;

@Service(value = "messageEventDataService")
@Transactional(readOnly = true)
public class MessageEventDataServiceImpl extends BaseDataServiceImpl<MessageEvent, MessageEventDTO> implements MessageEventDataService {

    @Override
    public MessageEvent getNewEntity() {
        return new MessageEvent();
    }

    @Autowired
    public void setDao(MessageEventDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(MessageEventConverterImpl converter) {
        this.converter = converter;
    }

}
