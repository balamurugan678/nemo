package com.novacroft.nemo.tfl.common.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * CUBIC Auto Load (Auto Top Up) State
 */
public enum AutoLoadState {
    NO_TOP_UP(1, 0, ContentCode.AUTO_TOP_UP_NO_TOP_UP),
    TOP_UP_AMOUNT_2(2, 2000, ContentCode.AUTO_TOP_UP_TOP_UP_AMOUNT_2),
    TOP_UP_AMOUNT_3(3, 4000, ContentCode.AUTO_TOP_UP_TOP_UP_AMOUNT_3),
    TOP_UP_AMOUNT_4(4, null, ContentCode.AUTO_TOP_UP_TOP_UP_AMOUNT_4);

    private Integer state;
    private Integer topUpAmountInPence;
    private ContentCode contentCode;

    private AutoLoadState(Integer state, Integer topUpAmountInPence, ContentCode contentCode) {
        this.state = state;
        this.topUpAmountInPence = topUpAmountInPence;
        this.contentCode = contentCode;
    }

    public Integer state() {
        return this.state;
    }

    public Integer topUpAmount() {
        return this.topUpAmountInPence;
    }

    public static final Integer lookUpState(Integer topUpAmount) {
        for (AutoLoadState autoLoadState : AutoLoadState.values()) {
            if (autoLoadState.topUpAmountInPence != null && autoLoadState.topUpAmountInPence.equals(topUpAmount)) {
                return autoLoadState.state;
            }
        }
        throw new IllegalArgumentException(String.format(PrivateError.INVALID_AUTO_AMOUNT.message(), topUpAmount));
    }

    public static final Integer lookUpAmount(Integer state) {
        for (AutoLoadState autoLoadState : AutoLoadState.values()) {
            if (autoLoadState.state != null && autoLoadState.state.equals(state)) {
                return autoLoadState.topUpAmountInPence;
            }
        }
        throw new IllegalArgumentException(String.format(PrivateError.INVALID_AUTO_STATE.message(), state));
    }

    public static final String lookUpContentCode(Integer state) {
        for (AutoLoadState autoLoadState : AutoLoadState.values()) {
            if (autoLoadState.state != null && autoLoadState.state.equals(state)) {
                return autoLoadState.contentCode.textCode();
            }
        }
        throw new IllegalArgumentException(String.format(PrivateError.INVALID_AUTO_STATE.message(), state));
    }

    public static final List<String> getStates() {
        List<String> states = new ArrayList<String>();
        for (AutoLoadState autoLoadState : AutoLoadState.values()) {
            states.add(String.valueOf(autoLoadState.state()));
        }
        return states;
    }
}
