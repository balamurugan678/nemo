package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.LocationTestUtil.LOCATION_ID_1;

import com.novacroft.nemo.common.utils.Converter;
import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayTicketRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayValueRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdateResponse;
import com.novacroft.nemo.tfl.common.transfer.CardUpdatePrePayTicketRequestDTO;
import com.novacroft.nemo.tfl.common.transfer.CardUpdatePrePayValueRequestDTO;
import com.novacroft.nemo.tfl.common.transfer.CardUpdateResponseDTO;

public class CardUpdateRequestTestUtil {
    
    public final static String REAL_TIME_FLAG = "N";
    public final static String PRESTIGE_ID = "123456789"; 
    public final static String ACTION = "ADD"; 
    public final static String ACTION_REMOVE = "REMOVE";
    public final static Integer PRODUCT_CODE = 123; 
    public final static String START_DATE = "01/01/2014";
    public final static String EXPIRY_DATE ="01/12/2014";;
    public final static Integer PRODUCT_PRICE = 360; 
    public final static Integer CURRENCY = 0;
    public final static Long PICKUP_LOCATION = 1L;
    public final static Integer PAYMENT_METHOD = 0;
    public final static String USER_ID = "WebUser";
    public final static String PASSWORD = "password";
    public final static Integer PRE_PAY_VALUE = 1200;
    public final static Integer AVAILABLE_SLOTS = 100;
    public static final Integer REQUEST_SEQUENCE_NUMBER = 9876;
    public final static Long ORIGINAL_REQUEST_SEQUENCE_NUMBER = 5L;
    public final static Long REMOVED_REQUEST_SEQUENCE_NUMBER = 5L;
    public final static Integer ERROR_CODE = 20;
    public final static String ERROR_DESCRIPTION = "UNEXPECTED SERVER ERROR";
    
    public static final String getTestCardUpdatePrePayTicketResponse() {
            return String.format("<CardUpdateResponse><PrestigeID>%s</PrestigeID>" +
                    "<RequestSequenceNumber>%s</RequestSequenceNumber>" +
                    "<LocationInfo><PickupLocation>%s</PickupLocation>" +
                    "<AvailableSlots>%s</AvailableSlots></LocationInfo></CardUpdateResponse>", PRESTIGE_ID,
                    REQUEST_SEQUENCE_NUMBER,  PICKUP_LOCATION, AVAILABLE_SLOTS);
    }
    
    public static final String getTestCardUpdateRemoveResponse() {
        return String.format("<CardUpdateResponse><PrestigeID>%s</PrestigeID>" +
                        "<RequestSequenceNumber>%s</RequestSequenceNumber>" +
                        "<RemovedRequestSequenceNumber>%s</RemovedRequestSequenceNumber>" +
                        "</CardUpdateResponse>", PRESTIGE_ID,
                        REQUEST_SEQUENCE_NUMBER, REMOVED_REQUEST_SEQUENCE_NUMBER);
    }
    
    public static final String getTestRequestFailureXml() {
        return String
                .format("<RequestFailure><ErrorCode>%s</ErrorCode><ErrorDescription>%s</ErrorDescription></RequestFailure>",
                        ERROR_CODE, ERROR_DESCRIPTION);
    }
    
    public static final String getTestRemoveUpdateRequestXml1() {
        return String.format("<CardUpdateRequest>" +
                        "<PrestigeID>%s</PrestigeID>" +
                        "<Action>%s</Action>" +
                        "<OriginalRequestSequenceNumber>%s</OriginalRequestSequenceNumber>" +
                        "<OriginatorInfo>" +
                        "<UserID>%s</UserID>" +
                        "<Password>%s</Password>" +
                        "</OriginatorInfo>" +
                        "</CardUpdateRequest>", PRESTIGE_ID, ACTION_REMOVE, ORIGINAL_REQUEST_SEQUENCE_NUMBER, USER_ID, PASSWORD);
    }
    
