package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.TicketType;
import com.novacroft.nemo.tfl.common.transfer.ZoneIdDescriptionDTO;

/**
 * Utilities for shopping basket tests
 */
public final class CartItemTestUtil {
    public static final String PAY_AS_YOU_GO_NAME_1 = "Pay as you go credit";
    public static final Integer PAY_AS_YOU_GO_TICKET_PRICE_DEFAULT = 500;
    public static final Integer PAY_AS_YOU_GO_TICKET_PRICE_1 = 1000;
    public static final Integer PAY_AS_YOU_GO_TICKET_PRICE_2 = 10000;
    public static final Integer PAY_AS_YOU_GO_TICKET_PRICE_3 = -10000;
    public static final Integer PAY_AS_YOU_GO_TICKET_PRICE_4 = 220;
    public static final Integer PAY_AS_YOU_GO_AUTO_TOP_UP_AMT_1 = 2000;
    public static final String PAY_AS_YOU_Go_REMINDER_DATE_1 = "None";
    public static final String PAY_AS_YOU_Go_START_DATE_1 = "15/11/2013";
    public static final String PAY_AS_YOU_Go_END_DATE_1 = "21/11/2013";

    public static final String REMINDER_DATE_1 = "4 days prior";
    public static final String ANNUAL_BUS_PASS_1 = "Annual Bus Pass - All London";
    public static final String ANNUAL_START_DATE_1 = "11/11/2013";
    public static final String ANNUAL_END_DATE_1 = "10/12/2014";
    public static final String ANNUAL_START_DATE_2 = "15/12/2013";
    public static final String ANNUAL_END_DATE_2 = "14/12/2014";
    public static final Integer ANNUAL_PRICE_1 = 78400;

    public static final String SEVEN_DAY_TRAVEL_CARDS_1 = "7 Day Travelcard Zones from 1 to 10";
    public static final String TRAVEL_START_DATE_1 = "11/11/2013";
    public static final String TRAVEL_END_DATE_1 = "17/11/2013";
    public static final Integer TRAVEL_PRICE_1 = 7900;
    public static final Integer TRAVEL_START_ZONE_1 = 1;
    public static final Integer TRAVEL_END_ZONE_1 = 5;
    public static final String SEVEN_DAY_TRAVEL_CARDS_2 = "7 Day Travelcard Zones from 2 to 6";
    public static final String TRAVEL_START_DATE_2 = "15/11/2013";
    public static final String TRAVEL_END_DATE_2 = "21/11/2013";
    public static final Integer TRAVEL_PRICE_2 = 3800;
    public static final Integer TRAVEL_START_ZONE_2 = 4;
    public static final Integer TRAVEL_END_ZONE_2 = 11;
    public static final String TRAVEL_START_DATE_3 = "18/11/2013";
    public static final String TRAVEL_END_DATE_3 = "24/11/2013";
    public static final Integer TRAVEL_START_ZONE_3 = 5;
    public static final Integer TRAVEL_END_ZONE_3 = 9;
    public static final String TRAVEL_START_DATE_5 = "03/10/2014";
    public static final String TRAVEL_END_DATE_5 = "09/10/2014";
    public static final Integer TRAVEL_PRICE_5 = 7900;
    public static final Integer TRAVEL_START_ZONE_5 = 1;
    public static final Integer TRAVEL_END_ZONE_5 = 3;
    
    public static final String TRAVEL_START_DATE_4 = "31/07/2014";

    public static final String SEVEN_DAY_TRAVEL_CARD_TYPE = "7Day";
    public static final String OTHER_TRAVEL_CARD_TYPE_1 = "Unknown";
    public static final String OTHER_TRAVEL_CARD_TYPE_1_Other = "Unknown";
    public static final String OTHER_TRAVEL_CARD_TYPE_2 = "1 Month 12 Day travelcard";
    public static final String OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE = "7Day";
    public static final Integer OTHER_TRAVELCARD_ANNUAL_MULTIPLAY_RATE = 40;
    public static final Float OTHER_TRAVELCARD_MONTHLY_MULTIPLAY_RATE = 3.84f;
    public static final Integer OTHER_TRAVELCARD_DAILY_DIVIDE_RATE = 5;
    public static final String OTHER_TRAVEL_CARD_SUBSTITUTION_ANNUAL_TRAVEL_CARD = "Annual";
    public static final Integer OTHER_TRAVEL_CARD_SUBSTITUTION_ANNUAL_TRAVEL_CARD_DURATION_IN_MONTHS = 12;
    public static final Integer OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS = 365;
    public static final Integer OTHER_TRAVELCARD_MAXIMUM_ALLOWED_MONTHS = 10;
    public static final Integer OTHER_TRAVELCARD_MAXIMUM_ALLOWED_DAYS = 12;
    public static final Integer OTHER_TRAVELCARD_MINIMUM_ALLOWED_MONTHS = 1;
    public static final Integer OTHER_TRAVELCARD_MINIMUM_ALLOWED_DAYS = 1;
    public static final String OTHER_TRAVEL_START_DATE_1 = "15/11/2013";
    public static final String OTHER_TRAVEL_END_DATE_1 = "21/02/2014";
    public static final String OTHER_TRAVEL_END_DATE_2 = "15/10/2014";
    public static final String OTHER_TRAVEL_END_DATE_3 = "15/12/2013";
    public static final String OTHER_TRAVEL_START_DATE_4 = "01/11/2013";
    public static final String OTHER_TRAVEL_END_DATE_4 = "21/11/2013";

