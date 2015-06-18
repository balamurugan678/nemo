package com.novacroft.nemo.tfl.online.controller;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;

@RunWith(MockitoJUnitRunner.class)
public class ViewRefundDetailsControllerTest {
	@Mock
	private RefundService mockRefundSrevice;
	
	@Mock
	private ViewRefundDetailsController mockViewRefundDetailsController;
	
	@Before
	public void setUp(){
		mockViewRefundDetailsController.refundService = mockRefundSrevice;
	}
	
	
	 @Test
	 public void getRefundsShouldReturnCorrectView(){
	    when(mockViewRefundDetailsController.getLoggedInUserCustomerId()).thenReturn(1l);
	    when(mockViewRefundDetailsController.loadRefundDetails(anyLong())).thenCallRealMethod();
	    
	    ModelAndView modelAndView =  mockViewRefundDetailsController.loadRefundDetails(1l);
	    String viewName = modelAndView.getViewName();
	    assertTrue(PageView.VIEW_REFUND_DETAILS.equals(viewName));
	       
	 }
	 
	 @Test
	 public void cancelShouldReturnToViewRefundTest(){
	     doCallRealMethod().when(mockViewRefundDetailsController).cancel();
	     ModelAndView modelAndView = mockViewRefundDetailsController.cancel();
	     RedirectView v = (RedirectView) modelAndView.getView();
	     assertTrue(PageUrl.VIEW_REFUNDS.equalsIgnoreCase(v.getUrl()));
	 }	     

}
