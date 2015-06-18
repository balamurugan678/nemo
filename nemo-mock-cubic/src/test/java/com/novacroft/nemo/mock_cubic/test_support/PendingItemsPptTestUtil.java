package com.novacroft.nemo.mock_cubic.test_support;

import com.novacroft.nemo.common.domain.cubic.PrePayTicket;
import com.novacroft.nemo.test_support.DateTestUtil;

/**
 * Utilities for Oyster card tests
 */
public final class PendingItemsPptTestUtil {
    
    public static final Integer BALANCE = 55;
    public static final Integer CURRENCY = 1;
    
    public static final String PRODUCT = "7 day Travelcard";
    public static final String START_DATE = DateTestUtil.AUG_19;
    public static final String EXPIRY_DATE = DateTestUtil.AUG_21;
    
    public static final Integer REQUEST_SEQUENCE_NUMBER = 12;
    public static final String REALTIME_FLAG = "N";
    public static final Integer PRODUCT_CODE = 23;
    public static final Integer PRODUCT_PRICE = 99;
    public static final Integer PICKUP_LOCATION = 507;
    
    public static final Integer PREPAY_VALUE = 50;    
       
    public static PrePayTicket getPendingItemsPptTest() {
        return getTestPendingItemsPpt(BALANCE,
                                      CURRENCY,        
                                      PRODUCT,
                                      START_DATE,
                                      EXPIRY_DATE,
                                      REQUEST_SEQUENCE_NUMBER,
                                      REALTIME_FLAG,
                                      PRODUCT_CODE,
                                      PRODUCT_PRICE,
                                      PICKUP_LOCATION,        
                                      PREPAY_VALUE);
    }

    public static PrePayTicket getTestPendingItemsPpt(Integer balance,
                                                      Integer currency,
                                                      String product,
                                                      String startDate,
                                                      String expiryDate,
                                                      Integer requestSequenceNumber,
                                                      String realtimeFlag,
                                                      Integer productCode,
                                                      Integer productPrice,
                                                      Integer pickupLocation,
                                                      Integer prepayValue) 
    {
        PrePayTicket ppt = new PrePayTicket();
        ppt.setRequestSequenceNumber(requestSequenceNumber);
        ppt.setRealTimeFlag(realtimeFlag);
        ppt.setProductCode(productCode);
        ppt.setProductPrice(productPrice);
        ppt.setCurrency(currency);
        ppt.setStartDate(startDate);
        ppt.setExpiryDate(expiryDate);
        ppt.setPickupLocation(pickupLocation);
        
        return ppt;
    }
}
