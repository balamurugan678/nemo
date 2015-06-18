package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO15;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO16;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO17;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO18;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO2;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO3;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO3WithInValidState;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO3WithValidState;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO4;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO6;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO9;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestEmptyTravelCardList;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.ProductTestUtil.getTestProductDTO1;
import static com.novacroft.nemo.test_support.ProductTestUtil.getTestProductDTOWithZones;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.test_support.ProductItemTestUtil;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

/**
 * RefundServiceImpl unit tests
 */
public class TopUpTicketServiceTest {
    private TopUpTicketServiceImpl service;

    private CardDataService mockCardDataService;
    private GetCardService mockGetCardService;
    private ProductDataService mockProductDataService;

    @Before
    public void setup() {
        service = new TopUpTicketServiceImpl();

        mockCardDataService = mock(CardDataService.class);
        mockGetCardService = mock(GetCardService.class);
        mockProductDataService = mock(ProductDataService.class);
        
        service.cardDataService = mockCardDataService;
        service.getCardService = mockGetCardService;
        service.productDataService = mockProductDataService;
        
    }

    @Test
    public void isOysterCardIncludesPendingOrExistingTravelCardsShouldReturnTrue() {
    	when(mockCardDataService.findById(CARD_ID_1)).thenReturn(getTestCardDTO1());
    	when(service.getCardProductDTOFromCubic(OYSTER_NUMBER_1)).thenReturn(getTestCardInfoResponseV2DTO3());
        
    	assertTrue(service.isOysterCardIncludesPendingOrExistingTravelCards(CARD_ID_1));
    	
    	verify(mockCardDataService, atLeastOnce()).findById(CARD_ID_1);
    }
    
    @Test
    public void isOysterCardIncludesPendingOrExistingTravelCardsShouldReturnFalse() {
    	when(mockCardDataService.findById(CARD_ID_1)).thenReturn(getTestCardDTO1());
    	when(service.getCardProductDTOFromCubic(OYSTER_NUMBER_1)).thenReturn(getTestCardInfoResponseV2DTO2());
        
    	assertFalse(service.isOysterCardIncludesPendingOrExistingTravelCards(CARD_ID_1));
    	
    	verify(mockCardDataService, atLeastOnce()).findById(CARD_ID_1);
    }
    
    @Test
    public void isOysterCardIncludesExistingTravelCardsShouldReturnTrue() {
    	assertTrue(service.isOysterCardIncludesExistingTravelCards(getTestCardInfoResponseV2DTO3()));
    }
    
    @Test
    public void isOysterCardIncludesExistingTravelCardsShouldReturnFalse() {
    	assertFalse(service.isOysterCardIncludesExistingTravelCards(getTestCardInfoResponseV2DTO2()));
    }
    
    @Test
    public void isOysterCardIncludesPendingTravelCardsShouldReturnTrue() {
    	assertTrue(service.isOysterCardIncludesPendingTravelCards(getTestCardInfoResponseV2DTO6()));
    }
    
    @Test
    public void isOysterCardIncludesPendingTravelCardsShouldReturnFalse() {
    	assertFalse(service.isOysterCardIncludesPendingTravelCards(getTestCardInfoResponseV2DTO2()));
    }
    
    @Test
    public void updateCartItemCmdWithProductsFromCubicShouldUpdateCartItemCmd() {
    	CartCmdImpl mockCartCmdImpl = mock(CartCmdImpl.class);
    	when(mockCartCmdImpl.getCartItemList()).thenReturn(getTestEmptyTravelCardList());
    	when(mockCartCmdImpl.getCardId()).thenReturn(CARD_ID_1);
    	when(mockCardDataService.findById(CARD_ID_1)).thenReturn(getTestCardDTO1());
    	when(service.getCardProductDTOFromCubic(OYSTER_NUMBER_1)).thenReturn(getTestCardInfoResponseV2DTO3());
    	
    	mockCartCmdImpl = service.updateCartItemCmdWithProductsFromCubic(mockCartCmdImpl);
    	assertEquals(getTestCardInfoResponseV2DTO3().getPptDetails().getPptSlots().size(), mockCartCmdImpl.getCartItemList().size());
    }
    
