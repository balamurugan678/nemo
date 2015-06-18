package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO1;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.TOTAL_AMT_1;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.getTestCartCmd1;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.getTestCartCmd5;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestCartItems6;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.FORMATED_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO3;
import static com.novacroft.nemo.test_support.CyberSourceSoapReplyTestUtil.AUTHORIZED_AMOUNT_1;
import static com.novacroft.nemo.test_support.OrderTestUtil.ORDER_ID;
import static com.novacroft.nemo.test_support.OrderTestUtil.ORDER_NUMBER;
import static com.novacroft.nemo.test_support.OrderTestUtil.PAYMENT_CARD_AMOUNT;
import static com.novacroft.nemo.test_support.OrderTestUtil.PAYMENT_CARD_AMOUNT_100;
import static com.novacroft.nemo.test_support.OrderTestUtil.STATION_ID;
import static com.novacroft.nemo.test_support.OrderTestUtil.WEB_CREDIT_AMOUNT;
import static com.novacroft.nemo.test_support.OrderTestUtil.getTestOrderDTO1;
import static com.novacroft.nemo.test_support.PaymentCardTestUtil.TEST_PAYMENT_CARD_ID_1;
import static com.novacroft.nemo.test_support.PaymentGatewayTestUtil.AUTH_TIME_1;
import static com.novacroft.nemo.test_support.PaymentGatewayTestUtil.AUTH_TRANS_REF_NO_1;
import static com.novacroft.nemo.test_support.PaymentGatewayTestUtil.DECISION_ACCEPT;
import static com.novacroft.nemo.test_support.PaymentGatewayTestUtil.REQ_ID;
import static com.novacroft.nemo.test_support.PaymentGatewayTestUtil.TRANSACTION_ID_1;
import static com.novacroft.nemo.test_support.PaymentGatewayTestUtil.getTestAcceptReply;
import static com.novacroft.nemo.test_support.PaymentGatewayTestUtil.getTestAcceptReplyWithEmptyRequestId;
import static com.novacroft.nemo.test_support.PaymentGatewayTestUtil.getTestAcceptReplyWithRequestId;
import static com.novacroft.nemo.test_support.PaymentGatewayTestUtil.getTestRejectReply;
import static com.novacroft.nemo.test_support.SettlementTestUtil.getTestPaymentCardSettlementDTO1;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.ApplicationEventService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.application_service.UIDGenerator;
import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartCmdTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.CyberSourceSoapReplyTestUtil;
import com.novacroft.nemo.test_support.LocationTestUtil;
import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.test_support.SettlementTestUtil;
import com.novacroft.nemo.tfl.common.application_service.AutoTopUpConfigurationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.PayAsYouGoService;
import com.novacroft.nemo.tfl.common.application_service.PaymentCardService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.application_service.WebCreditService;
import com.novacroft.nemo.tfl.common.application_service.fulfilment.FulfilmentQueuePopulationService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.AutoTopUpConfirmationEmailService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourceDecision;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.WebCreditSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.WebCreditSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceReplyDTO;

/**
 * PaymentService unit tests
 */
@SuppressWarnings("deprecation")
public class PaymentServiceTest {
    private PaymentServiceImpl mockService;
    private PaymentServiceImpl service;
    private PaymentCardService mockPaymentCardService;
    private PaymentCardSettlementDataService mockPaymentCardSettlementDataService;
    private CustomerDataService mockCustomerDataService;
    private AddressDataService mockAddressDataService;
    private SystemParameterService mockSystemParameterService;
    private CardDataService mockCardDataService;
    private BaseEmailPreparationService mockBaseEmailPreparationService;
    private AutoTopUpConfirmationEmailService mockAutoTopUpConfirmationEmailService;
    private UIDGenerator mockUidGenerator;
    private ApplicationEventService mockApplicationEventService;
    private CartService mockCartService;
    private WebCreditSettlementDataService mockWebAccountCreditSettlementDataService;
    private WebCreditService mockWebCreditService;
    private AutoTopUpConfigurationService mockAutoTopUpConfigurationService;
    private FulfilmentQueuePopulationService mockFulfilmentQueuePopulationService;
    private CustomerService mockCustomerService;
    
    private CartDTO cartDTO;
    private CartCmdImpl mockCartCmdImpl;
    private OrderDTO mockOrderDTO;
    private PaymentCardSettlementDTO mockPaymentCardSettlementDTO;
    private CyberSourceReplyDTO mockCyberSourceReplyDTO;
    private OrderDataService mockOrderDataService;
    private PayAsYouGoService mockPayAsYouGoService;
    private TravelCardService mockTravelCardService;
    private final String TRAVEL_CARD_PURCHSE = "Travel Card Purchase; ";
    private final String PAYASYOUGO_CARD_PURCHSE = "Pay As You Go Purchase; ";

