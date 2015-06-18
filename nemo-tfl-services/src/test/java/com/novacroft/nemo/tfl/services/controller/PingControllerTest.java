package com.novacroft.nemo.tfl.services.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * PingController unit tests
 */
public class PingControllerTest {
    private static final String METHOD_KEY = "method";
    private static final String PINGED_AT_KEY = "pingedAt";
    private PingController controller;

    @Before
    public void setUp() {
        this.controller = mock(PingController.class);
        when(this.controller.doPing(anyString())).thenCallRealMethod();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldGetPing() {
        doCallRealMethod().when(this.controller).getPing();
        checkResult(this.controller.getPing(), RequestMethod.GET.name());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldPutPing() {
        doCallRealMethod().when(this.controller).putPing();
        checkResult(this.controller.putPing(), RequestMethod.PUT.name());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldPostPing() {
        doCallRealMethod().when(this.controller).postPing();
        checkResult(this.controller.postPing(), RequestMethod.POST.name());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldDeletePing() {
        doCallRealMethod().when(this.controller).deletePing();
        checkResult(this.controller.deletePing(), RequestMethod.DELETE.name());
    }

    private void checkResult(Map<String, String> result, String expectedMethod) {
        assertTrue(result.containsKey(METHOD_KEY));
        assertTrue(result.containsKey(PINGED_AT_KEY));
        assertEquals(expectedMethod, result.get(METHOD_KEY));
    }
}
