package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.domain.ProductItem;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_2;
import static com.novacroft.nemo.test_support.CartTestUtil.CART_ID_2;
import static com.novacroft.nemo.test_support.CartTestUtil.PURCHASE_CART_ID_1;
import static com.novacroft.nemo.test_support.ProductTestUtil.*;
import static com.novacroft.nemo.test_support.CartItemTestUtil.*;

/**
 * Utilities for product item tests
 */
public final class ProductItemTestUtil {

    public static final Long PRODUCT_Item_ID_1 = 10L;
    public static final Long PRODUCT_Item_ID_2 = 11L;
    public static final String DURATION_1_MONTH = "1Month";

    public static List<ProductItemDTO> getTestBussPassProductDTOList1() {
        List<ProductItemDTO> productItems = new ArrayList<ProductItemDTO>();
        productItems.add(getTestBussPassProductDTO1());
        return productItems;
    }

    public static ProductItemDTO getTestBussPassProductDTO1() {
        return getTestProductItemDTO(PRODUCT_Item_ID_1, CARD_ID_1, PURCHASE_CART_ID_1, parse(ANNUAL_START_DATE_1),
                parse(ANNUAL_END_DATE_1), null, null, ANNUAL_PRICE_1, PRODUCT_ID_1, REMINDER_DATE_1);
    }

    public static List<ProductItemDTO> getTestTravelCardProductDTOList1() {
        List<ProductItemDTO> productItems = new ArrayList<ProductItemDTO>();
        productItems.add(getTestTravelCardProductDTO1());
        return productItems;
    }

    public static List<ProductItem> getTestTravelCardProductList1() {
        List<ProductItem> productItems = new ArrayList<ProductItem>();
        productItems.add(getTestTravelCardProduct1());
        return productItems;
    }

    public static ProductItemDTO getTestTravelCardProductDTO1() {
        return getTestProductItemDTO(PRODUCT_Item_ID_1, CARD_ID_1, PURCHASE_CART_ID_1, parse(TRAVEL_START_DATE_1),
                parse(TRAVEL_END_DATE_1), TRAVEL_START_ZONE_1, TRAVEL_END_ZONE_1, TRAVEL_PRICE_1, PRODUCT_ID_1,
                REMINDER_DATE_1);
    }

    public static List<ProductItemDTO> getTestOtherTravelCardProductDTOList1() {
        List<ProductItemDTO> productItems = new ArrayList<ProductItemDTO>();
        productItems.add(getTestOtherTravelCardProductDTO1());
        return productItems;
    }

    public static ProductItemDTO getTestOtherTravelCardProductDTO1() {
        return getTestProductItemDTO(PRODUCT_Item_ID_1, CARD_ID_1, PURCHASE_CART_ID_1, parse(OTHER_TRAVEL_START_DATE_1),
                parse(OTHER_TRAVEL_END_DATE_1), TRAVEL_START_ZONE_1, TRAVEL_END_ZONE_1, TRAVEL_PRICE_1, PRODUCT_ID_1,
                REMINDER_DATE_1);
    }

    public static ProductItemDTO getTestProductItemDTO(Long productItemId, Long cardId, Long cartId, Date startDate,
                                                       Date endDate, Integer startZone, Integer endZone, Integer price,
                                                       Long productId, String reminderDate) {
        ProductItemDTO productItemDTO = new ProductItemDTO();
        productItemDTO.setId(productItemId);
        productItemDTO.setCardId(cardId);
        productItemDTO.setCartId(cartId);
        productItemDTO.setStartDate(startDate);
        productItemDTO.setEndDate(endDate);
        productItemDTO.setStartZone(startZone);
        productItemDTO.setEndZone(endZone);
        productItemDTO.setPrice(price);
        productItemDTO.setProductId(productId);
        productItemDTO.setReminderDate(reminderDate);
        return productItemDTO;
    }
    public static ProductItemDTO getTestProductItemDTO(Long productItemId, Long cardId, Long cartId, Date startDate,
                    Date endDate, Integer startZone, Integer endZone, Integer price,
                    Long productId, String reminderDate,String duration) {
        ProductItemDTO productItemDTO = new ProductItemDTO();
        productItemDTO.setId(productItemId);
        productItemDTO.setCardId(cardId);
        productItemDTO.setCartId(cartId);
        productItemDTO.setStartDate(startDate);
        productItemDTO.setEndDate(endDate);
        productItemDTO.setStartZone(startZone);
        productItemDTO.setEndZone(endZone);
        productItemDTO.setPrice(price);
        productItemDTO.setProductId(productId);
        productItemDTO.setReminderDate(reminderDate);
        productItemDTO.setDuration(duration);
        return productItemDTO;
     }


