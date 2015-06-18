package com.novacroft.nemo.common.support;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * DirectoryFileFilter unit tests
 */
public class DirectoryFileFilterTest {
    @BeforeClass
    public static void setUp() {
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        if (!tmpDir.exists() || !tmpDir.isDirectory()) {
            fail(String.format("No temporary directory [%s]!", System.getProperty("java.io.tmpdir")));
        }
    }

    @Test
    public void shouldAcceptDirectory() {
        DirectoryFileFilter filter = new DirectoryFileFilter();
        assertTrue(filter.accept(new File(System.getProperty("java.io.tmpdir"))));
    }

    @Test
    public void shouldNotAcceptFile() throws IOException {
        DirectoryFileFilter filter = new DirectoryFileFilter();
        File testFile = File.createTempFile("shouldNotAcceptFileTest", ".tmp", new File(System.getProperty("java.io.tmpdir")));
        assertFalse(filter.accept(testFile));
    }
}
