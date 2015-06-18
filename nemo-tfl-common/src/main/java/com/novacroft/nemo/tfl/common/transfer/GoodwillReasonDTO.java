package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;

/*
 * Transports the GoodwillReason in the GoodwillReason Table
 */
public class GoodwillReasonDTO extends AbstractBaseDTO {

    protected Long reasonId;
    protected String description;
    private String extraValidationCode;
    private String type;
    
    public GoodwillReasonDTO() {
    }

    public GoodwillReasonDTO(Long reasonId, String description) {
        this.reasonId = reasonId;
        this.description = description;
    }
    
    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtraValidationCode() {
        return extraValidationCode;
    }

    public void setExtraValidationCode(String extraValidationCode) {
        this.extraValidationCode = extraValidationCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
