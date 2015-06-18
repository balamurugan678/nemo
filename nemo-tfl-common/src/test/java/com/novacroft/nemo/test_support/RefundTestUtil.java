package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.ProductTestUtil.getTestProduct2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ItemType;
import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;

/**
 * Fixtures and utilities for refund tests
 */
public class RefundTestUtil {
    public static final String OTHER = "Unknown";
    public static final String ANNUAL = "Annual";
    public static final String SIX_MONTH = "6Month";
    public static final String THREE_MONTH = "3Month";
    public static final String ONE_MONTH = "1Month";
    public static final String DAY_7 = "7Day";
    public static final Long REFUND_ID_1 = 2L;
    public static final String PICK_UP_LOCATION_NAME_1 = "";
    public static final Integer REFUND_AMOUNT_IN_PENCE_1 = 3456;
    public static final Long REFUND_AMOUNT_IN_PENCE_L = 3456L;
    public static final Integer REFUND_NUMBER_OF_DAYS_1 = 2;
    public static final Integer REFUND_NUMBER_OF_WEEKS_1 = 3;
    public static final Integer REFUND_NUMBER_OF_MONTHS_1 = 1;
    public static final Date REFUNDED_JOURNEY_ON_1 = DateTestUtil.getAug19();
    public static final String REFUND_REASON_1 = "Test Refund";
    public static final String REFUND_REFERENCE_1 = "246813579";
    public static final Date PICK_UP_EXPIRES_ON = DateTestUtil.getAug21();

    public static final String DATE_TODAY = "24/03/2014";
    public static final String EXPIRY_DATE_MORE_THAN_5_DAYS_FROM_DATE_TODAY = "30/03/2014";
    public static final String EXPIRY_DATE_EQUAL_TO_5_DAYS_FROM_DATE_TODAY = "29/03/2014";
    public static final String EXPIRY_DATE_LESS_THAN_5_DAYS_FROM_DATE_TODAY = "28/03/2014";

    public static final String DATE_TODAY_FOR_DAY_LIGHT_SAVINGS = "28/03/2014";
    public static final String EXPIRY_DATE_MORE_THAN_5_DAYS_FROM_DATE_TODAY_FOR_DAY_LIGHT_SAVINGS = "03/04/2014";
    public static final String EXPIRY_DATE_EQUAL_TO_5_DAYS_FROM_DATE_TODAY_FOR_DAY_LIGHT_SAVINGS = "02/04/2014";
    public static final String EXPIRY_DATE_LESS_THAN_5_DAYS_FROM_DATE_TODAY_FOR_DAY_LIGHT_SAVINGS = "01/04/2014";

    public static final long PRODUCTID_TRADEDTICKET = 18L;
    public static final long PRODUCT_ID = 8L;
    public static final Long weeklyProductID = 26L;
    public static Long productId;

    private RefundTestUtil() {
    }
    
    public static Refund getTestRefund1() {
        return getTestRefund(REFUND_AMOUNT_IN_PENCE_L, REFUND_NUMBER_OF_WEEKS_1, REFUND_NUMBER_OF_DAYS_1, REFUND_NUMBER_OF_MONTHS_1, null);
    }

    public static Refund getTestRefund(Long refundAmount, Integer refundDays, Integer refundWeeks, Integer refundMonths, Map<String, String> refundReasonings) {
        Refund refund = new Refund();
        refund.setRefundAmount(refundAmount);
        refund.setRefundableDays(refundDays);
        refund.setRefundableWeeks(refundWeeks);
        refund.setRefundableMonths(refundMonths);
        refund.setRefundReasonings(refundReasonings);
        return refund;
    }

    public static CartCmdImpl buildCommandObject() {
    	CartCmdImpl testCart = CartCmdTestUtil.getTestCartCmd2();
    	CartItemCmdImpl itemCmd = CartItemTestUtil.getTestCartItems().get(0);
    	itemCmd.setTravelCardType(ItemType.TRAVEL_CARD.toString());
    	testCart.setCartItemCmd(itemCmd);
    	return testCart;
    }

    public static ProductDTO getRefundCardProductDTOWeeklyZone1toZone2() {
        return getTestProduct2(5L, 1, 2, 3040, DAY_7);
    }

    public static ProductDTO getRefundCardProductDTOMonthlyBusPassAllLondon() {
        return getTestProduct2(5L, 0, 0, 7530, ONE_MONTH);
    }

    public static ProductDTO getRefundCardProductDTOThreeMonthZone1toZone2() {
        return getTestProduct2(63L, 1, 2, 35030, THREE_MONTH);
    }

    public static ProductDTO getRefundCardProductDTOSixMonthZone1toZone2() {
        return getTestProduct2(92L, 1, 2, 70050, SIX_MONTH);
    }

