package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug20;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug21;
import static com.novacroft.nemo.test_support.DateTestUtil.getZuluAug20;
import static com.novacroft.nemo.test_support.OrderTestUtil.ORDER_ID;
import static com.novacroft.nemo.test_support.OrderTestUtil.STATUS;
import static com.novacroft.nemo.test_support.OrderTestUtil.TOTAL_AMOUNT;
import static com.novacroft.nemo.test_support.PaymentGatewayTestUtil.AUTH_TRANS_REF_NO_1;
import static com.novacroft.nemo.test_support.PaymentGatewayTestUtil.DECISION_ACCEPT;
import static com.novacroft.nemo.test_support.PaymentGatewayTestUtil.MESSAGE_1;
import static com.novacroft.nemo.test_support.PaymentGatewayTestUtil.REASON_CODE_1;
import static com.novacroft.nemo.test_support.PaymentGatewayTestUtil.TRANSACTION_ID_1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.novacroft.nemo.tfl.common.constant.AutoLoadState;
import com.novacroft.nemo.tfl.common.domain.AdHocLoadSettlement;
import com.novacroft.nemo.tfl.common.domain.AutoLoadChangeSettlement;
import com.novacroft.nemo.tfl.common.domain.PaymentCardSettlement;
import com.novacroft.nemo.tfl.common.domain.Settlement;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.SettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.WebCreditSettlementDTO;

/**
 * Utilities for settlement unit tests
 */
public class SettlementTestUtil {

    public static final Long ID_1 = 00001L;
    public static final Long PAYMENT_CARD_ID_1 = 99L;
    public static final Long ORDER_ID_1 = 99L;
    public static final String TRANSACTION_TYPE_1 = "Sale";
    public static final String TRANSACTION_UUID_1 = "ABC-123";
    public static final Integer AUTHORISED_AMOUNT_1 = 9999;
    public static final Date AUTHORISATION_TIME_1 = getZuluAug20();
    public static final String AUTHORISATION_TRANSACTION_REFERENCE_NUMBER_1 = AUTH_TRANS_REF_NO_1;
    public static final Date SETTLEMENT_DATE_1 = getAug20();

    public static final Integer REQUEST_SEQUENCE_NUMBER = 9876;
    public static final Integer PICK_UP_NATIONAL_LOCATION_CODE = 5432;
    public static final Date EXPIRES_ON = getAug21();

    public static final String ACTION_STATUS_OK = "OK";
    public static final String ACTION_STATUS_FAILED = "FAILED";
    public static final Integer FAILURE_REASON_CODE_1 = 99;
    public static final String EVENT_INFORMATION_1 = "test-event-information";

    public static final Long SETTLEMENT_ID_1 = 123L;
    public static final String STATUS_2 = "";
    public static final Integer AMOUNT_2 = 0;
    public static final Date SETTLEMENT_DATE_2 = DateTestUtil.getDate(DateTestUtil.DATE_FORMAT_STR, "29/08/2014");
    public static final Integer REQUEST_SEQUENCE_NUMBER_2 = 123;
    public static final Date EXPIRES_ON_2 = DateTestUtil.getDate(DateTestUtil.DATE_FORMAT_STR, "30/09/2014");

    public static PaymentCardSettlementDTO getTestPaymentCardSettlementDTO1() {
        PaymentCardSettlementDTO paymentCardDTO = getTestPaymentCardSettlementDTO(PAYMENT_CARD_ID_1, TRANSACTION_TYPE_1, TRANSACTION_UUID_1,
                        DECISION_ACCEPT, MESSAGE_1, REASON_CODE_1, TRANSACTION_ID_1, AUTHORISED_AMOUNT_1, AUTHORISATION_TIME_1,
                        AUTHORISATION_TRANSACTION_REFERENCE_NUMBER_1);
        paymentCardDTO.setAmount(AUTHORISED_AMOUNT_1);
        paymentCardDTO.setSettlementDate(SETTLEMENT_DATE_1);
        return paymentCardDTO;
    }

    public static List<PaymentCardSettlementDTO> getTestPaymentCardSettlementDTOs() {
        List<PaymentCardSettlementDTO> dtos = new ArrayList<PaymentCardSettlementDTO>();
        dtos.add(getTestPaymentCardSettlementDTO1());
        return dtos;
    }

    public static PaymentCardSettlementDTO getTestPaymentCardSettlementDTOEmptyTransactionUuid() {
        return getTestPaymentCardSettlementDTO(PAYMENT_CARD_ID_1, TRANSACTION_TYPE_1, "", DECISION_ACCEPT, MESSAGE_1, REASON_CODE_1,
                        TRANSACTION_ID_1, AUTHORISED_AMOUNT_1, AUTHORISATION_TIME_1, AUTHORISATION_TRANSACTION_REFERENCE_NUMBER_1);
    }

