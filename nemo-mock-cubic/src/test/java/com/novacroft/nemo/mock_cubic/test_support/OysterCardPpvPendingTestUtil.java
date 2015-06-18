package com.novacroft.nemo.mock_cubic.test_support;

import com.novacroft.nemo.mock_cubic.transfer.OysterCardPpvPendingDTO;

/**
 * Utilities for Oyster card tests
 */
public final class OysterCardPpvPendingTestUtil {
    
    public static final String PRESTIGE_ID = "1234567890";    
    public static final Long REQUEST_SEQUENCE_NUMBER = 12L;
    public static final String REALTIME_FLAG = "N";
    public static final Long PREPAY_VALUE = 99L;
    public static final Long CURRENCY = 1L;
    public static final Long PICKUP_LOCATION = 765L;
    
       
    public static OysterCardPpvPendingDTO getTestOysterCardPpvPendingDTO1() 
    {
        return getTestOysterCardPpvPendingDTO(PRESTIGE_ID, 
                                              REQUEST_SEQUENCE_NUMBER,
                                              REALTIME_FLAG,
                                              PREPAY_VALUE,
                                              CURRENCY,
                                              PICKUP_LOCATION);
    }

    public static OysterCardPpvPendingDTO getTestOysterCardPpvPendingDTO(String prestigeId,
                                                                         Long requestSequenceNumber,
                                                                         String realtimeFlag,
                                                                         Long prepayValue,
                                                                         Long currency,                                                                        
                                                                         Long pickupLocation) 
    {
        OysterCardPpvPendingDTO oysterCardPpvPendingDTO = new OysterCardPpvPendingDTO();
        oysterCardPpvPendingDTO.setPrestigeId(prestigeId);
        oysterCardPpvPendingDTO.setRequestSequenceNumber(requestSequenceNumber);
        oysterCardPpvPendingDTO.setRealtimeFlag(realtimeFlag);
        oysterCardPpvPendingDTO.setCurrency(currency);
        return oysterCardPpvPendingDTO;
    }
}
