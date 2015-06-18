package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.test_support.AutoLoadConfigurationChangeTestUtil.getTestRequestFailure1;
import static com.novacroft.nemo.test_support.AutoLoadConfigurationChangeTestUtil.TEST_ERROR_CODE_1;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getCardUpdateResponse;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.PRESTIGE_ID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.transfer.CardUpdateResponseDTO;

public class CardUpdateChangeConverterTest {
    private CardUpdateChangeConverter converter;
    
    @Before
    public void setUp() {
        converter = new CardUpdateChangeConverter();
    }
    
    @Test
    public void shouldConvertCardUpdateResponseToDTO() {
        CardUpdateResponseDTO actualResult = converter.convertToDto(getCardUpdateResponse());
        
        assertNotNull(actualResult);
        assertEquals(PRESTIGE_ID, actualResult.getPrestigeId());
    }
    
    @Test
    public void shouldConvertRequestFailureToDTO() {
        CardUpdateResponseDTO actualResult = converter.convertToDto(getTestRequestFailure1());
        
        assertNotNull(actualResult);
        assertEquals(TEST_ERROR_CODE_1, actualResult.getErrorCode());
    }
}