    public static final Integer PRODUCT_START_AFTER_DAYS = 3;
    public static final Integer MAXIMUM_ALLOWED_TRAVEL_CARDS = 3;
    public static final Integer MAXIMUM_ALLOWED_PENDING_ITEMS = 4;
    public static final Integer USER_PRODUCT_START_AFTER_DAYS = 1;
    public static final Integer CARD_REFUNDABLE_DEPOSIT_AMT = 500;
    public static final Integer PAY_AS_YOU_GO_COLLECTION_DAYS = 8;
    public static final Integer PRODUCT_AVAILABLE_DAYS = 30;
    public static final Integer QUICK_BUY_WITHIN_NUMBER_OF_DAYS_OF_EXPIRY = 7;
    public static final Integer SUB_TOTAL_AMT = 10000;
    public static final Integer UPDATED_TOTAL_AMT = 11065;
    public static final Integer TOTAL_AMT = 15000;
    public static final Integer PAY_AS_YOU_GO_LIMIT = 9000;
    public static final Integer PAY_AS_YOU_GO_AD_HOC_PURCHASE_LIMIT = 5000;

    public static final Float JOB_CENTRE_PLUS_DISCOUNT_RATE = 0.3f;
    public static final Integer JOB_CENTRE_PLUS_DISCOUNT_MAXIMUM_ALLOWED_MONTHS = 3;
    public static final Integer JOB_CENTRE_PLUS_DISCOUNT_MAXIMUM_ALLOWED_DAYS = 0;

    public static final Long END_ZONE_ID_1 = 10L;
    public static final String END_ZONE_DESCRIPTION = "9 + Watford Junction";
    public static final Long STATION_ID_1 = 340L;
    public static final String PAGE_NAME_1 = "test-page-name";
    public static final Integer OTHER_TRAVEL_CARD_TICKET_PRICE = 1571072;

    public static final Long GOODWILL_PAYMENT_ID = 1L;
    public static final int GOODWILL_AMOUNT = 1000;
    public static final int GOODWILL_AMOUNT_ZERO = 0;
    public static final int GOODWILL_AMOUNT_NEGATIVE = -1;
    public static final Long GOODWILL_PAYMENT_ID_2 = 9L;
    public static final String GOODWILL_OTHER_TEXT = "Other Text";
    public static final String GOODWILL_TYPE = "Goodwill Entry/Exit Pay As You Go Overcharge";

    public static final Integer ADMINISTRATION_FEE_PRICE_1 = 1000;
    public static final String ADMINISTRATION_FEE_TYPE_1 = "Failed Card Refund";
    public static final String ADMINISTRATION_FEE_TYPE_2 = "administrationFeeFailedCardRefund";
    public static final Integer ADMINISTRATION_FEE_PRICE_2 = 500;
    public static final String ADMINISTRATION_FEE_CREATED_USER_ID_2 = "administrationFeeUser";
    public static final Long ADMINISTRATION_FEE_ID_2 = 2L;

    public static final Integer DECREMENT_COUNT_OF_ONE_DAY = -1;
    public static final String REFUND_CALCULATION_BASIS_PRO_RATA = "Pro Rata";
    public static final String REFUND_CALCULATION_BASIS_ORDINARY = "Ordinary";
    public static final Integer ANONYMOUS_GOODWILL_MAX_REFUND_AMOUNT = 50;

    public static final String VALID_QUICKBUY_EXPIRYDATE = "31/07/2014";
    public static final String INVALID_QUICKBUY_EXPIRYDATE = "15/08/2014";
    public static final Integer VALID_QUICKBUY_EXPIRYDATE_DAYS_TO_ADD = 9;
    public static final Integer INVALID_QUICKBUY_EXPIRYDATE_DAYS_TO_ADD = 4;
    
    public static final String RATE = "123";
    public static final String STATUS_MESSAGE = "test status message";
    
    public static final String MAGNETIC_TICKET_NUMBER = "159632587322566558";
    
    public static final Integer ZERO = 0;
    public static final Integer ADMINSTRATION_FEE_NEGATIVE_VALUE = -150;
    public static final Integer ADMINSTRATION_FEE_GREATER_THAN_ZERO_VALUE = 100;
    public static final Long PRE_PAID_TICKET_ID = 1L;
    public static final String PASSENGER_TYPE = "Adult";
    public static final String DISCOUNT_TYPE = "No Discount";

    public static List<CartItemCmdImpl> getTestPayAsYouGoList1() {
        List<CartItemCmdImpl> cartItems = new ArrayList<CartItemCmdImpl>();
        cartItems.add(getTestPayAsYouGo1());
        return cartItems;
    }

    public static List<CartItemCmdImpl> getTestPayAsYouGoEmptyList() {
        List<CartItemCmdImpl> cartItems = new ArrayList<CartItemCmdImpl>();
        cartItems.add(getTestPayAsYouGo3());
        return cartItems;
    }

    public static List<CartItemCmdImpl> getTestPayAsYouGoList2() {
        List<CartItemCmdImpl> cartItems = new ArrayList<CartItemCmdImpl>();
        cartItems.add(getTestPayAsYouGo4());
        return cartItems;
    }

    public static List<CartItemCmdImpl> getTestPayAsYouGoList3() {
        List<CartItemCmdImpl> cartItems = new ArrayList<CartItemCmdImpl>();
        cartItems.add(getTestPayAsYouGo5());
        return cartItems;
    }

    public static List<CartItemCmdImpl> getTestTravelCardList1() {
        List<CartItemCmdImpl> cartItems = new ArrayList<CartItemCmdImpl>();
        cartItems.add(getTestTravelCard4());
        return cartItems;
    }

    public static List<CartItemCmdImpl> getTestEmptyTravelCardList() {
        List<CartItemCmdImpl> cartItems = new ArrayList<CartItemCmdImpl>();
        return cartItems;
    }

