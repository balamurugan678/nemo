package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.ApprovalTestUtil.FIRSTNAME;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.INVALID_FIRSTNAME;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.INVALID_LASTNAME;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.INVALID_REFUND_IDENTIFIER;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.LASTNAME;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.REFUND_IDENTIFIER;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;

import com.novacroft.nemo.tfl.common.command.impl.RefundSearchCmd;

public class RefundSearchTestUtil {
    public static String SAP_NUMBER = "012345";
    public static String BACS_NUMBER = "012345";
    public static String CHEQUE_NUMBER = "012345";
    
    public static String INVALID_SAP_NUMBER = "!!!";
    public static String INVALID_OYSTER_NUMBER = "!!!";
    public static String INVALID_BACS_NUMBER = "!!!";
    public static String INVALID_CHEQUE_NUMBER = "!!!";
    
    public static RefundSearchCmd getSearch() {
        return new RefundSearchCmd(REFUND_IDENTIFIER, LASTNAME, FIRSTNAME, SAP_NUMBER, LASTNAME, FIRSTNAME, OYSTER_NUMBER_1,
                    BACS_NUMBER, CHEQUE_NUMBER);
    }
    
    public static RefundSearchCmd getInvalidSearch() {
        return new RefundSearchCmd(INVALID_REFUND_IDENTIFIER, INVALID_LASTNAME, INVALID_FIRSTNAME, INVALID_SAP_NUMBER, INVALID_LASTNAME, INVALID_FIRSTNAME, INVALID_OYSTER_NUMBER,
                        INVALID_BACS_NUMBER, INVALID_CHEQUE_NUMBER);
    }
    
    public static RefundSearchCmd getSearchCaseNumberEmpty() {
        return new RefundSearchCmd("", INVALID_LASTNAME, INVALID_FIRSTNAME, INVALID_SAP_NUMBER, INVALID_LASTNAME, INVALID_FIRSTNAME, INVALID_OYSTER_NUMBER,
                        INVALID_BACS_NUMBER, INVALID_CHEQUE_NUMBER);
    }
    
    public static RefundSearchCmd getSearchFirstNameEmpty(){
        return new RefundSearchCmd(REFUND_IDENTIFIER, LASTNAME, "", SAP_NUMBER, LASTNAME, "", OYSTER_NUMBER_1,
                        BACS_NUMBER, CHEQUE_NUMBER);
    }
    
    public static RefundSearchCmd getSearchLastNameEmpty() {
        return new RefundSearchCmd(REFUND_IDENTIFIER, "", FIRSTNAME, SAP_NUMBER, "", FIRSTNAME, OYSTER_NUMBER_1,
                    BACS_NUMBER, CHEQUE_NUMBER);
    }
    
    public static RefundSearchCmd getSearchSapNumberEmpty() {
        return new RefundSearchCmd(REFUND_IDENTIFIER, LASTNAME, FIRSTNAME, "", LASTNAME, FIRSTNAME, OYSTER_NUMBER_1,
                    BACS_NUMBER, CHEQUE_NUMBER);
    }
    
    public static RefundSearchCmd getSearchCardNumber() {
        return new RefundSearchCmd(REFUND_IDENTIFIER, LASTNAME, FIRSTNAME, SAP_NUMBER, LASTNAME, FIRSTNAME, "",
                    BACS_NUMBER, CHEQUE_NUMBER);
    }
    
    public static RefundSearchCmd getSearchBacsNumberEmpty() {
        return new RefundSearchCmd(REFUND_IDENTIFIER, LASTNAME, FIRSTNAME, SAP_NUMBER, LASTNAME, FIRSTNAME, OYSTER_NUMBER_1,
                    "", CHEQUE_NUMBER);
    }
    
    public static RefundSearchCmd getSearchChequeNumber() {
        return new RefundSearchCmd(REFUND_IDENTIFIER, LASTNAME, FIRSTNAME, SAP_NUMBER, LASTNAME, FIRSTNAME, OYSTER_NUMBER_1,
                    BACS_NUMBER, "");
    }
    
    public static RefundSearchCmd getSearchLastNameLengthLessThanMinimumLength() {
        return new RefundSearchCmd(REFUND_IDENTIFIER, "S", FIRSTNAME, SAP_NUMBER, "S", FIRSTNAME, OYSTER_NUMBER_1,
                    BACS_NUMBER, CHEQUE_NUMBER);
    }
    
    public static RefundSearchCmd getSearchFirstNameLengthLessThanMinimumLength() {
        return new RefundSearchCmd(REFUND_IDENTIFIER, LASTNAME, "A", SAP_NUMBER, LASTNAME, "A", OYSTER_NUMBER_1,
                    BACS_NUMBER, CHEQUE_NUMBER);
    }
}
