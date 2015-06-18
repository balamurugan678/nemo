package com.novacroft.nemo.tfl.services.domain;

/**
 * Domain object for returning true, false.
 */
public class WSResponse {

    public static final String CARTNOTFOUND = "Shopping cart not found";
    public static final String ITEMNOTREMOVED = "Shopping cart item not removed";
    public static final String ITEMNOTADDED = "Shopping cart item not added";

    private Boolean success;
    private String message;
    private String code;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the code
     */
    public final String getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public final void setCode(String code) {
        this.code = code;
    }
}
