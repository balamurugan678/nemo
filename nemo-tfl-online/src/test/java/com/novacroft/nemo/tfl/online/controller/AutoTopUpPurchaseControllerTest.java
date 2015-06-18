package com.novacroft.nemo.tfl.online.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.test_support.CartSessionDataTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.PickUpLocationValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Unit tests for AutoTopUpPurchaseController
 */
public class AutoTopUpPurchaseControllerTest {
    private AutoTopUpPurchaseController controller;
    private CartCmdImpl mockCmd;
    private BeanPropertyBindingResult mockResult;
    private PickUpLocationValidator mockPickUpLocationValidator;
    private CartService mockCartService;
    private HttpSession mockSession;
    private CartSessionData cartSessionData;
    private CartDTO cartDTO;
    @Before
    public void setUp() {
        controller = new AutoTopUpPurchaseController();

        mockCmd = mock(CartCmdImpl.class);
        mockResult = mock(BeanPropertyBindingResult.class);
        mockPickUpLocationValidator = mock(PickUpLocationValidator.class);
        mockCartService = mock(CartService.class);
        mockSession = mock(HttpSession.class);
        cartSessionData = CartSessionDataTestUtil.getTestCartSessionDataDTO1();
        cartDTO = CartTestUtil.getTestCartDTO1();
        controller.pickUpLocationValidator = mockPickUpLocationValidator;
        controller.cartService = mockCartService;

        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(cartSessionData);
        when(mockCartService.findById(anyLong())).thenReturn(cartDTO);

        doNothing().when(mockPickUpLocationValidator).validate(anyObject(), any(Errors.class));
    }

    @Test
    public void showCollectPurchase() {
        ModelAndView result = controller.showAutoTopUpPurchase(mockSession);
        assertEquals(PageView.AUTO_TOP_UP_PURCHASE, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.CART));
        assertTrue(result.getModel().get(PageCommand.CART) instanceof CartCmdImpl);
    }

    @Test
    public void collectPurchaseShouldShowCheckoutOnValidInput() {
        when(mockResult.hasErrors()).thenReturn(false);
        ModelAndView result = controller.autoTopUpPurchase(mockSession, mockCmd, mockResult);
        assertEquals(PageUrl.CHECKOUT, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void collectPurchaseShouldShowCollectPurchaseOnInValidInput() {
        when(mockResult.hasErrors()).thenReturn(true);
        ModelAndView result = controller.autoTopUpPurchase(mockSession, mockCmd, mockResult);
        assertEquals(PageView.AUTO_TOP_UP_PURCHASE, result.getViewName());
    }

}
