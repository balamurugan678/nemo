package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CustomerTestUtil.CS_CUSTOMERID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.DateTestUtil.WEB_SERVICE_REQUEST_END_DATE;
import static com.novacroft.nemo.test_support.DateTestUtil.WEB_SERVICE_REQUEST_START_DATE;
import static com.novacroft.nemo.test_support.OrderTestUtil.CUSTOMER_ID_2;
import static com.novacroft.nemo.test_support.OrderTestUtil.ORDER_NUMBER;
import static com.novacroft.nemo.test_support.OrderTestUtil.getItemDTO1;
import static com.novacroft.nemo.test_support.OrderTestUtil.getItemDTO2;
import static com.novacroft.nemo.test_support.OrderTestUtil.getOrders;
import static com.novacroft.nemo.test_support.OrderTestUtil.getOrdersWithPaid;
import static com.novacroft.nemo.test_support.OrderTestUtil.getTestOrderDTO1;
import static com.novacroft.nemo.test_support.OrderTestUtil.getTestOrderDTO2;
import static com.novacroft.nemo.test_support.SettlementTestUtil.getTestAdHocLoadSettlementDTO1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.getTestPaymentCardSettlementDTO1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.getTestWebCreditSettlementDTO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.action.ItemDTOActionDelegate;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.AdHocLoadSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.WebCreditSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderItemsDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.WebCreditSettlementDTO;

public class OrderServiceImplTest {

    private OrderServiceImpl orderService;
    private OrderDataService mockOrderDataService;
    private CustomerDataService mockCustomerDataService;
    private PaymentCardSettlementDataService mockCardSettlementDataService;
    private WebCreditSettlementDataService mockWebCreditSettlementDataService;
    private AdHocLoadSettlementDataService mockAdhocLoadSettlementDataService;
    private ItemDTOActionDelegate mockItemDTOActionDelegate;
    private CardDataService mockCardDataService;

    private OrderDTO orderDTO;
    private List<OrderDTO> orderDTOList;
    private List<ItemDTO> updatedItems;
    private List<AdHocLoadSettlementDTO> adHocSettlements;

