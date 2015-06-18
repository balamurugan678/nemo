package com.novacroft.nemo.tfl.batch.util;

import org.junit.Test;

import static com.novacroft.nemo.tfl.batch.util.JobUtil.SEPARATOR;
import static org.junit.Assert.assertTrue;

public class JobUtilTest {
    private static final String TEST_NAME = "test-name";
    private static final String TEST_IDENTIFIER = "test-identifier";
    private static final String DATE_PATTERN = "[0-9]{4}-[0-9]{2}-[0-9]{2}.*";

    @Test(expected = AssertionError.class)
    public void nameAndIdentifierArgumentCreateIdentityShouldFailWithoutName() {
        JobUtil.createIdentity(null, null);
    }

    @Test(expected = AssertionError.class)
    public void nameArgumentCreateIdentityShouldFailWithoutName() {
        JobUtil.createIdentity(null);
    }

    @Test
    public void shouldNameAndIdentifierArgumentCreateIdentityWithIdentifier() {
        String result = JobUtil.createIdentity(TEST_NAME, TEST_IDENTIFIER);
        String pattern = TEST_NAME + SEPARATOR + TEST_IDENTIFIER + SEPARATOR + DATE_PATTERN;
        assertTrue(result.matches(pattern));
    }

    @Test
    public void shouldNameAndIdentifierArgumentCreateIdentityWithoutIdentifier() {
        String result = JobUtil.createIdentity(TEST_NAME, null);
        String pattern = TEST_NAME + SEPARATOR + DATE_PATTERN;
        assertTrue(result.matches(pattern));
    }

    @Test
    public void shouldNameArgumentCreateIdentity() {
        String result = JobUtil.createIdentity(TEST_NAME);
        String pattern = TEST_NAME + SEPARATOR + DATE_PATTERN;
        assertTrue(result.matches(pattern));
    }
}