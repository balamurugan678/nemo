package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.PaymentDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceReplyDTO;

/**
 * Payment service specification.
 */
public interface PaymentService {
    CartCmdImpl createOrderAndSettlementsFromManagedAutoTopUp(CartDTO cartDTO, CartCmdImpl cmd);

    CartCmdImpl createOrderAndSettlementsFromCart(CartDTO cartDTO, CartCmdImpl cmd);

    @Deprecated
    CartCmdImpl processPaymentGatewayReply(CartDTO cartDTO, CartCmdImpl cmd);

    CartCmdImpl processPaymentGatewayReply(CartCmdImpl cmd);

    PaymentDetailsCmdImpl populatePaymentDetails(Long customerId);

    PaymentCardSettlementDTO updatePaymentCardSettlementWithPaymentGatewayResponse(
            PaymentCardSettlementDTO paymentCardSettlementDTO, CyberSourceReplyDTO cyberSourceReplyDTO);

    OrderDTO updateOrderStatusWithPaymentGatewayResponse(OrderDTO orderDTO, CyberSourceReplyDTO cyberSourceReplyDTO);

    Long getDefaultPaymentCardId(CartCmdImpl cmd);
}
