package com.novacroft.nemo.tfl.batch.file_filter.impl.cubic;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * AdHocDistributionsFileFilter unit tests
 */
public class AdHocDistributionsFileFilterTest {
    @Test
    public void shouldAcceptValidAdHocDistributionsFile() {
        File testFile = new File("/tmp/wad_08354_0001.dat");
        AdHocDistributionsFileFilter filter = new AdHocDistributionsFileFilter();
        assertTrue(filter.accept(testFile));
    }

    @Test
    public void shouldRejectAdHocDistributionsFileWithBadPrefix() {
        File testFile = new File("/tmp/WAD_08354_0001.dat");
        AdHocDistributionsFileFilter filter = new AdHocDistributionsFileFilter();
        assertFalse(filter.accept(testFile));
    }

    @Test
    public void shouldRejectAdHocDistributionsFileWithBadUtsDate() {
        File testFile = new File("/tmp/wad_abc_0001.dat");
        AdHocDistributionsFileFilter filter = new AdHocDistributionsFileFilter();
        assertFalse(filter.accept(testFile));
    }

    @Test
    public void shouldRejectAdHocDistributionsFileWithBadSequence() {
        File testFile = new File("/tmp/wad_08345_abc.dat");
        AdHocDistributionsFileFilter filter = new AdHocDistributionsFileFilter();
        assertFalse(filter.accept(testFile));
        testFile = new File("/tmp/wad_08345_123.dat");
        assertFalse(filter.accept(testFile));
        testFile = new File("/tmp/wad_08345_12345.dat");
        assertFalse(filter.accept(testFile));
    }

}
