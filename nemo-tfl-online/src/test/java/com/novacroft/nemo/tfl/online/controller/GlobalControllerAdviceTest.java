package com.novacroft.nemo.tfl.online.controller;

import com.novacroft.nemo.tfl.common.constant.PageUrl;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for GlobalControllerAdvice
 */
public class GlobalControllerAdviceTest {

    @Test
    public void shouldHandleUnexpectedError() {
        GlobalControllerAdvice controller = new GlobalControllerAdvice();
        ModelAndView result = controller.handleUnexpectedError(new Exception("Test Error!"));
        assertEquals(PageUrl.UNEXPECTED_ERROR, ((RedirectView) result.getView()).getUrl());
    }
}
