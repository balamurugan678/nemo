package com.novacroft.nemo.tfl.common.controller.purchase;

import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO1;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartSessionDataTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.tfl.common.application_service.BusPassService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.controller.BaseTestController;
import com.novacroft.nemo.tfl.common.form_validator.BusPassValidator;
import com.novacroft.nemo.tfl.common.form_validator.CartValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Unit tests for BusPass Controller
 */
public class BaseBusPassControllerTest extends BaseTestController {

    private BindingAwareModelMap model;
    private BaseBusPassController controller;
    private CartService mockCartService;
    private HttpSession mockSession;
    private BusPassService mockBusPassService;
    private CartAdministrationService mockNewCartAdminService;
    private BusPassValidator mockBusPassValidator;
    private CartValidator mockCartValidator;
    private BeanPropertyBindingResult mockResult;
    private CartItemCmdImpl mockCartItemCmd;
    private SelectListService mockSelectListService;
    private CartSessionData cartSessionData;
    private CartDTO cartDTO;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        controller = new BaseBusPassController();
        model = new BindingAwareModelMap();
        mockSession = mock(HttpSession.class);
        mockCartService = mock(CartService.class);
        mockBusPassService = mock(BusPassService.class);
        mockNewCartAdminService = mock(CartAdministrationService.class);
        mockBusPassValidator = mock(BusPassValidator.class);
        mockCartValidator = mock(CartValidator.class);
        mockResult = mock(BeanPropertyBindingResult.class);
        mockCartItemCmd = mock(CartItemCmdImpl.class);
        mockSelectListService = mock(SelectListService.class);
        cartSessionData = CartSessionDataTestUtil.getTestCartSessionDataDTO(CardTestUtil.CARD_ID);
        cartDTO = CartTestUtil.getTestCartDTO1();

        controller.cartService = mockCartService;
        controller.busPassService = mockBusPassService;
        controller.cartAdminService = mockNewCartAdminService;
        controller.busPassValidator = mockBusPassValidator;
        controller.cartValidator = mockCartValidator;
        controller.selectListService = mockSelectListService;
        
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(cartDTO);
    }

    @Test
    public void testPopulateStartDatesSelectList() {
        when(mockNewCartAdminService.getProductStartDateList()).thenReturn(null);
        controller.populateStartDatesSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.START_DATES));
    }

    @Test
    public void testPopulateEmailRemindersSelectList() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(null);
        controller.populateEmailRemindersSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.BASKET_EMAIL_REMINDERS));
    }

    @Test
    public void shouldShowBusPass() {
        ModelAndView result = controller.viewBusPass();
        assertEquals(PageView.BUS_PASS, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.CART_ITEM));
        assertTrue(result.getModel().get(PageCommand.CART_ITEM) instanceof CartItemCmdImpl);
    }

    @Test
    public void addBusPassToCartShouldShowCartOnValidInput() {
        doNothing().when(mockBusPassValidator).validate(anyObject(), any(Errors.class));
        doNothing().when(mockCartValidator).validate(anyObject(), any(Errors.class));
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockBusPassService.addCartItemForNewCard(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithItem());

        ModelAndView result = controller.addBusPassToCartModelAndView(mockSession, mockCartItemCmd, mockResult);

        verify(mockBusPassValidator, atLeastOnce()).validate(anyObject(), any(Errors.class));
        verify(mockCartValidator, atLeastOnce()).validate(anyObject(), any(Errors.class));
        verify(mockBusPassService, atLeastOnce()).addCartItemForNewCard(any(CartDTO.class), any(CartItemCmdImpl.class));
        assertEquals(PageUrl.CART, redirectViewCheck(result));
    }

    @Test
    public void addBusPassToCartShouldShowBussPassOnInvalidInput() {
        doNothing().when(mockBusPassValidator).validate(anyObject(), any(Errors.class));
        doNothing().when(mockCartValidator).validate(anyObject(), any(Errors.class));
        when(mockResult.hasErrors()).thenReturn(true);

        ModelAndView result = controller.addBusPassToCartModelAndView(mockSession, mockCartItemCmd, mockResult);

        verify(mockBusPassValidator, atLeastOnce()).validate(anyObject(), any(Errors.class));
        verify(mockCartValidator, atLeastOnce()).validate(anyObject(), any(Errors.class));
        assertEquals(PageView.BUS_PASS, result.getViewName());
    }

    @Test
    public void cancelShouldRedirectToShopingBasket() {
        BaseBusPassController controller = new BaseBusPassController();
        ModelAndView result = controller.cancel();
        assertEquals(PageUrl.BASKET, redirectViewCheck(result));
    }

    @Test
    public void shoppingBasketShouldRedirectToCart() {

        ModelAndView result = controller.shoppingBasket();
        assertEquals(PageUrl.CART, redirectViewCheck(result));
    }

}
