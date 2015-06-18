package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.DurationConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.DurationDAO;
import com.novacroft.nemo.tfl.common.data_service.DurationDataService;
import com.novacroft.nemo.tfl.common.domain.Duration;
import com.novacroft.nemo.tfl.common.transfer.DurationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service(value = "durationDataService")
@Transactional(readOnly = true)
public class DurationDataServiceImpl extends BaseDataServiceImpl<Duration, DurationDTO> implements DurationDataService {

    @Override
    public Duration getNewEntity() {
        return new Duration();
    }

    @Autowired
    public void setDao(DurationDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(DurationConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public DurationDTO findByCode(String code, Date effectiveDate) {
        String hql = "from Duration d where d.code = :code ";
        hql = hql + getPeriodClause(effectiveDate, "d");
        final Map<String, Object> namedParameterMap = new HashMap<>();

        if (effectiveDate != null) {
            namedParameterMap.put("effectiveDate", effectiveDate);
        }
        namedParameterMap.put("code", code);
        final Duration duration = dao.findByQueryUniqueResultUsingNamedParameters(hql, namedParameterMap);
        return duration == null ? null : converter.convertEntityToDto(duration);
    }

}
