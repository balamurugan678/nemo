package com.novacroft.nemo.tfl.batch.file_filter.impl.cubic;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * AutoLoadChangesFileFilter unit tests
 */
public class AutoLoadChangesFileFilterTest {

    @Test
    public void shouldAcceptValidAutoLoadChangesFile() {
        File testFile = new File("/tmp/wac_08354_0001.dat");
        AutoLoadChangesFileFilter filter = new AutoLoadChangesFileFilter();
        assertTrue(filter.accept(testFile));
    }

    @Test
    public void shouldRejectAutoLoadChangesFileWithBadPrefix() {
        File testFile = new File("/tmp/WAC_08354_0001.dat");
        AutoLoadChangesFileFilter filter = new AutoLoadChangesFileFilter();
        assertFalse(filter.accept(testFile));
    }

    @Test
    public void shouldRejectAutoLoadChangesFileWithBadUtsDate() {
        File testFile = new File("/tmp/wac_abc_0001.dat");
        AutoLoadChangesFileFilter filter = new AutoLoadChangesFileFilter();
        assertFalse(filter.accept(testFile));
    }

    @Test
    public void shouldRejectAutoLoadChangesFileWithBadSequence() {
        File testFile = new File("/tmp/wac_08354_123.dat");
        AutoLoadChangesFileFilter filter = new AutoLoadChangesFileFilter();
        assertFalse(filter.accept(testFile));
        testFile = new File("/tmp/wac_08354_abc.dat");
        assertFalse(filter.accept(testFile));
        testFile = new File("/tmp/wac_08354_12345.dat");
        assertFalse(filter.accept(testFile));
    }
}
