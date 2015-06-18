package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.domain.CardRefundableDeposit;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositDTO;

/**
 * Utilities for shipping method tests
 */

public final class CardRefundableDepositTestUtil {
    public static final Integer CARD_REFUNDABLE_DEPOSIT_AMOUNT_1 = 500;

    public static CardRefundableDepositDTO getTestCardRefundableDepositDTO1() {
        return getTestCardRefundableDepositDTO(CARD_REFUNDABLE_DEPOSIT_AMOUNT_1);
    }

    public static CardRefundableDepositDTO getTestCardRefundableDepositDTO(Integer cardRefundableDepositAmount) {
        CardRefundableDepositDTO dto = new CardRefundableDepositDTO();
        dto.setPrice(cardRefundableDepositAmount);
        return dto;
    }

    public static CardRefundableDeposit getTestCardRefundableDeposit1() {
        return getTestCardRefundableDeposit(CARD_REFUNDABLE_DEPOSIT_AMOUNT_1);
    }

    public static CardRefundableDeposit getTestCardRefundableDeposit(Integer cardRefundableDepositAmount) {
        CardRefundableDeposit cardRefundableDeposit = new CardRefundableDeposit();
        cardRefundableDeposit.setPrice(cardRefundableDepositAmount);
        return cardRefundableDeposit;
    }

}
