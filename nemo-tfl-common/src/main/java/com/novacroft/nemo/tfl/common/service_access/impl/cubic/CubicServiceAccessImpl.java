package com.novacroft.nemo.tfl.common.service_access.impl.cubic;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.application_service.cubic.CubicConnectorService;
import com.novacroft.nemo.tfl.common.service_access.cubic.CubicServiceAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;

/**
 * todo: refactor class to use RestTemplate
 */
@Service("cubicServiceAccess")
public class CubicServiceAccessImpl implements CubicServiceAccess {
    protected static final Logger logger = LoggerFactory.getLogger(CubicServiceAccessImpl.class);

    @Autowired
    protected CubicConnectorService cubicConnectorService;

    @Override
    public StringBuffer callCubic(String request) {
        HttpURLConnection connection = this.cubicConnectorService.getCubicConnection();
        return getResponseFromRequest(connection, request);
    }

    protected StringBuffer getResponseFromRequest(HttpURLConnection connection, String xmlRequest) {
        StringBuffer xmlResponse = new StringBuffer();
        sendRequest(connection, xmlRequest);
        xmlResponse = getResponseFromConnection(connection);
        return xmlResponse;
    }

    protected void sendRequest(HttpURLConnection connection, String request) {
        try {
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            PrintWriter writer = new PrintWriter(connection.getOutputStream());
            writer.print(request);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new ApplicationServiceException(e);
        }
    }

    protected StringBuffer getResponseFromConnection(HttpURLConnection connection) {
        StringBuffer response = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                response.append(line);
                response.append("\n");
            }
            reader.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new ApplicationServiceException(e);
        }
        return response;
    }
}
