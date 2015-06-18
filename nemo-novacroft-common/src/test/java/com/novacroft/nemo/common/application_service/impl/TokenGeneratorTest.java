package com.novacroft.nemo.common.application_service.impl;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for TokenGenerator
 *
 * <p>Note: not trying to test the underlying Base64 encoder here!</p>
 */
public class TokenGeneratorTest {
    static final int TEST_TOKEN_LENGTH = 32;

    @Test
    public void shouldCreateRawToken() {
        TokenGeneratorImpl tokenGenerator = new TokenGeneratorImpl();
        byte[] result = tokenGenerator.createRawToken(TEST_TOKEN_LENGTH);
        assertNotNull(result);
        assertEquals(TEST_TOKEN_LENGTH, result.length);
    }

    @Test
    public void shouldEncodeToken() {
        byte[] testRawToken = "a-test-token".getBytes();
        TokenGeneratorImpl tokenGenerator = new TokenGeneratorImpl();
        String result = tokenGenerator.encodeToken(testRawToken);
        assertNotNull(result);
        assertNotEquals("", result);
    }

    @Test
    public void shouldUrlSafeEncodeToken() {
        byte[] testRawToken = "a-test-token".getBytes();
        TokenGeneratorImpl tokenGenerator = new TokenGeneratorImpl();
        String result = tokenGenerator.urlSafeEncodeToken(testRawToken);
        assertNotNull(result);
        assertNotEquals("", result);
    }

    @Test
    public void shouldCreateUrlSafeToken() {
        TokenGeneratorImpl tokenGenerator = new TokenGeneratorImpl();
        String result = tokenGenerator.createUrlSafeToken(TEST_TOKEN_LENGTH);
        assertNotNull(result);
        assertNotEquals("", result);
    }

    @Test
    public void shouldCreateToken() {
        TokenGeneratorImpl tokenGenerator = new TokenGeneratorImpl();
        String result = tokenGenerator.createToken(TEST_TOKEN_LENGTH);
        assertNotNull(result);
        assertNotEquals("", result);
    }
}
