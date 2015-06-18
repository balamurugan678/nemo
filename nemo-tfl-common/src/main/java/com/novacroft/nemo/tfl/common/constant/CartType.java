package com.novacroft.nemo.tfl.common.constant;

/**
 * 
 * Cart types
 * 
 */
public enum CartType {
	PURCHASE("Purchase")
	, FAILED_CARD_REFUND("FailedCardRefund")
	, DESTROYED_CARD_REFUND("DestroyedCardRefund")
	, RENEW("Renew")
	, LOST_REFUND("LostRefund")
	, STOLEN_REFUND("StolenRefund")
	, CANCEL_SURRENDER_REFUND("CancelAndSurrenderRefund")
	, STANDALONE_GOODWILL_REFUND("StandaloneGoodwillRefund")
	, ANONYMOUS_GOODWILL_REFUND("AnonymousGoodwillRefund")
	, TRANSFER_PRODUCT("TransferProduct");

	private CartType(String code) {
		this.code = code;
	}

	private String code;

	public String code() {
		return this.code;
	}

    public static CartType find(String code) {
        if (code != null) {
            for (CartType type : CartType.values()) {
                if (code.equalsIgnoreCase(type.code)) {
                    return type;
                }
            }
        }
        return null;
    }
}