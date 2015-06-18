package com.novacroft.nemo.tfl.services.application_service;

import com.novacroft.nemo.tfl.services.transfer.Cart;
import com.novacroft.nemo.tfl.services.transfer.CheckoutResult;
import com.novacroft.nemo.tfl.services.transfer.ListResult;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;

public interface CartExternalService {

    Cart createCart(Long cardId);

    Cart retrieveCart(Long cartId);
    
    Cart updateCart(Cart cart, Long cartId);

    ListResult<Cart> getCartListByCustomerId(Long customerId);

    WebServiceResult deleteCart(Long externalCartId, Long externalCustomerId);

    CheckoutResult beginCheckout(Long externalCartId, Long stationId);

    CheckoutResult authoriseCheckoutPayment(Long externalCartId, Long externalOrderId, Long externalPaymentCardSettlementId, String paymentAuthorisationReference);

    CheckoutResult updatePaymentCardSettlementStatusToFailed(Long externalCartId, Long externalOrderId, Long externalPaymentCardSettlementId);

    CheckoutResult completeCheckout(Long externalCartId, Long stationId, Long externalOrderId, Long externalPaymentCardSettlementId);

}
