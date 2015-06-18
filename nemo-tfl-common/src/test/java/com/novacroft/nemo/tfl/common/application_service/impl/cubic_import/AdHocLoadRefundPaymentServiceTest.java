package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.BALANCE_1;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO13;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO14;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.LocationTestUtil.LOCATION_ID_1;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartCmdTestUtil;
import com.novacroft.nemo.test_support.CubicTestUtil;
import com.novacroft.nemo.test_support.SettlementTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CardUpdateService;
import com.novacroft.nemo.tfl.common.application_service.CommonRefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.data_service.AdHocLoadSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.impl.OrderDataServiceImpl;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;

/**
 * Unit tests for AdministrationFeeService
 */

public class AdHocLoadRefundPaymentServiceTest {

    private AdHocLoadRefundPaymentServiceImpl service;
    private GetCardService mockGetCardService;
    private CommonRefundPaymentService mockCommonRefundPaymentService;
    private CustomerDataService mockCustomerDataService;
    private CardUpdateService mockCardUpdateService;
    private CustomerService mockCustomerService;
    private AdHocLoadSettlementDataService mockAdHocLoadSettlementDataService;
    private CardDataService mockCardDataService;
    private OrderDataService mockOrderDataService;

    @Before
    public void setUp() {
        service = new AdHocLoadRefundPaymentServiceImpl();
        mockGetCardService = mock(GetCardService.class);
        mockCommonRefundPaymentService = mock(CommonRefundPaymentService.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        mockCardUpdateService = mock(CardUpdateService.class);
        mockCustomerService = mock(CustomerService.class);
        mockAdHocLoadSettlementDataService = mock(AdHocLoadSettlementDataService.class);
        mockCardDataService = mock(CardDataService.class);
        mockOrderDataService = mock(OrderDataServiceImpl.class);

        service.getCardService = mockGetCardService;
        service.commonRefundPaymentService = mockCommonRefundPaymentService;
        service.customerDataService = mockCustomerDataService;
        service.cardUpdateService = mockCardUpdateService;
        service.customerService = mockCustomerService;
        service.adHocLoadSettlementDataService = mockAdHocLoadSettlementDataService;
        service.cardDataService = mockCardDataService;
        service.orderDataService = mockOrderDataService;
    }

    @Test
    public void isPrePayValueForTargetCardNumberAvailableShouldReturnTrue() {
        assertTrue(service.isPendingItemPrePayValueForTargetCardNumberAvailable(getTestCardInfoResponseV2DTO14()));
    }

    @Test
    public void isPrePayValueForTargetCardNumberAvailableShouldReturnFalse() {
        assertFalse(service.isPendingItemPrePayValueForTargetCardNumberAvailable(getTestCardInfoResponseV2DTO13()));
    }

    @Test
    public void getPreviousCreditForTargetCardNumberShouldReturnNonZeroValue() {
        when(mockGetCardService.getCard(OYSTER_NUMBER_1)).thenReturn(getTestCardInfoResponseV2DTO14());
        assertTrue(service.getPreviousCreditForTargetCardNumber(OYSTER_NUMBER_1) > 0);
    }

    @Test
    public void getPreviousCreditForTargetCardNumberShouldReturnZeroValue() {
        when(mockGetCardService.getCard(OYSTER_NUMBER_1)).thenReturn(getTestCardInfoResponseV2DTO13());
        assertTrue(service.getPreviousCreditForTargetCardNumber(OYSTER_NUMBER_1) == 0);
    }

    @Test
    public void updatePrePayValueInCubicShouldCallCustomerServicesIfNoStationSupplied() {
        service = mock(AdHocLoadRefundPaymentServiceImpl.class);
        service.getCardService = mockGetCardService;
        service.commonRefundPaymentService = mockCommonRefundPaymentService;
        service.customerDataService = mockCustomerDataService;
        service.cardUpdateService = mockCardUpdateService;
        service.customerService = mockCustomerService;
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        when(mockCustomerDataService.findByCardNumber(anyString())).thenReturn(getTestCustomerDTO1());
        when(mockCustomerService.getPreferredStationId(CUSTOMER_ID_1)).thenReturn(LOCATION_ID_1);
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO14());
        doCallRealMethod().when(service).updatePrePayValueInCubic(cartCmdImpl);
        service.updatePrePayValueInCubic(cartCmdImpl);
        verify(mockCustomerService).getPreferredStationId(CUSTOMER_ID_1);
    }

    @Test
    public void testMakePayment() {
        doNothing().when(mockCommonRefundPaymentService).updateToPayAmount(any(CartCmdImpl.class));
        doNothing().when(mockCommonRefundPaymentService).updateOrderStatusToPaid(any(CartCmdImpl.class));
        doNothing().when(mockCommonRefundPaymentService).updateCartItemsWithOrderId(any(CartCmdImpl.class));
        doNothing().when(mockCommonRefundPaymentService).createEvent(any(CartCmdImpl.class));
        when(mockCommonRefundPaymentService.createOrderFromCart(any(CartCmdImpl.class))).thenReturn(null);
        when(mockCustomerDataService.findByCardNumber(anyString())).thenReturn(getTestCustomerDTO1());
        when(mockCustomerService.getPreferredStationId(CUSTOMER_ID_1)).thenReturn(LOCATION_ID_1);
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO14());
        when(mockCardUpdateService.requestCardUpdatePrePayValue(anyString(), anyLong(), anyInt(), anyInt(), anyString())).thenReturn(null);
        when(mockOrderDataService.createOrUpdate(any(OrderDTO.class))).then(returnsFirstArg());

        service.makePayment(CartCmdTestUtil.getTestCartCmdWithOrder());
        verify(mockCommonRefundPaymentService).updateToPayAmount(any(CartCmdImpl.class));
        verify(mockCommonRefundPaymentService).createOrderFromCart(any(CartCmdImpl.class));
        verify(mockCommonRefundPaymentService).updateOrderStatusToPaid(any(CartCmdImpl.class));
        verify(mockCommonRefundPaymentService).updateCartItemsWithOrderId(any(CartCmdImpl.class));
        verify(mockCardUpdateService).requestCardUpdatePrePayValue(null, LOCATION_ID_1, BALANCE_1, 0, CartType.PURCHASE.code());
        verify(mockOrderDataService).createOrUpdate(any(OrderDTO.class));
    }

    @Test
    public void shouldUpdateAdHocLoadSettlement() {
        when(mockCardDataService.findByCardNumber(anyString())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockAdHocLoadSettlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class)))
                .thenReturn(SettlementTestUtil.getTestAdHocLoadSettlementDTO2());
        service.updateAdHocLoadSettlement(CartCmdTestUtil.getTestCartCmdItemWithDefaultItemsAndOrderDetails(),
                CubicTestUtil.ORIGINATING_REQUEST_SEQUENCE_NUMBER);
        verify(mockCardDataService).findByCardNumber(anyString());
        verify(mockAdHocLoadSettlementDataService).createOrUpdate(any(AdHocLoadSettlementDTO.class));
    }
}

