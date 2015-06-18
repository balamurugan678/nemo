package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.LocationTestUtil.LOCATION_ID_1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.REQUEST_SEQUENCE_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.CubicConstant.DEFAULT_REAL_TIME_FLAG;
import static com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeRequestDTO.DEFAULT_PAYMENT_METHOD;

import com.novacroft.nemo.tfl.common.domain.cubic.AutoLoadRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.AutoLoadResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeRequestDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeResponseDTO;

/**
 * Fixtures and utilities for auto load configuration change tests
 */
public final class AutoLoadConfigurationChangeTestUtil {
    public static final String TEST_PRESTIGE_ID_1 = "012345678911";
    public static final Integer TEST_AVAILABLE_SLOTS_1 = 9;
    public static final Integer TEST_ERROR_CODE_1 = 99;
    public static final String TEST_ERROR_DESCRIPTION_1 = "AUTO LOAD CHANGE CONFIG TEST ERROR";
    public static final Integer TEST_AMOUNT_1 = 0;
    public static final Integer TEST_AMOUNT_2 = 2000;
    public static final Integer TEST_AMOUNT_3 = 4000;
    public static final Integer TEST_AMOUNT_INVALID = 9999;
    public static final Integer TEST_AUTO_LOAD_STATE_1 = 1;
    public static final Integer TEST_AUTO_LOAD_STATE_2 = 2;
    public static final Integer TEST_AUTO_LOAD_STATE_3 = 3;
    public static final Integer TEST_AUTO_LOAD_STATE_4 = 4;
    public static final String TEST_USER_ID_1 = "testUser1";
    public static final String TEST_PASSWORD_1 = "testPassword1";

    public static final String getTestAutoLoadResponseXml1() {
        return String.format("<AutoLoadResponse><PrestigeID>%s</PrestigeID>" +
                "<RequestSequenceNumber>%s</RequestSequenceNumber><AutoLoadState>%s</AutoLoadState>" +
                "<LocationInfo><PickupLocation>%s</PickupLocation>" +
                "<AvailableSlots>%s</AvailableSlots></LocationInfo></AutoLoadResponse>", TEST_PRESTIGE_ID_1,
                REQUEST_SEQUENCE_NUMBER, TEST_AUTO_LOAD_STATE_2, LOCATION_ID_1, TEST_AVAILABLE_SLOTS_1);
    }

    public static final String getTestRequestFailureXml1() {
        return String
                .format("<RequestFailure><ErrorCode>%s</ErrorCode><ErrorDescription>%s</ErrorDescription></RequestFailure>",
                        TEST_ERROR_CODE_1, TEST_ERROR_DESCRIPTION_1);
    }

    public static final String getTestRequestXml1() {
        return String.format("<AutoLoadRequest>" +
                "<RealTimeFlag>N</RealTimeFlag>" +
                "<PrestigeID>%s</PrestigeID>" +
                "<AutoLoadState>%s</AutoLoadState>" +
                "<PaymentMethod>32</PaymentMethod>" +
                "<PickupLocation>%s</PickupLocation>" +
                "<OriginatorInfo>" +
                "<UserID>%s</UserID>" +
                "<Password>%s</Password>" +
                "</OriginatorInfo>" +
                "</AutoLoadRequest>", TEST_PRESTIGE_ID_1, TEST_AUTO_LOAD_STATE_2, LOCATION_ID_1, TEST_USER_ID_1,
                TEST_PASSWORD_1);
    }
    
    public static final String getTestRequestPrepayValueXml1() {
        return String.format("<CardUpdateRequest>" +
                "<RealTimeFlag>N</RealTimeFlag>" +
                "<PrestigeID>%s</PrestigeID>" +
                "<Action>%s</Action>" +
                "<PPV><PrepayValue>%s<PrepayValue>" +
                "<Currency>%s<Currency></PPV>" +
                "<PickupLocation>%s</PickupLocation>" +
                "<PaymentMethod>32</PaymentMethod>" +
                "<OriginatorInfo>" +
                "<UserID>%s</UserID>" +
                "<Password>%s</Password>" +
                "</OriginatorInfo>" +
                "</AutoLoadRequest>", TEST_PRESTIGE_ID_1, "ADD", "2000" , "0",  LOCATION_ID_1, TEST_USER_ID_1,
                TEST_PASSWORD_1);
    }
    

