package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.tfl.common.constant.PageView.VIEW_LOST_CARD_REFUND_CONFIRMATION;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

public class LostCardRefundConfirmationControllerTest {

	LostCardRefundConfirmationController controller;
	@Before
	public void setUp() throws Exception {
		this.controller = new LostCardRefundConfirmationController();
	}

	@Test
	public void testViewRefundSummary() {
		ModelAndView result = controller.viewRefundSummary("010819984445", "2000", "12312313");
		assertEquals(VIEW_LOST_CARD_REFUND_CONFIRMATION, result.getViewName());
	}

}
