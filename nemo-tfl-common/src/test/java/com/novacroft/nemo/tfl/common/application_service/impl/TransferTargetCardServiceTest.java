package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardReponseWithPptSlots;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardReponseWithTotalPayAsYouGo;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardReponseWithoutPpts;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTONonHotlistedCard;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOPAYGBalanceLessForSourceCard;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOPAYGBalanceLessForTargetCard;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOPAYGBalanceMoreForSourceCard;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOPAYGBalanceMoreForTargetCard;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOPAYGBalanceWithoutGBPCurrency;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO2;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_2;
import static com.novacroft.nemo.test_support.TransferTestUtil.MAXIMUM_ALLOWED_TRAVEL_CARDS;
import static com.novacroft.nemo.test_support.TransferTestUtil.PAY_AS_YOU_GO_LIMIT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.exception.ServiceAccessException;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;

public class TransferTargetCardServiceTest {

    private TransferTargetCardServiceImpl transferTargetCardService;
    private List<CardDTO> mockList;

    @Mock
    private TransferTargetCardServiceImpl mockTransferTargetCardServiceImpl;

    @Mock
    private CardService mockCardService;

    @Mock
    private HotlistServiceImpl mockHotlistService;

    @Mock
    private GetCardService mockGetCardService;

    @Mock
    private SystemParameterService mockSystemParameterServiceImpl;

    @Mock
    private CardDataService mockCardDataService;

