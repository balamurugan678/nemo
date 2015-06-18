package com.novacroft.nemo.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MetaphoneUtilTest {
    private static final String TEST_VALUE = "Rhubarb";

    @Test
    public void shouldEncodeValue() {
        assertEquals("RPRP", MetaphoneUtil.encode(TEST_VALUE));
    }

    @Test
    public void shouldEncodeEmpty() {
        assertEquals(StringUtils.EMPTY, MetaphoneUtil.encode(StringUtils.EMPTY));
    }

    @Test
    public void shouldEncodeNull() {
        assertEquals(StringUtils.EMPTY, MetaphoneUtil.encode(null));
    }
}