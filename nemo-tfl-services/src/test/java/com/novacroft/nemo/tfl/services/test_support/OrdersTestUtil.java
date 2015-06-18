package com.novacroft.nemo.tfl.services.test_support;

import static com.novacroft.nemo.common.utils.StringUtil.formatPenceToCurrency;
import static com.novacroft.nemo.test_support.AutoTopUpTestUtil.AUTO_TOP_UP_AMOUNT_1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.REMINDER_DATE_1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.SEVEN_DAY_TRAVEL_CARD_TYPE;
import static com.novacroft.nemo.test_support.CartItemTestUtil.TRAVEL_END_ZONE_1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.TRAVEL_START_ZONE_1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.ADMINISTRATION_FEE_PRICE_1;
import static com.novacroft.nemo.test_support.OrderTestUtil.APPROVAL_ID;
import static com.novacroft.nemo.test_support.OrderTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.OrderTestUtil.CUSTOMER_ID_2;
import static com.novacroft.nemo.test_support.OrderTestUtil.ORDER_NUMBER;
import static com.novacroft.nemo.test_support.OrderTestUtil.STATION_ID;
import static com.novacroft.nemo.test_support.OrderTestUtil.STATUS;
import static com.novacroft.nemo.test_support.OrderTestUtil.TOTAL_AMOUNT;
import static com.novacroft.nemo.test_support.ProductTestUtil.PRODUCT_NAME_1;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.tfl.services.transfer.Item;
import com.novacroft.nemo.tfl.services.transfer.Order;
import com.novacroft.nemo.tfl.services.util.ErrorUtil;

public class OrdersTestUtil {   
    
    public static List<Order> getServiceOrderList() {
        List<Order> orders = Lists.newArrayList();
        orders.add(getTestOrder());
        return orders;
    }
    
    public static Order getTestOrder() {
        Order order = new Order();
        order.setCustomerId(CUSTOMER_ID_2);
        order.setOrderNumber(ORDER_NUMBER);
        order.setOrderDate(DateTestUtil.getAug10());
        order.setTotalAmount(TOTAL_AMOUNT);
        order.setStatus(STATUS);
        order.setStationId(STATION_ID);
        order.setFormattedTotalAmount(formatPenceToCurrency(TOTAL_AMOUNT));
        order.setApprovalId(APPROVAL_ID);
        order.setCardId(CARD_ID_1);
        order.setRefundDate(DateTestUtil.getAug19());
        order.setItems(getItemList1());
        return order;
    }
    
    public static Order getTestOrderWithErrors(){
        Order order = new Order();
        order.setErrors(ErrorResultTestUtil.getTestErrorResult1());
        return order;
    }
    
    public static List<Item> getItemList1() {
        List<Item> items = new ArrayList<Item>();
        Item item = new Item();
        item.setPrice(ADMINISTRATION_FEE_PRICE_1);
        item.setName(PRODUCT_NAME_1);
        item.setStartDate(DateTestUtil.getAug10());
        item.setEndDate(DateTestUtil.getAug22());
        item.setTradedDate(DateTestUtil.getAug20());
        item.setReminderDate(REMINDER_DATE_1);
        item.setStartZone(TRAVEL_START_ZONE_1);
        item.setEndZone(TRAVEL_END_ZONE_1);
        item.setProductType(SEVEN_DAY_TRAVEL_CARD_TYPE);
        item.setAutoTopUpAmount(AUTO_TOP_UP_AMOUNT_1);
        items.add(item);
        return items;
    }

}
