package com.novacroft.nemo.common.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Common MVC controller functions
 */
public abstract class CommonController {
    /**
     * Status message model attribute name.
     */
    public static final String STATUS_MESSAGE_ATTRIBUTE_NAME = "statusMessage";

    /**
     * Add the status message to the model.
     */
    @ModelAttribute(STATUS_MESSAGE_ATTRIBUTE_NAME)
    public String addStatusMessage(
            @RequestParam(value = STATUS_MESSAGE_ATTRIBUTE_NAME, required = false) String statusMessage) {
        return (statusMessage != null) ? statusMessage : "";
    }

    /**
     * Set the status message to a content code.
     */
    public void setFlashStatusMessage(RedirectAttributes redirectAttributes, String statusMessageCode) {
        setFlashAttribute(redirectAttributes, STATUS_MESSAGE_ATTRIBUTE_NAME, statusMessageCode);
    }

    /**
     * Clear the status message
     */
    protected void clearFlashStatusMessage(RedirectAttributes redirectAttributes) {
        setFlashAttribute(redirectAttributes, STATUS_MESSAGE_ATTRIBUTE_NAME, "");
    }

    /**
     * Set a flash attribute
     */
    protected void setFlashAttribute(RedirectAttributes redirectAttributes, String attribute, Object value) {
        redirectAttributes.addFlashAttribute(attribute, value);
    }
}
