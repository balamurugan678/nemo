package com.novacroft.nemo.mock_payment_gateway.cyber_source.command;

import java.util.List;

import static com.novacroft.nemo.common.utils.ListUtil.getCommaDelimitedStringAsList;
import static com.novacroft.nemo.common.utils.ListUtil.getListAsCommaDelimitedString;

/**
 * Command class for soap web service reply settings
 */
public class SoapSettingsCmd {
    protected String decision = "0";
    protected String reasonCode = "";
    protected String missingFields = "";
    protected String invalidFields = "";

    public SoapSettingsCmd() {
    }

    public SoapSettingsCmd(String decision, String reasonCode, List<String> missingFields, List<String> invalidFields) {
        this.decision = decision;
        this.reasonCode = reasonCode;
        this.missingFields = getListAsCommaDelimitedString(missingFields);
        this.invalidFields = getListAsCommaDelimitedString(invalidFields);
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

    public String getMissingFields() {
        return missingFields;
    }

    public List<String> getMissingFieldsAsList() {
        return getCommaDelimitedStringAsList(this.invalidFields);
    }

    public void setMissingFields(String missingFields) {
        this.missingFields = missingFields;
    }

    public String getInvalidFields() {
        return invalidFields;
    }

    public List<String> getInvalidFieldsAsList() {
        return getCommaDelimitedStringAsList(this.invalidFields);
    }

    public void setInvalidFields(String invalidFields) {
        this.invalidFields = invalidFields;
    }
}

