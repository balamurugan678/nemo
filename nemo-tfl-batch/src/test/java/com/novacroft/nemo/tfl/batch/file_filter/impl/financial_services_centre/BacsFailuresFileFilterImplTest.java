package com.novacroft.nemo.tfl.batch.file_filter.impl.financial_services_centre;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileFilter;

import org.junit.Before;
import org.junit.Test;

public class BacsFailuresFileFilterImplTest {
	
	private FileFilter filter;
    private File testFile;
    
    
    @Before
    public void setUp() {
    	
    	filter = new BacsFailuresFileFilterImpl();
    }
    
    @Test
	public void shouldAcceptCorrectFile() throws Exception {
    	testFile = new File("BF3.csv");
		assertTrue(filter.accept(testFile));
	}
    
    @Test
   	public void shouldRejectNonCSVExtension() throws Exception {
       	testFile = new File("BF3.txt");
   		assertFalse(filter.accept(testFile));
   	}
    
    @Test
   	public void shouldAcceptNoDigitAtEnd() throws Exception {
       	testFile = new File("BF.csv");
   		assertTrue(filter.accept(testFile));
   	}
    
    
    @Test
   	public void shouldRejectBadSequence() throws Exception {
       	testFile = new File("BFP34.csv");
   		assertFalse(filter.accept(testFile));
   	}
    
}