    public static WebCreditSettlementDTO getTestWebCreditSettlementDTO() {
        return getTestWebCreditSettlementDTO(ORDER_ID_1, AUTHORISED_AMOUNT_1, TRANSACTION_UUID_1);
    }

    public static AdHocLoadSettlement getTestAdHocLoadSettlement1() {
        return getTestAdHocLoadSettlement(ORDER_ID, STATUS, TOTAL_AMOUNT, SETTLEMENT_DATE_1, CARD_ID_1, REQUEST_SEQUENCE_NUMBER,
                        PICK_UP_NATIONAL_LOCATION_CODE, EXPIRES_ON);
    }

    public static List<AdHocLoadSettlement> getTestAdHocLoadSettlementList1() {
        List<AdHocLoadSettlement> list = new ArrayList<AdHocLoadSettlement>();
        list.add(getTestAdHocLoadSettlement1());
        return list;
    }

    public static List<AdHocLoadSettlement> getTestAdHocLoadSettlementList2() {
        List<AdHocLoadSettlement> list = getTestAdHocLoadSettlementList1();
        list.add(getTestAdHocLoadSettlement1());
        return list;
    }

    public static AdHocLoadSettlementDTO getTestAdHocLoadSettlementDTO1() {
        return getTestAdHocLoadSettlementDTO(ORDER_ID, STATUS, TOTAL_AMOUNT, SETTLEMENT_DATE_1, CARD_ID_1, REQUEST_SEQUENCE_NUMBER,
                        PICK_UP_NATIONAL_LOCATION_CODE, EXPIRES_ON);
    }

    public static AdHocLoadSettlementDTO getTestAdHocLoadSettlementDTO2() {
        return getTestAdHocLoadSettlementDTO(OrderTestUtil.ORDER_ID, STATUS_2, AMOUNT_2, SETTLEMENT_DATE_2, CardTestUtil.CARD_ID,
                        REQUEST_SEQUENCE_NUMBER_2, null, EXPIRES_ON_2);
    }

    
    public static AdHocLoadSettlementDTO getTestAdHocLoadSettlementDTOFailedCubicUpdate() {
        return getTestAdHocLoadSettlementDTO(OrderTestUtil.ORDER_ID, STATUS_2, AMOUNT_2, SETTLEMENT_DATE_2, CardTestUtil.CARD_ID, null, null, EXPIRES_ON_2);
    }

    public static AdHocLoadSettlementDTO getTestAdHocLoadSettlementDTO1WithId() {
        AdHocLoadSettlementDTO settlementDTO = getTestAdHocLoadSettlementDTO2();
        settlementDTO.setId(SETTLEMENT_ID_1);
        return settlementDTO;
    }

    public static List<AdHocLoadSettlementDTO> getTestCancelOrderSettlementDTOs() {
        List<AdHocLoadSettlementDTO> dtos = new ArrayList<AdHocLoadSettlementDTO>();
        dtos.add(getTestAdHocLoadSettlementDTO1());
        return dtos;
    }
    
    public static List<AdHocLoadSettlementDTO> getTestCancelOrderSettlementDTOWithFailedCubicUpdate() {
        List<AdHocLoadSettlementDTO> dtos = new ArrayList<AdHocLoadSettlementDTO>();
        dtos.add(getTestAdHocLoadSettlementDTO1());
        dtos.add(getTestAdHocLoadSettlementDTOFailedCubicUpdate());
        return dtos;
    }

    public static List<SettlementDTO> getTestPolymorphicSettlemtDTOs() {
        List<SettlementDTO> dtos = new ArrayList<SettlementDTO>();
        dtos.add(getTestAdHocLoadSettlementDTO1());
        return dtos;
    }

    public static AutoLoadChangeSettlementDTO getTestAutoLoadChangeSettlementDTO1() {
        return getTestAutoLoadChangeSettlementDTO(ORDER_ID, STATUS, TOTAL_AMOUNT, SETTLEMENT_DATE_1, CARD_ID_1, REQUEST_SEQUENCE_NUMBER,
                        PICK_UP_NATIONAL_LOCATION_CODE, AutoLoadState.lookUpState(TOTAL_AMOUNT));
    }

    public static AutoLoadChangeSettlement getTestAutoLoadChangeSettlement1() {
        return getTestAutoLoadChangeSettlement(ORDER_ID, STATUS, TOTAL_AMOUNT, SETTLEMENT_DATE_1, CARD_ID_1, REQUEST_SEQUENCE_NUMBER,
                        PICK_UP_NATIONAL_LOCATION_CODE, AutoLoadState.lookUpState(TOTAL_AMOUNT));
    }

