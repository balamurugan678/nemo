package com.novacroft.nemo.tfl.online.controller;

import com.novacroft.nemo.tfl.common.constant.PageView;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for CheckoutTermsAndConditionsController
 */
public class CheckoutTermsAndConditionsControllerTest {

    @Test
    public void shouldShowCheckoutTermsAndConditions() {
        CheckoutTermsAndConditionsController controller = new CheckoutTermsAndConditionsController();
        ModelAndView result = controller.viewTermsAndConditions();
        assertEquals(PageView.CHECKOUT_TERMS_AND_CONDITIONS, result.getViewName());
    }

}
