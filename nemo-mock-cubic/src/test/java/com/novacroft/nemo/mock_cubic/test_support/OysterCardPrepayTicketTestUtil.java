package com.novacroft.nemo.mock_cubic.test_support;

import java.util.Date;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPrepayTicketDTO;
import com.novacroft.nemo.test_support.DateTestUtil;

/**
 * Utilities for Oyster card tests
 */
public final class OysterCardPrepayTicketTestUtil {
    
    public static final String START_DATE_STRING_1 = DateTestUtil.AUG_19;
    public static final String EXPIRY_DATE_STRING_1 = DateTestUtil.AUG_20;
    public static final String START_DATE_STRING_2 = DateTestUtil.AUG_21;
    public static final String EXPIRY_DATE_STRING_2 = DateTestUtil.AUG_22;
    public static final String START_DATE_STRING_3 = DateTestUtil.JAN_1;
    public static final String EXPIRY_DATE_STRING_3 = DateTestUtil.JAN_31;
    
    public static final String PRESTIGE_ID = "1234567890";
    
    public static final Long SLOT_NUMBER_1 = 1L;
    public static final String PRODUCT_1 = "Product type 1";
    public static final String ZONE_1 = "1";
    public static Date START_DATE_1;
    public static Date EXPIRY_DATE_1;
    public static final String PASSENGER_TYPE_1 = "Passenger type 1";
    public static final String DISCOUNT_1 = "Discount type 1";
    public static final Long STATE_1 = 1L;
    
    public static final Long SLOT_NUMBER_2 = 2L;
    public static final String PRODUCT_2 = "Product type 2";
    public static final String ZONE_2 = "1";
    public static Date START_DATE_2;
    public static Date EXPIRY_DATE_2;
    public static final String PASSENGER_TYPE_2 = "Passenger type 1";
    public static final String DISCOUNT_2 = "Discount type 1";
    public static final Long STATE_2 = 1L;
    
    public static final Long SLOT_NUMBER_3 = 3L;
    public static final String PRODUCT_3 = "Product type 3";
    public static final String ZONE_3 = "1";
    public static Date START_DATE_3;
    public static Date EXPIRY_DATE_3;
    public static final String PASSENGER_TYPE_3 = "Passenger type 1";
    public static final String DISCOUNT_3 = "Discount type 1";
    public static final Long STATE_3 = 1L;
    
    
    static {
        START_DATE_1 = DateUtil.parse(START_DATE_STRING_1);
        EXPIRY_DATE_1 = DateUtil.parse(EXPIRY_DATE_STRING_1);
        START_DATE_2 = DateUtil.parse(START_DATE_STRING_2);
        EXPIRY_DATE_2 = DateUtil.parse(EXPIRY_DATE_STRING_2);
        START_DATE_3 = DateUtil.parse(START_DATE_STRING_3);
        EXPIRY_DATE_3 = DateUtil.parse(EXPIRY_DATE_STRING_3);
    }
       
    public static OysterCardPrepayTicketDTO getTestOysterCardPrepayTicketDTO1() 
    {
        return getTestOysterCardPrepayTicketDTO(PRESTIGE_ID,        
                                                SLOT_NUMBER_1,
                                                PRODUCT_1,
                                                ZONE_1,
                                                START_DATE_1,
                                                EXPIRY_DATE_1,
                                                PASSENGER_TYPE_1,
                                                DISCOUNT_1,
                                                STATE_1,        
                                                SLOT_NUMBER_2,
                                                PRODUCT_2,
                                                ZONE_2,
                                                START_DATE_2,
                                                EXPIRY_DATE_2,
                                                PASSENGER_TYPE_2,
                                                DISCOUNT_2,
                                                STATE_2,        
                                                SLOT_NUMBER_3,
                                                PRODUCT_3,
                                                ZONE_3,
                                                START_DATE_3,
                                                EXPIRY_DATE_3,
                                                PASSENGER_TYPE_3,
                                                DISCOUNT_3,
                                                STATE_3);
    }
    
