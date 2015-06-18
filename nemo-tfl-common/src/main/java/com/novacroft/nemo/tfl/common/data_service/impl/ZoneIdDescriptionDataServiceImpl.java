package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.ZoneIdDescriptionConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.ZoneIdDescriptionDAO;
import com.novacroft.nemo.tfl.common.data_service.ZoneIdDescriptionDataService;
import com.novacroft.nemo.tfl.common.domain.ZoneIdDescription;
import com.novacroft.nemo.tfl.common.transfer.ZoneIdDescriptionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Zone id description data service implementation
 */
@Service(value = "zoneIdDescriptionDataService")
@Transactional(readOnly = true)
public class ZoneIdDescriptionDataServiceImpl extends BaseDataServiceImpl<ZoneIdDescription, ZoneIdDescriptionDTO>
        implements ZoneIdDescriptionDataService {
    static final Logger logger = LoggerFactory.getLogger(ZoneIdDescriptionDataServiceImpl.class);

    public ZoneIdDescriptionDataServiceImpl() {
        super();
    }

    @Autowired
    public void setDao(ZoneIdDescriptionDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(ZoneIdDescriptionConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public ZoneIdDescription getNewEntity() {
        return new ZoneIdDescription();
    }

}
