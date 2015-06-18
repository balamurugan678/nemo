package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.PRESTIGE_ID;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getCardUpdatePrePayTicketRequestDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayTicketRequest;

public class CardUpdatePrePayTicketChangeConverterImplTest {
    
    @Test
    public void shouldConvertToModel() {
        CardUpdatePrePayTicketChangeConverterImpl converter = new CardUpdatePrePayTicketChangeConverterImpl();
        CardUpdatePrePayTicketRequest actualResult = converter.convertToModel(getCardUpdatePrePayTicketRequestDTO1());
        
        assertNotNull(actualResult);
        assertEquals(PRESTIGE_ID, actualResult.getPrestigeId());
    }
    
}
