package com.novacroft.nemo.tfl.batch.file_filter.impl.cubic;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * CurrentActionListFileFilter unit tests
 */
public class CurrentActionListFileFilterTest {
    @Test
    public void shouldAcceptValidCurrentActionListFile() {
        File testFile = new File("/tmp/wca_08354_0001.dat");
        CurrentActionListFileFilter filter = new CurrentActionListFileFilter();
        assertTrue(filter.accept(testFile));
    }

    @Test
    public void shouldRejectCurrentActionListFileWithBadPrefix() {
        File testFile = new File("/tmp/WCA_08354_0001.dat");
        CurrentActionListFileFilter filter = new CurrentActionListFileFilter();
        assertFalse(filter.accept(testFile));
    }

    @Test
    public void shouldRejectCurrentActionListFileWithBadUtsDate() {
        File testFile = new File("/tmp/wca_abc_0001.dat");
        CurrentActionListFileFilter filter = new CurrentActionListFileFilter();
        assertFalse(filter.accept(testFile));
    }

    @Test
    public void shouldRejectCurrentActionListFileWithBadSequence() {
        File testFile = new File("/tmp/wca_08354_123.dat");
        CurrentActionListFileFilter filter = new CurrentActionListFileFilter();
        assertFalse(filter.accept(testFile));
        testFile = new File("/tmp/wca_08354_abc.dat");
        assertFalse(filter.accept(testFile));
        testFile = new File("/tmp/wca_08354_12345.dat");
        assertFalse(filter.accept(testFile));
    }
}
