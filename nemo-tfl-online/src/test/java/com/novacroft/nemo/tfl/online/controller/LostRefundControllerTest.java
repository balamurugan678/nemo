package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.RefundScenarioHotListReasonTypeTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CardUpdateService;
import com.novacroft.nemo.tfl.common.application_service.HotlistCardService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.RefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.RefundScenarioHotListReasonTypeDataService;
import com.novacroft.nemo.tfl.common.form_validator.PaymentMethodValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;

public class LostRefundControllerTest {

	private LostRefundController controller;
    private CartCmdImpl mockCartCmdImpl;
    private RedirectAttributes redirectAttributes;
	private BindingResult bindingResult;
	private HttpSession mockHttpSession;
	private CartService mockNewCartService;
	private RefundService mockRefundService;
	private RefundScenarioHotListReasonTypeDataService mockRefundScenarioHotListReasonTypeDataService;
	private HotlistCardService mockHotlistCardService;
	private CardDataService mockCardDataService;
	
	@Before
    public void setUp() {
        mockCartCmdImpl = mock(CartCmdImpl.class);
        controller = new LostRefundController();
        controller.cardUpdateService = mock(CardUpdateService.class);
        controller.refundService=mock(RefundService.class);
        controller.selectListService=mock(SelectListService.class);
        controller.refundPaymentService=mock(RefundPaymentService.class);
        controller.paymentMethodValidator = mock(PaymentMethodValidator.class);
        controller.refundScenarioHotListReasonTypeDataService = mock(RefundScenarioHotListReasonTypeDataService.class);
        controller.cartAdministrationService = mock(CartAdministrationService.class);
        controller.hotlistCardService = mock (HotlistCardService.class);
        
        bindingResult=mock(BindingResult.class);
        redirectAttributes=mock(RedirectAttributes.class);
        mockHttpSession = mock(HttpSession.class);
        mockNewCartService = mock(CartService.class);
        mockRefundService = mock(RefundService.class);
        mockRefundScenarioHotListReasonTypeDataService = mock(RefundScenarioHotListReasonTypeDataService.class);
        mockHotlistCardService = mock(HotlistCardService.class);
        mockCardDataService = mock(CardDataService.class);
        
        controller.cardDataService = mockCardDataService;
        controller.refundService = mockRefundService;
        controller.refundScenarioHotListReasonTypeDataService = mockRefundScenarioHotListReasonTypeDataService;
        controller.hotlistCardService = mockHotlistCardService;
        
        when(mockRefundService.getCartDTOUsingCartSessionDataDTOInSession(mockHttpSession)).thenReturn(CartTestUtil.getNewCartDTOWithProductItemWithRefund());
        when(mockCardDataService.findByCardNumber(anyString())).thenReturn(CardTestUtil.getTestCardDTO1());
    }
	
	@Test
	public void getCartShouldReturnCartCmdImplWithCardNumber() {
		CartCmdImpl cartCmdImpl = controller.getCart(CardTestUtil.OYSTER_NUMBER_1);
		
		assertEquals(CardTestUtil.OYSTER_NUMBER_1, cartCmdImpl.getCardNumber());
	}

	@Test
	public void testViewRefundSummary() {
		when(mockRefundService.getCartDTOForRefund(anyString(), anyString(), (Boolean) anyObject())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
		doNothing().when(mockRefundService).storeCartIdInCartSessionDataDTOInSession((HttpSession) anyObject(), anyLong());

		ModelAndView result = controller.viewRefundSummary(mockHttpSession, mockCartCmdImpl, OYSTER_NUMBER_1);
		assertEquals(PageView.LOST_CARD_REFUND_SUMMARY, result.getViewName());
	}

	@Test
	public void testConfirmRefundShouldGoToConfirmLostCardPage() {
		when(mockCartCmdImpl.getPaymentType()).thenReturn(PaymentType.WEB_ACCOUNT_CREDIT.code());
		when(mockRefundService.getCartDTOUsingCartSessionDataDTOInSession((HttpSession) anyObject())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
		when(mockNewCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
		
		ModelAndView result = controller.confirmRefund(mockHttpSession, mockCartCmdImpl, bindingResult);
		assertEquals(PageView.CONFIRM_LOST_CARD_REFUND, result.getViewName());
	}

	@Test
	public void testShowLostCardRequestedConfirmationPage() {
		Mockito.when(mockCartCmdImpl.getCartDTO()).thenReturn(mock(CartDTO.class));
		ModelAndView result = controller.showConfirmation(mockHttpSession, mockCartCmdImpl, redirectAttributes);
		assertEquals(PageUrl.LOST_CARD_REFUND_CONFIRMATION, ((RedirectView)result.getView()).getUrl());
	}
	
	@Test
	public void testConfirmRefundShouldGoToLostCardRefundSummaryPageIfNoValidationErrors() {
		when(mockCartCmdImpl.getPaymentType()).thenReturn(PaymentType.AD_HOC_LOAD.code());
		when(mockRefundService.getCartDTOUsingCartSessionDataDTOInSession((HttpSession) anyObject())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
		when(mockNewCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
		
		ModelAndView result = controller.confirmRefund(mockHttpSession, mockCartCmdImpl, bindingResult);
		assertEquals(PageView.LOST_CARD_REFUND_SUMMARY, result.getViewName());
	}
	
	@Test
	public void testConfirmRefundShouldGoToLostCardRefundSummaryPageIfValidationErrors() {
		when(bindingResult.hasErrors()).thenReturn(true);
		
		ModelAndView result = controller.confirmRefund(mockHttpSession, mockCartCmdImpl, bindingResult);
		assertEquals(PageView.LOST_CARD_REFUND_SUMMARY, result.getViewName());
	}

	@Test
	public void testShowLostCardRequestedConfirmationPageWhenRefundScenarioHotListReasonTypeDTONotNull() {
		Mockito.when(mockCartCmdImpl.getCartDTO()).thenReturn(mock(CartDTO.class));
		when(mockRefundScenarioHotListReasonTypeDataService.findByRefundScenario(RefundScenarioEnum.find(CartType.LOST_REFUND.code()))).thenReturn(RefundScenarioHotListReasonTypeTestUtil.getRefundScenarioHotListReasonTypeDTO1());
        doNothing().when(mockHotlistCardService).toggleCardHotlisted(anyString(), anyInt());
		
		ModelAndView result = controller.showConfirmation(mockHttpSession, mockCartCmdImpl, redirectAttributes);
		
		assertEquals(PageUrl.LOST_CARD_REFUND_CONFIRMATION, ((RedirectView)result.getView()).getUrl());
	}
}
