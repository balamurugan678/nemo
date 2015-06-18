package com.novacroft.nemo.tfl.online.controller;

import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Configuration that is applied to all controllers
 */
@ControllerAdvice
public class GlobalControllerAdvice {
    protected static final Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    @ExceptionHandler
    public ModelAndView handleUnexpectedError(Exception exception) {
        logger.error(PrivateError.UNEXPECTED.message(), exception);
        return new ModelAndView(new RedirectView(PageUrl.UNEXPECTED_ERROR));
    }
}
