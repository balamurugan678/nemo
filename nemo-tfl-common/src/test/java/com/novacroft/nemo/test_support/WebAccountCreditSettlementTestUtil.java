package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.domain.WebAccountCreditSettlement;
import com.novacroft.nemo.tfl.common.transfer.SettlementDTO;

import java.util.Date;

/**
 * WebAccountCreditSettlement unit test fixtures and utilities
 */
public final class WebAccountCreditSettlementTestUtil {
    public static final Long ORDER_ID_1 = 42L;
    public static final String STATUS_1 = "test-status";
    public static final Date SETTLEMENT_DATE_1 = DateTestUtil.getAug19();
    public static final Integer AMOUNT_1 = 3456;

    public static WebAccountCreditSettlement getTestWebAccountCreditSettlement1() {
        return getTestWebAccountCreditSettlement(ORDER_ID_1, STATUS_1, SETTLEMENT_DATE_1, AMOUNT_1);
    }

    public static SettlementDTO getTestWebAccountCreditSettlementDTO1() {
        return getTestWebAccountCreditSettlementDTO(ORDER_ID_1, STATUS_1, SETTLEMENT_DATE_1, AMOUNT_1);
    }

    public static WebAccountCreditSettlement getTestWebAccountCreditSettlement(Long orderId, String status, Date settlementDate,
                                                                               Integer amount) {
        WebAccountCreditSettlement settlement = new WebAccountCreditSettlement();
        settlement.setOrderId(orderId);
        settlement.setStatus(status);
        settlement.setSettlementDate(settlementDate);
        settlement.setAmount(amount);
        return settlement;
    }

    public static SettlementDTO getTestWebAccountCreditSettlementDTO(Long orderId, String status, Date settlementDate,
                                                                     Integer amount) {
        SettlementDTO settlement = new SettlementDTO();
        settlement.setOrderId(orderId);
        settlement.setStatus(status);
        settlement.setSettlementDate(settlementDate);
        settlement.setAmount(amount);
        return settlement;
    }

    private WebAccountCreditSettlementTestUtil() {
    }
}
