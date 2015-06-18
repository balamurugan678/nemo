package com.novacroft.nemo.tfl.services.test_support;

import com.novacroft.nemo.test_support.LocationTestUtil;
import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.test_support.SettlementTestUtil;
import com.novacroft.nemo.tfl.services.transfer.CheckoutRequest;

public class CheckoutRequestTestUtil {

    public static final String PAYMENT_AUTHORISATION_REF = "112233445566778899"; 
    
    private CheckoutRequestTestUtil() {
    }

    public static CheckoutRequest getNewOrderCmdForCheckoutStart(){
        CheckoutRequest request = new CheckoutRequest();
        return request;
    }


    public static CheckoutRequest getNewOrderCmdForCheckoutAuthorisePayment(){
        CheckoutRequest request = new CheckoutRequest();
        request.setOrderId(OrderTestUtil.ORDER_ID);
        request.setPaymentCardSettlementId(SettlementTestUtil.SETTLEMENT_ID_1);
        request.setPaymentAuthoristationReference(PAYMENT_AUTHORISATION_REF);
        return request;
    }
    

    public static CheckoutRequest getNewOrderCmdForCheckoutAuthoriseUpdatePaymentRecordToFailed(){
        CheckoutRequest request = new CheckoutRequest();
        request.setOrderId(OrderTestUtil.ORDER_ID);
        request.setPaymentCardSettlementId(SettlementTestUtil.SETTLEMENT_ID_1);
        return request;
    }

    public static CheckoutRequest getNewOrderCmdForCheckoutComplete(){
        CheckoutRequest request = new CheckoutRequest();
        request.setOrderId(OrderTestUtil.ORDER_ID);
        request.setPaymentCardSettlementId(SettlementTestUtil.SETTLEMENT_ID_1);
        return request;
    }
    
    public static CheckoutRequest getExistingCardCmdForCheckoutStart(){
        CheckoutRequest request = new CheckoutRequest();
        request.setStationId(LocationTestUtil.LOCATION_ID_1);
        return request;
    }

}
