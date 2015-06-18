package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CartTestUtil.getTestCartDTO1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.SelectListTestUtil.getTestSelectListDTO;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;

/**
 * Unit tests for OrderOysterCardController
 */
public class OrderOysterCardControllerTest {

    @SuppressWarnings("deprecation")
    private OrderOysterCardController controller;
    private OrderOysterCardController mockController;
    private CartCmdImpl mockCmd;
    private CardDataService mockCardDataService;
    private CartService mockNewCartService;
    private SecurityService mockSecurityService;
    private Model mockModel;
    private HttpSession mockSession;
    private CustomerService mockCustomerService;
    private SelectListService mockSelectListService;

    @SuppressWarnings("deprecation")
    @Before
    public void setUp() {
        controller = new OrderOysterCardController();
        mockCmd = mock(CartCmdImpl.class);
        mockCardDataService = mock(CardDataService.class);
        mockSecurityService = mock(SecurityService.class);
        mockNewCartService = mock(CartService.class);
        mockModel = mock(Model.class);
        mockSession = mock(HttpSession.class);
        mockCustomerService = mock(CustomerService.class);
        mockSelectListService = mock(SelectListService.class);
        
        controller.cardDataService = mockCardDataService;
        controller.cartService = mockNewCartService;
        controller.customerService = mockCustomerService;
        controller.selectListService = mockSelectListService;
        
        setField(controller, "securityService", mockSecurityService);
        mockController = mock(OrderOysterCardController.class);
        mockController.selectListService = mockSelectListService;
    }

    @Test
    public void shouldShowBasket() {
        when(mockSecurityService.isLoggedIn()).thenReturn(true);
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockCustomerService.createCard(anyLong(), anyString())).thenReturn(getTestCardDTO1());
        when(mockNewCartService.createCartFromCustomerId(anyLong())).thenReturn(getTestCartDTO1());

        ModelAndView result = controller.viewBasket(mockSession, mockModel);
        assertEquals(PageView.BASKET, result.getViewName());
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotShowBasketOnNullCart() {
        controller.viewBasket(mockSession, mockModel);
    }

    @Test
    public void shouldSetCartSessionDataDTOToSession() {
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockCustomerService.createCard(anyLong(), anyString())).thenReturn(getTestCardDTO1());
        when(mockNewCartService.createCartFromCustomerId(anyLong())).thenReturn(getTestCartDTO1());
        when(mockNewCartService.createCart()).thenReturn(getTestCartDTO1());

        ModelAndView result = controller.viewBasket(mockSession, mockModel);
        verify(mockSession, atLeastOnce()).setAttribute(anyString(), any());
        assertEquals(PageView.BASKET, result.getViewName());
    }

    @Test
    public void shouldSetCartDTOToModel() {
        when(mockSecurityService.isLoggedIn()).thenReturn(true);
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockCustomerService.createCard((Long)any(), anyString())).thenReturn(getTestCardDTO1());
        when(mockNewCartService.createCartFromCustomerId(anyLong())).thenReturn(getTestCartDTO1());

        ModelAndView result = controller.viewBasket(mockSession, mockModel);

        verify(mockModel, atLeastOnce()).addAttribute(any(Object.class));
        verify(mockSession, atLeastOnce()).setAttribute(anyString(), any());
        assertEquals(PageView.BASKET, result.getViewName());
    }

    @Test
    public void cancelShouldRedirectToOysterHome() {
        ModelAndView result = controller.cancel();
        assertEquals(PageUrl.OYSTER_HOME, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void shouldPopulateBasketTicketTypesSelectList() {
        doCallRealMethod().when(mockController).populateBasketTicketTypesSelectList((Model)any());
        when(mockSelectListService.getSelectList(anyString())).thenReturn(getTestSelectListDTO());
        mockController.populateBasketTicketTypesSelectList(mockModel);
        verify(mockSelectListService, atLeastOnce()).getSelectList(anyString());
    }
}
