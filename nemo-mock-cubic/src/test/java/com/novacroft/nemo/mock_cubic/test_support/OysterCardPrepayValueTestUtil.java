package com.novacroft.nemo.mock_cubic.test_support;

import com.novacroft.nemo.mock_cubic.transfer.OysterCardPrepayValueDTO;

/**
 * Utilities for Oyster card tests
 */
public final class OysterCardPrepayValueTestUtil {
    
    public static final String PRESTIGE_ID = "1234567890";    
    public static final Long BALANCE = 50L;
    public static final Long CURRENCY = 1L;
    
       
    public static OysterCardPrepayValueDTO getTestOysterCardPrepayValueDTO1() 
    {
        return getTestOysterCardPrepayValueDTO(PRESTIGE_ID, 
                                               BALANCE,
                                               CURRENCY);
    }

    public static OysterCardPrepayValueDTO getTestOysterCardPrepayValueDTO(String prestigeId,
                                                                           Long balance,
                                                                           Long currency) 
    {
        OysterCardPrepayValueDTO oysterCardPrepayValueDTO = new OysterCardPrepayValueDTO();
        oysterCardPrepayValueDTO.setPrestigeId(prestigeId);
        oysterCardPrepayValueDTO.setBalance(balance);
        oysterCardPrepayValueDTO.setCurrency(currency);
        return oysterCardPrepayValueDTO;
    }
}
