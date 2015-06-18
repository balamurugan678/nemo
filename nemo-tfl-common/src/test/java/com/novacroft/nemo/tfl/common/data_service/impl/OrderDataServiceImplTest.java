package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.AddressTestUtil.ADDRESS_ID_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddress1;
import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO1;
import static com.novacroft.nemo.test_support.CardTestUtil.CARD_ID;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CS_CARDNUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.LocationTestUtil.LOCATION_ID_1;
import static com.novacroft.nemo.test_support.LocationTestUtil.getTestLocationDTO1;
import static com.novacroft.nemo.test_support.OrderTestUtil.ORDER_NUMBER;
import static com.novacroft.nemo.test_support.OrderTestUtil.getTestOrder1;
import static com.novacroft.nemo.test_support.OrderTestUtil.getTestOrderDTO1;
import static com.novacroft.nemo.test_support.RefundTestUtil.REFUND_ID_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import com.novacroft.nemo.common.converter.impl.AddressConverterImpl;
import com.novacroft.nemo.common.domain.Address;
import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.converter.impl.OrderConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.OrderDAO;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.domain.Order;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.RefundCeilingRuleLimitTally;
import com.novacroft.nemo.tfl.common.transfer.RefundOrderItemDTO;

public class OrderDataServiceImplTest {
    private OrderDataServiceImpl orderDataServiceImpl;
    private OrderDAO mockOrderDAO;
    private OrderDTO orderDTO;
    private AddressConverterImpl mockAddressConverter;
    private NemoUserContext mockNemoUserContext;
    private final DateTime thresholdTime = new DateTime();
    private final DateTime now = new DateTime();
    private OrderConverterImpl mockOrderConverter;
    private LocationDataService mockLocationDataService;

    @Before
    public void setUp() {
        orderDataServiceImpl = new OrderDataServiceImpl();
        mockOrderDAO = mock(OrderDAO.class);
        mockAddressConverter = mock(AddressConverterImpl.class);
        orderDTO = getTestOrderDTO1();

        this.mockNemoUserContext = mock(NemoUserContext.class);
        ReflectionTestUtils.setField(this.orderDataServiceImpl, "nemoUserContext", this.mockNemoUserContext);

        this.orderDataServiceImpl.setDao(mockOrderDAO);

        this.mockOrderConverter = mock(OrderConverterImpl.class);
        when(this.mockOrderConverter.convertDtoToEntity(any(OrderDTO.class), any(Order.class))).thenReturn(getTestOrder1());
        when(mockOrderConverter.convertEntityToDto(any(Order.class))).thenReturn(orderDTO);
        this.orderDataServiceImpl.setConverter(this.mockOrderConverter);
        
        mockLocationDataService = mock(LocationDataService.class);
        orderDataServiceImpl.locationDataService = mockLocationDataService;

        when(mockAddressConverter.convertDtoToEntity(any(AddressDTO.class), any(Address.class))).thenReturn(getTestAddress1());
        when(mockAddressConverter.convertEntityToDto(any(Address.class))).thenReturn(getTestAddressDTO1());
        orderDataServiceImpl.addressConverter = mockAddressConverter;

        when(mockOrderDAO.getNextOrderNumber()).thenReturn(ORDER_NUMBER);
        when(mockOrderDAO.findById(any(Long.class))).thenReturn(getTestOrder1());
        when(mockOrderDAO.findByQueryUniqueResult(anyString(), any(Long.class), any(DateTime.class), any(DateTime.class)))
                .thenReturn(getTestOrder1());
        when(mockOrderDAO.findByQueryUniqueResult(anyString(), anyString(), anyInt(), any(DateTime.class)))
                .thenReturn(getTestOrder1());
    }

    @Test
    public void createShouldInvokeDAO() {
        List<ItemDTO> orderItems = OrderTestUtil.getListOfItems();
        orderDTO.setOrderItems(orderItems);
        orderDataServiceImpl.create(orderDTO);
        verify(mockOrderDAO).getNextOrderNumber();
    }

    @Test
    public void createShouldInvokeDAOWithItems() {
        List<ItemDTO> orderItems = OrderTestUtil.getListOfItems();
        orderItems.get(0).setId(new Long(1));
        orderDTO.setOrderItems(orderItems);
        orderDataServiceImpl.create(orderDTO);
        verify(mockOrderDAO).getNextOrderNumber();
    }

    @Test
    public void findByOrderNumberShouldReturnNullForNoOrders() {
        when(mockOrderDAO.findByExample(any(Order.class))).thenReturn(new ArrayList<Order>());
        assertNull(orderDataServiceImpl.findByOrderNumber(ORDER_NUMBER));
        verify(mockOrderDAO).findByExample(any(Order.class));
    }
    
    @Test
    public void findByOrderNumberShouldReturnDTO() {
        when(mockOrderDAO.findByExample(any(Order.class))).thenReturn(Arrays.asList(getTestOrder1()));
        
        OrderDTO actualResult = orderDataServiceImpl.findByOrderNumber(ORDER_NUMBER);
        verify(mockOrderDAO).findByExample(any(Order.class));
        assertNotNull(actualResult);
    }