    public static final String getTestRequestPrePayTicketXml1() {
        return String.format("<CardUpdateRequest>" +
 "<RealTimeFlag>%s</RealTimeFlag>" +
                "<PrestigeID>%s</PrestigeID>" +
                "<Action>%s</Action>" +
                "<PPT><ProductCode>%s</ProductCode>" +
                "<StartDate>%s</StartDate>" +
                "<ExpiryDate>%s</ExpiryDate>" +
                "<ProductPrice>%s</ProductPrice>" +
                "<Currency>%s</Currency></PPT>" +
				"<PaymentMethod>%s</PaymentMethod>" + 
				"<PickupLocation>%s</PickupLocation>" +
                "<OriginatorInfo>" +
                "<UserID>%s</UserID>" +
                "<Password>%s</Password>" +
                "</OriginatorInfo>" +
                "</CardUpdateRequest>", REAL_TIME_FLAG, PRESTIGE_ID, ACTION, PRODUCT_CODE, START_DATE, EXPIRY_DATE,
                PRODUCT_PRICE, CURRENCY, PAYMENT_METHOD, PICKUP_LOCATION, USER_ID, PASSWORD);
    }
    public static final String getTestRequestPrePayValueXml1() {
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
                "</AutoLoadRequest>", PRESTIGE_ID, "ADD", "2000" , "0",  LOCATION_ID_1, USER_ID, PASSWORD);
    }
    
    public static CardUpdatePrePayTicketRequest getCardUpdatePrePayTicketRequest1(){
        return createCardUpdatePrePayTicketRequest(REAL_TIME_FLAG, PRESTIGE_ID, ACTION, PRE_PAY_VALUE, PRODUCT_CODE, 
                        START_DATE, EXPIRY_DATE, PRODUCT_PRICE, CURRENCY, PICKUP_LOCATION, PAYMENT_METHOD, USER_ID, PASSWORD);
    }

    public static CardUpdatePrePayTicketRequest createCardUpdatePrePayTicketRequest(String realTimeFlag, String prestigeId, String action, 
                    Integer prePayValue, Integer productCode, String startDate, String expiryDate, Integer productPrice, Integer currency,
                    Long pickupLocation, Integer paymentMethod, String userId, String password){
        return new CardUpdatePrePayTicketRequest(realTimeFlag, prestigeId, action,  productCode, startDate, expiryDate, currency, productPrice, pickupLocation, paymentMethod, userId, password);
    }
    
    public static CardUpdatePrePayTicketRequestDTO getCardUpdatePrePayTicketRequestDTO1(){
        CardUpdatePrePayTicketRequest request = getCardUpdatePrePayTicketRequest1();
        CardUpdatePrePayTicketRequestDTO requestDTO = new CardUpdatePrePayTicketRequestDTO();
        Converter.convert(request, requestDTO);
        return requestDTO;
    }
    
    public static CardUpdateResponseDTO getCardUpdateResponseDTO() {
        return createCardUpdateResponseDTO(PRESTIGE_ID, REQUEST_SEQUENCE_NUMBER, PICKUP_LOCATION, AVAILABLE_SLOTS);
    }
    
    public static CardUpdateResponseDTO createCardUpdateResponseDTO(
                    String prestigeId, Integer requestSequenceNumber, Long pickupLocation, Integer availableSlots) {
        return new CardUpdateResponseDTO(
                        prestigeId, requestSequenceNumber, pickupLocation, availableSlots);
    }
    
    public static CardUpdateResponseDTO getFailedCardUpdateResponseDTO(){
        return createFailedCardUpdateResponseDTo(ERROR_CODE, ERROR_DESCRIPTION);
    }
    
    public static CardUpdateResponseDTO createFailedCardUpdateResponseDTo(Integer errorCode, String errorDescription){
        return new CardUpdateResponseDTO(errorCode, errorDescription);
    }
    
    public static CardUpdatePrePayValueRequest getCardUpdatePrePayValueRequest1(){
        return createCardUpdatePrePayValueRequest(PRESTIGE_ID, ACTION, PICKUP_LOCATION, PAYMENT_METHOD, USER_ID, PASSWORD, PRE_PAY_VALUE, CURRENCY);
    }
    
    public static CardUpdatePrePayValueRequest createCardUpdatePrePayValueRequest(String prestigeId, String action, Long pickupLocation, Integer paymentMethod, String userId, String password, Integer prePayValue, Integer currency){
        return new CardUpdatePrePayValueRequest(prestigeId, action, pickupLocation, paymentMethod, userId, password, prePayValue, currency);
    }
    
    public static CardUpdatePrePayValueRequestDTO getCardUpdatePrePayValueRequestDTO1() {
        CardUpdatePrePayValueRequest request = getCardUpdatePrePayValueRequest1();
        CardUpdatePrePayValueRequestDTO requestDTO = new CardUpdatePrePayValueRequestDTO();
        Converter.convert(request, requestDTO);
        return requestDTO;
    }
    
    public static CardRemoveUpdateRequest getCardRemoveUpdateRequest1() {
        return createCardRemoveUpdateRequest(PRESTIGE_ID, ACTION_REMOVE, ORIGINAL_REQUEST_SEQUENCE_NUMBER, USER_ID, PASSWORD);
    }

    private static CardRemoveUpdateRequest createCardRemoveUpdateRequest(String prestigeId, String action, Long originalRequestSequenceNumber,
                    String userId, String password) {
        return new CardRemoveUpdateRequest(prestigeId, action, originalRequestSequenceNumber, userId, password);
    }
    
    public static String getRequestFailureXml1() {
        return String.format("<RequestFailure>" +
                        "<ErrorCode>%s</ErrorCode>" +
                        "<ErrorDescription>%s</ErrorDescription>" +
                        "</RequestFailure>", "40", "CARD NOT FOUND");
    }
    
    public static String getRequestFailureXml2() {
        return String.format("<RequestFailure>" +
                        "<ErrorCode>%s</ErrorCode>" +
                        "<ErrorDescription>%s</ErrorDescription>" +
                        "</RequestFailure>", "60", "NO AVAILABLE PPT SLOT");
    }
    
    public static String getRequestFailureXml3() {
        return String.format("<RequestFailure>" +
                        "<ErrorCode>%s</ErrorCode>" +
                        "<ErrorDescription>%s</ErrorDescription>" +
                        "</RequestFailure>", "140", "REMOVAL REQUEST SEQUENCE NUMBER NOT FOUND");
    }
    
    public static CardUpdateResponse getCardUpdateResponse() {
        return createCardUpdateResponse(PRESTIGE_ID, REQUEST_SEQUENCE_NUMBER, PICKUP_LOCATION, AVAILABLE_SLOTS);
    }
    
    private static CardUpdateResponse createCardUpdateResponse(
                    String prestigeId, Integer requestSequenceNumber, Long pickupLocation, Integer availableSlots) {
        return new CardUpdateResponse(
                        prestigeId, requestSequenceNumber, pickupLocation, availableSlots);
    }
}