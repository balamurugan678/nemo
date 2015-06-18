package com.novacroft.nemo.tfl.online.controller;

import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(PageUrl.CHECKOUT_TERMS_AND_CONDITIONS)
public class CheckoutTermsAndConditionsController extends OnlineBaseController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewTermsAndConditions() {
        return new ModelAndView(PageView.CHECKOUT_TERMS_AND_CONDITIONS);
    }
}
