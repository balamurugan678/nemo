package com.novacroft.nemo.mock_cubic.test_support;

import com.novacroft.nemo.common.domain.cubic.PrePayTicketSlot;
import com.novacroft.nemo.test_support.DateTestUtil;

/**
 * Utilities for Oyster card tests
 */
public final class PrepayTicketSlotTestUtil {
    
    public static final String PASSENGER_TYPE = "Adult";
    public static final Integer SLOT_NUMBER = 1;
    public static final String PRODUCT = "7 day Travelcard";
    public static final String ZONE = "1";
    public static final String START_DATE = DateTestUtil.AUG_21;
    public static final String EXPIRY_DATE = DateTestUtil.AUG_22;
    public static final String DISCOUNT = "Student railcard";
    public static final Integer STATE = 2;
              
    public static PrePayTicketSlot getPrePayTicketSlotTest() {
        return getTestPrePayTicketSlot(PASSENGER_TYPE, 
                                       SLOT_NUMBER,
                                       PRODUCT,
                                       ZONE,
                                       START_DATE,
                                       EXPIRY_DATE,
                                       DISCOUNT,
                                       STATE);
    }

    public static PrePayTicketSlot getTestPrePayTicketSlot(String passengerType,
                                                           Integer slotNumber,
                                                           String product,
                                                           String zone,
                                                           String startDate,
                                                           String expiryDate,
                                                           String discount,
                                                           Integer state) 
    {
        PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
        prePayTicketSlot.setSlotNumber(slotNumber);
        prePayTicketSlot.setProduct(product);
        prePayTicketSlot.setZone(zone);
        prePayTicketSlot.setStartDate(startDate);
        prePayTicketSlot.setExpiryDate(expiryDate);
        prePayTicketSlot.setPassengerType(passengerType);
        prePayTicketSlot.setDiscount(discount);
        prePayTicketSlot.setState(state);
                
        return prePayTicketSlot;
    }
}
