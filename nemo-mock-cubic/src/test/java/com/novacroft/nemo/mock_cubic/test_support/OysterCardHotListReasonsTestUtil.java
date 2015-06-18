package com.novacroft.nemo.mock_cubic.test_support;

import com.novacroft.nemo.mock_cubic.transfer.OysterCardHotListReasonsDTO;

/**
 * Utilities for Oyster card tests
 */
public final class OysterCardHotListReasonsTestUtil {
    
    public static final String PRESTIGE_ID = "100000098765";    
    public static final Long HOT_LIST_REASON_CODE = 654L;
    
       
    public static OysterCardHotListReasonsDTO getTestOysterCardHotListReasonsDTO1() 
    {
        return getTestOysterCardHotListReasonsDTO(PRESTIGE_ID, HOT_LIST_REASON_CODE);
    }

    public static OysterCardHotListReasonsDTO getTestOysterCardHotListReasonsDTO(String prestigeId,
                                                                                 Long hotListReasonCode) 
    {
        OysterCardHotListReasonsDTO oysterCardHotListReasonsDTO = new OysterCardHotListReasonsDTO();
        oysterCardHotListReasonsDTO.setPrestigeId(prestigeId);
        oysterCardHotListReasonsDTO.setHotListReasonCode(hotListReasonCode);
        return oysterCardHotListReasonsDTO;
    }
}