    public static PaymentCardSettlementDTO getTestPaymentCardSettlementDTO(Long paymentCardId, String transactionType, String transactionUuid,
                    String decision, String message, String reasonCode, String transactionId, Integer authorisedAmount, Date authorisationTime,
                    String authorisationTransactionReferenceNumber) {
        PaymentCardSettlementDTO dto = new PaymentCardSettlementDTO(paymentCardId, transactionType, transactionUuid, decision, message, reasonCode,
                        transactionId, authorisedAmount, authorisationTime, authorisationTransactionReferenceNumber);
        dto.setId(ID_1);
        dto.setExternalId(ID_1);
        return dto;
    }

    public static WebCreditSettlementDTO getTestWebCreditSettlementDTO(Long paymentCardId, Integer authorisedAmount, String transactionUuid) {
        WebCreditSettlementDTO dto = new WebCreditSettlementDTO(paymentCardId, authorisedAmount, transactionUuid);
        dto.setId(ID_1);
        dto.setExternalId(ID_1);
        return dto;
    }

    public static AdHocLoadSettlement getTestAdHocLoadSettlement(Long orderId, String status, Integer amount, Date settlementDate, Long cardId,
                    Integer requestSequenceNumber, Integer pickUpNationalLocationCode, Date expiresOn) {
        return new AdHocLoadSettlement(orderId, status, amount, settlementDate, cardId, requestSequenceNumber, pickUpNationalLocationCode, expiresOn);
    }

    public static AdHocLoadSettlementDTO getTestAdHocLoadSettlementDTO(Long orderId, String status, Integer amount, Date settlementDate, Long cardId,
                    Integer requestSequenceNumber, Integer pickUpNationalLocationCode, Date expiresOn) {
        return new AdHocLoadSettlementDTO(orderId, status, amount, settlementDate, cardId, requestSequenceNumber, pickUpNationalLocationCode,
                        expiresOn);
    }

    public static AutoLoadChangeSettlementDTO getTestAutoLoadChangeSettlementDTO(Long orderId, String status, Integer amount, Date settlementDate,
                    Long cardId, Integer requestSequenceNumber, Integer pickUpNationalLocationCode, Integer autoLoadState) {
        return new AutoLoadChangeSettlementDTO(orderId, status, amount, settlementDate, cardId, requestSequenceNumber, pickUpNationalLocationCode,
                        autoLoadState);
    }

    public static AutoLoadChangeSettlement getTestAutoLoadChangeSettlement(Long orderId, String status, Integer amount, Date settlementDate,
                    Long cardId, Integer requestSequenceNumber, Integer pickUpNationalLocationCode, Integer autoLoadState) {
        return new AutoLoadChangeSettlement(orderId, status, amount, settlementDate, cardId, requestSequenceNumber, pickUpNationalLocationCode,
                        autoLoadState);
    }

    public static Settlement getTestSettlement(Long id, Long orderId) {
        Settlement settlement = new Settlement();
        settlement.setId(id);
        settlement.setOrderId(orderId);
        return settlement;
    }

    private static PaymentCardSettlement getTestPaymentCardSettlement(Long paymentCardId, String transactionType, String transactionUuid,
                    String decision, String message, String reasonCode, String transactionId, Integer authorisedAmount, Date authorisationTime,
                    String authorisationTransactionReferenceNumber) {
        PaymentCardSettlement paymentCardSettlement = new PaymentCardSettlement();
        paymentCardSettlement.setPaymentCardId(paymentCardId);
        paymentCardSettlement.setTransactionType(transactionType);
        paymentCardSettlement.setTransactionUuid(transactionUuid);
        paymentCardSettlement.setDecision(decision);
        paymentCardSettlement.setMessage(message);
        paymentCardSettlement.setReasonCode(reasonCode);
        paymentCardSettlement.setTransactionId(transactionId);
        paymentCardSettlement.setAuthorisedAmount(authorisedAmount);
        paymentCardSettlement.setAuthorisationTime(authorisationTime);
        paymentCardSettlement.setAuthorisationTransactionReferenceNumber(authorisationTransactionReferenceNumber);
        return paymentCardSettlement;
    }

    public static PaymentCardSettlement getTestPaymentCardSettlement1() {
        return getTestPaymentCardSettlement(PAYMENT_CARD_ID_1, TRANSACTION_TYPE_1, TRANSACTION_UUID_1, DECISION_ACCEPT, MESSAGE_1, REASON_CODE_1,
                        TRANSACTION_ID_1, AUTHORISED_AMOUNT_1, AUTHORISATION_TIME_1, AUTHORISATION_TRANSACTION_REFERENCE_NUMBER_1);
    }
}
