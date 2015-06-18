package com.novacroft.nemo.common.support;

import com.novacroft.nemo.common.data_cache.CachingDataService;
import com.novacroft.nemo.common.data_cache.ContentCache;
import com.novacroft.nemo.common.data_cache.impl.ContentCacheImpl;
import com.novacroft.nemo.common.data_service.ContentDataService;
import com.novacroft.nemo.common.transfer.ContentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractMessageSource;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;

import static com.novacroft.nemo.common.constant.DateConstant.FIVE_MINUTES_IN_SECONDS;
import static com.novacroft.nemo.common.constant.LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE;

/**
 * <p>
 * message source implementation that retrieves messages from the database rather than a properties file.
 * <p/>
 * <p>
 * Messages (content) are referenced using a code.  The code is a string that represents a hierarchy, with the hierarchy
 * levels separated by dots.  If content is not found for a code, the hierarchy will be traversed upwards until a code
 * is found or the top of the hierarchy is reached.  The next code up in the hierarchy is found by taking off the
 * portion of the code to the left of the left-most dot, including the dot.
 * </p>
 * <p>
 * This implementation support message (content) caching.  The cache is refreshed periodically if content in the
 * database has been added or modified.
 * </p>
 */
public class ReloadableDatabaseMessageSource extends AbstractMessageSource {
    private static final Logger logger = LoggerFactory.getLogger(ReloadableDatabaseMessageSource.class);
    protected ContentDataService dataService;
    protected Boolean useFallBackLocale = Boolean.TRUE;
    protected Locale fallBackLocale = ENGLISH_UNITED_KINGDOM_LOCALE;
    protected Boolean useCodeForNullContent = Boolean.FALSE;
    protected Boolean useCache = Boolean.TRUE;
    protected Integer cacheRefreshIntervalInSeconds = FIVE_MINUTES_IN_SECONDS;
    protected ContentCache cache;

    /**
     * <code>init-method</code> - should be called in the bean definition.
     */
    public void initialiseCache() {
        assert (null != dataService);
        assert (null != cacheRefreshIntervalInSeconds);
        this.cache = new ContentCacheImpl(cacheRefreshIntervalInSeconds, (CachingDataService) dataService);
    }

    @Override
    public MessageFormat resolveCode(String code, Locale locale) {
        return new MessageFormat(resolveContent(code, locale).getContent());
    }

    @Override
    public String resolveCodeWithoutArguments(String code, Locale locale) {
        return resolveContent(code, locale).getContent();
    }

    protected ContentDTO resolveContent(String code, Locale locale) {
        ContentDTO content = getContent(code, locale);
        if (content == null) {
            content = new ContentDTO(locale.toString(), code);
            if (this.useCodeForNullContent) {
                content.setContent(code);
                logger.warn(String.format("Null content for code [%s]", code));
            }
        }
        return content;
    }

    protected ContentDTO getContent(String code, Locale locale) {
        ContentDTO content = null;
        if (this.useCache) {
            content = getContentFromCache(code, locale);
        } else {
            content = getContentFromDB(code, locale);
        }
        if (content == null && isNotTopOfCodeHierarchy(code)) {
            content = getContent(getNextCodeUpHierarchy(code), locale);
        }
        return content;
    }

    protected ContentDTO getContentFromDB(String code, Locale locale) {
        ContentDTO content = dataService.findByLocaleAndCode(code, locale.toString());
        if (content == null && useFallBackLocale) {
            content = dataService.findByLocaleAndCode(code, fallBackLocale.toString());
        }
        return content;
    }

    protected ContentDTO getContentFromCache(String code, Locale locale) {
        if (this.cache.containsCodeForLocale(code, locale)) {
            return new ContentDTO(locale.toString(), code, this.cache.getValue(code, locale));
        }

        if (this.useFallBackLocale && this.cache.containsCodeForLocale(code, fallBackLocale)) {
            return new ContentDTO(locale.toString(), code, this.cache.getValue(code, fallBackLocale));
        }

        return null;
    }

    protected boolean isNotTopOfCodeHierarchy(String code) {
        return code.contains(".");
    }

    protected String getNextCodeUpHierarchy(String code) {
        return code.substring(code.indexOf(".") + 1);
    }

    /**
     * Content data service will be injected by Spring.
     */
    public void setDataService(ContentDataService dataService) {
        this.dataService = dataService;
    }

    /**
     * <p>Controls whether the fall back locale will be used if there is no content for the user's locale.</p>
     *
     * @param useFallBackLocale Set to <code>true</code> to use the fall back locale.  Defaults to <code>true</code>.
     */
    public void setUseFallBackLocale(Boolean useFallBackLocale) {
        this.useFallBackLocale = useFallBackLocale;
    }

    /**
     * <p>Specifies the locale to use if there is no content for the user's locale.</p>
     *
     * @param fallBackLocale Set to the locale to fall back to.  Defaults to "en_GB".
     */
    public void setFallBackLocale(String fallBackLocale) {
        this.fallBackLocale = new Locale(fallBackLocale);
    }

    /**
     * <p>Controls whether the code is returned when there is no content for the code.</p>
     * <p>A warning message is logged when this flag is set.</p>
     * <p>Recommend that this is only set to true in development environments!</p>
     *
     * @param useCodeForNullContent Set to <code>true</code> to make the code the content.  Defaults to <code>false</code>.
     */
    public void setUseCodeForNullContent(Boolean useCodeForNullContent) {
        this.useCodeForNullContent = useCodeForNullContent;
    }

    /**
     * <p>Controls whether the content should be cached.</p>
     * <p>Each content request will result in a separate database access if the cache is not enabled.</p>
     * <p>Recommend that this is only set to false in development environments!</p>
     *
     * @param useCache Set to <code>true</code> to cache the content.  Defaults to <code>true</code>.
     */
    public void setUseCache(Boolean useCache) {
        this.useCache = useCache;
    }

    /**
     * <p>Sets the length of time in seconds between cache refreshes.</p>
     *
     * @param cacheRefreshIntervalInSeconds Length of time between cache refreshes.  Defaults to five minutes.
     */
    public void setCacheRefreshIntervalInSeconds(Integer cacheRefreshIntervalInSeconds) {
        this.cacheRefreshIntervalInSeconds = cacheRefreshIntervalInSeconds;
    }

    public Map<String, Map<String, String>> getCachedData() {
        return this.cache.getCachedData();
    }

    public String getFallBackLocale() {
        return fallBackLocale.toString();
    }

    public Boolean getUseFallBackLocale() {
        return useFallBackLocale;
    }
}
