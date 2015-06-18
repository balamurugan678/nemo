package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.ManagePaymentCardCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.PaymentCardCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;

/**
 * Payment Card service
 */
public interface PaymentCardService {
    void createPaymentCardOnTokenRequest(CartCmdImpl cmd);

    ManagePaymentCardCmdImpl getPaymentCards(Long customerId);

    ManagePaymentCardCmdImpl updatePaymentCards(Long customerId, ManagePaymentCardCmdImpl cmd);

    Boolean isCardInUse(Long paymentCardId);

    void linkPaymentCardToCardOnAutoLoadOrder(OrderDTO orderDTO);

    void updateSettlementWithPaymentCard(OrderDTO orderDTO, Long paymentCardId);

    PaymentCardCmdImpl getPaymentCard(Long paymentCardId);

    void updatePaymentCard(PaymentCardCmdImpl paymentCardCmd);

    void deletePaymentCard(PaymentCardCmdImpl paymentCardCmd);
    
}
