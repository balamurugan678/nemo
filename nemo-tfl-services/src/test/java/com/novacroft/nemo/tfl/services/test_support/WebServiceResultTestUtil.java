package com.novacroft.nemo.tfl.services.test_support;

import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.tfl.common.constant.CancelOrderResult;
import com.novacroft.nemo.tfl.services.constant.WebServiceResultAttribute;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;

public class WebServiceResultTestUtil {

    public static final String AWAITING_PAYMENT_MESSAGE = "Awaiting Refund Payment";

    public static final Long ID_1 = 1L;
    public static final Long ORIGINAL_ID_1 = 1L;

    private WebServiceResultTestUtil() {
    }

    public static WebServiceResult createSuccessfulResult(Long id) {
        WebServiceResult result = new WebServiceResult();
        result.setId(id);
        result.setResult(WebServiceResultAttribute.SUCCESS.name());
        result.setMessage(WebServiceResultAttribute.SUCCESS.contentCode());
        return result;
    }

    public static WebServiceResult createFailedResult(Long id) {
        WebServiceResult result = new WebServiceResult();
        result.setId(id);
        result.setResult(WebServiceResultAttribute.FAILURE.name());
        result.setMessage(WebServiceResultAttribute.FAILURE.contentCode());
        return result;
    }

    public static WebServiceResult getWebServiceResult(Long id, Long originalId, String result, String message) {
        WebServiceResult wsResult = new WebServiceResult();
        wsResult.setId(id);
        wsResult.setOriginalId(originalId);
        wsResult.setResult(result);
        wsResult.setMessage(message);
        return wsResult;
    }

    public static WebServiceResult getCancelOrder1() {
        return getWebServiceResult(OrderTestUtil.ORDER_ID_2, OrderTestUtil.ORDER_ID, CancelOrderResult.SUCCESS_AWAITING_REFUND_PAYMENT.name(),
                        AWAITING_PAYMENT_MESSAGE);

    }

    public static WebServiceResult getCompletedCancelOrder() {
        return getWebServiceResult(OrderTestUtil.ORDER_ID_2, null, CancelOrderResult.SUCCESS.name(), WebServiceResultAttribute.SUCCESS.contentCode());
    }

}
