package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.domain.ChequeSettlement;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddress1;
import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO1;

/**
 * Fixtures and utilities for cheque settlement tests
 */
public final class ChequeSettlementTestUtil {
    static final Logger logger = LoggerFactory.getLogger(ChequeSettlementTestUtil.class);

    public static final Integer DOCUMENT_ID = 1;
    public static final String DOCUMENT_ID_AS_STRING = "1";
    public static final String DOCUMENT_TYPE = "KR";
    public static final String COMPANY_CODE = "1004";
    public static final String CURRENCY = "GBP";
    public static final String REFERENCE_NUMBER_AS_STRING = "123456";
    public static final Long REFERENCE_NUMBER = 123456L;
    public static final String DOCUMENT_HEADER_TEXT = "DO99.csv";
    public static final String ACCOUNT_TYPE = "V";
    public static final Integer ACCOUNT_NUMBER = 9999;
    public static final String ACCOUNT_NUMBER_AS_STRING = "9999";
    public static final String AUG_19_FSC_FORMAT_STRING = "19/08/2013";
    public static final Float NET_AMOUNT = 12.34F;
    public static final Integer NET_AMOUNT_AS_PENCE = 1234;
    public static final String NET_AMOUNT_AS_STRING = "12.34";
    public static final String TAX_CODE = "V3";
    public static final String CUSTOMER_NAME = "test-customer-name";
    public static final String PAYMENT_METHOD = "R";
    public static final String CHEQUE_SERIAL_NUMBER_AS_STRING = "98765";
    public static final Long CHEQUE_SERIAL_NUMBER = 98765L;

    public static final String CUSTOMER_ADDRESS_LINE_1 = "test-address-line-1";
    public static final String CUSTOMER_ADDRESS_LINE_2 = "test-address-line-2";
    public static final String CUSTOMER_ADDRESS_STREET = "test-address-street";
    public static final String CUSTOMER_ADDRESS_TOWN = "test-address-town";
    public static final String CUSTOMER_ADDRESS_POST_CODE = "test-address-post-code";

    public static final String[] TEST_EXPORT_RECORD = new String[]{"ABC", "DEF", "123"};
    public static final String TEST_EXPORT_RECORD_AS_STRING = "ABC,DEF,123\r\n";

    public static final String SETTLEMENT_STATUS = "test-settlement-status";

    public static final Date SETTLEMENT_DATE_AUG19 = DateTestUtil.getAug19();
    public static final Date PRINTED_DATE_AUG20 = DateTestUtil.getAug20();
    public static final Date CLEARED_DATE_AUG21 = DateTestUtil.getAug21();

    public static final Long SETTLEMENT_NUMBER = 102L;

    public static final String[] TEST_CHEQUE_PRODUCED_IMPORT_RECORD =
            new String[]{REFERENCE_NUMBER_AS_STRING, NET_AMOUNT_AS_STRING, CUSTOMER_NAME, CHEQUE_SERIAL_NUMBER_AS_STRING,
                    AUG_19_FSC_FORMAT_STRING};

    public static final String[] TEST_CHEQUE_SETTLEMENT_IMPORT_RECORD =
            new String[]{CHEQUE_SERIAL_NUMBER_AS_STRING, REFERENCE_NUMBER_AS_STRING, CUSTOMER_NAME, AUG_19_FSC_FORMAT_STRING,
                    CURRENCY, NET_AMOUNT_AS_STRING};

    public static final String[] TEST_OUTDATED_CHEQUE_IMPORT_RECORD =
            new String[]{REFERENCE_NUMBER_AS_STRING, NET_AMOUNT_AS_STRING, CUSTOMER_NAME, CHEQUE_SERIAL_NUMBER_AS_STRING,
                    AUG_19_FSC_FORMAT_STRING};

    private ChequeSettlementTestUtil() {
    }

    public static ChequeSettlementDTO getTestChequeSettlementDTO1() {
        ChequeSettlementDTO chequeSettlementDTO = new ChequeSettlementDTO();
        chequeSettlementDTO.setChequeSerialNumber(CHEQUE_SERIAL_NUMBER);
        chequeSettlementDTO.setAmount(NET_AMOUNT_AS_PENCE);
        chequeSettlementDTO.setSettlementDate(SETTLEMENT_DATE_AUG19);
        chequeSettlementDTO.setPrintedOn(PRINTED_DATE_AUG20);
        chequeSettlementDTO.setClearedOn(CLEARED_DATE_AUG21);
        chequeSettlementDTO.setOrderId(REFERENCE_NUMBER);
        chequeSettlementDTO.setPayeeName(CUSTOMER_NAME);
        chequeSettlementDTO.setAddressDTO(getTestAddressDTO1());
        chequeSettlementDTO.setSettlementNumber(SETTLEMENT_NUMBER);
        chequeSettlementDTO.setStatus(SETTLEMENT_STATUS);
        return chequeSettlementDTO;
    }

    public static ChequeSettlement getTestChequeSettlement1() {
        ChequeSettlement settlement = new ChequeSettlement();
        settlement.setChequeSerialNumber(CHEQUE_SERIAL_NUMBER);
        settlement.setAmount(NET_AMOUNT_AS_PENCE);
        settlement.setSettlementDate(SETTLEMENT_DATE_AUG19);
        settlement.setPrintedOn(PRINTED_DATE_AUG20);
        settlement.setClearedOn(CLEARED_DATE_AUG21);
        settlement.setOrderId(REFERENCE_NUMBER);
        settlement.setPayeeName(CUSTOMER_NAME);
        settlement.setAddress(getTestAddress1());
        settlement.setSettlementNumber(SETTLEMENT_NUMBER);
        settlement.setStatus(SETTLEMENT_STATUS);
        return settlement;
    }

}