    @Before
    public void setUp() {
        orderService = new OrderServiceImpl();
        mockOrderDataService = mock(OrderDataService.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        mockCardSettlementDataService = mock(PaymentCardSettlementDataService.class);
        mockWebCreditSettlementDataService = mock(WebCreditSettlementDataService.class);
        mockItemDTOActionDelegate = mock(ItemDTOActionDelegate.class);
        mockAdhocLoadSettlementDataService= mock(AdHocLoadSettlementDataService.class);
        mockCardDataService = mock(CardDataService.class);

        orderDTO = getTestOrderDTO1();
        orderDTO.getOrderItems().add(getItemDTO1());
        orderDTO.getOrderItems().add(getItemDTO2());
        updatedItems = new ArrayList<ItemDTO>();
        ItemDTO itemDTO = getItemDTO1();
        itemDTO.setName("Item 1 Name Updated");
        updatedItems.add(itemDTO);
        itemDTO = getItemDTO2();
        itemDTO.setName("Item 2 Name Updated");
        updatedItems.add(itemDTO);

        orderService.orderDataService = mockOrderDataService;
        orderService.cardSettlementDataService = mockCardSettlementDataService;
        orderService.customerDataService = mockCustomerDataService;
        orderService.webCreditSettlementDataService = mockWebCreditSettlementDataService;
        orderService.adhocLoadSettlementDataService = mockAdhocLoadSettlementDataService;
        orderService.itemDTOActionDelegate = mockItemDTOActionDelegate;
        orderService.cardDataService = mockCardDataService;

        adHocSettlements = new ArrayList<AdHocLoadSettlementDTO>();
        AdHocLoadSettlementDTO adHocDTO = getTestAdHocLoadSettlementDTO1();
        adHocDTO.setStatus(SettlementStatus.PICKED_UP.code());
        adHocSettlements.add(adHocDTO);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOrderByNumberShouldReturnDTO() {
        when(mockOrderDataService.findByOrderNumber(any(Long.class))).thenReturn(orderDTO);
        when(mockItemDTOActionDelegate.postProcessItems(any(List.class),any(Boolean.class))).thenReturn(updatedItems);

        OrderDTO returnedOrderDTO = orderService.findOrderByNumber(ORDER_NUMBER);
        assertEquals(returnedOrderDTO.getOrderItems().size(), 2);
        assertEquals(returnedOrderDTO.getOrderItems().get(0).getName(), "Item 1 Name Updated");
        assertEquals(returnedOrderDTO.getOrderItems().get(1).getName(), "Item 2 Name Updated");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void findOrdersByCustomerIdReturnsDTOWithFormattedAmount() {
        orderDTOList = new ArrayList<OrderDTO>();
        orderDTOList.add(orderDTO);

        when(mockCustomerDataService.findById(any(Long.class))).thenReturn(getTestCustomerDTO1());
        when(mockOrderDataService.findByCustomerId(any(Long.class))).thenReturn(orderDTOList);
        when(mockItemDTOActionDelegate.postProcessItems(any(List.class),any(Boolean.class))).thenReturn(updatedItems);

        List<OrderDTO> returnedOrderDTOs = orderService.findOrdersByCustomerId(ORDER_NUMBER);
        assertEquals(returnedOrderDTOs.size(), 1);
        assertTrue(StringUtil.isNotBlank(returnedOrderDTOs.get(0).getFormattedTotalAmount()));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void findOrderItemsByCustomerIdReturnsDTOWithFormattedAmount() {
        when(mockCustomerDataService.findById(any(Long.class))).thenReturn(getTestCustomerDTO1());
        when(mockOrderDataService.findByCustomerId(any(Long.class))).thenReturn(getOrders());
        when(mockItemDTOActionDelegate.postProcessItems(any(List.class),any(Boolean.class))).thenReturn(updatedItems);
        when(mockWebCreditSettlementDataService.findByOrderId(any(Long.class)))
                .thenReturn(new ArrayList<WebCreditSettlementDTO>());
        when(mockCardSettlementDataService.findByOrderId(any(Long.class)))
                .thenReturn(new ArrayList<PaymentCardSettlementDTO>());
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class)))
        .thenReturn(new ArrayList<AdHocLoadSettlementDTO>());

        List<OrderItemsDTO> orderItemsDTO = orderService.findOrderItemsByCustomerId(CS_CUSTOMERID_1);
        assertTrue(StringUtil.isNotBlank(orderItemsDTO.get(0).getOrder().getFormattedTotalAmount()));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void findOrderItemsByCustomerIdAndOrderStatusReturnsDTOWithFormattedAmount() {
        when(mockCustomerDataService.findById(any(Long.class))).thenReturn(getTestCustomerDTO1());
        when(mockOrderDataService.findByCustomerId(any(Long.class))).thenReturn(getOrdersWithPaid());
        when(mockItemDTOActionDelegate.postProcessItems(any(List.class),any(Boolean.class))).thenReturn(updatedItems);
        when(mockWebCreditSettlementDataService.findByOrderId(any(Long.class)))
                .thenReturn(new ArrayList<WebCreditSettlementDTO>());
        when(mockCardSettlementDataService.findByOrderId(any(Long.class)))
                .thenReturn(new ArrayList<PaymentCardSettlementDTO>());
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class)))
        .thenReturn(new ArrayList<AdHocLoadSettlementDTO>());

