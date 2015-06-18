package com.novacroft.nemo.tfl.services.application_service.impl;

import static com.novacroft.nemo.test_support.AgentAuthenticationProviderServiceImplTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.DateTestUtil.WEB_SERVICE_REQUEST_END_DATE;
import static com.novacroft.nemo.test_support.DateTestUtil.WEB_SERVICE_REQUEST_INVALID_LENGTH_DATE;
import static com.novacroft.nemo.test_support.DateTestUtil.WEB_SERVICE_REQUEST_START_DATE;
import static com.novacroft.nemo.test_support.OrderTestUtil.getOrders;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.test_support.CancelOrderTestUtil;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.test_support.SettlementTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CancelOrderService;
import com.novacroft.nemo.tfl.common.application_service.OrderService;
import com.novacroft.nemo.tfl.common.application_service.impl.CancelOrderServiceImpl;
import com.novacroft.nemo.tfl.common.constant.CancelOrderResult;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.SettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.CancelOrderResultDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.SettlementDTO;
import com.novacroft.nemo.tfl.services.converter.WebsServiceResultConverter;
import com.novacroft.nemo.tfl.services.converter.impl.ItemConverterImpl;
import com.novacroft.nemo.tfl.services.converter.impl.SettlementConverterImpl;
import com.novacroft.nemo.tfl.services.converter.impl.WebsServiceResultConverterImpl;
import com.novacroft.nemo.tfl.services.test_support.ErrorResultTestUtil;
import com.novacroft.nemo.tfl.services.test_support.OrdersTestUtil;
import com.novacroft.nemo.tfl.services.transfer.CustomerOrder;
import com.novacroft.nemo.tfl.services.transfer.Order;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;

public class OrderItemServiceImplTest {

    private OrderItemServiceImpl service;
    private OrderService mockOrderService;
    private ItemConverterImpl mockItemConverterImpl;
    private OrderItemServiceImpl mockService;
    private CancelOrderService mockCancelOrderService;
    private WebsServiceResultConverter mockWebServiceResultConverter;
    private OrderDataService mockOrderDataService;
    private SettlementDataService mockSettlementService;
    private CustomerDataService mockCustomerDataService;
    private CardDataService mockCardDataService;
    private SettlementConverterImpl mockSettlementConverter;
    @Mock
    List<SettlementDTO> settlementDTOList;


