package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.SettlementTestUtil.TRANSACTION_UUID_1;

import java.util.HashMap;
import java.util.Map;

import com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourcePostReplyField;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourcePostReplyDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourcePostRequestDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapReplyDTO;

/**
 * Utilities for payment gateway unit tests
 */
public class PaymentGatewayTestUtil {

    public static final String ACCESS_KEY_1 = "test-access-key";
    public static final String AMOUNT_1 = "99.99";
    public static final String CURRENCY_1 = "Elbonian Groat";
    public static final String LOCALE_1 = "Elbonia";
    public static final String PROFILE_ID_1 = "test-profile";
    public static final String TRANSACTION_TYPE_1 = "barter";

    public static final String AUTH_AMOUNT_1 = "99.99";
    public static final String AUTH_TIME_1 = "2013-08-20T120000Z";
    public static final String AUTH_TRANS_REF_NO_1 = "9991";
    public static final String AUTH_TRANS_REF_NO_2 = "";
    public static final String DECISION_ACCEPT = "ACCEPT";
    public static final String DECISION_REVIEW = "REVIEW";
    public static final String DECISION_DECLINE = "DECLINE";
    public static final String DECISION_ERROR = "ERROR";
    public static final String DECISION_CANCEL = "CANCEL";
    public static final String INVALID_FIELDS_1 = "X";
    public static final String MESSAGE_1 = "X";
    public static final String REASON_CODE_1 = "100";
    public static final String PAYMENT_TOKEN_1 = "TOKEN";
    public static final String REQ_REFERENCE_NUMBER_1 = "9992";
    public static final String REQ_ID = "9992";
    public static final String REQ_ID_EMPTY = "";
    public static final String REQ_TRANSACTION_UUID_1 = TRANSACTION_UUID_1;
    public static final String REQ_TRANSACTION_UUID_2 = "XYZ-098";
    public static final String REQ_TRANSACTION_UUID_3 = "";
    public static final String SIGNATURE_1 = "SIGNATURE";
    public static final String SIGNED_DATE_TIME_1 = "2013-08-20T120000Z";
    public static final String SIGNED_FIELD_NAMES_1 = "X";
    public static final String TRANSACTION_ID_1 = "9994";
    public static final String TRANSACTION_ID_2 = "";
    public static final String REQUIRED_FIELDS_1 = "X";
    public static final String UNSIGNED_FIELD_NAMES_1 = "X";

    public static CyberSourcePostReplyDTO getTestAcceptReply() {
        return getTestReply(AUTH_AMOUNT_1, AUTH_TIME_1, AUTH_TRANS_REF_NO_1, DECISION_ACCEPT, INVALID_FIELDS_1, MESSAGE_1,
                REASON_CODE_1, PAYMENT_TOKEN_1, REQ_REFERENCE_NUMBER_1, REQ_TRANSACTION_UUID_1, SIGNATURE_1, SIGNED_DATE_TIME_1,
                SIGNED_FIELD_NAMES_1, TRANSACTION_ID_1, REQUIRED_FIELDS_1);
    }
    
    public static CyberSourcePostReplyDTO getTestRejectReply() {
        return getTestReply(AUTH_AMOUNT_1, AUTH_TIME_1, AUTH_TRANS_REF_NO_1, DECISION_DECLINE, INVALID_FIELDS_1, MESSAGE_1,
                REASON_CODE_1, PAYMENT_TOKEN_1, REQ_REFERENCE_NUMBER_1, REQ_TRANSACTION_UUID_1, SIGNATURE_1, SIGNED_DATE_TIME_1,
                SIGNED_FIELD_NAMES_1, TRANSACTION_ID_1, REQUIRED_FIELDS_1);
    }
    
