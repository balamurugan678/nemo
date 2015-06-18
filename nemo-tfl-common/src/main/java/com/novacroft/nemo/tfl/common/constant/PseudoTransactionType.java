package com.novacroft.nemo.tfl.common.constant;

/**
 * Journey Psuedo Transaction Type
 */
public enum PseudoTransactionType {
    UNKNOWN(0),
    MANUALLY_CORRECTED(508),
    PAY_AS_YOU_GO_CANCELLED_1(58),
    PAY_AS_YOU_GO_ADJUSTED_1(60),
    CHARGE_CORRECTED_1(63),
    CHARGE_CORRECTED_2(64),
    ROUTE_VALIDATION(65),
    COMPLETE_JOURNEY(500),
    UNSTARTED_JOURNEY(501),
    UNFINISHED_JOURNEY(502),
    START_NOT_REGISTERED(503),
    END_NOT_REGISTERED(504),
    SAME_STATION_EXIT_WITHIN_30_MINUTES(505),
    DISCOUNT_ADDED_TO_OYSTER_CARD(54),
    SEASON_TICKET_BOUGHT(56),
    SEASON_TICKET_REMOVED(57),
    OYSTER_CARD_CANCELLED(59),
    OYSTER_CARD_REPORTED_LOST_OR_STOLEN(10),
    PENDING_TRANSACTION(506),
    PAY_AS_YOU_GO_ENABLED(9),
    TOP_UP_1(2),
    PAY_AS_YOU_GO_ADJUSTED_2(3),
    DISABLE_NTM_CARD_CAPABILITIES(4),
    CANCEL_PPT(5),
    PAY_AS_YOU_GO_CANCELLED_2(6),
    SET_DISCOUNT_ENTITLEMENTS(7),
    SET_PERSONAL_DETAILS(8),
    ISSUE_PPT(11),
    ENTRY_1(12),
    ENTRY_2(13),
    VALIDATE_EXIT(14),
    MANUALLY_RE_ENABLE_NTM_CARD_CAPABILITIES(16),
    CANCEL_NTM_CARD(17),
    NTM_CARD_USAGE_STATISTICS(19),
    APPLY_LIFE_EXPIRY(20),
    CHANGE_NTM_CARD_KEYS(21),
    PRESTIGE_ENABLE_NTM_CARD(22),
    PRESTIGE_RE_ENABLE_NTM_CARD(23),
    PRESTIGE_INITIALISE_NTM_CARD(24),
    ISSUE_PERSONALISED_NTM_CARD(25),
    RTD_ACCOUNTING_PERIOD(26),
    RTD_OPERATION(27),
    RTD_POWER_FAIL_DETECTED(28),
    EMIRATES_AIR_LINE_TICKET_BOUGHT_USING_PAY_AS_YOU_GO(29),
    ISSUE_TRANSFERABLE_NTM_CARD(30),
    VOID_PURCHASE_USING_PAY_AS_YOU_GO(31),
    VOID_ENTRY(32),
    VOID_EXIT(33),
    VOID_BUS_BOARDING(34),
    VOID_ISSUE_PPT(35),
    PAY_AS_YOU_GO_CANCELLED(36),
    RIVERBOAT_TICKET_BOUGHT_USING_PAY_AS_YOU_GO(37),
    VOID_PURCHASE_CASH_TICKET_USING_PAY_AS_YOU_GO(38),
    ENTRY_CANCELLED_1(39),
    EXIT_CANCELLED(40),
    ENTRY_CANCELLED_2(41),
    TOP_UP_2(42),
    AD_HOC_ISSUE_PPT(43),
    AUTO_TOP_UP_CONFIGURATION(44),
    AUTO_TOP_UP(45),
    FAILED_AD_HOC_AUTO_TOP_UP_ACTION(46),
    ENTRY_3(47),
    ENTRY_CANCELLED(48),
    EXIT_1(49),
    CARD_NOT_TOUCHED_OUT(55),
    ENTRY(61),
    EXIT_2(62);

    private int pseudoTransactionTypeId;

    private PseudoTransactionType(int pseudoTransactionTypeId) {
        this.pseudoTransactionTypeId = pseudoTransactionTypeId;
    }

    public int pseudoTransactionTypeId() {
        return pseudoTransactionTypeId;
    }

    public static PseudoTransactionType getPseudoTransactionType(int pseudoTransactionTypeId) {
        for (PseudoTransactionType pseudoTransactionType : values()) {
            if (pseudoTransactionType.pseudoTransactionTypeId == pseudoTransactionTypeId) {
                return pseudoTransactionType;
            }
        }
        return null;
    }
}
