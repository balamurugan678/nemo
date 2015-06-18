package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.CartItemTestUtil.ADMINISTRATION_FEE_CREATED_USER_ID_2;
import static com.novacroft.nemo.test_support.CartItemTestUtil.ADMINISTRATION_FEE_ID_2;
import static com.novacroft.nemo.test_support.CartItemTestUtil.ADMINISTRATION_FEE_PRICE_1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.ADMINISTRATION_FEE_PRICE_2;
import static com.novacroft.nemo.test_support.CartTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CartTestUtil.PURCHASE_CART_ID_1;

import java.util.Date;

import com.novacroft.nemo.tfl.common.domain.AdministrationFeeItem;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

/**
 * Utilities for administration fee item tests
 */
public final class AdministrationFeeItemTestUtil {
    public static final Long ADMINISTRATION_FEE_ID_1 = 301L;
    public static final Integer ADMINISTRATION_FEE_CART_TOTAL_1 = 1600;
    public static final Integer ADMINISTRATION_FEE_CART_TOTAL_2 = 600;
    public static final Integer ADMINISTRATION_FEE_CART_TOTAL_3 = 1200;
    public static final Integer ADMINISTRATION_FEE_DEFAULT = 1600;

    public static AdministrationFeeItemDTO getTestAdministrationFeeItemDTO1() {
        return getTestAdministrationFeeItemDTO1(ADMINISTRATION_FEE_ID_1, CARD_ID_1, PURCHASE_CART_ID_1, ADMINISTRATION_FEE_PRICE_2);
    }
    
    public static AdministrationFeeItemDTO getTestAdministrationFeeItemDTODefaultPrice() {
        return getTestAdministrationFeeItemDTO1(ADMINISTRATION_FEE_ID_1, CARD_ID_1, PURCHASE_CART_ID_1, ADMINISTRATION_FEE_PRICE_1);
    }

    public static ItemDTO getTestAdministrationFeeItemDTO2() {
        return getTestAdministrationFeeItemDTO2(ADMINISTRATION_FEE_ID_1, CARD_ID_1, PURCHASE_CART_ID_1, ADMINISTRATION_FEE_PRICE_1);
    }
    
    public static ItemDTO getTestAdministrationFeeItemDTO3() {
        return getTestAdministrationFeeItemDTO3(ADMINISTRATION_FEE_ID_2, ADMINISTRATION_FEE_PRICE_2, ADMINISTRATION_FEE_CREATED_USER_ID_2, new Date());
    }

    public static AdministrationFeeItemDTO getTestAdministrationFeeItemDTO1(Long administrationFeeId, Long cardId, Long cartId, Integer price) {
        AdministrationFeeItemDTO dto = new AdministrationFeeItemDTO();
        dto.setAdministrationFeeId(administrationFeeId);
        dto.setCardId(cardId);
        dto.setCartId(cartId);
        dto.setPrice(price);
        return dto;
    }
    
    public static ItemDTO getTestAdministrationFeeItemDTO2(Long administrationFeeId, Long cardId, Long cartId, Integer price) {
        ItemDTO dto = new AdministrationFeeItemDTO();
        dto.setId(administrationFeeId);
        dto.setCardId(cardId);
        dto.setCartId(cartId);
        dto.setPrice(price);
        return dto;
    }
    
    public static AdministrationFeeItemDTO getTestAdministrationFeeItemDTO3(Long administrationFeeId, Integer price, String createdUserId, Date createdDateTime) {
        AdministrationFeeItemDTO dto = new AdministrationFeeItemDTO();
        dto.setAdministrationFeeId(administrationFeeId);
        dto.setPrice(price);
        dto.setCreatedUserId(createdUserId);
        dto.setCreatedDateTime(createdDateTime);
        return dto;
    }

    public static AdministrationFeeItem getTestAdministrationFeeItem1() {
        return getTestAdministrationFeeItem(ADMINISTRATION_FEE_ID_1, CARD_ID_1, PURCHASE_CART_ID_1, ADMINISTRATION_FEE_PRICE_1);
    }
    
    public static AdministrationFeeItem getTestAdministrationFeeItem2() {
        return getTestAdministrationFeeItem2(ADMINISTRATION_FEE_ID_2, ADMINISTRATION_FEE_PRICE_2, ADMINISTRATION_FEE_CREATED_USER_ID_2, new Date());
    }

    public static AdministrationFeeItem getTestAdministrationFeeItem(Long administrationFeeId, Long cardId, Long cartId, Integer price) {
        return new AdministrationFeeItem(administrationFeeId, cardId, cartId, price);
    }

    public static AdministrationFeeItem getTestAdministrationFeeItem2(Long administrationFeeId, Integer price, String createdUserId, Date createdDateTime) {
        return new AdministrationFeeItem(administrationFeeId, price, createdUserId, createdDateTime);
    }
}
