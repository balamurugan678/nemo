package com.novacroft.nemo.common.data_service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.constant.CommonPrivateError;
import com.novacroft.nemo.common.converter.impl.SystemParameterConverterImpl;
import com.novacroft.nemo.common.data_access.SystemParameterDAO;
import com.novacroft.nemo.common.data_cache.CachingDataService;
import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.data_service.SystemParameterDataService;
import com.novacroft.nemo.common.domain.SystemParameter;
import com.novacroft.nemo.common.exception.DataServiceException;
import com.novacroft.nemo.common.transfer.SystemParameterDTO;

/**
 * SystemParameter data service implementation.
 */
@Service(value = "systemParameterDataService")
@Transactional(readOnly = true)
public class SystemParameterDataServiceImpl extends BaseDataServiceImpl<SystemParameter, SystemParameterDTO> implements SystemParameterDataService,
                CachingDataService {
    static final Logger logger = LoggerFactory.getLogger(SystemParameterDataServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public final SystemParameterDTO findByCode(final String code) {
        final String hSql = "select p from SystemParameter p where p.code = ?";
        final List<SystemParameter> results = dao.findByQuery(hSql, code);
        if (results.size() > 1) {
            final String msg = String.format(CommonPrivateError.MORE_THAN_ONE_PARAMETER_FOR_CODE.message(), code);
            logger.error(msg);
            throw new DataServiceException(msg);
        }
        if (results.iterator().hasNext()) {
            return this.converter.convertEntityToDto(results.iterator().next());
        }
        return null;
    }

    @Override
    public final Date findLatestCreatedDateTime() {
        final String hsql = "select max(c.createdDateTime) from SystemParameter c";
        return findLatest(hsql);
    }

    @Override
    @Transactional(readOnly = true)
    public final Date findLatestModifiedDateTime() {
        final String hsql = "select max(c.modifiedDateTime) from SystemParameter c";
        return findLatest(hsql);
    }

    @SuppressWarnings("unchecked")
    protected final Date findLatest(final String hsql) {
        final List<Date> results = dao.findByQuery(hsql);
        if (results.iterator().hasNext()) {
            return (Date) results.iterator().next();
        }
        return null;
    }

    @Override
    public final SystemParameter getNewEntity() {
        return new SystemParameter();
    }

    @Autowired
    public final void setDao(final SystemParameterDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public final void setConverter(final SystemParameterConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public final List<SystemParameterDTO> listAllParameters() {
        return convert(dao.findAll());
    }
}
