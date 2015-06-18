package com.novacroft.nemo.common.data_service.impl;

import com.novacroft.nemo.common.converter.impl.ServiceCallLogConverterImpl;
import com.novacroft.nemo.common.data_access.ServiceCallLogDAO;
import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.data_service.ServiceCallLogDataService;
import com.novacroft.nemo.common.domain.ServiceCallLog;
import com.novacroft.nemo.common.transfer.ServiceCallLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service call log data service implementation.
 */
@Service(value = "serviceCallLogDataService")
@Transactional
public class ServiceCallLogDataServiceImpl extends BaseDataServiceImpl<ServiceCallLog, ServiceCallLogDTO>
        implements ServiceCallLogDataService {
    @Override
    public ServiceCallLog getNewEntity() {
        return new ServiceCallLog();
    }

    @Autowired
    public void setDao(ServiceCallLogDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(ServiceCallLogConverterImpl converter) {
        this.converter = converter;
    }

}
