package com.novacroft.nemo.tfl.common.constant;

/**
 * Settlement status
 */
public enum SettlementStatus {
    NEW("New"),
    REQUESTED("Requested"),
    ACCEPTED("Accepted"),
    INCOMPLETE("Incomplete"),
    CANCELLED("Cancelled"),
    READY("Ready"),
    COMPLETE("Completed"),
    SUCCESSFUL("Successful"),
    FAILED("Failed"),
    ISSUED("Issued"),
    CLEARED("Cleared"),
    OUTDATED("Outdated"),
    READY_FOR_PICK_UP("ReadyForPickUp"),
    PICK_UP_EXPIRED("PickUpExpired"),
    PICKED_UP("PickedUp"),
    RESETTLEMENT("Resettlement"),
    OPEN("Open");

    private SettlementStatus(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return this.code;
    }
}
