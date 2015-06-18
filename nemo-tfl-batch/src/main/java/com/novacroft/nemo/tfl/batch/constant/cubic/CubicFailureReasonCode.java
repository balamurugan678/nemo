package com.novacroft.nemo.tfl.batch.constant.cubic;

/**
 * CUBIC batch file failure reason code
 */
public enum CubicFailureReasonCode {
    PICK_UP_WINDOW_EXPIRED(500);

    private CubicFailureReasonCode(Integer code) {
        this.code = code;
    }

    private Integer code;

    public Integer code() {
        return this.code;
    }
}
