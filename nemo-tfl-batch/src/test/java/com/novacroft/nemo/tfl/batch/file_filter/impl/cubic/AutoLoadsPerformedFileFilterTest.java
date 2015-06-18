package com.novacroft.nemo.tfl.batch.file_filter.impl.cubic;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * AutoLoadsPerformedFileFilter unit tests
 */
public class AutoLoadsPerformedFileFilterTest {
    @Test
    public void shouldAcceptValidAutoLoadsPerformedFileWith() {
        File testFile = new File("/tmp/wap_08354_0001.dat");
        AutoLoadsPerformedFileFilter filter = new AutoLoadsPerformedFileFilter();
        assertTrue(filter.accept(testFile));
    }

    @Test
    public void shouldRejectAutoLoadsPerformedFileBadPrefix() {
        File testFile = new File("/tmp/WAP_08354_0001.dat");
        AutoLoadsPerformedFileFilter filter = new AutoLoadsPerformedFileFilter();
        assertFalse(filter.accept(testFile));
    }

    @Test
    public void shouldRejectAutoLoadsPerformedFileWithBadUtsDate() {
        File testFile = new File("/tmp/wap_abc_0001.dat");
        AutoLoadsPerformedFileFilter filter = new AutoLoadsPerformedFileFilter();
        assertFalse(filter.accept(testFile));
    }

    @Test
    public void shouldRejectAutoLoadsPerformedFileWithBadSequence() {
        File testFile = new File("/tmp/wap_08354_123.dat");
        AutoLoadsPerformedFileFilter filter = new AutoLoadsPerformedFileFilter();
        assertFalse(filter.accept(testFile));
        testFile = new File("/tmp/wap_08354_abc.dat");
        assertFalse(filter.accept(testFile));
        testFile = new File("/tmp/wap_08354_12345.dat");
        assertFalse(filter.accept(testFile));
    }
}
