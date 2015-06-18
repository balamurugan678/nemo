package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO1;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.AGENT;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.FIRSTNAME;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.LASTNAME;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.REFUND_IDENTIFIER;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.STATUS;
import static com.novacroft.nemo.test_support.CaseHistoryNoteServiceImplTestUtil.getTestCaseHistoryNotes1And2;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;

import java.util.List;

import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.command.impl.RefundCaseCmd;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.util.AddressFormatUtil;

public class RefundCaseTestUtil {
    public static String SAP_NUMBER = "012345";
    public static String BACS_NUMBER = "012345";
    public static String CHEQUE_NUMBER = "012345";
    
    public static String CUSTOMER_NAME = FIRSTNAME + StringUtil.SPACE + LASTNAME;
    public static List<String> ADDRESS = AddressFormatUtil.formatAddress(getTestAddressDTO1().getHouseNameNumber(), getTestAddressDTO1().getStreet(),
                    getTestAddressDTO1().getTown(), getTestAddressDTO1().getCounty(), getTestAddressDTO1().getCountry());
    
    public static RefundCaseCmd getRefundCaseBACS() {
        return new RefundCaseCmd(REFUND_IDENTIFIER, RefundTestUtil.REFUND_AMOUNT_IN_PENCE_L, DateTestUtil.REFUND_DATE_1, DateTestUtil.REFUND_DATE_1,
                        DateTestUtil.REFUND_DATE_1, STATUS, Boolean.FALSE, StringUtil.EMPTY_STRING, AGENT, SAP_NUMBER, CUSTOMER_NAME, LASTNAME,
                        ADDRESS, OYSTER_NUMBER_1, PaymentType.BACS.code(), BACS_NUMBER, StringUtil.EMPTY_STRING, getTestCaseHistoryNotes1And2());
    }
    
    public static RefundCaseCmd getRefundCaseCheque() {
        return new RefundCaseCmd(REFUND_IDENTIFIER, RefundTestUtil.REFUND_AMOUNT_IN_PENCE_L, DateTestUtil.REFUND_DATE_1, DateTestUtil.REFUND_DATE_1,
                        DateTestUtil.REFUND_DATE_1, STATUS, Boolean.FALSE, StringUtil.EMPTY_STRING, AGENT, SAP_NUMBER, CUSTOMER_NAME, LASTNAME,
                        ADDRESS, OYSTER_NUMBER_1, PaymentType.CHEQUE.code(), StringUtil.EMPTY_STRING, CHEQUE_NUMBER, getTestCaseHistoryNotes1And2());
    }

    public static RefundCaseCmd getRefundCaseAdHoc() {
        return new RefundCaseCmd(REFUND_IDENTIFIER, RefundTestUtil.REFUND_AMOUNT_IN_PENCE_L, DateTestUtil.REFUND_DATE_1, DateTestUtil.REFUND_DATE_1,
                        DateTestUtil.REFUND_DATE_1, STATUS, Boolean.FALSE, StringUtil.EMPTY_STRING, AGENT, SAP_NUMBER, CUSTOMER_NAME, LASTNAME,
                        ADDRESS, OYSTER_NUMBER_1, PaymentType.AD_HOC_LOAD.code(), StringUtil.EMPTY_STRING, StringUtil.EMPTY_STRING,
                        getTestCaseHistoryNotes1And2());
    }
}
