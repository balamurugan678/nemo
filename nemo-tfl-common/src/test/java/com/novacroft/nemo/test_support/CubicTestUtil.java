package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdateResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;
import com.novacroft.nemo.tfl.common.transfer.CardUpdateResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardRemoveUpdateRequestDTO;

public final class CubicTestUtil {
    public static final String CUBIC_USER_ID = "";
    public static final String CUBIC_PASSWORD = "";

    public static final String PRESTIGE_ID = "";
    public static final Integer ORIGINATING_REQUEST_SEQUENCE_NUMBER = 0123;
    public static final Integer REMOVED_REQUEST_SEQUENCE_NUMBER = 0124;

    public static final Integer ERROR_CODE = 0;
    public static final String ERROR_DESCRIPTION = "";

    public static final String REMOVE_ACTION = "REMOVE";

    public static final String XML_REQUEST = "<CardUpdateRequest><PrestigeID>%s</PrestigeID><Action>%s</Action>"
                    + "<OriginalRequestSequenceNumber>%s</OriginalRequestSequenceNumber><OriginatorInfo><UserID>%s</UserID>"
                    + "<Password>%s</Password></OriginatorInfo></CardUpdateRequest>";

    public static final String XML_RESPONSE = "<CardUpdateResponse><PrestigeID>%s</PrestigeID><RequestSequenceNumber>%s</RequestSequenceNumber><RemovedRequestSequenceNumber>%s</RemovedRequestSequenceNumber></CardUpdateResponse>";
    public static final String XML_FAILURE_RESPONSE = "<RequestFailure><ErrorCode>%s</ErrorCode><ErrorDescription>%s</ErrorDescription></RequestFailure>";

    public static final String getRequestXML1() {
        return String.format(XML_REQUEST, PRESTIGE_ID, REMOVE_ACTION, ORIGINATING_REQUEST_SEQUENCE_NUMBER, CUBIC_USER_ID, CUBIC_PASSWORD);
    }

    public static final String getResponseXML1() {
        return String.format(XML_RESPONSE, PRESTIGE_ID, REMOVED_REQUEST_SEQUENCE_NUMBER, ORIGINATING_REQUEST_SEQUENCE_NUMBER);
    }

    public static final String getFailureResponseXML1() {
        return String.format(XML_FAILURE_RESPONSE, ERROR_CODE, ERROR_DESCRIPTION);
    }

    public static CardRemoveUpdateRequestDTO getTestRequestDTO(String prestigeId, Long originatingRequestSequenceNumber, String userId,
                    String password) {
        return new CardRemoveUpdateRequestDTO(prestigeId, originatingRequestSequenceNumber, userId, password);
    }

    public static CardRemoveUpdateRequestDTO getTestRequestDTO1() {
        return getTestRequestDTO(PRESTIGE_ID, Long.valueOf(ORIGINATING_REQUEST_SEQUENCE_NUMBER), CUBIC_USER_ID, CUBIC_PASSWORD);
    }

    public static CardUpdateResponseDTO getTestResponseDTO1() {
        return getTestResponseDTO(PRESTIGE_ID, REMOVED_REQUEST_SEQUENCE_NUMBER, ORIGINATING_REQUEST_SEQUENCE_NUMBER, null, null);
    }

    public static CardUpdateResponseDTO getTestErrorResponseDTO1() {
        return getTestResponseDTO(PRESTIGE_ID, REMOVED_REQUEST_SEQUENCE_NUMBER, ORIGINATING_REQUEST_SEQUENCE_NUMBER, ERROR_CODE, ERROR_DESCRIPTION);
    }

    public static RequestFailure getTestRequestFailure(Integer errorCode, String errorDescription) {
        return new RequestFailure(errorCode, errorDescription);
    }

    public static RequestFailure getTestRequestFailure1() {
        return getTestRequestFailure(ERROR_CODE, ERROR_DESCRIPTION);
    }

    public static CardUpdateResponseDTO getTestResponseDTO(String prestigeId, Integer requestSequenceNumber,
                    Integer removedRequestSequenceNumber, Integer errorCode, String errorDescription) {
        CardUpdateResponseDTO responseDTO = new CardUpdateResponseDTO();
        responseDTO.setPrestigeId(prestigeId);
        responseDTO.setRequestSequenceNumber(requestSequenceNumber);
        responseDTO.setRemovedRequestSequenceNumber(removedRequestSequenceNumber);
        responseDTO.setErrorCode(errorCode);
        responseDTO.setErrorDescription(errorDescription);
        return responseDTO;
    }

    public static CardRemoveUpdateRequest getTestRequest(String prestigeId, String action, Long originalRequestSequenceNumber, String userId,
                    String password) {
        CardRemoveUpdateRequest request = new CardRemoveUpdateRequest();
        request.setPrestigeId(prestigeId);
        request.setAction(action);
        request.setOriginalRequestSequenceNumber(originalRequestSequenceNumber);
        request.setUserId(userId);
        request.setPassword(password);
        return request;
    }

    public static CardRemoveUpdateRequest getTestRequest1() {
        return getTestRequest(PRESTIGE_ID, REMOVE_ACTION, Long.valueOf(ORIGINATING_REQUEST_SEQUENCE_NUMBER), CUBIC_USER_ID, CUBIC_PASSWORD);
    }

    public static CardRemoveUpdateResponse getTestResponse(String presigeId, Integer requestSequenceNumber, Integer removedRequestSequenceNumber) {
        return new CardRemoveUpdateResponse(presigeId, requestSequenceNumber, removedRequestSequenceNumber);
    }

    public static CardRemoveUpdateResponse getTestRemoveResponse1() {
        return getTestResponse(PRESTIGE_ID, REMOVED_REQUEST_SEQUENCE_NUMBER, ORIGINATING_REQUEST_SEQUENCE_NUMBER);
    }

    public static CardUpdateResponse getTestResponse1() {
        return new CardUpdateResponse(PRESTIGE_ID, ORIGINATING_REQUEST_SEQUENCE_NUMBER, null, null);
    }

}
