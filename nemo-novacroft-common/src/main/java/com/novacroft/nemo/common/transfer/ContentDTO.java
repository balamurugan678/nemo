package com.novacroft.nemo.common.transfer;

/**
 * Content data transfer class.
 */
public class ContentDTO extends AbstractBaseDTO {
    protected String locale = "";
    protected String code = "";
    protected String content = "";

    public ContentDTO() {
    }

    public ContentDTO(String locale, String code) {
        this.locale = locale;
        this.code = code;
        this.content = "";
    }

    public ContentDTO(String locale, String code, String content) {
        this.locale = locale;
        this.code = code;
        this.content = content;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
