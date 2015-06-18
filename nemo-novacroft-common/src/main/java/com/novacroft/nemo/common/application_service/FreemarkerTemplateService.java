package com.novacroft.nemo.common.application_service;

import freemarker.template.Template;

/**
 * Freemarker template service specification
 */
public interface FreemarkerTemplateService {
    Template getTemplate(String name);
}
