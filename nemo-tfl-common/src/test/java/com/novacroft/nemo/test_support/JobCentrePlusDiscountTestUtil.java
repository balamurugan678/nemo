package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.common.utils.DateUtil.parse;

import java.util.Date;

import com.novacroft.nemo.common.transfer.JobCentrePlusDiscountDTO;

/**
 * Utilities for Cart tests
 */
public final class JobCentrePlusDiscountTestUtil {

    public static final String PRESTIGE_ID_1 = "53373785091L";
    public static final String DISCOUNT_EXPIRY_DATE_1 = "15/10/2013";
    public static final String DISCOUNT_EXPIRY_DATE_2 = "15/12/2013";
    public static final String DISCOUNT_EXPIRY_DATE_3 = "15/11/2014";
    public static final Integer PAY_AS_YOU_GO_BALANCE = 2000;
    public static final Integer JOB_CENTRE_PLUS_DISCOUNT_PRICE_1 = 7900;
    public static final Integer JOB_CENTRE_PLUS_DISCOUNT_PRICE_2 = 85119;
    public static final String JOB_CENTRE_PLUS = "JCP";


    public static JobCentrePlusDiscountDTO getTestJobCentrePlusDiscountDTO1() {
        return getTestJobCentrePlusDiscountDTO(PRESTIGE_ID_1, parse(DISCOUNT_EXPIRY_DATE_1), Boolean.FALSE, PAY_AS_YOU_GO_BALANCE);
    }

    public static JobCentrePlusDiscountDTO getTestJobCentrePlusDiscountDTO2() {
        return getTestJobCentrePlusDiscountDTO(PRESTIGE_ID_1, parse(DISCOUNT_EXPIRY_DATE_2), Boolean.TRUE, PAY_AS_YOU_GO_BALANCE);
    }
    
    public static JobCentrePlusDiscountDTO getTestJobCentrePlusDiscountDTO3() {
        return getTestJobCentrePlusDiscountDTO(PRESTIGE_ID_1, parse(DISCOUNT_EXPIRY_DATE_3), Boolean.TRUE, PAY_AS_YOU_GO_BALANCE);
    }
    
    public static JobCentrePlusDiscountDTO getTestJobCentrePlusDiscountDTO4() {
        return getTestJobCentrePlusDiscountDTO(PRESTIGE_ID_1, new Date(), Boolean.TRUE, PAY_AS_YOU_GO_BALANCE);
    }

    public static JobCentrePlusDiscountDTO getTestJobCentrePlusDiscountNotAvailiable() {
        return getTestJobCentrePlusDiscountDTO(PRESTIGE_ID_1, new Date(), Boolean.FALSE, PAY_AS_YOU_GO_BALANCE);
    }

    public static JobCentrePlusDiscountDTO getTestJobCentrePlusDiscountDTO(String prestigeId, Date discountExpiryDate,
                                                                           Boolean jobCenterPlusDiscountAvailable,
                                                                           Integer payAsYouGoBalance) {
        JobCentrePlusDiscountDTO jobCentrePlusDiscountDTO = new JobCentrePlusDiscountDTO();
        jobCentrePlusDiscountDTO.setPrestigeId(prestigeId);
        jobCentrePlusDiscountDTO.setDiscountExpiryDate(discountExpiryDate);
        jobCentrePlusDiscountDTO.setJobCentrePlusDiscountAvailable(jobCenterPlusDiscountAvailable);
        jobCentrePlusDiscountDTO.setPaygBalance(payAsYouGoBalance);
        return jobCentrePlusDiscountDTO;
    }

}
