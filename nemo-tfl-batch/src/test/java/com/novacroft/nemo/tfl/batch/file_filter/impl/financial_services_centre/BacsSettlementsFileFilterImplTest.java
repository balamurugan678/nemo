package com.novacroft.nemo.tfl.batch.file_filter.impl.financial_services_centre;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileFilter;

import org.junit.Before;
import org.junit.Test;

public class BacsSettlementsFileFilterImplTest {

	private FileFilter filter;
    private File testFile;
    
    
    @Before
    public void setUp() {
    	
    	filter = new BacsSettlementsFileFilterImpl();
    }
    
    @Test
	public void shouldAcceptCorrectFile() throws Exception {
    	testFile = new File("BO3.csv");
		assertTrue(filter.accept(testFile));
	}
    
    @Test
   	public void shouldRejectNonCSVExtension() throws Exception {
       	testFile = new File("BO3.txt");
   		assertFalse(filter.accept(testFile));
   	}
    
    @Test
   	public void shouldAcceptNoDigitAtEnd() throws Exception {
       	testFile = new File("BO.csv");
   		assertTrue(filter.accept(testFile));
   	}
    
    
    @Test
   	public void shouldRejectBadSequence() throws Exception {
       	testFile = new File("BOP34.csv");
   		assertFalse(filter.accept(testFile));
   	}
    
    @Test
   	public void shouldRejectBadInDirectorySequence() throws Exception {
       	testFile = new File("tmp/BOP34.csv");
   		assertFalse(filter.accept(testFile));
   	}
}
