package com.novacroft.nemo.tfl.common.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.novacroft.nemo.common.application_service.CachedContentSerialisationService;
import com.novacroft.nemo.common.support.ReloadableDatabaseMessageSource;
import com.novacroft.nemo.tfl.common.application_service.ContentCodeSerialisationService;
import com.novacroft.nemo.tfl.common.constant.PageUrl;

/**
 * Controller to dynamically create a JavaScript library that includes the content so that it can be used on the client
 */
@Controller
@RequestMapping(value = PageUrl.CONTENT_FOR_JAVA_SCRIPT)
public class ContentForJavaScriptController {
    protected static final Logger logger = LoggerFactory.getLogger(ContentForJavaScriptController.class);

    @Autowired
    protected ReloadableDatabaseMessageSource messageSource;
    @Autowired
    protected CachedContentSerialisationService cachedContentSerialisationService;
    @Autowired
    protected ContentCodeSerialisationService contentCodeSerialisationService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getContentAsJavaScript(Locale locale, HttpServletResponse response) {
        response.setContentType("text/javascript");
        return getJavaScript(locale);
    }

    protected String getJavaScript(Locale locale) {
        StringBuilder javaScript = new StringBuilder();
        javaScript.append(getContent(locale));
        javaScript.append(getContentCodes());
        return javaScript.toString();
    }

    protected String getContent(Locale locale) {
        return "var content=" + this.cachedContentSerialisationService.serialiseContent(locale.toString(), messageSource) + ";";
    }

    protected String getContentCodes() {
        return "var contentCode=" + this.contentCodeSerialisationService.serialiseContentCodes() + ";";
    }
}
