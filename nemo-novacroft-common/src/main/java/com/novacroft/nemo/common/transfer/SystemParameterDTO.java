package com.novacroft.nemo.common.transfer;

/**
 * System Parameter transfer class
 */
public class SystemParameterDTO extends AbstractBaseDTO {
    protected String code;
    protected String value;
    protected String purpose;

    public SystemParameterDTO() {
    }
    
    public SystemParameterDTO(String code, String value) {
        this.code = code;
        this.value = value;
    }
    
    public SystemParameterDTO(String code, String value, String purpose) {
        this.code = code;
        this.value = value;
        this.purpose = purpose;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
