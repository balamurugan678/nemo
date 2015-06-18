package com.novacroft.nemo.common.utils;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.net.URI;
import java.net.URISyntaxException;

import static com.novacroft.nemo.test_support.UriTestUtil.*;
import static org.junit.Assert.assertEquals;

/**
 * UriUrlUtil unit tests
 */
public class UriUrlUtilTest {
    @Test
    public void shouldGetApplicationBaseUri() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.setScheme(SCHEME_1);
        mockRequest.setServerName(HOST_1);
        mockRequest.setServerPort(PORT_1);
        mockRequest.setContextPath(PATH_1);

        URI result = UriUrlUtil.getApplicationBaseUri(mockRequest);

        assertEquals(SCHEME_1, result.getScheme());
        assertEquals(HOST_1, result.getHost());
        assertEquals(PORT_1, result.getPort());
        assertEquals(PATH_1, result.getPath());
    }

    @Test
    public void shouldAddPathToUri() throws URISyntaxException {
        URI uri = new URI(URI_1);
        String result = UriUrlUtil.addPathToUriAsUrlString(uri, PATH_2);
        String expectedResult = URI_1 + "/" + PATH_2;
        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldAddPathAndParameterToUri() throws URISyntaxException {
        URI uri = new URI(URI_1);
        String result = UriUrlUtil.addPathAndParameterToUriAsUrlString(uri, PATH_2, PARAMETER_1, VALUE_1);
        String expectedResult = URI_1 + "/" + PATH_2 + "?" + PARAMETER_1 + "=" + VALUE_1;
        assertEquals(expectedResult, result);
    }
}
