package com.novacroft.nemo.tfl.common.constant;

/**
 * Job Group
 */
public enum JobGroup {
    JOURNEY_HISTORY_STATEMENT("JourneyHistoryStatement"),
    CUBIC_IMPORT("CubicImport"),
    FINANCIAL_SERVICES_CENTRE_IMPORT("FinancialServicesCentreImport"),
    HOTLISTED_CARDS_EXPORT("HotListedCardsExport"),
    PRE_PAID_TICKET_DATA_IMPORT("PrePaidTicketDataImport"),
    MESSAGES("Messages");

    private JobGroup(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return this.code;
    }
}
