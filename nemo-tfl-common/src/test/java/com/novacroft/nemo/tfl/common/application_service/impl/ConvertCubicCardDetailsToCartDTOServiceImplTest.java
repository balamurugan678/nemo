package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardReponseWithTravelCardOddPeriodDuration;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO2;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO3;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO3WithInValidState;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO3WithValidState;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO18;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTOWithInvalidCardNumber;
import static com.novacroft.nemo.test_support.ProductTestUtil.getTestProductDTO1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil;
import com.novacroft.nemo.test_support.CardRefundableDepositTestUtil;
import com.novacroft.nemo.test_support.PrePaidTicketTestUtil;
import com.novacroft.nemo.test_support.ProductItemTestUtil;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.data_service.AutoTopUpDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CardRefundableDepositDataService;
import com.novacroft.nemo.tfl.common.data_service.DiscountTypeDataService;
import com.novacroft.nemo.tfl.common.data_service.PassengerTypeDataService;
import com.novacroft.nemo.tfl.common.data_service.PayAsYouGoDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.DiscountTypeDTO;
import com.novacroft.nemo.tfl.common.transfer.PassengerTypeDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

public class ConvertCubicCardDetailsToCartDTOServiceImplTest {

    private ConvertCubicCardDetailsToCartDTOServiceImpl service;
    private CardDataService mockCardDataService;
    private GetCardService mockGetCardService;
    private ProductDataService mockProductDataService;
    private AutoTopUpDataService mockAutoTopUpDataService;
    private PayAsYouGoDataService mockPayAsYouGoDataService;
    private CardRefundableDepositDataService mockCardRefundableDepositDataService;
    private PassengerTypeDataService mockPassengerTypeDataService;
    private DiscountTypeDataService mockDiscountTypeDataService;

    @Before
    public void setup() {
        service = new ConvertCubicCardDetailsToCartDTOServiceImpl();

        mockCardDataService = mock(CardDataService.class);
        mockGetCardService = mock(GetCardService.class);
        mockProductDataService = mock(ProductDataService.class);
        mockAutoTopUpDataService = mock(AutoTopUpDataService.class);
        mockPayAsYouGoDataService = mock(PayAsYouGoDataService.class);
        mockCardRefundableDepositDataService = mock(CardRefundableDepositDataService.class);
        mockPassengerTypeDataService = mock(PassengerTypeDataService.class);
        mockDiscountTypeDataService = mock(DiscountTypeDataService.class);

        service.cardDataService = mockCardDataService;
        service.getCardService = mockGetCardService;
        service.productDataService = mockProductDataService;
        service.autoTopUpDataService = mockAutoTopUpDataService;
        service.payAsYouGoDataService = mockPayAsYouGoDataService;
        service.cardRefundableDepositDataService = mockCardRefundableDepositDataService;
        service.passengerTypeDataService = mockPassengerTypeDataService;
        service.discountTypeDataService = mockDiscountTypeDataService;
    }

    @Test
    public void shouldPopulateCartItemsToCartDTOFromCubic() {
        when(mockCardDataService.findById(CARD_ID_1)).thenReturn(getTestCardDTO1());
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getTestCardInfoResponseV2DTO3();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setAutoLoadState(2);
        AutoTopUpDTO autoTopUpDto = new AutoTopUpDTO();
        autoTopUpDto.setId(new Long(12));
        CardInfoResponseV2DTO cardInfoResponseV2DTO2 = CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1();
        cardInfoResponseV2DTO.setPpvDetails(cardInfoResponseV2DTO2.getPpvDetails());
        cardInfoResponseV2DTO.setCardDeposit(500);
        PayAsYouGoDTO payAsYouGoDTO = new PayAsYouGoDTO();
        payAsYouGoDTO.setId(new Long(10));
        when(mockAutoTopUpDataService.findByAutoTopUpAmount(anyInt())).thenReturn(autoTopUpDto);
        when(mockPayAsYouGoDataService.findByTicketPrice(anyInt())).thenReturn(payAsYouGoDTO);
        when(mockGetCardService.getCard(anyString())).thenReturn(cardInfoResponseV2DTO);
        when(mockCardRefundableDepositDataService.findByPrice(anyInt())).thenReturn(CardRefundableDepositTestUtil.getTestCardRefundableDepositDTO1());
        PassengerTypeDTO passengerTypeDTO = PrePaidTicketTestUtil.getTestPassengerTypeDTO();
        DiscountTypeDTO discountTypeDTO = PrePaidTicketTestUtil.getTestDiscountTypeDTO();
        when(mockPassengerTypeDataService.findByName(anyString())).thenReturn(passengerTypeDTO);
        when(mockDiscountTypeDataService.findByName(anyString())).thenReturn(discountTypeDTO);
        CartDTO cartDTO = service.populateCartItemsToCartDTOFromCubic(new CartDTO(), new Long(5012));
        assertEquals(getTestCardInfoResponseV2DTO3().getPptDetails().getPptSlots().size() + 3, cartDTO.getCartItems().size());
    }

