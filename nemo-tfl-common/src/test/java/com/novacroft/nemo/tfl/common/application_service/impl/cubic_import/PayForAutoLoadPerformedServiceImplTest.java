package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import static com.novacroft.nemo.common.constant.LocaleConstant.UNITED_KINGDOM_CURRENCY_CODE;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.STATION_ID_507;
import static com.novacroft.nemo.test_support.CustomerTestUtil.ID_1;
import static com.novacroft.nemo.test_support.OrderTestUtil.ORDER_NUMBER;
import static com.novacroft.nemo.test_support.OrderTestUtil.TOTAL_AMOUNT;
import static com.novacroft.nemo.test_support.PaymentGatewayTestUtil.AUTH_TIME_1;
import static com.novacroft.nemo.test_support.PaymentGatewayTestUtil.AUTH_TRANS_REF_NO_1;
import static com.novacroft.nemo.test_support.PaymentGatewayTestUtil.DECISION_ACCEPT;
import static com.novacroft.nemo.test_support.PaymentGatewayTestUtil.TRANSACTION_ID_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.ApplicationEventService;
import com.novacroft.nemo.common.application_service.UIDGenerator;
import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.application_service.PaymentService;
import com.novacroft.nemo.tfl.common.constant.AutoLoadState;
import com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourceDecision;
import com.novacroft.nemo.tfl.common.data_service.AutoTopUpDataService;
import com.novacroft.nemo.tfl.common.data_service.AutoTopUpItemDataService;
import com.novacroft.nemo.tfl.common.data_service.AutoTopUpPerformedSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.cyber_source.CyberSourceTransactionDataService;
import com.novacroft.nemo.tfl.common.data_service.impl.CustomerDataServiceImpl;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpPerformedSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceReplyDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapReplyDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapRequestDTO;

/**
 * Unit tests for PayForAutoLoadPerformedService
 */
public class PayForAutoLoadPerformedServiceImplTest {
    private static final String TEST_TOKEN = "test-token";
    private PayForAutoLoadPerformedServiceImpl service;
    private CardDataService mockCardDataService;
    private OrderDataService mockOrderDataService;
    private AutoTopUpDataService mockAutoTopUpDataService;
    private AutoTopUpItemDataService mockAutoTopUpItemDataService;
    private PaymentCardSettlementDataService mockPaymentCardSettlementDataService;
    private UIDGenerator mockUidGenerator;
    private CyberSourceTransactionDataService mockCyberSourceTransactionDataService;
    private PaymentService mockPaymentService;
    private ApplicationEventService mockApplicationEventService;
    private CustomerDataServiceImpl mockCustomerDataService;
    private AutoTopUpPerformedSettlementDataService mockAutoTopUpPerformedSettlementDataService;

    private CardDTO mockCardDTO;
    private OrderDTO mockOrderDTO;
    private AutoTopUpConfigurationItemDTO mockAutoTopUpItemDTO;
    private AutoTopUpDTO mockAutoTopUpDTO;
    private PaymentCardSettlementDTO mockPaymentCardSettlementDTO;
    private AutoTopUpPerformedSettlementDTO mockAutoTopUpPerformedSettlementDTO;
    private CyberSourceSoapRequestDTO mockCyberSourceSoapRequestDTO;
    private CyberSourceSoapReplyDTO mockCyberSourceSoapReplyDTO;
    private CustomerDTO mockCustomerDTO;

