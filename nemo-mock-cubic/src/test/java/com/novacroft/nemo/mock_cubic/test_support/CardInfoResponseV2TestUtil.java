package com.novacroft.nemo.mock_cubic.test_support;

import com.novacroft.nemo.common.domain.cubic.*;
import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoResponseV2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Utilities for Oyster card tests
 */
public final class CardInfoResponseV2TestUtil {

    public static final String PRESTIGE_ID = "1234567890";
    public static final String PHOTOCARD_NUMBER = "987";
    public static final Integer REGISTERED = 1;
    public static final String PASSENGER_TYPE = "Adult";
    public static final Integer AUTOLOAD_STATE = 1;
    public static final Integer CARD_CAPABILITY = 1;
    public static final Integer CARD_TYPE = 10;
    public static Date CCC_LOST_STOLEN_DATE_TIME = DateTestUtil.getAug22();
    public static final Integer CARD_DEPOSIT = 15;
    public static final String DISCOUNT_ENTITLEMENT_1 = "Student 1";
    public static String DISCOUNT_EXPIRY_DATE_1;
    public static final String DISCOUNT_ENTITLEMENT_2 = "Student 2";
    public static String DISCOUNT_EXPIRY_DATE_2;
    public static final String DISCOUNT_ENTITLEMENT_3 = "Student 3";
    public static String DISCOUNT_EXPIRY_DATE_3;
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

    public static final Integer HOTLIST_REASON_CODE = 654;
    public static final Integer BALANCE = 55;
    public static final Integer CURRENCY = 1;

    public static final Integer SLOT_NUMBER = 1;
    public static final String PRODUCT = "7 day Travelcard";
    public static final String ZONE = "1";
    public static final String START_DATE = DateTestUtil.AUG_19;
    public static final String EXPIRY_DATE = DateTestUtil.AUG_20;
    public static final String DISCOUNT = "Student railcard";
    public static final Integer STATE = 2;

    public static final Integer REQUEST_SEQUENCE_NUMBER = 12;
    public static final String REALTIME_FLAG = "N";
    public static final Integer PRODUCT_CODE = 23;
    public static final Integer PRODUCT_PRICE = 99;
    public static final Integer PICKUP_LOCATION = 507;

    public static final Integer PREPAY_VALUE = 50;

    public static CardInfoResponseV2 getCardInfoResponseV2() {
        return getTestCardInfoResponseV2(PRESTIGE_ID, PHOTOCARD_NUMBER, REGISTERED, PASSENGER_TYPE, AUTOLOAD_STATE,
                CARD_CAPABILITY, CARD_TYPE, CCC_LOST_STOLEN_DATE_TIME, CARD_DEPOSIT, DISCOUNT_ENTITLEMENT_1,
                DISCOUNT_EXPIRY_DATE_1, DISCOUNT_ENTITLEMENT_2, DISCOUNT_EXPIRY_DATE_2, DISCOUNT_ENTITLEMENT_3,
                DISCOUNT_EXPIRY_DATE_3, TITLE, FIRST_NAME, MIDDLE_INITIAL, LAST_NAME, DAYTIME_PHONE_NUMBER, HOUSE_NUMBER,
                HOUSE_NAME, STREET, TOWN, COUNTY, POSTCODE, EMAIL_ADDRESS, PASSWORD, HOTLIST_REASON_CODE, BALANCE, CURRENCY,
                SLOT_NUMBER, PRODUCT, ZONE, START_DATE, EXPIRY_DATE, DISCOUNT, STATE, REQUEST_SEQUENCE_NUMBER, REALTIME_FLAG,
                PRODUCT_CODE, PRODUCT_PRICE, PICKUP_LOCATION, PREPAY_VALUE);
    }

