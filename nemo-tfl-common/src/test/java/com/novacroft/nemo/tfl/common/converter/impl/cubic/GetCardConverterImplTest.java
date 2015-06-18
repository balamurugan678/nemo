package com.novacroft.nemo.tfl.common.converter.impl.cubic;

import static com.novacroft.nemo.test_support.GetCardTestUtil.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.Before;

import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

public class GetCardConverterImplTest {
    GetCardConverterImpl getCardConverterImpl;

    @Before
    public void setUp() {
        getCardConverterImpl = mock(GetCardConverterImpl.class, CALLS_REAL_METHODS);
    }

    @Test
    public void shouldAutoTopUpEnabledTrueWithAutoLoadStateNoTopUpConfigured() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardConverterImpl.convertToDto(getCardInfoRequestV2WithAutoLoadStateNoTopUpConfigured());
        assertFalse(cardInfoResponseV2DTO.isAutoTopUpEnabled());
    }

    @Test
    public void shouldAutoTopUpEnabledfalseWithAutoLoadState2TopUpConfigured() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardConverterImpl.convertToDto(getCardInfoRequestV2WithAutoLoadState2TopUpConfigured());
        assertTrue(cardInfoResponseV2DTO.isAutoTopUpEnabled());
    }

    @Test
    public void shouldAutoTopUpEnabledfalseWithAutoLoadState3TopUpConfigured() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardConverterImpl.convertToDto(getCardInfoRequestV2WithAutoLoadState3TopUpConfigured());
        assertTrue(cardInfoResponseV2DTO.isAutoTopUpEnabled());
    }

}
