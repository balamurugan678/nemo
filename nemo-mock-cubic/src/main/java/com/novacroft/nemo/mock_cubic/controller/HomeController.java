package com.novacroft.nemo.mock_cubic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import static com.novacroft.nemo.mock_cubic.constant.Constant.HOME_URL;
import static com.novacroft.nemo.mock_cubic.constant.Constant.HOME_VIEW;

/**
 * CUBIC mock home controller
 */
@Controller
@RequestMapping(value = HOME_URL)
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showHomePage() {
        return new ModelAndView(HOME_VIEW);
    }
}
