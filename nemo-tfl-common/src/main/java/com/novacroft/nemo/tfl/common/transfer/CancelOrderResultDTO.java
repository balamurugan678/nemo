package com.novacroft.nemo.tfl.common.transfer;

public class CancelOrderResultDTO extends WebserviceResultDTO {
    private Long originalId;

    public CancelOrderResultDTO(Long id, Long originalId, String result, String message) {
        super(id, result, message);
        this.originalId = originalId;
    }

    public CancelOrderResultDTO(Long id, String result, String message) {
        super(id, result, message);
    }

    public CancelOrderResultDTO(Long id) {
        this.id = id;
    }

    public CancelOrderResultDTO(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public CancelOrderResultDTO() {
    }

    public Long getOriginalId() {
        return originalId;
    }

    public void setOriginalId(Long originalId) {
        this.originalId = originalId;
    }

}
