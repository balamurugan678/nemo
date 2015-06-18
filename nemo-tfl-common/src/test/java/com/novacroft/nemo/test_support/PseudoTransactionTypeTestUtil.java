package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.transfer.PseudoTransactionTypeLookupDTO;

/**
 * Fixtures and utilities for tests involving pseudo transaction types (PTTs)
 */
public final class PseudoTransactionTypeTestUtil {

    public static final String TRANSACTION_DESCRIPTION_1 = "journey-test-description";
    public static final String TRANSACTION_DISPLAY_DESCRIPTION_1 = "journey-test-display-description";

    public static PseudoTransactionTypeLookupDTO getTestPseudoTransactionTypeLookupDTO1() {
        return getTestPseudoTransactionTypeLookupDTO(TRANSACTION_DESCRIPTION_1, TRANSACTION_DISPLAY_DESCRIPTION_1);
    }

    public static PseudoTransactionTypeLookupDTO getTestPseudoTransactionTypeLookupDTO(String description,
                                                                                       String displayDescription) {
        return new PseudoTransactionTypeLookupDTO(description, displayDescription);
    }

    private PseudoTransactionTypeTestUtil() {
    }
}
