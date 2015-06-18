package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.common.utils.DateUtil.formatDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.novacroft.nemo.common.constant.DateConstant;
import com.novacroft.nemo.common.domain.cubic.HotListReasons;
import com.novacroft.nemo.common.domain.cubic.PendingItems;
import com.novacroft.nemo.common.domain.cubic.PrePayTicket;
import com.novacroft.nemo.common.domain.cubic.PrePayTicketDetails;
import com.novacroft.nemo.common.domain.cubic.PrePayTicketSlot;
import com.novacroft.nemo.common.domain.cubic.PrePayValue;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.constant.AutoLoadState;
import com.novacroft.nemo.tfl.common.constant.OysterCardDiscountType;
import com.novacroft.nemo.tfl.common.constant.TransferConstants;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

/**
 * Utilities for Card tests
 */
public final class CardInfoResponseV2TestUtil extends CommonCardTestUtil {

    public static final int PRE_PAY_VALUE = 1000;
    private static Integer EURO = 1;
    public static int SLOT_1 = 1;
    private static Integer REQUEST_SEQUENCE_1 = 1;
    public static final int BALANCE_4 = 49;
    public static final int PRODUCT_CODE_TRAVEL_CARD = 33705;
    public static final String DISCOUNT_ENTITLEMENT_FAMILY = "Family";
    public static final String DISCOUNT_ENTITLEMENT_PRIVILEGE = "Privilege";
    public static final String DISCOUNT_ENTITLEMENT_DISABLED = "Disabled";
    public static final String ADULT_PASSENGER_TYPE = "Adult";
    public static final Integer CARD_DEPOSIT = 5;
    public static final Integer BALANCE_1 = 5700;
    public static final Integer NEGATIVE_BALANCE = -1000;
    public static final Integer BALANCE_0 = 0;
    public static final Integer CURRENCY_1 = 0;
    public static final Integer CURRENCY_2 = 1;
    public static final Integer BALANCE_2 = 500;
    public static final Integer BALANCE_3 = 5000;
    public static final Integer BALANCE_OVER_MAX = 9001;
    public static final Integer CARD_CAPABILITY_HOTLISTED = 4;
    public static final Integer CARD_CAPABILITY_NOT_HOTLISTED = 1;

    public static final String PRODUCT_1 = "Annual Travelcard";
    public static final Integer PRODUCT_CODE_1 = 1;
    public static final String ZONE_1 = "Zones 1 to 2";
    public static final String DISCOUNT_1 = "";
    public static final String PASSENGER_TYPE_1 = "";

    public static final String PRODUCT_2 = "Annual Travelcard";
    public static final String ZONE_2 = "Zones 2 to 3";
    public static final String DISCOUNT_2 = "";
    public static final String PASSENGER_TYPE_2 = "";

    public static final String PRODUCT_3 = "Annual Travelcard";
    public static final String ZONE_3 = "Zones 3 to 4";
    public static final String DISCOUNT_3 = "";
    public static final String PASSENGER_TYPE_3 = "";

    public static final Long STATION_ID = 567l;
    public static final Long STATION_ID_1 = 123L;
    public static final Long DIFFERENT_STATION_ID = 450l;

    public static final Integer PREPAY_TICKET_INVALID_STATE = 0;

    public static final Integer PREPAY_TICKET_VALID_STATE = 1;

    public static final String CARD_NUMBER_CCC_LOSt_STOLEN_DATE_TIME = "056487654440";
    public static final String CARD_NUMBER_NON_CCC_LOSt_STOLEN_DATE_TIME = "035840893766";

    public static final String GET_CARD_PAGE_VIEW = "GetCardView";

