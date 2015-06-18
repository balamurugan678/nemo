package com.novacroft.nemo.tfl.common.constant;

public enum CancelOrderResult {
    SUCCESS(ContentCode.CANCEL_ORDER_SUCCESS.codeStem()), SUCCESS_AWAITING_REFUND_PAYMENT(ContentCode.CANCEL_ORDER_AWAITING_REFUND_PAYMENT.codeStem()), UNABLE_TO_CANCEL_ORDER(
                    ContentCode.CANCEL_ORDER_GENERAL.errorCode()), UNABLE_TO_CANCEL_ORDER_AFTER_CUT_OFF_TIME(
                    ContentCode.CANCEL_ORDER_AFTER_CUT_OFF_TIME.errorCode()), UNABLE_TO_CREATE_OR_UPDATE_REFUND_ORDER(
                    ContentCode.CANCEL_ORDER_CREATE_OR_UPDATE_REFUND_ORDER.errorCode()), UNABLE_TO_CREATE_OR_UPDATE_REFUND_SETTLEMENT(
                    ContentCode.CANCEL_ORDER_CREATE_OR_UPDATE_REFUND_SETTLEMENT.errorCode());
    
    private String contentCode;
    
    private CancelOrderResult(String contentCode) {
        this.contentCode = contentCode;
    }

    public String contentCode() {
        return contentCode;
    }
}
