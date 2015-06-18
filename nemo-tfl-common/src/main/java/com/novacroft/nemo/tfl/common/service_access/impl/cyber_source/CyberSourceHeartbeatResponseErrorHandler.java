package com.novacroft.nemo.tfl.common.service_access.impl.cyber_source;

import com.novacroft.nemo.common.exception.ServiceNotAvailableException;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * Error handler for use with the CyberSource heartbeat service
 */
@Component("cyberSourceHeartbeatResponseErrorHandler")
public class CyberSourceHeartbeatResponseErrorHandler implements ResponseErrorHandler {
    protected static final Logger logger = LoggerFactory.getLogger(CyberSourceHeartbeatResponseErrorHandler.class);

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return !HttpStatus.OK.equals(clientHttpResponse.getStatusCode());
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        String message = String.format(PrivateError.CYBER_SOURCE_HEARTBEAT_DEAD.message(), clientHttpResponse.getStatusCode());
        logger.error(message);
        throw new ServiceNotAvailableException(message);
    }
}
