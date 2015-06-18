package com.novacroft.nemo.common.application_service.impl;

import com.google.gson.Gson;
import com.novacroft.nemo.common.application_service.CachedContentSerialisationService;
import com.novacroft.nemo.common.support.ReloadableDatabaseMessageSource;
import com.novacroft.nemo.common.transfer.CachedContentDTO;
import com.novacroft.nemo.common.transfer.CachedLocaleMessagesDTO;
import com.novacroft.nemo.common.transfer.CachedMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * JSON serialiser for cached content
 */
@Service("cachedContentSerialisationService")
public class CachedContentSerialisationServiceImpl implements CachedContentSerialisationService {
    protected static final Logger logger = LoggerFactory.getLogger(CachedContentSerialisationServiceImpl.class);

    @Override
    public String serialiseContent(String userLocale, ReloadableDatabaseMessageSource messageSource) {
        return new Gson().toJson(new CachedContentDTO(getLocales(messageSource.getCachedData()), userLocale,
                messageSource.getUseFallBackLocale() ? messageSource.getFallBackLocale() : ""));
    }

    protected List<CachedLocaleMessagesDTO> getLocales(Map<String, Map<String, String>> cachedContent) {
        List<CachedLocaleMessagesDTO> locales = new ArrayList<CachedLocaleMessagesDTO>();
        for (String locale : getLocaleCodes(cachedContent)) {
            locales.add(new CachedLocaleMessagesDTO(locale, getMessages(cachedContent.get(locale))));
        }
        return locales;
    }

    protected List<CachedMessageDTO> getMessages(Map<String, String> localeContent) {
        List<CachedMessageDTO> messages = new ArrayList<CachedMessageDTO>();
        for (String code : getMessageCodes(localeContent)) {
            messages.add(new CachedMessageDTO(code, localeContent.get(code)));
        }
        return messages;
    }

    protected Set<String> getMessageCodes(Map<String, String> localeContent) {
        return localeContent.keySet();
    }

    protected Set<String> getLocaleCodes(Map<String, Map<String, String>> cachedContent) {
        return cachedContent.keySet();
    }
}
