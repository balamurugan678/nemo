package com.novacroft.nemo.mock_payment_gateway.cyber_source.constant;

/**
 * Request name constants
 */
public final class RequestName {

    public static final String GATEWAY_REQUEST = "CyberSource/SecureAcceptance/standard/pay";
    public static final String SOAP_SETTINGS = "SoapSettings.htm";
    public static final String PARAMETER_SPY = "ParameterSpy.htm";
    public static final String HEARTBEAT = "status";
    public static final String POST_SETTINGS = "PostSettings.htm";

    private RequestName() {
    }
}
