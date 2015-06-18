package com.novacroft.nemo.tfl.online.controller;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.tfl.common.constant.Page;

import static org.mockito.Mockito.*;

public class AutoTopUpConfirmationOnPaymentCardChangeTest {
	
	AutoTopUpConfirmationOnPaymentCardChangeController controller = mock(AutoTopUpConfirmationOnPaymentCardChangeController.class);
	
	@Test
	public void shouldPostViewAutoTopUp() {
		when(controller.postViewAutoTopUp(null, null)).thenCallRealMethod();
		ModelAndView result = controller.postViewAutoTopUp(null, null);
		assert(result.getViewName().contains(Page.AUTO_TOP_UP_CONFIRMATION_ON_PAYMENT_CARD_CHANGE));
	}

}