    @Test
    public void shouldNotUpdateCartDTOWithPayAsYouGoItemWhenPayAsYouGoIsNull() {
        when(mockCardDataService.findById(CARD_ID_1)).thenReturn(getTestCardDTO1());
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getTestCardInfoResponseV2DTO3();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setAutoLoadState(2);
        AutoTopUpDTO autoTopUpDto = new AutoTopUpDTO();
        autoTopUpDto.setId(new Long(12));
        CardInfoResponseV2DTO cardInfoResponseV2DTO2 = CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1();
        cardInfoResponseV2DTO.setPpvDetails(cardInfoResponseV2DTO2.getPpvDetails());
        cardInfoResponseV2DTO.setCardDeposit(500);
        PayAsYouGoDTO payAsYouGoDTO = null;
        when(mockAutoTopUpDataService.findByAutoTopUpAmount(anyInt())).thenReturn(autoTopUpDto);
        when(mockPayAsYouGoDataService.findByTicketPrice(anyInt())).thenReturn(payAsYouGoDTO);
        when(mockGetCardService.getCard(anyString())).thenReturn(cardInfoResponseV2DTO);
        when(mockCardRefundableDepositDataService.findByPrice(anyInt())).thenReturn(CardRefundableDepositTestUtil.getTestCardRefundableDepositDTO1());
        PassengerTypeDTO passengerTypeDTO = PrePaidTicketTestUtil.getTestPassengerTypeDTO();
        DiscountTypeDTO discountTypeDTO = PrePaidTicketTestUtil.getTestDiscountTypeDTO();
        when(mockPassengerTypeDataService.findByName(anyString())).thenReturn(passengerTypeDTO);
        when(mockDiscountTypeDataService.findByName(anyString())).thenReturn(discountTypeDTO);
        CartDTO cartDTO = service.populateCartItemsToCartDTOFromCubic(new CartDTO(), new Long(5012));
        assertEquals(getTestCardInfoResponseV2DTO3().getPptDetails().getPptSlots().size() + 2, cartDTO.getCartItems().size());
    }

