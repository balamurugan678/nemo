package com.novacroft.nemo.tfl.common.transfer;


public class WebserviceResultDTO {
    protected Long id;
    protected String result;
    protected String message;

    public WebserviceResultDTO() {
    }

    public WebserviceResultDTO(Long id, String result, String message) {
        this.id = id;
        this.result = result;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
