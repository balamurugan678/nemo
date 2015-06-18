package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;

public class FailedAutoTopUpNotificationMessageDTO extends AbstractBaseDTO {
    private static final long serialVersionUID = -3009905411541105437L;

    private String code;
    private String content;

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
