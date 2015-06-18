package com.novacroft.nemo.test_support;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;

/**
 * Utilities for Customer tests
 */
public final class FailedCardRefundCartTestUtil {
    public static final String ITEM_1 = "Annual Travelcard";
    public static final int START_ZONE_1 = 1;
    public static final int END_ZONE_1 = 2;
    public static final String TODAY = DateUtil.formatDate(new Date());
    public static final int DAYS_TO_ADD_FOR_UNEXPIRED_PRODUCT_1 = 1;
    public static final String UNEXPIRED_PRODUCT_DATE_ON_DATE_OF_REFUND_1 = DateUtil.formatDate(DateUtil.addDaysToDate(new Date(), DAYS_TO_ADD_FOR_UNEXPIRED_PRODUCT_1));
    public static final int DAYS_TO_ADD_FOR_UNEXPIRED_PRODUCT_2 = 2;
    public static final String UNEXPIRED_PRODUCT_DATE_ON_DATE_OF_REFUND_2 = DateUtil.formatDate(DateUtil.addDaysToDate(new Date(), DAYS_TO_ADD_FOR_UNEXPIRED_PRODUCT_2));
    public static final int DAYS_TO_ADD_FOR_UNEXPIRED_PRODUCT_3 = 3;
    public static final String UNEXPIRED_PRODUCT_DATE_ON_DATE_OF_REFUND_3 = DateUtil.formatDate(DateUtil.addDaysToDate(new Date(), DAYS_TO_ADD_FOR_UNEXPIRED_PRODUCT_3));
    public static final int DAYS_TO_SUBTRACT_FOR_EXPIRED_PRODUCT = -1;
    public static final String EXPIRED_PRODUCT_DATE_ON_DATE_OF_REFUND = DateUtil.formatDate(DateUtil.addDaysToDate(new Date(), DAYS_TO_SUBTRACT_FOR_EXPIRED_PRODUCT));
    public static final Date DATE_OF_REFUND = new Date();

    public static List<CartItemCmdImpl> getTestCartItemCmdImplListWithUnExpiredProductsOnDateOfRefund() {
        List<CartItemCmdImpl> cartItemCmdImplList = new ArrayList<CartItemCmdImpl>();
        cartItemCmdImplList.add(getTestCartItemCmdImplWithUnExpiredDate1());
        cartItemCmdImplList.add(getTestCartItemCmdImplWithUnExpiredDate2());
        cartItemCmdImplList.add(getTestCartItemCmdImplWithUnExpiredDate3());
        return cartItemCmdImplList;
    }

    public static CartItemCmdImpl getTestCartItemCmdImplWithUnExpiredDate1() {
        CartItemCmdImpl cartItemCmdImpl = new CartItemCmdImpl();
        cartItemCmdImpl.setItem(ITEM_1);
        cartItemCmdImpl.setStartZone(START_ZONE_1);
        cartItemCmdImpl.setEndZone(END_ZONE_1);
        cartItemCmdImpl.setStartDate(TODAY);
        cartItemCmdImpl.setEndDate(UNEXPIRED_PRODUCT_DATE_ON_DATE_OF_REFUND_1);

        return cartItemCmdImpl;
    }

    public static CartItemCmdImpl getTestCartItemCmdImplWithUnExpiredDate2() {
        CartItemCmdImpl cartItemCmdImpl = new CartItemCmdImpl();
        cartItemCmdImpl.setItem(ITEM_1);
        cartItemCmdImpl.setStartZone(START_ZONE_1);
        cartItemCmdImpl.setEndZone(END_ZONE_1);
        cartItemCmdImpl.setStartDate(TODAY);
        cartItemCmdImpl.setEndDate(UNEXPIRED_PRODUCT_DATE_ON_DATE_OF_REFUND_1);

        return cartItemCmdImpl;
    }

    public static CartItemCmdImpl getTestCartItemCmdImplWithUnExpiredDate3() {
        CartItemCmdImpl cartItemCmdImpl = new CartItemCmdImpl();
        cartItemCmdImpl.setItem(ITEM_1);
        cartItemCmdImpl.setStartZone(START_ZONE_1);
        cartItemCmdImpl.setEndZone(END_ZONE_1);
        cartItemCmdImpl.setStartDate(TODAY);
        cartItemCmdImpl.setEndDate(UNEXPIRED_PRODUCT_DATE_ON_DATE_OF_REFUND_1);

        return cartItemCmdImpl;
    }

    public static List<CartItemCmdImpl> getTestCartItemCmdImplListWithExpiredProductsOnDateOfRefund() {
        List<CartItemCmdImpl> cartItemCmdImplList = new ArrayList<CartItemCmdImpl>();
        cartItemCmdImplList.add(getTestCartItemCmdImplWithExpiredDate());
        return cartItemCmdImplList;
    }

    public static CartItemCmdImpl getTestCartItemCmdImplWithExpiredDate() {
        CartItemCmdImpl cartItemCmdImpl = new CartItemCmdImpl();
        cartItemCmdImpl.setItem(ITEM_1);
        cartItemCmdImpl.setStartZone(START_ZONE_1);
        cartItemCmdImpl.setEndZone(END_ZONE_1);
        cartItemCmdImpl.setStartDate(TODAY);
        cartItemCmdImpl.setEndDate(EXPIRED_PRODUCT_DATE_ON_DATE_OF_REFUND);

        return cartItemCmdImpl;
    }

}
