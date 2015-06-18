package com.novacroft.nemo.common.application_service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Locale;

/**
 * Base implementation for a service
 */
public abstract class BaseService {
    protected static final Logger logger = LoggerFactory.getLogger(BaseService.class);

    @Autowired
    protected ApplicationContext applicationContext;

    public String getContent(String code) {
        return this.applicationContext.getMessage(code, null, null);
    }

    public String getContent(String code, String... messageArguments) {
        return this.applicationContext.getMessage(code, messageArguments, null);
    }

    public String getContent(String code, Locale locale, String... messageArguments) {
        return this.applicationContext.getMessage(code, messageArguments, locale);
    }

}
