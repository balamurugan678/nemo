package com.novacroft.nemo.common.application_service;

import com.novacroft.nemo.common.transfer.ServiceCallLogDTO;

/**
 * Log service call service specification
 */
public interface ServiceCallLogService {
    ServiceCallLogDTO initialiseCallLog(String serviceName, String userId, Long customerId);

    void finaliseCallLog(ServiceCallLogDTO serviceCallLogDTO, String request, String response);
}