    public static final String UNEXPECTED_SERVER_ERROR_DESCRIPTION = "UNEXPECTED_SERVER_ERROR";
    private static final String DISCOUNT_TRAVELCARD = "Yes";
    private static final String NO_DISCOUNT_TRAVELCARD = "No Discount";
    private static final Integer BALANCE_SRC_CARD = 5000;
    private static final Integer BALANCE_TARGET_CARD = 6000;
    private static final Integer SLOT_2 = 2;
    private static final String STUDENT_PASSENGER_TYPE = "Student";

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTO1() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);

        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_1);
        ppvDetails.setCurrency(CURRENCY_1);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTO2() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(false);

        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_1);
        ppvDetails.setCurrency(CURRENCY_1);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOWithEmptyPPV() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(false);
        cardInfoResponseV2DTO.setPendingItems(new PendingItems());

        PrePayValue ppvDetails = null;
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTO3() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        List<PrePayTicketSlot> pptSlots = new ArrayList<PrePayTicketSlot>();

        pptSlots.add(getTestPrePayTicketSlot1());
        pptSlots.add(getTestPrePayTicketSlot2());
        pptSlots.add(getTestPrePayTicketSlot3());

        pptDetails.setPptSlots(pptSlots);
        cardInfoResponseV2DTO.setPptDetails(pptDetails);

        return cardInfoResponseV2DTO;
    }

    public static PrePayTicketSlot getTestPrePayTicketSlot1() {
        return getTestPptSlot(PRODUCT_1, ZONE_1, formatDate(new Date()), formatDate(DateUtil.addDaysToDate(new Date(), 6)), DISCOUNT_1, PASSENGER_TYPE_1, null);
    }

    public static PrePayTicketSlot getTestPrePayTicketSlot2() {
        return getTestPptSlot(PRODUCT_2, ZONE_2, formatDate(new Date()), formatDate(DateUtil.addDaysToDate(new Date(), 6)), DISCOUNT_2, PASSENGER_TYPE_2, null);
    }

    public static PrePayTicketSlot getTestPrePayTicketSlot3() {
        return getTestPptSlot(PRODUCT_3, ZONE_3, formatDate(new Date()), formatDate(DateUtil.addDaysToDate(new Date(), 6)), DISCOUNT_3, PASSENGER_TYPE_3, null);
    }

    public static PrePayTicketSlot getTestPrePayTicketSlot4() {
        return getTestPptSlot(PRODUCT_3, ZONE_3, formatDate(new Date()), null, DISCOUNT_3, PASSENGER_TYPE_3, null);
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTO3WithValidState() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        List<PrePayTicketSlot> pptSlots = new ArrayList<PrePayTicketSlot>();

        pptSlots.add(getTestPrePayTicketSlot1WithValidState());
        pptSlots.add(getTestPrePayTicketSlot2WithValidState());
        pptSlots.add(getTestPrePayTicketSlot3WithValidState());

        pptDetails.setPptSlots(pptSlots);
        cardInfoResponseV2DTO.setPptDetails(pptDetails);

        return cardInfoResponseV2DTO;
    }

    public static PrePayTicketSlot getTestPrePayTicketSlot1WithValidState() {
        return getTestPptSlot(PRODUCT_1, ZONE_1, formatDate(new Date()), formatDate(DateUtil.addDaysToDate(new Date(), 6)), DISCOUNT_1, PASSENGER_TYPE_1,
                        PREPAY_TICKET_VALID_STATE);
    }

    public static PrePayTicketSlot getTestPrePayTicketSlot2WithValidState() {
        return getTestPptSlot(PRODUCT_2, ZONE_2, formatDate(new Date()), formatDate(DateUtil.addDaysToDate(new Date(), 6)), DISCOUNT_2, PASSENGER_TYPE_2,
                        PREPAY_TICKET_VALID_STATE);
    }

    public static PrePayTicketSlot getTestPrePayTicketSlot3WithValidState() {
        return getTestPptSlot(PRODUCT_3, ZONE_3, formatDate(new Date()), formatDate(DateUtil.addDaysToDate(new Date(), 6)), DISCOUNT_3, PASSENGER_TYPE_3,
                        PREPAY_TICKET_VALID_STATE);
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTO3WithInValidState() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        List<PrePayTicketSlot> pptSlots = new ArrayList<PrePayTicketSlot>();

        pptSlots.add(getTestPrePayTicketSlot1WithInValidState());
        pptSlots.add(getTestPrePayTicketSlot2WithInValidState());
        pptSlots.add(getTestPrePayTicketSlot3WithInValidState());

        pptDetails.setPptSlots(pptSlots);
        cardInfoResponseV2DTO.setPptDetails(pptDetails);

        return cardInfoResponseV2DTO;
    }

    public static PrePayTicketSlot getTestPrePayTicketSlot1WithInValidState() {
        return getTestPptSlot(PRODUCT_1, ZONE_1, formatDate(new Date()), formatDate(new Date()), DISCOUNT_1, PASSENGER_TYPE_1,
                        PREPAY_TICKET_INVALID_STATE);
    }

    public static PrePayTicketSlot getTestPrePayTicketSlot2WithInValidState() {
        return getTestPptSlot(PRODUCT_2, ZONE_2, formatDate(new Date()), formatDate(new Date()), DISCOUNT_2, PASSENGER_TYPE_2,
                        PREPAY_TICKET_INVALID_STATE);
    }

    public static PrePayTicketSlot getTestPrePayTicketSlot3WithInValidState() {
        return getTestPptSlot(PRODUCT_3, ZONE_3, formatDate(new Date()), formatDate(new Date()), DISCOUNT_3, PASSENGER_TYPE_3,
                        PREPAY_TICKET_INVALID_STATE);
    }

    public static PrePayTicketSlot getTestPptSlot(String product, String zone, String startDate, String endDate, String discount,
                    String passengerType, Integer state) {
        PrePayTicketSlot pptSlot = new PrePayTicketSlot();
        pptSlot.setProduct(product);
        pptSlot.setZone(zone);
        pptSlot.setStartDate(startDate);
        pptSlot.setExpiryDate(endDate);
        pptSlot.setDiscount(discount);
        pptSlot.setPassengerType(passengerType);
        pptSlot.setState(state);
        return pptSlot;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTO4() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_1);
        ppvDetails.setCurrency(CURRENCY_1);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);

        cardInfoResponseV2DTO.setDiscountEntitlement1(JobCentrePlusDiscountTestUtil.JOB_CENTRE_PLUS);
        cardInfoResponseV2DTO.setDiscountExpiry1(formatDate(new Date()));

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOWithDiscountEntitlement2() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_1);
        ppvDetails.setCurrency(CURRENCY_1);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);

        cardInfoResponseV2DTO.setDiscountEntitlement2(JobCentrePlusDiscountTestUtil.JOB_CENTRE_PLUS);
        cardInfoResponseV2DTO.setDiscountExpiry2(formatDate(new Date()));

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOWithDiscountEntitlement3() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_1);
        ppvDetails.setCurrency(CURRENCY_1);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);

        cardInfoResponseV2DTO.setDiscountEntitlement3(JobCentrePlusDiscountTestUtil.JOB_CENTRE_PLUS);
        cardInfoResponseV2DTO.setDiscountExpiry3(formatDate(new Date()));

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTO5() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PendingItems pendingItems = new PendingItems();
        List<PrePayValue> ppvs = new ArrayList<PrePayValue>();
        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_1);
        ppvDetails.setCurrency(CURRENCY_1);
        ppvDetails.setPickupLocation(STATION_ID.intValue());
        ppvDetails.setPrePayValue(BALANCE_1);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);
        ppvs.add(ppvDetails);
        pendingItems.setPpvs(ppvs);

        cardInfoResponseV2DTO.setPendingItems(pendingItems);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTO6() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PendingItems pendingItems = new PendingItems();
        List<PrePayTicket> ppts = new ArrayList<PrePayTicket>();
        PrePayTicket ppt = new PrePayTicket();

        ppt.setStartDate(formatDate(new Date()));
        ppt.setExpiryDate(formatDate(new Date()));
        ppt.setPickupLocation(STATION_ID.intValue());
        ppt.setProductCode(PRODUCT_CODE_1);
        ppts.add(ppt);
        pendingItems.setPpts(ppts);

        cardInfoResponseV2DTO.setPendingItems(pendingItems);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTO7() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        	
        PendingItems pendingItems = new PendingItems();
        List<PrePayValue> ppvs = new ArrayList<PrePayValue>();
        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_1);
        ppvDetails.setPrePayValue(BALANCE_2);
        ppvDetails.setCurrency(CURRENCY_1);
        ppvDetails.setPickupLocation(DIFFERENT_STATION_ID.intValue());
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);
        ppvs.add(ppvDetails);
        pendingItems.setPpvs(ppvs);
        cardInfoResponseV2DTO.setPendingItems(pendingItems);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTO8() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PendingItems pendingItems = new PendingItems();
        List<PrePayTicket> ppts = new ArrayList<PrePayTicket>();
        ppts.add(null);
        pendingItems.setPpts(ppts);

        cardInfoResponseV2DTO.setPendingItems(pendingItems);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTO9() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PendingItems pendingItems = new PendingItems();
        List<PrePayTicket> ppts = new ArrayList<PrePayTicket>();
        PrePayTicket ppt = new PrePayTicket();

        ppt.setProductCode(Integer.valueOf(PRODUCT_CODE_1));
        ppt.setStartDate(formatDate(new Date()));
        ppt.setExpiryDate(formatDate(new Date()));
        ppt.setPickupLocation(STATION_ID.intValue());
        ppts.add(ppt);
        pendingItems.setPpts(ppts);

        cardInfoResponseV2DTO.setPendingItems(pendingItems);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTO10() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setCardDeposit(CARD_DEPOSIT);

        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_2);
        ppvDetails.setCurrency(CURRENCY_1);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTO11() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);

        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_3);
        ppvDetails.setCurrency(CURRENCY_1);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTO12() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);

        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_OVER_MAX);
        ppvDetails.setCurrency(CURRENCY_1);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTO13() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);

        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(220);
        ppvDetails.setCurrency(CURRENCY_1);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTO14() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PendingItems pendingItems = new PendingItems();
        List<PrePayValue> ppvs = new ArrayList<PrePayValue>();
        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_1);
        ppvDetails.setCurrency(CURRENCY_1);
        ppvDetails.setPickupLocation(DIFFERENT_STATION_ID.intValue());
        ppvDetails.setPrePayValue(BALANCE_1);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);
        ppvs.add(ppvDetails);
        pendingItems.setPpvs(ppvs);
        cardInfoResponseV2DTO.setPendingItems(pendingItems);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTO15() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        List<PrePayTicketSlot> prePayTicketSlots = new ArrayList<PrePayTicketSlot>();

        PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
        prePayTicketSlot.setExpiryDate(DateTestUtil.EXPIRY_DATE);
        prePayTicketSlot.setStartDate(DateTestUtil.START_DATE);

        prePayTicketSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(prePayTicketSlots);

        cardInfoResponseV2DTO.setPptDetails(pptDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTO16() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        SimpleDateFormat format = new SimpleDateFormat(DateConstant.SHORT_DATE_PATTERN);
        Date futureDate = DateUtil.getTomorrowDate();
        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        List<PrePayTicketSlot> prePayTicketSlots = new ArrayList<PrePayTicketSlot>();

        PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
        prePayTicketSlot.setExpiryDate(format.format(futureDate));
        prePayTicketSlot.setStartDate(DateTestUtil.START_DATE);

        prePayTicketSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(prePayTicketSlots);

        PendingItems pendingItems = new PendingItems();

        PrePayTicket ppt = new PrePayTicket();
        ppt.setPickupLocation(STATION_ID_1.intValue());
        pendingItems.setPpts(Arrays.asList(new PrePayTicket[] { ppt }));
        ppt.setStartDate(DateTestUtil.START_DATE);
        ppt.setExpiryDate(format.format(futureDate));
        cardInfoResponseV2DTO.setPendingItems(pendingItems);

        cardInfoResponseV2DTO.setPptDetails(pptDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTO17() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        List<PrePayTicketSlot> prePayTicketSlots = new ArrayList<PrePayTicketSlot>();

        prePayTicketSlots.add(getTestPrePayTicketSlot4());
        pptDetails.setPptSlots(prePayTicketSlots);

        cardInfoResponseV2DTO.setPptDetails(pptDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTO18() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        cardInfoResponseV2DTO.setPptDetails(pptDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTO19() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setPrestigeId(VALID_CARD_NUMBER_1);
        PendingItems pendingItems = new PendingItems();
        List<PrePayTicket> ppts = new ArrayList<PrePayTicket>();
        pendingItems.setPpts(ppts);

        cardInfoResponseV2DTO.setPendingItems(pendingItems);

        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOWithInvalidExpiryDate() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        List<PrePayTicketSlot> prePayTicketSlots = new ArrayList<PrePayTicketSlot>();

        PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
        prePayTicketSlot.setExpiryDate(DateTestUtil.AUG_16_WITH_TIMESTAMP_FORMAT);
        prePayTicketSlot.setStartDate(DateTestUtil.START_DATE);

        prePayTicketSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(prePayTicketSlots);

        cardInfoResponseV2DTO.setPptDetails(pptDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOWithAutoloadState() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setAutoLoadState(AutoLoadState.NO_TOP_UP.state());
        PendingItems pendingItems = new PendingItems();
        List<PrePayValue> ppvs = new ArrayList<PrePayValue>();
        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_1);
        ppvDetails.setCurrency(CURRENCY_1);
        ppvDetails.setPickupLocation(DIFFERENT_STATION_ID.intValue());
        ppvDetails.setPrePayValue(BALANCE_1);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);
        pendingItems.setPpvs(ppvs);

        cardInfoResponseV2DTO.setPendingItems(pendingItems);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTONullPendingItems() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PendingItems pendingItems = null;
        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_1);
        ppvDetails.setCurrency(CURRENCY_1);
        ppvDetails.setPickupLocation(DIFFERENT_STATION_ID.intValue());
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);

        cardInfoResponseV2DTO.setPendingItems(pendingItems);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTONullPpvLocation() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PendingItems pendingItems = new PendingItems();
        List<PrePayValue> ppvs = new ArrayList<PrePayValue>();
        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_1);
        ppvDetails.setCurrency(CURRENCY_1);
        ppvDetails.setPickupLocation(null);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);
        pendingItems.setPpvs(ppvs);

        cardInfoResponseV2DTO.setPendingItems(pendingItems);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOPrePayTicketIncorrectStationID() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PendingItems pendingItems = new PendingItems();

        PrePayTicket ppt = new PrePayTicket();
        ppt.setPickupLocation(STATION_ID_1.intValue());
        pendingItems.setPpts(Arrays.asList(new PrePayTicket[] { ppt }));

        cardInfoResponseV2DTO.setPendingItems(pendingItems);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOPrePayTicketSizeZero() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PendingItems pendingItems = new PendingItems();

        pendingItems.setPpts(Arrays.asList(new PrePayTicket[] {}));

        cardInfoResponseV2DTO.setPendingItems(pendingItems);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTONullPrePayTicket() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PendingItems pendingItems = new PendingItems();

        PrePayTicket ppt = new PrePayTicket();
        ppt.setPickupLocation(STATION_ID_1.intValue());
        pendingItems.setPpts(Arrays.asList(new PrePayTicket[] { null, ppt }));

        cardInfoResponseV2DTO.setPendingItems(pendingItems);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOWithPpvAndEmptyPptList() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PendingItems pendingItems = new PendingItems();
        List<PrePayValue> ppvs = new ArrayList<PrePayValue>();
        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_1);
        ppvDetails.setCurrency(CURRENCY_1);
        ppvDetails.setPickupLocation(STATION_ID.intValue());
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);
        ppvs.add(ppvDetails);
        pendingItems.setPpvs(ppvs);

        List<PrePayTicket> pptList = new ArrayList<PrePayTicket>();
        pendingItems.setPpts(pptList);
        cardInfoResponseV2DTO.setPendingItems(pendingItems);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOWithHotlistedCard() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setCardCapability(CARD_CAPABILITY_HOTLISTED);

        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_1);
        ppvDetails.setCurrency(CURRENCY_1);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTONonHotlistedCard() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setCardCapability(CARD_CAPABILITY_NOT_HOTLISTED);

        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_1);
        ppvDetails.setCurrency(CURRENCY_1);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOCCCLostStolenDateTime() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setCardCapability(CARD_CAPABILITY_NOT_HOTLISTED);
        cardInfoResponseV2DTO.setCccLostStolenDateTime(DateTestUtil.getAug19());
        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_1);
        ppvDetails.setCurrency(CURRENCY_1);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOWithNTickets(int n) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        ArrayList<PrePayTicketSlot> list = new ArrayList<PrePayTicketSlot>();
        list.add(null);
        list.add(null);
        list.add(null);
        pptDetails.setPptSlots(list);
        for (int i = 0; i < n; i++) {
            pptDetails.getPptSlots().set(i, getTestPrePayTicketSlot1());
        }
        cardInfoResponseV2DTO.setPptDetails(pptDetails);
        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOWithInvalidCurrency() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(false);

        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_2);
        ppvDetails.setCurrency(CURRENCY_2);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoForAdultType() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setPassengerType(ADULT_PASSENGER_TYPE);
        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_1);
        ppvDetails.setCurrency(CURRENCY_1);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);

        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getTestCardInfoForNotAdultType() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setPassengerType(STUDENT_PASSENGER_TYPE);
        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_1);
        ppvDetails.setCurrency(CURRENCY_1);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);

        return cardInfoResponseV2DTO;
    }
    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOWithMoreThenOneDayRemaining() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        SimpleDateFormat format = new SimpleDateFormat(DateConstant.SHORT_DATE_PATTERN);
        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        List<PrePayTicketSlot> prePayTicketSlots = new ArrayList<PrePayTicketSlot>();

        PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
        String furtureDate = format.format(new DateTime(new Date()).plusDays(2).toDate());
        prePayTicketSlot.setExpiryDate(furtureDate);
        prePayTicketSlot.setStartDate(DateTestUtil.START_DATE);
        prePayTicketSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(prePayTicketSlots);

        cardInfoResponseV2DTO.setPptDetails(pptDetails);
        cardInfoResponseV2DTO.setPendingItems(getTestCardInfoResponseV2DTOPendingItemTicketList().getPendingItems());

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOWithMoreThenOneDayRemainingForPendingTickets() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        SimpleDateFormat format = new SimpleDateFormat(DateConstant.SHORT_DATE_PATTERN);
        List<PrePayTicketSlot> prePayTicketSlots = new ArrayList<PrePayTicketSlot>();
        PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        prePayTicketSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(prePayTicketSlots);
        PendingItems pendingItems = new PendingItems();
        PrePayTicket ppt = new PrePayTicket();
        ppt.setPickupLocation(STATION_ID_1.intValue());
        ppt.setStartDate(DateTestUtil.START_DATE);
        String furtureDate = format.format(new DateTime(new Date()).plusDays(2).toDate());
        ppt.setExpiryDate(furtureDate);
        pendingItems.setPpts(Arrays.asList(new PrePayTicket[] { ppt }));
        cardInfoResponseV2DTO.setPendingItems(pendingItems);
        cardInfoResponseV2DTO.setPendingItems(getTestCardInfoResponseV2DTOPendingItemTicketList().getPendingItems());
        cardInfoResponseV2DTO.setPptDetails(pptDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOWithLessThenFiftyBalance() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);

        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(20);
        ppvDetails.setCurrency(CURRENCY_1);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOPendingItemTicketList() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        SimpleDateFormat format = new SimpleDateFormat(DateConstant.SHORT_DATE_PATTERN);
        PendingItems pendingItems = new PendingItems();
        PrePayTicket ppt = new PrePayTicket();
        ppt.setPickupLocation(STATION_ID_1.intValue());
        ppt.setStartDate(DateTestUtil.START_DATE);
        String furtureDate = format.format(new DateTime(new Date()).plusDays(2).toDate());
        ppt.setExpiryDate(furtureDate);
        pendingItems.setPpts(Arrays.asList(new PrePayTicket[] { ppt }));
        cardInfoResponseV2DTO.setPendingItems(pendingItems);
        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getProdouctItemOfOnlyTravelCard() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        List<PrePayTicketSlot> prePayTicketSlots = new ArrayList<PrePayTicketSlot>();
        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
        prePayTicketSlot.setStartDate(DateTestUtil.START_DATE);
        prePayTicketSlot.setProduct(TransferConstants.TRAVELCARD);
        prePayTicketSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(prePayTicketSlots);
        cardInfoResponseV2DTO.setPptDetails(pptDetails);
        PendingItems pendingItems = new PendingItems();
        PrePayTicket ppt = new PrePayTicket();
        ppt.setProductCode(PRODUCT_CODE_TRAVEL_CARD);
        pendingItems.setPpts(Arrays.asList(new PrePayTicket[] { ppt }));
        cardInfoResponseV2DTO.setPendingItems(pendingItems);
        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getProdouctItemOfTypeBus() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        List<PrePayTicketSlot> prePayTicketSlots = new ArrayList<PrePayTicketSlot>();
        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
        prePayTicketSlot.setStartDate(DateTestUtil.START_DATE);
        prePayTicketSlot.setProduct(TransferConstants.PRODUCT_TYPE_BUS);
        prePayTicketSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(prePayTicketSlots);
        cardInfoResponseV2DTO.setPptDetails(pptDetails);
        PendingItems pendingItems = new PendingItems();
        PrePayTicket ppt = new PrePayTicket();
        ppt.setProductCode(PRODUCT_CODE_TRAVEL_CARD);
        pendingItems.setPpts(Arrays.asList(new PrePayTicket[] { ppt }));
        cardInfoResponseV2DTO.setPendingItems(pendingItems);
        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getProdouctItemOfTypeBusForPrePayTicket() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        List<PrePayTicketSlot> prePayTicketSlots = new ArrayList<PrePayTicketSlot>();
        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
        prePayTicketSlot.setStartDate(DateTestUtil.START_DATE);
        prePayTicketSlot.setProduct(TransferConstants.PRODUCT_TYPE_BUS);
        prePayTicketSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(prePayTicketSlots);
        cardInfoResponseV2DTO.setPptDetails(pptDetails);
        PendingItems pendingItems = new PendingItems();
        cardInfoResponseV2DTO.setPendingItems(pendingItems);
        pendingItems.setPpts(new ArrayList<PrePayTicket>());
        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getCardWithDiscountEntitlement() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setDiscountEntitlement1(OysterCardDiscountType.EIGHTEEN_PLUS.code());
        cardInfoResponseV2DTO.setDiscountEntitlement2(OysterCardDiscountType.APPRENTICE.code());
        cardInfoResponseV2DTO.setDiscountEntitlement3(OysterCardDiscountType.JOB_CENTRE_PLUS.code());
        String futureDate = DateTestUtil.getDate(DateConstant.SHORT_DATE_PATTERN, new DateTime(new Date()).plusDays(2).toDate());
        cardInfoResponseV2DTO.setDiscountExpiry1(futureDate);
        cardInfoResponseV2DTO.setDiscountExpiry2(futureDate);
        cardInfoResponseV2DTO.setDiscountExpiry3(futureDate);
        return cardInfoResponseV2DTO;

    }

    public static CardInfoResponseV2DTO getCardWitDeposit() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setCardDeposit(CARD_DEPOSIT);
        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getCardWithNoDeposit() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setCardDeposit(0);
        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOPAYGBalanceLessThenFifty() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);

        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_4);
        ppvDetails.setCurrency(CURRENCY_1);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTravelCardWithDiscount() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        List<PrePayTicketSlot> prePayTicketSlots = new ArrayList<PrePayTicketSlot>();

        PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
        prePayTicketSlot.setStartDate(DateTestUtil.START_DATE);
        prePayTicketSlot.setDiscount(DISCOUNT_TRAVELCARD);

        prePayTicketSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(prePayTicketSlots);

        cardInfoResponseV2DTO.setPptDetails(pptDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTravelCardWithNoDiscount() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        List<PrePayTicketSlot> prePayTicketSlots = new ArrayList<PrePayTicketSlot>();

        PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
        prePayTicketSlot.setStartDate(DateTestUtil.START_DATE);
        prePayTicketSlot.setDiscount(OysterCardDiscountType.NO_DISCOUNT.code());
        prePayTicketSlot.setProduct(PRODUCT_1);
        prePayTicketSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(prePayTicketSlots);

        cardInfoResponseV2DTO.setPptDetails(pptDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getCardReponseWithPptSlots() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        List<PrePayTicketSlot> prePayTicketSlots = new ArrayList<PrePayTicketSlot>();

        PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
        prePayTicketSlot.setSlotNumber(SLOT_1);
        prePayTicketSlot.setStartDate(DateTestUtil.START_DATE);
        prePayTicketSlot.setDiscount(NO_DISCOUNT_TRAVELCARD);
        prePayTicketSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(prePayTicketSlots);
        cardInfoResponseV2DTO.setPptDetails(pptDetails);
        PendingItems pendingItems = new PendingItems();
        PrePayTicket ppt = new PrePayTicket();
        ppt.setRequestSequenceNumber(REQUEST_SEQUENCE_1);
        ppt.setProductCode(PRODUCT_CODE_TRAVEL_CARD);
        pendingItems.setPpts(Arrays.asList(new PrePayTicket[] { ppt }));
        cardInfoResponseV2DTO.setPendingItems(pendingItems);
        cardInfoResponseV2DTO.setCardCapability(CARD_CAPABILITY_NOT_HOTLISTED);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getCardReponseWithoutPpts() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        List<PrePayTicketSlot> prePayTicketSlots = new ArrayList<PrePayTicketSlot>();

        PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
        prePayTicketSlot.setSlotNumber(SLOT_1);
        prePayTicketSlot.setStartDate(DateTestUtil.START_DATE);
        prePayTicketSlot.setDiscount(NO_DISCOUNT_TRAVELCARD);
        prePayTicketSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(prePayTicketSlots);
        cardInfoResponseV2DTO.setPptDetails(pptDetails);
        PendingItems pendingItems = new PendingItems();
        PrePayTicket ppt = new PrePayTicket();
        pendingItems.setPpts(Arrays.asList(new PrePayTicket[] { ppt }));
        cardInfoResponseV2DTO.setPendingItems(pendingItems);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOPAYGBalanceLessForSourceCard() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);

        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_SRC_CARD);
        ppvDetails.setCurrency(CURRENCY_1);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOPAYGBalanceLessForTargetCard() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setCardDeposit(CARD_DEPOSIT);

        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_TARGET_CARD);
        ppvDetails.setCurrency(CURRENCY_1);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);
        PendingItems pendingItems = new PendingItems();
        PrePayValue pendingPrePayvalue = new PrePayValue();
        pendingPrePayvalue.setPrePayValue(BALANCE_OVER_MAX);
        List<PrePayValue> ppvs = new ArrayList<PrePayValue>();
        ppvs.add(pendingPrePayvalue);
        pendingItems.setPpvs(ppvs);
        cardInfoResponseV2DTO.setPendingItems(pendingItems);
        
        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOPAYGBalanceMoreForSourceCard() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);

        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_2);
        ppvDetails.setCurrency(CURRENCY_1); 
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOPAYGBalanceMoreForTargetCard() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setCardDeposit(CARD_DEPOSIT);

        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_2);
        ppvDetails.setCurrency(CURRENCY_1);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);
        PendingItems pendingItems = new PendingItems();
        PrePayValue pendingPrePayvalue = new PrePayValue();
        pendingPrePayvalue.setPrePayValue(BALANCE_0);
        List<PrePayValue> ppvs = new ArrayList<PrePayValue>();
        ppvs.add(pendingPrePayvalue);
        pendingItems.setPpvs(ppvs);
        cardInfoResponseV2DTO.setPendingItems(pendingItems);
        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getCardReponseWithMoreTwoPptSlots() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        List<PrePayTicketSlot> prePayTicketSlots = new ArrayList<PrePayTicketSlot>();

        PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
        prePayTicketSlot.setSlotNumber(SLOT_1);
        prePayTicketSlot.setStartDate(DateTestUtil.START_DATE);
        prePayTicketSlot.setDiscount(NO_DISCOUNT_TRAVELCARD);
        prePayTicketSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(prePayTicketSlots);
        cardInfoResponseV2DTO.setPptDetails(pptDetails);
        prePayTicketSlot.setSlotNumber(SLOT_2);
        prePayTicketSlot.setStartDate(DateTestUtil.START_DATE);
        prePayTicketSlot.setDiscount(NO_DISCOUNT_TRAVELCARD);
        prePayTicketSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(prePayTicketSlots);
        cardInfoResponseV2DTO.setPptDetails(pptDetails);
        PendingItems pendingItems = new PendingItems();
        PrePayTicket ppt = new PrePayTicket();
        ppt.setRequestSequenceNumber(REQUEST_SEQUENCE_1);
        ppt.setProductCode(PRODUCT_CODE_TRAVEL_CARD);
        pendingItems.setPpts(Arrays.asList(new PrePayTicket[] { ppt }));
        cardInfoResponseV2DTO.setPendingItems(pendingItems);
        cardInfoResponseV2DTO.setCardCapability(CARD_CAPABILITY_NOT_HOTLISTED);

        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOWithHotListReasons(){
        CardInfoResponseV2DTO cardInfo = getTestCardInfoResponseV2DTOWithHotlistedCard();
        HotListReasons hotListReasons = new HotListReasons();
        ArrayList<Integer> hotListReasonCodes = new ArrayList<Integer>();
        hotListReasonCodes.add(1);
        hotListReasons.setHotListReasonCodes(hotListReasonCodes);
        
        cardInfo.setHotListReasons(hotListReasons);
        return cardInfo;
    }
    
    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOPAYGBalanceWithoutGBPCurrency(){
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);

        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_2);
        ppvDetails.setCurrency(EURO);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);

        return cardInfoResponseV2DTO;
    }

    public static CardInfoResponseV2DTO getEighteenPlusDiscounted() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setDiscountEntitlement1(OysterCardDiscountType.EIGHTEEN_PLUS.code());
        return cardInfoResponseV2DTO;

    }

    public static CardInfoResponseV2DTO getApprenticeDiscounted() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setDiscountEntitlement1(OysterCardDiscountType.APPRENTICE.code());
        return cardInfoResponseV2DTO;

    }

    public static CardInfoResponseV2DTO getTravelCardEighteenPlusDiscounted() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        List<PrePayTicketSlot> prePayTicketSlots = new ArrayList<PrePayTicketSlot>();
        PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
        prePayTicketSlot.setStartDate(DateTestUtil.START_DATE);
        prePayTicketSlot.setDiscount(OysterCardDiscountType.EIGHTEEN_PLUS.code());
        prePayTicketSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(prePayTicketSlots);
        cardInfoResponseV2DTO.setPptDetails(pptDetails);
        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getCardEighteenPlusDiscounted() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setDiscountEntitlement1(OysterCardDiscountType.EIGHTEEN_PLUS.code());
        String furtureDate = DateTestUtil.getDate(DateConstant.SHORT_DATE_PATTERN, new DateTime(new Date()).plusDays(2).toDate());
        cardInfoResponseV2DTO.setDiscountExpiry1(furtureDate);
        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getCardEighteenPlusDiscountedForDiscountEntitlement2() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setDiscountEntitlement2(OysterCardDiscountType.EIGHTEEN_PLUS.code());
        String furtureDate = DateTestUtil.getDate(DateConstant.SHORT_DATE_PATTERN, new DateTime(new Date()).plusDays(2).toDate());
        cardInfoResponseV2DTO.setDiscountExpiry2(furtureDate);
        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getCardEighteenPlusDiscountedForDiscountEntitlement3() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setDiscountEntitlement3(OysterCardDiscountType.EIGHTEEN_PLUS.code());
        String furtureDate = DateTestUtil.getDate(DateConstant.SHORT_DATE_PATTERN, new DateTime(new Date()).plusDays(2).toDate());
        cardInfoResponseV2DTO.setDiscountExpiry3(furtureDate);
        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getCardWithJobCenterPlusDiscount() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setDiscountEntitlement1(OysterCardDiscountType.JOB_CENTRE_PLUS.code());
        String furtureDate = DateTestUtil.getDate(DateConstant.SHORT_DATE_PATTERN, new DateTime(new Date()).plusDays(2).toDate());
        cardInfoResponseV2DTO.setDiscountExpiry1(furtureDate);

        return cardInfoResponseV2DTO;

    }
    
    public static CardInfoResponseV2DTO getCardWithJobCenterPlusDiscountEntitlement2() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setDiscountEntitlement2(OysterCardDiscountType.JOB_CENTRE_PLUS.code());
        String furtureDate = DateTestUtil.getDate(DateConstant.SHORT_DATE_PATTERN, new DateTime(new Date()).plusDays(2).toDate());
        cardInfoResponseV2DTO.setDiscountExpiry2(furtureDate);

        return cardInfoResponseV2DTO;

    }
    
    public static CardInfoResponseV2DTO getCardWithJobCenterPlusDiscountEntitlement3() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setDiscountEntitlement3(OysterCardDiscountType.JOB_CENTRE_PLUS.code());
        String furtureDate = DateTestUtil.getDate(DateConstant.SHORT_DATE_PATTERN, new DateTime(new Date()).plusDays(2).toDate());
        cardInfoResponseV2DTO.setDiscountExpiry3(furtureDate);
        return cardInfoResponseV2DTO;
    }
    
    
    public static CardInfoResponseV2DTO getCardWithApprenticeDiscount() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setDiscountEntitlement1(OysterCardDiscountType.APPRENTICE.code());
        String furtureDate = DateTestUtil.getDate(DateConstant.SHORT_DATE_PATTERN, new DateTime(new Date()).plusDays(2).toDate());
        cardInfoResponseV2DTO.setDiscountExpiry1(furtureDate);
        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getCardWithApprenticeDiscountExpired() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setDiscountEntitlement2(OysterCardDiscountType.APPRENTICE.code());
        String pastDate = DateTestUtil.getDate(DateConstant.SHORT_DATE_PATTERN, new DateTime(new Date()).minusDays(2).toDate());
        cardInfoResponseV2DTO.setDiscountExpiry2(pastDate);
        return cardInfoResponseV2DTO; 

    }
    
    public static CardInfoResponseV2DTO getCardWithApprenticeDiscountExpiredForDiscountEntitlement2() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setDiscountEntitlement2(OysterCardDiscountType.APPRENTICE.code());
        String furtureDate = DateTestUtil.getDate(DateConstant.SHORT_DATE_PATTERN, new DateTime(new Date()).plusDays(2).toDate());
        cardInfoResponseV2DTO.setDiscountExpiry2(furtureDate);
        return cardInfoResponseV2DTO; 

    }
    
    public static CardInfoResponseV2DTO getCardWithApprenticeDiscountExpiredForDiscountEntitlement3() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setDiscountEntitlement3(OysterCardDiscountType.APPRENTICE.code());
        String furtureDate = DateTestUtil.getDate(DateConstant.SHORT_DATE_PATTERN, new DateTime(new Date()).plusDays(2).toDate());
        cardInfoResponseV2DTO.setDiscountExpiry3(furtureDate);
        return cardInfoResponseV2DTO; 

    }
    
    public static CardInfoResponseV2DTO getCardWithApprenticeDiscountExpiryFieldEmpty() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setDiscountEntitlement2(OysterCardDiscountType.APPRENTICE.code());
        return cardInfoResponseV2DTO;

    }
    
    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOWithAutoloadStateForPendingTopUp() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setAutoLoadState(AutoLoadState.TOP_UP_AMOUNT_3.state());
        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOWithAutoloadStateForNoPendingTopUp() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(true);
        cardInfoResponseV2DTO.setAutoLoadState(AutoLoadState.NO_TOP_UP.state());
        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getCardReponseWithTravelCards() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        List<PrePayTicketSlot> prePayTicketSlots = new ArrayList<PrePayTicketSlot>();
        PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
        prePayTicketSlot.setSlotNumber(SLOT_1);
        prePayTicketSlot.setProduct(TransferConstants.TRAVELCARD);
        prePayTicketSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(prePayTicketSlots);
        cardInfoResponseV2DTO.setPptDetails(pptDetails);
        prePayTicketSlot.setSlotNumber(SLOT_2);
        prePayTicketSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(prePayTicketSlots);
        cardInfoResponseV2DTO.setPptDetails(pptDetails);
        PendingItems pendingItems = new PendingItems();
        PrePayTicket ppt = new PrePayTicket();
        ppt.setRequestSequenceNumber(REQUEST_SEQUENCE_1);
        ppt.setProductCode(PRODUCT_CODE_TRAVEL_CARD);
        pendingItems.setPpts(Arrays.asList(new PrePayTicket[] { ppt }));
        cardInfoResponseV2DTO.setPendingItems(pendingItems);
        cardInfoResponseV2DTO.setCardCapability(CARD_CAPABILITY_NOT_HOTLISTED);
        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getCardWithJobCenterPlusDiscountOnTravelCards() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        List<PrePayTicketSlot> prePayTicketSlots = new ArrayList<PrePayTicketSlot>();
        PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
        prePayTicketSlot.setSlotNumber(SLOT_1);
        prePayTicketSlot.setProduct(TransferConstants.TRAVELCARD);
        prePayTicketSlots.add(prePayTicketSlot);
        prePayTicketSlot.setDiscount(OysterCardDiscountType.JOB_CENTRE_PLUS.code());
        pptDetails.setPptSlots(prePayTicketSlots);
        cardInfoResponseV2DTO.setPptDetails(pptDetails);
        prePayTicketSlot.setSlotNumber(SLOT_2);
        prePayTicketSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(prePayTicketSlots);
        cardInfoResponseV2DTO.setPptDetails(pptDetails);

        return cardInfoResponseV2DTO;

    }
    
    public static CardInfoResponseV2DTO getTestCardInfoResponseV2DTOWithAutoTopUpStateDisabled() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setAutoTopUpEnabled(false);
        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getCardReponseWithPendingTravelCards() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        List<PrePayTicketSlot> prePayTicketSlots = new ArrayList<PrePayTicketSlot>();
        PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
        prePayTicketSlot.setSlotNumber(SLOT_1);
        prePayTicketSlot.setProduct(TransferConstants.PRODUCT_TYPE_BUS);
        prePayTicketSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(prePayTicketSlots);
        cardInfoResponseV2DTO.setPptDetails(pptDetails);
        PendingItems pendingItems = new PendingItems();
        PrePayTicket ppt = new PrePayTicket();
        ppt.setRequestSequenceNumber(REQUEST_SEQUENCE_1);
        ppt.setProductCode(PRODUCT_CODE_TRAVEL_CARD);
        pendingItems.setPpts(Arrays.asList(new PrePayTicket[] { ppt }));
        cardInfoResponseV2DTO.setPendingItems(pendingItems);
        cardInfoResponseV2DTO.setCardCapability(CARD_CAPABILITY_NOT_HOTLISTED);
        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getCardReponseWithPayAsyouItemMoreThenZero() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardReponseWithTravelCards();
        cardInfoResponseV2DTO.setCardDeposit(BALANCE_0);
        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(BALANCE_1);
        ppvDetails.setCurrency(CURRENCY_1);
        ppvDetails.setPrePayValue(BALANCE_2);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);
        PendingItems pendingItems = new PendingItems();
        List<PrePayValue> ppvs = new ArrayList<PrePayValue>();
        ppvs.add(ppvDetails);
        pendingItems.setPpvs(ppvs);
        cardInfoResponseV2DTO.setPendingItems(pendingItems);
        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getCardReponseWithPayAsYouGoItemLessThenZero() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setCardDeposit(BALANCE_0);
        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(NEGATIVE_BALANCE);
        ppvDetails.setCurrency(CURRENCY_1);
        ppvDetails.setPrePayValue(0);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);
        PendingItems pendingItems = new PendingItems();
        List<PrePayValue> ppvs = new ArrayList<PrePayValue>();
        ppvs.add(ppvDetails);
        pendingItems.setPpvs(ppvs);
        cardInfoResponseV2DTO.setPendingItems(pendingItems);
        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getCardReponseWithPayAsYouGoItemIsZero() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = getProdouctItemOfTypeBusForPrePayTicket();

        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(0);
        ppvDetails.setCurrency(CURRENCY_1);
        ppvDetails.setPrePayValue(0);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);
        PendingItems pendingItems = new PendingItems();
        List<PrePayValue> ppvs = new ArrayList<PrePayValue>();
        ppvs.add(ppvDetails);
        pendingItems.setPpvs(ppvs);
        cardInfoResponseV2DTO.setPendingItems(pendingItems);
        pendingItems.setPpts(new ArrayList<PrePayTicket>());
        cardInfoResponseV2DTO.setPendingItems(pendingItems);
        cardInfoResponseV2DTO.setCardDeposit(BALANCE_0);
        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getCardReponseWithTotalAmountMoreThenLimit() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = getProdouctItemOfTypeBusForPrePayTicket();

        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(0);
        ppvDetails.setCurrency(CURRENCY_1);
        ppvDetails.setPrePayValue(0);
        ppvDetails.setBalance(BALANCE_OVER_MAX);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);
        PendingItems pendingItems = new PendingItems();
        List<PrePayValue> ppvs = new ArrayList<PrePayValue>();
        ppvs.add(ppvDetails);
        pendingItems.setPpvs(ppvs);
        cardInfoResponseV2DTO.setPendingItems(pendingItems);
        pendingItems.setPpts(new ArrayList<PrePayTicket>());
        cardInfoResponseV2DTO.setPendingItems(pendingItems);
        cardInfoResponseV2DTO.setCardDeposit(CARD_DEPOSIT);
        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getCardReponseWithTotalAmountLessThenLimit() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = getProdouctItemOfTypeBusForPrePayTicket();

        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setBalance(0);
        ppvDetails.setCurrency(CURRENCY_1);
        ppvDetails.setPrePayValue(0);
        ppvDetails.setBalance(BALANCE_2);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);
        PendingItems pendingItems = new PendingItems();
        List<PrePayValue> ppvs = new ArrayList<PrePayValue>();
        ppvs.add(ppvDetails);
        pendingItems.setPpvs(ppvs);
        cardInfoResponseV2DTO.setPendingItems(pendingItems);
        pendingItems.setPpts(new ArrayList<PrePayTicket>());
        cardInfoResponseV2DTO.setPendingItems(pendingItems);
        cardInfoResponseV2DTO.setCardDeposit(CARD_DEPOSIT);
        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getCardReponseWithTotalPayAsYouGo() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardReponseWithTravelCards();
        cardInfoResponseV2DTO.setCardDeposit(BALANCE_0);
        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setCurrency(CURRENCY_1);
        ppvDetails.setPrePayValue(PRE_PAY_VALUE);
        ppvDetails.setBalance(BALANCE_0);
        cardInfoResponseV2DTO.setPpvDetails(ppvDetails);
        PendingItems pendingItems = new PendingItems();
        List<PrePayValue> ppvs = new ArrayList<PrePayValue>();
        ppvs.add(ppvDetails);
        pendingItems.setPpvs(ppvs);
        cardInfoResponseV2DTO.setPendingItems(pendingItems);
        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getCardReponseWithTravelCardWithMoreThenADayRemaining() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        SimpleDateFormat format = new SimpleDateFormat(DateConstant.SHORT_DATE_PATTERN);
        Date futureDate = DateUtil.getTomorrowDate();
        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        List<PrePayTicketSlot> prePayTicketSlots = new ArrayList<PrePayTicketSlot>();

        PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
        prePayTicketSlot.setExpiryDate(format.format(futureDate));
        prePayTicketSlot.setStartDate(DateTestUtil.START_DATE);

        prePayTicketSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(prePayTicketSlots);
        cardInfoResponseV2DTO.setPptDetails(pptDetails);
        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getCardReponseWithTravelCardWithCurrentDate() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        SimpleDateFormat format = new SimpleDateFormat(DateConstant.SHORT_DATE_PATTERN);
        Date currentDate = new Date() ;
        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        List<PrePayTicketSlot> prePayTicketSlots = new ArrayList<PrePayTicketSlot>();

        PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
        prePayTicketSlot.setExpiryDate(format.format(currentDate));
        prePayTicketSlot.setStartDate(DateTestUtil.START_DATE);

        prePayTicketSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(prePayTicketSlots);
        cardInfoResponseV2DTO.setPptDetails(pptDetails);
        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getCardReponseWithTravelCardOddPeriodDuration() {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();

        PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        List<PrePayTicketSlot> prePayTicketSlots = new ArrayList<PrePayTicketSlot>();

        PrePayTicketSlot prePayTicketSlot = new PrePayTicketSlot();
        prePayTicketSlot.setProduct(TransferConstants.TRAVELCARD);
        prePayTicketSlot.setStartDate(DateTestUtil.DATE_31_07_2014);
        prePayTicketSlot.setExpiryDate(DateTestUtil.DATE_10_09_2014);
        prePayTicketSlot.setZone(ZONE_1);
        
        prePayTicketSlots.add(prePayTicketSlot);
        pptDetails.setPptSlots(prePayTicketSlots);
        cardInfoResponseV2DTO.setPptDetails(pptDetails);
        return cardInfoResponseV2DTO;
    }
    
    public static CardInfoResponseV2DTO getCardResponseWithMaxPendingItems(){
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        PendingItems pendingItems = new PendingItems();
        PrePayValue ppvDetails = new PrePayValue();
        ppvDetails.setCurrency(CURRENCY_1);
        ppvDetails.setPrePayValue(PRE_PAY_VALUE);
        ppvDetails.setBalance(BALANCE_0);
        List<PrePayValue> ppvs = new ArrayList<PrePayValue>();
        ppvs.add(ppvDetails);
        pendingItems.setPpvs(ppvs);
        PrePayTicket ppt = new PrePayTicket();
        ppt.setRequestSequenceNumber(REQUEST_SEQUENCE_1);
        ppt.setProductCode(PRODUCT_CODE_TRAVEL_CARD);
        pendingItems.setPpts(Arrays.asList(new PrePayTicket[] { ppt, ppt, ppt }));
        cardInfoResponseV2DTO.setPendingItems(pendingItems);
        return cardInfoResponseV2DTO;
    }
}