        List<OrderItemsDTO> orderItemsDTO = orderService.findOrderItemsByCustomerIdAndOrderStatus(CS_CUSTOMERID_1, OrderStatus.PAID.code());
        assertTrue(StringUtil.isNotBlank(orderItemsDTO.get(0).getOrder().getFormattedTotalAmount()));
    }
    
    
    @SuppressWarnings("unchecked")
    @Test
    public void findOrderItemsByCustomerIdAndOrderStatusReturnsEmptyDTOList() {
        when(mockCustomerDataService.findById(any(Long.class))).thenReturn(getTestCustomerDTO1());
        when(mockOrderDataService.findByCustomerId(any(Long.class))).thenReturn(getOrders());
        when(mockItemDTOActionDelegate.postProcessItems(any(List.class),any(Boolean.class))).thenReturn(updatedItems);
        when(mockWebCreditSettlementDataService.findByOrderId(any(Long.class)))
                .thenReturn(new ArrayList<WebCreditSettlementDTO>());
        when(mockCardSettlementDataService.findByOrderId(any(Long.class)))
                .thenReturn(new ArrayList<PaymentCardSettlementDTO>());
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class)))
        .thenReturn(new ArrayList<AdHocLoadSettlementDTO>());

        List<OrderItemsDTO> orderItemsDTO = orderService.findOrderItemsByCustomerIdAndOrderStatus(CS_CUSTOMERID_1, OrderStatus.PAID.code());
        assertEquals(0, orderItemsDTO.size());
    }

    @Test
    public void testPopulateOrderSettlementShouldReturnNullBecauseEmptyResultSet() {
        when(mockWebCreditSettlementDataService.findByOrderId(any(Long.class)))
                .thenReturn(new ArrayList<WebCreditSettlementDTO>());
        WebCreditSettlementDTO dto = orderService.populateOrderSettlement(ORDER_NUMBER);
        assertNull(dto);
    }

    @Test
    public void testPopulateOrderSettlementShouldReturnNullBecauseNoMatch() {
        List<WebCreditSettlementDTO> settlements = new ArrayList<WebCreditSettlementDTO>();
        settlements.add(null);
        settlements.add(null);
        settlements.add(null);
        when(mockWebCreditSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        WebCreditSettlementDTO dto = orderService.populateOrderSettlement(ORDER_NUMBER);
        assertNull(dto);
    }

    @Test
    public void testPopulateOrderSettlementShouldReturnDTO() {
        List<WebCreditSettlementDTO> settlements = new ArrayList<WebCreditSettlementDTO>();
        settlements.add(null);
        settlements.add(new WebCreditSettlementDTO());
        settlements.add(null);
        when(mockWebCreditSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        WebCreditSettlementDTO dto = orderService.populateOrderSettlement(ORDER_NUMBER);
        assertNotNull(dto);
    }

    @Test
    public void testPopulatePaymentSettlementShouldReturnNullBecauseEmptyResultSet() {
        when(mockCardSettlementDataService.findByOrderId(any(Long.class)))
                .thenReturn(new ArrayList<PaymentCardSettlementDTO>());
        PaymentCardSettlementDTO dto = orderService.populateOrderCardSettlement(ORDER_NUMBER);
        assertNull(dto);
    }

    @Test
    public void testPopulatePaymentSettlementShouldReturnNullBecauseNoMatch() {
        List<PaymentCardSettlementDTO> settlements = new ArrayList<PaymentCardSettlementDTO>();
        settlements.add(null);
        settlements.add(null);
        settlements.add(null);
        when(mockCardSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        PaymentCardSettlementDTO dto = orderService.populateOrderCardSettlement(ORDER_NUMBER);
        assertNull(dto);
    }

    @Test
    public void testPopulatePaymentSettlementShouldReturnDTO() {
        List<PaymentCardSettlementDTO> settlements = new ArrayList<PaymentCardSettlementDTO>();
        settlements.add(null);
        settlements.add(new PaymentCardSettlementDTO());
        settlements.add(null);
        when(mockCardSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        PaymentCardSettlementDTO dto = orderService.populateOrderCardSettlement(ORDER_NUMBER);
        assertNotNull(dto);
    }
    
    @Test
    public void testPopulateAdhocLoadSettlementShouldReturnNullBecauseEmptyResultSet() {
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class))).thenReturn(new ArrayList<AdHocLoadSettlementDTO>());
        AdHocLoadSettlementDTO dto = orderService.populateAdhocLoadSettlement(ORDER_NUMBER);
        assertNull(dto);
    }

    @Test
    public void testPopulateAdhocLoadSettlementShouldReturnNullBecauseNoMatch() {
        List<AdHocLoadSettlementDTO> settlements = new ArrayList<AdHocLoadSettlementDTO>();
        settlements.add(null);
        settlements.add(null);
        settlements.add(null);
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        AdHocLoadSettlementDTO dto = orderService.populateAdhocLoadSettlement(ORDER_NUMBER);
        assertNull(dto);
    }

    @Test
    public void testPopulateAdhocLoadSettlementShouldReturnDTO() {
        List<AdHocLoadSettlementDTO> settlements = new ArrayList<AdHocLoadSettlementDTO>();
        settlements.add(null);
        settlements.add(new AdHocLoadSettlementDTO());
        settlements.add(null);
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        AdHocLoadSettlementDTO dto = orderService.populateAdhocLoadSettlement(ORDER_NUMBER);
        assertNotNull(dto);
    }

    @Test
    public void shouldFindOrdersByCustomerIdAndDates() {
        when(mockCustomerDataService.findByExternalId(CUSTOMER_ID_2)).thenReturn(getTestCustomerDTO1());
        when(mockOrderDataService.findByCustomerIdAndDuration(anyLong(), any(DateTime.class), any(DateTime.class))).thenReturn(getOrders());
        List<OrderDTO> orders = orderService.findOrdersByCustomerIdAndDates(CUSTOMER_ID_2, WEB_SERVICE_REQUEST_START_DATE,
                        WEB_SERVICE_REQUEST_END_DATE);
        assertNotNull(orders);
        assertTrue(orders.size() > 0);
        verify(mockCustomerDataService, atLeastOnce()).findByExternalId(CUSTOMER_ID_2);
        verify(mockOrderDataService, atLeastOnce()).findByCustomerIdAndDuration(anyLong(), any(DateTime.class), any(DateTime.class));
    }

    @Test
    public void updateOrderStatusForAdHocLoadSettlementShouldReturnOrderWithCompleteStatus() {
        when(mockOrderDataService.findById(any(Long.class))).thenReturn(getTestOrderDTO1());
        List<AdHocLoadSettlementDTO> settlements = new ArrayList<AdHocLoadSettlementDTO>();
        AdHocLoadSettlementDTO adHocDTO = getTestAdHocLoadSettlementDTO1();
        adHocDTO.setStatus(SettlementStatus.PICKED_UP.code());
        settlements.add(null);
        settlements.add(adHocDTO);
        settlements.add(null);
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        OrderDTO dto = orderService.updateOrderStatus(ORDER_NUMBER);
        assertNotNull(dto);
        assertEquals(OrderStatus.COMPLETE.code(), dto.getStatus());
    }

    @Test
    public void updateOrderStatusForAdHocLoadSettlementShouldReturnOrderWithErrorStatus() {
        when(mockOrderDataService.findById(any(Long.class))).thenReturn(getTestOrderDTO1());
        List<AdHocLoadSettlementDTO> settlements = new ArrayList<AdHocLoadSettlementDTO>();
        AdHocLoadSettlementDTO adHocDTO = getTestAdHocLoadSettlementDTO1();
        adHocDTO.setStatus(SettlementStatus.PICK_UP_EXPIRED.code());
        settlements.add(null);
        settlements.add(adHocDTO);
        settlements.add(null);
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        OrderDTO dto = orderService.updateOrderStatus(ORDER_NUMBER);
        assertNotNull(dto);
        assertEquals(OrderStatus.ERROR.code(), dto.getStatus());
    }

    @Test
    public void updateOrderStatusForAdHocLoadSettlementShouldReturnOrderWithCancelledStatus() {
        when(mockOrderDataService.findById(any(Long.class))).thenReturn(getTestOrderDTO1());
        List<AdHocLoadSettlementDTO> settlements = new ArrayList<AdHocLoadSettlementDTO>();
        AdHocLoadSettlementDTO adHocDTO = getTestAdHocLoadSettlementDTO1();
        adHocDTO.setStatus(SettlementStatus.CANCELLED.code());
        settlements.add(null);
        settlements.add(adHocDTO);
        settlements.add(null);
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        OrderDTO dto = orderService.updateOrderStatus(ORDER_NUMBER);
        assertNotNull(dto);
        assertEquals(OrderStatus.CANCELLED.code(), dto.getStatus());
    }

    @Test
    public void updateOrderStatusForAdHocLoadSettlementShouldReturnOrderWithPaidStatus() {
        when(mockOrderDataService.findById(any(Long.class))).thenReturn(getTestOrderDTO2());
        List<AdHocLoadSettlementDTO> settlements = new ArrayList<AdHocLoadSettlementDTO>();
        AdHocLoadSettlementDTO adHocDTO = getTestAdHocLoadSettlementDTO1();
        adHocDTO.setStatus("Rubish!!");
        settlements.add(null);
        settlements.add(adHocDTO);
        settlements.add(null);
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        OrderDTO dto = orderService.updateOrderStatus(ORDER_NUMBER);
        assertNotNull(dto);
        assertEquals(OrderStatus.PAID.code(), dto.getStatus());
    }

    @Test
    public void updateOrderStatusForPaymentCardSettlementShouldReturnOrderWithCompleteStatus() {
        when(mockOrderDataService.findById(any(Long.class))).thenReturn(getTestOrderDTO1());
        List<PaymentCardSettlementDTO> settlements = new ArrayList<PaymentCardSettlementDTO>();
        PaymentCardSettlementDTO adHocDTO = getTestPaymentCardSettlementDTO1();
        adHocDTO.setStatus(SettlementStatus.ACCEPTED.code());
        settlements.add(null);
        settlements.add(adHocDTO);
        settlements.add(null);
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class))).thenReturn(adHocSettlements);
        when(mockCardSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        OrderDTO dto = orderService.updateOrderStatus(ORDER_NUMBER);
        assertNotNull(dto);
        assertEquals(OrderStatus.COMPLETE.code(), dto.getStatus());
    }

    @Test
    public void updateOrderStatusForPaymentCardSettlementShouldReturnOrderWithErrorStatus() {
        when(mockOrderDataService.findById(any(Long.class))).thenReturn(getTestOrderDTO1());
        List<PaymentCardSettlementDTO> settlements = new ArrayList<PaymentCardSettlementDTO>();
        PaymentCardSettlementDTO adHocDTO = getTestPaymentCardSettlementDTO1();
        adHocDTO.setStatus(SettlementStatus.INCOMPLETE.code());
        settlements.add(null);
        settlements.add(adHocDTO);
        settlements.add(null);
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class))).thenReturn(adHocSettlements);
        when(mockCardSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        OrderDTO dto = orderService.updateOrderStatus(ORDER_NUMBER);
        assertNotNull(dto);
        assertEquals(OrderStatus.ERROR.code(), dto.getStatus());
    }

    @Test
    public void updateOrderStatusForPaymentCardSettlementShouldReturnOrderWithErrorStatusForRequested() {
        when(mockOrderDataService.findById(any(Long.class))).thenReturn(getTestOrderDTO1());
        List<PaymentCardSettlementDTO> settlements = new ArrayList<PaymentCardSettlementDTO>();
        PaymentCardSettlementDTO adHocDTO = getTestPaymentCardSettlementDTO1();
        adHocDTO.setStatus(SettlementStatus.REQUESTED.code());
        settlements.add(null);
        settlements.add(adHocDTO);
        settlements.add(null);
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class))).thenReturn(adHocSettlements);
        when(mockCardSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        OrderDTO dto = orderService.updateOrderStatus(ORDER_NUMBER);
        assertNotNull(dto);
        assertEquals(OrderStatus.ERROR.code(), dto.getStatus());
    }

    @Test
    public void updateOrderStatusForPaymentCardSettlementShouldReturnOrderWithCancelledStatus() {
        when(mockOrderDataService.findById(any(Long.class))).thenReturn(getTestOrderDTO1());
        List<PaymentCardSettlementDTO> settlements = new ArrayList<PaymentCardSettlementDTO>();
        PaymentCardSettlementDTO adHocDTO = getTestPaymentCardSettlementDTO1();
        adHocDTO.setStatus(SettlementStatus.CANCELLED.code());
        settlements.add(null);
        settlements.add(adHocDTO);
        settlements.add(null);
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class))).thenReturn(adHocSettlements);
        when(mockCardSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        OrderDTO dto = orderService.updateOrderStatus(ORDER_NUMBER);
        assertNotNull(dto);
        assertEquals(OrderStatus.CANCELLED.code(), dto.getStatus());
    }

    @Test
    public void updateOrderStatusForPaymentCardSettlementWithDifferentStatusShouldReturnOrderWithCompleteStatus() {
        when(mockOrderDataService.findById(any(Long.class))).thenReturn(getTestOrderDTO2());
        List<PaymentCardSettlementDTO> settlements = new ArrayList<PaymentCardSettlementDTO>();
        PaymentCardSettlementDTO adHocDTO = getTestPaymentCardSettlementDTO1();
        adHocDTO.setStatus("Rubish!!");
        settlements.add(null);
        settlements.add(adHocDTO);
        settlements.add(null);
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class))).thenReturn(adHocSettlements);
        when(mockCardSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        OrderDTO dto = orderService.updateOrderStatus(ORDER_NUMBER);
        assertNotNull(dto);
        assertEquals(OrderStatus.COMPLETE.code(), dto.getStatus());
    }

    @Test
    public void updateOrderStatusForWebCreditSettlementShouldReturnOrderWithCompleteStatus() {
        when(mockOrderDataService.findById(any(Long.class))).thenReturn(getTestOrderDTO1());
        List<WebCreditSettlementDTO> settlements = new ArrayList<WebCreditSettlementDTO>();
        WebCreditSettlementDTO adHocDTO = getTestWebCreditSettlementDTO();
        adHocDTO.setStatus(SettlementStatus.COMPLETE.code());
        settlements.add(null);
        settlements.add(adHocDTO);
        settlements.add(null);
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class))).thenReturn(adHocSettlements);
        when(mockWebCreditSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        OrderDTO dto = orderService.updateOrderStatus(ORDER_NUMBER);
        assertNotNull(dto);
        assertEquals(OrderStatus.COMPLETE.code(), dto.getStatus());
    }

    @Test
    public void updateOrderStatusForWebCreditSettlementShouldReturnOrderWithErrorStatus() {
        when(mockOrderDataService.findById(any(Long.class))).thenReturn(getTestOrderDTO1());
        List<WebCreditSettlementDTO> settlements = new ArrayList<WebCreditSettlementDTO>();
        WebCreditSettlementDTO adHocDTO = getTestWebCreditSettlementDTO();
        adHocDTO.setStatus(SettlementStatus.INCOMPLETE.code());
        settlements.add(null);
        settlements.add(adHocDTO);
        settlements.add(null);
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class))).thenReturn(adHocSettlements);
        when(mockWebCreditSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        OrderDTO dto = orderService.updateOrderStatus(ORDER_NUMBER);
        assertNotNull(dto);
        assertEquals(OrderStatus.ERROR.code(), dto.getStatus());
    }

    @Test
    public void updateOrderStatusForWebCreditSettlementWithDifferentStatusShouldReturnOrderWithCompleteStatus() {
        when(mockOrderDataService.findById(any(Long.class))).thenReturn(getTestOrderDTO1());
        List<WebCreditSettlementDTO> settlements = new ArrayList<WebCreditSettlementDTO>();
        WebCreditSettlementDTO adHocDTO = getTestWebCreditSettlementDTO();
        adHocDTO.setStatus("Rubish!!");
        settlements.add(null);
        settlements.add(adHocDTO);
        settlements.add(null);
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class))).thenReturn(adHocSettlements);
        when(mockWebCreditSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        OrderDTO dto = orderService.updateOrderStatus(ORDER_NUMBER);
        assertNotNull(dto);
        assertEquals(OrderStatus.COMPLETE.code(), dto.getStatus());
    }

    @Test
    public void updateOrderStatusForWebCreditSettlementShouldReturnOrderWithErrorStatusForRequested() {
        when(mockOrderDataService.findById(any(Long.class))).thenReturn(getTestOrderDTO1());
        List<WebCreditSettlementDTO> settlements = new ArrayList<WebCreditSettlementDTO>();
        WebCreditSettlementDTO adHocDTO = getTestWebCreditSettlementDTO();
        adHocDTO.setStatus(SettlementStatus.REQUESTED.code());
        settlements.add(null);
        settlements.add(adHocDTO);
        settlements.add(null);
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class))).thenReturn(adHocSettlements);
        when(mockWebCreditSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        OrderDTO dto = orderService.updateOrderStatus(ORDER_NUMBER);
        assertNotNull(dto);
        assertEquals(OrderStatus.ERROR.code(), dto.getStatus());
    }

    @Test
    public void updateOrderStatusForAdhocSettlementAndWebCreditSettlementShouldReturnOrderWithCompleteStatus() {
        when(mockOrderDataService.findById(any(Long.class))).thenReturn(getTestOrderDTO1());
        List<AdHocLoadSettlementDTO> adhocSettlements = new ArrayList<AdHocLoadSettlementDTO>();
        AdHocLoadSettlementDTO adHocDTO = getTestAdHocLoadSettlementDTO1();
        adHocDTO.setStatus(SettlementStatus.PICKED_UP.code());
        adhocSettlements.add(null);
        adhocSettlements.add(adHocDTO);
        List<WebCreditSettlementDTO> settlements = new ArrayList<WebCreditSettlementDTO>();
        WebCreditSettlementDTO webCreditDTO = getTestWebCreditSettlementDTO();
        webCreditDTO.setStatus(SettlementStatus.COMPLETE.code());
        settlements.add(null);
        settlements.add(webCreditDTO);
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class))).thenReturn(adhocSettlements);
        when(mockWebCreditSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        OrderDTO dto = orderService.updateOrderStatus(ORDER_NUMBER);
        assertNotNull(dto);
        assertEquals(OrderStatus.COMPLETE.code(), dto.getStatus());
    }

    @Test
    public void updateOrderStatusForAdhocSettlementAndPaymentCardSettlementShouldReturnOrderWithCompleteStatus() {
        when(mockOrderDataService.findById(any(Long.class))).thenReturn(getTestOrderDTO1());
        List<AdHocLoadSettlementDTO> adhocSettlements = new ArrayList<AdHocLoadSettlementDTO>();
        AdHocLoadSettlementDTO adHocDTO = getTestAdHocLoadSettlementDTO1();
        adHocDTO.setStatus(SettlementStatus.PICKED_UP.code());
        adhocSettlements.add(null);
        adhocSettlements.add(adHocDTO);
        List<PaymentCardSettlementDTO> settlements = new ArrayList<PaymentCardSettlementDTO>();
        PaymentCardSettlementDTO paymentCardDTO = getTestPaymentCardSettlementDTO1();
        paymentCardDTO.setStatus(SettlementStatus.ACCEPTED.code());
        settlements.add(null);
        settlements.add(paymentCardDTO);
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class))).thenReturn(adhocSettlements);
        when(mockCardSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        OrderDTO dto = orderService.updateOrderStatus(ORDER_NUMBER);
        assertNotNull(dto);
        assertEquals(OrderStatus.COMPLETE.code(), dto.getStatus());
    }

    @Test
    public void updateOrderStatusForAdhocSettlementAndWebCreditSettlementShouldReturnOrderWithErrorStatus() {
        when(mockOrderDataService.findById(any(Long.class))).thenReturn(getTestOrderDTO1());
        List<AdHocLoadSettlementDTO> adhocSettlements = new ArrayList<AdHocLoadSettlementDTO>();
        AdHocLoadSettlementDTO adHocDTO = getTestAdHocLoadSettlementDTO1();
        adHocDTO.setStatus(SettlementStatus.PICK_UP_EXPIRED.code());
        adhocSettlements.add(null);
        adhocSettlements.add(adHocDTO);
        List<WebCreditSettlementDTO> settlements = new ArrayList<WebCreditSettlementDTO>();
        WebCreditSettlementDTO webCreditDTO = getTestWebCreditSettlementDTO();
        webCreditDTO.setStatus(SettlementStatus.COMPLETE.code());
        settlements.add(null);
        settlements.add(webCreditDTO);
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class))).thenReturn(adhocSettlements);
        when(mockWebCreditSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        OrderDTO dto = orderService.updateOrderStatus(ORDER_NUMBER);
        assertNotNull(dto);
        assertEquals(OrderStatus.ERROR.code(), dto.getStatus());
    }

    @Test
    public void updateOrderStatusForAdhocSettlementAndPaymentCardSettlementShouldReturnOrderWithErrorStatus() {
        when(mockOrderDataService.findById(any(Long.class))).thenReturn(getTestOrderDTO1());
        List<AdHocLoadSettlementDTO> adhocSettlements = new ArrayList<AdHocLoadSettlementDTO>();
        AdHocLoadSettlementDTO adHocDTO = getTestAdHocLoadSettlementDTO1();
        adHocDTO.setStatus(SettlementStatus.FAILED.code());
        adhocSettlements.add(null);
        adhocSettlements.add(adHocDTO);
        List<PaymentCardSettlementDTO> settlements = new ArrayList<PaymentCardSettlementDTO>();
        PaymentCardSettlementDTO paymentCardDTO = getTestPaymentCardSettlementDTO1();
        paymentCardDTO.setStatus(SettlementStatus.ACCEPTED.code());
        settlements.add(null);
        settlements.add(paymentCardDTO);
        when(mockAdhocLoadSettlementDataService.findByOrderId(any(Long.class))).thenReturn(adhocSettlements);
        when(mockCardSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        OrderDTO dto = orderService.updateOrderStatus(ORDER_NUMBER);
        assertNotNull(dto);
        assertEquals(OrderStatus.ERROR.code(), dto.getStatus());
    }
}