    @Test
    public void findByCustomerIdShouldInvokeDAO() {
        orderDataServiceImpl.findByCustomerId(CUSTOMER_ID_1);
        verify(mockOrderDAO).findByExampleWithOrderBy(any(Order.class), any(org.hibernate.criterion.Order.class));
    }

    @Test
    public void findByCustomerIdAndDurationShouldInvokeDAO() {
        orderDataServiceImpl.findByCustomerIdAndDuration(CUSTOMER_ID_1, thresholdTime, now);
        verify(mockOrderDAO).findByQuery(anyString(), any(Long.class), any(DateTime.class), any(DateTime.class));
    }

    @Test
    public void findByAddressIdAndDurationShouldInvokeDAO() {
        orderDataServiceImpl.findByAddressIdAndDuration(ADDRESS_ID_1, thresholdTime, now);
        verify(mockOrderDAO).findByQuery(anyString(), any(Long.class), any(DateTime.class), any(DateTime.class));
    }

    @Test
    public void findByRefundedCardNumberPriceAndDateShouldInvokeDAO() {
        orderDataServiceImpl.findByRefundedCardNumberPriceAndDate(CS_CARDNUMBER_1, ORDER_NUMBER, now);
        verify(mockOrderDAO).findByQuery(anyString(), anyString(), anyInt(), any(DateTime.class));
    }

    @Test
    public void findByCustomerCardNumberPriceAndDateShouldInvokeDAO() {
        orderDataServiceImpl.findByCustomerCardNumberPriceAndDate(CS_CARDNUMBER_1, CUSTOMER_ID_1, ORDER_NUMBER, now);
        verify(mockOrderDAO).findByQuery(anyString(), anyLong(), anyString(), anyInt(), any(DateTime.class));
    }

    @Test
    public void findByExampleShouldInvokeDAO() {
        orderDataServiceImpl.findByExample(getTestOrder1());
        verify(mockOrderDAO).findByExample(any(Order.class));
    }

    @Test
    public void findAllRefundsInPast12MonthsShouldInvokeDAO() {
        returnRows();
        List<RefundCeilingRuleLimitTally> result = orderDataServiceImpl.findAllRefundsForCustomerInPast12Months(CUSTOMER_ID_1, "REFUND");
        assertNotNull(result);
    }

    @Test
    public void findAllGoodwillsForCustomerInPast12MonthsTest() {
        returnRows();
        List<RefundCeilingRuleLimitTally> result = orderDataServiceImpl.findAllGoodwillsForCustomerInPast12Months(CUSTOMER_ID_1);
        assertNotNull(result);
    }

    @Test
    public void findAllRefundsForCustomerShouldInvokeDAO() {
        SQLQuery query = mock(SQLQuery.class);
        when(mockOrderDAO.createSQLQuery(anyString())).thenReturn(query);
        orderDataServiceImpl.findAllRefundsForCustomer(CUSTOMER_ID_1);
        verify(mockOrderDAO).createSQLQuery(anyString());
    }

    @Test
    public void findRefundDetailForItemAndCustomerShouldInvokeDAO() {
        orderDataServiceImpl.findRefundDetailForItemAndCustomer(CARD_ID, CUSTOMER_ID_1);
        verify(mockOrderDAO).findByQuery(anyString(), anyLong(), anyLong());
    }
    
    @Test
    public void findRefundDetailForItemAndCustomerShouldInvokeAddressConverter() {
        RefundOrderItemDTO mockRefundDTO = mock(RefundOrderItemDTO.class);
        when(mockRefundDTO.getStationId()).thenReturn(LOCATION_ID_1);
        when(mockOrderDAO.findByQuery(anyString(), anyLong(), anyLong())).thenReturn(Arrays.asList(mockRefundDTO));
        when(mockLocationDataService.getActiveLocationById(mockRefundDTO.getStationId().intValue())).thenReturn(getTestLocationDTO1());
        
        RefundOrderItemDTO actualResult = orderDataServiceImpl.findRefundDetailForItemAndCustomer(REFUND_ID_1, CUSTOMER_ID_1);
        verify(mockOrderDAO).findByQuery(anyString(), anyLong(), anyLong());
        verify(mockAddressConverter).convertEntityToDto(any(Address.class));
        assertNotNull(actualResult);
        verify(mockLocationDataService).getActiveLocationById(anyInt());
    }

    @Test
    public void findRefundByBACSReferenceNumberShouldInvokeDAO() {
        orderDataServiceImpl.findRefundByBACSReferenceNumber(CARD_ID);
        verify(mockOrderDAO).findByQuery(anyString(), anyLong());
    }

    @Test
    public void findRefundByChequeSerialNumberShouldInvokeDAO() {
        orderDataServiceImpl.findRefundByChequeSerialNumber(CARD_ID);
        verify(mockOrderDAO).findByQuery(anyString(), anyLong());
    }

    @Test
    public void findRefundsForCustomerShouldInvokeDAO() {
        orderDataServiceImpl.findRefundsForCustomer(CUSTOMER_ID_1);
        verify(mockOrderDAO).findByQuery(anyString(), anyLong());
    }

