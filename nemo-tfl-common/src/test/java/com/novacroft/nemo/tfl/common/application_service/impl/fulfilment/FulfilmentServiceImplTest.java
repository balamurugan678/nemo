package com.novacroft.nemo.tfl.common.application_service.impl.fulfilment;

import static com.novacroft.nemo.common.utils.StringUtil.COMMA;
import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static com.novacroft.nemo.common.utils.StringUtil.WHITE_SPACE;
import static com.novacroft.nemo.test_support.CardTestUtil.CARD_ID;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.OrderTestUtil.ORDER_NUMBER;
import static com.novacroft.nemo.test_support.OrderTestUtil.getListOfItemsWithProductItem;
import static com.novacroft.nemo.test_support.OrderTestUtil.getOrderDTOWithAnnualTravelCard;
import static com.novacroft.nemo.test_support.OrderTestUtil.getOrderDTOWithItems;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.TICKET_DESCRIPTION;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.getTestPrePaidTicketDTO;
import static com.novacroft.nemo.test_support.SettlementTestUtil.AUTHORISED_AMOUNT_1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.getTestPaymentCardSettlementDTO1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.getTestWebCreditSettlementDTO;
import static com.novacroft.nemo.test_support.ShippingMethodTestUtil.SHIPPING_METHOD_RECORDED_DELIVERY_1;
import static com.novacroft.nemo.test_support.ShippingMethodTestUtil.getTestShippingMethodDTO1;
import static com.novacroft.nemo.test_support.WebAccountCreditRefundPaymentTestUtil.getWebCreditSettlementDTOList;
import static com.novacroft.nemo.tfl.common.constant.FulfilmentConstants.PAYMENT_CARD;
import static com.novacroft.nemo.tfl.common.constant.FulfilmentConstants.WEB_CREDIT;
import static com.novacroft.nemo.tfl.common.util.CartUtil.isItemDTOProductItemDTO;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.data_service.impl.AddressDataServiceImpl;
import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.tfl.common.command.impl.fulfilment.FulfilmentCmd;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.PrePaidTicketDataService;
import com.novacroft.nemo.tfl.common.data_service.ShippingMethodDataService;
import com.novacroft.nemo.tfl.common.data_service.WebCreditSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.impl.CardDataServiceImpl;
import com.novacroft.nemo.tfl.common.data_service.impl.CustomerDataServiceImpl;
import com.novacroft.nemo.tfl.common.data_service.impl.OrderDataServiceImpl;
import com.novacroft.nemo.tfl.common.data_service.impl.PaymentCardSettlementDataServiceImpl;
import com.novacroft.nemo.tfl.common.data_service.impl.PrePaidTicketDataServiceImpl;
import com.novacroft.nemo.tfl.common.data_service.impl.ShippingMethodDataServiceImpl;
import com.novacroft.nemo.tfl.common.data_service.impl.WebCreditSettlementDataServiceImpl;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodItemDTO;
import com.novacroft.nemo.tfl.common.transfer.WebCreditSettlementDTO;

public class FulfilmentServiceImplTest {

    private FulfilmentServiceImpl service;
    private OrderDataService mockOrderDataService;
    private CardDataService mockCardDataService;
    private PrePaidTicketDataService mockPrePaidTicketDataService;
    private ShippingMethodDataService mockShippingMethodDataService;
    private PaymentCardSettlementDataService mockCardSettlementDataService;
    private WebCreditSettlementDataService mockWebCreditSettlementDataService;
    private CustomerDataService mockCustomerDataService;
    private List<PaymentCardSettlementDTO> paymentCardSettlementList;
    private PaymentCardSettlementDTO mockPaymentCardSettlement;
    private AddressDataService mockAddressDataService;

