package com.novacroft.nemo.tfl.common.constant;

public enum LostStolenOptionType {
	TRANSFER_CARD("transferCard"),NEW_CARD("newCard"),REFUND_CARD("refundCard");
	private String code;

	private LostStolenOptionType(String code){
		this.code=code;
	}

	public final String code() {
	   return code;
	}
	
	public static LostStolenOptionType lookUpOptionType(String code) {
        if (code != null) {
            for (LostStolenOptionType optionType : LostStolenOptionType.values()) {
                if (code.equalsIgnoreCase(optionType.code)) {
                    return optionType;
                }
            }
        }
        return null;
    }

}
