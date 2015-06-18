package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.AlternativeZoneMappingConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.AlternativeZoneMappingDAO;
import com.novacroft.nemo.tfl.common.data_service.AlternativeZoneMappingDataService;
import com.novacroft.nemo.tfl.common.domain.AlternativeZoneMapping;
import com.novacroft.nemo.tfl.common.transfer.AlternativeZoneMappingDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Alternative zone mapping data service implementation
 */
@Service(value = "alternativeZoneMappingDataService")
@Transactional(readOnly = true)
public class AlternativeZoneMappingDataServiceImpl
        extends BaseDataServiceImpl<AlternativeZoneMapping, AlternativeZoneMappingDTO>
        implements AlternativeZoneMappingDataService {
    static final Logger logger = LoggerFactory.getLogger(AlternativeZoneMappingDataServiceImpl.class);

    public AlternativeZoneMappingDataServiceImpl() {
        super();
    }

    @Autowired
    public void setDao(AlternativeZoneMappingDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(AlternativeZoneMappingConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public AlternativeZoneMapping getNewEntity() {
        return new AlternativeZoneMapping();
    }

    @Override
    public AlternativeZoneMappingDTO findByStartZoneAndEndZone(Integer startZone, Integer endZone) {
        final String hsql = "from AlternativeZoneMapping azm where azm.startZone = ? and azm.endZone = ?";
        if (startZone != null && endZone != null) {
            AlternativeZoneMapping alternativeZoneMapping = dao.findByQueryUniqueResult(hsql, startZone, endZone);
            if (alternativeZoneMapping != null) {
                return this.converter.convertEntityToDto(alternativeZoneMapping);
            }
        }
        return null;
    }

}