    public static List<CartItemCmdImpl> getTestGoodwillPaymentList1() {
        List<CartItemCmdImpl> cartItems = new ArrayList<CartItemCmdImpl>();
        cartItems.add(getTestGoodwillPayment1());
        return cartItems;
    }

    public static List<CartItemCmdImpl> getTestCartItems() {
        List<CartItemCmdImpl> cartItems = new ArrayList<CartItemCmdImpl>();
        cartItems.add(getTestTravelCard4());
        cartItems.add(getTestGoodwillPayment1());
        cartItems.add(getTestPayAsYouGo1());
        return cartItems;
    }

    public static List<CartItemCmdImpl> getTestCartItems1() {
        List<CartItemCmdImpl> cartItems = new ArrayList<CartItemCmdImpl>();
        cartItems.add(getTestTravelCard4());
        cartItems.add(getTestGoodwillPayment1());
        cartItems.add(getTestPayAsYouGo1());
        cartItems.add(getTestAdministrationFee2());
        return cartItems;
    }

    public static List<CartItemCmdImpl> getTestCartItems2() {
        List<CartItemCmdImpl> cartItems = new ArrayList<CartItemCmdImpl>();
        cartItems.add(getTestPayAsYouGo6());
        cartItems.add(getTestAdministrationFee2());
        return cartItems;
    }

    public static List<CartItemCmdImpl> getTestCartItems3() {
        List<CartItemCmdImpl> cartItems = new ArrayList<CartItemCmdImpl>();
        return cartItems;
    }

    public static List<CartItemCmdImpl> getTestCartItems4() {
        List<CartItemCmdImpl> cartItems = new ArrayList<CartItemCmdImpl>();
        cartItems.add(getTestTravelCard4());
        cartItems.add(getTestAnnualBusPass1());
        cartItems.add(getTestGoodwillPayment1());
        cartItems.add(getTestPayAsYouGo1());
        return cartItems;
    }

    public static List<CartItemCmdImpl> getTestCartItems5() {
        List<CartItemCmdImpl> cartItems = new ArrayList<CartItemCmdImpl>();
        cartItems.add(getTestTravelCard2());
        cartItems.add(getTestTravelCard4());
        cartItems.add(getTestAnnualBusPass2());
        cartItems.add(getTestGoodwillPayment1());
        cartItems.add(getTestPayAsYouGo1());
        cartItems.add(getTestAdministrationFee2());
        return cartItems;
    }

    public static List<CartItemCmdImpl> getTestCartItems6() {
        List<CartItemCmdImpl> cartItems = new ArrayList<CartItemCmdImpl>();
        cartItems.add(getTestAdministrationFee1());
        return cartItems;
    }

    public static List<CartItemCmdImpl> getTestCartItems7() {
        List<CartItemCmdImpl> cartItems = new ArrayList<CartItemCmdImpl>();
        cartItems.add(getTestTravelCard6());
        cartItems.add(getTestTravelCard7());
        cartItems.add(getTestAnnualBusPass2());
        cartItems.add(getTestGoodwillPayment1());
        cartItems.add(getTestPayAsYouGo1());
        cartItems.add(getTestAdministrationFee2());
        return cartItems;
    }

    public static List<CartItemCmdImpl> getTestCartItems8() {
        List<CartItemCmdImpl> cartItems = new ArrayList<CartItemCmdImpl>();
        cartItems.add(getTestTravelCard8());
        return cartItems;
    }

    public static List<CartItemCmdImpl> getTestCartItems9() {
        List<CartItemCmdImpl> cartItems = new ArrayList<CartItemCmdImpl>();
        cartItems.add(getTestTravelCard2());
        return cartItems;
    }

    public static List<CartItemCmdImpl> getTestCartItems10() {
        List<CartItemCmdImpl> cartItems = new ArrayList<CartItemCmdImpl>();
        cartItems.add(getTestPayAsYouGo1());
        return cartItems;
    }

    public static CartItemCmdImpl getTestPayAsYouGo1() {
        return getTestCartItem(PAY_AS_YOU_GO_NAME_1, PAY_AS_YOU_Go_START_DATE_1, null, PAY_AS_YOU_Go_REMINDER_DATE_1, PAY_AS_YOU_GO_TICKET_PRICE_1, 0);
    }

    public static CartItemCmdImpl getTestPayAsYouGo2() {
        return getTestCartItem(PAY_AS_YOU_GO_NAME_1, PAY_AS_YOU_Go_START_DATE_1, null, PAY_AS_YOU_Go_REMINDER_DATE_1, PAY_AS_YOU_GO_TICKET_PRICE_1,
                        PAY_AS_YOU_GO_AUTO_TOP_UP_AMT_1);
    }

    public static CartItemCmdImpl getTestPayAsYouGo3() {
        return getTestCartItem(PAY_AS_YOU_GO_NAME_1, PAY_AS_YOU_Go_START_DATE_1, null, PAY_AS_YOU_Go_REMINDER_DATE_1, null, 0);
    }

    public static CartItemCmdImpl getTestPayAsYouGo4() {
        return getTestCartItem(PAY_AS_YOU_GO_NAME_1, PAY_AS_YOU_Go_START_DATE_1, null, PAY_AS_YOU_Go_REMINDER_DATE_1, PAY_AS_YOU_GO_TICKET_PRICE_2, 0);
    }

    public static CartItemCmdImpl getTestPayAsYouGo5() {
        return getTestCartItem(PAY_AS_YOU_GO_NAME_1, PAY_AS_YOU_Go_START_DATE_1, null, PAY_AS_YOU_Go_REMINDER_DATE_1, PAY_AS_YOU_GO_TICKET_PRICE_3, 0);
    }

