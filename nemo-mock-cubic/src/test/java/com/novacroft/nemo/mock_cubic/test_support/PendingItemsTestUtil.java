package com.novacroft.nemo.mock_cubic.test_support;

import java.util.ArrayList;
import java.util.List;

import com.novacroft.nemo.common.domain.cubic.PendingItems;
import com.novacroft.nemo.common.domain.cubic.PrePayTicket;
import com.novacroft.nemo.common.domain.cubic.PrePayValue;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPendingDTO;
import com.novacroft.nemo.test_support.DateTestUtil;

/**
 * Utilities for Oyster card tests
 */
public final class PendingItemsTestUtil {
    
    public static final Integer BALANCE = 55;
    public static final Integer CURRENCY = 1;
    
    public static final String PRODUCT = "7 day Travelcard";
    public static final String START_DATE = DateTestUtil.AUG_21;
    public static final String EXPIRY_DATE = DateTestUtil.AUG_22;
    
    public static final Integer REQUEST_SEQUENCE_NUMBER = 12;
    public static final String REALTIME_FLAG = "N";
    public static final Integer PRODUCT_CODE = 23;
    public static final Integer PRODUCT_PRICE = 99;
    public static final Integer PICKUP_LOCATION = 507;
    public static final String PRESTIGE_ID = "1002589633215";
    
    public static final Integer PREPAY_VALUE = 50;    
       
    public static PendingItems getPendingItemsTest() {
        return getTestPendingItems(BALANCE,
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

    public static PendingItems getTestPendingItems(Integer balance,
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
        PendingItems pendingItems = new PendingItems();
        
        List<PrePayValue> ppvs = new ArrayList<PrePayValue>();
        PrePayValue ppv = new PrePayValue();
        ppv.setRequestSequenceNumber(requestSequenceNumber);
        ppv.setRealTimeFlag(realtimeFlag);
        ppv.setPrePayValue(prepayValue);
        ppv.setCurrency(currency);
        ppv.setPickupLocation(pickupLocation);
        ppvs.add(ppv);
        pendingItems.setPpvs(ppvs);
        
        List<PrePayTicket> ppts = new ArrayList<PrePayTicket>();
        PrePayTicket ppt = new PrePayTicket();
        ppt.setRequestSequenceNumber(requestSequenceNumber);
        ppt.setRealTimeFlag(realtimeFlag);
        ppt.setProductCode(productCode);
        ppt.setProductPrice(productPrice);
        ppt.setCurrency(currency);
        ppt.setStartDate(startDate);
        ppt.setExpiryDate(expiryDate);
        ppt.setPickupLocation(pickupLocation);
        ppts.add(ppt);
        pendingItems.setPpts(ppts);              
        
        return pendingItems;
    }
    
    public static OysterCardPendingDTO getTestOysterCardPendingDTOWithPrePayValue(){
        return getTestOysterCardPendingDTO(PRESTIGE_ID, REQUEST_SEQUENCE_NUMBER, PREPAY_VALUE.longValue(), null, null);
    }
    
    public static OysterCardPendingDTO getTestOysterCardPendingDTOWithPrePayTicket(){
        return getTestOysterCardPendingDTO(PRESTIGE_ID, REQUEST_SEQUENCE_NUMBER, null, PRODUCT_CODE.longValue(), PRODUCT_PRICE.longValue());
    }
    
    public static OysterCardPendingDTO getTestOysterCardPendingDTO(String prestigeId, Integer requestSequenceNumber, Long prePayValue, Long productCode, Long productPrice){
        OysterCardPendingDTO dto = new OysterCardPendingDTO();
        dto.setPrestigeId(prestigeId);
        dto.setRequestSequenceNumber(requestSequenceNumber.longValue());
        dto.setPrePayValue(prePayValue);
        dto.setProductCode(productCode);
        dto.setProductPrice(productPrice);
        return dto;
    }
}