    @Before
    public void setUp() {
        this.mockService = mock(PaymentServiceImpl.class);
        this.service = new PaymentServiceImpl();
        this.mockPaymentCardService = mock(PaymentCardService.class);
        this.mockService.paymentCardService = this.mockPaymentCardService;
        this.mockPaymentCardSettlementDataService = mock(PaymentCardSettlementDataService.class);
        this.mockService.paymentCardSettlementDataService = this.mockPaymentCardSettlementDataService;
        this.mockOrderDataService = mock(OrderDataService.class);
        this.mockService.orderDataService = this.mockOrderDataService;
        this.mockCustomerDataService = mock(CustomerDataService.class);
        this.mockService.customerDataService = this.mockCustomerDataService;
        this.mockAddressDataService = mock(AddressDataService.class);
        this.mockService.addressDataService = this.mockAddressDataService;
        this.mockSystemParameterService = mock(SystemParameterService.class);
        this.mockService.systemParameterService = this.mockSystemParameterService;
        this.mockCardDataService = mock(CardDataService.class);
        this.mockService.cardDataService = this.mockCardDataService;
        this.mockBaseEmailPreparationService = mock(BaseEmailPreparationService.class);
        this.mockService.baseEmailPreparationService = this.mockBaseEmailPreparationService;
        this.mockAutoTopUpConfirmationEmailService = mock(AutoTopUpConfirmationEmailService.class);
        this.mockService.autoTopUpConfirmationEmailService = this.mockAutoTopUpConfirmationEmailService;
        this.mockUidGenerator = mock(UIDGenerator.class);
        this.mockApplicationEventService = mock(ApplicationEventService.class);
        this.mockCartService = mock(CartService.class);
        this.mockWebAccountCreditSettlementDataService = mock(WebCreditSettlementDataService.class);
        this.mockPayAsYouGoService = mock(PayAsYouGoService.class);
        this.mockTravelCardService = mock(TravelCardService.class);
        this.mockService.travelCardService = mockTravelCardService;
        this.mockWebCreditService = mock(WebCreditService.class);
        this.mockAutoTopUpConfigurationService = mock(AutoTopUpConfigurationServiceImpl.class);
        this.mockFulfilmentQueuePopulationService=mock(FulfilmentQueuePopulationService.class);
        this.mockService.fulfilmentQueuePopulationService=this.mockFulfilmentQueuePopulationService;
        this.mockCustomerService = mock(CustomerService.class);
        this.mockService.customerService = mockCustomerService;
        
        this.cartDTO = new CartDTO();
        this.mockCartCmdImpl = mock(CartCmdImpl.class);
        this.mockOrderDTO = mock(OrderDTO.class);
        this.mockPaymentCardSettlementDTO = mock(PaymentCardSettlementDTO.class);
        this.mockCyberSourceReplyDTO = mock(CyberSourceReplyDTO.class);

        this.service.orderDataService = this.mockOrderDataService;
        this.service.paymentCardSettlementDataService = this.mockPaymentCardSettlementDataService;
        this.service.paymentCardService = this.mockPaymentCardService;
        this.service.customerDataService = this.mockCustomerDataService;
        this.service.addressDataService = this.mockAddressDataService;
        this.service.baseEmailPreparationService = this.mockBaseEmailPreparationService;
        this.service.autoTopUpConfirmationEmailService = this.mockAutoTopUpConfirmationEmailService;
        this.service.uidGenerator = this.mockUidGenerator;
        this.service.cardDataService = this.mockCardDataService;
        this.service.applicationEventService = mockApplicationEventService;
        this.service.cartService = this.mockCartService;
        this.service.webAccountCreditSettlementDataService = this.mockWebAccountCreditSettlementDataService;
        this.service.payAsYouGoService = mockPayAsYouGoService;
        this.service.travelCardService = mockTravelCardService;
        this.service.webCreditService = mockWebCreditService;
        this.service.autoTopUpConfigurationService = mockAutoTopUpConfigurationService;
        this.service.fulfilmentQueuePopulationService=mockFulfilmentQueuePopulationService;
        this.service.customerService = mockCustomerService;
    }

    @Test
    public void shouldBuildAdditionalInformation() {
        when(this.mockService.buildAdditionalInformation(any(CartCmdImpl.class))).thenCallRealMethod();
        when(this.mockService.isPaymentCardSettlementUsed(anyInt())).thenReturn(true);
        when(this.mockTravelCardService.isTravelCard(any(ItemDTO.class))).thenReturn(true);
        CartCmdImpl cmd = getTestCartCmd1();
        cartDTO.setCyberSourceReply(getTestAcceptReply());
        cartDTO.setOrder(getTestOrderDTO1());
        cartDTO.addCartItem(CartTestUtil.getNewProductItemDTO());
        cmd.setCartDTO(cartDTO);
        String expectedResult = String
                        .format(" "
                                        + TRAVEL_CARD_PURCHSE
                                        + "Order Number [%s]; Mode Of Payment [Payment Card]; amount [%s]; Transaction Result [%s]; Authorised At [%s]; Transaction ID [%s]; Authorisation Reference"
                                        + " [%s]", ORDER_NUMBER, PAYMENT_CARD_AMOUNT, DECISION_ACCEPT, AUTH_TIME_1, TRANSACTION_ID_1, AUTH_TRANS_REF_NO_1);
        assertEquals(expectedResult, this.mockService.buildAdditionalInformation(cmd));
    }
    
    @Test
    public void shouldBuildAdditionalInformationForPaymentWithSavedPaymentCard() {
        when(this.mockService.buildAdditionalInformation(any(CartCmdImpl.class))).thenCallRealMethod();
        when(this.mockService.isPaymentCardSettlementUsed(anyInt())).thenReturn(true);
        when(this.mockTravelCardService.isTravelCard(any(ItemDTO.class))).thenReturn(true);
        CartCmdImpl cmd = getTestCartCmd1();
        cartDTO.setCyberSourceReply(getTestAcceptReplyWithRequestId());
        cartDTO.setOrder(getTestOrderDTO1());
        cartDTO.addCartItem(CartTestUtil.getNewProductItemDTO());
        cmd.setCartDTO(cartDTO);
        String expectedResult = String
                        .format(" "
                                        + TRAVEL_CARD_PURCHSE
                                        + "Order Number [%s]; Mode Of Payment [Payment Card]; amount [%s]; Transaction Result [%s]; Authorised At [%s]; Request Id"
                                        + " [%s]", ORDER_NUMBER, PAYMENT_CARD_AMOUNT, DECISION_ACCEPT, AUTH_TIME_1, REQ_ID);
        assertEquals(expectedResult, this.mockService.buildAdditionalInformation(cmd));
    }
    
    @Test
    public void shouldBuildAdditionalInformationForPaymentWithNoTranscationIdAndNoRequestId() {
        when(this.mockService.buildAdditionalInformation(any(CartCmdImpl.class))).thenCallRealMethod();
        when(this.mockService.isPaymentCardSettlementUsed(anyInt())).thenReturn(true);
        when(this.mockTravelCardService.isTravelCard(any(ItemDTO.class))).thenReturn(true);
        CartCmdImpl cmd = getTestCartCmd1();
        cartDTO.setCyberSourceReply(getTestAcceptReplyWithEmptyRequestId());
        cartDTO.setOrder(getTestOrderDTO1());
        cartDTO.addCartItem(CartTestUtil.getNewProductItemDTO());
        cmd.setCartDTO(cartDTO);
        String expectedResult = String
                        .format(" "
                                        + TRAVEL_CARD_PURCHSE
                                        + "Order Number [%s]; Mode Of Payment [Payment Card]; amount [%s]; Transaction Result [%s]; Authorised At"
                                        + " [%s]", ORDER_NUMBER, PAYMENT_CARD_AMOUNT, DECISION_ACCEPT, AUTH_TIME_1);
        assertEquals(expectedResult, this.mockService.buildAdditionalInformation(cmd));
    }
    
