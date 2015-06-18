package com.novacroft.nemo.tfl.common.data_service.impl.cyber_source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.tfl.common.data_service.cyber_source.CyberSourceHeartbeatDataService;
import com.novacroft.nemo.tfl.common.service_access.cyber_source.CyberSourceHeartbeatServiceAccess;

/**
 * CyberSource payment gateway heartbeat data service
 */
@Service("cyberSourceHeartbeatDataService")
public class CyberSourceHeartbeatDataServiceImpl implements CyberSourceHeartbeatDataService {
    @Autowired
    protected CyberSourceHeartbeatServiceAccess cyberSourceHeartbeatServiceAccess;

    @Override
    public void checkHeartbeat(Long customerId) {
        this.cyberSourceHeartbeatServiceAccess.checkHeartbeat(customerId);
    }
}
