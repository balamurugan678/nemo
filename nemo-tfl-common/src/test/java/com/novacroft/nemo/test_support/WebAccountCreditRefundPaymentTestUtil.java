package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.transfer.WebCreditSettlementDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * WebAccountCreditRefundPayment unit test fixtures and utilities
 */
public final class WebAccountCreditRefundPaymentTestUtil {
    public static final String TRANSACTION_TYPE_1 = "test transaction type 1";
    public static final String TRANSACTION_UUID_1 = "test transaction uuid 1";
    public static final String DECISION_1 = "test decision 1";
    public static final String MESSAGE_1 = "test message 1";
    public static final String REASON_CODE_1 = "test reason code 1";
    public static final String TRANSACTION_ID_1 = "test transaction id 1";
    public static final Integer AUTHORIZED_AMOUNT_1 = 25;
    public static final Date AUTHORIZATION_TIME_1 = DateTestUtil.getApr03();
    public static final String AUTHORIZATION_TRANSACTION_REFERENCE_NUMBER_1 = "test authorization reference number 1";
    
    public static final String TRANSACTION_TYPE_2 = "test transaction type 2";
    public static final String TRANSACTION_UUID_2 = "test transaction uuid 2";
    public static final String DECISION_2 = "test decision 2";
    public static final String MESSAGE_2 = "test message 2";
    public static final String REASON_CODE_2 = "test reason code 2";
    public static final String TRANSACTION_ID_2 = "test transaction id 2";
    public static final Integer AUTHORIZED_AMOUNT_2 = 55;
    public static final Date AUTHORIZATION_TIME_2 = DateTestUtil.getAug10();
    public static final String AUTHORIZATION_TRANSACTION_REFERENCE_NUMBER_2 = "test authorization reference number 2";
    
    public static List<WebCreditSettlementDTO> getWebCreditSettlementDTOList() {
        List<WebCreditSettlementDTO> testWebCreditSettlementDTOs = new ArrayList<WebCreditSettlementDTO>(2);
        testWebCreditSettlementDTOs.add(getWebCreditSettlementDTO1());
        testWebCreditSettlementDTOs.add(getWebCreditSettlementDTO2());
        return testWebCreditSettlementDTOs;
    }

    public static List<WebCreditSettlementDTO> getWebCreditSettlementDTOEmptyList() {
        List<WebCreditSettlementDTO> testWebCreditSettlementDTOs = new ArrayList<WebCreditSettlementDTO>(2);
        return testWebCreditSettlementDTOs;
    }

    public static WebCreditSettlementDTO getWebCreditSettlementDTO1() {
        WebCreditSettlementDTO webCreditSettlementDTO = new WebCreditSettlementDTO();
        webCreditSettlementDTO.setTransactionType(TRANSACTION_TYPE_1);
        webCreditSettlementDTO.setTransactionUuid(TRANSACTION_UUID_1);
        webCreditSettlementDTO.setDecision(DECISION_1);
        webCreditSettlementDTO.setMessage(MESSAGE_1);
        webCreditSettlementDTO.setReasonCode(REASON_CODE_1);
        webCreditSettlementDTO.setTransactionId(TRANSACTION_ID_1);
        webCreditSettlementDTO.setAuthorisedAmount(AUTHORIZED_AMOUNT_1);
        webCreditSettlementDTO.setAuthorisationTime(AUTHORIZATION_TIME_1);
        webCreditSettlementDTO.setAuthorisationTransactionReferenceNumber(AUTHORIZATION_TRANSACTION_REFERENCE_NUMBER_1);
        return webCreditSettlementDTO;
    }

    public static WebCreditSettlementDTO getWebCreditSettlementDTO2() {
        WebCreditSettlementDTO webCreditSettlementDTO = new WebCreditSettlementDTO();
        webCreditSettlementDTO.setTransactionType(TRANSACTION_TYPE_2);
        webCreditSettlementDTO.setTransactionUuid(TRANSACTION_UUID_2);
        webCreditSettlementDTO.setDecision(DECISION_2);
        webCreditSettlementDTO.setMessage(MESSAGE_2);
        webCreditSettlementDTO.setReasonCode(REASON_CODE_2);
        webCreditSettlementDTO.setTransactionId(TRANSACTION_ID_2);
        webCreditSettlementDTO.setAuthorisedAmount(AUTHORIZED_AMOUNT_2);
        webCreditSettlementDTO.setAuthorisationTime(AUTHORIZATION_TIME_2);
        webCreditSettlementDTO.setAuthorisationTransactionReferenceNumber(AUTHORIZATION_TRANSACTION_REFERENCE_NUMBER_2);
        return webCreditSettlementDTO;
    }
        
    private WebAccountCreditRefundPaymentTestUtil() {
    }
}
