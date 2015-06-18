package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.tfl.common.domain.GoodwillReason;

/*
 * A cart item extension type of goodwill payment
 */
public class CancelAndSurrenderRefundItemDTO extends ItemDTO {

    protected GoodwillReason goodwillReason;

    public CancelAndSurrenderRefundItemDTO() {
    }

    public CancelAndSurrenderRefundItemDTO(Long cardId, Long cartId, String createdUserId, Long id, Integer price, Boolean nullable, GoodwillReason goodwillReason) {
        this.cardId = cardId;
        this.cartId = cartId;
        this.createdUserId = createdUserId;
        this.id = id;
        this.price = price;
        this.nullable = nullable;
        this.goodwillReason = goodwillReason;
    }

    public GoodwillReason getGoodwillReason() {
        return goodwillReason;
    }

    public void setGoodwillReason(GoodwillReason goodwillReason) {
        this.goodwillReason = goodwillReason;
    }

}
