package com.novacroft.nemo.tfl.common.constant;

public enum TicketType {
	AUTO_TOP_UP("autoTopUp"),PAY_AS_YOU_GO("payAsYouGo"), PAY_AS_YOU_GO_AUTO_TOP_UP("payAsYouGoAutoTopUp"), BUS_PASS("annualBusTramPass"), TRAVEL_CARD("travelCard"), GOODWILL("goodwill"), ADMINISTRATION_FEE("administrationFee");

    private String code;

    TicketType(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public static TicketType lookUpTicketType(String code) {
        if (code != null) {
            for (TicketType ticketType : TicketType.values()) {
                if (code.equalsIgnoreCase(ticketType.code)) {
                    return ticketType;
                }
            }
        }
        return null;
    }

}