    public static ProductItem getTestBussPassProduct1() {
        return getTestProductItem(PRODUCT_Item_ID_1, CARD_ID_1, PURCHASE_CART_ID_1, parse(ANNUAL_START_DATE_1), parse(ANNUAL_END_DATE_1),
                null, null, ANNUAL_PRICE_1, PRODUCT_ID_1, REMINDER_DATE_1);
    }

    public static ProductItem getTestTravelCardProduct1() {
        return getTestProductItem(PRODUCT_Item_ID_1, CARD_ID_1, PURCHASE_CART_ID_1, parse(TRAVEL_START_DATE_1), parse(TRAVEL_END_DATE_1),
                TRAVEL_START_ZONE_1, TRAVEL_END_ZONE_1, TRAVEL_PRICE_1, PRODUCT_ID_1, REMINDER_DATE_1);
    }

    public static ProductItem getTestOtherTravelCardProduct1() {
        return getTestProductItem(PRODUCT_Item_ID_1, CARD_ID_1, PURCHASE_CART_ID_1, parse(OTHER_TRAVEL_START_DATE_1),
                parse(OTHER_TRAVEL_END_DATE_1), TRAVEL_START_ZONE_1, TRAVEL_END_ZONE_1, TRAVEL_PRICE_1, PRODUCT_ID_1,
                REMINDER_DATE_1);
    }

    public static ProductItem getTestProductItem(Long productItemId, Long cardId, Long cartId, Date startDate, Date endDate,
                                                 Integer startZone, Integer endZone, Integer price, Long productId,
                                                 String reminderDate) {
        ProductItem productItem = new ProductItem();
        productItem.setId(productItemId);
        productItem.setCardId(cardId);
        productItem.setStartDate(startDate);
        productItem.setEndDate(endDate);
        productItem.setStartZone(startZone);
        productItem.setEndZone(endZone);
        productItem.setPrice(price);
        productItem.setProductId(productId);
        productItem.setReminderDate(reminderDate);
        return productItem;
    }

    public static ProductItemDTO getTestTravelCardProductDTO_RefundExample1() {
        return getTestProductItemDTO(PRODUCT_Item_ID_1, CARD_ID_1, PURCHASE_CART_ID_1, parse(TRAVEL_START_DATE_1),
                parse(TRAVEL_END_DATE_1), TRAVEL_START_ZONE_1, TRAVEL_END_ZONE_1, TRAVEL_PRICE_1, PRODUCT_ID_1,
                REMINDER_DATE_1);
    }
    
    public static ProductItemDTO getTestOtherTravelCardProductItemDTO1() {
        return getTestProductItemDTO(PRODUCT_Item_ID_1, CARD_ID_1, PURCHASE_CART_ID_1, parse(OTHER_TRAVEL_START_DATE_1),
                parse(OTHER_TRAVEL_END_DATE_1), TRAVEL_START_ZONE_1, TRAVEL_END_ZONE_1, TRAVEL_PRICE_1, PRODUCT_ID_1,
                REMINDER_DATE_1);
    }
    
    public static ProductItemDTO getTestPayAsYouGoProductItemDTO1() {
        return getTestProductItemDTO(PRODUCT_Item_ID_2, CARD_ID_2, CART_ID_2, parse(PAY_AS_YOU_Go_START_DATE_1),
                parse(PAY_AS_YOU_Go_END_DATE_1), TRAVEL_START_ZONE_1, TRAVEL_END_ZONE_1, PAY_AS_YOU_GO_TICKET_PRICE_DEFAULT, PRODUCT_ID_2,
                PAY_AS_YOU_Go_REMINDER_DATE_1);
    }
    
    public static List<ItemDTO> getTestEmptyItemDTOList() {
        List<ItemDTO> cartItems = new ArrayList<ItemDTO>();
        return cartItems;
    }
    
    public static ProductItemDTO getTestTravelCardProductDTO2() {
        return getTestProductItemDTO(PRODUCT_Item_ID_1, CARD_ID_1, PURCHASE_CART_ID_1, parse(TRAVEL_START_DATE_5),
                parse(TRAVEL_END_DATE_5), TRAVEL_START_ZONE_5, TRAVEL_END_ZONE_5, TRAVEL_PRICE_5, PRODUCT_ID_1,
                REMINDER_DATE_1);
    }
}
