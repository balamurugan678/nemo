package com.novacroft.nemo.mock_payment_gateway.cyber_source.constant;

/**
 * Request Parameter constants
 */
public final class RequestParameter {

    public static final String TARGET_ACTION = "targetAction=";
    public static final String TARGET_ACTION_SIGN = TARGET_ACTION + "sign";
    public static final String TARGET_ACTION_UPDATE = TARGET_ACTION + "update";
    public static final String TARGET_ACTION_CANCEL = TARGET_ACTION + "cancel";

    private RequestParameter() {
    }
}