    public static CyberSourceSoapReplyDTO getTestAcceptReplyWithRequestId() {
        return getSOAPTestReply(AUTH_AMOUNT_1, AUTH_TIME_1, AUTH_TRANS_REF_NO_2, DECISION_ACCEPT, INVALID_FIELDS_1, MESSAGE_1,
                REASON_CODE_1, PAYMENT_TOKEN_1, REQ_REFERENCE_NUMBER_1, REQ_TRANSACTION_UUID_3, SIGNATURE_1, SIGNED_DATE_TIME_1,
                SIGNED_FIELD_NAMES_1, TRANSACTION_ID_2, REQUIRED_FIELDS_1, REQ_ID);
    }
    
    public static CyberSourceSoapReplyDTO getTestAcceptReplyWithEmptyRequestId() {
        return getSOAPTestReply(AUTH_AMOUNT_1, AUTH_TIME_1, AUTH_TRANS_REF_NO_2, DECISION_ACCEPT, INVALID_FIELDS_1, MESSAGE_1,
                REASON_CODE_1, PAYMENT_TOKEN_1, REQ_REFERENCE_NUMBER_1, REQ_TRANSACTION_UUID_3, SIGNATURE_1, SIGNED_DATE_TIME_1,
                SIGNED_FIELD_NAMES_1, TRANSACTION_ID_2, REQUIRED_FIELDS_1, REQ_ID_EMPTY);
    }

    public static CyberSourcePostRequestDTO getTestRequest() {
        return getTestRequest(ACCESS_KEY_1, AMOUNT_1, CURRENCY_1, LOCALE_1, PROFILE_ID_1, REQ_REFERENCE_NUMBER_1, SIGNATURE_1,
                SIGNED_DATE_TIME_1, SIGNED_FIELD_NAMES_1, TRANSACTION_TYPE_1, TRANSACTION_UUID_1, UNSIGNED_FIELD_NAMES_1);
    }

    public static CyberSourcePostReplyDTO getTestReply(String authAmount, String authTime, String authTransRefNo,
                                                       String decision, String invalidFields, String message, String reasonCode,
                                                       String paymentToken, String reqReferenceNumber,
                                                       String reqTransactionUuid, String signature, String signedDateTime,
                                                       String signedFieldNames, String transactionId, String requiredFields) {
        return new CyberSourcePostReplyDTO(authAmount, authTime, authTransRefNo, decision, invalidFields, message, reasonCode,
                paymentToken, reqReferenceNumber, reqTransactionUuid, signature, signedDateTime, signedFieldNames,
                transactionId, requiredFields);
    }
    
    public static CyberSourceSoapReplyDTO getSOAPTestReply(String authAmount, String authTime, String authTransRefNo,
                    String decision, String invalidFields, String message, String reasonCode,
                    String paymentToken, String reqReferenceNumber,
                    String reqTransactionUuid, String signature, String signedDateTime,
                    String signedFieldNames, String transactionId, String requiredFields, String requestId) {

     return new CyberSourceSoapReplyDTO( authAmount,  authTransRefNo,  authTime,
                    reqTransactionUuid,  reqReferenceNumber,
                    authAmount,  reasonCode,  decision,  requiredFields,
                     paymentToken,  requiredFields,  reasonCode,  requestId);
    }

    public static CyberSourcePostRequestDTO getTestRequest(String accessKey, String amount, String currency, String locale,
                                                           String profileId, String referenceNumber, String signature,
                                                           String signedDateTime, String signedFieldNames,
                                                           String transactionType, String transactionUuid,
                                                           String unsignedFieldNames) {
        return new CyberSourcePostRequestDTO(accessKey, amount, currency, locale, profileId, referenceNumber, signature,
                signedDateTime, signedFieldNames, transactionType, transactionUuid, unsignedFieldNames);
    }

    public static Map<String, String> getTestReplyArguments() {
        Map<String, String> arguments = new HashMap<String, String>();
        for (CyberSourcePostReplyField cyberSourcePostReplyField : CyberSourcePostReplyField.values()) {
            arguments.put(cyberSourcePostReplyField.code(), "");
        }
        return arguments;
    }

    private PaymentGatewayTestUtil() {
    }
}
