package com.novacroft.nemo.common.data_cache.impl;

import com.novacroft.nemo.common.data_cache.DataCacheValue;

/**
 * Content data cache value implementation
 */
public class ContentValueImpl implements DataCacheValue {
    protected String content;

    public ContentValueImpl() {
    }

    ContentValueImpl(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
