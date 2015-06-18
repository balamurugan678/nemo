package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.tfl.common.constant.PageAttribute.CARD_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.REFUND_AMOUNT;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.REFUND_CASE_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageView.VIEW_LOST_CARD_REFUND_CONFIRMATION;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.tfl.common.constant.PageUrl;

@Controller
@RequestMapping(value = PageUrl.LOST_CARD_REFUND_CONFIRMATION)
public class LostCardRefundConfirmationController extends OnlineBaseController{

	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewRefundSummary(@RequestParam(CARD_NUMBER) String cardNumber,
    		@RequestParam(REFUND_AMOUNT) String refundAmount,@RequestParam(REFUND_CASE_NUMBER) String caseNumber) {
		
		ModelAndView modelAndView = new ModelAndView(VIEW_LOST_CARD_REFUND_CONFIRMATION);
		modelAndView.addObject(REFUND_AMOUNT, refundAmount);
		modelAndView.addObject(CARD_NUMBER, cardNumber);
		modelAndView.addObject(REFUND_CASE_NUMBER, caseNumber);
		return modelAndView;
		
    }
	
}
