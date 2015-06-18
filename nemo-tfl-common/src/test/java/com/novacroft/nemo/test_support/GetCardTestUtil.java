package com.novacroft.nemo.test_support;

import com.novacroft.nemo.common.domain.cubic.PrePayValue;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoResponseV2;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

/**
 * Utilities for Card tests
 */
public final class GetCardTestUtil extends CommonCardTestUtil {

    public final static String PRESTIGE_ID_1 = "0123456789";
    public final static String PRESTIGE_ID_2 = "0123456790";
    public final static String PRESTIGE_ID_3 = "0123456791";
    public final static String PHOTO_CARD_NUMBER = "7846526";
    public final static Integer REGISTERED = 1;
    public final static String PASSENGER_TYPE = "test-passenger-type";
    public final static Integer AUTOLOAD_STATE_TOP_UP_AMOUNT_1_CONFIGURED = 2;
    public final static Integer AUTOLOAD_STATE_TOP_UP_AMOUNT_2_CONFIGURED = 3;
    public final static Integer AUTOLOAD_STATE_NO_AUTO_LOAD_CONFIGURED = 1;
    public final static String DISCOUNT_ENTITLEMENT_1 = "discount-1";
    public final static String DISCOUNT_ENTITLEMENT_2 = "discount-2";
    public final static String DISCOUNT_ENTITLEMENT_3 = "discount-3";
    public final static String DISCOUNT_EXPIRY_1 = "01/10/2004";
    public final static String DISCOUNT_EXPIRY_2 = "09/11/2013";
    public final static String DISCOUNT_EXPIRY_3 = "05/07/2014";
    public static final Integer TEST_ERROR_CODE_1 = 111;
    public static final String TEST_ERROR_DESCRIPTION_1 = "GET CARD CONFIG TEST ERROR";
    public static final String CARD_NUMBER_1 = "123456789101";
    public static final Long CARD_ID = 100l;
    public static final Integer PREPAY_BALANCE_0 = new Integer(0);
    public static final Integer PREPAY_CURRENCY = new Integer(0);
    public static final Long PREPAY_LOCATION = new Long(500);
    public static final Integer PREPAY_VALUE = new Integer(0);
    public static final Integer CUBIC_REQUEST_NUMBER_1 = new Integer(1);

    public static final String getTestGetCardResposneXml1() {
        return String.format("<CardInfoResponseV2><PrestigeID>%s</PrestigeID><PhotocardNumber>%s</PhotocardNumber>" +
                        "<Registered>%s</Registered><PassengerType>%s</PassengerType><AutoloadState>%s</AutoloadState>" +
 "<DiscountEntitleMent1>%s</DiscountEntitleMent1><DiscountExpiry1>%s</DiscountExpiry1>"
                        + "<DiscountEntitleMent2>%s</DiscountEntitleMent2><DiscountExpiry2>%s</DiscountExpiry2"
                        + "><DiscountEntitleMent3>%s</DiscountEntitleMent3>" +
                        "<DiscountExpiry3>%s</DiscountExpiry3></CardInfoResponseV2>", PRESTIGE_ID_1, PHOTO_CARD_NUMBER,
                REGISTERED, PASSENGER_TYPE, AUTOLOAD_STATE_TOP_UP_AMOUNT_1_CONFIGURED, DISCOUNT_ENTITLEMENT_1,
                DISCOUNT_EXPIRY_1, DISCOUNT_ENTITLEMENT_2, DISCOUNT_EXPIRY_2, DISCOUNT_ENTITLEMENT_3, DISCOUNT_EXPIRY_3);
    }

    public static final String getTestRequestFailureXml1() {
        return String
                .format("<RequestFailure><ErrorCode>%s</ErrorCode><ErrorDescription>%s</ErrorDescription></RequestFailure>",
                        TEST_ERROR_CODE_1, TEST_ERROR_DESCRIPTION_1);
    }

    public static final CardInfoResponseV2DTO getTestSuccessCardInfoResponseV2DTO1() {
        return getTestCardInfoResponseV2DTO(PRESTIGE_ID_1, PHOTO_CARD_NUMBER, REGISTERED, PASSENGER_TYPE,
                AUTOLOAD_STATE_TOP_UP_AMOUNT_1_CONFIGURED, DISCOUNT_ENTITLEMENT_1, DISCOUNT_EXPIRY_1, DISCOUNT_ENTITLEMENT_2,
                DISCOUNT_EXPIRY_2, DISCOUNT_ENTITLEMENT_3, DISCOUNT_EXPIRY_3);
    }

    public static final CardInfoResponseV2DTO getTestSuccessCardInfoResponseV2DTO2() {
        return getTestCardInfoResponseV2DTO(PRESTIGE_ID_2, PHOTO_CARD_NUMBER, REGISTERED, PASSENGER_TYPE,
                AUTOLOAD_STATE_TOP_UP_AMOUNT_2_CONFIGURED, DISCOUNT_ENTITLEMENT_1, DISCOUNT_EXPIRY_1, DISCOUNT_ENTITLEMENT_2,
                DISCOUNT_EXPIRY_2, DISCOUNT_ENTITLEMENT_3, DISCOUNT_EXPIRY_3);
    }