    @Before
    public void setUp() {
        service = new OrderItemServiceImpl();
        mockOrderService = mock(OrderService.class);
        mockItemConverterImpl = mock(ItemConverterImpl.class);
        mockCancelOrderService = mock(CancelOrderServiceImpl.class);
        mockWebServiceResultConverter = mock(WebsServiceResultConverterImpl.class);
        mockOrderDataService = mock(OrderDataService.class);
        mockSettlementService = mock(SettlementDataService.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        mockCardDataService = mock(CardDataService.class);
        mockSettlementConverter = mock(SettlementConverterImpl.class);
        service.orderService = mockOrderService;
        service.itemDTOConverter = mockItemConverterImpl;
        service.cancelOrderService = mockCancelOrderService;
        service.webServiceResultConverter = mockWebServiceResultConverter;
        service.orderDataService = mockOrderDataService;
        service.settlementDataService = mockSettlementService;
        service.customerDataService = mockCustomerDataService;
        service.cardDataService = mockCardDataService;
        service.settlementConverter = mockSettlementConverter;
        mockService = mock(OrderItemServiceImpl.class);
    }

    @Test
    public void shouldGetItemsByOrderAndCustomerId() {
        when(mockService.isDateValid(anyString())).thenReturn(true);
        when(mockCustomerDataService.findById(anyLong())).thenReturn(CustomerTestUtil.getTestCustomerDTO5());
        when(mockOrderService.findOrdersByCustomerIdAndDates(anyLong(), anyString(), anyString())).thenReturn(getOrders());
        settlementDTOList = SettlementTestUtil.getTestPolymorphicSettlemtDTOs();
        when(mockSettlementService.findPolymorphicChildTypeSettlementByOrderId(anyLong())).thenReturn(settlementDTOList);
        when(mockSettlementConverter.convertDTOs(any(List.class))).thenCallRealMethod();
        CustomerOrder orderList = service.getOrderItemsByCustomerIdAndDates(CUSTOMER_ID_1, WEB_SERVICE_REQUEST_START_DATE, WEB_SERVICE_REQUEST_END_DATE);
        assertNotNull(orderList);
        assertNotEquals(0, orderList.getOrders().size());
        verify(mockOrderService, atLeastOnce()).findOrdersByCustomerIdAndDates(anyLong(), anyString(), anyString());
    }
    
    @Test
    public void shouldNotGetItemsByOrderAndCustomerId() {
        when(mockService.isDateValid(anyString())).thenReturn(true);
        when(mockService.getContent(anyString())).thenReturn("DATE_GREATER_THAN_START_DATE");
        when(mockService.getOrderItemsByCustomerIdAndDates(anyLong(), anyString(), anyString())).thenCallRealMethod();
        when(mockCustomerDataService.findById(anyLong())).thenReturn(CustomerTestUtil.getTestCustomerDTO5());
        when(mockOrderService.findOrdersByCustomerIdAndDates(anyLong(), anyString(), anyString())).thenReturn(getOrders());
        settlementDTOList = SettlementTestUtil.getTestPolymorphicSettlemtDTOs();
        when(mockSettlementService.findPolymorphicChildTypeSettlementByOrderId(anyLong())).thenReturn(settlementDTOList);
        when(mockSettlementConverter.convertDTOs(any(List.class))).thenCallRealMethod();
        CustomerOrder orderList = mockService.getOrderItemsByCustomerIdAndDates(CUSTOMER_ID_1, WEB_SERVICE_REQUEST_END_DATE, WEB_SERVICE_REQUEST_START_DATE);
        assertNotNull(orderList);
        assertNotNull(orderList.getErrors());
        assertNotNull(orderList.getErrors().getErrors());
        verify(mockOrderService, never()).findOrdersByCustomerIdAndDates(anyLong(), anyString(), anyString());
    }

    @Test
    public void shouldNotGetItemsByOrderAndCustomerIdWhenEitherDateInvalid() {
        doCallRealMethod().when(mockService).getOrderItemsByCustomerIdAndDates(CUSTOMER_ID_1, WEB_SERVICE_REQUEST_START_DATE, WEB_SERVICE_REQUEST_INVALID_LENGTH_DATE);
        when(mockService.getContent(anyString())).thenReturn(StringUtil.EMPTY_STRING);
        CustomerOrder orderList = mockService.getOrderItemsByCustomerIdAndDates(CUSTOMER_ID_1, WEB_SERVICE_REQUEST_START_DATE, WEB_SERVICE_REQUEST_INVALID_LENGTH_DATE);
        assertNotNull(orderList);
        assertEquals(0, orderList.getOrders().size());
        verify(mockOrderService, never()).findOrdersByCustomerIdAndDates(anyLong(), anyString(), anyString());
    }

    @Test
    public void shouldNotGetItemsByOrderAndCustomerIdWhenEndDateBeforeStartDate() {
        doCallRealMethod().when(mockService).getOrderItemsByCustomerIdAndDates(CUSTOMER_ID_1, WEB_SERVICE_REQUEST_END_DATE, WEB_SERVICE_REQUEST_START_DATE);
        when(mockService.getContent(anyString())).thenReturn(StringUtil.EMPTY_STRING);
        CustomerOrder orderList = mockService.getOrderItemsByCustomerIdAndDates(CUSTOMER_ID_1, WEB_SERVICE_REQUEST_END_DATE, WEB_SERVICE_REQUEST_START_DATE);
        assertNotNull(orderList);
        assertEquals(0, orderList.getOrders().size());
        verify(mockOrderService, never()).findOrdersByCustomerIdAndDates(anyLong(), anyString(), anyString());
    }

    @Test
    public void getOrderByOrderNumberWithErrors() {
        service = mock(OrderItemServiceImpl.class);
        service.orderService = mockOrderService;
        service.orderDataService = mockOrderDataService;
        when(mockOrderDataService.findByExternalId(anyLong())).thenReturn(null);
        when(service.getContent(anyString())).thenReturn(ErrorResultTestUtil.ERROR_DESCRIPTION_1);
        when(service.getOrderByExternalOrderId(anyLong())).thenCallRealMethod().thenReturn(OrdersTestUtil.getTestOrderWithErrors());
        Order order = service.getOrderByExternalOrderId(OrderTestUtil.ORDER_NUMBER);
        assertNotNull(order);
        assertNotNull(order.getErrors());
    }

    @Test
    public void getOrderByOrderNumber() {
        when(mockOrderDataService.findByExternalId(anyLong())).thenReturn(OrderTestUtil.getTestOrderDTO1());
        when(mockCustomerDataService.findById(anyLong())).thenReturn(CustomerTestUtil.getTestCustomerDTO5());
        Order order = service.getOrderByExternalOrderId(OrderTestUtil.ORDER_NUMBER);
        assertNotNull(order);
        assertNull(order.getErrors());
    }

    @Test
    public void cancelOrder() {
        when(mockCancelOrderService.cancelOrderWithExternalOrderIdAndCustomerId(anyLong(), anyLong())).thenReturn(
                        CancelOrderTestUtil.getCancelOrderResultDTO1());
        when(mockWebServiceResultConverter.convertCancelOrderResultDTOToWebServiceResult(any(CancelOrderResultDTO.class))).thenCallRealMethod();
        WebServiceResult webServiceResult = service.cancelOrder(CustomerTestUtil.EXTERNAL_CUSTOMER_ID, OrderTestUtil.EXTERNAL_ID);

        assertNotNull(webServiceResult);
        assertEquals(CancelOrderResult.SUCCESS_AWAITING_REFUND_PAYMENT.name(), webServiceResult.getResult());
    }

    @Test
    public void completeCancelOrder() {
        when(mockCancelOrderService.completeCancelOrderWithExternalOrderIdAndCustomerId(anyLong(), anyLong())).thenReturn(
                        CancelOrderTestUtil.getCancelOrderResultDTO2());
        when(mockWebServiceResultConverter.convertCancelOrderResultDTOToWebServiceResult(any(CancelOrderResultDTO.class))).thenCallRealMethod();
        WebServiceResult webServiceResult = service.completeCancelOrder(CustomerTestUtil.EXTERNAL_CUSTOMER_ID, OrderTestUtil.EXTERNAL_ID);

        assertNotNull(webServiceResult);
        assertEquals(CancelOrderResult.SUCCESS.name(), webServiceResult.getResult());
    }
    
    @Test
    public void testGetOrderFromOrderDTO(){
        OrderDTO orderDTO = OrderTestUtil.getTestOrderDTO1();
        when(mockCustomerDataService.findById(anyLong())).thenReturn(CustomerTestUtil.getTestCustomerDTO5());
        Order order = service.getOrderFromOrderDTO(orderDTO);
        assertEquals(order.getCardId(), orderDTO.getCardId());
        assertEquals(order.getOrderNumber(), orderDTO.getOrderNumber());
        assertEquals(order.getOrderDate(), orderDTO.getOrderDate());
        assertEquals(order.getStatus(), orderDTO.getStatus());
    }
    
    @Test
    public void testGetExternalCardId(){
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        Long externalCardId = service.getExternalCardId(CardTestUtil.CARD_ID);
        assertNotNull(externalCardId);
    }
    
    @Test
    public void testGetExternalCardIdShouldReturnNull(){
        when(mockCardDataService.findById(anyLong())).thenReturn(null);
        Long externalCardId = service.getExternalCardId(CardTestUtil.CARD_ID);
        assertNull(externalCardId);
    }

}