    public static CardInfoResponseV2 getTestCardInfoResponseV2(String prestigeId, String photoCardNumber, Integer registered,
                                                               String passengerType, Integer autoloadState,
                                                               Integer cardCapability, Integer cardType,
                                                               Date cccLostStolenDateTime, Integer cardDeposit,
                                                               String discountEntitlement1, String discountExpiry1,
                                                               String discountEntitlement2, String discountExpiry2,
                                                               String discountEntitlement3, String discountExpiry3,
                                                               String title, String firstName, String middleInitial,
                                                               String lastName, String dayTimePhoneNumber, String houseNumber,
                                                               String houseName, String street, String town, String county,
                                                               String postcode, String emailAddress, String password,
                                                               Integer hotListReasonCode, Integer balance, Integer currency,
                                                               Integer slotNumber, String product, String zone,
                                                               String startDate, String expiryDate, String discount,
                                                               Integer state, Integer requestSequenceNumber,
                                                               String realtimeFlag, Integer productCode, Integer productPrice,
                                                               Integer pickupLocation, Integer prepayValue) {
        CardInfoResponseV2 cardInfoResponseV2 = new CardInfoResponseV2();
        cardInfoResponseV2.setPrestigeId(prestigeId);
        cardInfoResponseV2.setPhotoCardNumber(photoCardNumber);
        cardInfoResponseV2.setRegistered(registered);
        cardInfoResponseV2.setPassengerType(passengerType);
        cardInfoResponseV2.setAutoLoadState(autoloadState);
        cardInfoResponseV2.setCardCapability(cardCapability);
        cardInfoResponseV2.setCardType(cardType);
        cardInfoResponseV2.setCccLostStolenDateTime(cccLostStolenDateTime);
        cardInfoResponseV2.setCardDeposit(cardDeposit);
        cardInfoResponseV2.setDiscountEntitlement1(discountEntitlement1);
        cardInfoResponseV2.setDiscountExpiry1(discountExpiry1);
        cardInfoResponseV2.setDiscountEntitlement2(discountEntitlement2);
        cardInfoResponseV2.setDiscountExpiry2(discountExpiry2);
        cardInfoResponseV2.setDiscountEntitlement3(discountEntitlement3);
        cardInfoResponseV2.setDiscountExpiry3(discountExpiry3);

        HotListReasons hotlistReasons = new HotListReasons();
        List<Integer> hotListReasonCodes = new ArrayList<Integer>();
        hotListReasonCodes.add(hotListReasonCode);
        hotlistReasons.setHotListReasonCodes(hotListReasonCodes);

        HolderDetails holderDetails = new HolderDetails();

        holderDetails.setTitle(title);
        holderDetails.setFirstName(firstName);
        holderDetails.setMiddleInitial(middleInitial);
        holderDetails.setLastName(lastName);
        holderDetails.setDayTimeTelephoneNumber(dayTimePhoneNumber);
        holderDetails.setHouseNumber(houseNumber);
        holderDetails.setHouseName(houseName);
        holderDetails.setStreet(street);
        holderDetails.setTown(town);
        holderDetails.setCounty(county);
        holderDetails.setPostcode(postcode);
        holderDetails.setEmailAddress(emailAddress);
        holderDetails.setPassword(password);

        PrePayValue prePayValue = new PrePayValue();
        prePayValue.setBalance(balance);
        prePayValue.setCurrency(currency);

        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        List<PrePayTicketSlot> pptSlots = new ArrayList<PrePayTicketSlot>();
        PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
        prePayTicketSlot.setSlotNumber(slotNumber);
        prePayTicketSlot.setProduct(product);
        prePayTicketSlot.setZone(zone);
        prePayTicketSlot.setStartDate(startDate);
        prePayTicketSlot.setExpiryDate(expiryDate);
        prePayTicketSlot.setPassengerType(passengerType);
        prePayTicketSlot.setDiscount(discount);
        prePayTicketSlot.setState(state);
        pptSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(pptSlots);

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

        return cardInfoResponseV2;
    }
}
