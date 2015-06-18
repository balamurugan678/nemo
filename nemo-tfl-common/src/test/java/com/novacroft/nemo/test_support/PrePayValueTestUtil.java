package com.novacroft.nemo.test_support;

import com.novacroft.nemo.common.domain.cubic.PrePayValue;

/**
 * Utilities for Oyster card tests
 */
public final class PrePayValueTestUtil {
    
    public static final Integer CURRENCY = 1;    
    public static final Integer REQUEST_SEQUENCE_NUMBER = 12;
    public static final String REALTIME_FLAG = "N";
    public static final Integer PICKUP_LOCATION = 507;    
    public static final Integer PREPAY_VALUE = 50;    
    public static final Integer BALANCE_POSITIVE = 500;    
    public static final Integer BALANCE_NEGATIVE = -500;    
       
    public static PrePayValue getPendingItemsPpvTestWithPositiveBalance() {
        return getTestPendingItemsPpv(CURRENCY,        
                                      REQUEST_SEQUENCE_NUMBER,
                                      REALTIME_FLAG,
                                      PICKUP_LOCATION,        
                                      PREPAY_VALUE,
                                      BALANCE_POSITIVE);
    }

    public static PrePayValue getPendingItemsPpvTestWithNegativeBalance() {
        return getTestPendingItemsPpv(CURRENCY,        
                                      REQUEST_SEQUENCE_NUMBER,
                                      REALTIME_FLAG,
                                      PICKUP_LOCATION,        
                                      PREPAY_VALUE,
                                      BALANCE_NEGATIVE);
    }

    public static PrePayValue getTestPendingItemsPpv(Integer currency,
                                                     Integer requestSequenceNumber,
                                                     String realtimeFlag,
                                                     Integer pickupLocation,
                                                     Integer prepayValue,
                                                     Integer balance) {
        PrePayValue ppv = new PrePayValue();
        ppv.setRequestSequenceNumber(requestSequenceNumber);
        ppv.setRealTimeFlag(realtimeFlag);
        ppv.setPrePayValue(prepayValue);
        ppv.setCurrency(currency);
        ppv.setPickupLocation(pickupLocation);
        ppv.setBalance(balance);        
        return ppv;
    }
}
