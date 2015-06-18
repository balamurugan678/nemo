package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.constant.CancelOrderResult;
import com.novacroft.nemo.tfl.common.transfer.CancelOrderResultDTO;

public class CancelOrderTestUtil {
    public static Long ID_1 = 1L;
    public static Long ORIGNAL_ID_1 = 2L;
    public static String AWAITING_PAYMENT_RESULT = CancelOrderResult.SUCCESS_AWAITING_REFUND_PAYMENT.name();
    public static String AWAITING_PAYMENT_MESSAGE = "Awaiiting for refun payment";
    
    public static CancelOrderResultDTO getCancelOrderResultDTO(Long id, Long originalId, String result, String message){
        return new CancelOrderResultDTO(id, originalId, result, message);
    }

    public static CancelOrderResultDTO getCancelOrderResultDTO1() {
        return getCancelOrderResultDTO(ID_1, ORIGNAL_ID_1, AWAITING_PAYMENT_RESULT, AWAITING_PAYMENT_MESSAGE);
    }

    public static CancelOrderResultDTO getCancelOrderResultDTO2() {
        return getCancelOrderResultDTO(ID_1, ORIGNAL_ID_1, CancelOrderResult.SUCCESS.name(), null);
    }
                    
}
