package com.novacroft.nemo.tfl.common.constant;

/**
 * Web Service Name
 */
public enum WebServiceName {
    OYSTER_JOURNEY_GET_HISTORY("OysterJourneyGetHistory"),
    CUBIC_CHANGE_AUTO_LOAD_CONFIGURATION("CubicChangeAutoLoadConfiguration"),
    CUBIC_ADD_PRE_PAY_VALUE("CubicAddPrePayValue"),
    CUBIC_ADD_PRE_PAY_TICKET("CubicAddPrePayTicket"),
	CUBIC_REMOVE_PENDING_UPDATE("CubicRemovePendingUpdate"),
    CYBER_SOURCE_RUN_TRANSACTION("CyberSourceRunTransaction"),
    FAIR_AGGREGATION_ENGINE("FairAggregationEngine"),
    NOTIFICATIONS_STATUS_SERVICE("NotificationsStatusService"),
    CYBER_SOURCE_HEARTBEAT("CyberSourceHeartbeat"),
    NOTIFICATIONS_SERVICE_GET_INCOMPLETEJOURNEYS("NotificationsServiceGetIncompleteJourneys");

    private WebServiceName(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return this.code;
    }
}
