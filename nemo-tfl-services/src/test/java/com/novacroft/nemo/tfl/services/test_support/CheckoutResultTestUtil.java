package com.novacroft.nemo.tfl.services.test_support;

import com.novacroft.nemo.tfl.services.constant.WebServiceResultAttribute;
import com.novacroft.nemo.tfl.services.transfer.CheckoutResult;

public class CheckoutResultTestUtil {

    public static final Long ORDER_ID = 1000L;
    public static final Long PAYMENT_SETTLEMENT_ID = 2000L;

    private CheckoutResultTestUtil() {
    }

    public static CheckoutResult createSuccessfulResult(Long id) {
        CheckoutResult result = new CheckoutResult();
        result.setId(id);
        result.setOrderId(ORDER_ID);
        result.setPaymentSettlementId(PAYMENT_SETTLEMENT_ID);
        result.setResult(WebServiceResultAttribute.SUCCESS.name());
        result.setMessage(WebServiceResultAttribute.SUCCESS.contentCode());
        return result;
    }

    public static CheckoutResult createFailedResult(Long id) {
        CheckoutResult result = new CheckoutResult();
        result.setId(id);
        result.setOrderId(null);
        result.setPaymentSettlementId(null);
        result.setResult(WebServiceResultAttribute.FAILURE.name());
        result.setMessage(WebServiceResultAttribute.FAILURE.contentCode());
        return result;
    }


}