    @Mock
    private Model mockModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        transferTargetCardService = new TransferTargetCardServiceImpl();
        mockList = new ArrayList<CardDTO>();
        transferTargetCardService.cardService = mockCardService;
        transferTargetCardService.getCardService = mockGetCardService;
        transferTargetCardService.systemParameterService = mockSystemParameterServiceImpl;
        transferTargetCardService.hotlistService = mockHotlistService;
        transferTargetCardService.cardDataService = mockCardDataService;
        mockTransferTargetCardServiceImpl.cardDataService = mockCardDataService;
        when(mockSystemParameterServiceImpl.getParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);

    }

    @Test
    public void testIfTargetCardIsHotlisted() {
        when(mockHotlistService.isCardHotlisted(anyString())).thenReturn(true);
        assertTrue(transferTargetCardService.isTargetCardHotlisted(anyString()));
    }

    @Test
    public void testIfTargetCardIsNotHotlisted() {
        when(transferTargetCardService.getCardInfoResponseFromCubic(anyString())).thenReturn(getTestCardInfoResponseV2DTONonHotlistedCard());
        assertFalse(transferTargetCardService.isTargetCardHotlisted(anyString()));
    }

    @Test
    public void testCalculateBusyPrePayTicketSlots() {
        when(transferTargetCardService.getCardInfoResponseFromCubic(anyString())).thenReturn(getCardReponseWithPptSlots());
        assertEquals(1, transferTargetCardService.calculateBusyPrePayTicketSlots(OYSTER_NUMBER_1));
    }

    @Test
    public void testCalculateTotalNumberOfBusyPrePayTicketSlotsOfSourceAndTargetCard() {
        when(transferTargetCardService.getCardInfoResponseFromCubic(anyString())).thenReturn(getCardReponseWithPptSlots(),
                        getCardReponseWithPptSlots());
        assertEquals(3, transferTargetCardService.calculateTotalNumberOfBusyPrePayTicketSlotsOfSourceAndTargetCard(OYSTER_NUMBER_1, OYSTER_NUMBER_2));
    }

    @Test
    public void hasTargetCardReachedItsMaxLimitOfTravelCards() {
        when(transferTargetCardService.getCardInfoResponseFromCubic(anyString())).thenReturn(getCardReponseWithPptSlots());
        assertFalse(transferTargetCardService.hasTargetCardReachedItsMaxLimitOfTravelCards(OYSTER_NUMBER_1, OYSTER_NUMBER_2));
    }

    @Test
    public void hasTargetCardNotReachedItsMaxLimitOfTravelCards() {
        when(transferTargetCardService.getCardInfoResponseFromCubic(anyString())).thenReturn(getCardReponseWithPptSlots(),
                        getCardReponseWithoutPpts());
        when(mockSystemParameterServiceImpl.getIntegerParameterValue(anyString())).thenReturn(3);
        assertTrue(transferTargetCardService.hasTargetCardReachedItsMaxLimitOfTravelCards(OYSTER_NUMBER_1, OYSTER_NUMBER_2));
    }

    @Test
    public void testHasTooMuchPAYG() {
        when(transferTargetCardService.getCardInfoResponseFromCubic(anyString())).thenReturn(
                        getTestCardInfoResponseV2DTOPAYGBalanceLessForSourceCard(), getTestCardInfoResponseV2DTOPAYGBalanceLessForTargetCard());
        when(mockSystemParameterServiceImpl.getParameterValue(anyString())).thenReturn(PAY_AS_YOU_GO_LIMIT);
        assertTrue(transferTargetCardService.hasTooMuchPayAsYouGo(OYSTER_NUMBER_1, OYSTER_NUMBER_2));
    }

    @Test
    public void testHasAcceptableAmountOfPAYG() {
        when(transferTargetCardService.getCardInfoResponseFromCubic(anyString())).thenReturn(
                        getTestCardInfoResponseV2DTOPAYGBalanceMoreForSourceCard(), getTestCardInfoResponseV2DTOPAYGBalanceMoreForTargetCard());
        when(mockSystemParameterServiceImpl.getParameterValue(anyString())).thenReturn(PAY_AS_YOU_GO_LIMIT);
        assertFalse(transferTargetCardService.hasTooMuchPayAsYouGo(OYSTER_NUMBER_1, OYSTER_NUMBER_2));
    }

    @Test
    public void testTotalPendingPrePayValueItems() {
        assertEquals(new Integer(1000), transferTargetCardService.getTotalPendingPrePayValue(getCardReponseWithTotalPayAsYouGo()));
    }

    @Test(expected = ServiceAccessException.class)
    public void testTooMuchPAYGThrowsServiceException() {
        when(transferTargetCardService.getCardInfoResponseFromCubic(anyString())).thenReturn(
                        getTestCardInfoResponseV2DTOPAYGBalanceMoreForSourceCard(), getTestCardInfoResponseV2DTOPAYGBalanceWithoutGBPCurrency());
        when(mockSystemParameterServiceImpl.getParameterValue(anyString())).thenReturn(PAY_AS_YOU_GO_LIMIT);
        assertFalse(transferTargetCardService.hasTooMuchPayAsYouGo(OYSTER_NUMBER_1, OYSTER_NUMBER_2));
    }

    @Test
    public void testGetResponseFromCubic() {
        TransferTargetCardServiceImpl mockTransferTargetCardServiceImpl = mock(TransferTargetCardServiceImpl.class);
        mockTransferTargetCardServiceImpl.getCardService = mockGetCardService;
        when(mockTransferTargetCardServiceImpl.getCardInfoResponseFromCubic(anyString())).thenCallRealMethod();
        mockTransferTargetCardServiceImpl.getCardInfoResponseFromCubic(anyString());
        verify(mockTransferTargetCardServiceImpl, atLeast(1)).getCardInfoResponseFromCubic(anyString());
    }

    @Test
    public void testIsEligibleAsTargetCard() {

        TransferTargetCardServiceImpl mockTransferTargetCardServiceImpl = mock(TransferTargetCardServiceImpl.class);
        when(mockTransferTargetCardServiceImpl.isEligibleAsTargetCard(OYSTER_NUMBER_1, OYSTER_NUMBER_2)).thenCallRealMethod();
        when(mockTransferTargetCardServiceImpl.isTargetCardHotlisted(anyString())).thenReturn(false);

        when(mockTransferTargetCardServiceImpl.hasTooMuchPayAsYouGo(anyString(), anyString())).thenReturn(false);
        when(mockTransferTargetCardServiceImpl.hasTargetCardReachedItsMaxLimitOfTravelCards(anyString(), anyString())).thenReturn(false);
        when(mockTransferTargetCardServiceImpl.getCardInfoResponseFromCubic(anyString())).thenReturn(getCardReponseWithPptSlots());
        mockTransferTargetCardServiceImpl.isEligibleAsTargetCard(OYSTER_NUMBER_1, OYSTER_NUMBER_2);

        verify(mockTransferTargetCardServiceImpl, atLeast(1)).isTargetCardHotlisted(anyString());
        verify(mockTransferTargetCardServiceImpl, atLeast(1)).hasTooMuchPayAsYouGo(anyString(), anyString());
        verify(mockTransferTargetCardServiceImpl, atLeast(1)).hasTargetCardReachedItsMaxLimitOfTravelCards(anyString(), anyString());
    }

    @Test
    public void testPopulateCardsSelectList() {
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        mockList.add(getTestCardDTO2());
        when(mockCardDataService.getAllCardsFromUserExceptCurrent(anyString())).thenReturn(mockList);
        doReturn(Boolean.TRUE).when(mockTransferTargetCardServiceImpl).isEligibleAsTargetCard(anyString(), anyString());
        doCallRealMethod().when(mockTransferTargetCardServiceImpl).populateCardsSelectList(OYSTER_NUMBER_1, mockModel);
        mockTransferTargetCardServiceImpl.populateCardsSelectList(OYSTER_NUMBER_1, mockModel);
        ;
        verify(mockModel).addAttribute(anyString(), anyObject());
    }

}