    @Before
    public void setUp() {
        this.service = mock(PayForAutoLoadPerformedServiceImpl.class);
        this.mockCardDataService = mock(CardDataService.class);
        this.service.cardDataService = this.mockCardDataService;
        this.mockOrderDataService = mock(OrderDataService.class);
        this.service.orderDataService = this.mockOrderDataService;
        this.mockAutoTopUpDataService = mock(AutoTopUpDataService.class);
        this.service.autoTopUpDataService = this.mockAutoTopUpDataService;
        this.mockAutoTopUpItemDataService = mock(AutoTopUpItemDataService.class);
        this.service.autoTopUpItemDataService = this.mockAutoTopUpItemDataService;
        this.mockPaymentCardSettlementDataService = mock(PaymentCardSettlementDataService.class);
        this.service.paymentCardSettlementDataService = this.mockPaymentCardSettlementDataService;
        this.mockUidGenerator = mock(UIDGenerator.class);
        this.service.uidGenerator = this.mockUidGenerator;
        this.mockCyberSourceTransactionDataService = mock(CyberSourceTransactionDataService.class);
        this.service.cyberSourceTransactionDataService = this.mockCyberSourceTransactionDataService;
        this.mockPaymentService = mock(PaymentService.class);
        this.service.paymentService = this.mockPaymentService;
        this.mockApplicationEventService = mock(ApplicationEventService.class);
        this.service.applicationEventService = mockApplicationEventService;
        this.mockCustomerDataService = mock(CustomerDataServiceImpl.class);
        this.service.customerDataService = mockCustomerDataService;
        mockAutoTopUpPerformedSettlementDataService = mock(AutoTopUpPerformedSettlementDataService.class);
        service.autoTopUpPerformedSettlementDataService = mockAutoTopUpPerformedSettlementDataService;

        this.mockCardDTO = mock(CardDTO.class);
        this.mockOrderDTO = mock(OrderDTO.class);
        mockOrderDTO.setCustomerId(ID_1);
        mockAutoTopUpPerformedSettlementDTO = mock(AutoTopUpPerformedSettlementDTO.class);
        this.mockAutoTopUpItemDTO = mock(AutoTopUpConfigurationItemDTO.class);
        this.mockAutoTopUpDTO = mock(AutoTopUpDTO.class);
        this.mockPaymentCardSettlementDTO = mock(PaymentCardSettlementDTO.class);
        this.mockCyberSourceSoapRequestDTO = mock(CyberSourceSoapRequestDTO.class);
        this.mockCyberSourceSoapReplyDTO = mock(CyberSourceSoapReplyDTO.class);
        this.mockCustomerDTO = mock(CustomerDTO.class);
    }

    @Test
    public void isExistingCardShouldReturnTrue() {
        when(this.service.isExistingCard(anyString())).thenCallRealMethod();
        when(this.mockCardDataService.findByCardNumber(anyString())).thenReturn(this.mockCardDTO);
        assertTrue(this.service.isExistingCard(OYSTER_NUMBER_1));
    }

    @Test
    public void isExistingCardShouldReturnFalse() {
        when(this.service.isExistingCard(anyString())).thenCallRealMethod();
        when(this.mockCardDataService.findByCardNumber(anyString())).thenReturn(null);
        assertFalse(this.service.isExistingCard(OYSTER_NUMBER_1));
    }

    @Test
    public void payForAutoLoadPerformed() {
        doCallRealMethod().when(this.service).payForAutoLoadPerformed(anyString(), anyInt(), anyInt(), anyString());
        when(this.mockCardDataService.findByCardNumber(anyString())).thenReturn(this.mockCardDTO);
        when(this.service.createOrder(any(CardDTO.class), anyInt())).thenReturn(this.mockOrderDTO);
        when(this.service.createAutoTopUpItemDTO(any(OrderDTO.class), any(CardDTO.class), anyInt())).thenReturn(this.mockAutoTopUpItemDTO);
        when(this.service.createSettlementForOrder(any(OrderDTO.class), anyInt()))
                .thenReturn(this.mockPaymentCardSettlementDTO);
        when(this.service
                .preparePaymentRequest(any(OrderDTO.class), any(PaymentCardSettlementDTO.class), anyString(), anyInt()))
                .thenReturn(this.mockCyberSourceSoapRequestDTO);
        when(this.service.requestPayment(any(CyberSourceSoapRequestDTO.class))).thenReturn(this.mockCyberSourceSoapReplyDTO);
        when(this.service.updateSettlementWithPaymentGatewayResponse(any(PaymentCardSettlementDTO.class),
                any(CyberSourceSoapReplyDTO.class))).thenReturn(this.mockPaymentCardSettlementDTO);
        when(this.service.updateOrderWithPaymentGatewayResponse(any(OrderDTO.class), any(CyberSourceReplyDTO.class)))
                .thenReturn(this.mockOrderDTO);
        doNothing().when(this.service)
                .createApplicationEvent(any(CardDTO.class), any(OrderDTO.class), any(CyberSourceSoapReplyDTO.class));

        this.service.payForAutoLoadPerformed(OYSTER_NUMBER_1, STATION_ID_507, AutoLoadState.TOP_UP_AMOUNT_2.topUpAmount(),
                UNITED_KINGDOM_CURRENCY_CODE);

        verify(this.mockCardDataService).findByCardNumber(anyString());
        verify(this.service).createOrder(any(CardDTO.class), anyInt());
        verify(this.service).createSettlementForOrder(any(OrderDTO.class), anyInt());
        verify(this.service)
                .preparePaymentRequest(any(OrderDTO.class), any(PaymentCardSettlementDTO.class), anyString(), anyInt());
        verify(this.service).requestPayment(any(CyberSourceSoapRequestDTO.class));
        verify(this.service).updateSettlementWithPaymentGatewayResponse(any(PaymentCardSettlementDTO.class),
                any(CyberSourceSoapReplyDTO.class));
        verify(this.service).updateOrderWithPaymentGatewayResponse(any(OrderDTO.class), any(CyberSourceReplyDTO.class));
        verify(this.service)
                .createApplicationEvent(any(CardDTO.class), any(OrderDTO.class), any(CyberSourceSoapReplyDTO.class));
    }

