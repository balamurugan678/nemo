package com.novacroft.nemo.tfl.common.data_service.cyber_source;

/**
 * CyberSource payment gateway heartbeat data service
 */
public interface CyberSourceHeartbeatDataService {
    void checkHeartbeat(Long customerId);
}
