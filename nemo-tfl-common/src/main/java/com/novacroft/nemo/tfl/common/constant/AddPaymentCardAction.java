package com.novacroft.nemo.tfl.common.constant;

/**
 * Add new payment card actions
 */
public enum AddPaymentCardAction {
    ADD("add"),
    ADD_AND_SAVE("addAndSave");

    private AddPaymentCardAction(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return this.code;
    }

    public static boolean isAddPaymentCardAction(String value) {
        for (AddPaymentCardAction addPaymentCardAction : AddPaymentCardAction.values()) {
            if (addPaymentCardAction.code().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
