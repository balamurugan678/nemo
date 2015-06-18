package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.ZoneConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.ZoneDAO;
import com.novacroft.nemo.tfl.common.data_service.ZoneDataService;
import com.novacroft.nemo.tfl.common.domain.Zone;
import com.novacroft.nemo.tfl.common.transfer.ZoneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service(value = "zoneDataService")
@Transactional(readOnly = true)
public class ZoneDataServiceImpl extends BaseDataServiceImpl<Zone, ZoneDTO> implements ZoneDataService {

    @Override
    public Zone getNewEntity() {
        return new Zone();
    }

    @Autowired
    public void setDao(ZoneDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(ZoneConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public ZoneDTO findByCode(String code, Date effectiveDate) {
        String hql = "from Zone z where z.code = :code ";
        hql = hql + getPeriodClause(effectiveDate, "z");
        final Map<String, Object> namedParameterMap = new HashMap<>();

        if (effectiveDate != null) {
            namedParameterMap.put("effectiveDate", effectiveDate);
        }
        namedParameterMap.put("code", code);
        final Zone zone = dao.findByQueryUniqueResultUsingNamedParameters(hql, namedParameterMap);
        return zone == null ? null : converter.convertEntityToDto(zone);
    }

}
