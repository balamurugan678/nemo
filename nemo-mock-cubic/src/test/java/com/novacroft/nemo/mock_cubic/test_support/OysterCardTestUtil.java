package com.novacroft.nemo.mock_cubic.test_support;

import java.util.Date;

import com.novacroft.nemo.mock_cubic.transfer.OysterCardDTO;
import com.novacroft.nemo.test_support.DateTestUtil;

/**
 * Utilities for Oyster card tests
 */
public final class OysterCardTestUtil {
    
    public static final String PRESTIGE_ID = "1234567890";
    public static final String PHOTOCARD_NUMBER = "987";
    public static final Long REGISTERED = 1L;
    public static final String PASSENGER_TYPE = "Adult";
    public static final Long AUTOLOAD_STATE = 1L;
    public static final Long CARD_CAPABILITY = 1L;
    public static final Long CARD_TYPE = 10L;
    public static Date CCC_LOST_STOLEN_DATE_TIME = DateTestUtil.get1Jan();
    public static final Long CARD_DEPOSIT = 15L;
    public static final String DISCOUNT_ENTITLEMENT_1 = "Student 1";
    public static Date DISCOUNT_EXPIRY_DATE_1  = DateTestUtil.get31Jan();
    public static final String DISCOUNT_ENTITLEMENT_2 = "Student 2";
    public static Date DISCOUNT_EXPIRY_DATE_2 = DateTestUtil.getApr03();
    public static final String DISCOUNT_ENTITLEMENT_3 = "Student 3";
    public static Date DISCOUNT_EXPIRY_DATE_3 = DateTestUtil.getAug19();
    public static final String TITLE = "Mr";
    public static final String FIRST_NAME = "Charlie";
    public static final String MIDDLE_INITIAL = "F";
    public static final String LAST_NAME = "Farnsbarn";
    public static final String DAYTIME_PHONE_NUMBER = "012321542356";
    public static final String HOUSE_NUMBER = "12";
    public static final String HOUSE_NAME = "The House";
    public static final String STREET = "The Street";
    public static final String TOWN = "The Town";
    public static final String COUNTY = "The County";
    public static final String POSTCODE = "NN6 4ES";
    public static final String EMAIL_ADDRESS = "charlie.farnsbarn@test.co.uk";
    public static final String PASSWORD = "charlie123";
           
    public static OysterCardDTO getTestOysterCardDTO1() {
        return getTestOysterCardDTO(PRESTIGE_ID, 
                                    PHOTOCARD_NUMBER, 
                                    REGISTERED, 
                                    PASSENGER_TYPE, 
                                    AUTOLOAD_STATE, 
                                    CARD_CAPABILITY,
                                    CARD_TYPE, 
                                    CCC_LOST_STOLEN_DATE_TIME, 
                                    CARD_DEPOSIT, 
                                    DISCOUNT_ENTITLEMENT_1, 
                                    DISCOUNT_EXPIRY_DATE_1,
                                    DISCOUNT_ENTITLEMENT_2, 
                                    DISCOUNT_EXPIRY_DATE_2, 
                                    DISCOUNT_ENTITLEMENT_3, 
                                    DISCOUNT_EXPIRY_DATE_3,
                                    TITLE, 
                                    FIRST_NAME, 
                                    MIDDLE_INITIAL, 
                                    LAST_NAME, 
                                    DAYTIME_PHONE_NUMBER, 
                                    HOUSE_NUMBER, 
                                    HOUSE_NAME,
                                    STREET, 
                                    TOWN, 
                                    COUNTY, 
                                    POSTCODE, 
                                    EMAIL_ADDRESS, 
                                    PASSWORD);
    }

    public static OysterCardDTO getTestOysterCardDTO(String prestigeId,
                                                     String photoCardNumber,
                                                     Long registered,
                                                     String passengerType,
                                                     Long autoloadState,
                                                     Long cardCapability,
                                                     Long cardType,
                                                     Date cccLostStolenDateTime,
                                                     Long cardDeposit,
                                                     String discountEntitlement1,
                                                     Date discountExpiry1,
                                                     String discountEntitlement2,
                                                     Date discountExpiry2,
                                                     String discountEntitlement3,
                                                     Date discountExpiry3,
                                                     String title,
                                                     String firstName,
                                                     String middleInitial,
                                                     String lastName,
                                                     String dayTimePhoneNumber,
                                                     String houseNumber,
                                                     String houseName,
                                                     String street,
                                                     String town,
                                                     String county,
                                                     String postcode,
                                                     String emailAddress,
                                                     String password) 
    {
        OysterCardDTO oysterCardDTO = new OysterCardDTO();
        oysterCardDTO.setPrestigeId(prestigeId);
        oysterCardDTO.setPhotoCardNumber(photoCardNumber);
        oysterCardDTO.setRegistered(registered);
        oysterCardDTO.setPassengerType(passengerType);
        oysterCardDTO.setAutoloadState(autoloadState);
        oysterCardDTO.setCardCapability(cardCapability);
        oysterCardDTO.setCardType(cardType);
        oysterCardDTO.setCccLostStolenDateTime(cccLostStolenDateTime);
        oysterCardDTO.setCardDeposit(cardDeposit);
        oysterCardDTO.setDiscountEntitlement1(discountEntitlement1);
        oysterCardDTO.setDiscountExpiry1(discountExpiry1);
        oysterCardDTO.setDiscountEntitlement2(discountEntitlement2);
        oysterCardDTO.setDiscountExpiry2(discountExpiry2);
        oysterCardDTO.setDiscountEntitlement3(discountEntitlement3);
        oysterCardDTO.setDiscountExpiry3(discountExpiry3);
        oysterCardDTO.setTitle(title);
        oysterCardDTO.setFirstName(firstName);
        oysterCardDTO.setMiddleInitial(middleInitial);
        oysterCardDTO.setLastName(lastName);
        oysterCardDTO.setDayTimePhoneNumber(dayTimePhoneNumber);
        oysterCardDTO.setHouseNumber(houseNumber);
        oysterCardDTO.setHouseName(houseName);
        oysterCardDTO.setStreet(street);
        oysterCardDTO.setTown(town);
        oysterCardDTO.setCounty(county);
        oysterCardDTO.setPostcode(postcode);
        oysterCardDTO.setEmailAddress(emailAddress);
        oysterCardDTO.setPassword(password);
        return oysterCardDTO;
    }
}
