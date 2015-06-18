package com.novacroft.nemo.mock_cubic.test_support;

import com.novacroft.nemo.common.domain.cubic.PrePayValue;

/**
 * Utilities for Oyster card tests
 */
public final class PendingItemsPpvTestUtil {
    
    public static final Integer CURRENCY = 1;    
    public static final Integer REQUEST_SEQUENCE_NUMBER = 12;
    public static final String REALTIME_FLAG = "N";
    public static final Integer PICKUP_LOCATION = 507;    
    public static final Integer PREPAY_VALUE = 50;    
       
    public static PrePayValue getPendingItemsPpvTest() {
        return getTestPendingItemsPpv(CURRENCY,        
                                      REQUEST_SEQUENCE_NUMBER,
                                      REALTIME_FLAG,
                                      PICKUP_LOCATION,        
                                      PREPAY_VALUE);
    }

    public static PrePayValue getTestPendingItemsPpv(Integer currency,
                                                     Integer requestSequenceNumber,
                                                     String realtimeFlag,
                                                     Integer pickupLocation,
                                                     Integer prepayValue) 
    {
        PrePayValue ppv = new PrePayValue();
        ppv.setRequestSequenceNumber(requestSequenceNumber);
        ppv.setRealTimeFlag(realtimeFlag);
        ppv.setPrePayValue(prepayValue);
        ppv.setCurrency(currency);
        ppv.setPickupLocation(pickupLocation);
        
        return ppv;
    }
}