    @Test
    public void updateCartCmdImplWithTicketsShouldUpdateCartItemCmd() {
    	CartCmdImpl mockCartCmdImpl = mock(CartCmdImpl.class);
    	when(mockCartCmdImpl.getCartItemList()).thenReturn(getTestEmptyTravelCardList());
    	when(mockCartCmdImpl.getCardId()).thenReturn(CARD_ID_1);
    	when(mockCardDataService.findById(CARD_ID_1)).thenReturn(getTestCardDTO1());
    	when(service.getCardProductDTOFromCubic(OYSTER_NUMBER_1)).thenReturn(getTestCardInfoResponseV2DTO3());
    	
    	mockCartCmdImpl = service.updateCartCmdImplWithTickets(getTestCardInfoResponseV2DTO3(), CARD_ID_1, mockCartCmdImpl);
    	assertEquals(getTestCardInfoResponseV2DTO3().getPptDetails().getPptSlots().size(), mockCartCmdImpl.getCartItemList().size());
    }
    
    @Test
    public void updateCartCmdImplWithPendingTicketsShouldUpdateCartItemCmd() {
    	CartCmdImpl mockCartCmdImpl = mock(CartCmdImpl.class);
    	when(mockCartCmdImpl.getCartItemList()).thenReturn(getTestEmptyTravelCardList());
    	when(mockCartCmdImpl.getCardId()).thenReturn(CARD_ID_1);
    	when(mockCardDataService.findById(CARD_ID_1)).thenReturn(getTestCardDTO1());
    	when(service.getCardProductDTOFromCubic(OYSTER_NUMBER_1)).thenReturn(getTestCardInfoResponseV2DTO9());
        when(mockProductDataService.findByProductCode(anyString(), any(Date.class))).thenReturn(getTestProductDTOWithZones());
    	
    	mockCartCmdImpl = service.updateCartCmdImplWithPendingTickets(getTestCardInfoResponseV2DTO9(), CARD_ID_1, mockCartCmdImpl);
    	assertEquals(getTestCardInfoResponseV2DTO9().getPendingItems().getPpts().size(), mockCartCmdImpl.getCartItemList().size());
    }
    
    @Test
    public void removeExpiredPrePaidTicketsInACardTest(){
        CardDTO card = getTestCardDTO1();
        CardInfoResponseV2DTO cardInfo = getTestCardInfoResponseV2DTO15();
        service.removeExpiredPrePaidTicketsInACard(card, cardInfo);
        assertEquals(0, cardInfo.getPptDetails().getPptSlots().size());
    }

    @Test
    public void removeExpiredPrePaidTicketsInACardShouldNotRemoveUnExpiredTicketTest(){
        CardDTO card = getTestCardDTO1();
        CardInfoResponseV2DTO cardInfo = getTestCardInfoResponseV2DTO16();
        service.removeExpiredPrePaidTicketsInACard(card, cardInfo);
        assertEquals(1, cardInfo.getPptDetails().getPptSlots().size());
    }

    @Test
    public void removeExpiredPrePaidTicketsInACardForTicketsWithNoExpiryDateTest(){
        CardDTO card = getTestCardDTO1();
        CardInfoResponseV2DTO cardInfo = getTestCardInfoResponseV2DTO17();
        service.removeExpiredPrePaidTicketsInACard(card, cardInfo);
        assertEquals(1, cardInfo.getPptDetails().getPptSlots().size());
    }

    @Test
    public void shouldRemoveExpiredPrePaidTicketsInACardForNoPrePaidTicketTest(){
        CardDTO card = getTestCardDTO1();
        CardInfoResponseV2DTO cardInfo = getTestCardInfoResponseV2DTO4();
        service.removeExpiredPrePaidTicketsInACard(card, cardInfo);
        assertNull(cardInfo.getPptDetails());
    }

    @Test
    public void shouldRemoveExpiredPrePaidTicketsInACardForNoPrePaidTicketSlotsTest(){
        CardDTO card = getTestCardDTO1();
        CardInfoResponseV2DTO cardInfo = getTestCardInfoResponseV2DTO18();
        service.removeExpiredPrePaidTicketsInACard(card, cardInfo);
        assertNull(cardInfo.getPptDetails().getPptSlots());
    }
    
