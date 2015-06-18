package com.novacroft.nemo.mock_cubic.controller;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

/**
 * HomeController unit tests
 */
public class HomeControllerTest {

    @Test
    public void shouldShowHomePage() {
        HomeController controller = new HomeController();
        ModelAndView result = controller.showHomePage();
        assertViewName(result, "HomeView");
    }
}
