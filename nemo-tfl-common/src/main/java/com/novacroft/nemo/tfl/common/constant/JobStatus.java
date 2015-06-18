package com.novacroft.nemo.tfl.common.constant;

/**
 * Job status
 */
public enum JobStatus {
    COMPLETE("Complete"), IN_COMPLETE("InComplete");

    private JobStatus(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return this.code;
    }
}