    @Test
    public void shouldBuildAdditionalInformationForPayAsYouGo() {
        when(this.mockService.buildAdditionalInformation(any(CartCmdImpl.class))).thenCallRealMethod();
        when(this.mockService.isPaymentCardSettlementUsed(anyInt())).thenReturn(true);
        CartCmdImpl cmd = getTestCartCmd1();
        cartDTO.setCyberSourceReply(getTestAcceptReply());
        cartDTO.setOrder(getTestOrderDTO1());
        cartDTO.addCartItem(CartTestUtil.getPayAsYouGoItemDTO());
        cmd.setCartDTO(cartDTO);
        String result = this.mockService.buildAdditionalInformation(cmd);
        assertTrue(result.contains(PAYASYOUGO_CARD_PURCHSE));
        assertTrue(result.contains(ORDER_NUMBER.toString()));
        assertTrue(result.contains(TRANSACTION_ID_1));
    }

    @Test
    public void shouldBuildAdditionalInformationForPayAsYouGoWithWebCredit() {
        when(this.mockService.buildAdditionalInformation(any(CartCmdImpl.class))).thenCallRealMethod();
        when(this.mockService.isPaymentCardSettlementUsed(anyInt())).thenReturn(false);
        when(this.mockService.isWebCreditSettlementUsed(anyInt())).thenReturn(true);
        CartCmdImpl cmd = getTestCartCmd1();
        cartDTO.setCyberSourceReply(getTestAcceptReply());
        cartDTO.setOrder(getTestOrderDTO1());
        cartDTO.addCartItem(CartTestUtil.getPayAsYouGoItemDTO());
        cmd.setCartDTO(cartDTO);
        String expectedResult = String
                        .format(PAYASYOUGO_CARD_PURCHSE
                                        + "Order Number [%s]; Mode Of Payment [Web Credit]; amount [%s]", ORDER_NUMBER, WEB_CREDIT_AMOUNT);
        assertEquals(expectedResult, this.mockService.buildAdditionalInformation(cmd));
    }

    @Test
    public void shouldBuildAdditionalInformationForPayAsYouGoWithBothPaymentCardAndWebCredit() {
        when(this.mockService.buildAdditionalInformation(any(CartCmdImpl.class))).thenCallRealMethod();
        when(this.mockService.isPaymentCardSettlementUsed(anyInt())).thenReturn(true);
        when(this.mockService.isWebCreditSettlementUsed(anyInt())).thenReturn(true);
        CartCmdImpl cmd = getTestCartCmd1();
        cartDTO.setCyberSourceReply(getTestAcceptReply());
        cartDTO.setOrder(getTestOrderDTO1());
        cartDTO.addCartItem(CartTestUtil.getPayAsYouGoItemDTO());
        cmd.setCartDTO(cartDTO);
        String result = this.mockService.buildAdditionalInformation(cmd);
        assertTrue(result.contains(PAYASYOUGO_CARD_PURCHSE));
        assertTrue(result.contains(ORDER_NUMBER.toString()));
        assertTrue(result.contains(TRANSACTION_ID_1));
    }

    @Test
    public void resolveEventNameShouldReturnAccepted() {
        PaymentServiceImpl service = new PaymentServiceImpl();
        assertEquals(EventName.PAYMENT_RESOLVED, service.resolveEventName(CyberSourceDecision.ACCEPT.code()));
    }

    @Test
    public void resolveEventNameShouldReturnCancelled() {
        PaymentServiceImpl service = new PaymentServiceImpl();
        assertEquals(EventName.PAYMENT_CANCELLED, service.resolveEventName(CyberSourceDecision.CANCEL.code()));
    }

    @Test
    public void resolveEventNameShouldReturnIncomplete() {
        PaymentServiceImpl service = new PaymentServiceImpl();
        assertEquals(EventName.PAYMENT_INCOMPLETE, service.resolveEventName(CyberSourceDecision.ERROR.code()));
    }

    @Test(expected = ApplicationServiceException.class)
    public void resolveEventNameShouldError() {
        PaymentServiceImpl service = new PaymentServiceImpl();
        service.resolveEventName("Rubbish!");
    }

    @Test
    public void shouldCreateEvent() {
        CartCmdImpl cmd = getTestCartCmd1();
        cartDTO.setCyberSourceReply(getTestAcceptReply());
        cartDTO.setOrder(getTestOrderDTO1());
        cmd.setCartDTO(cartDTO);

        PaymentServiceImpl service = new PaymentServiceImpl();

        ApplicationEventService mockApplicationEventService = mock(ApplicationEventService.class);
        doNothing().when(mockApplicationEventService).create(anyLong(), any(EventName.class), anyString());

        service.applicationEventService = mockApplicationEventService;

        service.createEvent(cmd);

        verify(mockApplicationEventService).create(anyLong(), any(EventName.class), anyString());
    }

