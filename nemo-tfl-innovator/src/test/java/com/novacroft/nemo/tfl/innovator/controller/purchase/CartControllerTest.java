package com.novacroft.nemo.tfl.innovator.controller.purchase;

import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO1;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.test_support.CartItemTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.tfl.common.application_service.BusPassService;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.PayAsYouGoService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.BusPassValidator;
import com.novacroft.nemo.tfl.common.form_validator.CartValidator;
import com.novacroft.nemo.tfl.common.form_validator.PayAsYouGoValidator;
import com.novacroft.nemo.tfl.common.form_validator.TravelCardValidator;
import com.novacroft.nemo.tfl.common.form_validator.ZoneMappingValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Unit tests for cart Controller
 */
public class CartControllerTest {

    private CartController controller;
    private CartCmdImpl mockCmd;
    private CartService mockCartService;
    private CustomerService mockCustomerService;
    private SecurityService mockSecurityService;
    private BindingResult mockResult;
    private HttpSession mockSession;
    private CartAdministrationService mockCartAdminService;
    private SystemParameterService mockSystemParameterService;
    private BindingAwareModelMap model;
    private SelectListService mockSelectListService;
    private PayAsYouGoService mockPayAsYouGoService;
    private BusPassService mockBusPassService;
    private TravelCardService mockTravelCardService;
    private PayAsYouGoValidator mockPayAsYouGoValidator;
    private BusPassValidator mockBusPassValidator;
    private TravelCardValidator mockTravelCardValidator;
    private ZoneMappingValidator mockZoneMappingValidator;
    private CartValidator mockCartValidator;
    private CardService mockCardService;

    public static final String ANONYMOUS_USER = "anonymousUser";
    public static final String SHIPPING_TYPE_RECORDED_DELIVERY = "Recorded Delivery";
    public static final String USER = "user";

