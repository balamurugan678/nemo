package com.novacroft.nemo.tfl.online.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.transfer.RefundOrderItemDTO;

@RunWith(MockitoJUnitRunner.class)
public class ViewRefundsControllerTest {
	
	@Mock
	private RefundService mockRefundService;
	@Mock
	private SecurityService mockSecurityService;
	@Mock
	private ViewRefundsController mockViewRefundsController;
	
	@Before
	public void setUp(){
		mockViewRefundsController.refundService = mockRefundService;
	}
	
	
	 @Test
	 public void getRefundsShouldReturnCorrectView(){
	 	RefundOrderItemDTO refund = new RefundOrderItemDTO(4L, 3333l, "444444", new Date());
    	List<RefundOrderItemDTO> refundsList = new ArrayList<>();
	    when(mockRefundService.findAllRefundsForCustomer(anyLong())).thenReturn(refundsList);
	    when(mockViewRefundsController.getLoggedInUserCustomerId()).thenReturn(1l);
	    when(mockViewRefundsController.getRefunds()).thenCallRealMethod();
	    
	    ModelAndView modelAndView =  mockViewRefundsController.getRefunds();
	    String viewName = modelAndView.getViewName();
	    assertTrue(PageView.VIEW_REFUNDS.equals(viewName));
	       
	 }
	
	

}
