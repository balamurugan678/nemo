package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CartItemTestUtil.ANNUAL_START_DATE_1;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithThreeProductItems;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

/**
 * Unit tests for New Bus Pass Service
 */
public class BusPassServiceTest {

    private BusPassServiceImpl service;
    private CartDTO cartDTO;
    private CartItemCmdImpl cartItemCmd;
    private CartService mockCartService;
    private CartAdministrationService mockNewCartAdministrationService;

    @Before
    public void setUp() {
        service = new BusPassServiceImpl();
        cartItemCmd = new CartItemCmdImpl();
        cartDTO = new CartDTO();

        mockCartService = mock(CartService.class);
        mockNewCartAdministrationService = mock(CartAdministrationService.class);

        service.cartAdminService = mockNewCartAdministrationService;
        service.cartService = mockCartService;
    }

    @Test
    public void addCartItemForExistingCardShouldAddToCart() {
        when(mockCartService.addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ProductItemDTO.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartDTO = service.addCartItemForExistingCard(cartDTO, cartItemCmd);
        verify(mockCartService, atLeastOnce()).addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ProductItemDTO.class));
        assertEquals(cartDTO.getCartItems().size(), 3);
    }

    @Test(expected = AssertionError.class)
    public void addCartItemForExistingCardWithNullCartItemCmdShouldShowAssertionException() {
        when(mockCartService.addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ProductItemDTO.class))).thenReturn(null);
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartDTO = service.addCartItemForExistingCard(cartDTO, null);
        verify(mockCartService, atLeastOnce()).addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ProductItemDTO.class));
    }

    @Test
    public void addCartItemForNewCardShouldAddToCart() {
        when(mockCartService.addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ProductItemDTO.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        when(mockNewCartAdministrationService.applyRefundableDeposit(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        when(mockNewCartAdministrationService.applyShippingCost(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartDTO = service.addCartItemForNewCard(cartDTO, cartItemCmd);
        verify(mockCartService, atLeastOnce()).addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ProductItemDTO.class));
        verify(mockNewCartAdministrationService, atLeastOnce()).applyRefundableDeposit(any(CartDTO.class), any(CartItemCmdImpl.class));
        verify(mockNewCartAdministrationService, atLeastOnce()).applyShippingCost(any(CartDTO.class), any(CartItemCmdImpl.class));
        assertEquals(cartDTO.getCartItems().size(), 3);
    }

    @Test(expected = AssertionError.class)
    public void addCartItemForNewCardWithNullCartItemCmdShouldNotAddToCart() {
        when(mockCartService.addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ProductItemDTO.class))).thenReturn(null);
        when(mockNewCartAdministrationService.applyRefundableDeposit(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        when(mockNewCartAdministrationService.applyShippingCost(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartDTO = service.addCartItemForNewCard(cartDTO, null);
        verify(mockCartService, atLeastOnce()).addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ProductItemDTO.class));
        verify(mockNewCartAdministrationService, atLeastOnce()).applyRefundableDeposit(any(CartDTO.class), any(CartItemCmdImpl.class));
        verify(mockNewCartAdministrationService, atLeastOnce()).applyShippingCost(any(CartDTO.class), any(CartItemCmdImpl.class));
    }

}