    @Before
    public void setUp() {
        service = new FulfilmentServiceImpl();
        
        mockOrderDataService = mock(OrderDataServiceImpl.class);
        mockCardDataService = mock(CardDataServiceImpl.class);
        mockPrePaidTicketDataService = mock(PrePaidTicketDataServiceImpl.class);
        mockShippingMethodDataService = mock(ShippingMethodDataServiceImpl.class);
        mockCardSettlementDataService = mock(PaymentCardSettlementDataServiceImpl.class);
        mockWebCreditSettlementDataService = mock(WebCreditSettlementDataServiceImpl.class);
        mockCustomerDataService = mock(CustomerDataServiceImpl.class);
        mockPaymentCardSettlement = mock(PaymentCardSettlementDTO.class);
        mockAddressDataService = mock(AddressDataServiceImpl.class);
        
        service.orderDataService = mockOrderDataService;
        service.cardDataService = mockCardDataService;
        service.prePaidTicketDataService = mockPrePaidTicketDataService;
        service.shippingMethodDataService = mockShippingMethodDataService;
        service.cardSettlementDataService = mockCardSettlementDataService;
        service.webCreditSettlementDataService = mockWebCreditSettlementDataService;
        service.customerDataService = mockCustomerDataService;
        service.addressDataService = mockAddressDataService;
        
        paymentCardSettlementList = new ArrayList<PaymentCardSettlementDTO>();
        paymentCardSettlementList.add(mockPaymentCardSettlement);
        
        when(mockOrderDataService.findByOrderNumber(any(Long.class))).thenReturn(OrderTestUtil.getOrderDTOWithItems());
        when(mockCardDataService.findById(any(Long.class))).thenReturn(getTestCardDTO1());
        when(mockPrePaidTicketDataService.findById(any(Long.class))).thenReturn(getTestPrePaidTicketDTO());
        when(mockShippingMethodDataService.findById(any(Long.class))).thenReturn(getTestShippingMethodDTO1());
        when(mockCustomerDataService.findByCustomerId(any(Long.class))).thenReturn(getTestCustomerDTO1());
        when(mockAddressDataService.findById(any(Long.class))).thenReturn(null);
    }

    @Test
    public void updateOrderStatusShouldInvokeService() {
        service.updateOrderStatus(getOrderDTOWithItems(), OrderStatus.FULFILLED.code());
        verify(mockOrderDataService).createOrUpdate(any(OrderDTO.class));
    }

    @Test
    public void associateFulfiledOysterCardNumberWithTheOrderShouldInvokeServices() {
        service.associateFulfiledOysterCardNumberWithTheOrder(getOrderDTOWithItems(), OYSTER_NUMBER_1);
        verify(mockCardDataService).findById(any(Long.class));
        verify(mockCardDataService).createOrUpdate(any(CardDTO.class));
    }
    
    @Test
    public void getOrderFromOrderNumberShouldInvokeService() {
        service.getOrderFromOrderNumber(ORDER_NUMBER);
        verify(mockOrderDataService).findByOrderNumber(any(Long.class));
    }
    
    @Test
    public void getCustomerByCustomerIdShouldInvokeService() {
        service.getCustomerByCustomerId(CUSTOMER_ID_1);
        verify(mockCustomerDataService).findByCustomerId(any(Long.class));
    }
    
    @Test
    public void getCardByCardIdShouldInvokeCardDataService() {
        service.getCardByCardId(CARD_ID);
        verify(mockCardDataService).findById(any(Long.class));
    }
    
    @Test
    public void populateProductNameForTicketsShouldSetProductName() {
        FulfilmentCmd cmd = new FulfilmentCmd();
        cmd.setOrder(getOrderDTOWithAnnualTravelCard());
        service.populateProductNameForTickets(cmd.getOrder());
        for(ItemDTO orderItem : cmd.getOrder().getOrderItems()) {
            if(isItemDTOProductItemDTO(orderItem)) {
                assertEquals(TICKET_DESCRIPTION, orderItem.getName());
            }
        }
    }
    
    @Test
    public void findDescriptionAndPopulateProductNameShouldInvokeService() {
        service.findDescriptionAndPopulateProductName(new ProductItemDTO());
        verify(mockPrePaidTicketDataService).findById((any(Long.class)));
    }
    
    @Test
    public void populateShippingMethodForTicketsShouldSetShippingMethod() {
        FulfilmentCmd cmd = new FulfilmentCmd();
        cmd.setOrder(getOrderDTOWithItems());
        service.populateShippingMethodForTickets(cmd);
        assertEquals(SHIPPING_METHOD_RECORDED_DELIVERY_1, cmd.getShippingMethod());
    }
    
    @Test
    public void findShippingMethodShouldInvokeService() {
        service.findShippingMethod(new ShippingMethodItemDTO());
        verify(mockShippingMethodDataService).findById(any(Long.class));
    }
    
    @Test
    public void populatePaymentMethodForTicketsShouldSetPaymentMethodNull() {
        FulfilmentCmd cmd = new FulfilmentCmd();
        cmd.setOrder(getOrderDTOWithItems());
        assertEquals(EMPTY_STRING, service.populatePaymentMethodForTickets(cmd));
    }
    
