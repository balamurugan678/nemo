package com.novacroft.nemo.tfl.common.data_service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.ExternalUserConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.ExternalUserDAO;
import com.novacroft.nemo.tfl.common.data_service.ExternalUserDataService;
import com.novacroft.nemo.tfl.common.domain.ExternalUser;
import com.novacroft.nemo.tfl.common.transfer.ExternalUserDTO;

@Service(value = "externalUserDataService")
@Transactional(readOnly = true)
public class ExternalUserDataServiceImpl extends BaseDataServiceImpl<ExternalUser, ExternalUserDTO> implements ExternalUserDataService {
    static final Logger logger = LoggerFactory.getLogger(ExternalUserDataServiceImpl.class);

    @Autowired
    public void setDao(ExternalUserDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(ExternalUserConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public ExternalUser getNewEntity() {
        return new ExternalUser();
    }

    @Override
    public Long findExternalUserIdByUsername(String username) {
        final String hsql = "from ExternalUser ex where lower(ex.username) = ?";
        ExternalUser externalUser = this.dao.findByQueryUniqueResult(hsql, username.toLowerCase().trim());
        return (externalUser != null) ? externalUser.getId() : null;
    }
    
    @Override
    public ExternalUserDTO findByUsername(String username) {
        final String hsql = "from ExternalUser where lower(username) = ?";
        ExternalUser result = this.dao.findByQueryUniqueResult(hsql, username.toLowerCase().trim());
        return this.converter.convertEntityToDto(result);
    }

}
