package com.novacroft.nemo.tfl.online.controller;

import com.novacroft.nemo.tfl.common.constant.PageView;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for UnexpectedErrorController
 */
public class UnexpectedErrorControllerTest {
    @Test
    public void shouldHandleUnexpectedError() {
        UnexpectedErrorController controller = new UnexpectedErrorController();
        ModelAndView result = controller.showUnexpectedError();
        assertEquals(PageView.UNEXPECTED_ERROR, result.getViewName());
    }
}
