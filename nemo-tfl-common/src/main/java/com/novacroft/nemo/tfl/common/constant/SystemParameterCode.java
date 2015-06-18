package com.novacroft.nemo.tfl.common.constant;

/**
 * TfL system parameter codes
 */
public enum SystemParameterCode {
    GHOST_EMAIL_ADDDRESS_START("ghostEmailAddressStart"),
    GHOST_EMAIL_ADDDRESS_END("ghostEmailAddressEnd"),
    ONLINE_SYSTEM_BASE_URI("onlineSystemBaseURI"),
    PAYMENT_GATEWAY_CURRENCY("paymentGatewayCurrency"),
    PAYMENT_GATEWAY_LOCALE("paymentGatewayLocale"),
    OYSTER_CARD_LENGTH("oysterCardLength"),
    ENCODING("UTF-8"),
    CUBIC_SERVICE_URL("cubicServiceUrl"),
    CUBIC_USERID("cubicUserId"),
    CUBIC_PASSWORD("cubicPassword"),
    CARDHOLDERDETAILS("cardHolderDetails"),
    FAILED_CARD_PRODUCTS_PAY_AS_YOU_GO_LIMIT("failedCardProducts.payAsYouGo.limit"),
    OYSTER_CARD_FULL_LENGTH("oysterCardFullLength"),
    OYSTER_CARD_CHECKSUM_LENGTH("oysterCardChecksumLength"),
    PAY_AS_YOU_GO_LIMIT("payAsYouGo.limit"),
    PAY_AS_YOU_GO_AD_HOC_REFUND_LIMIT("payAsYouGoAdHoc.limit"),
    PAY_AS_YOU_GO_AD_HOC_PURCHASE_LIMIT("payAsYouGoAdHoc.limit"),
    AD_HOC_REFUND_TARGET_OYSTER_CARD("adHocRefundTargetOysterCard.limit"),
    HOTLISTFILELOCATION("hotlistFileLocation"),
    HOTLISTFILEEXTENSION("hotlistFileExtension"),
    WEB_SERVICE_ONLINE_ACCESS_TIMEOUT("oyster.services.online.timeout"),
    WEB_SERVICE_BATCH_ACCESS_TIMEOUT("oyster.services.batch.timeout"),
    INNOVATOR_SYSTEM_BASE_URI("innovatorSystemBaseURI"),
    INNOVATOR_SYSTEM_NEMO_URI("innovatorSystemNemoURI"),
    INNOVATOR_SYSTEM_TIMEOUT_URI("innovatorSystemTimeOutURI"),
    INNOVATOR_SYSTEM_LOGIN_URI("innovatorSystemLoginURI"),
    SAVED_PAYMENT_CARD_MAX_NUMBER("savedPaymentCardMaxNumber"),
    ANONYMOUS_EMAIL_ADDDRESS_START("anonymousEmailAddressStart"),
    ANONYMOUS_EMAIL_ADDDRESS_END("anonymousEmailAddressEnd"),
    FSC_EXPORT_FILE_SUFFIX("fsc.exportFile.suffix"),
    FSC_EXPORT_FILE_CHEQUE_REQUEST_PREFIX("fsc.exportFile.cheque.request.prefix"),
    FSC_EXPORT_FILE_CHEQUE_STOP_PREFIX("fsc.exportFile.cheque.stop.prefix"),
    FSC_EXPORT_FILE_BACS_REQUEST_PREFIX("fsc.exportFile.bacs.request.prefix"),
    FSC_EXPORT_FILE_COMPANY_CODE("fsc.exportFile.companyCode"),
    FSC_EXPORT_FILE_CURRENCY("fsc.exportFile.currency"),
    FSC_EXPORT_FILE_CHEQUE_REQUEST_DOCUMENT_TYPE("fsc.exportFile.cheque.request.documentType"),
    FSC_EXPORT_FILE_CHEQUE_REQUEST_VENDOR_ACCOUNT_NUMBER("fsc.exportFile.cheque.request.vendor.accountNumber"),
    FSC_EXPORT_FILE_CHEQUE_REQUEST_GL_ACCOUNT_NUMBER("fsc.exportFile.cheque.request.gl.accountNumber"),
    FSC_EXPORT_FILE_CHEQUE_REQUEST_TAX_CODE("fsc.exportFile.cheque.request.taxCode"),
    FSC_EXPORT_FILE_BACS_REQUEST_DOCUMENT_TYPE("fsc.exportFile.bacs.request.documentType"),
    FSC_EXPORT_FILE_BACS_REQUEST_COMPANY_CODE("fsc.exportFile.bacs.request.companyCode"),
    FSC_EXPORT_FILE_BACS_REQUEST_CURRENCY("fsc.exportFile.bacs.request.currency"),
    FSC_EXPORT_FILE_BACS_REQUEST_ACCOUNT_NUMBER("fsc.exportFile.bacs.request.accountNumber"),
    FSC_EXPORT_FILE_BACS_REQUEST_VENDOR_ACCOUNT_NUMBER("fsc.exportFile.bacs.request.vendor.accountNumber"),
    FSC_EXPORT_FILE_BACS_REQUEST_GL_ACCOUNT_NUMBER("fsc.exportFile.bacs.request.gl.accountNumber"),
    FSC_EXPORT_FILE_BACS_REQUEST_TAX_CODE("fsc.exportFile.bacs.request.taxCode"),
    FSC_EXPORT_FILE_BACS_REQUEST_PAYEE_REF_PREFIX("fsc.exportFile.bacs.payee.ref.prefix"),
    GOODWILL_ANONYMOUS_REFUND_UPPER_LIMIT("goodwill.anonymous.refund.upper.limit"),
    MIN_START_DATE_FOR_CALENDAR("startDate.refunds.calendar.min.limit"),
    SINGLE_SIGN_ON_AUTHENTICATION_FLAG("singleSignOn.authenticationFlag"),
    SINGLE_SIGN_ON_BASE_URL("singleSignOn.baseUrl"),
    SINGLE_SIGN_ON_REDIRECT_URL("singleSignOn.entryPointRedirectUrl"),
    SINGLE_SIGN_ON_SERVER_ID("singleSignOn.serverID"),
    AUTOTOPUP_RESETTLEMENT_DAYS_FOR_WEEKLY_TRAVELCARD("autoTopUpResettlementPeriodDaysForWeeklyTravelcard"),
    AUTOTOPUP_RESETTLEMENT_DAYS_FOR_MONTHLY_TRAVELCARD("autoTopUpResettlementPeriodDaysForMonthlyTravelcard"),
    AUTOTOPUP_RESETTLEMENT_DAYS_FOR_3MONTH_TRAVELCARD("autoTopUpResettlementPeriodDaysFor3MonthTravelcard"),
    AUTOTOPUP_RESETTLEMENT_DAYS_FOR_6MONTH_TRAVELCARD("autoTopUpResettlementPeriodDaysFor6MonthTravelcard"),
    AUTOTOPUP_RESETTLEMENT_DAYS_FOR_ODD_PERIOD_TRAVELCARD("autoTopUpResettlementPeriodDaysForOddPeriodTravelcard"),
    AUTOTOPUP_RESETTLEMENT_DAYS_DEFAULT("defaultAutoTopUpResettlementPeriodDays"),
    MAX_RETRIES_FOR_SENDING_MESSAGES("maxRetriesForSendingMessages");
    
    private SystemParameterCode(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return this.code;
    }
    
    public static SystemParameterCode lookUp(String code) {
        if (code != null) {
            for (SystemParameterCode systemParameterCode : SystemParameterCode.values()) {
                if (code.equalsIgnoreCase(systemParameterCode.code)) {
                    return systemParameterCode;
                }
            }
        }
        return null;
    }
}