    public static final CardInfoResponseV2DTO getTestSuccessCardInfoResponseV2DTO3() {
        return getTestCardInfoResponseV2DTO(PRESTIGE_ID_3, PHOTO_CARD_NUMBER, REGISTERED, PASSENGER_TYPE,
                AUTOLOAD_STATE_NO_AUTO_LOAD_CONFIGURED, DISCOUNT_ENTITLEMENT_1, DISCOUNT_EXPIRY_1, DISCOUNT_ENTITLEMENT_2,
                DISCOUNT_EXPIRY_2, DISCOUNT_ENTITLEMENT_3, DISCOUNT_EXPIRY_3);
    }

    public static final CardInfoResponseV2DTO getTestSuccessCardInfoResponseV2DTOWithPrePayDetails() {
        CardInfoResponseV2DTO dto = getTestCardInfoResponseV2DTO(PRESTIGE_ID_3, PHOTO_CARD_NUMBER, REGISTERED, PASSENGER_TYPE,
                AUTOLOAD_STATE_NO_AUTO_LOAD_CONFIGURED, DISCOUNT_ENTITLEMENT_1, DISCOUNT_EXPIRY_1, DISCOUNT_ENTITLEMENT_2,
                DISCOUNT_EXPIRY_2, DISCOUNT_ENTITLEMENT_3, DISCOUNT_EXPIRY_3);
        dto.setPpvDetails(getPrePayValue());
        return dto;
    }

    public static final CardInfoResponseV2DTO getTestSuccessCardInfoResponseV2DTOWithNoPrePayDetails() {
        CardInfoResponseV2DTO dto = getTestCardInfoResponseV2DTO(PRESTIGE_ID_3, PHOTO_CARD_NUMBER, REGISTERED, PASSENGER_TYPE,
                AUTOLOAD_STATE_NO_AUTO_LOAD_CONFIGURED, DISCOUNT_ENTITLEMENT_1, DISCOUNT_EXPIRY_1, DISCOUNT_ENTITLEMENT_2,
                DISCOUNT_EXPIRY_2, DISCOUNT_ENTITLEMENT_3, DISCOUNT_EXPIRY_3);
        dto.setPpvDetails(new PrePayValue());
        return dto;
    }

    public static final CardInfoResponseV2DTO getTestFailureCardInfoResponseV2DTO2() {
        return new CardInfoResponseV2DTO(TEST_ERROR_CODE_1, TEST_ERROR_DESCRIPTION_1);
    }

    private static CardInfoResponseV2DTO getTestCardInfoResponseV2DTO(String prestigeId, String photoCardNumber,
                                                                      Integer registered, String passengerType,
                                                                      Integer autoLoadState, String discountEntitleMent1,
                                                                      String discountExpiry1, String discountEntitleMent2,
                                                                      String discountExpiry2, String discountEntitleMent3,
                                                                      String discountExpiry3) {
        CardInfoResponseV2DTO dto = new CardInfoResponseV2DTO();
        dto.setPrestigeId(prestigeId);
        dto.setPhotoCardNumber(photoCardNumber);
        dto.setRegistered(registered);
        dto.setPassengerType(passengerType);
        dto.setAutoLoadState(autoLoadState);
        dto.setDiscountEntitlement1(discountEntitleMent1);
        dto.setDiscountExpiry1(discountExpiry1);
        dto.setDiscountEntitlement2(discountEntitleMent2);
        dto.setDiscountExpiry2(discountExpiry2);
        dto.setDiscountEntitlement3(discountEntitleMent3);
        dto.setDiscountExpiry3(discountExpiry3);
        return dto;
    }

    public static final CardInfoResponseV2 getCardInfoRequestV2WithAutoLoadStateNoTopUpConfigured() {
        return getTestCardInfoResponseV2(PRESTIGE_ID_1, AUTOLOAD_STATE_NO_AUTO_LOAD_CONFIGURED);
    }

    public static final CardInfoResponseV2 getCardInfoRequestV2WithAutoLoadState2TopUpConfigured() {
        return getTestCardInfoResponseV2(PRESTIGE_ID_1, AUTOLOAD_STATE_TOP_UP_AMOUNT_1_CONFIGURED);
    }

    public static final CardInfoResponseV2 getCardInfoRequestV2WithAutoLoadState3TopUpConfigured() {
        return getTestCardInfoResponseV2(PRESTIGE_ID_1, AUTOLOAD_STATE_TOP_UP_AMOUNT_2_CONFIGURED);
    }

    private static CardInfoResponseV2 getTestCardInfoResponseV2(String prestigeId, Integer autoLoadStateNoAutoTopUpConfigured) {
        CardInfoResponseV2 cardInfoResponseV2 = new CardInfoResponseV2();
        cardInfoResponseV2.setPrestigeId(prestigeId);
        cardInfoResponseV2.setAutoLoadState(autoLoadStateNoAutoTopUpConfigured);
        return cardInfoResponseV2;
    }

    public static PrePayValue getPrePayValue() {
        PrePayValue dto = new PrePayValue();
        dto.setBalance(PREPAY_BALANCE_0);
        dto.setCurrency(PREPAY_CURRENCY);
        dto.setPrePayValue(PREPAY_VALUE);
        return dto;
    }
}
