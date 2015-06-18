package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getCardUpdatePrePayValueRequestDTO1;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.PRESTIGE_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayValueRequest;

public class CardUpdatePrePayValueChangeConverterImplTest {
    
    @Test
    public void shouldConvertToModel() {
        CardUpdatePrePayValueChangeConverterImpl converter = new CardUpdatePrePayValueChangeConverterImpl();
        CardUpdatePrePayValueRequest actualResult = converter.convertToModel(getCardUpdatePrePayValueRequestDTO1());
        
        assertNotNull(actualResult);
        assertEquals(PRESTIGE_ID, actualResult.getPrestigeId());
    }
    
}
