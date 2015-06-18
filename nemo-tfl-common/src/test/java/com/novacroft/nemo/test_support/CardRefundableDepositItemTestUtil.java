package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.CartTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CartTestUtil.PURCHASE_CART_ID_1;
import static com.novacroft.nemo.test_support.ItemTestUtil.PRICE_1;

import com.novacroft.nemo.tfl.common.domain.CardRefundableDepositItem;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositItemDTO;

/**
 * Utilities for card refundable deposit item tests
 */
public final class CardRefundableDepositItemTestUtil {
    public static final Long CARD_REFUNDABLE_DEPOSIT_ITEM_ID = 32L;
    public static final Long CARD_REFUNDABLE_DEPOSIT_ITEM_ID_1 = 36L;
    public static final Long CARD_ID_2 = 1045676435L;
    public static final Long PURCHASE_CART_ID_2 = 8L;

    public static CardRefundableDepositItemDTO getTestCardRefundableDepositItemDTO1() {
        return getTestCardRefundableDepositItemDTO(CARD_REFUNDABLE_DEPOSIT_ITEM_ID, CARD_ID_1, PURCHASE_CART_ID_1, PRICE_1);
    }

    public static CardRefundableDepositItemDTO getTestCardRefundableDepositItemDTO2() {
        return getTestCardRefundableDepositItemDTO(CARD_REFUNDABLE_DEPOSIT_ITEM_ID_1, CARD_ID_2, PURCHASE_CART_ID_2, PRICE_1);
    }

    public static CardRefundableDepositItemDTO getTestCardRefundableDepositItemDTO(Long cardRefundableDepositItemdId, Long cardId, Long cartId, Integer price) {
        CardRefundableDepositItemDTO dto = new CardRefundableDepositItemDTO();
        dto.setId(cardRefundableDepositItemdId);
        dto.setCardId(cardId);
        dto.setCartId(cartId);
        dto.setPrice(price);
        return dto;
    }

    public static CardRefundableDepositItem getTestCardRefundableDepositItem1() {
        return getTestCardRefundableDepositItem(CARD_REFUNDABLE_DEPOSIT_ITEM_ID, CARD_ID_1, PURCHASE_CART_ID_1, PRICE_1);
    }

    public static CardRefundableDepositItem getTestCardRefundableDepositItem(Long cardRefundableDepositItemdId, Long cardId, Long cartId, Integer price) {
        CardRefundableDepositItem cardRefundableDepositItem = new CardRefundableDepositItem();
        cardRefundableDepositItem.setId(cardRefundableDepositItemdId);
        cardRefundableDepositItem.setCardId(cardId);
        cardRefundableDepositItem.setPrice(price);
        return cardRefundableDepositItem;
    }

}
