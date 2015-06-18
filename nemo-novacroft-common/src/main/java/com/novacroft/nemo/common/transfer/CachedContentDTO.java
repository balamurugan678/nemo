package com.novacroft.nemo.common.transfer;

import java.util.ArrayList;
import java.util.List;

/**
 * Transfer class for cached content
 */
public class CachedContentDTO {
    protected List<CachedLocaleMessagesDTO> localeMessages = new ArrayList<CachedLocaleMessagesDTO>();
    protected String userLocale;
    protected String fallBackLocale;

    public CachedContentDTO() {
    }

    public CachedContentDTO(List<CachedLocaleMessagesDTO> localeMessages, String userLocale, String fallBackLocale) {
        this.localeMessages = localeMessages;
        this.userLocale = userLocale;
        this.fallBackLocale = fallBackLocale;
    }

    public List<CachedLocaleMessagesDTO> getLocaleMessages() {

        return localeMessages;
    }

    public void setLocaleMessages(List<CachedLocaleMessagesDTO> localeMessages) {
        this.localeMessages = localeMessages;
    }

    public String getFallBackLocale() {
        return fallBackLocale;
    }

    public void setFallBackLocale(String fallBackLocale) {
        this.fallBackLocale = fallBackLocale;
    }

    public String getUserLocale() {
        return userLocale;
    }

    public void setUserLocale(String userLocale) {
        this.userLocale = userLocale;
    }
}