    public static CartItemCmdImpl getTestPayAsYouGo6() {
        return getTestCartItem(PAY_AS_YOU_GO_NAME_1, PAY_AS_YOU_Go_START_DATE_1, null, PAY_AS_YOU_Go_REMINDER_DATE_1, PAY_AS_YOU_GO_TICKET_PRICE_4, 0);
    }
    
    public static CartItemCmdImpl getTestPayAsYouGo7() {
        CartItemCmdImpl cartItemCmdImpl = getTestCartItem(PAY_AS_YOU_GO_NAME_1, PAY_AS_YOU_Go_START_DATE_1, null, PAY_AS_YOU_Go_REMINDER_DATE_1, PAY_AS_YOU_GO_TICKET_PRICE_4, 0);
        cartItemCmdImpl.setTicketType(TicketType.PAY_AS_YOU_GO.code());
        return cartItemCmdImpl;
    }

    public static CartItemCmdImpl getTestAdministrationFee1() {
        return getTestCartItem(ADMINISTRATION_FEE_TYPE_1, ADMINISTRATION_FEE_PRICE_1);
    }

    public static CartItemCmdImpl getTestAdministrationFee2() {
        return getTestCartItem(ADMINISTRATION_FEE_TYPE_2, ADMINISTRATION_FEE_PRICE_2);
    }

    public static CartItemCmdImpl getTestAnnualBusPass1() {
        CartItemCmdImpl cartItem = getTestCartItem(ANNUAL_BUS_PASS_1, ANNUAL_START_DATE_1, ANNUAL_END_DATE_1, REMINDER_DATE_1, ANNUAL_PRICE_1);
        cartItem.setPassengerType(PASSENGER_TYPE);
        cartItem.setDiscountType(DISCOUNT_TYPE);
        return cartItem;
    }

    public static CartItemCmdImpl getTestAnnualBusPass2() {
        return getTestCartItem(ANNUAL_BUS_PASS_1, ANNUAL_START_DATE_2, ANNUAL_END_DATE_2, REMINDER_DATE_1, ANNUAL_PRICE_1);
    }

    public static CartItemCmdImpl getTestTravelCard1() {
        CartItemCmdImpl cartItem = getTestCartItem(SEVEN_DAY_TRAVEL_CARDS_1, TRAVEL_START_DATE_1, TRAVEL_END_DATE_1, REMINDER_DATE_1, TRAVEL_PRICE_1,
                        TRAVEL_START_ZONE_1, TRAVEL_END_ZONE_1, REFUND_CALCULATION_BASIS_PRO_RATA);
        cartItem.setTravelCardType(SEVEN_DAY_TRAVEL_CARD_TYPE);
        cartItem.setPassengerType(PASSENGER_TYPE);
        cartItem.setDiscountType(DISCOUNT_TYPE);
        return cartItem;
    }

    public static CartItemCmdImpl getTestTravelCard2() {
        return getTestCartItem(SEVEN_DAY_TRAVEL_CARDS_2, TRAVEL_START_DATE_2, TRAVEL_END_DATE_2, REMINDER_DATE_1, TRAVEL_PRICE_2,
                        TRAVEL_START_ZONE_2, TRAVEL_END_ZONE_2, REFUND_CALCULATION_BASIS_ORDINARY);
    }

    public static CartItemCmdImpl getTestTravelCard3() {
        return getTestCartItem(SEVEN_DAY_TRAVEL_CARDS_1, TRAVEL_START_DATE_3, TRAVEL_END_DATE_3, REMINDER_DATE_1, TRAVEL_PRICE_1,
                        TRAVEL_START_ZONE_3, TRAVEL_END_ZONE_3, REFUND_CALCULATION_BASIS_PRO_RATA);
    }

    public static CartItemCmdImpl getTestTravelCard4() {
        Date travelCardDateExpired = DateUtil.addDaysToDate(new Date(), DECREMENT_COUNT_OF_ONE_DAY);
        return getTestCartItem(SEVEN_DAY_TRAVEL_CARDS_1, TRAVEL_START_DATE_3, DateUtil.formatDate(travelCardDateExpired), REMINDER_DATE_1,
                        TRAVEL_PRICE_1, TRAVEL_START_ZONE_3, TRAVEL_END_ZONE_3, REFUND_CALCULATION_BASIS_PRO_RATA);
    }

    public static CartItemCmdImpl getTestTravelCard5() {
        return getTestCartItem(SEVEN_DAY_TRAVEL_CARDS_1, TRAVEL_START_DATE_1, DateUtil.formatDate(new Date()), REMINDER_DATE_1, TRAVEL_PRICE_1,
                        TRAVEL_START_ZONE_1, TRAVEL_END_ZONE_1, REFUND_CALCULATION_BASIS_PRO_RATA);
    }

    public static CartItemCmdImpl getTestTravelCard6() {
        return getTestCartItem(SEVEN_DAY_TRAVEL_CARDS_1, TRAVEL_START_DATE_4, DateUtil.formatDate(new Date()), REMINDER_DATE_1, TRAVEL_PRICE_1,
                        TRAVEL_START_ZONE_3, TRAVEL_END_ZONE_1, REFUND_CALCULATION_BASIS_PRO_RATA);
    }

    public static CartItemCmdImpl getTestTravelCard7() {
        return getTestCartItem(SEVEN_DAY_TRAVEL_CARDS_1, TRAVEL_START_DATE_4, DateUtil.formatDate(new Date()), REMINDER_DATE_1, TRAVEL_PRICE_1,
                        TRAVEL_START_ZONE_1, TRAVEL_END_ZONE_3, REFUND_CALCULATION_BASIS_PRO_RATA);
    }

