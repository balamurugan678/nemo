package com.novacroft.nemo.common.application_service;

import com.novacroft.nemo.common.support.ReloadableDatabaseMessageSource;

/**
 * JSON serialiser for cached content
 */
public interface CachedContentSerialisationService {
    String serialiseContent(String userLocale, ReloadableDatabaseMessageSource messageSource);
}
