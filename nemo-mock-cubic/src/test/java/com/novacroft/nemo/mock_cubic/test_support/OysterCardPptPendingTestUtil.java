package com.novacroft.nemo.mock_cubic.test_support;

import java.util.Date;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPptPendingDTO;
import com.novacroft.nemo.test_support.DateTestUtil;

/**
 * Utilities for Oyster card tests
 */
public final class OysterCardPptPendingTestUtil {
    
    public static final String START_DATE_STRING_1 = DateTestUtil.AUG_19;
    public static final String EXPIRY_DATE_STRING_1 = DateTestUtil.AUG_20;
    public static final String START_DATE_STRING_2 = DateTestUtil.AUG_21;
    public static final String EXPIRY_DATE_STRING_2 = DateTestUtil.AUG_22;
    public static final String START_DATE_STRING_3 = DateTestUtil.JAN_1;
    public static final String EXPIRY_DATE_STRING_3 = DateTestUtil.JAN_31;
    
    public static final String PRESTIGE_ID = "1234567890";
    
    public static final Long REQUEST_SEQUENCE_NUMBER_1 = 12L;
    public static final String REALTIME_FLAG_1 = "N";
    public static final Long PRODUCT_CODE_1 = 15L;
    public static final Long PRODUCT_PRICE_1 = 55L;
    public static final Long CURRENCY_1 = 1L;
    public static Date START_DATE_1;
    public static Date EXPIRY_DATE_1;
    public static final Long PICKUP_LOCATION_1 = 765L;
    
    public static final Long REQUEST_SEQUENCE_NUMBER_2 = 13L;
    public static final String REALTIME_FLAG_2 = "N";
    public static final Long PRODUCT_CODE_2 = 25L;
    public static final Long PRODUCT_PRICE_2 = 65L;
    public static final Long CURRENCY_2 = 2L;
    public static Date START_DATE_2;
    public static Date EXPIRY_DATE_2;
    public static final Long PICKUP_LOCATION_2 = 615L;
    
    public static final Long REQUEST_SEQUENCE_NUMBER_3 = 14L;
    public static final String REALTIME_FLAG_3 = "N";
    public static final Long PRODUCT_CODE_3 = 75L;
    public static final Long PRODUCT_PRICE_3 = 80L;
    public static final Long CURRENCY_3 = 3L;
    public static Date START_DATE_3;
    public static Date EXPIRY_DATE_3;
    public static final Long PICKUP_LOCATION_3 = 715L;
    
    static {
        START_DATE_1 = DateUtil.parse(START_DATE_STRING_1);
        EXPIRY_DATE_1 = DateUtil.parse(EXPIRY_DATE_STRING_1);
        START_DATE_2 = DateUtil.parse(START_DATE_STRING_2);
        EXPIRY_DATE_2 = DateUtil.parse(EXPIRY_DATE_STRING_2);
        START_DATE_3 = DateUtil.parse(START_DATE_STRING_3);
        EXPIRY_DATE_3 = DateUtil.parse(EXPIRY_DATE_STRING_3);
    }
       
    public static OysterCardPptPendingDTO getTestOysterCardPptPendingDTO1() 
    {
        return getTestOysterCardPptPendingDTO(PRESTIGE_ID, 
                                              REQUEST_SEQUENCE_NUMBER_1,
                                              REALTIME_FLAG_1,
                                              PRODUCT_CODE_1,
                                              PRODUCT_PRICE_1,
                                              CURRENCY_1,
                                              START_DATE_1,
                                              EXPIRY_DATE_1,
                                              PICKUP_LOCATION_1,
                                              REQUEST_SEQUENCE_NUMBER_2,
                                              REALTIME_FLAG_2,
                                              PRODUCT_CODE_2,
                                              PRODUCT_PRICE_2,
                                              CURRENCY_2,
                                              START_DATE_2,
                                              EXPIRY_DATE_2,
                                              PICKUP_LOCATION_2,
                                              REQUEST_SEQUENCE_NUMBER_3,
                                              REALTIME_FLAG_3,
                                              PRODUCT_CODE_3,
                                              PRODUCT_PRICE_3,
                                              CURRENCY_3,
                                              START_DATE_3,
                                              EXPIRY_DATE_3,
                                              PICKUP_LOCATION_3);
    }

    public static OysterCardPptPendingDTO getTestOysterCardPptPendingDTO(String prestigeId,
                                                                         Long requestSequenceNumber1,
                                                                         String realtimeFlag1,
                                                                         Long productCode1,
                                                                         Long productPrice1,
                                                                         Long currency1,
                                                                         Date startDate1,
                                                                         Date expiryDate1,
                                                                         Long pickupLocation1,                                                                        
                                                                         Long requestSequenceNumber2,
                                                                         String realtimeFlag2,
                                                                         Long productCode2,
                                                                         Long productPrice2,
                                                                         Long currency2,
                                                                         Date startDate2,
                                                                         Date expiryDate2,
                                                                         Long pickupLocation2,                                                                        
                                                                         Long requestSequenceNumber3,
                                                                         String realtimeFlag3,
                                                                         Long productCode3,
                                                                         Long productPrice3,
                                                                         Long currency3,
                                                                         Date startDate3,
                                                                         Date expiryDate3,
                                                                         Long pickupLocation3) 
    {
        OysterCardPptPendingDTO oysterCardPptPendingDTO = new OysterCardPptPendingDTO();
        oysterCardPptPendingDTO.setPrestigeId(prestigeId);
        oysterCardPptPendingDTO.setRequestSequenceNumber1(requestSequenceNumber1);
        oysterCardPptPendingDTO.setRealTimeFlag1(realtimeFlag1);
        oysterCardPptPendingDTO.setProductCode1(productCode1);
        oysterCardPptPendingDTO.setProductPrice1(productPrice1);
        oysterCardPptPendingDTO.setCurrency1(currency1);
        oysterCardPptPendingDTO.setStartDate1(startDate1);
        oysterCardPptPendingDTO.setExpiryDate1(expiryDate1);
        oysterCardPptPendingDTO.setPickUpLocation1(pickupLocation1);
        oysterCardPptPendingDTO.setRequestSequenceNumber2(requestSequenceNumber2);
        oysterCardPptPendingDTO.setRealTimeFlag2(realtimeFlag2);
        oysterCardPptPendingDTO.setProductCode2(productCode2);
        oysterCardPptPendingDTO.setProductPrice2(productPrice2);
        oysterCardPptPendingDTO.setCurrency2(currency2);
        oysterCardPptPendingDTO.setStartDate2(startDate2);
        oysterCardPptPendingDTO.setExpiryDate2(expiryDate2);
        oysterCardPptPendingDTO.setPickUpLocation2(pickupLocation2);
        oysterCardPptPendingDTO.setRequestSequenceNumber3(requestSequenceNumber3);
        oysterCardPptPendingDTO.setRealTimeFlag3(realtimeFlag3);
        oysterCardPptPendingDTO.setProductCode3(productCode3);
        oysterCardPptPendingDTO.setProductPrice3(productPrice3);
        oysterCardPptPendingDTO.setCurrency3(currency3);
        oysterCardPptPendingDTO.setStartDate3(startDate3);
        oysterCardPptPendingDTO.setExpiryDate3(expiryDate3);
        oysterCardPptPendingDTO.setPickUpLocation3(pickupLocation3);
        return oysterCardPptPendingDTO;
    }
}