    public static CartItemCmdImpl getTestTravelCard8() {
        CartItemCmdImpl cartItem = getTestCartItem(SEVEN_DAY_TRAVEL_CARDS_1, TRAVEL_START_DATE_4, DateUtil.formatDate(new Date()), REMINDER_DATE_1,
                        TRAVEL_PRICE_1, TRAVEL_START_ZONE_1, TRAVEL_END_ZONE_1, REFUND_CALCULATION_BASIS_PRO_RATA);
        cartItem.setTravelCardType(SEVEN_DAY_TRAVEL_CARD_TYPE);
        cartItem.setPassengerType(PASSENGER_TYPE);
        cartItem.setDiscountType(DISCOUNT_TYPE);
        return cartItem;
    }

    public static CartItemCmdImpl getTestTravelCard9() {
        CartItemCmdImpl cartItem = getTestCartItem(SEVEN_DAY_TRAVEL_CARDS_1, TRAVEL_START_DATE_4, DateUtil.formatDate(new Date()), REMINDER_DATE_1, TRAVEL_PRICE_1,
                        TRAVEL_START_ZONE_2, TRAVEL_END_ZONE_1, REFUND_CALCULATION_BASIS_PRO_RATA, RATE);
        cartItem.setTravelCardType(SEVEN_DAY_TRAVEL_CARD_TYPE);
        cartItem.setPassengerType(PASSENGER_TYPE);
        cartItem.setDiscountType(DISCOUNT_TYPE);
        return cartItem;
    }
    
    public static CartItemCmdImpl getTestTravelCard10() {
        CartItemCmdImpl cartItem = getTestCartItem(SEVEN_DAY_TRAVEL_CARDS_1, TRAVEL_START_DATE_4, DateUtil.formatDate(new Date()), REMINDER_DATE_1, TRAVEL_PRICE_1,
                        TRAVEL_START_ZONE_2, TRAVEL_END_ZONE_1, REFUND_CALCULATION_BASIS_PRO_RATA, RATE);
        cartItem.setTravelCardType(SEVEN_DAY_TRAVEL_CARD_TYPE);
        cartItem.setTicketType(TicketType.TRAVEL_CARD.code());
        cartItem.setPassengerType(PASSENGER_TYPE);
        cartItem.setDiscountType(DISCOUNT_TYPE);
        return cartItem;
    }
    
    public static CartItemCmdImpl getTestTravelCard11() {
    	CartItemCmdImpl  cartItemCmdImpl = getTestCartItem(SEVEN_DAY_TRAVEL_CARDS_1, TRAVEL_START_DATE_1, TRAVEL_END_DATE_1, REMINDER_DATE_1, TRAVEL_PRICE_1,
                        TRAVEL_START_ZONE_1, TRAVEL_END_ZONE_1, REFUND_CALCULATION_BASIS_PRO_RATA);
    	cartItemCmdImpl.setPreviouslyExchanged(true);
    	return cartItemCmdImpl;
    } 

    public static CartItemCmdImpl getTestOtherTravelCard1() {
        return getTestCartItem(OTHER_TRAVEL_CARD_TYPE_2, TRAVEL_START_DATE_3, TRAVEL_END_DATE_3, REMINDER_DATE_1, TRAVEL_PRICE_1,
                        TRAVEL_START_ZONE_3, TRAVEL_END_ZONE_3, REFUND_CALCULATION_BASIS_ORDINARY);
    }

    public static CartItemCmdImpl getTestGoodwillPayment1() {
        return getTestGoodwillPaymentItem(GOODWILL_TYPE, null, null, null, GOODWILL_AMOUNT, null, null, GOODWILL_PAYMENT_ID);
    }

    public static CartItemCmdImpl getTestGoodwillPayment2() {
        return getTestGoodwillPaymentItem(GOODWILL_AMOUNT, GOODWILL_PAYMENT_ID, CARD_ID_1);
    }