    @Test
    public void shouldNotUpdateCartDTOWithPayAsYouGoItemWhenPpvIsNull() {
        when(mockCardDataService.findById(CARD_ID_1)).thenReturn(getTestCardDTO1());
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getTestCardInfoResponseV2DTO3();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setAutoLoadState(2);
        AutoTopUpDTO autoTopUpDto = new AutoTopUpDTO();
        autoTopUpDto.setId(new Long(12));
        CardInfoResponseV2DTO cardInfoResponseV2DTO2 = CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOWithEmptyPPV();
        cardInfoResponseV2DTO.setPpvDetails(cardInfoResponseV2DTO2.getPpvDetails());
        cardInfoResponseV2DTO.setCardDeposit(500);
        when(mockAutoTopUpDataService.findByAutoTopUpAmount(anyInt())).thenReturn(autoTopUpDto);
        when(mockGetCardService.getCard(anyString())).thenReturn(cardInfoResponseV2DTO);
        when(mockCardRefundableDepositDataService.findByPrice(anyInt())).thenReturn(CardRefundableDepositTestUtil.getTestCardRefundableDepositDTO1());
        PassengerTypeDTO passengerTypeDTO = PrePaidTicketTestUtil.getTestPassengerTypeDTO();
        DiscountTypeDTO discountTypeDTO = PrePaidTicketTestUtil.getTestDiscountTypeDTO();
        when(mockPassengerTypeDataService.findByName(anyString())).thenReturn(passengerTypeDTO);
        when(mockDiscountTypeDataService.findByName(anyString())).thenReturn(discountTypeDTO);
        CartDTO cartDTO = service.populateCartItemsToCartDTOFromCubic(new CartDTO(), new Long(5012));
        assertEquals(getTestCardInfoResponseV2DTO3().getPptDetails().getPptSlots().size() + 2, cartDTO.getCartItems().size());
        verify(mockPayAsYouGoDataService, never()).findByTicketPrice(anyInt());
    }

    @Test
    public void shouldNotUpdateCartDTOWithAutoTopItem() {
        when(mockCardDataService.findById(CARD_ID_1)).thenReturn(getTestCardDTO1());
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getTestCardInfoResponseV2DTO3();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(false);
        cardInfoResponseV2DTO.setAutoLoadState(2);
        CardInfoResponseV2DTO cardInfoResponseV2DTO2 = CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1();
        cardInfoResponseV2DTO.setPpvDetails(cardInfoResponseV2DTO2.getPpvDetails());
        cardInfoResponseV2DTO.setCardDeposit(500);
        PayAsYouGoDTO payAsYouGoDTO = new PayAsYouGoDTO();
        payAsYouGoDTO.setId(new Long(10));
        when(mockPayAsYouGoDataService.findByTicketPrice(anyInt())).thenReturn(payAsYouGoDTO);
        when(mockGetCardService.getCard(anyString())).thenReturn(cardInfoResponseV2DTO);
        when(mockCardRefundableDepositDataService.findByPrice(anyInt())).thenReturn(CardRefundableDepositTestUtil.getTestCardRefundableDepositDTO1());
        PassengerTypeDTO passengerTypeDTO = PrePaidTicketTestUtil.getTestPassengerTypeDTO();
        DiscountTypeDTO discountTypeDTO = PrePaidTicketTestUtil.getTestDiscountTypeDTO();
        when(mockPassengerTypeDataService.findByName(anyString())).thenReturn(passengerTypeDTO);
        when(mockDiscountTypeDataService.findByName(anyString())).thenReturn(discountTypeDTO);
        CartDTO cartDTO = service.populateCartItemsToCartDTOFromCubic(new CartDTO(), new Long(5012));
        assertEquals(getTestCardInfoResponseV2DTO3().getPptDetails().getPptSlots().size() + 2, cartDTO.getCartItems().size());
        verify(mockAutoTopUpDataService, never()).findByAutoTopUpAmount(anyInt());
    }

    @Test
    public void shouldNotUpdateCartDTOWithDepositItem() {
        when(mockCardDataService.findById(CARD_ID_1)).thenReturn(getTestCardDTO1());
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getTestCardInfoResponseV2DTO3();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setAutoLoadState(2);
        AutoTopUpDTO autoTopUpDto = new AutoTopUpDTO();
        autoTopUpDto.setId(new Long(12));
        CardInfoResponseV2DTO cardInfoResponseV2DTO2 = CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1();
        cardInfoResponseV2DTO.setPpvDetails(cardInfoResponseV2DTO2.getPpvDetails());
        cardInfoResponseV2DTO.setCardDeposit(0);
        PayAsYouGoDTO payAsYouGoDTO = new PayAsYouGoDTO();
        payAsYouGoDTO.setId(new Long(10));
        when(mockAutoTopUpDataService.findByAutoTopUpAmount(anyInt())).thenReturn(autoTopUpDto);
        when(mockPayAsYouGoDataService.findByTicketPrice(anyInt())).thenReturn(payAsYouGoDTO);
        when(mockGetCardService.getCard(anyString())).thenReturn(cardInfoResponseV2DTO);
        PassengerTypeDTO passengerTypeDTO = PrePaidTicketTestUtil.getTestPassengerTypeDTO();
        DiscountTypeDTO discountTypeDTO = PrePaidTicketTestUtil.getTestDiscountTypeDTO();
        when(mockPassengerTypeDataService.findByName(anyString())).thenReturn(passengerTypeDTO);
        when(mockDiscountTypeDataService.findByName(anyString())).thenReturn(discountTypeDTO);
        CartDTO cartDTO = service.populateCartItemsToCartDTOFromCubic(new CartDTO(), new Long(5012));
        assertEquals(getTestCardInfoResponseV2DTO3().getPptDetails().getPptSlots().size() + 2, cartDTO.getCartItems().size());
        verify(mockCardRefundableDepositDataService, never()).findByPrice(anyInt());
    }

