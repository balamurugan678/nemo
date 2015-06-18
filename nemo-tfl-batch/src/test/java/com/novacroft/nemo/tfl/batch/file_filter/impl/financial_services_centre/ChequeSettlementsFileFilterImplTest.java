package com.novacroft.nemo.tfl.batch.file_filter.impl.financial_services_centre;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChequeSettlementsFileFilterImplTest {
    private FileFilter filter;
    private File testFile;

    @Before
    public void setUp() {
        this.filter = new ChequeSettlementsFileFilterImpl();
    }

    @Test
    public void shouldAccept() {
        this.testFile = new File("/tmp/HX01.csv");
        assertTrue(this.filter.accept(this.testFile));
    }

    @Test
    public void shouldNotAcceptBadSuffix() {
        this.testFile = new File("/tmp/HX01.abc");
        assertFalse(this.filter.accept(this.testFile));
    }

    @Test
    public void shouldNotAcceptBadPrefix() {
        this.testFile = new File("/tmp/AB01.csv");
        assertFalse(this.filter.accept(this.testFile));
    }

    @Test
    public void shouldNotAcceptBadSequence() {
        this.testFile = new File("/tmp/HX0A0.csv");
        assertFalse(this.filter.accept(this.testFile));
    }

}