    public static final AutoLoadChangeResponseDTO getTestSuccessAutoLoadChangeResponseDTO1() {
        return getTestAutoLoadChangeResponseDTO(TEST_PRESTIGE_ID_1, REQUEST_SEQUENCE_NUMBER, TEST_AUTO_LOAD_STATE_2,
                LOCATION_ID_1, TEST_AVAILABLE_SLOTS_1);
    }

    public static final AutoLoadChangeResponseDTO getTestFailAutoLoadChangeResponseDTO1() {
        return getTestAutoLoadChangeResponseDTO(TEST_ERROR_CODE_1, TEST_ERROR_DESCRIPTION_1);
    }

    public static final AutoLoadChangeResponseDTO getTestAutoLoadChangeResponseDTO(String prestigeId,
                                                                                   Integer requestSequenceNumber,
                                                                                   Integer autoLoadState, Long pickUpLocation,
                                                                                   Integer availableSlots) {
        return new AutoLoadChangeResponseDTO(prestigeId, requestSequenceNumber, autoLoadState, pickUpLocation, availableSlots);
    }

    public static final AutoLoadChangeRequestDTO getTestAutoLoadChangeRequestDTO1() {
        return getTestAutoLoadChangeRequestDTO(TEST_PRESTIGE_ID_1, TEST_AUTO_LOAD_STATE_2, LOCATION_ID_1, TEST_USER_ID_1,
                TEST_PASSWORD_1);
    }

    public static final AutoLoadRequest getTestAutoLoadRequest1() {
        return getTestAutoLoadRequest(DEFAULT_REAL_TIME_FLAG, TEST_PRESTIGE_ID_1, TEST_AUTO_LOAD_STATE_2,
                DEFAULT_PAYMENT_METHOD, LOCATION_ID_1.intValue(), TEST_USER_ID_1, TEST_PASSWORD_1);
    }

    public static final AutoLoadResponse getTestAutoLoadResponse1() {
        return getTestAutoLoadResponse(TEST_PRESTIGE_ID_1, REQUEST_SEQUENCE_NUMBER, TEST_AUTO_LOAD_STATE_2,
                LOCATION_ID_1.intValue(), TEST_AVAILABLE_SLOTS_1);
    }

    public static final RequestFailure getTestRequestFailure1() {
        return getTestRequestFailure(TEST_ERROR_CODE_1, TEST_ERROR_DESCRIPTION_1);
    }

    public static final AutoLoadChangeResponseDTO getTestAutoLoadChangeResponseDTO(Integer errorCode, String errorDescription) {
        return new AutoLoadChangeResponseDTO(errorCode, errorDescription);
    }

    public static final AutoLoadChangeRequestDTO getTestAutoLoadChangeRequestDTO(String prestigeId, Integer autoLoadState,
                                                                                 Long pickUpLocation, String userId,
                                                                                 String password) {
        return new AutoLoadChangeRequestDTO(prestigeId, autoLoadState, pickUpLocation, userId, password);
    }

    public static final AutoLoadRequest getTestAutoLoadRequest(String realTimeFlag, String prestigeId, Integer autoLoadState,
                                                               Integer paymentMethod, Integer pickupLocation, String userId,
                                                               String password) {
        return new AutoLoadRequest(realTimeFlag, prestigeId, autoLoadState, paymentMethod, pickupLocation, userId, password);
    }

    public static final AutoLoadResponse getTestAutoLoadResponse(String prestigeId, Integer requestSequenceNumber,
                                                                 Integer autoLoadState, Integer pickupLocation,
                                                                 Integer availableSlots) {
        return new AutoLoadResponse(prestigeId, requestSequenceNumber, autoLoadState, pickupLocation, availableSlots);
    }

    public static final RequestFailure getTestRequestFailure(Integer errorCode, String errorDescription) {
        return new RequestFailure(errorCode, errorDescription);
    }

    private AutoLoadConfigurationChangeTestUtil() {
    }
}
