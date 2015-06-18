package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.constant.MessageStatus;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.converter.impl.EmailMessageConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.EmailMessageDAO;
import com.novacroft.nemo.tfl.common.data_service.EmailMessageDataService;
import com.novacroft.nemo.tfl.common.domain.EmailMessage;
import com.novacroft.nemo.tfl.common.transfer.EmailMessageDTO;

@Service(value = "emailMessageDataService")
@Transactional(readOnly = true)
public class EmailMessageDataServiceImpl extends BaseDataServiceImpl<EmailMessage, EmailMessageDTO> implements EmailMessageDataService {

    @Autowired
    SystemParameterService systemParameterService;

    @Override
    public EmailMessage getNewEntity() {
        return new EmailMessage();
    }

    @Autowired
    public void setDao(EmailMessageDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(EmailMessageConverterImpl converter) {
        this.converter = converter;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public List<EmailMessageDTO> findEmailMessagesToBeSent() {
        final String hsql = "from EmailMessage where status =? or status = ? and attempts < ?";
        List<EmailMessage> results = dao.findByQuery(hsql, MessageStatus.REQUESTED.code(), MessageStatus.RETRY.code(),
                        systemParameterService.getIntegerParameterValue(SystemParameterCode.MAX_RETRIES_FOR_SENDING_MESSAGES.code()));
        return (results != null && results.size() > 0 ? convert(results) : null);
    }

}
