package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.domain.AutoTopUp;
import com.novacroft.nemo.tfl.common.domain.AutoTopUpConfigurationItem;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;

/**
 * Utilities for auto top-up item tests
 */
public final class AutoTopUpTestUtil {
    public static final Long AUTO_TOP_UP_ITEM_ID_1 = 31L;
    public static final Long CARD_ID_1 = 3L;
    public static final Long CART_ID_1 = 1569L;
    public static final Long AUTO_TOP_UP_ITEM_ID_2 = 36L;
    public static final Long CARD_ID_2 = 5L;
    public static final Long CART_ID_2 = 1590L;
    public static final Integer CREDIT_BALANCE_1 = 15;
    public static final Integer AUTO_TOP_UP_AMOUNT_1 = 15;
    public static final Boolean ACTIVE_1 = false;
    public static final Long AUTO_TOP_UP_ID_1 = 35L;
    public static final String CONTENT_CODE_AUTO_TOPUP = "autoTopup.text";
    public static final String CONTENT_CODE_AUTO_TOPUP_NAME = "Test Auto Topup Item";
    public static final String TEST_FIELD_AUTO_TOP_UP = "Test_autotopup";
    public static final String AUTO_TOP_UP_ACTIVITY = "Set-up";
    
    public static AutoTopUpConfigurationItemDTO getTestAutoTopUpItemDTO1() {
        return getTestAutoTopUpItemDTO(AUTO_TOP_UP_ITEM_ID_1, CARD_ID_1, CART_ID_1, AUTO_TOP_UP_AMOUNT_1);
    }

    public static AutoTopUpConfigurationItemDTO getTestAutoTopUpItemDTO2() {
        return getTestAutoTopUpItemDTO(AUTO_TOP_UP_ITEM_ID_2, CARD_ID_2, CART_ID_2, AUTO_TOP_UP_AMOUNT_1);
    }

    public static AutoTopUpConfigurationItemDTO getTestAutoTopUpItemDTO(Long autoTopupItemId, Long cardId, Long cartId, Integer autoTopUpAmount) {
        AutoTopUpConfigurationItemDTO dto = new AutoTopUpConfigurationItemDTO();
        dto.setId(autoTopupItemId);
        dto.setCartId(cartId);
        dto.setCardId(cardId);
        dto.setAutoTopUpAmount(autoTopUpAmount);
        return dto;
    }

    public static AutoTopUpConfigurationItem getTestAutoTopUpItem1() {
        return getTestAutoTopUpItem(AUTO_TOP_UP_ITEM_ID_1, CARD_ID_1, CART_ID_1, AUTO_TOP_UP_AMOUNT_1);
    }

    public static AutoTopUpConfigurationItem getTestAutoTopUpItem(Long autoTopupItemId, Long cardId, Long cartId, Integer autoTopUpAmount) {
        AutoTopUpConfigurationItem autoTopUpItem = new AutoTopUpConfigurationItem();
        autoTopUpItem.setId(autoTopupItemId);
        autoTopUpItem.setCardId(cardId);
        autoTopUpItem.setAutoTopUpAmount(autoTopUpAmount);
        return autoTopUpItem;
    }

    public static AutoTopUpDTO getTestAutoTopUpDTO1() {
        return getTestAutoTopUpDTO(AUTO_TOP_UP_ID_1, AUTO_TOP_UP_AMOUNT_1);
    }

    public static AutoTopUpDTO getTestAutoTopUpDTO(Long autoTopupId, Integer autoTopUpAmount) {
        AutoTopUpDTO dto = new AutoTopUpDTO();
        dto.setId(autoTopupId);
        dto.setAutoTopUpAmount(autoTopUpAmount);
        return dto;
    }

    public static AutoTopUp getTestAutoTopUp1() {
        return getTestAutoTopUp(AUTO_TOP_UP_ID_1, AUTO_TOP_UP_AMOUNT_1);
    }

    public static AutoTopUp getTestAutoTopUp(Long autoTopupId, Integer autoTopUpAmount) {
        AutoTopUp autoTopUp = new AutoTopUp();
        autoTopUp.setId(autoTopupId);
        autoTopUp.setAutoTopUpAmount(autoTopUpAmount);
        return autoTopUp;
    }

}
