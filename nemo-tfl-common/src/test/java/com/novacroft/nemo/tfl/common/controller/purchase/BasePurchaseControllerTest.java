package com.novacroft.nemo.tfl.common.controller.purchase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartSessionDataTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.WebCreditService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameter;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;

/**
 * Unit tests for TravelCard Controller
 */
public class BasePurchaseControllerTest {
    private BasePurchaseController controller;
    private CartService mockCartService;
    private HttpSession mockHttpSession;
    private CardDataService mockCardDataService;
    private WebCreditService mockWebCreditService;
    private BindingAwareModelMap model;
    private CartSessionData cartSessionData;
    private CartDTO cartDTO;
    private CartCmdImpl cartCmd;

    @Before
    public void setUp() {
        controller = new BaseTravelCardController();
        mockCartService = mock(CartService.class);
        mockHttpSession = mock(HttpSession.class);
        mockCardDataService = mock(CardDataService.class);
        mockWebCreditService = mock(WebCreditService.class);
        model = new BindingAwareModelMap();
        cartSessionData = CartSessionDataTestUtil.getTestCartSessionDataDTO(CardTestUtil.CARD_ID);
        cartDTO = CartTestUtil.getTestCartDTO1();
        cartCmd = new CartCmdImpl();

        controller.cartService = mockCartService;
        controller.cardDataService = mockCardDataService;
        controller.webCreditService = mockWebCreditService;

    }

    @Test
    public void cancelReturnToCartShowCart() {
        when(mockHttpSession.getAttribute(CartAttribute.SESSION_ATTRIBUTE_SHOPPING_CART_DATA)).thenReturn(cartSessionData);
        when(mockCartService.findById(anyLong())).thenReturn(cartDTO);

        ModelAndView result = controller.cancelReturnToCart(mockHttpSession, model, cartCmd);

        assertEquals(PageUrl.CART, ((RedirectView) result.getView()).getUrl());
        assertTrue(model.containsAttribute(PageCommand.CART));
        assertTrue(model.containsAttribute(PageAttribute.CART_DTO));
        assertTrue(model.containsAttribute(PageParameter.CUSTOMER_ID));
    }

}