    @Test
    public void getOrdersByCaseNumberShouldInvokeDAO() {
        orderDataServiceImpl.getOrdersByCaseNumber(CS_CARDNUMBER_1);
        verify(mockOrderDAO).findByExample(any(Order.class));
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(orderDataServiceImpl.getNewEntity());
    }
    
    @Test
    public void flushShouldInvokeDAO() {
        doNothing().when(mockOrderDAO).flush();
        orderDataServiceImpl.flush();
        verify(mockOrderDAO).flush();
    }
    
    @Test
    public void findAllRefundsShouldInvokeDAO() {
        Query mockQuery = mock(Query.class);
        List<Order> mockReturnedList = Arrays.asList(getTestOrder1());
        when(mockQuery.list()).thenReturn(mockReturnedList);
        when(mockOrderDAO.createQuery(anyString())).thenReturn(mockQuery);
        
        List<OrderDTO> actualResult = orderDataServiceImpl.findAllRefunds();
        verify(mockOrderDAO).createQuery(anyString());
        assertNotNull(actualResult);
    }

    @Test
    public void findByIdAndCustomerId() {
        when(mockOrderDAO.findByQueryUniqueResult(anyString(), anyLong(), anyLong())).thenReturn(OrderTestUtil.getOrderEntityWithExternalId());
        when(mockOrderConverter.convertEntityToDto(any(Order.class))).thenReturn(OrderTestUtil.getOrderDTOWithAdminFeeAndShippingItem());

        orderDTO = orderDataServiceImpl.findByIdAndCustomerId(OrderTestUtil.ORDER_ID, CustomerTestUtil.CUSTOMER_ID_1);
        assertNotNull(orderDTO);
        verify(mockOrderDAO).findByQueryUniqueResult(anyString(), anyLong(), anyLong());
    }

    @Test
    public void findByIdAndCustomerIdReturnsNull() {
        when(mockOrderDAO.findByQueryUniqueResult(anyString(), anyLong(), anyLong())).thenReturn(null);

        orderDTO = orderDataServiceImpl.findByIdAndCustomerId(OrderTestUtil.ORDER_ID, CustomerTestUtil.CUSTOMER_ID_1);
        assertNull(orderDTO);
        verify(mockOrderDAO).findByQueryUniqueResult(anyString(), anyLong(), anyLong());
        verify(mockOrderConverter, never()).convertEntityToDto(any(Order.class));
    }

    @Test
    public void getExternalIdFromInternalId() {
        when(mockOrderDAO.findById(anyLong())).thenReturn(OrderTestUtil.getOrderEntityWithExternalId());
        Long result = orderDataServiceImpl.getExternalIdFromInternalId(OrderTestUtil.EXTERNAL_ID);
        assertNotNull(result);
    }

    @Test
    public void getInternalIdFromExternalId(){
        when(mockOrderDAO.findByExternalId(anyLong())).thenReturn(OrderTestUtil.getOrderEntityWithExternalId());
        when(mockOrderDAO.getInternalIdFromExternalId(anyLong())).thenReturn(OrderTestUtil.ORDER_ID);
        assertEquals(OrderTestUtil.ORDER_ID, orderDataServiceImpl.getInternalIdFromExternalId(OrderTestUtil.EXTERNAL_ID));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getNumberOfOrdersInQueue() {
        when(mockOrderDAO.getNumberOfResultsFromQuery(anyString(), anyMap())).thenReturn(ORDER_NUMBER);
        assertEquals(ORDER_NUMBER, orderDataServiceImpl.getNumberOfOrdersInQueue(OrderStatus.FULFILMENT_PENDING));
        verify(mockOrderDAO).getNumberOfResultsFromQuery(anyString(), anyMap());
    }
    
    @Test
    public void getExampleWithSoonestOrderBy(){
        when(mockOrderDAO.findByExampleWithSoonestOrderBy(any(Order.class), anyString())).thenReturn(Arrays.asList(OrderTestUtil.getTestOrder1()));
        assertEquals(Arrays.asList(OrderTestUtil.getTestOrderDTO1()), orderDataServiceImpl.findByFulfilmentQueue(OrderStatus.FULFILMENT_PENDING.code()));
    }

    private void returnRows() {
        SQLQuery query = mock(SQLQuery.class);
        Mockito.doReturn(query).when(mockOrderDAO).createSQLQuery(anyString());
        List<Object[]> rows = new ArrayList<Object[]>();
        Object row[] = { BigDecimal.ONE, "1", "3" };
        rows.add(row);
        when(query.list()).thenReturn(rows);
    }
    
    @Test
    public void findAllOverlapRefundForCustomerInPast12MonthsShouldInvokeDAO() {
        returnRows();
        
        RefundCeilingRuleLimitTally actualResult = orderDataServiceImpl.findAllOverlapRefundForCustomerInPast12Months(CUSTOMER_ID_1);
        
        verify(mockOrderDAO).createSQLQuery(anyString());
        assertEquals(Integer.valueOf(1), actualResult.getTally());
    }

}

