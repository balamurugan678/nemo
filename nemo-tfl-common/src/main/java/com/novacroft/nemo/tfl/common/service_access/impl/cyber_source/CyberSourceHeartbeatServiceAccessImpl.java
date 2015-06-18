package com.novacroft.nemo.tfl.common.service_access.impl.cyber_source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.novacroft.nemo.common.application_service.ServiceCallLogService;
import com.novacroft.nemo.common.exception.ServiceNotAvailableException;
import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.common.transfer.ServiceCallLogDTO;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.WebServiceName;
import com.novacroft.nemo.tfl.common.service_access.cyber_source.CyberSourceHeartbeatServiceAccess;

/**
 * CyberSource payment gateway heartbeat service access
 */
@Service("cyberSourceHeartbeatServiceAccess")
public class CyberSourceHeartbeatServiceAccessImpl implements CyberSourceHeartbeatServiceAccess {
    protected static final Logger logger = LoggerFactory.getLogger(CyberSourceHeartbeatServiceAccessImpl.class);

    @Autowired
    protected ServiceCallLogService serviceCallLogService;
    @Autowired
    protected NemoUserContext nemoUserContext;
    @Autowired(required = false)
    protected RestTemplate cyberSourceHeartbeatRestTemplate;
    @Autowired(required = false)
    protected ResponseErrorHandler cyberSourceHeartbeatResponseErrorHandler;

    @Value("${CyberSource.status.url:unknown}")
    protected String cyberSourceStatusUrl;

    @Override
    public void checkHeartbeat(Long customerId) {
        assert (this.cyberSourceHeartbeatRestTemplate != null);
        assert (this.cyberSourceHeartbeatResponseErrorHandler != null);
        ServiceCallLogDTO serviceCallLogDTO = this.serviceCallLogService
                .initialiseCallLog(WebServiceName.CYBER_SOURCE_HEARTBEAT.code(), this.nemoUserContext.getUserName(),
                        customerId);
        try {
            this.cyberSourceHeartbeatRestTemplate.setErrorHandler(this.cyberSourceHeartbeatResponseErrorHandler);
            try {
                this.cyberSourceHeartbeatRestTemplate.getForEntity(cyberSourceStatusUrl, String.class);
            } catch (RestClientException e) {
                String message = String.format(PrivateError.CYBER_SOURCE_HEARTBEAT_DEAD.message(), e.getMessage());
                logger.error(message, e);
                throw new ServiceNotAvailableException(message, e);
            }
        } finally {
            this.serviceCallLogService.finaliseCallLog(serviceCallLogDTO, StringUtil.EMPTY_STRING, StringUtil.EMPTY_STRING);
        }
    }
}
