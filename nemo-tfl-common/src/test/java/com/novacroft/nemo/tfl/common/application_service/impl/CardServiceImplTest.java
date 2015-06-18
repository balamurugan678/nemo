package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.ManageCardCmd;
import com.novacroft.nemo.tfl.common.constant.CubicConstant;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import org.junit.Before;
import org.junit.Test;

import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO2;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOWithAutoloadState;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.ManageCardCmdTestUtil.getTestManageCardCmd1;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for card service impl service
 */
public class CardServiceImplTest {

    private CardServiceImpl cardService;
    private CardServiceImpl mockCardService;
    private CustomerService mockCustomerService;
    private CardDataService mockCardDataService;
    private GetCardService mockGetCardService;
    private CardDTO mockCardDTO;

    @Before
    public void setUp() {
        cardService = new CardServiceImpl();
        mockCardService = mock(CardServiceImpl.class);
        mockCustomerService = mock(CustomerService.class);
        mockCardDataService = mock(CardDataService.class);
        mockGetCardService = mock(GetCardService.class);
        mockCardService.customerService = mockCustomerService;
        mockCardService.cardDataService = mockCardDataService;
        mockCardService.getCardService = mockGetCardService;
        cardService.customerService = mockCustomerService;
        cardService.cardDataService = mockCardDataService;
        cardService.getCardService = mockGetCardService;
        mockCardDTO = mock(CardDTO.class);
    }

    @Test
    public void shouldPopulateManageCardCmdWithCubicCardDetails() {
        doCallRealMethod().when(mockCardService)
                .populateManageCardCmdWithCubicCardDetails(anyString(), anyLong(), any(ManageCardCmd.class));
        when(mockCardDataService.findByCardNumber(anyString())).thenReturn(getTestCardDTO1());
        doNothing().when(mockCardService).checkAndPopulateManageCardCmd(anyLong(), anyLong(), any(ManageCardCmd.class));
        mockCardService.populateManageCardCmdWithCubicCardDetails(anyString(), anyLong(), any(ManageCardCmd.class));
        verify(mockCardDataService, atLeastOnce()).findByCardNumber(anyString());
        verify(mockCardService, atLeastOnce()).checkAndPopulateManageCardCmd(anyLong(), anyLong(), any(ManageCardCmd.class));
    }

    @Test
    public void shouldCheckAndPopulateManageCardCmd() {
        when(mockCustomerService.validateCustomerOwnsCard(anyLong(), anyLong())).thenReturn(true);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTOWithAutoloadState());
        cardService.checkAndPopulateManageCardCmd(CUSTOMER_ID_1, CARD_ID_1, getTestManageCardCmd1());
        verify(mockCustomerService, atLeastOnce()).validateCustomerOwnsCard(anyLong(), anyLong());
        verify(mockCardDataService, atLeastOnce()).findById(anyLong());
        verify(mockGetCardService, atLeastOnce()).getCard(anyString());
    }

    @Test
    public void shouldNotCheckAndPopulateManageCardCmd() {
        when(mockCustomerService.validateCustomerOwnsCard(anyLong(), anyLong())).thenReturn(false);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTOWithAutoloadState());
        cardService.checkAndPopulateManageCardCmd(CUSTOMER_ID_1, CARD_ID_1, getTestManageCardCmd1());
        verify(mockCustomerService, atLeastOnce()).validateCustomerOwnsCard(anyLong(), anyLong());
        verify(mockCardDataService, never()).findById(anyLong());
        verify(mockGetCardService, never()).getCard(anyString());
    }

    @Test
    public void shouldUpdatePassengerAndDiscountForCardItemCmd() {
        CartItemCmdImpl cartItem = new CartItemCmdImpl();
        cardService.updatePassengerAndDiscountForCardItemCmd(cartItem);
        assertEquals(CubicConstant.PASSENGER_TYPE_ADULT_CODE, cartItem.getPassengerType());
        assertEquals(CubicConstant.NO_DISCOUNT_TYPE_CODE, cartItem.getDiscountType());
    }

    @Test
    public void shouldGetAutoTopUpVisibleOptionForCardWithValidCardNumber() {
        when(mockCardDataService.findById(anyLong())).thenReturn(mockCardDTO);
        when(mockCardDTO.getCardNumber()).thenReturn(OYSTER_NUMBER_1);
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTOWithAutoloadState());
        cardService.getAutoTopUpVisibleOptionForCard(CARD_ID_1);
        assertTrue(cardService.getAutoTopUpVisibleOptionForCard(CARD_ID_1));
        verify(mockCardDataService, atLeastOnce()).findById(anyLong());
        verify(mockCardDTO, atLeastOnce()).getCardNumber();
        verify(mockGetCardService, atLeastOnce()).getCard(anyString());
    }

    @Test
    public void shouldNotGetAutoTopUpVisibleOptionForCardWithInvalidCardNumber() {
        when(mockCardDataService.findById(anyLong())).thenReturn(mockCardDTO);
        when(mockCardDTO.getCardNumber()).thenReturn(OYSTER_NUMBER_1);
        when(mockGetCardService.getCard(anyString())).thenReturn(null);
        cardService.getAutoTopUpVisibleOptionForCard(CARD_ID_1);
        assertFalse(cardService.getAutoTopUpVisibleOptionForCard(CARD_ID_1));
        verify(mockCardDataService, atLeastOnce()).findById(anyLong());
        verify(mockCardDTO, atLeastOnce()).getCardNumber();
        verify(mockGetCardService, atLeastOnce()).getCard(anyString());
    }

    @Test
    public void shouldNotGetAutoTopUpVisibleOptionForCardWithAutoTopUpDisabled() {
        when(mockCardDataService.findById(anyLong())).thenReturn(mockCardDTO);
        when(mockCardDTO.getCardNumber()).thenReturn(OYSTER_NUMBER_1);
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO2());
        cardService.getAutoTopUpVisibleOptionForCard(CARD_ID_1);
        assertFalse(cardService.getAutoTopUpVisibleOptionForCard(CARD_ID_1));
        verify(mockCardDataService, atLeastOnce()).findById(anyLong());
        verify(mockCardDTO, atLeastOnce()).getCardNumber();
        verify(mockGetCardService, atLeastOnce()).getCard(anyString());
    }

    @Test
    public void shouldGetCardDTOById() {
        cardService.getCardDTOById(CARD_ID_1);
        verify(mockCardDataService).findById(anyLong());
    }

    @Test
    public void shouldGetCardIdFromCardNumber() {
        cardService.getCardIdFromCardNumber(OYSTER_NUMBER_1);
        verify(mockCardDataService).findByCardNumber(anyString());
    }

}
