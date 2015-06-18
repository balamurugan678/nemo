package com.novacroft.nemo.common.transfer;

import java.util.List;

/**
 * Transfer class for cached content localeMessages
 */
public class CachedLocaleMessagesDTO {
    protected String locale;
    protected List<CachedMessageDTO> messages;

    public CachedLocaleMessagesDTO() {
    }

    public CachedLocaleMessagesDTO(String locale, List<CachedMessageDTO> messages) {
        this.locale = locale;
        this.messages = messages;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public List<CachedMessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<CachedMessageDTO> messages) {
        this.messages = messages;
    }
}
