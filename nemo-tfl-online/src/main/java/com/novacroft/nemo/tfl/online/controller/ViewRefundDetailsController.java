package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.tfl.common.constant.PageParameterValue.REFUND;
import static com.novacroft.nemo.tfl.common.constant.PageView.VIEW_REFUND_DETAILS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.transfer.RefundOrderItemDTO;

@Controller
@RequestMapping(value = PageUrl.VIEW_REFUND_DETAILS)
public class ViewRefundDetailsController extends OnlineBaseController {

	@Autowired
	protected RefundService refundService;

	@RequestMapping(method = RequestMethod.GET, params = "refundId")
	public ModelAndView loadRefundDetails(@RequestParam Long refundId) {
		final Long customerId = getLoggedInUserCustomerId(); 
		RefundOrderItemDTO refundItem =  refundService.findRefundDetailsForIdAndCustomer(refundId, customerId);	  
        return new ModelAndView(VIEW_REFUND_DETAILS, REFUND, refundItem);
	} 
	
	@Override
	@RequestMapping(params = PageParameterArgument.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel() {
        return new ModelAndView(new RedirectView(PageUrl.VIEW_REFUNDS));
    }
}
