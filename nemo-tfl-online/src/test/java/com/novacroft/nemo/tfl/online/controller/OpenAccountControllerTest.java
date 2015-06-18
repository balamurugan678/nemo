package com.novacroft.nemo.tfl.online.controller;

import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.form_validator.OysterCardValidator;

import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

/**
 * Unit tests for OpenAccountController
 */
public class OpenAccountControllerTest {

    protected static final String CARD_NUMBER = "cardNumber";

    @Test
    public void shouldShowOpenAccount() {
        OpenAccountController controller = new OpenAccountController();
        ModelAndView result = controller.viewOpenAccount();
        assertEquals(PageView.OPEN_ACCOUNT, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.CART));
        assertTrue(result.getModel().get(PageCommand.CART) instanceof CartCmdImpl);
    }

    @Test
    public void validateOysterCardNumberShouldShowAccountDetailsOnValidInput() {
        CartCmdImpl mockCmd = mock(CartCmdImpl.class);

        OysterCardValidator mockOysterCardValidator = mock(OysterCardValidator.class);
        doNothing().when(mockOysterCardValidator).validate(anyObject(), any(Errors.class));

        BeanPropertyBindingResult mockResult = mock(BeanPropertyBindingResult.class);
        when(mockResult.hasErrors()).thenReturn(false);

        OpenAccountController controller = new OpenAccountController();
        controller.oysterCardValidator = mockOysterCardValidator;

        ModelAndView result = controller.validateOysterCardNumber(mockCmd, mockResult);
        assertEquals(PageUrl.ACCOUNT_DETAILS, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void validateOysterCardNumberShouldShowOpenAccountExistOnInvalidInput() {
        CartCmdImpl mockCmd = mock(CartCmdImpl.class);

        OysterCardValidator mockOysterCardValidator = mock(OysterCardValidator.class);
        doNothing().when(mockOysterCardValidator).validate(anyObject(), any(Errors.class));

        BeanPropertyBindingResult mockResult = mock(BeanPropertyBindingResult.class);
        when(mockResult.hasErrors()).thenReturn(true);
        when(mockResult.getFieldError(CARD_NUMBER))
                .thenReturn(new FieldError(PageCommand.CART, PrivateError.CARD_NUMBER_EXIST.message(), ""));

        OpenAccountController controller = new OpenAccountController();
        controller.oysterCardValidator = mockOysterCardValidator;

        ModelAndView result = controller.validateOysterCardNumber(mockCmd, mockResult);
        assertEquals(PageView.OPEN_ACCOUNT_EXIST, result.getViewName());
    }

    @Test
    public void validateOysterCardNumberShouldShowOpenAccountOnInvalidInput() {
        CartCmdImpl mockCmd = mock(CartCmdImpl.class);

        OysterCardValidator mockOysterCardValidator = mock(OysterCardValidator.class);
        doNothing().when(mockOysterCardValidator).validate(anyObject(), any(Errors.class));

        BeanPropertyBindingResult mockResult = mock(BeanPropertyBindingResult.class);
        when(mockResult.hasErrors()).thenReturn(true);

        OpenAccountController controller = new OpenAccountController();
        controller.oysterCardValidator = mockOysterCardValidator;

        ModelAndView result = controller.validateOysterCardNumber(mockCmd, mockResult);
        assertEquals(PageView.OPEN_ACCOUNT, result.getViewName());
    }

    @Test
    public void cancelShouldRedirectToOysterHome() {
        OpenAccountController controller = new OpenAccountController();
        ModelAndView result = controller.cancel();
        assertEquals(PageUrl.OYSTER_HOME, ((RedirectView) result.getView()).getUrl());
    }

}
