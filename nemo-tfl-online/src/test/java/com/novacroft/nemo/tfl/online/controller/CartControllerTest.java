package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO1;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithItem;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithShippingItem;
import static com.novacroft.nemo.test_support.ShippingMethodItemTestUtil.getTestShippingMethodItemDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.PayAsYouGoService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodItemDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Unit tests for cart Controller
 */
public class CartControllerTest {

    private CartController controller;
    private CartCmdImpl mockCmd;
    private RedirectAttributes mockRedirectAttributes;
    private CartService mockCartService;
    private CustomerService mockCustomerService;
    private SecurityService mockSecurityService;
    private Model mockModel;
    private HttpSession mockSession;
    private CartAdministrationService mockNewCartAdminService;
    private BindingAwareModelMap model;
    private SelectListService mockSelectListService;
    private PayAsYouGoService mockPayAsYouGoService;
    private CartDTO mockCartDTO;

    public static final String ANONYMOUS_USER = "anonymousUser";
    public static final String SHIPPING_TYPE_RECORDED_DELIVERY = "Recorded Delivery";
    public static final String USER = "user";

    @Before
    public void setUp() {
        controller = new CartController();
        mockRedirectAttributes = mock(RedirectAttributes.class);
        mockCmd = mock(CartCmdImpl.class);
        mockCustomerService = mock(CustomerService.class);
        mockSecurityService = mock(SecurityService.class);
        mockCartService = mock(CartService.class);
        mockModel = mock(Model.class);
        mockSession = mock(HttpSession.class);
        mockNewCartAdminService = mock(CartAdministrationService.class);
        mockSelectListService = mock(SelectListService.class);
        mockPayAsYouGoService = mock(PayAsYouGoService.class);
        mockCartDTO = mock(CartDTO.class);
        model = new BindingAwareModelMap();

        controller.customerService = mockCustomerService;
        controller.cartService = mockCartService;
        controller.cartAdminService = mockNewCartAdminService;
        controller.selectListService = mockSelectListService;
        controller.payAsYouGoService = mockPayAsYouGoService;
        setField(controller, "securityService", mockSecurityService);
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO1());
    }

    @Test
    public void shouldGetCartCmd() {
        assertNotNull(controller.getCartCmd());
    }

    @Test
    public void shouldpopulateBasketTicketTypesSelectList() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        controller.populateBasketTicketTypesSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.CART_SHIPPING_METHODS));
    }

    @Test
    public void shouldShowCart() {
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        ModelAndView result = controller.viewCart(mockSession, mockCmd, mockRedirectAttributes, mockModel);
        assertEquals(PageView.CART, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.CART));
        assertTrue(result.getModel().get(PageCommand.CART) instanceof CartCmdImpl);
    }

    @Test
    public void shouldShowCartForCartWithShippingItem() {
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithShippingItem());
        ModelAndView result = controller.viewCart(mockSession, mockCmd, mockRedirectAttributes, mockModel);
        assertEquals(PageView.CART, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.CART));
        assertTrue(result.getModel().get(PageCommand.CART) instanceof CartCmdImpl);
    }

    @Test
    public void emptyCartShouldShowCartAndCallEmprtyCart() {
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        ModelAndView result = controller.emptyCart(mockSession, mockCmd);
        verify(mockCartService, atLeastOnce()).emptyCart((CartDTO) any());
        assertEquals(PageUrl.BASKET, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void addMoreItemsShouldShowBasket() {
        ModelAndView result = controller.addMoreItems();
        assertEquals(PageUrl.BASKET, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void continueCartShouldShowCheckout() {
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        ModelAndView result = controller.continueCart(mockSession, mockCmd);
        verify(mockCartService, atLeastOnce()).findById(anyLong());
        assertEquals(PageUrl.ACCOUNT_DETAILS, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void updateTotalCostShouldShowCart() {
        ModelAndView result = controller.updateTotalCost(mockSession, mockCmd, mockModel);
        assertEquals(PageView.CART, result.getViewName());
    }

    @Test
    public void updateTotalCostShouldShowCartAndApplyShippingCost() {
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockCmd.getShippingType()).thenReturn(SHIPPING_TYPE_RECORDED_DELIVERY);
        ModelAndView result = controller.updateTotalCost(mockSession, mockCmd, mockModel);

        verify(mockNewCartAdminService, atLeastOnce()).applyShippingCost(any(CartDTO.class), any(CartItemCmdImpl.class));
        verify(mockCartService, atLeastOnce()).findById(anyLong());
        assertEquals(PageView.CART, result.getViewName());
    }

    @Test
    public void deleteShoppingItemShouldShowCart() {
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockNewCartAdminService.removeRefundableDepositAndShippingCost(getNewCartDTOWithItem()))
                .thenReturn(getNewCartDTOWithItem());
        when(mockNewCartAdminService.removeRefundableDepositAndShippingCost(any(CartDTO.class))).thenReturn(mockCartDTO);
        when(mockPayAsYouGoService.removeNonApplicableAutoTopUpCartItem(any(CartDTO.class))).thenReturn(mockCartDTO);

        ModelAndView result = controller.deleteShoppingItem(mockSession, mockCmd, 1, mockModel);

        verify(mockCartService, atLeastOnce()).deleteItem((CartDTO) any(), anyLong());
        verify(mockNewCartAdminService, atLeastOnce()).removeRefundableDepositAndShippingCost((CartDTO) any());
        verify(mockCartService, atLeastOnce()).findById(anyLong());
        assertEquals(PageView.CART, result.getViewName());
    }

    @Test
    public void deleteShoppingItemShouldSetShippingType() {
        ShippingMethodItemDTO shippingItemDTO = getTestShippingMethodItemDTO1();
        shippingItemDTO.setName(SHIPPING_TYPE_RECORDED_DELIVERY);
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockNewCartAdminService.removeRefundableDepositAndShippingCost(getNewCartDTOWithItem()))
                .thenReturn(getNewCartDTOWithItem());
        when(mockNewCartAdminService.removeRefundableDepositAndShippingCost(any(CartDTO.class))).thenReturn(mockCartDTO);
        when(mockPayAsYouGoService.removeNonApplicableAutoTopUpCartItem(any(CartDTO.class))).thenReturn(mockCartDTO);
        when(mockCartDTO.getShippingMethodItem()).thenReturn(shippingItemDTO);

        ModelAndView result = controller.deleteShoppingItem(mockSession, mockCmd, 1, mockModel);

        verify(mockCartService, atLeastOnce()).deleteItem((CartDTO) any(), anyLong());
        verify(mockNewCartAdminService, atLeastOnce()).removeRefundableDepositAndShippingCost((CartDTO) any());
        verify(mockCartService, atLeastOnce()).findById(anyLong());
        verify(mockCmd, atLeastOnce()).setShippingType(anyString());
        assertEquals(PageView.CART, result.getViewName());
        assertNotNull(result.getModel());
    }

}
