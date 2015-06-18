package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.test_support.CartItemTestUtil.PAY_AS_YOU_GO_TICKET_PRICE_1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.PAY_AS_YOU_GO_TICKET_PRICE_DEFAULT;
import static com.novacroft.nemo.test_support.CartItemTestUtil.PAY_AS_YOU_Go_END_DATE_1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.PAY_AS_YOU_Go_REMINDER_DATE_1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.PAY_AS_YOU_Go_START_DATE_1;
import static com.novacroft.nemo.test_support.CartTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CartTestUtil.PURCHASE_CART_ID_1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.novacroft.nemo.tfl.common.domain.PayAsYouGoItem;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;

/**
 * Utilities for pay as you go product item tests
 */
public final class PayAsYouGoItemTestUtil {
    public static final Long PAY_AS_YOU_GO_ID_1 = 301L;
    public static final Long ITEM_CARD_ID = 123456789L;
    public static final Long ITEM_CART_ID = 1234L;
    public static final Long ITEM_ORDER_ID = 123L;
    public static final Integer ITEM_PRICE = 100;    
    public static final Integer ITEM_PRICE_ZERO = 0;    
    
    public static List<ItemDTO> getListOfPayAsYouGoItemDTOs() {
        ItemDTO item = getItemDTO1();
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        items.add(item);
        return items;
    }

    public static List<ItemDTO> getListOfNonPayAsYouGoItemDTOsWithNonZeroPrice() {
        ItemDTO item = getItemDTO2();
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        items.add(item);
        return items;
    }

    public static List<ItemDTO> getListOfNonPayAsYouGoItemDTOsWithZeroPrice() {
        ItemDTO item = getItemDTO3();
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        items.add(item);
        return items;
    }

    public static List<ItemDTO> getListOfPayAsYouGoItemDTOsWithZeroPrice() {
        ItemDTO item = getItemDTO4();
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        items.add(item);
        return items;
    }

    public static ItemDTO getItemDTO1() {
        return createPayAsYouGoItemDTO(ITEM_CARD_ID, ITEM_CART_ID, ITEM_ORDER_ID, ITEM_PRICE);
    }

    public static ItemDTO getItemDTO2() {
        return createNonPayAsYouGoItemDTO(ITEM_CARD_ID, ITEM_CART_ID, ITEM_ORDER_ID, ITEM_PRICE);
    }

    public static ItemDTO getItemDTO3() {
        return createNonPayAsYouGoItemDTO(ITEM_CARD_ID, ITEM_CART_ID, ITEM_ORDER_ID, ITEM_PRICE_ZERO);
    }

    public static ItemDTO getItemDTO4() {
        return createPayAsYouGoItemDTO(ITEM_CARD_ID, ITEM_CART_ID, ITEM_ORDER_ID, ITEM_PRICE_ZERO);
    }

    public static ItemDTO createPayAsYouGoItemDTO(Long cardId, Long cartId, Long orderId, Integer price) {
        ItemDTO dto = new PayAsYouGoItemDTO();
        dto.setOrderId(orderId);
        dto.setCardId(cardId);
        dto.setCartId(cartId);
        dto.setPrice(price);
        return dto;
    }

    public static ItemDTO createNonPayAsYouGoItemDTO(Long cardId, Long cartId, Long orderId, Integer price) {
        ItemDTO dto = new AdministrationFeeItemDTO();
        dto.setOrderId(orderId);
        dto.setCardId(cardId);
        dto.setCartId(cartId);
        dto.setPrice(price);
        return dto;
    }

    public static PayAsYouGoItemDTO getTestPayAsYouGoItemDTO1() {
        return getTestPayAsYouGoItemDTO(PAY_AS_YOU_GO_ID_1, CARD_ID_1, PURCHASE_CART_ID_1, parse(PAY_AS_YOU_Go_START_DATE_1),
                parse(PAY_AS_YOU_Go_END_DATE_1), PAY_AS_YOU_GO_TICKET_PRICE_1, PAY_AS_YOU_Go_REMINDER_DATE_1);
    }

    public static PayAsYouGoItemDTO getTestPayAsYouGoItemDTO2() {
        return getTestPayAsYouGoItemDTO(PAY_AS_YOU_GO_ID_1, CARD_ID_1, PURCHASE_CART_ID_1, parse(PAY_AS_YOU_Go_START_DATE_1),
                        parse(PAY_AS_YOU_Go_END_DATE_1), PAY_AS_YOU_GO_TICKET_PRICE_DEFAULT, PAY_AS_YOU_Go_REMINDER_DATE_1);
    }

    public static PayAsYouGoItemDTO getTestPayAsYouGoItemDTO(Long payAsYouGoId, Long cardId, Long cartId, Date startDate,
                                                             Date endDate, Integer price, String reminderDate) {
        PayAsYouGoItemDTO dto = new PayAsYouGoItemDTO();
        dto.setPayAsYouGoId(payAsYouGoId);
        dto.setCardId(cardId);
        dto.setCartId(cartId);
        dto.setStartDate(startDate);
        dto.setEndDate(endDate);
        dto.setPrice(price);
        dto.setReminderDate(reminderDate);
        return dto;
    }

    public static PayAsYouGoItem getTestPayAsYouGoItem1() {
        return getTestPayAsYouGoItem(PAY_AS_YOU_GO_ID_1, CARD_ID_1, PURCHASE_CART_ID_1, parse(PAY_AS_YOU_Go_START_DATE_1),
                parse(PAY_AS_YOU_Go_END_DATE_1), PAY_AS_YOU_GO_TICKET_PRICE_1, PAY_AS_YOU_Go_REMINDER_DATE_1);
    }

    public static PayAsYouGoItem getTestPayAsYouGoItem(Long payAsYouGoId, Long cardId, Long cartId, Date startDate,
                                                       Date endDate, Integer price, String reminderDate) {
        PayAsYouGoItem payAsYouGoItem = new PayAsYouGoItem();
        payAsYouGoItem.setPayAsYouGoId(payAsYouGoId);
        payAsYouGoItem.setCardId(cardId);
        payAsYouGoItem.setStartDate(startDate);
        payAsYouGoItem.setEndDate(endDate);
        payAsYouGoItem.setPrice(price);
        payAsYouGoItem.setReminderDate(reminderDate);
        return payAsYouGoItem;
    }

}
