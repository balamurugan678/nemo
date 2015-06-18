package com.novacroft.nemo.tfl.online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;

@Controller
@RequestMapping(PageUrl.CONFIRMATION)
public class ConfirmationController {

	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView showConfirmation(RedirectAttributes redirectAttributes) {
        return new ModelAndView(PageView.CONFIRMATION);
    }
}

