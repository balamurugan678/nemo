package com.novacroft.nemo.tfl.online.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.TicketType;

/**
 * Unit tests for BasketController
 */
public class BasketControllerTest {

    @Test
    public void shouldShowBasket() {
        CartCmdImpl mockCmd = mock(CartCmdImpl.class);

        BasketController controller = new BasketController();
        ModelAndView result = controller.viewBasket(mockCmd);
        assertEquals(PageView.BASKET, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.CART));
        assertTrue(result.getModel().get(PageCommand.CART) instanceof CartCmdImpl);
    }

    @Test
    public void chooseBasketShouldShowPayAsYouGoOnPaygTicketType() {
        CartCmdImpl mockCmd = mock(CartCmdImpl.class);
        when(mockCmd.getTicketType()).thenReturn(TicketType.PAY_AS_YOU_GO.code());

        BasketController controller = new BasketController();

        ModelAndView result = controller.chooseBasket(mockCmd);
        assertEquals(PageUrl.PAY_AS_YOU_GO, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void chooseBasketShouldShowBusTramPassOnBusPassTicketType() {
        CartCmdImpl mockCmd = mock(CartCmdImpl.class);
        when(mockCmd.getTicketType()).thenReturn(TicketType.BUS_PASS.code());

        BasketController controller = new BasketController();

        ModelAndView result = controller.chooseBasket(mockCmd);
        assertEquals(PageUrl.BUS_PASS, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void chooseBasketShouldShowTravelCardOnTravelCardTicketType() {
        CartCmdImpl mockCmd = mock(CartCmdImpl.class);
        when(mockCmd.getTicketType()).thenReturn(TicketType.TRAVEL_CARD.code());

        BasketController controller = new BasketController();

        ModelAndView result = controller.chooseBasket(mockCmd);
        assertEquals(PageUrl.TRAVEL_CARD, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void cancelShouldRedirectToOysterHome() {
        BasketController controller = new BasketController();
        ModelAndView result = controller.cancel();
        assertEquals(PageUrl.OYSTER_HOME, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void shoppingBasketShouldRedirectToCart() {
        BasketController controller = new BasketController();
        ModelAndView result = controller.shoppingBasket();
        assertEquals(PageUrl.CART, ((RedirectView) result.getView()).getUrl());
    }

}