    public static CartItemCmdImpl getPreviouslyExchnagedSevenDayTravelCard() {
        CartItemCmdImpl cartItem = getTestTravelCard1();
        cartItem.setRate(RATE);
        cartItem.setTravelCardType(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
        cartItem.setTradedTicket(getSevenDayTradedTicket());
        cartItem.setPreviouslyExchanged(true);
        return cartItem;
    }

    public static CartItemCmdImpl getPreviouslyExchnagedOtherTravelCard() {
        CartItemCmdImpl cartItem = getTestTravelCard1();
        cartItem.setPassengerType(PASSENGER_TYPE);
        cartItem.setDiscountType(DISCOUNT_TYPE);
        cartItem.setTravelCardType(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
        cartItem.setTradedTicket(getOtherTradedTicket());
        cartItem.setPreviouslyExchanged(true);
        return cartItem;
    }

    public static CartItemCmdImpl getSevenDayTradedTicket() {
        CartItemCmdImpl cartItem = getTestCartItem(OTHER_TRAVEL_CARD_TYPE_2, TRAVEL_START_DATE_3, TRAVEL_END_DATE_3, REMINDER_DATE_1, TRAVEL_PRICE_1,
                        TRAVEL_START_ZONE_3, TRAVEL_END_ZONE_3, REFUND_CALCULATION_BASIS_ORDINARY);
        cartItem.setExchangedDate(new DateTime());
        cartItem.setStartDate("2014/07/31");
        cartItem.setPassengerType(PASSENGER_TYPE);
        cartItem.setDiscountType(DISCOUNT_TYPE);
        cartItem.setTravelCardType(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
        return cartItem;
    }

    public static CartItemCmdImpl getOtherTradedTicket() {
        CartItemCmdImpl cartItem = getTestCartItem(OTHER_TRAVEL_CARD_TYPE_2, TRAVEL_START_DATE_3, TRAVEL_END_DATE_3, REMINDER_DATE_1, TRAVEL_PRICE_1,
                        TRAVEL_START_ZONE_3, TRAVEL_END_ZONE_3, REFUND_CALCULATION_BASIS_ORDINARY);
        cartItem.setExchangedDate(new DateTime());
        cartItem.setStartDate("2014/07/31");
        cartItem.setPassengerType(PASSENGER_TYPE);
        cartItem.setDiscountType(DISCOUNT_TYPE);
        cartItem.setTravelCardType(OTHER_TRAVEL_CARD_TYPE_1);
        return cartItem;
    }

    public static CartItemCmdImpl getTestCartItem(String itemName, String startDate, String endDate, String emailReminder, Integer price) {
        CartItemCmdImpl cartItem = new CartItemCmdImpl();
        cartItem.setItem(itemName);
        cartItem.setStartDate(startDate);
        cartItem.setEndDate(endDate);
        cartItem.setEmailReminder(emailReminder);
        cartItem.setPrice(price);
        return cartItem;
    }

    public static CartItemCmdImpl getTestCartItem(String itemName, String startDate, String endDate, String emailReminder, Integer price,
                    Integer autoTopUpAmt) {
        CartItemCmdImpl cartItem = new CartItemCmdImpl();
        cartItem.setItem(itemName);
        cartItem.setStartDate(startDate);
        cartItem.setEndDate(endDate);
        cartItem.setEmailReminder(emailReminder);
        cartItem.setPrice(price);
        cartItem.setAutoTopUpAmt(autoTopUpAmt);
        cartItem.setId(CARD_ID_1);
        return cartItem;
    }

    public static CartItemCmdImpl getTestCartItemWithBackdatedTrue(String itemName, String startDate, String endDate, String emailReminder, Integer price,
                    Integer autoTopUpAmt) {
        CartItemCmdImpl cartItem = new CartItemCmdImpl();
        cartItem.setItem(itemName);
        cartItem.setStartDate(startDate);
        cartItem.setEndDate(endDate);
        cartItem.setEmailReminder(emailReminder);
        cartItem.setPrice(price);
        cartItem.setAutoTopUpAmt(autoTopUpAmt);
        cartItem.setId(CARD_ID_1);
        cartItem.setBackdated(true);
        return cartItem;
    }

    public static CartItemCmdImpl getTestCartItemWithDeceasedCustomerTrue(String itemName, String startDate, String endDate, String emailReminder, Integer price,
                    Integer autoTopUpAmt) {
        CartItemCmdImpl cartItem = new CartItemCmdImpl();
        cartItem.setItem(itemName);
        cartItem.setStartDate(startDate);
        cartItem.setEndDate(endDate);
        cartItem.setEmailReminder(emailReminder);
        cartItem.setPrice(price);
        cartItem.setAutoTopUpAmt(autoTopUpAmt);
        cartItem.setId(CARD_ID_1);
        cartItem.setDeceasedCustomer(true);
        return cartItem;
    }

    public static CartItemCmdImpl getTestCartItem(String itemName, Integer price) {
        CartItemCmdImpl cartItem = new CartItemCmdImpl();
        cartItem.setItem(itemName);
        cartItem.setPrice(price);
        return cartItem;
    }

    public static CartItemCmdImpl getTestCartItem(String itemName, String startDate, String endDate, String emailReminder, Integer price,
                    Integer startZone, Integer endZone, String refundCalculationBasis) {
        CartItemCmdImpl cartItem = new CartItemCmdImpl();
        cartItem.setItem(itemName);
        cartItem.setStartDate(startDate);
        cartItem.setEndDate(endDate);
        cartItem.setEmailReminder(emailReminder);
        cartItem.setPrice(price);
        cartItem.setStartZone(startZone);
        cartItem.setEndZone(endZone);
        cartItem.setRefundCalculationBasis(refundCalculationBasis);
        cartItem.setCardId(CARD_ID_1);
        return cartItem;
    }

    public static CartItemCmdImpl getTestCartItem(String itemName, String startDate, String endDate, String emailReminder, Integer price,
                    Integer startZone, Integer endZone, String refundCalculationBasis, String rate) {
        CartItemCmdImpl cartItem = getTestCartItem(itemName, startDate, endDate, emailReminder, price, startZone, endZone, refundCalculationBasis);
        cartItem.setRate(rate);
        return cartItem;
    }

    public static CartItemCmdImpl getTestGoodwillPaymentItem(String itemName, String startDate, String endDate, String emailReminder, Integer price,
                    Integer startZone, Integer endZone, Long goodwillPaymentId) {
        CartItemCmdImpl cartItem = new CartItemCmdImpl();
        cartItem.setItem(itemName);
        cartItem.setStartDate(startDate);
        cartItem.setEndDate(endDate);
        cartItem.setEmailReminder(emailReminder);
        cartItem.setPrice(price);
        cartItem.setStartZone(startZone);
        cartItem.setEndZone(endZone);
        cartItem.setGoodwillPaymentId(goodwillPaymentId);
        return cartItem;
    }

    public static CartItemCmdImpl getTestGoodwillPaymentItem(Integer goodwillPrice, Long goodwillPaymentId, Long cardId) {
        CartItemCmdImpl cartItem = new CartItemCmdImpl();
        cartItem.setGoodwillPrice(goodwillPrice);
        cartItem.setGoodwillPaymentId(goodwillPaymentId);
        cartItem.setCardId(cardId);
        return cartItem;
    }

    public static ZoneIdDescriptionDTO getTestZoneIdDescriptionDTO1() {
        return getTestZoneIdDescriptionDTO(END_ZONE_ID_1, END_ZONE_DESCRIPTION);
    }

    public static ZoneIdDescriptionDTO getTestZoneIdDescriptionDTO(Long zoneId, String description) {
        ZoneIdDescriptionDTO zoneIdDescriptionDTO = new ZoneIdDescriptionDTO();
        zoneIdDescriptionDTO.setId(zoneId);
        zoneIdDescriptionDTO.setDescription(description);
        return zoneIdDescriptionDTO;
    }

    public static CartItemCmdImpl getTestTravelCardCmd1() {
        return getTestTravelCardCmd(SEVEN_DAY_TRAVEL_CARD_TYPE, TRAVEL_START_DATE_1, null, TRAVEL_START_ZONE_1, TRAVEL_END_ZONE_1);
    }

    public static CartItemCmdImpl getTestOddPeriodOtherTravelCardCmd1() {
        return getTestTravelCardCmd(OTHER_TRAVEL_CARD_TYPE_1, OTHER_TRAVEL_START_DATE_1, OTHER_TRAVEL_END_DATE_1, TRAVEL_START_ZONE_1,
                        TRAVEL_END_ZONE_1);
    }

    public static CartItemCmdImpl getTestOddPeriodOtherTravelCardCmd2() {
        return getTestTravelCardCmd(OTHER_TRAVEL_CARD_TYPE_1, OTHER_TRAVEL_START_DATE_1, OTHER_TRAVEL_END_DATE_2, TRAVEL_START_ZONE_1,
                        TRAVEL_END_ZONE_1);
    }

    public static CartItemCmdImpl getTestOddPeriodOtherTravelCardCmd3() {
        return getTestTravelCardCmd(OTHER_TRAVEL_CARD_TYPE_1, OTHER_TRAVEL_START_DATE_1, OTHER_TRAVEL_END_DATE_3, TRAVEL_START_ZONE_1,
                        TRAVEL_END_ZONE_1);
    }

    public static CartItemCmdImpl getTestTravelCardCmd(String travelCardType, String startDate, String endDate, Integer startZone, Integer endZone) {
        CartItemCmdImpl cmd = new CartItemCmdImpl();
        cmd.setTravelCardType(travelCardType);
        cmd.setStartDate(startDate);
        cmd.setEndDate(endDate);
        cmd.setStartZone(startZone);
        cmd.setEndZone(endZone);
        return cmd;
    }

    public static CartItemCmdImpl getTestTopUpTicketTravelCardCmd1() {
        return getTestTravelCardCmd(CARD_ID_1, SEVEN_DAY_TRAVEL_CARD_TYPE, TRAVEL_START_DATE_1, TRAVEL_END_DATE_1, TRAVEL_START_ZONE_1,
                        TRAVEL_END_ZONE_1);
    }

    public static CartItemCmdImpl getTestTopUpTicketTravelCardWithExchangeCmd1() {
        CartItemCmdImpl testTravelCardCmd = getTestTravelCardCmd(CARD_ID_1, SEVEN_DAY_TRAVEL_CARD_TYPE, TRAVEL_START_DATE_1, TRAVEL_END_DATE_1,
                        TRAVEL_START_ZONE_1, TRAVEL_END_ZONE_1);
        testTravelCardCmd.setTicketType(ANNUAL_END_DATE_1);
        testTravelCardCmd.setTradedTicket(getTestTravelCardCmd(CARD_ID_1, OTHER_TRAVEL_CARD_TYPE_1, OTHER_TRAVEL_START_DATE_1,
                        OTHER_TRAVEL_END_DATE_1, TRAVEL_START_ZONE_1, TRAVEL_END_ZONE_1));
        return testTravelCardCmd;
    }

    public static CartItemCmdImpl getTestTopUpTicketOddPeriodOtherTravelCardCmd1() {
        return getTestTravelCardCmd(CARD_ID_1, OTHER_TRAVEL_CARD_TYPE_1, OTHER_TRAVEL_START_DATE_1, OTHER_TRAVEL_END_DATE_1, TRAVEL_START_ZONE_1,
                        TRAVEL_END_ZONE_1);
    }

    public static CartItemCmdImpl getTestTopUpTicketOddPeriodOtherTravelCardWithExchangeCmd1() {
        CartItemCmdImpl testTravelCardCmd = getTestTravelCardCmd(CARD_ID_1, OTHER_TRAVEL_CARD_TYPE_1, OTHER_TRAVEL_START_DATE_1,
                        OTHER_TRAVEL_END_DATE_1, TRAVEL_START_ZONE_1, TRAVEL_END_ZONE_1);
        testTravelCardCmd.setTradedTicket(getTestTravelCardCmd(CARD_ID_1, OTHER_TRAVEL_CARD_TYPE_1, OTHER_TRAVEL_START_DATE_1,
                        OTHER_TRAVEL_END_DATE_1, TRAVEL_START_ZONE_1, TRAVEL_END_ZONE_1));
        return testTravelCardCmd;
    }

    public static CartItemCmdImpl getTestTopUpTicketOddPeriodOtherTravelCardCmd2() {
        return getTestTravelCardCmd(CARD_ID_1, OTHER_TRAVEL_CARD_TYPE_1, OTHER_TRAVEL_START_DATE_1, OTHER_TRAVEL_END_DATE_2, TRAVEL_START_ZONE_1,
                        TRAVEL_END_ZONE_1);
    }

    public static CartItemCmdImpl getTestTopUpTicketOddPeriodOtherTravelCardCmd3() {
        return getTestTravelCardCmd(CARD_ID_1, OTHER_TRAVEL_CARD_TYPE_1, OTHER_TRAVEL_START_DATE_1, OTHER_TRAVEL_END_DATE_3, TRAVEL_START_ZONE_1,
                        TRAVEL_END_ZONE_1);
    }

    public static CartItemCmdImpl getTestTopUpTicketOddPeriodOtherTravelCardJobCentreCmd1() {
        return getTestTravelCardCmd(CARD_ID_1, OTHER_TRAVEL_CARD_TYPE_1, OTHER_TRAVEL_START_DATE_4, OTHER_TRAVEL_END_DATE_4, TRAVEL_START_ZONE_1,
                        TRAVEL_END_ZONE_1);
    }

    public static CartItemCmdImpl getTestJobCentrePlusTravelCardCmd1() {
        return getTestTravelCardCmd(CARD_ID_1, OTHER_TRAVEL_CARD_TYPE_1, OTHER_TRAVEL_START_DATE_4, OTHER_TRAVEL_END_DATE_4, TRAVEL_START_ZONE_1,
                        TRAVEL_END_ZONE_1);
    }
    
    public static CartItemCmdImpl getAnnualBusPassCartItem() {
        CartItemCmdImpl cartItemCmd = new CartItemCmdImpl();
        Date today = new Date();
        cartItemCmd.setStartDate(DateUtil.formatDate(today));
        cartItemCmd.setTicketType(TicketType.BUS_PASS.code());
        cartItemCmd.setTravelCardType(SEVEN_DAY_TRAVEL_CARD_TYPE);
        cartItemCmd.setRefundType(CartType.LOST_REFUND.code());
        cartItemCmd.setCardId(new Long(1234));
        cartItemCmd.setStartZone(0);
        cartItemCmd.setEndZone(0);

        return cartItemCmd;
    }

    public static CartItemCmdImpl getTestTravelCardCmd(Long cardId, String travelCardType, String startDate, String endDate, Integer startZone,
                    Integer endZone) {
        CartItemCmdImpl cmd = new CartItemCmdImpl();
        cmd.setCardId(cardId);
        cmd.setTravelCardType(travelCardType);
        cmd.setStartDate(startDate);
        cmd.setEndDate(endDate);
        cmd.setStartZone(startZone);
        cmd.setEndZone(endZone);
        return cmd;
    }

    public static CartItemCmdImpl getTravelcardWithPrePaidTicketId() {
        CartItemCmdImpl cmd = new CartItemCmdImpl();
        cmd.setCardId(CARD_ID_1);
        cmd.setPrePaidTicketId(PRE_PAID_TICKET_ID);
        cmd.setStartZone(TRAVEL_START_ZONE_1);
        cmd.setEndZone(TRAVEL_END_ZONE_1);
        cmd.setStartDate(ANNUAL_START_DATE_1);
        cmd.setEndDate(ANNUAL_END_DATE_1);
        return cmd;
    }

    public static CartItemCmdImpl getTestPayAsYouGoWithBackdatedRefundTrue() {
        return getTestCartItemWithBackdatedTrue(PAY_AS_YOU_GO_NAME_1, PAY_AS_YOU_Go_START_DATE_1, null, PAY_AS_YOU_Go_REMINDER_DATE_1, PAY_AS_YOU_GO_TICKET_PRICE_1, 0);
    }

    public static CartItemCmdImpl getTestPayAsYouGoWithDeceasedCustomerRefundTrue() {
        return getTestCartItemWithDeceasedCustomerTrue(PAY_AS_YOU_GO_NAME_1, PAY_AS_YOU_Go_START_DATE_1, null, PAY_AS_YOU_Go_REMINDER_DATE_1, PAY_AS_YOU_GO_TICKET_PRICE_1, 0);
    }

    public static CartItemCmdImpl getCartItemCmdWithFaileAutoTopUpCaseAdded() {
        CartItemCmdImpl cartItemCmdImpl = getTestCartItem(PAY_AS_YOU_GO_NAME_1, PAY_AS_YOU_Go_START_DATE_1, null, PAY_AS_YOU_Go_REMINDER_DATE_1, PAY_AS_YOU_GO_TICKET_PRICE_4, 0);
        cartItemCmdImpl.setTicketType(TicketType.PAY_AS_YOU_GO.code());
        cartItemCmdImpl.setOysterCardWithFailedAutoTopUpCaseCheck(true);
        cartItemCmdImpl.setTicketType(TicketType.PAY_AS_YOU_GO_AUTO_TOP_UP.code());
        return cartItemCmdImpl;
    }

    public static CartItemCmdImpl getCartItemCmdWithOutFaileAutoTopUpCaseAdded() {
        CartItemCmdImpl cartItemCmdImpl = getTestCartItem(PAY_AS_YOU_GO_NAME_1, PAY_AS_YOU_Go_START_DATE_1, null, PAY_AS_YOU_Go_REMINDER_DATE_1, PAY_AS_YOU_GO_TICKET_PRICE_4, 0);
        cartItemCmdImpl.setTicketType(TicketType.PAY_AS_YOU_GO.code());
        cartItemCmdImpl.setOysterCardWithFailedAutoTopUpCaseCheck(false);
        return cartItemCmdImpl;
    }

    public static CartItemCmdImpl getCartItemCmdWithOutFaileAutoTopUpCaseAddedWithStatusMessage() {
        CartItemCmdImpl cartItemCmdImpl = getTestCartItem(PAY_AS_YOU_GO_NAME_1, PAY_AS_YOU_Go_START_DATE_1, null, PAY_AS_YOU_Go_REMINDER_DATE_1, PAY_AS_YOU_GO_TICKET_PRICE_4, 0);
        cartItemCmdImpl.setTicketType(TicketType.PAY_AS_YOU_GO.code());
        cartItemCmdImpl.setOysterCardWithFailedAutoTopUpCaseCheck(false);
        cartItemCmdImpl.setStatusMessage(STATUS_MESSAGE);
        return cartItemCmdImpl;
    }

}
