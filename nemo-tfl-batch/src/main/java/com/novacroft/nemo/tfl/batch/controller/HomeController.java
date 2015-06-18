package com.novacroft.nemo.tfl.batch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Home
 */
@RequestMapping("Home.htm")
@Controller
public class HomeController {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showHomePage() {
        return new ModelAndView("HomeView");
    }
}
