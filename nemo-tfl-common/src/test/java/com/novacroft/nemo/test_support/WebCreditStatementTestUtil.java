package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.command.impl.WebCreditStatementCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.WebCreditStatementLineCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.WebCreditStatementLineDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.novacroft.nemo.test_support.DateTestUtil.*;

/**
 * Web Account Credit Statement test fixtures and utilities
 */
public final class WebCreditStatementTestUtil {

    public static final String REFUND = "Refund";
    public static final String PURCHASE = "Purchase";

    public static final Long SETTLEMENT_ID_1 = 1L;
    public static final String SETTLEMENT_STATUS_1 = "";
    public static final Date SETTLEMENT_DATE_1 = getAug19();
    public static final Integer SETTLEMENT_AMOUNT_1 = 2345;
    public static final Long ORDER_ID_1 = 6L;
    public static final Long ORDER_NUMBER_1 = 7L;
    public static final Date ORDER_DATE_1 = getAug20();
    public static final Integer ORDER_TOTAL_AMOUNT_1 = 8901;
    public static final String ORDER_STATUS_1 = "";

    public static final Long SETTLEMENT_ID_2 = 2L;
    public static final String SETTLEMENT_STATUS_2 = "";
    public static final Date SETTLEMENT_DATE_2 = getAug20();
    public static final Integer SETTLEMENT_AMOUNT_2 = -5678;
    public static final Long ORDER_ID_2 = 3L;
    public static final Long ORDER_NUMBER_2 = 4L;
    public static final Date ORDER_DATE_2 = getAug21();
    public static final Integer ORDER_TOTAL_AMOUNT_2 = 9876;
    public static final String ORDER_STATUS_2 = "";

    public static Object[] getTestRow1() {
        return new Object[]{(Object) SETTLEMENT_ID_1, (Object) SETTLEMENT_STATUS_1, (Object) SETTLEMENT_DATE_1,
                (Object) SETTLEMENT_AMOUNT_1, (Object) ORDER_ID_1, (Object) ORDER_NUMBER_1, (Object) ORDER_DATE_1,
                (Object) ORDER_TOTAL_AMOUNT_1, (Object) ORDER_STATUS_1};
    }

    public static Object[] getTestRow2() {
        return new Object[]{(Object) SETTLEMENT_ID_2, (Object) SETTLEMENT_STATUS_2, (Object) SETTLEMENT_DATE_2,
                (Object) SETTLEMENT_AMOUNT_2, (Object) ORDER_ID_2, (Object) ORDER_NUMBER_2, (Object) ORDER_DATE_2,
                (Object) ORDER_TOTAL_AMOUNT_2, (Object) ORDER_STATUS_2};
    }

    public static List<Object[]> getTestRowList1() {
        List<Object[]> list = new ArrayList<Object[]>();
        list.add(getTestRow1());
        return list;
    }

    public static List<Object[]> getTestRowList2() {
        List<Object[]> list = getTestRowList1();
        list.add(getTestRow2());
        return list;
    }

    public static WebCreditStatementLineDTO getTestWebCreditStatementLineDTO1() {
        return getTestWebCreditStatementLineDTO(SETTLEMENT_ID_1, SETTLEMENT_STATUS_1, SETTLEMENT_DATE_1,
                SETTLEMENT_AMOUNT_1, ORDER_ID_1, ORDER_NUMBER_1, ORDER_DATE_1, ORDER_TOTAL_AMOUNT_1, ORDER_STATUS_1);
    }

    public static WebCreditStatementLineDTO getTestWebCreditStatementLineDTO2() {
        return getTestWebCreditStatementLineDTO(SETTLEMENT_ID_2, SETTLEMENT_STATUS_2, SETTLEMENT_DATE_2,
                SETTLEMENT_AMOUNT_2, ORDER_ID_2, ORDER_NUMBER_2, ORDER_DATE_2, ORDER_TOTAL_AMOUNT_2, ORDER_STATUS_2);
    }

    public static List<WebCreditStatementLineDTO> getTestWebCreditStatementLineDTOList1() {
        List<WebCreditStatementLineDTO> list = new ArrayList<WebCreditStatementLineDTO>();
        list.add(getTestWebCreditStatementLineDTO1());
        return list;
    }

    public static List<WebCreditStatementLineDTO> getTestWebCreditStatementLineDTOList2() {
        List<WebCreditStatementLineDTO> list = getTestWebCreditStatementLineDTOList1();
        list.add(getTestWebCreditStatementLineDTO2());
        return list;
    }

    public static WebCreditStatementLineCmdImpl getTestWebCreditStatementLineCmd1() {
        return getTestWebCreditStatementLineCmd(SETTLEMENT_DATE_1, PURCHASE, ORDER_NUMBER_1.toString(),
                SETTLEMENT_AMOUNT_1, null, 0);
    }

    public static WebCreditStatementLineCmdImpl getTestWebCreditStatementLineCmd2() {
        return getTestWebCreditStatementLineCmd(SETTLEMENT_DATE_2, REFUND, ORDER_NUMBER_2.toString(), null,
                SETTLEMENT_AMOUNT_2, 0);
    }

    public static List<WebCreditStatementLineCmdImpl> getTestWebCreditStatementLineCmdList1() {
        List<WebCreditStatementLineCmdImpl> list = new ArrayList<WebCreditStatementLineCmdImpl>();
        list.add(getTestWebCreditStatementLineCmd1());
        return list;
    }

    public static List<WebCreditStatementLineCmdImpl> getTestWebCreditStatementLineCmdList2() {
        List<WebCreditStatementLineCmdImpl> list = getTestWebCreditStatementLineCmdList1();
        list.add(getTestWebCreditStatementLineCmd2());
        return list;
    }

    public static WebCreditStatementCmdImpl getTestWebCreditStatementCmdImpl1() {
        return getTestWebCreditStatementCmdImpl(SETTLEMENT_AMOUNT_1 + SETTLEMENT_AMOUNT_2,
                getTestWebCreditStatementLineCmdList2());
    }

    public static WebCreditStatementLineDTO getTestWebCreditStatementLineDTO(Long settlementId,
                                                                                           String settlementStatus,
                                                                                           Date settlementDate,
                                                                                           Integer settlementAmount,
                                                                                           Long orderId, Long orderNumber,
                                                                                           Date orderDate,
                                                                                           Integer orderTotalAmount,
                                                                                           String orderStatus) {
        return new WebCreditStatementLineDTO(settlementId, settlementStatus, settlementDate, settlementAmount, orderId,
                orderNumber, orderDate, orderTotalAmount, orderStatus);
    }

    public static WebCreditStatementLineCmdImpl getTestWebCreditStatementLineCmd(Date transactionDate,
                                                                                               String item,
                                                                                               String referenceNumber,
                                                                                               Integer creditAmount,
                                                                                               Integer debitAmount,
                                                                                               Integer cumulativeBalanceAmount) {
        return new WebCreditStatementLineCmdImpl(transactionDate, item, referenceNumber, creditAmount, debitAmount,
                cumulativeBalanceAmount);
    }

    public static WebCreditStatementCmdImpl getTestWebCreditStatementCmdImpl(Integer currentBalance,
                                                                                           List<WebCreditStatementLineCmdImpl> statementLines) {
        return new WebCreditStatementCmdImpl(currentBalance, statementLines);
    }

    private WebCreditStatementTestUtil() {
    }
}
