package com.novacroft.nemo.tfl.online.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.novacroft.nemo.tfl.common.constant.PageView;

public class ConfirmationControllerTest {
	
	private ConfirmationController controller;
	private RedirectAttributes redirectAttributes;
    
    @Before
    public void setUp() {
        controller = new ConfirmationController();
        redirectAttributes = new RedirectAttributesModelMap();
               
    }
    
    @Test
    public void shouldShowConfirmation() {
        ModelAndView result = controller.showConfirmation(redirectAttributes);
        assertEquals(PageView.CONFIRMATION, result.getViewName());
    }

}
