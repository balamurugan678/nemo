package com.novacroft.nemo.common.domain;

/**
 * Managed content.
 */
public class Content extends AbstractBaseEntity {
	private static final long serialVersionUID = -2637955212375589214L;
	protected String locale;
    protected String code;
    protected String content;

    public Content() {
        super();
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
