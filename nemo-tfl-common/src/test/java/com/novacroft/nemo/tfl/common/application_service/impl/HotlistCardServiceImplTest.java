package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.HotlistReasonDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import org.junit.Before;
import org.junit.Test;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.HotlistReasonTestUtil.getHotlistReasonList;
import static com.novacroft.nemo.test_support.LostOrStolenCmdTestUtil.HOTLIST_REASON_ID_0;
import static com.novacroft.nemo.test_support.LostOrStolenCmdTestUtil.HOTLIST_REASON_ID_1;
import static com.novacroft.nemo.test_support.ManageCardCmdTestUtil.CARD_NUMBER_1;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class HotlistCardServiceImplTest {

    private HotlistCardServiceImpl hotlistCardService;
    private HotlistCardServiceImpl mockHotlistCardService;
    private CardDataService mockCardDataService;
    private HotlistReasonDataService mockHotlistReasonDataService;

    @Before
    public void setUp() throws Exception {
        hotlistCardService = new HotlistCardServiceImpl();
        mockCardDataService = mock(CardDataService.class);
        mockHotlistReasonDataService = mock(HotlistReasonDataService.class);
        hotlistCardService.cardDataService = mockCardDataService;
        hotlistCardService.hotlistReasonDataService = mockHotlistReasonDataService;
        mockHotlistCardService = mock(HotlistCardServiceImpl.class);
    }

    @Test
    public void shouldToggleCardHotlistedIfCardIsNotNull() {
        when(mockCardDataService.findByCardNumber(anyString())).thenReturn(getTestCardDTO1());
        when(mockCardDataService.createOrUpdate(any(CardDTO.class))).thenReturn(getTestCardDTO1());
        hotlistCardService.toggleCardHotlisted(CARD_NUMBER_1, HOTLIST_REASON_ID_1);
        assertNotNull(mockCardDataService.findByCardNumber(anyString()));
        assertNotNull(HOTLIST_REASON_ID_1);
        verify(mockCardDataService, atLeastOnce()).findByCardNumber(anyString());
        verify(mockCardDataService, atLeastOnce()).createOrUpdate(any(CardDTO.class));
    }

    @Test
    public void shouldToggleCardHotlistedIfHotlistReasonIdIsZero() {
        when(mockCardDataService.findByCardNumber(anyString())).thenReturn(getTestCardDTO1());
        when(mockCardDataService.createOrUpdate(any(CardDTO.class))).thenReturn(getTestCardDTO1());
        hotlistCardService.toggleCardHotlisted(CARD_NUMBER_1, HOTLIST_REASON_ID_0);
        assertNotNull(mockCardDataService.findByCardNumber(anyString()));
        assertEquals(HOTLIST_REASON_ID_0, Integer.valueOf(0));
        verify(mockCardDataService, atLeastOnce()).findByCardNumber(anyString());
        verify(mockCardDataService, atLeastOnce()).createOrUpdate(any(CardDTO.class));
    }

    @Test
    public void shouldNotToggleCardHotlistedIfCardIsNull() {
        when(mockCardDataService.findByCardNumber(anyString())).thenReturn(null);
        hotlistCardService.toggleCardHotlisted(CARD_NUMBER_1, HOTLIST_REASON_ID_1);
        assertNull(mockCardDataService.findByCardNumber(anyString()));
        assertNotNull(HOTLIST_REASON_ID_1);
        verify(mockCardDataService, atLeastOnce()).findByCardNumber(anyString());
        verify(mockCardDataService, never()).createOrUpdate(any(CardDTO.class));
    }

    @Test
    public void shouldGetHotlistReasonSelectList() {
        when(mockHotlistReasonDataService.findAll()).thenReturn(getHotlistReasonList());
        hotlistCardService.getHotlistReasonSelectList();
        assertTrue(mockHotlistReasonDataService.findAll().size() > 0);
        verify(mockHotlistReasonDataService, atLeastOnce()).findAll();
    }
}