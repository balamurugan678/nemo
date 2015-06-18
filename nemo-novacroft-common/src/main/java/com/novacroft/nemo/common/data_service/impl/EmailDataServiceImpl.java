package com.novacroft.nemo.common.data_service.impl;

import com.novacroft.nemo.common.converter.impl.EmailConverterImpl;
import com.novacroft.nemo.common.data_access.EmailDAO;
import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.data_service.EmailDataService;
import com.novacroft.nemo.common.domain.Email;
import com.novacroft.nemo.common.transfer.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "emailDataService")
@Transactional(readOnly = true)
public class EmailDataServiceImpl extends BaseDataServiceImpl<Email, EmailDTO> implements EmailDataService {

    @Autowired
    public void setDao(EmailDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(EmailConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public Email getNewEntity() {
        return new Email();
    }

}
