package com.novacroft.nemo.tfl.services.transfer;

/**
 * Class to hold generic response to be returned webservice methods (for example a delete request) that does not require information other than some
 * indication that the request failed or completed successfully.<br/>
 * <b>Usage:</b><br/>
 * <code>id</code> - the primary identifier (for example cartId)<br/>
 * <code>result</code> - indicator to denote success/failure <br/>
 * <code>message</code> - cause to error to be returned to webservice consumer.
 */
public class WebServiceResult {

    private Long id;
    private Long originalId;
    private String result = "";
    private String message = "";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOriginalId() {
        return originalId;
    }

    public void setOriginalId(Long originalId) {
        this.originalId = originalId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
