package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.domain.ShippingMethod;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodDTO;

/**
 * Utilities for shipping method tests
 */

public final class ShippingMethodTestUtil {
    public static final String SHIPPING_METHOD_FIRST_CLASS_1 = "First Class";
    public static final Integer SHIPPING_METHOD_FIRST_CLASS_PRICE_1 = 0;
    public static final String SHIPPING_METHOD_RECORDED_DELIVERY_1 = "Recorded Delivery";
    public static final Integer SHIPPING_METHOD_RECORDED_DELIVERY_PRICE_1 = 565;
    public static final Integer SHIPPING_METHOD_RECORDED_DELIVERY_PRICE_2 = 865;

    public static ShippingMethodDTO getTestShippingMethodDTO1() {
        return getTestShippingMethodDTO(SHIPPING_METHOD_RECORDED_DELIVERY_1, SHIPPING_METHOD_RECORDED_DELIVERY_PRICE_1);
    }

    public static ShippingMethodDTO getTestShippingMethodDTO(String name, Integer price) {
        ShippingMethodDTO shippingMethodDTO = new ShippingMethodDTO();
        shippingMethodDTO.setName(name);
        shippingMethodDTO.setPrice(price);
        return shippingMethodDTO;
    }

    public static ShippingMethod getTestShippingMethod1() {
        return getTestShippingMethod(SHIPPING_METHOD_RECORDED_DELIVERY_1, SHIPPING_METHOD_RECORDED_DELIVERY_PRICE_1);
    }

    public static ShippingMethod getTestShippingMethod(String name, Integer price) {
        ShippingMethod shippingMethod = new ShippingMethod();
        shippingMethod.setName(name);
        shippingMethod.setPrice(price);
        return shippingMethod;
    }

}
