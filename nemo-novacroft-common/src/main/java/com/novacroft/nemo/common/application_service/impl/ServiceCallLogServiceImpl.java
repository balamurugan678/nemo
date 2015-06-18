package com.novacroft.nemo.common.application_service.impl;

import com.novacroft.nemo.common.application_service.ServiceCallLogService;
import com.novacroft.nemo.common.data_service.ServiceCallLogDataService;
import com.novacroft.nemo.common.transfer.ServiceCallLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Log service call service implementation
 */
@Service("serviceCallLogService")
public class ServiceCallLogServiceImpl implements ServiceCallLogService {
    @Autowired
    protected ServiceCallLogDataService serviceCallLogDataService;

    @Override
    public ServiceCallLogDTO initialiseCallLog(String serviceName, String userId, Long customerId) {
        return new ServiceCallLogDTO(serviceName, userId, customerId, new Date());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void finaliseCallLog(ServiceCallLogDTO serviceCallLogDTO, String request, String response) {
        serviceCallLogDTO.setRespondedAt(new Date());
        serviceCallLogDTO.setRequest(request);
        serviceCallLogDTO.setResponse(response);
        this.serviceCallLogDataService.createOrUpdate(serviceCallLogDTO);
    }
}