    @Before
    public void setUp() {
        controller = new CartController();
        mockCmd = mock(CartCmdImpl.class);
        mockCustomerService = mock(CustomerService.class);
        mockSecurityService = mock(SecurityService.class);
        mockCartService = mock(CartService.class);
        mockResult = mock(BindingResult.class);
        mockSession = mock(HttpSession.class);
        mockCartAdminService = mock(CartAdministrationService.class);
        mockSelectListService = mock(SelectListService.class);
        mockPayAsYouGoService = mock(PayAsYouGoService.class);
        mockBusPassService = mock(BusPassService.class);
        mockTravelCardService = mock(TravelCardService.class);
        model = new BindingAwareModelMap();
        mockSystemParameterService = mock(SystemParameterService.class);
        mockPayAsYouGoValidator = mock(PayAsYouGoValidator.class);
        mockBusPassValidator = mock(BusPassValidator.class);
        mockTravelCardValidator = mock(TravelCardValidator.class);
        mockZoneMappingValidator = mock(ZoneMappingValidator.class);
        mockCartValidator = mock(CartValidator.class);
        mockCardService = mock(CardService.class);
        
        controller.customerService = mockCustomerService;
        controller.cartService = mockCartService;
        controller.cartAdminService = mockCartAdminService;
        controller.selectListService = mockSelectListService;
        controller.payAsYouGoService = mockPayAsYouGoService;
        controller.busPassService = mockBusPassService;
        controller.travelCardService = mockTravelCardService;
        controller.systemParameterService = mockSystemParameterService;
        controller.payAsYouGoValidator = mockPayAsYouGoValidator;
        controller.busPassValidator = mockBusPassValidator;
        controller.travelCardValidator = mockTravelCardValidator;
        controller.zoneMappingValidator = mockZoneMappingValidator;
        controller.cartValidator = mockCartValidator;
        controller.cardService = mockCardService;
        setField(controller, "securityService", mockSecurityService);
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO1());
    }

    @Test
    public void shouldGetCartCmd() {
        assertNotNull(controller.getCartCmd());
    }

    @Test
    public void shouldPopulateModels() {
        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(100);
        controller.populateModels(model);
        assertTrue(model.containsAttribute(PageAttribute.CART_SHIPPING_METHODS));
    }

    @Test
    public void shouldpopulateCartShippingSelectList() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        controller.populateCartShippingSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.CART_SHIPPING_METHODS));
    }

    @Test
    public void shouldpopulateSecurityQuestionsSelectList() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        controller.populateSecurityQuestionSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.SECURITY_QUESTIONS));
    }

    @Test
    public void shouldpopulateBasketTicketTypesSelectList() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        controller.populateBasketTicketTypesSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.BASKET_TICKET_TYPES));
    }

    @Test
    public void shouldpopulatePayAsYouGoCreditBalancesSelectList() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        controller.populatePayAsYouGoCreditBalancesSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.PAY_AS_YOU_GO_CREDIT_BALANCES));
    }

    @Test
    public void shouldpopulatePayAsYouGoAutoTopUpAmtsSelectList() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        controller.populatePayAsYouGoAutoTopUpAmtsSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.PAY_AS_YOU_GO_AUTO_TOPUP_AMOUNTS));
    }

    @Test
    public void shouldpopulateStartDatesSelectList() {
        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(100);
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        controller.populateStartDatesSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.START_DATES));
    }
    
    @Test
    public void shouldpopulateEmailRemindersSelectList() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        controller.populateEmailRemindersSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.BASKET_EMAIL_REMINDERS));
    }

    @Test
    public void shouldpopulateTravelCardTypesSelectList() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        controller.populateTravelCardTypesSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.TRAVEL_CARD_TYPES));
    }

    @Test
    public void shouldpopulateTravelCardZonesSelectList() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        controller.populateTravelCardZonesSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.TRAVEL_CARD_ZONES));
    }

    @Test
    public void viewCartShouldShowAddOrder() {
        when(mockCartService.createCartFromCustomerId(anyLong())).thenReturn(getNewCartDTOWithItem());

        ModelAndView result = controller.viewCart(mockSession, CustomerTestUtil.CUSTOMER_ID_1);

        assertEquals(PageView.INV_ORDER_ADD, result.getViewName());
        assertTrue(result.getModel().containsKey(PageAttribute.CART_DTO));
        assertTrue(result.getModel().get(PageAttribute.CART_DTO) instanceof CartDTO);
    }

    @Test
    public void viewCartShouldShowAddOrderWithNullCardDTO() {
        when(mockCartService.createCartFromCustomerId(anyLong())).thenReturn(getNewCartDTOWithItem());

        ModelAndView result = controller.viewCart(mockSession, CustomerTestUtil.CUSTOMER_ID_1);

        assertEquals(PageView.INV_ORDER_ADD, result.getViewName());
        assertTrue(result.getModel().containsKey(PageAttribute.CART_DTO));
        assertTrue(result.getModel().get(PageAttribute.CART_DTO) instanceof CartDTO);
    }

    @Test
    public void deleteItemShouldShowAddOrder() {
        when(mockCartService.deleteItem(any(CartDTO.class), anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockCartAdminService.removeRefundableDepositAndShippingCost(any(CartDTO.class))).thenReturn(getNewCartDTOWithItem());
        when(mockPayAsYouGoService.removeNonApplicableAutoTopUpCartItem(any(CartDTO.class))).thenReturn(getNewCartDTOWithItem());

        ModelAndView result = controller.deleteItem(mockSession, mockCmd, CustomerTestUtil.CUSTOMER_ID_1);

        verify(mockCartService, atLeastOnce()).deleteItem(any(CartDTO.class), anyLong());
        verify(mockCartAdminService, atLeastOnce()).removeRefundableDepositAndShippingCost(any(CartDTO.class));
        verify(mockPayAsYouGoService, atLeastOnce()).removeNonApplicableAutoTopUpCartItem(any(CartDTO.class));
        assertEquals(PageView.INV_ORDER_ADD, result.getViewName());
        assertTrue(result.getModel().containsKey(PageAttribute.CART_DTO));
        assertTrue(result.getModel().get(PageAttribute.CART_DTO) instanceof CartDTO);
    }

    @Test
    public void addPayAsYouGoToCartShouldShowAddOrderAndCallPayAsYouGoService() {
        when(mockPayAsYouGoService.addCartItemForNewCard(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithItem());
        when(mockResult.hasErrors()).thenReturn(false);

        ModelAndView result = controller.addPayAsYouGoToCart(mockSession, mockCmd, mockResult);

        verify(mockPayAsYouGoService, atLeastOnce()).addCartItemForNewCard(any(CartDTO.class), any(CartItemCmdImpl.class));
        assertEquals(PageView.INV_ORDER_ADD, result.getViewName());
        assertTrue(result.getModel().containsKey(PageAttribute.CART_DTO));
        assertTrue(result.getModel().get(PageAttribute.CART_DTO) instanceof CartDTO);
    }

    @Test
    public void addPayAsYouGoToCartShouldShowAddOrder() {
        when(mockResult.hasErrors()).thenReturn(true);

        ModelAndView result = controller.addPayAsYouGoToCart(mockSession, mockCmd, mockResult);

        verify(mockPayAsYouGoService, never()).addCartItemForNewCard(any(CartDTO.class), any(CartItemCmdImpl.class));
        assertEquals(PageView.INV_ORDER_ADD, result.getViewName());
        assertTrue(result.getModel().containsKey(PageAttribute.CART_DTO));
    }

    @Test
    public void addBusPassToCartShouldShowAddOrderAndCallBusPassService() {
        when(mockBusPassService.addCartItemForNewCard(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithItem());
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        doNothing().when(mockBusPassValidator).validate(any(), any(Errors.class));
        when(mockBusPassValidator.supports(CartItemCmdImpl.class)).thenReturn(true);
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockCmd.getCartItemCmd()).thenReturn(CartItemTestUtil.getAnnualBusPassCartItem());

        ModelAndView result = controller.addBusPassToCart(mockSession, mockCmd, mockResult);

        verify(mockCartService, atLeastOnce()).findById(anyLong());
        verify(mockBusPassService, atLeastOnce()).addCartItemForNewCard(any(CartDTO.class), any(CartItemCmdImpl.class));
        assertEquals(PageView.INV_ORDER_ADD, result.getViewName());
        assertTrue(result.getModel().containsKey(PageAttribute.CART_DTO));
        assertTrue(result.getModel().get(PageAttribute.CART_DTO) instanceof CartDTO);
    }

    @Test
    public void addBusPassToCartShouldShowAddOrder() {
        when(mockBusPassService.addCartItemForNewCard(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithItem());
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        doNothing().when(mockBusPassValidator).validate(any(), any(Errors.class));
        when(mockBusPassValidator.supports(CartItemCmdImpl.class)).thenReturn(true);
        when(mockResult.hasErrors()).thenReturn(true);
        when(mockCmd.getCartItemCmd()).thenReturn(CartItemTestUtil.getAnnualBusPassCartItem());

        ModelAndView result = controller.addBusPassToCart(mockSession, mockCmd, mockResult);

        verify(mockCartService, atLeastOnce()).findById(anyLong());
        verify(mockBusPassService, never()).addCartItemForNewCard(any(CartDTO.class), any(CartItemCmdImpl.class));
        assertEquals(PageView.INV_ORDER_ADD, result.getViewName());
        assertTrue(result.getModel().containsKey(PageAttribute.CART_DTO));
        assertTrue(result.getModel().get(PageAttribute.CART_DTO) instanceof CartDTO);
    }

    
    @Test
    public void addTravelCardToCartShouldShowAddOrderAndCallTravelCardService() {
        RedirectAttributes mokcRedirectAttributes = mock(RedirectAttributes.class);
        when(mockTravelCardService.addCartItemForNewCard(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithItem());
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        doNothing().when(mockTravelCardValidator).validate(any(), any(Errors.class));
        doNothing().when(mockZoneMappingValidator).validate(any(), any(Errors.class));
        doNothing().when(mockCartValidator).validate(any(), any(Errors.class));
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockCmd.getCartItemCmd()).thenReturn(CartItemTestUtil.getAnnualBusPassCartItem());

        ModelAndView result = controller.addTravelCardToCart(mockSession, mockCmd, mockResult, mokcRedirectAttributes);

        verify(mockTravelCardService, atLeastOnce()).addCartItemForNewCard(any(CartDTO.class), any(CartItemCmdImpl.class));
        assertEquals(PageView.INV_ORDER_ADD, result.getViewName());
        assertTrue(result.getModel().containsKey(PageAttribute.CART_DTO));
        assertTrue(result.getModel().get(PageAttribute.CART_DTO) instanceof CartDTO);
    }

   
    @Test
    public void addTravelCardToCartShouldShowAddOrder() {
        RedirectAttributes mokcRedirectAttributes = mock(RedirectAttributes.class);
        when(mockTravelCardService.addCartItemForNewCard(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithItem());
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        doNothing().when(mockTravelCardValidator).validate(any(), any(Errors.class));
        doNothing().when(mockZoneMappingValidator).validate(any(), any(Errors.class));
        doNothing().when(mockCartValidator).validate(any(), any(Errors.class));
        when(mockResult.hasErrors()).thenReturn(true);
        when(mockCmd.getCartItemCmd()).thenReturn(CartItemTestUtil.getAnnualBusPassCartItem());

        ModelAndView result = controller.addTravelCardToCart(mockSession, mockCmd, mockResult, mokcRedirectAttributes);

        verify(mockTravelCardService, never()).addCartItemForNewCard(any(CartDTO.class), any(CartItemCmdImpl.class));
        assertEquals(PageView.INV_ORDER_ADD, result.getViewName());
        assertTrue(result.getModel().containsKey(PageAttribute.CART_DTO));
        assertTrue(result.getModel().get(PageAttribute.CART_DTO) instanceof CartDTO);
    }

    @Test
    public void emptyBasketShouldShowAddOrderAndCallEmptyCart() {
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        ModelAndView result = controller.emptyBasket(mockSession, mockCmd);
        verify(mockCartService, atLeastOnce()).emptyCart((CartDTO) any());
        assertEquals(PageView.INV_ORDER_ADD, (result.getViewName()));
    }

    @Test
    public void continueCartShouldShowCheckout() {
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        ModelAndView result = controller.continueCart(mockSession, mockCmd);
        verify(mockCartService, atLeastOnce()).findById(anyLong());
        assertEquals(PageUrl.VERIFY_SECURITY_QUESTION, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void continueCartWithCartItemsShouldShowCheckout() {
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getCartDTOWithAllItemsForOrderNewCard());
        when(mockCartAdminService.applyShippingCost(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(
                        CartTestUtil.getCartDTOWithAllItemsForOrderNewCard());

        ModelAndView result = controller.continueCart(mockSession, mockCmd);

        verify(mockCartService, atLeastOnce()).findById(anyLong());
        assertEquals(PageUrl.VERIFY_SECURITY_QUESTION, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void updateTotalCostShouldShowCart() {
        ModelAndView result = controller.updateTotalCost(mockSession, mockCmd);
        assertEquals(PageView.INV_ORDER_ADD, result.getViewName());
    }

    @Test
    public void updateTotalCostShouldShowCartAndApplyShippingCost() {
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockCmd.getShippingType()).thenReturn(SHIPPING_TYPE_RECORDED_DELIVERY);
        ModelAndView result = controller.updateTotalCost(mockSession, mockCmd);

        verify(mockCartAdminService, atLeastOnce()).applyShippingCost(any(CartDTO.class), any(CartItemCmdImpl.class));
        verify(mockCartService, atLeastOnce()).findById(anyLong());
        assertEquals(PageView.INV_ORDER_ADD, result.getViewName());
    }
}