    @Test
    public void shouldCreateOrder() {
        when(this.service.createOrder(any(CardDTO.class), anyInt())).thenCallRealMethod();
        when(this.mockOrderDataService.create(any(OrderDTO.class))).thenReturn(this.mockOrderDTO);

        this.service.createOrder(this.mockCardDTO, AutoLoadState.TOP_UP_AMOUNT_2.topUpAmount());

        verify(this.mockOrderDataService).create(any(OrderDTO.class));
    }

    @Test
    public void shouldCreateAutoTopUpItemDTO() {
        when(this.service.createAutoTopUpItemDTO(any(OrderDTO.class), any(CardDTO.class), anyInt())).thenCallRealMethod();
        when(this.mockAutoTopUpDataService.findByAutoTopUpAmount(anyInt())).thenReturn(mockAutoTopUpDTO);

        this.service.createAutoTopUpItemDTO(mockOrderDTO, mockCardDTO, AutoLoadState.TOP_UP_AMOUNT_2.topUpAmount());

        verify(this.mockAutoTopUpDataService).findByAutoTopUpAmount(anyInt());
    }

    @Test(expected = ApplicationServiceException.class)
    public void shouldNotCreateAutoTopUpItemDTOShouldThrowExceptionWhenAutoTopUpDataServiceReturnsNull() {
        when(this.service.createAutoTopUpItemDTO(any(OrderDTO.class), any(CardDTO.class), anyInt())).thenCallRealMethod();
        when(mockAutoTopUpDataService.findByAutoTopUpAmount(anyInt())).thenReturn(null);
        service.createAutoTopUpItemDTO(mockOrderDTO, mockCardDTO, AutoLoadState.TOP_UP_AMOUNT_2.topUpAmount());
        verify(mockAutoTopUpDataService, atLeastOnce()).findByAutoTopUpAmount(anyInt());
    }

    @Test
    public void shouldAddItemToOrder() {
        when(this.service.addItemToOrder(any(OrderDTO.class), any(CardDTO.class), anyInt())).thenCallRealMethod();
        when(this.mockAutoTopUpDataService.findByAutoTopUpAmount(anyInt())).thenReturn(this.mockAutoTopUpDTO);
        when(this.mockAutoTopUpItemDataService.createOrUpdate(any(AutoTopUpConfigurationItemDTO.class)))
                .thenReturn(this.mockAutoTopUpItemDTO);

        this.service.addItemToOrder(this.mockOrderDTO, this.mockCardDTO, AutoLoadState.TOP_UP_AMOUNT_2.topUpAmount());

        verify(this.mockAutoTopUpDataService).findByAutoTopUpAmount(anyInt());
        verify(this.mockAutoTopUpItemDataService).createOrUpdate(any(AutoTopUpConfigurationItemDTO.class));
    }

