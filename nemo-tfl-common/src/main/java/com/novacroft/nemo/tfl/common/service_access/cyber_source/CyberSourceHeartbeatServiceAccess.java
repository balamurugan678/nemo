package com.novacroft.nemo.tfl.common.service_access.cyber_source;

/**
 * CyberSource payment gateway heartbeat service access
 */
public interface CyberSourceHeartbeatServiceAccess {
    void checkHeartbeat(Long customerId);
}
