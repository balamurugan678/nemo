package com.novacroft.nemo.tfl.common.constant;

/**
 * Work Flow Status
 */
public enum WorkFlowStatus {
    OPEN("O"), HOLD("H"), COMPLETE("C"), APPROVED("Approved"), REJECTED("Rejected"), PENDING_APPROVAL("Pending Approval");

    private WorkFlowStatus(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return this.code;
    }
}