    public static ProductDTO getRefundCardProductDTOMonthlyZone3toZone5() {
        return getTestProduct2(productId, 3, 5, 9680, ONE_MONTH);
    }

    public static ProductDTO getRefundCardProductDTOWeeklyZone3toZone5() {
        return getTestProduct2(weeklyProductID, 3, 5, 2520, "");
    }

    public static ProductDTO getRefundCardProductDTOWeeklyZone1toZone6() {
        return getTestProduct2(weeklyProductID, 1, 6, 5560, DAY_7);
    }

    public static ProductDTO getRefundCardProductDTOWeeklyZone2toZone3() {
        return getTestProduct2(weeklyProductID, 2, 3, 2300, "");
    }

    public static ProductDTO getRefundCardProductDTOWeeklyZone2toZone5() {
        return getTestProduct2(weeklyProductID, 2, 5, 3020, "");
    }

    public static ProductDTO getRefundCardProductDTOAnnualZone1toZone3() {
        return getTestProduct2(productId, 1, 3, 142400, ANNUAL);
    }

    public static ProductDTO getRefundCardProductDTOAnnualZone1toZone3_2008() {
        return getTestProduct2(productId, 1, 3, 113600, ANNUAL);
    }

    public static ProductDTO getRefundCardProductDTOAnnualZone1toZone4() {
        return getTestProduct2(productId, 1, 4, 147200, ANNUAL);
    }

    public static ProductDTO getRefundCardProductDTOAnnualZone1toZone5() {
        return getTestProduct2(productId, 1, 5, 207200, ANNUAL);
    }

    public static ProductDTO getRefundCardProductDTOAnnualZone2toZone5() {
        return getTestProduct2(productId, 2, 5, 120800, ANNUAL);
    }

    public static ProductDTO getRefundCardProductDTOOddPeriodZone2toZone5() {
        return getTestProduct2(productId, 2, 5, 3020, OTHER);
    }

    public static ProductDTO getRefundCardProductDTOOddPeriodZone1toZone2() {
        return getTestProduct2(productId, 1, 2, 3040, OTHER);
    }

    public static ProductDTO getRefundCardProductDTOOddPeriodZone1toZone6() {
        return getTestProduct2(productId, 1, 6, 93950, OTHER);
    }

    public static ProductDTO getRefundCardProductDTOOddPeriodZone2toZone5_2() {
        return getTestProduct2(productId, 2, 5, 118290, OTHER);
    }

    public static ProductDTO getRefundCardProductDTOOddPeriodZone1toZone3_2() {
        return getTestProduct2(productId, 1, 3, 3560, OTHER);
    }

    public static ProductDTO getRefundCardProductDTO() {
        return getTestProduct2(8L, 1, 5, 5180, DAY_7);
    }

    public static ProductDTO getRefundCardProductDTOForTradedTicket() {
        return getTestProduct2(8L, 1, 5, 3120, DAY_7);
    }

    public static List<ProductDTO> getAllProductDTOAnnualZone2toZone3() {
        List<ProductDTO> list = new ArrayList<ProductDTO>();
        list.add(getTestProduct2(55L, 2, 3, 8840, ONE_MONTH));
        list.add(getTestProduct2(16L, 2, 3, 2300, DAY_7));
        return list;
    }

    public static List<ProductDTO> getAllProductDTOAnnualZone2toZone5() {
        List<ProductDTO> list = new ArrayList<ProductDTO>();
        list.add(getTestProduct2(18L, 2, 5, 3020, DAY_7));
        return list;
    }

    public static List<ProductDTO> getAllProductDTOAnnualZone3toZone5() {
        List<ProductDTO> list = new ArrayList<ProductDTO>();
        list.add(getTestProduct2(26L, 3, 5, 2520, DAY_7));
        return list;
    }

    public static List<ProductDTO> getAllProductDTOAnnualZone1toZone2() {
        List<ProductDTO> list = new ArrayList<ProductDTO>();
        list.add(getTestProduct2(5L, 1, 2, 3040, DAY_7));
        return list;
    }

    public static List<ProductDTO> getAllProductDTOAnnualZone1toZone6() {
        List<ProductDTO> list = new ArrayList<ProductDTO>();
        list.add(getRefundCardProductDTOWeeklyZone1toZone6());
        return list;
    }

    public static ProductDTO getRefundCardProductDTOForSixMonthsZone1to3() {
        return getTestProduct2(93L, 1, 3, 82030, SIX_MONTH);
    }

    public static ProductDTO getRefundCardProductDTOAnnualZone1toZone6() {
        return getTestProduct2(99L, 2, 5, 120800, ANNUAL);
    }

}
