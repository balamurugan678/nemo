package com.novacroft.nemo.tfl.common.controller.purchase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartSessionDataTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.application_service.WebCreditService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.form_validator.CartValidator;
import com.novacroft.nemo.tfl.common.form_validator.TravelCardValidator;
import com.novacroft.nemo.tfl.common.form_validator.ZoneMappingValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;

/**
 * Unit tests for TravelCard Controller
 */
public class BaseTravelCardControllerTest {
    private BaseTravelCardController controller;
    private CartItemCmdImpl mockCartItemCmd;
    private RedirectAttributes mockRedirectAttributes;
    private CartService mockCartService;
    private TravelCardValidator mockTravelCardValidator;
    private ZoneMappingValidator mockZoneMappingValidator;
    private CartValidator mockCartValidator;
    private HttpSession mockHttpSession;
    private CardDataService mockCardDataService;
    private WebCreditService mockWebCreditService;
    private SelectListService mockSelectListService;
    private BindingAwareModelMap model;
    private TravelCardService mockTravelCardService;
    private CartAdministrationService mockNewCartAdminService;
    private CartSessionData cartSessionData;
    private CartDTO cartDTO;
    private CardService mockCardService;
  
    @Before
    public void setUp() {
        controller = new BaseTravelCardController();
        mockCartItemCmd = mock(CartItemCmdImpl.class);
        mockRedirectAttributes = mock(RedirectAttributes.class);
        mockCartService = mock(CartService.class);
        mockTravelCardValidator = mock(TravelCardValidator.class);
        mockZoneMappingValidator = mock(ZoneMappingValidator.class);
        mockCartValidator = mock(CartValidator.class);
        mockHttpSession = mock(HttpSession.class);
        mockCardDataService = mock(CardDataService.class);
        mockWebCreditService = mock(WebCreditService.class);
        mockSelectListService = mock(SelectListService.class);
        mockTravelCardService = mock(TravelCardService.class);
        model = new BindingAwareModelMap();
        mockNewCartAdminService = mock(CartAdministrationService.class);
        mockCardService = mock(CardService.class);
        cartSessionData = CartSessionDataTestUtil.getTestCartSessionDataDTO(CardTestUtil.CARD_ID);
        cartDTO = CartTestUtil.getTestCartDTO1();
        doNothing().when(mockTravelCardValidator).validate(anyObject(), any(Errors.class));
        doNothing().when(mockZoneMappingValidator).validate(anyObject(), any(Errors.class));
        doNothing().when(mockCartValidator).validate(anyObject(), any(Errors.class));

        controller.cartService = mockCartService;
        controller.travelCardValidator = mockTravelCardValidator;
        controller.zoneMappingValidator = mockZoneMappingValidator;
        controller.cartValidator = mockCartValidator;
        controller.cardDataService = mockCardDataService;
        controller.webCreditService = mockWebCreditService;
        controller.selectListService = mockSelectListService;
        controller.travelCardService = mockTravelCardService;
        controller.cartAdminService = mockNewCartAdminService;
        controller.cardService = mockCardService;

        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        when(mockHttpSession.getAttribute(CartAttribute.SESSION_ATTRIBUTE_SHOPPING_CART_DATA)).thenReturn(cartSessionData);
        when(mockCartService.findById(anyLong())).thenReturn(cartDTO);

    }

    @Test
    public void shouldPopulateEmailRemindersSelectList() {
        controller.populateEmailRemindersSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.BASKET_EMAIL_REMINDERS));
    }

    @Test
    public void shouldPopulateTravelCardTypesSelectList() {
        controller.populateTravelCardTypesSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.TRAVEL_CARD_TYPES));
    }

    @Test
    public void shouldPopulateTravelCardZonesSelectList() {
        controller.populateTravelCardZonesSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.TRAVEL_CARD_ZONES));
    }

    @Test
    public void shouldPopulateStartDatesSelectList() {
        controller.populateStartDatesSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.START_DATES));
    }

    @Test
    public void shouldShowBusPass() {
        ModelAndView result = controller.viewTravelCard();
        assertEquals(PageView.TRAVEL_CARD, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.CART_ITEM));
        assertTrue(result.getModel().get(PageCommand.CART_ITEM) instanceof CartItemCmdImpl);
    }

    @Test
    public void addTravelCardToCartShouldShowCartOnValidInput() {
        BeanPropertyBindingResult mockResult = mock(BeanPropertyBindingResult.class);
        when(mockResult.hasErrors()).thenReturn(false);
        ModelAndView result = controller.addTravelCardToCart(mockHttpSession, mockCartItemCmd, mockResult, mockRedirectAttributes);
        verify(mockTravelCardService, atLeastOnce()).addCartItemForNewCard(any(CartDTO.class), any(CartItemCmdImpl.class));
        assertEquals(PageUrl.CART, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void addTravelCardToCartShouldShowFlashMessage() {
        BeanPropertyBindingResult mockResult = mock(BeanPropertyBindingResult.class);
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockCartItemCmd.getStatusMessage()).thenReturn("TEST");
        ModelAndView result = controller.addTravelCardToCart(mockHttpSession, mockCartItemCmd, mockResult, mockRedirectAttributes);
        verify(mockTravelCardService, atLeastOnce()).addCartItemForNewCard(any(CartDTO.class), any(CartItemCmdImpl.class));
        assertEquals(PageUrl.CART, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void addTravelCardToCartShouldShowTravelCardOnInvalidInput() {
        BeanPropertyBindingResult mockResult = mock(BeanPropertyBindingResult.class);
        when(mockResult.hasErrors()).thenReturn(true);  
        ModelAndView result = controller.addTravelCardToCart(mockHttpSession, mockCartItemCmd, mockResult, mockRedirectAttributes);
        verify(mockTravelCardService, never()).addCartItemForNewCard(any(CartDTO.class), any(CartItemCmdImpl.class));
        assertEquals(PageView.TRAVEL_CARD, result.getViewName());
    }

    @Test
    public void cancelShouldRedirectToOysterHome() {
        ModelAndView result = controller.cancel();
        assertEquals(PageUrl.DASHBOARD, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void shoppingBasketShouldRedirectToShoppingBasket() {
        ModelAndView result = controller.shoppingBasket();
        assertEquals(PageUrl.CART, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void addTravelCardToCartShouldReturnErrors() {
        BeanPropertyBindingResult mockResult = mock(BeanPropertyBindingResult.class);
        when(mockResult.hasErrors()).thenReturn(true);
        String viewName = controller.addTravelCardToCart(mockHttpSession, mockCartItemCmd, mockResult, mockRedirectAttributes).getViewName();
        verify(mockTravelCardService, never()).addCartItemForNewCard(any(CartDTO.class), any(CartItemCmdImpl.class));
        assertTrue(PageView.TRAVEL_CARD.equals(viewName));
    }

    @Test
    public void addTravelCardToCartShouldComplete() {
        BeanPropertyBindingResult mockResult = mock(BeanPropertyBindingResult.class);
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockWebCreditService.getAvailableBalance(anyLong())).thenReturn(0);
        ModelAndView result = controller.addTravelCardToCart(mockHttpSession, mockCartItemCmd, mockResult, mockRedirectAttributes);
        assertEquals(PageUrl.CART, ((RedirectView) result.getView()).getUrl());
    }
}