    @Test
    public void updateSettlementsShouldUpdatePaymentCardSettlement() {
        CartCmdImpl cmd = getTestCartCmd1();
        cmd.setToPayAmount(TOTAL_AMT_1);
        cartDTO.setCyberSourceReply(getTestAcceptReply());
        cartDTO.setOrder(getTestOrderDTO1());
        cartDTO.setPaymentCardSettlement(new PaymentCardSettlementDTO());
        cmd.setCartDTO(cartDTO);

        when(this.mockPaymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenReturn(getTestPaymentCardSettlementDTO1());

        PaymentCardSettlementDTO settlementDTO = this.service.updateSettlements(cmd);

        verify(this.mockPaymentCardSettlementDataService).createOrUpdate(any(PaymentCardSettlementDTO.class));
        assertNotNull(settlementDTO);
    }

    @Test
    public void updateSettlementsShouldUpdateWebCreditSettlement() {
        CartCmdImpl cmd = getTestCartCmd1();
        cmd.setWebCreditApplyAmount(TOTAL_AMT_1);
        cartDTO.setCyberSourceReply(getTestAcceptReply());
        cartDTO.setOrder(getTestOrderDTO1());
        cartDTO.setPaymentCardSettlement(new PaymentCardSettlementDTO());
        cmd.setCartDTO(cartDTO);
        WebCreditSettlementDTO settlement = new WebCreditSettlementDTO();
        WebCreditSettlementDTO[] settlementList = new WebCreditSettlementDTO[] { settlement };

        when(this.mockWebAccountCreditSettlementDataService.findByOrderId(anyLong())).thenReturn(Arrays.asList(settlementList));
        when(this.mockWebAccountCreditSettlementDataService.createOrUpdate(any(WebCreditSettlementDTO.class))).thenReturn(null);

        PaymentCardSettlementDTO settlementDTO = this.service.updateSettlements(cmd);

        verify(this.mockWebAccountCreditSettlementDataService).createOrUpdate(any(WebCreditSettlementDTO.class));
        verify(this.mockWebAccountCreditSettlementDataService, atLeastOnce()).findByOrderId(anyLong());
        assertNotNull(settlementDTO);
    }

    @Test
    public void shouldUpdateOrderStatus() {
        CartCmdImpl cmd = getTestCartCmd1();
        cartDTO.setCyberSourceReply(getTestAcceptReply());
        cartDTO.setOrder(getTestOrderDTO1());
        cartDTO.getOrder().setStatus(OrderStatus.PAID.code());
        cartDTO.setPaymentCardSettlement(getTestPaymentCardSettlementDTO1());
        cmd.setCartDTO(cartDTO);
        when(this.mockOrderDataService.findById(anyLong())).thenReturn(cmd.getCartDTO().getOrder());
        when(this.mockOrderDataService.createOrUpdate(any(OrderDTO.class))).thenReturn(cmd.getCartDTO().getOrder());

        doCallRealMethod().when(this.mockService).updateOrderStatusAndItems(any(CartDTO.class), any(OrderDTO.class));
        doCallRealMethod().when(this.mockService).updateOrderRecord(any(OrderDTO.class));
        doCallRealMethod().when(this.mockService).moveCartItemsToOrder(any(CartDTO.class), any(OrderDTO.class));

        this.mockService.updateOrderStatusAndItems(cmd.getCartDTO(), cmd.getCartDTO().getOrder());
        verify(this.mockOrderDataService,times(2)).createOrUpdate(cmd.getCartDTO().getOrder());
    }

    @Test
    public void shouldUpdateOrderStatusForPaidStatus() {
        CartCmdImpl cmd = getTestCartCmd1();
        cartDTO = CartTestUtil.getNewCartDTOWithThreeProductItems();
        cartDTO.setCyberSourceReply(getTestAcceptReply());
        cartDTO.setOrder(getTestOrderDTO1());
        cartDTO.getOrder().setStatus(OrderStatus.PAID.code());
        cartDTO.setPaymentCardSettlement(getTestPaymentCardSettlementDTO1());
        cmd.setCartDTO(cartDTO);
        when(this.mockOrderDataService.findById(anyLong())).thenReturn(cmd.getCartDTO().getOrder());
        when(this.mockOrderDataService.createOrUpdate(any(OrderDTO.class))).thenReturn(cmd.getCartDTO().getOrder());

        doCallRealMethod().when(this.mockService).updateOrderStatusAndItems(any(CartDTO.class), any(OrderDTO.class));
        doCallRealMethod().when(this.mockService).updateOrderRecord(any(OrderDTO.class));
        when(this.mockService.moveCartItemsToOrder(any(CartDTO.class), any(OrderDTO.class))).thenReturn(cmd.getCartDTO().getOrder());

        this.service.updateOrderStatusAndItems(cartDTO, cmd.getCartDTO().getOrder());
        verify(this.mockOrderDataService,times(2)).createOrUpdate(cmd.getCartDTO().getOrder());
    }

    @Test
    public void resolveNextSettlementStatusShouldReturnAccepted() {
        PaymentServiceImpl service = new PaymentServiceImpl();
        assertEquals(SettlementStatus.ACCEPTED.code(), service.resolveNextPaymentCardSettlementStatus(CyberSourceDecision.ACCEPT.code()));
    }

    @Test
    public void resolveNextSettlementStatusShouldReturnCancelled() {
        PaymentServiceImpl service = new PaymentServiceImpl();
        assertEquals(SettlementStatus.CANCELLED.code(), service.resolveNextPaymentCardSettlementStatus(CyberSourceDecision.CANCEL.code()));
    }

    @Test
    public void resolveNextSettlementStatusShouldReturnIncomplete() {
        PaymentServiceImpl service = new PaymentServiceImpl();
        assertEquals(SettlementStatus.INCOMPLETE.code(), service.resolveNextPaymentCardSettlementStatus(CyberSourceDecision.ERROR.code()));
    }

    @Test(expected = ApplicationServiceException.class)
    public void resolveNextSettlementStatusShouldError() {
        PaymentServiceImpl service = new PaymentServiceImpl();
        service.resolveNextPaymentCardSettlementStatus("Rubbish!");
    }

    @Test
    public void resolveNextOrderStatusShouldReturnAccepted() {
        PaymentServiceImpl service = new PaymentServiceImpl();
        assertEquals(OrderStatus.PAID.code(), service.resolveNextOrderStatus(CyberSourceDecision.ACCEPT.code()));
    }

    @Test
    public void resolveNextOrderStatusShouldReturnCancelled() {
        PaymentServiceImpl service = new PaymentServiceImpl();
        assertEquals(OrderStatus.CANCELLED.code(), service.resolveNextOrderStatus(CyberSourceDecision.CANCEL.code()));
    }

    @Test
    public void resolveNextOrderStatusShouldReturnIncomplete() {
        PaymentServiceImpl service = new PaymentServiceImpl();
        assertEquals(OrderStatus.ERROR.code(), service.resolveNextOrderStatus(CyberSourceDecision.ERROR.code()));
    }

    @Test(expected = ApplicationServiceException.class)
    public void resolveNextOrderStatusShouldError() {
        this.service.resolveNextOrderStatus("Rubbish!");
    }

    @Test
    public void shouldCreatePaymentCardSettlementForOrder() {
        when(this.mockPaymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenReturn(getTestPaymentCardSettlementDTO1());

        UIDGenerator mockUIDGenerator = mock(UIDGenerator.class);
        when(mockUIDGenerator.getIdAsString()).thenReturn("test-UID");

        PaymentServiceImpl service = new PaymentServiceImpl();
        service.paymentCardSettlementDataService = this.mockPaymentCardSettlementDataService;
        service.uidGenerator = mockUIDGenerator;

        service.createPaymentCardSettlementForOrder(ORDER_ID, TOTAL_AMT_1);

        verify(this.mockPaymentCardSettlementDataService).createOrUpdate(any(PaymentCardSettlementDTO.class));
        verify(mockUIDGenerator).getIdAsString();
    }

    @Test
    public void shouldCreateOrderFromCart() {
        when(this.mockOrderDataService.create(any(OrderDTO.class))).thenReturn(getTestOrderDTO1());
        when(this.mockService.createOrderAndSettlementsFromCart(any(CartDTO.class), any(CartCmdImpl.class))).thenCallRealMethod();
        when(this.mockService.createOrderFromCart(anyLong(), anyInt(), anyLong())).thenCallRealMethod();
        when(this.mockService.createPaymentCardSettlementForOrder(anyLong(), anyInt())).thenReturn(this.mockPaymentCardSettlementDTO);

        this.mockService.createOrderAndSettlementsFromCart(cartDTO, getTestCartCmd1());
        verify(this.mockOrderDataService).create(any(OrderDTO.class));
    }

    @Test
    public void shouldPopulatePaymentDetails() {
        when(this.mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(getTestCustomerDTO3());
        when(this.mockCustomerDataService.findById(CUSTOMER_ID_1)).thenReturn(getTestCustomerDTO1());
        when(this.mockAddressDataService.findById(anyLong())).thenReturn(getTestAddressDTO1());
        when(this.mockSystemParameterService.getParameterValue(anyString())).thenReturn("Elbonia");
        when(this.mockCustomerDataService.findById(anyLong())).thenReturn(getTestCustomerDTO3());

        PaymentServiceImpl service = new PaymentServiceImpl();
        service.customerDataService = this.mockCustomerDataService;
        service.addressDataService = this.mockAddressDataService;
        service.systemParameterService = this.mockSystemParameterService;

        service.populatePaymentDetails(CUSTOMER_ID_1);

        verify(this.mockCustomerDataService).findById(CUSTOMER_ID_1);
        verify(this.mockAddressDataService).findById(anyLong());
    }

    @Test
    public void shouldCreateOrderAndSettlementsFromCart() {
        when(this.mockService.createOrderAndSettlementsFromCart(any(CartDTO.class), any(CartCmdImpl.class))).thenCallRealMethod();
        when(this.mockService.createOrderFromCart(anyLong(), anyInt(), anyLong())).thenReturn(this.mockOrderDTO);
        when(this.mockService.createPaymentCardSettlementForOrder(anyLong(), anyInt())).thenReturn(this.mockPaymentCardSettlementDTO);
        doNothing().when(this.mockService).processWebCreditApplyAmount(anyLong(), anyInt());
        doNothing().when(this.mockService).handleAutoTopUpChange(any(CartCmdImpl.class), any(CartDTO.class));

        this.mockService.createOrderAndSettlementsFromCart(cartDTO, this.mockCartCmdImpl);

        verify(this.mockService).createOrderAndSettlementsFromCart(any(CartDTO.class), any(CartCmdImpl.class));
        verify(this.mockService).createOrderFromCart(anyLong(), anyInt(), anyLong());
        verify(this.mockService).createPaymentCardSettlementForOrder(anyLong(), anyInt());
        verify(this.mockService).processWebCreditApplyAmount(anyLong(), anyInt());
        verify(this.mockService).handleAutoTopUpChange(any(CartCmdImpl.class), any(CartDTO.class));
    }


    @Test
    public void processPaymentGatewayReplyForNewOrderDeprecated() {
        CartCmdImpl cmd = getTestCartCmd1();
        cartDTO.setOrder(getTestOrderDTO1());
        cartDTO.getOrder().setStatus(OrderStatus.NEW.code());
        cmd.setCartDTO(cartDTO);
        when(this.mockService.processPaymentGatewayReply(any(CartDTO.class), any(CartCmdImpl.class))).thenCallRealMethod();
        when(this.mockService.setOrderStatusFromCybersourceReply(any(CartCmdImpl.class), any(OrderDTO.class)))
                        .thenReturn(cmd.getCartDTO().getOrder());
        when(this.mockService.updateOrderStatusAndItems(any(CartDTO.class), any(OrderDTO.class))).thenReturn(cmd.getCartDTO().getOrder());
        when(this.mockService.updateSettlements(any(CartCmdImpl.class))).thenReturn(cmd.getCartDTO().getPaymentCardSettlement());
        doNothing().when(this.mockService).createEvent(any(CartCmdImpl.class));
        doNothing().when(this.mockPaymentCardService).createPaymentCardOnTokenRequest(any(CartCmdImpl.class));
        doNothing().when(this.mockPaymentCardService).linkPaymentCardToCardOnAutoLoadOrder(any(OrderDTO.class));
        this.mockService.processPaymentGatewayReply(cartDTO, cmd);

        verify(this.mockService).updateSettlements(cmd);

    }

    @Test
    public void processPaymentGatewayReplyForNewOrder() {
        CartCmdImpl cmd = getTestCartCmd1();
        cartDTO.setOrder(getTestOrderDTO1());
        cartDTO.getOrder().setStatus(OrderStatus.NEW.code());
        cmd.setCartDTO(cartDTO);
        when(this.mockService.processPaymentGatewayReply(any(CartCmdImpl.class))).thenCallRealMethod();
        when(this.mockService.setOrderStatusFromCybersourceReply(any(CartCmdImpl.class), any(OrderDTO.class))).thenReturn(cmd.getCartDTO().getOrder());
        when(this.mockService.updateOrderStatusAndItems(any(CartDTO.class), any(OrderDTO.class))).thenReturn(cmd.getCartDTO().getOrder());
        when(this.mockService.updateSettlements(any(CartCmdImpl.class))).thenReturn(cmd.getCartDTO().getPaymentCardSettlement());
        doNothing().when(this.mockService).createEvent(any(CartCmdImpl.class));
        doNothing().when(this.mockPaymentCardService).createPaymentCardOnTokenRequest(any(CartCmdImpl.class));
        doNothing().when(this.mockPaymentCardService).linkPaymentCardToCardOnAutoLoadOrder(any(OrderDTO.class));
        this.mockService.processPaymentGatewayReply(cmd);

        verify(this.mockService).updateSettlements(cmd);

    }

    @Test
    public void processPaymentGatewayReplyForPaidNewOrderDeprecated() {
        CartCmdImpl cmd = getTestCartCmd1();
        cartDTO.setOrder(getTestOrderDTO1());
        cartDTO.getOrder().setStatus(OrderStatus.PAID.code());
        cmd.setCartDTO(cartDTO);
        when(this.mockService.processPaymentGatewayReply(any(CartDTO.class), any(CartCmdImpl.class))).thenCallRealMethod();
        when(this.mockService.setOrderStatusFromCybersourceReply(any(CartCmdImpl.class), any(OrderDTO.class)))
                        .thenReturn(cmd.getCartDTO().getOrder());
        when(this.mockService.updateOrderStatusAndItems(any(CartDTO.class), any(OrderDTO.class))).thenReturn(cmd.getCartDTO().getOrder());
        when(this.mockService.updateSettlements(any(CartCmdImpl.class))).thenReturn(cmd.getCartDTO().getPaymentCardSettlement());
        when(this.mockOrderDataService.createOrUpdate(any(OrderDTO.class))).thenReturn(cmd.getCartDTO().getOrder());
        doNothing().when(this.mockApplicationEventService).create(anyLong(), any(EventName.class), anyString());
        doNothing().when(this.mockCartService).deleteCart((anyLong()));

        this.service.processPaymentGatewayReply(cartDTO, cmd);
        verify(this.mockOrderDataService).createOrUpdate(any(OrderDTO.class));
    }

    @Test
    public void processPaymentGatewayReplyForPaidNewOrder() {
        CartCmdImpl cmd = getTestCartCmd1();
        cartDTO.setOrder(getTestOrderDTO1());
        cartDTO.getOrder().setStatus(OrderStatus.PAID.code());
        cmd.setCartDTO(cartDTO);
        when(this.mockService.processPaymentGatewayReply(any(CartCmdImpl.class))).thenCallRealMethod();
        when(this.mockService.setOrderStatusFromCybersourceReply(any(CartCmdImpl.class), any(OrderDTO.class))).thenReturn(cmd.getCartDTO().getOrder());
        when(this.mockService.updateOrderStatusAndItems(any(CartDTO.class), any(OrderDTO.class))).thenReturn(cmd.getCartDTO().getOrder());
        when(this.mockService.updateSettlements(any(CartCmdImpl.class))).thenReturn(cmd.getCartDTO().getPaymentCardSettlement());
        when(this.mockOrderDataService.createOrUpdate(any(OrderDTO.class))).thenReturn(cmd.getCartDTO().getOrder());
        doNothing().when(this.mockApplicationEventService).create(anyLong(), any(EventName.class), anyString());
        doNothing().when(this.mockCartService).deleteCart((anyLong()));

        this.service.processPaymentGatewayReply(cmd);
        verify(this.mockOrderDataService).createOrUpdate(any(OrderDTO.class));
    }

    @Test
    public void shouldNotUpdateCartItemsToCubicForNewOrderWithErrorStatus() {
        CartCmdImpl cmd = getTestCartCmd1();
        cartDTO.setOrder(getTestOrderDTO1());
        cartDTO.getOrder().setStatus(OrderStatus.ERROR.code());
        cmd.setStationId(null);
        cmd.setCartDTO(cartDTO);
        this.service.pushCartItemsToCubic(cmd);
        verify(this.mockPayAsYouGoService, never()).updatePrePayValueToCubic(any(CartCmdImpl.class));
        verify(this.mockTravelCardService, never()).addPrePayTicketToCubic(any(CartCmdImpl.class));

    }

    @Test
    public void shouldNotUpdateCartItemsToCubicForNewOrderWithPaidStatus() {
        CartCmdImpl cmd = getTestCartCmd1();
        cartDTO.setOrder(getTestOrderDTO1());
        cartDTO.getOrder().setStatus(OrderStatus.PAID.code());
        cmd.setStationId(null);
        cmd.setCartDTO(cartDTO);
        this.service.pushCartItemsToCubic(cmd);
        verify(this.mockPayAsYouGoService, never()).updatePrePayValueToCubic(any(CartCmdImpl.class));
        verify(this.mockTravelCardService, never()).addPrePayTicketToCubic(any(CartCmdImpl.class));

    }

    @Test
    public void shouldNotUpdateCartItemsToCubicForExistingCardWithErrorStatus() {
        CartCmdImpl cmd = getTestCartCmd1();
        cartDTO.setOrder(getTestOrderDTO1());
        cartDTO.getOrder().setStatus(OrderStatus.ERROR.code());
        cmd.setStationId(LocationTestUtil.LOCATION_ID_1);
        cmd.setCartDTO(cartDTO);
        this.service.pushCartItemsToCubic(cmd);
        verify(this.mockPayAsYouGoService, never()).updatePrePayValueToCubic(any(CartCmdImpl.class));
        verify(this.mockTravelCardService, never()).addPrePayTicketToCubic(any(CartCmdImpl.class));

    }

    @Test
    public void shouldUpdateCartItemsToCubicForExistingCardWithPaidStatus() {
        CartCmdImpl cmd = getTestCartCmd1();
        cartDTO.setOrder(getTestOrderDTO1());
        cartDTO.getOrder().setStatus(OrderStatus.PAID.code());
        cmd.setStationId(LocationTestUtil.LOCATION_ID_1);
        cmd.setCartDTO(cartDTO);
        cmd.setCardNumber(CardTestUtil.OYSTER_NUMBER_1);
        when(this.mockPayAsYouGoService.updatePrePayValueToCubic(any(CartCmdImpl.class))).thenReturn(false);
        when(this.mockTravelCardService.addPrePayTicketToCubic(any(CartCmdImpl.class))).thenReturn(false);
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());

        this.service.pushCartItemsToCubic(cmd);
        verify(this.mockPayAsYouGoService, times(1)).updatePrePayValueToCubic(any(CartCmdImpl.class));
        verify(this.mockTravelCardService, times(1)).addPrePayTicketToCubic(any(CartCmdImpl.class));

    }

    @Test(expected = ApplicationServiceException.class)
    public void shouldNotUpdateCartItemsToCubicDueToPrePayValueFailure() {
        CartCmdImpl cmd = getTestCartCmd1();
        cartDTO.setOrder(getTestOrderDTO1());
        cartDTO.getOrder().setStatus(OrderStatus.PAID.code());
        cmd.setStationId(LocationTestUtil.LOCATION_ID_1);
        cmd.setCartDTO(cartDTO);
        cmd.setCardNumber(CardTestUtil.OYSTER_NUMBER_1);
        when(this.mockPayAsYouGoService.updatePrePayValueToCubic(any(CartCmdImpl.class))).thenReturn(true);
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());

        this.service.pushCartItemsToCubic(cmd);
        verify(this.mockPayAsYouGoService, times(1)).updatePrePayValueToCubic(any(CartCmdImpl.class));
        verify(this.mockTravelCardService, never()).addPrePayTicketToCubic(any(CartCmdImpl.class));

    }

    @Test(expected = ApplicationServiceException.class)
    public void shouldNotUpdateCartItemsToCubicDueToPrePayIicketFailure() {
        CartCmdImpl cmd = getTestCartCmd1();
        cartDTO.setOrder(getTestOrderDTO1());
        cartDTO.getOrder().setStatus(OrderStatus.PAID.code());
        cmd.setStationId(LocationTestUtil.LOCATION_ID_1);
        cmd.setCartDTO(cartDTO);
        cmd.setCardNumber(CardTestUtil.OYSTER_NUMBER_1);
        when(this.mockPayAsYouGoService.updatePrePayValueToCubic(any(CartCmdImpl.class))).thenReturn(false);
        when(this.mockTravelCardService.addPrePayTicketToCubic(any(CartCmdImpl.class))).thenReturn(true);
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());

        this.service.pushCartItemsToCubic(cmd);
        verify(this.mockPayAsYouGoService, times(1)).updatePrePayValueToCubic(any(CartCmdImpl.class));
        verify(this.mockTravelCardService, times(1)).addPrePayTicketToCubic(any(CartCmdImpl.class));

    }

