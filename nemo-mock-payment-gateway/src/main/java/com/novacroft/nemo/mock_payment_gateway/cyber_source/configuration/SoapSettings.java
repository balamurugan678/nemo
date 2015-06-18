package com.novacroft.nemo.mock_payment_gateway.cyber_source.configuration;

import java.util.Collections;
import java.util.List;

/**
 * Bean to hold transaction web service reply settings
 */
public class SoapSettings {
    private static SoapSettings instance = null;
    protected String decision = "ACCEPT";
    protected String reasonCode = "100";
    @SuppressWarnings("unchecked")
    protected List<String> missingFields = Collections.EMPTY_LIST;
    @SuppressWarnings("unchecked")
    protected List<String> invalidFields = Collections.EMPTY_LIST;

    protected SoapSettings() {
    }

    public static SoapSettings getInstance() {
        if (instance == null) {
            instance = new SoapSettings();
        }
        return instance;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public List<String> getMissingFields() {
        return missingFields;
    }

    public void setMissingFields(List<String> missingFields) {
        this.missingFields = missingFields;
    }

    public List<String> getInvalidFields() {
        return invalidFields;
    }

    public void setInvalidFields(List<String> invalidFields) {
        this.invalidFields = invalidFields;
    }
}
