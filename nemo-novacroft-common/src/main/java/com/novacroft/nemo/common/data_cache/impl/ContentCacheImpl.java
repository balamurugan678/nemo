package com.novacroft.nemo.common.data_cache.impl;

import com.novacroft.nemo.common.data_cache.CachingDataService;
import com.novacroft.nemo.common.data_cache.ContentCache;
import com.novacroft.nemo.common.data_cache.DataCacheKey;
import com.novacroft.nemo.common.data_cache.DataCacheValue;
import com.novacroft.nemo.common.data_service.ContentDataService;
import com.novacroft.nemo.common.transfer.ContentDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Content data cache implementation
 */
public class ContentCacheImpl extends AbstractDataCache implements ContentCache {
    protected Map<String, Map<String, String>> cache = new HashMap<String, Map<String, String>>();

    public ContentCacheImpl() {
        super();
    }

    public ContentCacheImpl(Integer cacheRefreshIntervalInSeconds, CachingDataService cachingDataService) {
        super(cacheRefreshIntervalInSeconds, cachingDataService);
    }

    @Override
    protected void loadCache() {
        synchronized (this.cache) {
            this.cache = new HashMap<String, Map<String, String>>();
            List<ContentDTO> content = ((ContentDataService) this.cachingDataService).findAll();
            for (ContentDTO c : content) {
                if (!this.cache.containsKey(c.getLocale())) {
                    this.cache.put(c.getLocale(), new HashMap<String, String>());
                }
                this.cache.get(c.getLocale()).put(c.getCode().toLowerCase(), c.getContent());
            }
        }
    }

    @Override
    protected DataCacheValue getValueFromCache(DataCacheKey key) {
        String code = ((ContentKeyImpl) key).getCode();
        String locale = ((ContentKeyImpl) key).getLocale().toString();
        String content = this.cache.get(locale).get(code.toLowerCase());
        return new ContentValueImpl(content);
    }

    @Override
    public String getValue(String code, Locale locale) {
        ContentKeyImpl key = new ContentKeyImpl(code, locale);
        ContentValueImpl value = (ContentValueImpl) getValue(key);
        return value.getContent();
    }

    @Override
    public Boolean containsLocale(Locale locale) {
        refreshCacheIfNeeded();
        return this.cache.containsKey(locale.toString());
    }

    @Override
    public Boolean containsCodeForLocale(String code, Locale locale) {
        refreshCacheIfNeeded();
        return containsLocale(locale) && this.cache.get(locale.toString()).containsKey(code.toLowerCase());
    }

    @Override
    public Map<String, Map<String, String>> getCachedData() {
        return this.cache;
    }
}
