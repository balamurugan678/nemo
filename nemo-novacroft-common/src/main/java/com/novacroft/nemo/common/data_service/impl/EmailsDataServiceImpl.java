package com.novacroft.nemo.common.data_service.impl;

import com.novacroft.nemo.common.converter.impl.EmailsConverterImpl;
import com.novacroft.nemo.common.data_access.EmailsDAO;
import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.data_service.EmailsDataService;
import com.novacroft.nemo.common.domain.Emails;
import com.novacroft.nemo.common.transfer.EmailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "emailsDataService")
@Transactional(readOnly = true)
public class EmailsDataServiceImpl extends BaseDataServiceImpl<Emails, EmailsDTO> implements EmailsDataService {
    @Autowired
    public void setDao(EmailsDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(EmailsConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public Emails getNewEntity() {
        return new Emails();
    }
}