    @Test(expected = ApplicationServiceException.class)
    public void shouldNotAddItemToOrderWithNullAutotopup() {
        when(this.service.addItemToOrder(any(OrderDTO.class), any(CardDTO.class), anyInt())).thenCallRealMethod();
        when(this.mockAutoTopUpDataService.findByAutoTopUpAmount(anyInt())).thenReturn(null);
        this.service.addItemToOrder(this.mockOrderDTO, this.mockCardDTO, AutoLoadState.TOP_UP_AMOUNT_2.topUpAmount());
    }

    @Test
    public void shouldCreateSettlementForOrder() {
        when(this.service.createSettlementForOrder(any(OrderDTO.class), anyInt())).thenCallRealMethod();
        when(this.mockUidGenerator.getIdAsString()).thenReturn(TEST_TOKEN);
        when(this.mockPaymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class)))
                .thenReturn(this.mockPaymentCardSettlementDTO);

        this.service.createSettlementForOrder(this.mockOrderDTO, AutoLoadState.TOP_UP_AMOUNT_2.topUpAmount());

        verify(this.mockUidGenerator).getIdAsString();
        verify(this.mockPaymentCardSettlementDataService).createOrUpdate(any(PaymentCardSettlementDTO.class));
    }

    @Test
    public void shouldPreparePaymentRequest() {
        when(this.service
                .preparePaymentRequest(any(OrderDTO.class), any(PaymentCardSettlementDTO.class), anyString(), anyInt()))
                .thenCallRealMethod();
        
        when(this.service.requestPayment(any(CyberSourceSoapRequestDTO.class))).thenCallRealMethod();
        when(this.mockOrderDTO.getOrderNumber()).thenReturn(ORDER_NUMBER);
        when(this.mockPaymentCardSettlementDTO.getTransactionUuid()).thenReturn(TEST_TOKEN);
        when(this.mockCustomerDataService.findById(mockOrderDTO.getCustomerId())).thenReturn(this.mockCustomerDTO);
        doReturn(5L).when(mockCustomerDTO).getId();
        doReturn(ORDER_NUMBER).when(mockOrderDTO).getOrderNumber();

        CyberSourceSoapRequestDTO result = this.service
                .preparePaymentRequest(this.mockOrderDTO, this.mockPaymentCardSettlementDTO, UNITED_KINGDOM_CURRENCY_CODE,
                        TOTAL_AMOUNT);

        assertEquals(String.valueOf(ORDER_NUMBER), result.getOrderNumber());
        assertEquals(TEST_TOKEN, result.getTransactionUuid());
        assertEquals(UNITED_KINGDOM_CURRENCY_CODE, result.getCurrency());
        assertEquals(TOTAL_AMOUNT, result.getTotalAmountInPence());
    }

    @Test
    public void shouldRequestPayment() {
        when(this.service.requestPayment(any(CyberSourceSoapRequestDTO.class))).thenCallRealMethod();
        when(this.mockCyberSourceTransactionDataService.runTransaction(any(CyberSourceSoapRequestDTO.class)))
                .thenReturn(this.mockCyberSourceSoapReplyDTO);

        this.service.requestPayment(this.mockCyberSourceSoapRequestDTO);

        verify(this.mockCyberSourceTransactionDataService).runTransaction(any(CyberSourceSoapRequestDTO.class));
    }

    @Test
    public void shouldUpdateSettlementWithPaymentGatewayResponse() {
        when(this.service.updateSettlementWithPaymentGatewayResponse(any(PaymentCardSettlementDTO.class),
                any(CyberSourceSoapReplyDTO.class))).thenCallRealMethod();
        when(this.mockPaymentService.updatePaymentCardSettlementWithPaymentGatewayResponse(any(PaymentCardSettlementDTO.class),
                any(CyberSourceReplyDTO.class))).thenReturn(this.mockPaymentCardSettlementDTO);

        this.service.updateSettlementWithPaymentGatewayResponse(this.mockPaymentCardSettlementDTO,
                this.mockCyberSourceSoapReplyDTO);

        verify(this.mockPaymentService)
                .updatePaymentCardSettlementWithPaymentGatewayResponse(any(PaymentCardSettlementDTO.class),
                        any(CyberSourceReplyDTO.class));
    }

    @Test
    public void shouldBuildAdditionalInformation() {
        when(this.service
                .buildAdditionalInformation(any(CardDTO.class), any(OrderDTO.class), any(CyberSourceSoapReplyDTO.class)))
                .thenCallRealMethod();
        when(this.mockCardDTO.getCardNumber()).thenReturn(OYSTER_NUMBER_1);
        when(this.mockOrderDTO.getOrderNumber()).thenReturn(ORDER_NUMBER);
        when(this.mockCyberSourceSoapReplyDTO.getDecision()).thenReturn(DECISION_ACCEPT);
        when(this.mockCyberSourceSoapReplyDTO.getTransactionId()).thenReturn(TRANSACTION_ID_1);
        when(this.mockCyberSourceSoapReplyDTO.getTransactionReference()).thenReturn(AUTH_TRANS_REF_NO_1);
        when(this.mockCyberSourceSoapReplyDTO.getAuthorizedAt()).thenReturn(AUTH_TIME_1);

        String expectedResult =
                "Card Number [100000000001]; Order Number [999990]; Transaction Result [ACCEPT]; Transaction ID [9994]; " +
                        "Authorisation Reference [9991]; Authorised At [2013-08-20T120000Z]";
        String actualResult =
                this.service.buildAdditionalInformation(this.mockCardDTO, this.mockOrderDTO, this.mockCyberSourceSoapReplyDTO);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldCreateApplicationEvent() {
        doCallRealMethod().when(this.service)
                .createApplicationEvent(any(CardDTO.class), any(OrderDTO.class), any(CyberSourceSoapReplyDTO.class));
        when(this.service
                .buildAdditionalInformation(any(CardDTO.class), any(OrderDTO.class), any(CyberSourceSoapReplyDTO.class)))
                .thenReturn("");
        doNothing().when(this.mockApplicationEventService).create(anyLong(), any(EventName.class), anyString());
        when(this.mockCyberSourceSoapReplyDTO.getDecision()).thenReturn(CyberSourceDecision.ACCEPT.code());

        this.service.createApplicationEvent(this.mockCardDTO, this.mockOrderDTO, this.mockCyberSourceSoapReplyDTO);

        verify(this.service)
                .buildAdditionalInformation(any(CardDTO.class), any(OrderDTO.class), any(CyberSourceSoapReplyDTO.class));
        verify(this.mockApplicationEventService).create(anyLong(), any(EventName.class), anyString());
    }

    @Test
    public void shouldUpdateOrderWithPaymentGatewayResponse() {
        when(this.service.updateOrderWithPaymentGatewayResponse(any(OrderDTO.class), any(CyberSourceReplyDTO.class)))
                .thenCallRealMethod();
        when(this.mockPaymentService
                .updateOrderStatusWithPaymentGatewayResponse(any(OrderDTO.class), any(CyberSourceReplyDTO.class)))
                .thenReturn(this.mockOrderDTO);

        this.service.updateOrderWithPaymentGatewayResponse(this.mockOrderDTO, this.mockCyberSourceSoapReplyDTO);

        verify(this.mockPaymentService)
                .updateOrderStatusWithPaymentGatewayResponse(any(OrderDTO.class), any(CyberSourceReplyDTO.class));
    }
    
    @Test
    public void shouldCreateAutoTopUpPerformedSettlementForOrder() {
        doCallRealMethod().when(this.service).createAutoTopUpPerformedSettlementForOrder(any(OrderDTO.class), anyInt(), any(PaymentCardSettlementDTO.class));
        when(this.mockAutoTopUpPerformedSettlementDataService.createOrUpdate(any(AutoTopUpPerformedSettlementDTO.class))).thenReturn(mockAutoTopUpPerformedSettlementDTO);

        this.service.createAutoTopUpPerformedSettlementForOrder(this.mockOrderDTO, AutoLoadState.TOP_UP_AMOUNT_2.topUpAmount(), this.mockPaymentCardSettlementDTO);

        verify(this.mockAutoTopUpPerformedSettlementDataService).createOrUpdate(any(AutoTopUpPerformedSettlementDTO.class));        
    }
}
