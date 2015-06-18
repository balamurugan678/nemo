package com.novacroft.nemo.tfl.common.application_service.impl.cubic;

import com.novacroft.nemo.common.domain.cubic.PrePayTicketDetails;
import com.novacroft.nemo.common.domain.cubic.PrePayTicketSlot;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.service_access.cubic.CubicServiceAccess;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1;
import static com.novacroft.nemo.test_support.GetCardTestUtil.PRESTIGE_ID_1;
import static com.novacroft.nemo.test_support.PrePayValueTestUtil.getPendingItemsPpvTestWithNegativeBalance;
import static com.novacroft.nemo.test_support.PrePayValueTestUtil.getPendingItemsPpvTestWithPositiveBalance;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class CubicCardServiceImplTest {

    private CubicCardServiceImpl service;
    private CubicCardServiceImpl mockService;
    private GetCardService mockGetCardService;
    private CubicServiceAccess mockCubicServiceAccess;

    private CardInfoResponseV2DTO mockCardInfoResponseV2DTO;
    private PrePayTicketDetails mockPrePayTicketDetails;
    private PrePayTicketSlot mockPrePayTicketSlot;

    private List<PrePayTicketSlot> prePayTicketSlots;

    @Before
    public void setUp() {
        service = new CubicCardServiceImpl();
        mockService = mock(CubicCardServiceImpl.class);

        mockGetCardService = mock(GetCardService.class);
        mockCubicServiceAccess = mock(CubicServiceAccess.class);

        service.getCardService = mockGetCardService;
        mockService.getCardService = mockGetCardService;
        mockService.cubicServiceAccess = mockCubicServiceAccess;

        mockCardInfoResponseV2DTO = mock(CardInfoResponseV2DTO.class);
        mockPrePayTicketDetails = mock(PrePayTicketDetails.class);
        mockPrePayTicketSlot = mock(PrePayTicketSlot.class);

        this.prePayTicketSlots = new ArrayList<PrePayTicketSlot>();
        this.prePayTicketSlots.add(this.mockPrePayTicketSlot);
    }

    @Test
    public void checkCardPrePayValueStatusReturnMessageShouldReturnBalance() {
        doCallRealMethod().when(mockService).checkCardPrePayValueStatusReturnMessage(PRESTIGE_ID_1, mockCardInfoResponseV2DTO);
        when(mockCardInfoResponseV2DTO.getPpvDetails()).thenReturn(getPendingItemsPpvTestWithPositiveBalance());
        mockService.checkCardPrePayValueStatusReturnMessage(PRESTIGE_ID_1, mockCardInfoResponseV2DTO);
        assertTrue(mockCardInfoResponseV2DTO.getPpvDetails().getBalance() > 0);
    }

    @Test
    public void checkCardPrePayValueStatusReturnMessageShouldNotReturnBalance() {
        doCallRealMethod().when(mockService).checkCardPrePayValueStatusReturnMessage(PRESTIGE_ID_1, mockCardInfoResponseV2DTO);
        when(mockCardInfoResponseV2DTO.getPpvDetails()).thenReturn(getPendingItemsPpvTestWithNegativeBalance());
        mockService.checkCardPrePayValueStatusReturnMessage(PRESTIGE_ID_1, mockCardInfoResponseV2DTO);
        assertFalse(mockCardInfoResponseV2DTO.getPpvDetails().getBalance() > 0);
    }

    @Test
    public void checkCardStatusReturnMessage() {
        doCallRealMethod().when(mockService).checkCardStatusReturnMessage(anyString());
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO1());
        mockService.checkCardStatusReturnMessage(anyString());
        verify(mockService, atLeastOnce()).checkCardStatusReturnMessage(anyString());
    }

    @Test
    public void checkCardPrePayTicketStatusReturnMessageShouldReturnBlank() {
        when(this.mockService.checkCardPrePayTicketStatusReturnMessage(anyString(), any(CardInfoResponseV2DTO.class)))
                .thenCallRealMethod();
        when(this.mockCardInfoResponseV2DTO.getPptDetails()).thenReturn(this.mockPrePayTicketDetails);
        when(this.mockPrePayTicketDetails.getPptSlots()).thenReturn(Collections.EMPTY_LIST);
        assertEquals(StringUtils.EMPTY, this.mockService
                .checkCardPrePayTicketStatusReturnMessage(CardTestUtil.CARD_ID.toString(), this.mockCardInfoResponseV2DTO));
    }

    @Test
    public void checkCardPrePayTicketStatusReturnMessageShouldReturnMessage() {
        when(this.mockService.checkCardPrePayTicketStatusReturnMessage(anyString(), any(CardInfoResponseV2DTO.class)))
                .thenCallRealMethod();
        when(this.mockService.isPrePaidTicketExpired(any(PrePayTicketSlot.class))).thenReturn(false);
        when(this.mockCardInfoResponseV2DTO.getPptDetails()).thenReturn(this.mockPrePayTicketDetails);
        when(this.mockPrePayTicketDetails.getPptSlots()).thenReturn(this.prePayTicketSlots);

        when(this.mockService.getContent(anyString())).thenReturn("expired");
        when(this.mockService
                .getContent(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn("slot-status");

        assertNotNull(this.mockService
                .checkCardPrePayTicketStatusReturnMessage(CardTestUtil.CARD_ID.toString(), this.mockCardInfoResponseV2DTO));

        verify(this.mockService).getContent(anyString());
        verify(this.mockService)
                .getContent(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    public void checkCardPrePayTicketStatusReturnMessageShouldReturnExpiredMessage() {
        when(this.mockService.checkCardPrePayTicketStatusReturnMessage(anyString(), any(CardInfoResponseV2DTO.class)))
                .thenCallRealMethod();
        when(this.mockService.isPrePaidTicketExpired(any(PrePayTicketSlot.class))).thenReturn(true);
        when(this.mockCardInfoResponseV2DTO.getPptDetails()).thenReturn(this.mockPrePayTicketDetails);
        when(this.mockPrePayTicketDetails.getPptSlots()).thenReturn(this.prePayTicketSlots);

        when(this.mockService.getContent(anyString())).thenReturn("expired");
        when(this.mockService
                .getContent(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn("slot-status");

        assertNotNull(this.mockService
                .checkCardPrePayTicketStatusReturnMessage(CardTestUtil.CARD_ID.toString(), this.mockCardInfoResponseV2DTO));

        verify(this.mockService, atLeast(2)).getContent(anyString());
        verify(this.mockService)
                .getContent(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    public void isPrePaidTicketExpiredShouldReturnTrue() {
        when(this.mockService.isPrePaidTicketExpired(any(PrePayTicketSlot.class))).thenCallRealMethod();
        when(this.mockPrePayTicketSlot.getExpiryDate()).thenReturn(DateUtil.formatDate(DateUtil.getDayBefore(new Date())));
        assertTrue(this.mockService.isPrePaidTicketExpired(this.mockPrePayTicketSlot));
    }

    @Test
    public void isPrePaidTicketExpiredShouldReturnFalse() {
        when(this.mockService.isPrePaidTicketExpired(any(PrePayTicketSlot.class))).thenCallRealMethod();
        when(this.mockPrePayTicketSlot.getExpiryDate()).thenReturn(DateUtil.formatDate(DateUtil.getTomorrowDate()));
        assertFalse(this.mockService.isPrePaidTicketExpired(this.mockPrePayTicketSlot));
    }
}