    @Test
    public void populatePaymentMethodForTicketsShouldSetPaymentMethod() {
        when(mockWebCreditSettlementDataService.findByOrderId(any(Long.class))).thenReturn(getWebCreditSettlementDTOList());
        when(mockCardSettlementDataService.findByOrderId(any(Long.class))).thenReturn(paymentCardSettlementList);
        FulfilmentCmd cmd = new FulfilmentCmd();
        cmd.setOrder(getOrderDTOWithItems());
        assertEquals(WEB_CREDIT + COMMA + WHITE_SPACE + PAYMENT_CARD, service.populatePaymentMethodForTickets(cmd));
    }
    
    @Test
    public void populatePaymentMethodForTicketsShouldSetPaymentMethodToWebCredit() {
        when(mockWebCreditSettlementDataService.findByOrderId(any(Long.class))).thenReturn(getWebCreditSettlementDTOList());
        when(mockCardSettlementDataService.findByOrderId(any(Long.class))).thenReturn(null);
        FulfilmentCmd cmd = new FulfilmentCmd();
        cmd.setOrder(getOrderDTOWithItems());
        assertEquals(WEB_CREDIT, service.populatePaymentMethodForTickets(cmd));
    }
    
    @Test
    public void populatePaymentMethodForTicketsShouldSetPaymentMethodToPaymentCard() {
        when(mockWebCreditSettlementDataService.findByOrderId(any(Long.class))).thenReturn(null);
        when(mockCardSettlementDataService.findByOrderId(any(Long.class))).thenReturn(paymentCardSettlementList);
        FulfilmentCmd cmd = new FulfilmentCmd();
        cmd.setOrder(getOrderDTOWithItems());
        assertEquals(PAYMENT_CARD, service.populatePaymentMethodForTickets(cmd));
    }
    
    @Test
    public void findSettlementsToPopulatePaymentMethodShouldInvokeServices(){
        FulfilmentCmd cmd = new FulfilmentCmd();
        cmd.setOrder(getOrderDTOWithItems());
        service.findSettlementsAndPopulatePaymentMethod(cmd);
        verify(mockWebCreditSettlementDataService).findByOrderId(any(Long.class));
        verify(mockCardSettlementDataService).findByOrderId(any(Long.class));
    }
    
    @Test
    public void loadOrderDetailsShouldInvokeServices() {
        FulfilmentCmd cmd = new FulfilmentCmd();
        cmd.setOrder(getOrderDTOWithItems());
        service.loadOrderDetails(cmd);
        verify(mockOrderDataService).findByOrderNumber(any(Long.class));
        verify(mockCustomerDataService).findByCustomerId(any(Long.class));
        verify(mockShippingMethodDataService).findById(any(Long.class));
    }
    
    @Test
    public void getAmountPaidUsingWebCreditShouldCalculateAmountPaidUsingWebCredit() {
        List<WebCreditSettlementDTO> settlements = new ArrayList<WebCreditSettlementDTO>();
        WebCreditSettlementDTO adHocDTO = getTestWebCreditSettlementDTO();
        adHocDTO.setStatus(SettlementStatus.COMPLETE.code());
        settlements.add(adHocDTO);
        when(mockWebCreditSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        assertEquals(AUTHORISED_AMOUNT_1,service.getAmountPaidUsingWebCredit(getOrderDTOWithItems()));
    }
    
    @Test
    public void getAmountPaidUsingPaymentCardShouldCalculateAmountPaidUsingPaymentCard() {
        List<PaymentCardSettlementDTO> settlements = new ArrayList<PaymentCardSettlementDTO>();
        PaymentCardSettlementDTO adHocDTO = getTestPaymentCardSettlementDTO1();
        adHocDTO.setStatus(SettlementStatus.COMPLETE.code());
        settlements.add(adHocDTO);
        when(mockCardSettlementDataService.findByOrderId(any(Long.class))).thenReturn(settlements);
        assertEquals(AUTHORISED_AMOUNT_1,service.getAmountPaidUsingPaymentCard(getOrderDTOWithItems()));
    }
    
    @Test
    public void getWebCreditSettlementsForOrderShouldInvokeService() {
        service.getWebCreditSettlementsForOrder(getOrderDTOWithItems());
        verify(mockWebCreditSettlementDataService).findByOrderId(any(Long.class));
    }

    @Test
    public void getPaymentCardSettlementsForOrderShouldInvokeService() {
        service.getPaymentCardSettlementsForOrder(getOrderDTOWithItems());
        verify(mockCardSettlementDataService).findByOrderId(any(Long.class));
    }

    @Test
    public void getSubTotalShouldCalculateTotal() {
        assertEquals(new Integer(200), service.getSubTotal(getListOfItemsWithProductItem()));
    }
}
