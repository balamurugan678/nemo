package com.novacroft.nemo.common.data_cache;

import java.util.Locale;
import java.util.Map;

/**
 * Content data cache specification
 */
public interface ContentCache extends DataCache {
    String getValue(String code, Locale locale);

    Boolean containsLocale(Locale locale);

    Boolean containsCodeForLocale(String code, Locale locale);

    Map<String, Map<String, String>> getCachedData();
}
