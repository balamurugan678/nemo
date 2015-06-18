package com.novacroft.nemo.common.utils;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * ObjectUtil unit tests
 */
public class ObjectUtilTest {
    protected final static Integer TEST_INTEGER = 99;
    protected final static String TEST_STRING = "ABC";
    protected final static String TEST_EMPTY_STRING = "";
    protected final static String TEST_NULL = null;

    @Test
    public void isBlankStringShouldReturnFalseForObjectThatIsNotString() {
        assertFalse(ObjectUtil.isObjectBlankString(TEST_INTEGER));
    }

    @Test
    public void isBlankStringShouldReturnTrueForObjectThatIsEmptyString() {
        assertTrue(ObjectUtil.isObjectBlankString(TEST_EMPTY_STRING));
    }

    @Test
    public void isBlankStringShouldReturnFalseForObjectThatIsPopulatedString() {
        assertFalse(ObjectUtil.isObjectBlankString(TEST_STRING));
    }

    @Test
    public void isNullOrBlankStringShouldReturnTrueForNull() {
        assertTrue(ObjectUtil.isObjectNullOrBlankString(TEST_NULL));
    }

    @Test
    public void isNullOrBlankStringShouldReturnFalseForNonNull() {
        assertFalse(ObjectUtil.isObjectNullOrBlankString(TEST_INTEGER));
    }

    @Test
    public void isArgsNotBlankShouldReturnFalseForNull() {
        assertFalse(ObjectUtil.isObjectArgsNotBlank(TEST_NULL));
    }

    @Test
    public void isArgsNotBlankShouldReturnFalseForEmptyString() {
        assertFalse(ObjectUtil.isObjectArgsNotBlank(TEST_NULL, TEST_EMPTY_STRING));
    }

    @Test
    public void isArgsNotBlankShouldReturnTrueForNonNull() {
        assertTrue(ObjectUtil.isObjectArgsNotBlank(TEST_INTEGER));
    }

    @Test
    public void isArgsNotBlankShouldReturnTrueForNonNulls() {
        assertTrue(ObjectUtil.isObjectArgsNotBlank(TEST_NULL, TEST_EMPTY_STRING, TEST_STRING, TEST_INTEGER));
    }
}
