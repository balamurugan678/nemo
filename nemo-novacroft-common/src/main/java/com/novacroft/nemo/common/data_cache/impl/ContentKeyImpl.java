package com.novacroft.nemo.common.data_cache.impl;

import com.novacroft.nemo.common.data_cache.DataCacheKey;

import java.util.Locale;

/**
 * Content data cache key implementation
 */
public class ContentKeyImpl implements DataCacheKey {
    protected String code;
    protected Locale locale;

    public ContentKeyImpl() {
    }

    public ContentKeyImpl(String code, Locale locale) {
        this.code = code;
        this.locale = locale;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
