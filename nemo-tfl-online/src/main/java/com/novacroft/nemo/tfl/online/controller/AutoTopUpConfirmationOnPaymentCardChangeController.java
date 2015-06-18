package com.novacroft.nemo.tfl.online.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.tfl.common.command.impl.ManageCardCmd;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;

@Controller
@RequestMapping(value = PageUrl.AUTO_TOP_UP_CONFIRMATION_ON_PAYMENT_CARD_CHANGE)
public class AutoTopUpConfirmationOnPaymentCardChangeController {
	
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView postViewAutoTopUp(@ModelAttribute(PageCommand.MANAGE_CARD) ManageCardCmd cmd, HttpSession session) {
    	return new ModelAndView(PageView.AUTO_TOP_UP_CONFIRMATION_ON_PAYMENT_CARD_CHANGE);
    }

}
