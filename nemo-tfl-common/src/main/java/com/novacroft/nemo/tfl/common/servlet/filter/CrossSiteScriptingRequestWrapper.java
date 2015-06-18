package com.novacroft.nemo.tfl.common.servlet.filter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

public class CrossSiteScriptingRequestWrapper extends HttpServletRequestWrapper {
    static final Logger logger = LoggerFactory.getLogger(CrossSiteScriptingRequestWrapper.class);
    
    private static final Set<String> XSS_FILTER_VALUES = new HashSet<String>(Arrays.asList(new String[] { "<", ">" }));

    public CrossSiteScriptingRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = handleXSS(values[i]);
        }
        return encodedValues;
    }

    public String getParameter(String parameter) {
        return getEncodedValue(super.getParameter(parameter));
    }


    public String getHeader(String name) {
        return getEncodedValue(super.getHeader(name));
    }
    
    protected String getEncodedValue(String value) {
        if (value == null) {
            return null;
        }
        return handleXSS(value);
    }

    protected String handleXSS(String value) {
        if (isValueContainsXSS(value)) {
            value = HtmlUtils.htmlUnescape(value);
            value = HtmlUtils.htmlEscape(value);
        }
        return value;
    }
    
    protected boolean isValueContainsXSS(String value) {
        for (String xssString : XSS_FILTER_VALUES) {
            if (value.toUpperCase().contains(xssString)) {
                return true;
            }
        }
        return false;
    }
    
}