    @Test
    public void updatePaymentCardSettlementWithPaymentGatewayResponse() {
        when(this.mockService.updatePaymentCardSettlementWithPaymentGatewayResponse(any(PaymentCardSettlementDTO.class), any(CyberSourceReplyDTO.class))).thenCallRealMethod();
        when(this.mockService.resolveNextPaymentCardSettlementStatus(anyString())).thenReturn(EMPTY_STRING);
        when(this.mockPaymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenReturn(this.mockPaymentCardSettlementDTO);
        when(this.mockCyberSourceReplyDTO.getTransactionAmount()).thenReturn(AUTHORIZED_AMOUNT_1);

        this.mockService.updatePaymentCardSettlementWithPaymentGatewayResponse(this.mockPaymentCardSettlementDTO, this.mockCyberSourceReplyDTO);

        verify(this.mockService).resolveNextPaymentCardSettlementStatus(anyString());
        verify(this.mockPaymentCardSettlementDataService).createOrUpdate(any(PaymentCardSettlementDTO.class));
    }

    @Test
    public void updateOrderStatusWithPaymentGatewayResponseShouldReturnOrder() {
        OrderDTO order = getTestOrderDTO1();
        when(this.mockCyberSourceReplyDTO.getDecision()).thenReturn(CyberSourceDecision.ACCEPT.code());

        OrderDTO resultOrder = this.service.updateOrderStatusWithPaymentGatewayResponse(order, this.mockCyberSourceReplyDTO);

        assertEquals(OrderStatus.PAID.code(), resultOrder.getStatus());

    }

    @Test
    public void createOrderAndSettlementsFromManagedAutoTopUpShouldReturnCartCmdImpl() {

        CartCmdImpl cmd = getTestCartCmd1();
        when(this.mockOrderDataService.create(any(OrderDTO.class))).thenReturn(getTestOrderDTO1());
        when(this.mockPaymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenReturn(getTestPaymentCardSettlementDTO1());
        when(this.mockOrderDataService.createOrUpdate(any(OrderDTO.class))).thenReturn(getTestOrderDTO1());
        when(this.mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(this.mockCustomerDataService.findById(any(Long.class))).thenReturn(getTestCustomerDTO1());
        when(this.mockBaseEmailPreparationService.getSalutation(any(CustomerDTO.class))).thenReturn(FORMATED_NAME_1);
        doNothing().when(this.mockAutoTopUpConfirmationEmailService).sendConfirmationMessage(any(EmailArgumentsDTO.class));
        when(this.mockUidGenerator.getIdAsString()).thenReturn(EMPTY_STRING);

        CartCmdImpl resultCart = this.service.createOrderAndSettlementsFromManagedAutoTopUp(cartDTO, cmd);

        verify(this.mockOrderDataService, atLeastOnce()).create(any(OrderDTO.class));
        verify(this.mockOrderDataService, atLeastOnce()).createOrUpdate(any(OrderDTO.class));
        verify(this.mockCardDataService, atLeastOnce()).findById(anyLong());
        verify(this.mockCustomerDataService, atLeastOnce()).findById(any(Long.class));
        verify(this.mockBaseEmailPreparationService, atLeastOnce()).getSalutation(any(CustomerDTO.class));
        verify(this.mockAutoTopUpConfirmationEmailService, atLeastOnce()).sendConfirmationMessage(any(EmailArgumentsDTO.class));
        assertNotNull(resultCart);
        assertNotNull(resultCart.getCartDTO().getOrder());
    }

    @Test
    public void getDefaultPaymentCardIdShouldReturnPaymentCardId() {
        CartCmdImpl cmd = getTestCartCmd1();
        CardDTO cardDTO = CardTestUtil.getTestCardDTO1();
        cardDTO.setPaymentCardId(CardTestUtil.CARD_ID);
        when(this.mockCardDataService.findById(anyLong())).thenReturn(cardDTO);

        Long paymentCardId = this.service.getDefaultPaymentCardId(cmd);
        verify(this.mockCardDataService, atLeastOnce()).findById(anyLong());
        assertEquals(CardTestUtil.CARD_ID, paymentCardId);
    }

    @Test
    public void getDefaultPaymentCardIdForNullCardIdShouldReturnPaymentCardIdFromCartItem() {
        CartCmdImpl cmd = getTestCartCmd5();
        CardDTO cardDTO = CardTestUtil.getTestCardDTO1();
        cardDTO.setPaymentCardId(CardTestUtil.CARD_ID);
        cmd.setCardId(null);
        when(this.mockCardDataService.findById(anyLong())).thenReturn(cardDTO);

        Long paymentCardId = this.service.getDefaultPaymentCardId(cmd);
        verify(this.mockCardDataService, atLeastOnce()).findById(anyLong());
        assertEquals(CardTestUtil.CARD_ID, paymentCardId);
    }

    @Test
    public void getDefaultPaymentCardIdShouldReturnNULLForNullCardId() {
        CartCmdImpl cmd = getTestCartCmd1();
        cmd.setCardId(null);
        CardDTO cardDTO = CardTestUtil.getTestCardDTO1();
        cardDTO.setPaymentCardId(CardTestUtil.CARD_ID);
        cmd.setCartDTO(cartDTO);
        Long paymentCardId = this.service.getDefaultPaymentCardId(cmd);
        verify(this.mockCardDataService, never()).findById(anyLong());
        assertEquals(null, paymentCardId);
    }

    @Test
    public void resolveNextWebCreditSettlementStatusShouldReturnAccepted() {
        CartCmdImpl cmd = getTestCartCmd1();
        cmd.setToPayAmount(TOTAL_AMT_1);
        cartDTO.setCyberSourceReply(CyberSourceSoapReplyTestUtil.getTestCyberSourceSoapReplyDTO1());
        cmd.setCartDTO(cartDTO);
        when(this.mockCyberSourceReplyDTO.getDecision()).thenReturn(CyberSourceDecision.ACCEPT.code());
        assertEquals(SettlementStatus.COMPLETE.code(), this.service.resolveNextWebCreditSettlementStatus(cmd));
    }

    @Test
    public void processWebCreditApplyAmountShouldCallWebCreditService() {
        this.service.processWebCreditApplyAmount(ORDER_ID, TOTAL_AMT_1);
        verify(this.mockWebCreditService).applyWebCreditToOrder(anyLong(), any(Integer.class));
    }

    @Test
    public void handleAutoTopUpChangeShouldCallAutoTopUpConfigurationService() {
        CartCmdImpl cmd = getTestCartCmd1();
        cmd.setAutoTopUpAmount(TOTAL_AMT_1);
        cmd.setStationId(CartCmdTestUtil.STATION_500);
        cartDTO.setOrder(getTestOrderDTO1());
        this.service.handleAutoTopUpChange(cmd, cartDTO);
        verify(this.mockAutoTopUpConfigurationService).changeConfiguration(anyLong(), anyLong(), any(Integer.class), anyLong());
    }

    @Test
    public void processPaymentGatewayReplyNewOrder() {
        CartCmdImpl cmd = getTestCartCmd1();
        cartDTO.setOrder(getTestOrderDTO1());
        cartDTO.getOrder().setStatus(OrderStatus.NEW.code());
        cmd.setCartDTO(cartDTO);
        when(this.mockService.processPaymentGatewayReply(any(CartCmdImpl.class))).thenCallRealMethod();
        when(this.mockService.setOrderStatusFromCybersourceReply(any(CartCmdImpl.class), any(OrderDTO.class)))
                        .thenReturn(cmd.getCartDTO().getOrder());
        when(this.mockService.updateOrderStatusAndItems(any(CartDTO.class), any(OrderDTO.class))).thenReturn(cmd.getCartDTO().getOrder());
        when(this.mockService.updateSettlements(any(CartCmdImpl.class))).thenReturn(cmd.getCartDTO().getPaymentCardSettlement());
        doNothing().when(this.mockService).createEvent(any(CartCmdImpl.class));
        doNothing().when(this.mockPaymentCardService).createPaymentCardOnTokenRequest(any(CartCmdImpl.class));
        doNothing().when(this.mockPaymentCardService).linkPaymentCardToCardOnAutoLoadOrder(any(OrderDTO.class));
        this.mockService.processPaymentGatewayReply(cmd);

        verify(this.mockService).updateSettlements(cmd);

    }
    
    
    @Test
    public void testCreatePaymentCardSettlementForAutoTopUpOrder() {
        when(mockPaymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenReturn(SettlementTestUtil.getTestPaymentCardSettlementDTO1());
        PaymentCardSettlementDTO paymentCardSettlementDTO = service.createPaymentCardSettlementForAutoTopUpOrder(OrderTestUtil.ORDER_ID, TOTAL_AMT_1, 1L);
        assertNotNull(paymentCardSettlementDTO);
        verify(mockPaymentCardSettlementDataService).createOrUpdate(any(PaymentCardSettlementDTO.class));
    }
    
    @Test
    public void testCreatePaymentCardSettlementForOrderReturnsNull() {
        PaymentCardSettlementDTO paymentCardSettlementDTO = service.createPaymentCardSettlementForOrder(OrderTestUtil.ORDER_ID, 0);
        assertNull(paymentCardSettlementDTO);
        verify(mockPaymentCardSettlementDataService, never()).createOrUpdate(any(PaymentCardSettlementDTO.class));
    }
    
    @Test
    public void isNotExistingStation(){
        assertFalse(service.isExisitingStation(null));
    }
    
    @Test
    public void isNotExisitingCard(){
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTOWithoutCardNumber());
        assertFalse(service.isExistingOysterCard(CardTestUtil.CARD_ID));
    }

}