    @Test
    public void shouldNotPopulateCartItemsToCartDTOFromCubicIfCardDetailsReturnsNull() {
        when(mockCardDataService.findById(any(Long.class))).thenReturn(getTestCardDTOWithInvalidCardNumber());
        when(mockGetCardService.getCard(anyString())).thenReturn(null);
        CartDTO cartDTO = service.populateCartItemsToCartDTOFromCubic(new CartDTO(), new Long(9999));
        assertEquals(0, cartDTO.getCartItems().size());
    }

    @Test
    public void updateCartDTOWithTicketsShouldUpdateCartItemCmd() {
        CartDTO mockCartDTO = mock(CartDTO.class);
        when(mockCartDTO.getCartItems()).thenReturn(ProductItemTestUtil.getTestEmptyItemDTOList());
        when(mockCartDTO.getCardId()).thenReturn(CARD_ID_1);
        when(mockCardDataService.findById(CARD_ID_1)).thenReturn(getTestCardDTO1());
        when(service.getCardProductDTOFromCubic(OYSTER_NUMBER_1)).thenReturn(getTestCardInfoResponseV2DTO3());
        PassengerTypeDTO passengerTypeDTO = PrePaidTicketTestUtil.getTestPassengerTypeDTO();
        DiscountTypeDTO discountTypeDTO = PrePaidTicketTestUtil.getTestDiscountTypeDTO();
        when(mockPassengerTypeDataService.findByName(anyString())).thenReturn(passengerTypeDTO);
        when(mockDiscountTypeDataService.findByName(anyString())).thenReturn(discountTypeDTO);
        mockCartDTO = service.updateCartDTOWithTickets(getTestCardInfoResponseV2DTO3(), CARD_ID_1, mockCartDTO);
        assertEquals(getTestCardInfoResponseV2DTO3().getPptDetails().getPptSlots().size(), mockCartDTO.getCartItems().size());
    }

    @Test
    public void shouldNotUpdateCartDTOWhenNoTicketsAvailable() {
        CartDTO mockCartDTO = mock(CartDTO.class);
        mockCartDTO = service.updateCartDTOWithTickets(getTestCardInfoResponseV2DTO2(), CARD_ID_1, mockCartDTO);
        assertNull(getTestCardInfoResponseV2DTO2().getPptDetails());
        verify(mockCartDTO, never()).getCartItems();
        verify(mockCartDTO, never()).getCardId();
        verify(mockCardDataService, never()).findById(any(Long.class));
        verify(mockPassengerTypeDataService, never()).findByName(anyString());
        verify(mockDiscountTypeDataService, never()).findByName(anyString());
    }

    @Test
    public void shouldNotUpdateCartDTOWhenNoTicketSlotsAvailable() {
        CartDTO mockCartDTO = mock(CartDTO.class);
        mockCartDTO = service.updateCartDTOWithTickets(getTestCardInfoResponseV2DTO18(), CARD_ID_1, mockCartDTO);
        assertNull(getTestCardInfoResponseV2DTO18().getPptDetails().getPptSlots());
        verify(mockCartDTO, never()).getCartItems();
        verify(mockCartDTO, never()).getCardId();
        verify(mockCardDataService, never()).findById(any(Long.class));
        verify(mockPassengerTypeDataService, never()).findByName(anyString());
        verify(mockDiscountTypeDataService, never()).findByName(anyString());
    }

