package com.novacroft.nemo.tfl.common.data_service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.FailedAutoTopUpNotificationMessageConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.FailedAutoTopUpNotificationMessageDAO;
import com.novacroft.nemo.tfl.common.data_service.FailedAutoTopUpNotificationMessageDataService;
import com.novacroft.nemo.tfl.common.domain.FailedAutoTopUpNotificationMessage;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpNotificationMessageDTO;

@Service(value = "failedAutoTopUpNotificationMessageDataService")
@Transactional(readOnly = true)
public class FailedAutoTopUpNotificationMessageDataServiceImpl extends
                BaseDataServiceImpl<FailedAutoTopUpNotificationMessage, FailedAutoTopUpNotificationMessageDTO> implements
                FailedAutoTopUpNotificationMessageDataService {

    @Override
    public FailedAutoTopUpNotificationMessage getNewEntity() {
        return new FailedAutoTopUpNotificationMessage();
    }

    @Autowired
    public void setDao(FailedAutoTopUpNotificationMessageDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(FailedAutoTopUpNotificationMessageConverterImpl converter) {
        this.converter = converter;
    }

}
