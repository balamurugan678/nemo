package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.constant.financial_services_centre.BACSRejectCodeEnum;
import com.novacroft.nemo.tfl.common.domain.BACSSettlement;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;

import java.util.Date;

import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.*;

/**
 * Utilities for get BacsRefundPaymentService tests
 */
public final class BACSSettlementTestUtil {

    public static final String BANK_ACCOUNT = "12345678";
    public static final String SORT_CODE = "11-22-33";
    public static final String STATUS = "test approved";
    public static final Long ORDER_ID = 55L;
    public static final Long PAYMENT_REFERENCE = 55L;
    public static final Date SETTLEMENT_DATE = DateTestUtil.get1Jan();
    public static final Date PAYMENT_FAILED_DATE = DateTestUtil.get31Jan();
    public static final BACSRejectCodeEnum BACS_REJECT_CODE = BACSRejectCodeEnum.C;

    public static final String[] BACS_REQUESTS_HANDLED_RECORD =
            new String[]{REFERENCE_NUMBER_AS_STRING, NET_AMOUNT_AS_STRING, CUSTOMER_NAME, REFERENCE_NUMBER_AS_STRING,
                    AUG_19_FSC_FORMAT_STRING};

    public static final String[] BACS_REJECT_RECORD =
            new String[]{REFERENCE_NUMBER_AS_STRING, NET_AMOUNT_AS_STRING, BACSRejectCodeEnum.C.name(),
                    AUG_19_FSC_FORMAT_STRING};

    public static BACSSettlementDTO getTestBACSSettlementDTO1() {
        return getTestBACSSettlementDTO(BANK_ACCOUNT, SORT_CODE, SETTLEMENT_DATE, PAYMENT_FAILED_DATE, BACS_REJECT_CODE);
    }

    public static BACSSettlementDTO getTestBACSSettlementDTO2() {
        BACSSettlementDTO cmd = new BACSSettlementDTO();
        cmd.setBankAccount(BANK_ACCOUNT);
        cmd.setSortCode(SORT_CODE);
        cmd.setSettlementDate(SETTLEMENT_DATE);
        cmd.setOrderId(ORDER_ID);
        cmd.setPaymentReference(PAYMENT_REFERENCE);
        return cmd;
    }

    private static BACSSettlementDTO getTestBACSSettlementDTO(String bankAccount, String sortCode, Date bankPaymentDate,
                                                              Date paymentFailedDate,
                                                              BACSRejectCodeEnum financialServicesRejectCode) {
        BACSSettlementDTO cmd = new BACSSettlementDTO();
        cmd.setBankAccount(bankAccount);
        cmd.setSortCode(sortCode);
        cmd.setBankPaymentDate(bankPaymentDate);
        cmd.setPaymentFailedDate(paymentFailedDate);
        cmd.setFinancialServicesRejectCode(financialServicesRejectCode);
        return cmd;
    }

    private static BACSSettlement getTestBACSSettlement(String bankAccount, String sortCode, Date bankPaymentDate,
                                                        Date paymentFailedDate,
                                                        BACSRejectCodeEnum financialServicesRejectCode) {
        BACSSettlement settlement = new BACSSettlement();
        settlement.setBankAccount(bankAccount);
        settlement.setSortCode(sortCode);
        settlement.setBankPaymentDate(bankPaymentDate);
        settlement.setPaymentFailedDate(paymentFailedDate);
        settlement.setFinancialServicesRejectCode(financialServicesRejectCode);
        return settlement;
    }

    public static BACSSettlement getTestBACSSettlement1() {
        return getTestBACSSettlement(BANK_ACCOUNT, SORT_CODE, SETTLEMENT_DATE, PAYMENT_FAILED_DATE, BACS_REJECT_CODE);
    }
}
