package com.novacroft.nemo.tfl.common.constant;

/**
 * Order Status
 */
public enum OrderStatus {
    NEW("New"),
    PAID("Paid"),
    COMPLETE("Completed"),
    CANCEL_REQUESTED("Cancel Requested"),
    CANCELLED("Cancelled"),
    ERROR("Error"),
    FULFILLED("Fulfilled"),
    FULFILMENT_PENDING("Fulfilment Pending"),
    AUTO_TOP_UP_REPLACEMENT_FULFILMENT_PENDING("ATU Replacement Fulfilment Pending"),
    REPLACEMENT("Replacement"),
    PAY_AS_YOU_GO_FULFILMENT_PENDING("PAYG Fulfilment Pending"),
    AUTO_TOP_UP_PAYG_FULFILMENT_PENDING("ATU PAYG Fulfilment Pending"),
    GOLD_CARD_PENDING("Gold Card Pending"),
    SELECTED_FOR_FULFILMENT("Selected For Fulfilment");
    private OrderStatus(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return this.code;
    }

    public static OrderStatus find(String code) {
        if (code != null) {
            for (OrderStatus orderStatus : OrderStatus.values()) {
                if (code.equalsIgnoreCase(orderStatus.code)) {
                    return orderStatus;
                }
            }
        }
        return null;
    }
}
