package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.tfl.common.constant.PageParameterValue.REFUNDS;
import static com.novacroft.nemo.tfl.common.constant.PageView.VIEW_REFUNDS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.transfer.RefundOrderItemDTO;

@Controller
@RequestMapping(value = PageUrl.VIEW_REFUNDS)
public class ViewRefundsController extends OnlineBaseController {

	
	@Autowired
	protected RefundService refundService;
	    
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getRefunds() {
		final Long customerId = getLoggedInUserCustomerId(); 
		List<RefundOrderItemDTO> refundItems =  refundService.findAllRefundsForCustomer(customerId);	  
        return new ModelAndView(VIEW_REFUNDS, REFUNDS, refundItems);
	}   
	
	
}
