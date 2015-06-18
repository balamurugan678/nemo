package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import antlr.collections.List;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.common.utils.DateUtilTest;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CubicTestUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.test_support.SettlementTestUtil;
import com.novacroft.nemo.tfl.common.application_service.cubic.CardRemoveUpdateService;
import com.novacroft.nemo.tfl.common.constant.CancelOrderResult;
import com.novacroft.nemo.tfl.common.data_service.AdHocLoadSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.CancelOrderResultDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;

public class CancelOrderServiceImplTest {
    private CancelOrderServiceImpl service;
    private OrderDataService orderDataService;
    private CustomerDataService customerDataService;
    private CardRemoveUpdateService cardRemoveUpdateService;
    private CardDataService cardDataService;
    private AdHocLoadSettlementDataService settlementDataService;
    private PaymentCardSettlementDataService paymentCardSettlementDataService;

    @Before
    public void setUp() throws Exception {
        service = mock(CancelOrderServiceImpl.class);

        orderDataService = mock(OrderDataService.class);
        customerDataService = mock(CustomerDataService.class);
        cardRemoveUpdateService = mock(CardRemoveUpdateService.class);
        cardDataService = mock(CardDataService.class);
        settlementDataService = mock(AdHocLoadSettlementDataService.class);
        paymentCardSettlementDataService = mock(PaymentCardSettlementDataService.class);

        service.orderDataService = orderDataService;
        service.customerDataService = customerDataService;
        service.cardRemoveUpdateService = cardRemoveUpdateService;
        service.cardDataService = cardDataService;
        service.adhocLoadSettlementDataService = settlementDataService;
        service.paymentCardSettlementDataService = paymentCardSettlementDataService;

        when(customerDataService.getInternalIdFromExternalId(anyLong())).thenReturn(CustomerTestUtil.CUSTOMER_ID_1);
        when(orderDataService.getInternalIdFromExternalId(anyLong())).thenReturn(OrderTestUtil.ORDER_ID);
        when(settlementDataService.findByOrderId(anyLong())).thenReturn(SettlementTestUtil.getTestCancelOrderSettlementDTOs());
        when(orderDataService.getExternalIdFromInternalId(anyLong())).thenReturn(OrderTestUtil.EXTERNAL_ID);
        when(service.getExistingCardNumber(anyLong())).thenCallRealMethod();
        when(service.processExistingCard(anyString(), any(OrderDTO.class), anyList())).thenCallRealMethod();
        when(service.isBeforeCutOffTime(any(Date.class))).thenCallRealMethod();
        when(service.removePendingUpdates(anyString(), anyList())).thenCallRealMethod();
        when(service.removePendingUpdate(anyString(), anyInt())).thenCallRealMethod();
        when(service.createRefundOrderAndSettlement(any(OrderDTO.class), anyList())).thenCallRealMethod();
        when(service.getPaymentCardSettlementFromSettlementDTOs(anyLong())).thenCallRealMethod();
        when(service.updateRefundOrderAndSettlement(any(OrderDTO.class))).thenCallRealMethod();
        when(service.updateRefundOrder(any(OrderDTO.class))).thenCallRealMethod();
        when(service.createRefundSettlement(any(PaymentCardSettlementDTO.class), anyLong())).thenCallRealMethod();
        when(service.createOrUpdateRefundSettlement(any(PaymentCardSettlementDTO.class))).thenCallRealMethod();
        when(service.getCardIdFromOrderItems(anyList())).thenCallRealMethod();
        when(service.hasAdhocLoadRequestBeenRegisteredInCubic(any(Integer.class))).thenCallRealMethod();

    }

