package com.novacroft.nemo.common.utils;

import com.novacroft.nemo.common.constant.CommonPrivateError;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Uniform resource identifier (URI) and uniform resource locator (URL) utilities
 */
public final class UriUrlUtil {
    protected static final Logger logger = LoggerFactory.getLogger(UriUrlUtil.class);
    public static final String PATH_SEPARATOR = "/";

    private UriUrlUtil() {
    }

    /**
     * Get current application URI.  That's the start of the URL up to and including the context path.
     */
    public static URI getApplicationBaseUri(HttpServletRequest request) {
        URI applicationBaseUri = null;
        try {
            URIBuilder builder = new URIBuilder();
            builder.setScheme(request.getScheme());
            builder.setHost(request.getServerName());
            builder.setPort(Integer.valueOf(request.getServerPort()));
            builder.setPath(request.getContextPath());
            applicationBaseUri = builder.build();
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationServiceException(CommonPrivateError.URL_CREATION_FAILED.message(), e);
        }
        return applicationBaseUri;
    }

    /**
     * Extend the path of a URI.
     */
    public static URI addPathToUri(URI baseUri, String path) {
        URI uri = null;
        try {
            URIBuilder builder = new URIBuilder(baseUri);
            builder.setPath(builder.getPath() + PATH_SEPARATOR + path);
            uri = builder.build();
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationServiceException(CommonPrivateError.URL_CREATION_FAILED.message(), e);
        }
        return uri;
    }

    /**
     * Extend the path of a URI.
     */
    public static String addPathToUriAsUrlString(URI baseUri, String path) {
        return getUrlForUriAsString(addPathToUri(baseUri, path));
    }

    /**
     * Extend the path of a URI and add a new parameter.
     */
    public static URI addPathAndParameterToUri(URI baseUri, String path, String parameterName, String parameterValue) {
        URI uri = null;
        try {
            URIBuilder builder = new URIBuilder(addPathToUri(baseUri, path));
            builder.addParameter(parameterName, parameterValue);
            uri = builder.build();
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationServiceException(CommonPrivateError.URL_CREATION_FAILED.message(), e);
        }
        return uri;
    }

    /**
     * Extend the path of a URI and add a new parameter.
     */
    public static String addPathAndParameterToUriAsUrlString(URI baseUri, String path, String parameterName,
                                                             String parameterValue) {
        return getUrlForUriAsString(addPathAndParameterToUri(baseUri, path, parameterName, parameterValue));
    }

    /**
     * Translate a URI to a URL.
     */
    public static URL getUrlForUri(URI uri) {
        URL url = null;
        try {
            url = uri.toURL();
        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationServiceException(CommonPrivateError.URL_CREATION_FAILED.message(), e);
        }
        return url;
    }

    /**
     * Translate a URI to a URL.
     */
    public static String getUrlForUriAsString(URI uri) {
        return getUrlForUri(uri).toString();
    }

    /**
     * Translate a String to a URI
     */
    public static URI getUriFromAString(String uriAsString) {
        try {
            return new URI(uriAsString);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationServiceException(CommonPrivateError.URL_CREATION_FAILED.message(), e);
        }
    }
}
