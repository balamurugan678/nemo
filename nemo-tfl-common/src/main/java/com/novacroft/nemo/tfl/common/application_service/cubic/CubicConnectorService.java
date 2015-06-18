package com.novacroft.nemo.tfl.common.application_service.cubic;

import java.net.HttpURLConnection;

/**
 * Cubic connector Service specification
 */
public interface CubicConnectorService {
    HttpURLConnection getCubicConnection();
}
