package com.novacroft.nemo.common.controller;

import org.junit.Test;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import static com.novacroft.nemo.common.controller.CommonController.STATUS_MESSAGE_ATTRIBUTE_NAME;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CommonController
 */
public class CommonControllerTest {
    final String TEST_MESSAGE = "test-message";
    final String TEST_ATTRIBUTE = "test-attribute";
    final String BLANK = "";

    @Test
    public void shouldReturnStatusMessageWithNonNullInput() {
        CommonController controller = mock(CommonController.class);
        when(controller.addStatusMessage(anyString())).thenCallRealMethod();
        assertEquals(TEST_MESSAGE, controller.addStatusMessage(TEST_MESSAGE));
    }

    @Test
    public void shouldReturnEmptyStringWithNullInput() {
        CommonController controller = mock(CommonController.class);
        when(controller.addStatusMessage(anyString())).thenCallRealMethod();
        assertEquals(BLANK, controller.addStatusMessage(null));
    }

    @Test
    public void shouldSetFlashAttribute() {
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        CommonController controller = mock(CommonController.class);
        doCallRealMethod().when(controller).setFlashAttribute(any(RedirectAttributes.class), anyString(), anyObject());
        controller.setFlashAttribute(redirectAttributes, TEST_ATTRIBUTE, TEST_MESSAGE);
        assertEquals(TEST_MESSAGE, redirectAttributes.getFlashAttributes().get(TEST_ATTRIBUTE));
    }

    @Test
    public void shouldSetFlashStatusMessage() {
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        CommonController controller = mock(CommonController.class);
        doCallRealMethod().when(controller).setFlashAttribute(any(RedirectAttributes.class), anyString(), anyObject());
        doCallRealMethod().when(controller).setFlashStatusMessage(any(RedirectAttributes.class), anyString());
        controller.setFlashStatusMessage(redirectAttributes, TEST_MESSAGE);
        assertEquals(TEST_MESSAGE, redirectAttributes.getFlashAttributes().get(STATUS_MESSAGE_ATTRIBUTE_NAME));
    }

    @Test
    public void shouldClearFlashStatusMessage() {
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        redirectAttributes.addAttribute(STATUS_MESSAGE_ATTRIBUTE_NAME, TEST_MESSAGE);
        CommonController controller = mock(CommonController.class);
        doCallRealMethod().when(controller).setFlashAttribute(any(RedirectAttributes.class), anyString(), anyObject());
        doCallRealMethod().when(controller).clearFlashStatusMessage(any(RedirectAttributes.class));
        controller.clearFlashStatusMessage(redirectAttributes);
        assertEquals("", redirectAttributes.getFlashAttributes().get(STATUS_MESSAGE_ATTRIBUTE_NAME));
    }
}
