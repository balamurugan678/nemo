package com.novacroft.nemo.tfl.common.constant;

public enum ItemType {
	TRAVEL_CARD("Travelcard"), BUS_PASS("Bus Pass"), ODD_PERIOD_TICKET("Odd Period Ticket"), PAY_AS_YOU_GO("Pay as you go"), ADMINISTRATION_FEE("administrationFee"), AUTO_TOP_UP("Auto top up"), REFUNDABLE_DEPOSIT_AMOUNT("Refundable Deposit Amount");

    private String code;

    ItemType(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}