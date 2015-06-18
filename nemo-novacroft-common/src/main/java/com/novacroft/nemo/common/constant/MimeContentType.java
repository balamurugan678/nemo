package com.novacroft.nemo.common.constant;

/**
 * MIME content type
 */
public enum MimeContentType {
    CSV("text/csv");

    private MimeContentType(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return this.code;
    }
}
