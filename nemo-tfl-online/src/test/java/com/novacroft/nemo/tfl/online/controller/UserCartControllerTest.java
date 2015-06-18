package com.novacroft.nemo.tfl.online.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartSessionDataTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;

/**
 * Unit tests for User Cart Controller
 */
public class UserCartControllerTest {

    private UserCartController controller;
    private RedirectAttributes mockRedirectAttributes;
    private CartService mockCartService;
    private CartAdministrationService mockCartAdminService;
    private HttpSession mockSession;
    private Model mockModel;
    private CartDTO mockCartDTO;
    private CartSessionData cartSessionData;
    private CardService mockCardService;
    private CardDTO cardDTO;
    
    @Before
    public void setUp() {
        controller = new UserCartController();
        mockRedirectAttributes = mock(RedirectAttributes.class);
        mockCartService = mock(CartService.class);
        mockSession = mock(HttpSession.class);
        mockModel = mock(Model.class);
        mockCartDTO = mock(CartDTO.class);
        cartSessionData = CartSessionDataTestUtil.getTestCartSessionDataDTO1();
        cardDTO = CardTestUtil.getTestCardDTO1();
        mockCartAdminService = mock(CartAdministrationService.class);
        mockCardService = mock(CardService.class);
        
        controller.cartService = mockCartService;
        controller.cartAdminService = mockCartAdminService;
        controller.cardService = mockCardService;

        when(mockSession.getAttribute(anyString())).thenReturn(cartSessionData);
        when(mockCartService.findById(any(Long.class))).thenReturn(mockCartDTO);
   }

    @Test
    public void shouldShowUserCart() {
        when(mockCardService.getAutoTopUpVisibleOptionForCard(any(Long.class))).thenReturn(Boolean.TRUE);
        when(mockCardService.getCardDTOById(any(Long.class))).thenReturn(cardDTO);
        ModelAndView result = controller.viewUserCart(mockSession, mockModel, mockRedirectAttributes);
        assertEquals(PageView.USER_CART, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.CART));
        assertTrue(result.getModel().get(PageCommand.CART) instanceof CartCmdImpl);
    }

    @Test
    public void emptyUserCartShouldShowTopUpTicket() {
        when(mockCartAdminService.removeRefundableDepositAndShippingCost(mockCartDTO)).thenReturn(mockCartDTO);
        ModelAndView result = controller.emptyUserCart(mockSession);
        verify(mockCartService, atLeastOnce()).emptyCart(any(CartDTO.class));
        assertEquals(PageUrl.TOP_UP_TICKET, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void addMoreItemsShouldShowTopUpTicket() {
        ModelAndView result = controller.addMoreItems(mockSession);
        assertEquals(PageUrl.TOP_UP_TICKET, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void continueUserCartShouldShowCollectPurchase() {
        ModelAndView result = controller.continueUserCart();
        assertEquals(PageUrl.COLLECT_PURCHASE, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void deleteShoppingItemShouldShowUserCart() {
        when(mockCartAdminService.removeRefundableDepositAndShippingCost(mockCartDTO)).thenReturn(mockCartDTO);
        ModelAndView result = controller.deleteShoppingItem(mockSession, mockModel, 1);
        verify(mockCartService, atLeastOnce()).deleteItem(any(CartDTO.class), any(Long.class));
        assertEquals(PageUrl.USER_CART, ((RedirectView)result.getView()).getUrl());
    }

}
