package com.novacroft.nemo.tfl.online.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.FailedAutoTopUpCaseDataService;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;

public class ResettleFailedAutoTopUpControllerTest {
	
	ResettleFailedAutoTopUpController controller;
	FailedAutoTopUpCaseDataService mockFailedAutoTopUpCaseDataService;
	CustomerDataService mockCustomerDataService;
	CartService mockCartService;
	
	ResettleFailedAutoTopUpController mockController;
	
	@Before
	public void setUp() {
		controller = new ResettleFailedAutoTopUpController();
		mockFailedAutoTopUpCaseDataService = mock(FailedAutoTopUpCaseDataService.class);
		controller.failedAutoTopUpCaseDataService = mockFailedAutoTopUpCaseDataService;
		mockCustomerDataService = mock(CustomerDataService.class);
		controller.customerDataService = mockCustomerDataService;
		mockCartService = mock(CartService.class);
		controller.cartService = mockCartService;
		
		mockController = mock(ResettleFailedAutoTopUpController.class);
		mockController.customerDataService = mockCustomerDataService;
		mockController.failedAutoTopUpCaseDataService = mockFailedAutoTopUpCaseDataService;
		mockController.cartService = mockCartService;
	}

    @Test
    public void shouldShowPage() {
    	when(mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
    	when(mockFailedAutoTopUpCaseDataService.findPendingAmountByCustomerId(anyLong())).thenReturn(20);
    	when(mockController.getLoggedInUsername()).thenReturn(CustomerTestUtil.getTestCustomerDTO1().getUsername());
    	when(mockController.showPage()).thenCallRealMethod();
    	ModelAndView result = mockController.showPage();
    	assertViewName(result, PageView.RESETTLE_FAILED_AUTO_TOP_UP);
    }
    
    @Test
    public void shouldRedirectToCheckout() {
    	HttpSession mockSession = mock(HttpSession.class);
    	when(mockCartService.createCartFromCardId(anyLong())).thenReturn(CartTestUtil.getCartDTOWithAllItemsForOrderNewCard());
    	when(mockCartService.removeExpiredCartItems(any(CartDTO.class))).thenReturn(CartTestUtil.getCartDTOWithAllItemsForOrderNewCard());
    	doNothing().when(mockSession).setAttribute(anyString(), any(CartSessionData.class));
    	when(mockFailedAutoTopUpCaseDataService.findPendingAmountByCustomerId(anyLong())).thenReturn(5);
    	when(mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
    	when(mockCartService.updateCartWithoutRefundCalculationInPostProcess(any(CartDTO.class))).thenReturn(CartTestUtil.getTestCartDTO1());
    	when(mockController.redirectToCheckout(mockSession, null, null, null)).thenCallRealMethod();
    	when(mockController.getRedirectViewWithoutExposedAttributes(PageUrl.CHECKOUT)).thenReturn(new RedirectView(PageUrl.CHECKOUT));
    	ModelAndView result = mockController.redirectToCheckout(mockSession, null, null, null);
    	assert(result.getView().toString().contains(Page.CHECKOUT));
    }
}
