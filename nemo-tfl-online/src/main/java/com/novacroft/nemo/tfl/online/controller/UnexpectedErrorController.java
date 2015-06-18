package com.novacroft.nemo.tfl.online.controller;

import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Show error page
 */
@Controller
@RequestMapping(PageUrl.UNEXPECTED_ERROR)
public class UnexpectedErrorController extends OnlineBaseController {
    protected static final Logger logger = LoggerFactory.getLogger(UnexpectedErrorController.class);

    @RequestMapping
    public ModelAndView showUnexpectedError() {
        return new ModelAndView(PageView.UNEXPECTED_ERROR);
    }
}
