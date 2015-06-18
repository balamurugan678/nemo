package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import org.joda.time.DateTime;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.domain.Order;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.RefundCeilingRuleLimitTally;
import com.novacroft.nemo.tfl.common.transfer.RefundOrderItemDTO;

/**
 * Order data service specification
 */
public interface OrderDataService extends BaseDataService<Order, OrderDTO> {
    OrderDTO create(OrderDTO orderDTO);
    void flush();

    OrderDTO findByOrderNumber(Long orderNumber);

    List<OrderDTO> findByCustomerId(Long customerId);

    List<OrderDTO> findByCustomerIdAndDuration(Long currentId, DateTime thresholdTime, DateTime now);

    List<OrderDTO> findByAddressIdAndDuration(Long addressId, DateTime thresholdTime, DateTime now);

    List<OrderDTO> findByExample(Order exampleEntity);

    List<OrderDTO> findByRefundedCardNumberPriceAndDate(String cardNumber, Long price, DateTime date);

    List<OrderDTO> findByCustomerCardNumberPriceAndDate(String cardNumber, Long account, Long price, DateTime date);

    List<RefundOrderItemDTO> findAllRefundsForCustomer(Long customerId);

    RefundOrderItemDTO findRefundDetailForItemAndCustomer(Long refundId, Long customerId);

    List<OrderDTO> findAllRefunds();

    List<OrderDTO> findRefundByBACSReferenceNumber(Long bacsReference);

    List<OrderDTO> findRefundByChequeSerialNumber(Long chequeSerialNumber);

    List<OrderDTO> findRefundsForCustomer(Long id);

    List<OrderDTO> getOrdersByCaseNumber(String caseNumber);

    OrderDTO findByIdAndCustomerId(Long id, Long customerId);

    Long getExternalIdFromInternalId(Long internalId);

    List<RefundCeilingRuleLimitTally> findAllRefundsForCustomerInPast12Months(Long customerId, String refundType);

    List<RefundCeilingRuleLimitTally> findAllGoodwillsForCustomerInPast12Months(Long customerId);
    
    RefundCeilingRuleLimitTally findAllOverlapRefundForCustomerInPast12Months(Long customerId);

    Long getNumberOfOrdersInQueue(OrderStatus orderStatus);

    List<OrderDTO> findByFulfilmentQueue(String fulfilmentQueue);
    
}
