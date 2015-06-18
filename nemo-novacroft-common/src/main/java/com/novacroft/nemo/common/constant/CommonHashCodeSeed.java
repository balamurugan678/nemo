package com.novacroft.nemo.common.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Seed values for the Apache hash code builder
 *
 * <p>Use prime numbers for the initialisers and mulitpliers.  <code>HashCodeSeedTest</code> has a dummy test method that
 * runs the prime generator.</p>
 */
public enum CommonHashCodeSeed {

    SELECT_LIST_OPTION_DTO(31, 37),
    APPLICATION_EVENT(191, 193),
    COMMON_ADDRESS_DTO(227, 229),
    ADDRESS_DTO(233, 239),
    CONTACT_DTO(241, 251);

    private CommonHashCodeSeed(int initialiser, int multiplier) {
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
