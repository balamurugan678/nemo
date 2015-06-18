package com.novacroft.nemo.tfl.common.application_service.impl.cubic;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.application_service.cubic.CubicConnectorService;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.novacroft.nemo.tfl.common.constant.CubicAttribute.HOST;

/**
 * Service class for Cubic Connector
 */
@Service("cubicConnectorService")
public class CubicConnectorServiceImpl implements CubicConnectorService {
    static final Logger logger = LoggerFactory.getLogger(CubicConnectorServiceImpl.class);

    @Autowired
    protected SystemParameterService systemParameterService;

    @Override
    public HttpURLConnection getCubicConnection() {
        HttpURLConnection connection = null;
        URL url = null;
        try {
            String cubicServiceUrl = systemParameterService.getParameterValue(SystemParameterCode.CUBIC_SERVICE_URL.code());
            url = new URL(cubicServiceUrl);
            logger.debug(String.format("Connecting to CUBIC [%s]", url));
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty(HOST, url.getHost());
        } catch (IOException e) {
            String message = String.format("CUBIC connection failed; url [%s]; error [%s]", url, e.getMessage());
            logger.error(message);
            throw new ApplicationServiceException(message, e);
        }
        return connection;
    }
}
