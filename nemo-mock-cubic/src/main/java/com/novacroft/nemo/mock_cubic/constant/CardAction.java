package com.novacroft.nemo.mock_cubic.constant;

/**
 * Store the actions that can be carried out by the mock cubic calls.
 */
public enum CardAction {
    GET_CARD("GetCard"),
    UPDATE_CARD("UpdateCard"),
    UPDATE_CARD_ERROR("UpdateCardError")
    ;

    private String code;

    private CardAction(final String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }
}
