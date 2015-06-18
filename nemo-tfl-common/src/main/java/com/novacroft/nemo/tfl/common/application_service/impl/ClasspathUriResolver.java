package com.novacroft.nemo.tfl.common.application_service.impl;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

public class ClasspathUriResolver implements URIResolver {

    public Source resolve(String href, String base) throws TransformerException {
        Source source = null;
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(href);
        if (inputStream != null) {
            source = new StreamSource(inputStream);
        }
        return source;
    }
}