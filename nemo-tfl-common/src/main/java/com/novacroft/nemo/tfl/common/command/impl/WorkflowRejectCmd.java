package com.novacroft.nemo.tfl.common.command.impl;

public class WorkflowRejectCmd {

    protected String rejectReason;
    protected String rejectFreeText;

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getRejectFreeText() {
        return rejectFreeText;
    }

    public void setRejectFreeText(String rejectFreeText) {
        this.rejectFreeText = rejectFreeText;
    }
}