    @Test
    public void updateCartDTOWithTicketsForOddPeriodDuration() {
        CartDTO mockCartDTO = mock(CartDTO.class);
        when(mockCartDTO.getCartItems()).thenReturn(ProductItemTestUtil.getTestEmptyItemDTOList());
        when(mockCartDTO.getCardId()).thenReturn(CARD_ID_1);
        when(mockCardDataService.findById(CARD_ID_1)).thenReturn(getTestCardDTO1());
        when(service.getCardProductDTOFromCubic(OYSTER_NUMBER_1)).thenReturn(getCardReponseWithTravelCardOddPeriodDuration());
        PassengerTypeDTO passengerTypeDTO = PrePaidTicketTestUtil.getTestPassengerTypeDTO();
        DiscountTypeDTO discountTypeDTO = PrePaidTicketTestUtil.getTestDiscountTypeDTO();
        when(mockPassengerTypeDataService.findByName(anyString())).thenReturn(passengerTypeDTO);
        when(mockDiscountTypeDataService.findByName(anyString())).thenReturn(discountTypeDTO);
        when(mockProductDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(),
                        anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(getTestProductDTO1());
        mockCartDTO = service.updateCartDTOWithTickets(getCardReponseWithTravelCardOddPeriodDuration(), CARD_ID_1, mockCartDTO);
        assertEquals(1, mockCartDTO.getCartItems().size());
    }

    @Test
    public void updateCartDTOWithTicketsShouldUpdateCartItemCmdExcludingInvalidTickets() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getTestCardInfoResponseV2DTO3WithInValidState();
        CartDTO mockCartDTO = mock(CartDTO.class);
        when(mockCartDTO.getCartItems()).thenReturn(ProductItemTestUtil.getTestEmptyItemDTOList());
        when(mockCartDTO.getCardId()).thenReturn(CARD_ID_1);
        when(mockCardDataService.findById(CARD_ID_1)).thenReturn(getTestCardDTO1());
        when(service.getCardProductDTOFromCubic(OYSTER_NUMBER_1)).thenReturn(cardInfoResponseV2DTO);

        mockCartDTO = service.updateCartDTOWithTickets(cardInfoResponseV2DTO, CARD_ID_1, mockCartDTO);
        assertEquals(0, mockCartDTO.getCartItems().size());
    }

    @Test
    public void updateCartDTOWithTicketsShouldUpdateCartItemCmdWithvalidTickets() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getTestCardInfoResponseV2DTO3WithValidState();
        CartDTO mockCartDTO = mock(CartDTO.class);
        when(mockCartDTO.getCartItems()).thenReturn(ProductItemTestUtil.getTestEmptyItemDTOList());
        when(mockCartDTO.getCardId()).thenReturn(CARD_ID_1);
        when(mockCardDataService.findById(CARD_ID_1)).thenReturn(getTestCardDTO1());
        PassengerTypeDTO passengerTypeDTO = PrePaidTicketTestUtil.getTestPassengerTypeDTO();
        DiscountTypeDTO discountTypeDTO = PrePaidTicketTestUtil.getTestDiscountTypeDTO();
        when(mockPassengerTypeDataService.findByName(anyString())).thenReturn(passengerTypeDTO);
        when(mockDiscountTypeDataService.findByName(anyString())).thenReturn(discountTypeDTO);
        when(service.getCardProductDTOFromCubic(OYSTER_NUMBER_1)).thenReturn(cardInfoResponseV2DTO);

        mockCartDTO = service.updateCartDTOWithTickets(cardInfoResponseV2DTO, CARD_ID_1, mockCartDTO);
        assertEquals(3, mockCartDTO.getCartItems().size());
    }

}