    @Test
    public void updateCartDTOWithProductsFromCubicShouldUpdateCartDTO() {
        CartDTO mockCartDTO = mock(CartDTO.class);
        when(mockCartDTO.getCartItems()).thenReturn(ProductItemTestUtil.getTestEmptyItemDTOList());
        when(mockCartDTO.getCardId()).thenReturn(CARD_ID_1);
        when(mockCardDataService.findById(CARD_ID_1)).thenReturn(getTestCardDTO1());
        when(service.getCardProductDTOFromCubic(OYSTER_NUMBER_1)).thenReturn(getTestCardInfoResponseV2DTO3());
        
        mockCartDTO = service.updateCartDTOWithProductsFromCubic(mockCartDTO);
        assertEquals(getTestCardInfoResponseV2DTO3().getPptDetails().getPptSlots().size(), mockCartDTO.getCartItems().size());
    }
    
    @Test
    public void updateCartDTOWithTicketsShouldUpdateCartItemCmd() {
        CartDTO mockCartDTO = mock(CartDTO.class);
        when(mockCartDTO.getCartItems()).thenReturn(ProductItemTestUtil.getTestEmptyItemDTOList());
        when(mockCartDTO.getCardId()).thenReturn(CARD_ID_1);
        when(mockCardDataService.findById(CARD_ID_1)).thenReturn(getTestCardDTO1());
        when(service.getCardProductDTOFromCubic(OYSTER_NUMBER_1)).thenReturn(getTestCardInfoResponseV2DTO3());
        
        mockCartDTO = service.updateCartDTOWithTickets(getTestCardInfoResponseV2DTO3(), CARD_ID_1, mockCartDTO);
        assertEquals(getTestCardInfoResponseV2DTO3().getPptDetails().getPptSlots().size(), mockCartDTO.getCartItems().size());
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
        when(service.getCardProductDTOFromCubic(OYSTER_NUMBER_1)).thenReturn(cardInfoResponseV2DTO);
        
        mockCartDTO = service.updateCartDTOWithTickets(cardInfoResponseV2DTO, CARD_ID_1, mockCartDTO);
        assertEquals(3, mockCartDTO.getCartItems().size());
    }
    
    
    @Test
    public void updateCartDTOWithPendingTicketsShouldUpdateCartItemCmd() {
        CartDTO mockCartDTO = mock(CartDTO.class);
        when(mockCartDTO.getCartItems()).thenReturn(ProductItemTestUtil.getTestEmptyItemDTOList());
        when(mockCartDTO.getCardId()).thenReturn(CARD_ID_1);
        when(mockCardDataService.findById(CARD_ID_1)).thenReturn(getTestCardDTO1());
        when(service.getCardProductDTOFromCubic(OYSTER_NUMBER_1)).thenReturn(getTestCardInfoResponseV2DTO9());
        when(mockProductDataService.findByProductCode(anyString(), any(Date.class))).thenReturn(getTestProductDTO1());
        
        mockCartDTO = service.updateCartDTOWithPendingTickets(getTestCardInfoResponseV2DTO9(), CARD_ID_1, mockCartDTO);
        assertEquals(getTestCardInfoResponseV2DTO9().getPendingItems().getPpts().size(), mockCartDTO.getCartItems().size());
    }
    
    @Test
    public void updateCartDTOWithProductsFromCubicShouldUpdate() {
        CartDTO mockCartDTO = mock(CartDTO.class);
        when(mockCartDTO.getCartItems()).thenReturn(ProductItemTestUtil.getTestEmptyItemDTOList());
        when(mockCartDTO.getCardId()).thenReturn(CARD_ID_1);
        when(mockCardDataService.findById(CARD_ID_1)).thenReturn(getTestCardDTO1());
        when(service.getCardProductDTOFromCubic(OYSTER_NUMBER_1)).thenReturn(getTestCardInfoResponseV2DTO3());
        
        mockCartDTO = service.updateCartDTOWithProductsFromCubic(mockCartDTO, null);
        assertEquals(getTestCardInfoResponseV2DTO3().getPptDetails().getPptSlots().size(), mockCartDTO.getCartItems().size());
    }
}
