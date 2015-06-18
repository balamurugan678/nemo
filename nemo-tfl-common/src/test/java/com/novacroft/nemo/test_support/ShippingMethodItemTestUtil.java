package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.CartTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CartTestUtil.PURCHASE_CART_ID_1;
import static com.novacroft.nemo.test_support.ShippingMethodTestUtil.SHIPPING_METHOD_RECORDED_DELIVERY_PRICE_1;
import static com.novacroft.nemo.test_support.ShippingMethodTestUtil.SHIPPING_METHOD_RECORDED_DELIVERY_PRICE_2;

import com.novacroft.nemo.tfl.common.domain.ShippingMethodItem;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodItemDTO;

/**
 * Utilities for shipping method item tests
 */
public final class ShippingMethodItemTestUtil {
    public static final Long SHIPPING_METHOD_ITEM_ID = 30L;
    public static final Long SHIPPING_METHOD_ITEM_ID1 = 60L;
    public static final Long SHIPPING_METHOD_ID = 1L;

    public static ShippingMethodItemDTO getTestShippingMethodItemDTO1() {
        return getTestShippingMethodItemDTO(SHIPPING_METHOD_ITEM_ID, SHIPPING_METHOD_ID, CARD_ID_1, PURCHASE_CART_ID_1, SHIPPING_METHOD_RECORDED_DELIVERY_PRICE_1);
    }

    public static ShippingMethodItemDTO getTestShippingMethodItemDTO2() {
        return getTestShippingMethodItemDTO(SHIPPING_METHOD_ITEM_ID1, SHIPPING_METHOD_ID, CARD_ID_1, PURCHASE_CART_ID_1, SHIPPING_METHOD_RECORDED_DELIVERY_PRICE_2);
    }

    public static ShippingMethodItemDTO getTestShippingMethodItemDTO(Long shippingMethodItemdId, Long shippingMethodId, Long cardId, Long cartId, Integer price) {
        ShippingMethodItemDTO dto = new ShippingMethodItemDTO();
        dto.setId(shippingMethodItemdId);
        dto.setShippingMethodId(shippingMethodId);
        dto.setCardId(cardId);
        dto.setCartId(cartId);
        dto.setPrice(price);
        return dto;
    }

    public static ShippingMethodItem getTestShippingMethodItem1() {
        return getTestShippingMethodItem(SHIPPING_METHOD_ITEM_ID, SHIPPING_METHOD_ID, CARD_ID_1, PURCHASE_CART_ID_1, SHIPPING_METHOD_RECORDED_DELIVERY_PRICE_1);
    }

    public static ShippingMethodItem getTestShippingMethodItem(Long shippingMethodItemdId, Long shippingMethodId, Long cardId, Long cartId, Integer price) {
        ShippingMethodItem shippingMethodItem = new ShippingMethodItem();
        shippingMethodItem.setId(shippingMethodItemdId);
        shippingMethodItem.setShippingMethodId(shippingMethodId);
        shippingMethodItem.setCardId(cardId);
        shippingMethodItem.setPrice(price);
        return shippingMethodItem;
    }

}
