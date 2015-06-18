package com.novacroft.nemo.tfl.common.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Seed values for the Apache hash code builder
 *
 * <p>Use prime numbers for the initialisers and mulitpliers.  <code>HashCodeSeedTest</code> has a dummy test method that
 * runs the prime generator.</p>
 */
public enum HashCodeSeed {

    AUTO_LOAD_REQUEST(31, 37),
    AUTO_LOAD_RESPONSE(41, 43),
    CARD_INFO_REQUEST_V2(47, 53),
    CARD_INFO_RESPONSE_V2(59, 61),
    REQUEST_FAILURE(67, 71),
    AD_HOC_DISTRIBUTION_RECORD(73, 79),
    AUTO_LOAD_CHANGE_REQUEST_DTO(83, 89),
    AUTO_LOAD_CHANGE_RESPONSE_DTO(97, 101),
    CARD_UPDATE_RESPONSE_DTO(103, 107),
    WEB_ACCOUNT_CREDIT_STATEMENT_LINE_DTO(109, 113),
    CARD_INFO_RESPONSE_V2_DTO(127, 131),
    JOURNEY_DAY_DTO(137, 139),
    JOURNEY_DTO(149, 151),
    JOURNEY_HISTORY_DTO(157, 163),
    TAP_DTO(167, 173),
    MANAGE_CARD_CMD(179, 181),
    ORDER_DTO(227, 229),
    CASE_HISTORY_NOTE(197, 199), 
    REFUND_ITEM(211, 223),
 CART_ITEM(235, 251),
    GOODWILL_ITEM(233,239);

    private HashCodeSeed(int initialiser, int multiplier) {
        this.initialiser = initialiser;
        this.multiplier = multiplier;
    }

    private int initialiser;
    private int multiplier;

    public int initialiser() {
        return this.initialiser;
    }

    public int multiplier() {
        return this.multiplier;
    }

    /**
     * Utility to generate prime numbers to use as initialisers and multipliers.
     */
    public static List<Integer> generatePrimes(int rangeFrom, int rangeTo) {
        List<Integer> primes = new ArrayList<Integer>();
        for (int i = rangeFrom; i <= rangeTo; i++) {
            if (isPrime(i)) {
                primes.add(i);
            }
        }
        return primes;
    }

    /**
     * Utility to check whether a number is prime.
     */
    public static boolean isPrime(int value) {
        for (int i = 2; i < ((value + 1) / 2); i++) {
            if (value % i == 0) {
                return false;
            }
        }
        return true;
    }
}