    public static OysterCardPrepayTicketDTO getTestOysterCardPrepayTicketDTO2() 
    {
        return getTestOysterCardPrepayTicketDTO(PRESTIGE_ID,        
                                                SLOT_NUMBER_1,
                                                PRODUCT_1,
                                                ZONE_1,
                                                new Date(),
                                                EXPIRY_DATE_1,
                                                PASSENGER_TYPE_1,
                                                DISCOUNT_1,
                                                STATE_1,        
                                                SLOT_NUMBER_2,
                                                PRODUCT_2,
                                                ZONE_2,
                                                START_DATE_2,
                                                EXPIRY_DATE_2,
                                                PASSENGER_TYPE_2,
                                                DISCOUNT_2,
                                                STATE_2,        
                                                SLOT_NUMBER_3,
                                                PRODUCT_3,
                                                ZONE_3,
                                                START_DATE_3,
                                                EXPIRY_DATE_3,
                                                PASSENGER_TYPE_3,
                                                DISCOUNT_3,
                                                STATE_3);
    }
    
    public static OysterCardPrepayTicketDTO getTestOysterCardPrepayTicketDTO3() 
    {
        return getTestOysterCardPrepayTicketDTO(PRESTIGE_ID,        
                                                SLOT_NUMBER_1,
                                                PRODUCT_1,
                                                ZONE_1,
                                                new Date(),
                                                EXPIRY_DATE_1,
                                                PASSENGER_TYPE_1,
                                                DISCOUNT_1,
                                                STATE_1,        
                                                SLOT_NUMBER_2,
                                                PRODUCT_2,
                                                ZONE_2,
                                                new Date(),
                                                EXPIRY_DATE_2,
                                                PASSENGER_TYPE_2,
                                                DISCOUNT_2,
                                                STATE_2,        
                                                SLOT_NUMBER_3,
                                                PRODUCT_3,
                                                ZONE_3,
                                                START_DATE_3,
                                                EXPIRY_DATE_3,
                                                PASSENGER_TYPE_3,
                                                DISCOUNT_3,
                                                STATE_3);
    }

    public static OysterCardPrepayTicketDTO getTestOysterCardPrepayTicketDTO(String prestigeId,
                                                                             Long slotNumber1,
                                                                             String product1,
                                                                             String zone1,
                                                                             Date startDate1,
                                                                             Date expiryDate1,
                                                                             String passengerType1,                                                                        
                                                                             String discount1,
                                                                             Long state1,
                                                                             Long slotNumber2,
                                                                             String product2,
                                                                             String zone2,
                                                                             Date startDate2,
                                                                             Date expiryDate2,
                                                                             String passengerType2,                                                                        
                                                                             String discount2,
                                                                             Long state2,
                                                                             Long slotNumber3,
                                                                             String product3,
                                                                             String zone3,
                                                                             Date startDate3,
                                                                             Date expiryDate3,
                                                                             String passengerType3,                                                                        
                                                                             String discount3,
                                                                             Long state3)
    {
        OysterCardPrepayTicketDTO oysterCardPrepayTicketDTO = new OysterCardPrepayTicketDTO();
        oysterCardPrepayTicketDTO.setPrestigeId(prestigeId);
        oysterCardPrepayTicketDTO.setSlotNumber1(slotNumber1);
        oysterCardPrepayTicketDTO.setProduct1(product1);
        oysterCardPrepayTicketDTO.setZone1(zone1);
        oysterCardPrepayTicketDTO.setStartDate1(startDate1);
        oysterCardPrepayTicketDTO.setExpiryDate1(expiryDate1);
        oysterCardPrepayTicketDTO.setPassengerType1(passengerType1);
        oysterCardPrepayTicketDTO.setDiscount1(discount1);
        oysterCardPrepayTicketDTO.setSlotNumber2(slotNumber2);
        oysterCardPrepayTicketDTO.setProduct2(product2);
        oysterCardPrepayTicketDTO.setZone2(zone2);
        oysterCardPrepayTicketDTO.setStartDate2(startDate2);
        oysterCardPrepayTicketDTO.setExpiryDate2(expiryDate2);
        oysterCardPrepayTicketDTO.setPassengerType2(passengerType2);
        oysterCardPrepayTicketDTO.setDiscount2(discount2);
        oysterCardPrepayTicketDTO.setSlotNumber3(slotNumber3);
        oysterCardPrepayTicketDTO.setProduct3(product3);
        oysterCardPrepayTicketDTO.setZone3(zone3);
        oysterCardPrepayTicketDTO.setStartDate3(startDate3);
        oysterCardPrepayTicketDTO.setExpiryDate3(expiryDate3);
        oysterCardPrepayTicketDTO.setPassengerType3(passengerType3);
        oysterCardPrepayTicketDTO.setDiscount3(discount3);
        return oysterCardPrepayTicketDTO;
    }
}