    @SuppressWarnings("unchecked")
    @Test
    public void externalCancelOrderShouldReturnSuccessfulOnExisitingCard() {
        when(settlementDataService.findByOrderId(anyLong())).thenReturn(SettlementTestUtil.getTestCancelOrderSettlementDTOs());
        OrderDTO orderDto = OrderTestUtil.getOrderDTOWithAdminFeeAndShippingItem();
        orderDto.setOrderDate(new Date());
        orderDto.setTotalAmount(OrderTestUtil.TOTAL_AMOUNT);
        OrderDTO refundOrderDTO = OrderTestUtil.getOrderDTOWithItems();
        refundOrderDTO.setId(OrderTestUtil.ORDER_ID);
        when(orderDataService.findByIdAndCustomerId(anyLong(), anyLong())).thenReturn(orderDto);
        when(cardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(cardRemoveUpdateService.removePendingUpdate(anyString(), anyLong())).thenReturn(CubicTestUtil.getTestResponseDTO1());
        when(orderDataService.createOrUpdate(any(OrderDTO.class))).thenReturn(refundOrderDTO);

        when(settlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class))).thenReturn(SettlementTestUtil.getTestAdHocLoadSettlementDTO1());
        when(paymentCardSettlementDataService.findByOrderId(anyLong())).thenReturn(SettlementTestUtil.getTestPaymentCardSettlementDTOs());
        when(settlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class))).thenReturn(SettlementTestUtil.getTestAdHocLoadSettlementDTO1());
        when(paymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenReturn(
                        SettlementTestUtil.getTestPaymentCardSettlementDTO1());

        when(service.cancelOrderWithExternalOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.cancelOrderWithOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.getContent(anyString())).thenReturn(null);
        when(service.createRefundAdHocLoadSettlements((java.util.List<AdHocLoadSettlementDTO>) any(List.class), any(OrderDTO.class)))
                        .thenCallRealMethod();
        when(service.createRefundAdHocLoadSettlement(any(AdHocLoadSettlementDTO.class), any(OrderDTO.class))).thenCallRealMethod();

        CancelOrderResultDTO resultDTO = service.cancelOrderWithExternalOrderIdAndCustomerId(CustomerTestUtil.EXTERNAL_CUSTOMER_ID,
                        OrderTestUtil.EXTERNAL_ID);
        assertNotNull(resultDTO);
        assertEquals(CancelOrderResult.SUCCESS_AWAITING_REFUND_PAYMENT.name(), resultDTO.getResult());

        verify(customerDataService, times(1)).getInternalIdFromExternalId(anyLong());
        verify(orderDataService, times(1)).getInternalIdFromExternalId(anyLong());
        verify(settlementDataService, times(1)).findByOrderId(anyLong());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void externalCancelOrderShouldReturnSuccessfulOnNewCard() {

        OrderDTO orderDto = OrderTestUtil.getOrderDTOWithAdminFeeAndShippingItem();
        orderDto.setOrderDate(new Date());
        orderDto.setTotalAmount(OrderTestUtil.TOTAL_AMOUNT);
        OrderDTO refundOrderDTO = OrderTestUtil.getOrderDTOWithItems();
        refundOrderDTO.setId(OrderTestUtil.ORDER_ID);
        when(orderDataService.findByIdAndCustomerId(anyLong(), anyLong())).thenReturn(orderDto);
        when(cardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTOWithoutCardNumber());
        when(orderDataService.createOrUpdate((OrderDTO) any())).thenReturn(refundOrderDTO);
        when(orderDataService.createOrUpdate(any(OrderDTO.class))).thenReturn(refundOrderDTO);
        when(paymentCardSettlementDataService.findByOrderId(anyLong())).thenReturn(SettlementTestUtil.getTestPaymentCardSettlementDTOs());
        when(settlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class))).thenReturn(SettlementTestUtil.getTestAdHocLoadSettlementDTO1());
        when(paymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenReturn(
                        SettlementTestUtil.getTestPaymentCardSettlementDTO1());

        when(service.createRefundAdHocLoadSettlements((java.util.List<AdHocLoadSettlementDTO>) any(List.class), any(OrderDTO.class)))
                        .thenCallRealMethod();
        when(service.createRefundAdHocLoadSettlement(any(AdHocLoadSettlementDTO.class), any(OrderDTO.class))).thenCallRealMethod();
        when(service.cancelOrderWithExternalOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.cancelOrderWithOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.getContent(anyString())).thenReturn(null);

        CancelOrderResultDTO resultDTO = service.cancelOrderWithExternalOrderIdAndCustomerId(CustomerTestUtil.EXTERNAL_CUSTOMER_ID,
                        OrderTestUtil.EXTERNAL_ID);
        assertNotNull(resultDTO);
        assertEquals(CancelOrderResult.SUCCESS_AWAITING_REFUND_PAYMENT.name(), resultDTO.getResult());

        verify(customerDataService, times(1)).getInternalIdFromExternalId(anyLong());
        verify(orderDataService, times(1)).getInternalIdFromExternalId(anyLong());
        verify(settlementDataService, times(1)).findByOrderId(anyLong());
        verify(cardRemoveUpdateService, never()).removePendingUpdate(anyString());
    }

    @Test
    public void externalCancelOrderShouldReturnSuccessfulForMultipleSettlements() {
        when(settlementDataService.findByOrderId(anyLong())).thenReturn(SettlementTestUtil.getTestCancelOrderSettlementDTOWithFailedCubicUpdate());
        OrderDTO orderDto = OrderTestUtil.getOrderDTOWithAdminFeeAndShippingItem();
        orderDto.setOrderDate(new Date());
        orderDto.setTotalAmount(OrderTestUtil.TOTAL_AMOUNT);
        OrderDTO refundOrderDTO = OrderTestUtil.getOrderDTOWithItems();
        refundOrderDTO.setId(OrderTestUtil.ORDER_ID);
        when(orderDataService.findByIdAndCustomerId(anyLong(), anyLong())).thenReturn(orderDto);
        when(cardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(cardRemoveUpdateService.removePendingUpdate(anyString(), anyLong())).thenReturn(CubicTestUtil.getTestResponseDTO1());
        when(orderDataService.createOrUpdate(any(OrderDTO.class))).thenReturn(refundOrderDTO);

        when(settlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class))).thenReturn(SettlementTestUtil.getTestAdHocLoadSettlementDTO1());
        when(paymentCardSettlementDataService.findByOrderId(anyLong())).thenReturn(SettlementTestUtil.getTestPaymentCardSettlementDTOs());
        when(paymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenReturn(
                        SettlementTestUtil.getTestPaymentCardSettlementDTO1());

        when(service.cancelOrderWithExternalOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.cancelOrderWithOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.getContent(anyString())).thenReturn(null);
        when(service.createRefundAdHocLoadSettlements((java.util.List<AdHocLoadSettlementDTO>) any(List.class), any(OrderDTO.class)))
                        .thenCallRealMethod();
        when(service.createRefundAdHocLoadSettlement(any(AdHocLoadSettlementDTO.class), any(OrderDTO.class))).thenCallRealMethod();

        CancelOrderResultDTO resultDTO = service.cancelOrderWithExternalOrderIdAndCustomerId(CustomerTestUtil.EXTERNAL_CUSTOMER_ID,
                        OrderTestUtil.EXTERNAL_ID);

        assertNotNull(resultDTO);
        assertEquals(CancelOrderResult.SUCCESS_AWAITING_REFUND_PAYMENT.name(), resultDTO.getResult());
        verify(settlementDataService, times(1)).findByOrderId(anyLong());
        verify(cardRemoveUpdateService, times(1)).removePendingUpdate(anyString(), anyLong());
        verify(settlementDataService, times(1)).createOrUpdate(any(AdHocLoadSettlementDTO.class));
    }

    @Test
    public void externalCancelOrderShouldReturnErrorIfAfterCutOffTimes() {
        OrderDTO orderDto = OrderTestUtil.getOrderDTOWithItems();
        orderDto.setTotalAmount(OrderTestUtil.TOTAL_AMOUNT);
        Date date = parse(formatDate(new Date()));
        date.setTime(date.getTime() - (DateUtilTest.HOUR_IN_MILLIS + 60000));
        orderDto.setOrderDate(date);
        when(orderDataService.findByIdAndCustomerId(anyLong(), anyLong())).thenReturn(orderDto);
        when(cardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());

        when(service.cancelOrderWithExternalOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.cancelOrderWithOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.getContent(anyString())).thenReturn(null);

        CancelOrderResultDTO resultDTO = service.cancelOrderWithExternalOrderIdAndCustomerId(CustomerTestUtil.EXTERNAL_CUSTOMER_ID,
                        OrderTestUtil.EXTERNAL_ID);
        assertNotNull(resultDTO);
        assertEquals(CancelOrderResult.UNABLE_TO_CANCEL_ORDER_AFTER_CUT_OFF_TIME.name(), resultDTO.getResult());

        verify(customerDataService, times(1)).getInternalIdFromExternalId(anyLong());
        verify(orderDataService, times(1)).getInternalIdFromExternalId(anyLong());
        verify(cardRemoveUpdateService, never()).removePendingUpdate(anyString());
    }

    @Test
    public void externalCancelOrderShouldReturnErrorIfErrorInCubicCall() {
        when(settlementDataService.findByOrderId(anyLong())).thenReturn(SettlementTestUtil.getTestCancelOrderSettlementDTOs());
        OrderDTO orderDto = OrderTestUtil.getOrderDTOWithItems();
        orderDto.setOrderDate(new Date());
        orderDto.setTotalAmount(OrderTestUtil.TOTAL_AMOUNT);
        OrderDTO refundOrderDTO = OrderTestUtil.getOrderDTOWithAdminFeeAndShippingItem();
        refundOrderDTO.setId(OrderTestUtil.ORDER_ID);
        when(orderDataService.findByIdAndCustomerId(anyLong(), anyLong())).thenReturn(orderDto);
        when(cardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(cardRemoveUpdateService.removePendingUpdate(anyString(), anyLong())).thenReturn(CubicTestUtil.getTestErrorResponseDTO1());

        when(service.cancelOrderWithExternalOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.cancelOrderWithOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.getContent(anyString())).thenReturn(null);

        CancelOrderResultDTO resultDTO = service.cancelOrderWithExternalOrderIdAndCustomerId(CustomerTestUtil.EXTERNAL_CUSTOMER_ID,
                        OrderTestUtil.EXTERNAL_ID);
        assertNotNull(resultDTO);
        assertEquals(CancelOrderResult.UNABLE_TO_CANCEL_ORDER.name(), resultDTO.getResult());

        verify(cardRemoveUpdateService, times(3)).removePendingUpdate(anyString(), anyLong());
        verify(orderDataService, never()).createOrUpdate((OrderDTO) any());
    }

    @Test(expected = ApplicationServiceException.class)
    public void externalCancelOrderShouldReturnErrorIfThereAreMultipleCardIdsOnOrder() {
        when(settlementDataService.findByOrderId(anyLong())).thenReturn(SettlementTestUtil.getTestCancelOrderSettlementDTOs());
        when(orderDataService.findByIdAndCustomerId(anyLong(), anyLong())).thenReturn(OrderTestUtil.getOrderDTOWithDifferentCardIdOnItems());

        when(service.cancelOrderWithExternalOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.cancelOrderWithOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.getContent(anyString())).thenReturn(null);

        service.cancelOrderWithExternalOrderIdAndCustomerId(CustomerTestUtil.EXTERNAL_CUSTOMER_ID, OrderTestUtil.EXTERNAL_ID);

    }

    @Test
    public void externalCancelOrderShouldReturnErrorIfRefundOrderIsNull() {

        OrderDTO orderDto = OrderTestUtil.getOrderDTOWithItems();
        orderDto.setOrderDate(new Date());
        orderDto.setTotalAmount(OrderTestUtil.TOTAL_AMOUNT);
        when(orderDataService.findByIdAndCustomerId(anyLong(), anyLong())).thenReturn(orderDto);
        when(cardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(cardRemoveUpdateService.removePendingUpdate(anyString(), anyLong())).thenReturn(CubicTestUtil.getTestResponseDTO1());
        when(orderDataService.createOrUpdate((OrderDTO) any())).thenReturn(null);

        when(service.cancelOrderWithExternalOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.cancelOrderWithOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.getContent(anyString())).thenReturn(null);

        CancelOrderResultDTO resultDTO = service.cancelOrderWithExternalOrderIdAndCustomerId(CustomerTestUtil.EXTERNAL_CUSTOMER_ID,
                        OrderTestUtil.EXTERNAL_ID);
        assertNotNull(resultDTO);
        assertEquals(CancelOrderResult.UNABLE_TO_CREATE_OR_UPDATE_REFUND_ORDER.name(), resultDTO.getResult());

        verify(customerDataService, times(1)).getInternalIdFromExternalId(anyLong());
        verify(orderDataService, times(1)).getInternalIdFromExternalId(anyLong());
        verify(settlementDataService, times(1)).findByOrderId(anyLong());
    }

    @Test
    public void externalCancelOrderShouldReturnErrorIfRSettlementDTOsAreNull() {

        OrderDTO orderDto = OrderTestUtil.getOrderDTOWithItems();
        orderDto.setOrderDate(new Date());
        orderDto.setTotalAmount(OrderTestUtil.TOTAL_AMOUNT);
        OrderDTO refundOrderDTO = OrderTestUtil.getOrderDTOWithAdminFeeAndShippingItem();
        refundOrderDTO.setId(OrderTestUtil.ORDER_ID);
        when(orderDataService.findByIdAndCustomerId(anyLong(), anyLong())).thenReturn(orderDto);
        when(cardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(cardRemoveUpdateService.removePendingUpdate(anyString(), anyLong())).thenReturn(CubicTestUtil.getTestResponseDTO1());
        when(orderDataService.createOrUpdate(any(OrderDTO.class))).thenReturn(refundOrderDTO);
        when(paymentCardSettlementDataService.findByOrderId(anyLong())).thenReturn(SettlementTestUtil.getTestPaymentCardSettlementDTOs());

        when(service.cancelOrderWithExternalOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.cancelOrderWithOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.getContent(anyString())).thenReturn(null);

        CancelOrderResultDTO resultDTO = service.cancelOrderWithExternalOrderIdAndCustomerId(CustomerTestUtil.EXTERNAL_CUSTOMER_ID,
                        OrderTestUtil.EXTERNAL_ID);
        assertNotNull(resultDTO);
        assertEquals(CancelOrderResult.UNABLE_TO_CREATE_OR_UPDATE_REFUND_SETTLEMENT.name(), resultDTO.getResult());

        verify(customerDataService, times(1)).getInternalIdFromExternalId(anyLong());
        verify(orderDataService, times(1)).getInternalIdFromExternalId(anyLong());
        verify(settlementDataService, times(1)).findByOrderId(anyLong());
    }

    @Test
    public void externalCancelOrderShouldReturnSuccessIfRefundAdHocLoadSettlementDTOsAreNull() {
        OrderDTO orderDto = OrderTestUtil.getOrderDTOWithAdminFeeAndShippingItem();
        orderDto.setOrderDate(new Date());
        orderDto.setTotalAmount(OrderTestUtil.TOTAL_AMOUNT);
        OrderDTO refundOrderDTO = OrderTestUtil.getOrderDTOWithItems();
        refundOrderDTO.setId(OrderTestUtil.ORDER_ID);
        when(orderDataService.findByIdAndCustomerId(anyLong(), anyLong())).thenReturn(orderDto);
        when(cardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTOWithoutCardNumber());
        when(orderDataService.createOrUpdate((OrderDTO) any())).thenReturn(refundOrderDTO);
        when(orderDataService.createOrUpdate(any(OrderDTO.class))).thenReturn(refundOrderDTO);
        when(paymentCardSettlementDataService.findByOrderId(anyLong())).thenReturn(SettlementTestUtil.getTestPaymentCardSettlementDTOs());
        when(settlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class))).thenReturn(SettlementTestUtil.getTestAdHocLoadSettlementDTO1());
        when(paymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenReturn(
                        SettlementTestUtil.getTestPaymentCardSettlementDTO1());

        when(service.createRefundAdHocLoadSettlements((java.util.List<AdHocLoadSettlementDTO>) any(List.class), any(OrderDTO.class)))
                        .thenReturn(null);
        when(service.createRefundAdHocLoadSettlement(any(AdHocLoadSettlementDTO.class), any(OrderDTO.class))).thenCallRealMethod();
        when(service.cancelOrderWithExternalOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.cancelOrderWithOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.getContent(anyString())).thenReturn(null);

        CancelOrderResultDTO resultDTO = service.cancelOrderWithExternalOrderIdAndCustomerId(CustomerTestUtil.EXTERNAL_CUSTOMER_ID,
                        OrderTestUtil.EXTERNAL_ID);
        assertNotNull(resultDTO);
        assertEquals(CancelOrderResult.SUCCESS_AWAITING_REFUND_PAYMENT.name(), resultDTO.getResult());

        verify(customerDataService, times(1)).getInternalIdFromExternalId(anyLong());
        verify(orderDataService, times(1)).getInternalIdFromExternalId(anyLong());
        verify(settlementDataService, times(1)).findByOrderId(anyLong());
        verify(cardRemoveUpdateService, never()).removePendingUpdate(anyString());
    }

    @Test
    public void completeCancelOrderWithExternalOrderIdAndCustomerId() {
        OrderDTO orderDto = OrderTestUtil.getOrderDTOWithItems();
        orderDto.setOrderDate(new Date());
        orderDto.setTotalAmount(OrderTestUtil.TOTAL_AMOUNT);
        OrderDTO refundOrderDTO = OrderTestUtil.getOrderDTOWithAdminFeeAndShippingItem();
        refundOrderDTO.setId(OrderTestUtil.ORDER_ID);
        when(orderDataService.findByIdAndCustomerId(anyLong(), anyLong())).thenReturn(orderDto);
        when(orderDataService.createOrUpdate(any(OrderDTO.class))).thenReturn(refundOrderDTO);
        when(settlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class))).thenReturn(SettlementTestUtil.getTestAdHocLoadSettlementDTO1());
        when(paymentCardSettlementDataService.findByOrderId(anyLong())).thenReturn(SettlementTestUtil.getTestPaymentCardSettlementDTOs());
        when(settlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class))).thenReturn(SettlementTestUtil.getTestAdHocLoadSettlementDTO1());
        when(paymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenReturn(
                        SettlementTestUtil.getTestPaymentCardSettlementDTO1());
        when(service.completeCancelOrderWithOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.completeCancelOrderWithExternalOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.getContent(anyString())).thenReturn(null);
        CancelOrderResultDTO completeResult = service.completeCancelOrderWithExternalOrderIdAndCustomerId(CustomerTestUtil.EXTERNAL_CUSTOMER_ID,
                        OrderTestUtil.EXTERNAL_ID);
        assertNotNull(completeResult);
        assertEquals(CancelOrderResult.SUCCESS.name(), completeResult.getResult());
    }

    @Test
    public void completeCancelOrderWillReturnErrorIfRefundOrderIsNull() {
        OrderDTO orderDto = OrderTestUtil.getOrderDTOWithItems();
        orderDto.setOrderDate(new Date());
        orderDto.setTotalAmount(OrderTestUtil.TOTAL_AMOUNT);
        when(orderDataService.findByIdAndCustomerId(anyLong(), anyLong())).thenReturn(orderDto);
        when(orderDataService.createOrUpdate(any(OrderDTO.class))).thenReturn(null);
        when(settlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class))).thenReturn(SettlementTestUtil.getTestAdHocLoadSettlementDTO1());
        when(paymentCardSettlementDataService.findByOrderId(anyLong())).thenReturn(SettlementTestUtil.getTestPaymentCardSettlementDTOs());
        when(settlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class))).thenReturn(SettlementTestUtil.getTestAdHocLoadSettlementDTO1());
        when(paymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenReturn(
                        SettlementTestUtil.getTestPaymentCardSettlementDTO1());
        when(service.completeCancelOrderWithOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.completeCancelOrderWithExternalOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.getContent(anyString())).thenReturn(null);
        CancelOrderResultDTO completeResult = service.completeCancelOrderWithOrderIdAndCustomerId(CustomerTestUtil.CUSTOMER_ID_1,
                        OrderTestUtil.ORDER_ID);
        assertNotNull(completeResult);
        assertEquals(CancelOrderResult.UNABLE_TO_CREATE_OR_UPDATE_REFUND_ORDER.name(), completeResult.getResult());
    }

    @Test
    public void completeCancelOrderWithOrderIdAndCustomerId() {
        OrderDTO orderDto = OrderTestUtil.getOrderDTOWithItems();
        orderDto.setOrderDate(new Date());
        orderDto.setTotalAmount(OrderTestUtil.TOTAL_AMOUNT);
        OrderDTO refundOrderDTO = OrderTestUtil.getOrderDTOWithAdminFeeAndShippingItem();
        refundOrderDTO.setId(OrderTestUtil.ORDER_ID);
        when(orderDataService.findByIdAndCustomerId(anyLong(), anyLong())).thenReturn(orderDto);
        when(orderDataService.createOrUpdate(any(OrderDTO.class))).thenReturn(refundOrderDTO);
        when(settlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class))).thenReturn(SettlementTestUtil.getTestAdHocLoadSettlementDTO1());
        when(paymentCardSettlementDataService.findByOrderId(anyLong())).thenReturn(SettlementTestUtil.getTestPaymentCardSettlementDTOs());
        when(settlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class))).thenReturn(SettlementTestUtil.getTestAdHocLoadSettlementDTO1());
        when(paymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenReturn(
                        SettlementTestUtil.getTestPaymentCardSettlementDTO1());
        when(service.completeCancelOrderWithOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.completeCancelOrderWithExternalOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.getContent(anyString())).thenReturn(null);
        CancelOrderResultDTO completeResult = service.completeCancelOrderWithOrderIdAndCustomerId(CustomerTestUtil.CUSTOMER_ID_1,
                        OrderTestUtil.ORDER_ID);
        assertNotNull(completeResult);
        assertEquals(CancelOrderResult.SUCCESS.name(), completeResult.getResult());
    }

    @Test
    public void completeCancelOrderWillReturnErrorIfSettlementDTOsAreNull() {
        OrderDTO orderDto = OrderTestUtil.getOrderDTOWithItems();
        orderDto.setOrderDate(new Date());
        orderDto.setTotalAmount(OrderTestUtil.TOTAL_AMOUNT);
        OrderDTO refundOrderDTO = OrderTestUtil.getOrderDTOWithAdminFeeAndShippingItem();
        refundOrderDTO.setId(OrderTestUtil.ORDER_ID);
        when(orderDataService.findByIdAndCustomerId(anyLong(), anyLong())).thenReturn(orderDto);
        when(orderDataService.createOrUpdate(any(OrderDTO.class))).thenReturn(refundOrderDTO);
        when(settlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class))).thenReturn(null);
        when(paymentCardSettlementDataService.findByOrderId(anyLong())).thenReturn(SettlementTestUtil.getTestPaymentCardSettlementDTOs());
        when(paymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenReturn(null);
        when(service.completeCancelOrderWithOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.completeCancelOrderWithExternalOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(service.getContent(anyString())).thenReturn(null);

        CancelOrderResultDTO completeResult = service.completeCancelOrderWithOrderIdAndCustomerId(CustomerTestUtil.CUSTOMER_ID_1,
                        OrderTestUtil.ORDER_ID);
        assertNotNull(completeResult);
        assertEquals(CancelOrderResult.UNABLE_TO_CREATE_OR_UPDATE_REFUND_SETTLEMENT.name(), completeResult.getResult());
    }

    @Test(expected = AssertionError.class)
    public void cancelOrderWithOrderIdAndCustomerIdAssertionErrorIfCustomerIdIsNull() {
        when(service.cancelOrderWithOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        service.cancelOrderWithOrderIdAndCustomerId(null, OrderTestUtil.ORDER_ID);
    }

    @Test(expected = AssertionError.class)
    public void cancelOrderWithOrderIdAndCustomerIdAssertionErrorIfOrderIdIsNull() {
        when(service.cancelOrderWithOrderIdAndCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        service.cancelOrderWithOrderIdAndCustomerId(CustomerTestUtil.CUSTOMER_ID_1, null);
    }

}
