package com.novacroft.nemo.tfl.services.test_support;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.tfl.services.transfer.Card;
import com.novacroft.nemo.tfl.services.transfer.CardDiscount;
import com.novacroft.nemo.tfl.services.transfer.PendingItems;
import com.novacroft.nemo.tfl.services.transfer.PrePayValue;
import com.novacroft.nemo.tfl.services.transfer.Ticket;

public final class CardDataTestUtil {

    public static final int CARD_DEPOSIT = 5;
    private static final Integer BALANCE = 5700;

    public static final String OYSTER_NUMBER_1 = "1234567890";
    private static final String PRESTIGE_ID = "1234567890";
    private static final String PHOTOCARD_NUMBER = "987";
    private static final Integer REGISTERED = 1;
    private static final String PASSENGER_TYPE = "Adult";
    private static final Integer AUTOLOAD_STATE = 1;
    private static final Integer CARD_CAPABILITY = 1;
    private static final Integer CARD_TYPE = 10;
    private static Date CCC_LOST_STOLEN_DATE_TIME = DateTestUtil.getAug22();
    private static final String DISCOUNT_ENTITLEMENT_1 = "Student 1";
    private static String DISCOUNT_EXPIRY_DATE_1;
    
    private static final Integer HOTLIST_REASON_CODE = 654;
    
    private static final String PRODUCT = "7 day Travelcard";
    private static final String ZONE = "1";
    private static final String START_DATE = DateTestUtil.AUG_19;
    private static final String EXPIRY_DATE = DateTestUtil.AUG_20;
    private static final Integer PICKUP_LOCATION = 507;
    
    private static final Integer PREPAY_VALUE = 50;   
    
    
    public static Card getTestCard1(){
        Card card = new Card();
        card.setAutoLoadState(AUTOLOAD_STATE);
        card.setCardCapability(CARD_CAPABILITY);
        card.setAutoTopUpEnabled(false);
        card.setCardDeposit(CARD_DEPOSIT);
        card.setCardType(CARD_TYPE);
        card.setCccLostStolenDateTime(CCC_LOST_STOLEN_DATE_TIME);
        card.setHotListReason(HOTLIST_REASON_CODE);
        card.setPassengerType(PASSENGER_TYPE);
        card.setPhotoCardNumber(PHOTOCARD_NUMBER);
        card.setPrestigeId(PRESTIGE_ID);
        card.setRegistered(REGISTERED);
        List<PrePayValue> prePayValues = new ArrayList<PrePayValue>();
        PrePayValue prePayValue = new PrePayValue();
        prePayValue.setBalance(BALANCE);
        prePayValue.setPickupLocation(PICKUP_LOCATION);
        prePayValue.setPrePayValue(PREPAY_VALUE);
        prePayValues.add(prePayValue);
        card.setPrePayValue(prePayValue);
        List<CardDiscount> discounts = new ArrayList<>();
        CardDiscount discount = new CardDiscount();
        discount.setEntitlement(DISCOUNT_ENTITLEMENT_1);
        discount.setExpiry(DISCOUNT_EXPIRY_DATE_1);
        discount.setId(1);
        discounts.add(discount);
        card.setDiscounts(discounts);
        List<Ticket> tickets = new ArrayList<>();
        Ticket ticket = new Ticket();
        ticket.setExpiryDate(EXPIRY_DATE);
        ticket.setProduct(PRODUCT);
        ticket.setStartDate(START_DATE);
        ticket.setZone(ZONE);
        tickets.add(ticket );
        card.setTickets(tickets);
        PendingItems pendingItems = new PendingItems();
        pendingItems.setPrePayValues(prePayValues);
        pendingItems.setTickets(tickets);
        card.setPendingItems(pendingItems);
        return card;
    }

    private CardDataTestUtil() {
    }
    
    
}
