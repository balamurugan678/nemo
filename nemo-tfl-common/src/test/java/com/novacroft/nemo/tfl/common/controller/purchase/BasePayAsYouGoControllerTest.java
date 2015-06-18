package com.novacroft.nemo.tfl.common.controller.purchase;

import static com.novacroft.nemo.test_support.CartCmdTestUtil.AUTO_TOPUP_AMT;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.AUTO_TOPUP_VISIBLE_1;
import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO1;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithItem;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithPAYGAndATUItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.PayAsYouGoService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.application_service.WebCreditService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.form_validator.PayAsYouGoValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Unit tests for Pay As You Go Controller
 */
public class BasePayAsYouGoControllerTest {

    public static final String ANONYMOUS_USER = "anonymousUser";
    public static final String USER = "user";
    public static final Integer AUTO_TOP_UP_AMOUNT_1 = 1500;

    private BasePayAsYouGoController controller;
    private CartCmdImpl mockCmd;
    private CartItemCmdImpl mockCartItemCmd;
    private BeanPropertyBindingResult mockResult;
    private CustomerService mockCustomerService;
    private PayAsYouGoValidator mockPayAsYouGoValidator;
    private SecurityService mockSecurityService;
    private BindingAwareModelMap model;
    private SelectListService mockSelectListService;
    private CardDataService mockCardDataService;
    private WebCreditService mockWebCreditService;
    private PayAsYouGoService mockPayAsYouGoService;
    private CartService mockCartService;
    private HttpSession mockSession;
    private CartAdministrationService mockNewCartAdminService;

    @Before
    public void setUp() {
        controller = new BasePayAsYouGoController();
        mockCmd = mock(CartCmdImpl.class);
        mockCartItemCmd = mock(CartItemCmdImpl.class);
        mockResult = mock(BeanPropertyBindingResult.class);
        model = new BindingAwareModelMap();
        mockNewCartAdminService = mock(CartAdministrationService.class);
        mockCustomerService = mock(CustomerService.class);
        mockPayAsYouGoValidator = mock(PayAsYouGoValidator.class);
        mockSecurityService = mock(SecurityService.class);
        mockSelectListService = mock(SelectListService.class);
        mockCardDataService = mock(CardDataService.class);
        mockWebCreditService = mock(WebCreditService.class);
        mockPayAsYouGoService = mock(PayAsYouGoService.class);
        mockCartService = mock(CartService.class);
        mockSession = mock(HttpSession.class);

        controller.customerService = mockCustomerService;
        controller.payAsYouGoValidator = mockPayAsYouGoValidator;
        controller.selectListService = mockSelectListService;
        setField(controller, "securityService", mockSecurityService);
        controller.cardDataService = mockCardDataService;
        controller.webCreditService = mockWebCreditService;
        controller.payAsYouGoService = mockPayAsYouGoService;
        controller.cartService = mockCartService;
        controller.cartAdminService = mockNewCartAdminService;

        doNothing().when(mockPayAsYouGoValidator).validate(anyObject(), any(Errors.class));
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO1());
    }

    @Test
    public void shouldGetCartCmd() {
        assertNotNull(controller.getCartCmd());
    }

    @Test
    public void shouldPopulatePayAsYouGoCreditBalancesSelectList() {
        controller.populatePayAsYouGoCreditBalancesSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.PAY_AS_YOU_GO_CREDIT_BALANCES));
    }

    @Test
    public void shouldPopulatePayAsYouGoAutoTopUpAmtsSelectList() {
        controller.populatePayAsYouGoAutoTopUpAmtsSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.PAY_AS_YOU_GO_AUTO_TOPUP_AMOUNTS));
    }

    @Test
    public void shouldShowPayAsYouGo() {
        ModelAndView result = controller.viewPayAsYouGo();
        assertEquals(PageView.PAY_AS_YOU_GO, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.CART_ITEM));
        assertTrue(result.getModel().get(PageCommand.CART_ITEM) instanceof CartItemCmdImpl);
    }

    @Test
    public void addPayAsYouGoToCartShouldShowCartOnValidInput() {
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockSecurityService.getLoggedInUsername()).thenReturn(ANONYMOUS_USER);
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        ModelAndView result = controller.addPayAsYouGoToCart(mockSession, mockCartItemCmd, mockResult);

        verify(mockPayAsYouGoService, atLeastOnce()).addCartItemForNewCard(any(CartDTO.class), any(CartItemCmdImpl.class));
        verify(mockSecurityService, never()).getLoggedInUsername();
        assertEquals(PageUrl.CART, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void addPayAsYouGoToCartPAYGAndATUShouldShowCartOnValidInput() {
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockSecurityService.getLoggedInUsername()).thenReturn(ANONYMOUS_USER);
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithPAYGAndATUItems());
        ModelAndView result = controller.addPayAsYouGoToCart(mockSession, mockCartItemCmd, mockResult);

        verify(mockPayAsYouGoService, atLeastOnce()).addCartItemForNewCard(any(CartDTO.class), any(CartItemCmdImpl.class));
        verify(mockSecurityService, never()).getLoggedInUsername();
        assertEquals(PageUrl.CART, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void addPayAsYouGoToCartShouldShowAccountDetailsOnValidInput() {
        when(mockCartItemCmd.getAutoTopUpAmt()).thenReturn(AUTO_TOPUP_AMT);
        when(mockCmd.getAutoTopUpVisible()).thenReturn(AUTO_TOPUP_VISIBLE_1);
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockSecurityService.isLoggedIn()).thenReturn(false);
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockNewCartAdminService.applyShippingCost(any(CartDTO.class), any(CartItemCmdImpl.class)))
                .thenReturn(getNewCartDTOWithItem());

        ModelAndView result = controller.addPayAsYouGoToCart(mockSession, mockCartItemCmd, mockResult);

        verify(mockPayAsYouGoService, atLeastOnce()).addCartItemForNewCard(any(CartDTO.class), any(CartItemCmdImpl.class));
        assertEquals(PageUrl.CART, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void addPayAsYouGoToCartShouldShowPayAsYouGoOnInvalidInput() {
        when(mockResult.hasErrors()).thenReturn(true);
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        controller.payAsYouGoValidator = mockPayAsYouGoValidator;

        ModelAndView result = controller.addPayAsYouGoToCart(mockSession, mockCartItemCmd, mockResult);
        assertEquals(PageView.PAY_AS_YOU_GO, result.getViewName());
    }

    @Test
    public void cancelShouldRedirectToOysterHome() {
        ModelAndView result = controller.cancel();
        assertEquals(PageUrl.DASHBOARD, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void shoppingBasketShouldRedirectToCart() {
        ModelAndView result = controller.shoppingBasket();
        assertEquals(PageUrl.CART, ((RedirectView) result.getView()).getUrl());
    }

}
