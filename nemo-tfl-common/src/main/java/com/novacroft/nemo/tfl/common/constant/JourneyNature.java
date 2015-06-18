package com.novacroft.nemo.tfl.common.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * Journey characteristics of interest.  Associates natures with pseudo transaction types or identifiers.
 */
public enum JourneyNature {
    WARNING(PseudoTransactionType.UNSTARTED_JOURNEY, PseudoTransactionType.UNFINISHED_JOURNEY,
            PseudoTransactionType.START_NOT_REGISTERED, PseudoTransactionType.END_NOT_REGISTERED,
            PseudoTransactionType.SAME_STATION_EXIT_WITHIN_30_MINUTES, PseudoTransactionType.PENDING_TRANSACTION),
    AUTO_COMPLETED(89, 121),
    TOP_UP(PseudoTransactionType.TOP_UP_1, PseudoTransactionType.TOP_UP_2, PseudoTransactionType.AUTO_TOP_UP),
    FARE_CAPPED(89, 121),
    PAY_AS_YOU_GO_USED(89, 121),
    TFR_DISCOUNTS_APPLIED(89, 121),
    TRAVEL_CARD_USED(89, 121),
    UNSTARTED(PseudoTransactionType.UNSTARTED_JOURNEY, PseudoTransactionType.START_NOT_REGISTERED),
    UNFINISHED(PseudoTransactionType.UNFINISHED_JOURNEY, PseudoTransactionType.END_NOT_REGISTERED);
    

    private JourneyNature() {
    }

    private JourneyNature(PseudoTransactionType... transactionTypes) {
        for (int i = 0; i < transactionTypes.length; i++) {
            this.transactionTypes.add(transactionTypes[i]);
        }
    }

    private JourneyNature(Integer... identifiers) {
        for (int i = 0; i < identifiers.length; i++) {
            this.identifiers.add(identifiers[i]);
        }
    }

    private Set<PseudoTransactionType> transactionTypes = new HashSet<PseudoTransactionType>();
    private Set<Integer> identifiers = new HashSet<Integer>();

    public Set<Integer> identifiers() {
        return this.identifiers;
    }

    public static Set<JourneyNature> getNatures(int pseudoTransactionTypeId) {
        Set<JourneyNature> natures = new HashSet<JourneyNature>();
        for (JourneyNature journeyNature : values()) {
            if (journeyNature.transactionTypes
                    .contains(PseudoTransactionType.getPseudoTransactionType(pseudoTransactionTypeId))) {
                natures.add(journeyNature);
            }
        }
        return natures;
    }
}
