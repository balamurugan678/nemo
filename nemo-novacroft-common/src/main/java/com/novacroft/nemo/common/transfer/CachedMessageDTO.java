package com.novacroft.nemo.common.transfer;

/**
 * Transfer class for cached content messages
 */
public class CachedMessageDTO {
    protected String code;
    protected String message;

    public CachedMessageDTO() {
    }

    public CachedMessageDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
