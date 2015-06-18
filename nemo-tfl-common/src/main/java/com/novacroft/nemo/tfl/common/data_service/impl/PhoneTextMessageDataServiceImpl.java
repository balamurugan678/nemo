package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.constant.MessageStatus;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.converter.impl.PhoneTextMessageConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.PhoneTextMessageDAO;
import com.novacroft.nemo.tfl.common.data_service.PhoneTextMessageDataService;
import com.novacroft.nemo.tfl.common.domain.PhoneTextMessage;
import com.novacroft.nemo.tfl.common.transfer.PhoneTextMessageDTO;

@Service(value = "phoneTextMessageDataService")
@Transactional(readOnly = true)
public class PhoneTextMessageDataServiceImpl extends BaseDataServiceImpl<PhoneTextMessage, PhoneTextMessageDTO> implements
                PhoneTextMessageDataService {

    @Autowired
    SystemParameterService systemParameterService;

    @Override
    public PhoneTextMessage getNewEntity() {
        return new PhoneTextMessage();
    }

    @Autowired
    public void setDao(PhoneTextMessageDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(PhoneTextMessageConverterImpl converter) {
        this.converter = converter;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public List<PhoneTextMessageDTO> findPhoneTextMessagesToBeSent() {
        final String hsql = "from PhoneTextMessage where status =? or status = ? and attempts < ?";
        List<PhoneTextMessage> results = dao.findByQuery(hsql, MessageStatus.REQUESTED.code(), MessageStatus.RETRY.code(),
                        systemParameterService.getIntegerParameterValue(SystemParameterCode.MAX_RETRIES_FOR_SENDING_MESSAGES.code()));
        return (results != null && results.size() > 0 ? convert(results) : null);
    }

}
