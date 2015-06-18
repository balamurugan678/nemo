package com.novacroft.nemo.tfl.common.constant;

/**
 * Job Name
 */
public enum JobName {
    IMPORT_CURRENT_ACTION_LIST_CUBIC_FILE("ImportCurrentActionListCubicFile"),
    IMPORT_AD_HOC_DISTRIBUTION_CUBIC_FILE("ImportAdHocDistributionCubicFileJob"),
    IMPORT_AUTO_LOAD_CHANGES_CUBIC_FILE("importAutoLoadChangesCubicFileJob"),
    IMPORT_AUTO_LOADS_PERFORMED_CUBIC_FILE("importAutoLoadsPerformedCubicFileJob"),
    JOURNEY_HISTORY_MONTHLY_EMAIL("JourneyHistoryMonthlyEmail"),
    JOURNEY_HISTORY_WEEKLY_EMAIL("JourneyHistoryWeeklyEmail"),
    JOURNEY_HISTORY_MASTER_MONTHLY_EMAIL("JourneyHistoryMasterMonthlyEmail"),
    JOURNEY_HISTORY_MASTER_WEEKLY_EMAIL("JourneyHistoryMasterWeeklyEmail"),
    FINANCIAL_SERVICES_CENTRE_IMPORT_CHEQUES_PRODUCED("ImportFinancialServicesCentreChequesProducedFile"),
    FINANCIAL_SERVICES_CENTRE_IMPORT_CHEQUE_SETTLEMENTS("ImportFinancialServicesCentreChequeSettlementsFile"),
    FINANCIAL_SERVICES_CENTRE_IMPORT_OUTDATED_CHEQUES("ImportFinancialServicesCentreOutdatedChequesFile"),
    FINANCIAL_SERVICES_CENTRE_IMPORT_BACS_SETTLEMENTS("ImportFinancialServicesCentreBacsSettlementsFile"),
    FINANCIAL_SERVICES_CENTRE_IMPORT_BACS_FAILURES("ImportFinancialServicesCentreBacsFailuresFile"),
    EXPORT_HOTLISTCARD_REQUEST_FILE("ExportHotlistCardRequestFile"),
    UPLOAD_PRE_PAID_TICKET_PRICE_DATA("UploadPrePaidTicketPriceData"), 
    SEND_MESSAGES("SendMessages");

    private JobName(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return this.code;
    }
}
