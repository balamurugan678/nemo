package com.novacroft.nemo.common.application_service.impl;

import com.novacroft.nemo.common.application_service.FreemarkerTemplateService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Freemarker template service implementation
 */
@Service("freemarkerTemplateService")
public class FreemarkerTemplateServiceImpl implements FreemarkerTemplateService {
    protected Configuration configuration;

    @PostConstruct
    public void initialiseConfiguration() {
        this.configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        configuration.setClassForTemplateLoading(this.getClass(), "/freemarker");
    }

    @Override
    public Template getTemplate(String name) {
        try {
            return this.configuration.getTemplate(name);
        } catch (IOException e) {
            throw new ApplicationServiceException(e.getMessage(), e);
        }
    }
}
