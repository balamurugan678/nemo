package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.tfl.common.application_service.*;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadRefundPaymentService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CartDataService;
import com.novacroft.nemo.tfl.common.domain.HotlistReason;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO2;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithThreeProductItems;
import static com.novacroft.nemo.test_support.CartTestUtil.getTestCartDTO1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_2;
import static com.novacroft.nemo.test_support.HotlistReasonTestUtil.HOTLIST_REASON_ID;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class RefundPaymentServiceImplTest {
    private RefundPaymentServiceImpl service;
    private CartCmdImpl cartCmdImpl;
    private CartService mockCartService;
    private WebAccountCreditRefundPaymentService mockWebAccountCreditRefundPaymentService;
    private AdHocLoadRefundPaymentService mockAdHocLoadRefundPaymentService;
    private BacsRefundPaymentService mockBacsRefundPaymentService;
    private ChequeRefundPaymentService mockChequeRefundPaymentService;
    private CartDataService mockcartDataService;
    private ApplicationContext mockApplicationContext;
    private CardDataService mockCardDataService;
    private List<CardDTO> mockList;
    private CardDTO cardDTO;
    private HotlistReason hotlistReason;

    String paymentType = "";

    @Before
    public void setUp() {
        service = new RefundPaymentServiceImpl();
        new CartItemCmdImpl();
        cartCmdImpl = new CartCmdImpl();
        new CartDTO();

        mockCartService = mock(CartService.class);
        mock(CartAdministrationService.class);
        mock(AutoTopUpConfigurationService.class);
        mock(SystemParameterService.class);
        mockWebAccountCreditRefundPaymentService = mock(WebAccountCreditRefundPaymentService.class);
        mockAdHocLoadRefundPaymentService = mock(AdHocLoadRefundPaymentService.class);
        mockBacsRefundPaymentService = mock(BacsRefundPaymentService.class);
        mockChequeRefundPaymentService = mock(ChequeRefundPaymentService.class);
        mockcartDataService = mock(CartDataService.class);
        mockApplicationContext = mock(ApplicationContext.class);
        mockCardDataService = mock(CardDataService.class);
        mockList = new ArrayList<CardDTO>();
        hotlistReason = new HotlistReason();

        service.cartDataService = mockcartDataService;
        service.cartService = mockCartService;
        service.cardDataService = mockCardDataService;
        service.webAccountCreditRefundPaymentService = mockWebAccountCreditRefundPaymentService;
        service.adHocLoadRefundPaymentService = mockAdHocLoadRefundPaymentService;
        service.bacsRefundPaymentService = mockBacsRefundPaymentService;
        service.chequeRefundPaymentService = mockChequeRefundPaymentService;
    }

    @Test
    public void paymentTypeShouldCallWebAccountCreditType() {
        when(mockCartService.findById(anyLong())).thenReturn(getTestCartDTO1());
        cartCmdImpl.setCartDTO(getNewCartDTOWithThreeProductItems());
        cartCmdImpl.setPaymentType(PaymentType.WEB_ACCOUNT_CREDIT.code());
        service.completeRefund(cartCmdImpl);
        verify(mockWebAccountCreditRefundPaymentService, atLeastOnce()).makePayment(any(CartCmdImpl.class));
    }

    @Test
    public void paymentTypeShouldCallAdHocLoadType() {
        when(mockCartService.findById(anyLong())).thenReturn(getTestCartDTO1());
        cartCmdImpl.setCartDTO(getNewCartDTOWithThreeProductItems());
        cartCmdImpl.setPaymentType(PaymentType.AD_HOC_LOAD.code());
        service.completeRefund(cartCmdImpl);
        verify(mockAdHocLoadRefundPaymentService, atLeastOnce()).makePayment(any(CartCmdImpl.class));
    }

    @Test
    public void paymentTypeShouldCallBacsType() {
        when(mockCartService.findById(anyLong())).thenReturn(getTestCartDTO1());
        cartCmdImpl.setCartDTO(getNewCartDTOWithThreeProductItems());
        cartCmdImpl.setPaymentType(PaymentType.BACS.code());
        service.completeRefund(cartCmdImpl);
        verify(mockBacsRefundPaymentService, atLeastOnce()).makePayment(any(CartCmdImpl.class));
    }

    @Test
    public void paymentTypeShouldCallChequeType() {
        when(mockCartService.findById(anyLong())).thenReturn(getTestCartDTO1());
        cartCmdImpl.setCartDTO(getNewCartDTOWithThreeProductItems());
        cartCmdImpl.setPaymentType(PaymentType.CHEQUE.code());
        service.completeRefund(cartCmdImpl);
        verify(mockChequeRefundPaymentService, atLeastOnce()).makePayment(any(CartCmdImpl.class));
    }

    @Test
    public void createCardsSelectListForAdHocLoadShouldReturnNonEmptySelectList() {
        ReflectionTestUtils.setField(this.service, "applicationContext", mockApplicationContext);
        cardDTO = getTestCardDTO1();
        hotlistReason.setId(HOTLIST_REASON_ID);
        cardDTO.setHotlistReason(hotlistReason);
        mockList.add(cardDTO);
        mockList.add(getTestCardDTO1());
        mockList.add(getTestCardDTO2());
        when(mockCardDataService.findByCustomerId(any(Long.class))).thenReturn(mockList);
        when(mockCardDataService.findByCardNumber(anyString())).thenReturn(getTestCardDTO1());
        when(mockCardDataService.getAllCardsFromUserExceptCurrent(anyString())).thenReturn(mockList);
        SelectListDTO result = service.createCardsSelectListForAdHocLoad(OYSTER_NUMBER_1);
        assertTrue(result instanceof SelectListDTO);
        assertFalse(result.getOptions().isEmpty());
    }

    @Test
    public void createCardsSelectListForAdHocLoadShouldReturnSelectListWithOtherCard() {
        when(mockCardDataService.findByCustomerId(any(Long.class))).thenReturn(mockList);
        when(mockCardDataService.findByCardNumber(anyString())).thenReturn(getTestCardDTO2());
        ReflectionTestUtils.setField(this.service, "applicationContext", mockApplicationContext);
        cardDTO = getTestCardDTO1();
        hotlistReason.setId(HOTLIST_REASON_ID);
        cardDTO.setHotlistReason(hotlistReason);
        mockList.add(cardDTO);
        assertTrue(service.createCardsSelectListForAdHocLoad(OYSTER_NUMBER_2) instanceof SelectListDTO);
    }

    @Test
    public void createCardsSelectListForAdHocLoadShouldReturnSelectListWithOtherCardAndNoHotlistReason() {
        when(mockCardDataService.findByCustomerId(any(Long.class))).thenReturn(mockList);
        when(mockCardDataService.findByCardNumber(anyString())).thenReturn(getTestCardDTO2());
        ReflectionTestUtils.setField(this.service, "applicationContext", mockApplicationContext);
        cardDTO = getTestCardDTO1();
        mockList.add(cardDTO);
        assertTrue(service.createCardsSelectListForAdHocLoad(OYSTER_NUMBER_2) instanceof SelectListDTO);
    }

    @Test
    public void createCardsSelectListForAdHocLoadShouldReturnSelectListWithNoCardNumber() {
        when(mockCardDataService.findByCustomerId(any(Long.class))).thenReturn(mockList);
        when(mockCardDataService.findByCardNumber(anyString())).thenReturn(getTestCardDTO1());

        ReflectionTestUtils.setField(this.service, "applicationContext", mockApplicationContext);
        assertTrue(service.createCardsSelectListForAdHocLoad(null) instanceof SelectListDTO);
    }

    @Test
    public void completeRefundShouldNotInvokeServicesForEmptyPaymentType() {
        when(mockCartService.findById(anyLong())).thenReturn(getTestCartDTO1());
        cartCmdImpl.setCartDTO(getNewCartDTOWithThreeProductItems());
        cartCmdImpl.setPaymentType(null);
        service.completeRefund(cartCmdImpl);
        verify(mockChequeRefundPaymentService, never()).makePayment(any(CartCmdImpl.class));
        verify(mockWebAccountCreditRefundPaymentService, never()).makePayment(any(CartCmdImpl.class));
        verify(mockAdHocLoadRefundPaymentService, never()).makePayment(any(CartCmdImpl.class));
        verify(mockBacsRefundPaymentService, never()).makePayment(any(CartCmdImpl.class));
    }
}
