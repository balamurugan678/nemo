package com.novacroft.nemo.tfl.common.transfer.cubic;

import com.novacroft.nemo.tfl.common.transfer.BaseResponseDTO;

public class CardRemoveUpdateResponseDTO implements BaseResponseDTO {

    protected String prestigeId;
    protected Integer requestSequenceNumber;
    protected Integer removedRequestSequenceNumber;
    protected Integer errorCode;
    protected String errorDescription;

    public CardRemoveUpdateResponseDTO(Integer errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public CardRemoveUpdateResponseDTO() {
    }

    @Override
    public Integer getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorDescription() {
        return errorDescription;
    }

    public String getPrestigeId() {
        return prestigeId;
    }

    public void setPrestigeId(String prestigeId) {
        this.prestigeId = prestigeId;
    }

    public Integer getRequestSequenceNumber() {
        return requestSequenceNumber;
    }

    public void setRequestSequenceNumber(Integer requestSequenceNumber) {
        this.requestSequenceNumber = requestSequenceNumber;
    }

    public Integer getRemovedRequestSequenceNumber() {
        return removedRequestSequenceNumber;
    }

    public void setRemovedRequestSequenceNumber(Integer removedRequestSequenceNumber) {
        this.removedRequestSequenceNumber = removedRequestSequenceNumber;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

}
